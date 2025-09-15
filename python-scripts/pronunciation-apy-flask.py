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
    try:
        audio_file = request.files.get('audioFile')
        audio_file_name = request.form.get('audioFileName', 'nom-inconnu')

        if not audio_file:
            return jsonify({'error': 'No audio file provided'}), 400

        filename = secure_filename(audio_file.filename or "uploaded")
        ext = Path(filename).suffix.lower().lstrip('.')
        mimetype = (getattr(audio_file, "mimetype", None) or "").lower()

        if ext in ALLOWED_EXT and ext != "":
            fmt = ext
        else:
            fmt = MIME_TO_FMT.get(mimetype)

        if not fmt:
            return jsonify({'error': f'Unsupported file type', 'mimetype': mimetype, 'filename': filename}), 400

        # Sauvegarde temporaire
        with tempfile.NamedTemporaryFile(suffix=f".{fmt}", delete=False) as tmp_f:
            audio_file.save(tmp_f.name)
            in_path = tmp_f.name

        # Conversion vers wav si besoin
        if fmt != "wav":
            # Vérifier ffmpeg dispo (pydub en a besoin) :
            import shutil
            if not shutil.which("ffmpeg"):
                app.logger.error("ffmpeg is not available on PATH; cannot convert %s -> wav", fmt)
                return jsonify({'error': 'FFMPEG_NOT_AVAILABLE'}), 500

            with tempfile.NamedTemporaryFile(suffix=".wav", delete=False) as tmp_wav:
                AudioSegment.from_file(in_path, format=fmt).export(tmp_wav.name, format="wav")
                audio = tmp_wav.name
            os.remove(in_path)
        else:
            audio = in_path

        # Fichier quasi vide
        if os.path.getsize(audio) < 1024:
            os.remove(audio)
            return jsonify({"audioName": filename, "score": 0.0, "reason": "empty_file"}), 200

        # Prétraitement
        audio_wav = preprocess_wav(audio)
        os.remove(audio)

        if audio_wav.size < int(0.30 * 16000):
            return jsonify({"audioName": filename, "score": 0.0, "reason": "too_short"}), 200

        if float(np.max(np.abs(audio_wav))) < 0.005:
            return jsonify({"audioName": filename, "score": 0.0, "reason": "silent"}), 200

        # Récup des références
        ref_dir = Path(os.getenv("REFERENCE_DIR", "audio_data")) / audio_file_name.lower()
        wav_fpaths = sorted(ref_dir.glob("*.m4a")) + sorted(ref_dir.glob("*.wav"))

        if not wav_fpaths:
            app.logger.warning("No reference files in %s", ref_dir)
            return jsonify({
                "audioName": audio_file_name,
                "score": 0.0,
                "reason": "no_reference_audio",
                "detail": f"No files found in {str(ref_dir)}"
            }), 200

        # Groupby (même dossier => 1 groupe, mais OK)
        speaker_wavs = {
            speaker: list(map(preprocess_wav, group_files))
            for speaker, group_files in groupby(
                wav_fpaths,
                lambda p: p.parent.stem
            )
        }

        all_reference_embeds = []
        for wavs in speaker_wavs.values():
            for wav in wavs:
                embed = encoder.embed_utterance(wav)
                all_reference_embeds.append(embed)

        if not all_reference_embeds:
            return jsonify({
                "audioName": audio_file_name,
                "score": 0.0,
                "reason": "no_reference_embed"
            }), 200

        embed_received = encoder.embed_utterance(audio_wav)
        similarities = [np.inner(embed_received, ref) for ref in all_reference_embeds]

        if not similarities:
            return jsonify({
                "audioName": audio_file_name,
                "score": 0.0,
                "reason": "no_similarity_values"
            }), 200

        mean_similarity = float(np.mean(similarities))
        # clamp pour éviter NaN/inf
        if not np.isfinite(mean_similarity):
            mean_similarity = 0.0

        similarity_percentage = round(max(0.0, min(1.0, mean_similarity)) * 100, 2)

        return jsonify({
            "audioName": audio_file_name,
            "score": similarity_percentage
        }), 200

    except Exception as e:
        app.logger.exception("compare_pronunciation failed")
        return jsonify({"error": "INTERNAL_ERROR", "detail": str(e)}), 500
