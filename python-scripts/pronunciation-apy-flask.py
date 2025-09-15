from flask import Flask, request, jsonify,abort
from resemblyzer import VoiceEncoder, preprocess_wav
from pathlib import Path
from itertools import groupby
from tqdm import tqdm
import numpy as np
from pydub import AudioSegment
from werkzeug.utils import secure_filename
import os,tempfile

app = Flask(__name__)
encoder = VoiceEncoder()
API_KEY = os.getenv("FLASK_SHARED_SECRET", "")
ALLOWED_EXT = {"webm", "m4a", "wav", "mp3"}
MIME_TO_FMT = {
    "audio/webm": "webm",
    "audio/mp4": "m4a",
    "audio/x-m4a": "m4a",
    "video/mp4": "m4a",
    "audio/wav": "wav",
    "audio/x-wav": "wav",
    "audio/mpeg": "mp3",
}

@app.route("/", methods=["GET"])
def root():
    return "OK", 200

@app.route("/healthz", methods=["GET"])
def healthz():
    return jsonify(status="ok"), 200

def require_api_key():
    if API_KEY and request.headers.get("Api-Key") != API_KEY:
        abort(403)
        
@app.route('/compare-pronunciation', methods=['POST'])
def compare_pronunciation():
    require_api_key()
    audio_file = request.files.get('audioFile')
    audio_file_name = request.form.get('audioFileName', 'nom-inconnu')

    if not audio_file:
        return jsonify({'error': 'No audio file provided'}), 400

    filename = secure_filename(audio_file.filename)
    ext = Path(filename).suffix.lower().lstrip('.')
    if ext in ALLOWED_EXT and ext != "":
        fmt = ext
    else:
        fmt = MIME_TO_FMT.get(audio_file.mimetype)
    if not fmt:
        return jsonify({'error': f'Unsupported file type (mimetype: {audio_file.mimetype})'}),400
    
    with tempfile.NamedTemporaryFile(suffix=f".{fmt}", delete=False) as tmp_f:
        audio_file.save(tmp_f.name)
        in_path = tmp_f.name
    if fmt != "wav":
        with tempfile.NamedTemporaryFile(suffix=".wav", delete=False) as tmp_wav:
            AudioSegment.from_file(in_path, format=fmt).export(tmp_wav.name, format="wav")
            audio = tmp_wav.name
        os.remove(in_path)
    else :
        audio = in_path

        # 1) Fichier vide (ou quasi vide)
    if os.path.getsize(audio) < 1024:  # < 1 Ko
        os.remove(audio)
        return jsonify({"audioName": filename, "score": 0.0, "reason": "empty_file"})
    
    # 2) Après prétraitement : durée et amplitude
    audio_wav = preprocess_wav(audio)   # numpy array 16 kHz
    os.remove(audio)
    
    # Durée ~ nombre d'échantillons / 16000 (Hz)
    if audio_wav.size < int(0.30 * 16000):  # < 300 ms
        return jsonify({"audioName": filename, "score": 0.0, "reason": "too_short"})
    
    # Amplitude max très faible => quasi silence
    if float(np.max(np.abs(audio_wav))) < 0.005:
        return jsonify({"audioName": filename, "score": 0.0, "reason": "silent"})
    
    wav_fpaths = sorted(Path("audio_data", audio_file_name.lower()).glob("*.m4a"))
    speaker_wavs = {
    speaker: list(map(preprocess_wav, wav_fpaths)) for speaker, wav_fpaths in
    groupby(tqdm(wav_fpaths, "Preprocessing wavs", len(wav_fpaths), unit="wavs"),
            lambda wav_fpath: wav_fpath.parent.stem)
}

    all_reference_embeds = []
    for wavs in speaker_wavs.values():
        for wav in wavs:
            embed = encoder.embed_utterance(wav)
            all_reference_embeds.append(embed)

    # Prétraitement et calcul d'empreinte
    embed_received = encoder.embed_utterance(audio_wav)

    # Calcul similarité avec les références
    similarities = [np.inner(embed_received, ref) for ref in all_reference_embeds]
    mean_similarity = float(np.mean(similarities))
    similarity_percentage = round(mean_similarity * 100, 2)

    return jsonify({
        "audioName": audio_file_name,
        "score": similarity_percentage
    })

if __name__ == '__main__':
    app.run()
