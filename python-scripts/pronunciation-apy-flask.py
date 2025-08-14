from flask import Flask, request, jsonify
from resemblyzer import VoiceEncoder, preprocess_wav
from pathlib import Path
from itertools import groupby
from tqdm import tqdm
import numpy as np
from pydub import AudioSegment
import os

app = Flask(__name__)
encoder = VoiceEncoder()

# Pré-calcul des embeddings de référence au démarrage
wav_fpaths = list(Path("audio_data", "samake").glob("*.m4a"))
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

@app.route('/compare-pronunciation', methods=['POST'])
def compare_pronunciation():
    audio_file = request.files.get('audioFile')
    audio_file_name = request.form.get('audioFileName', 'nom-inconnu')

    if not audio_file:
        return jsonify({'error': 'No audio file provided'}), 400

    # Sauvegarde dans un fichier temporaire
    temp_webm_path = "/tmp/uploaded_audio.webm"
    audio_file.save(temp_webm_path)

    # Conversion m4a ➜ wav
    temp_wav_path = "/tmp/uploaded_audio.wav"
    audio = AudioSegment.from_file(temp_webm_path, format="webm")
    audio.export(temp_wav_path, format="wav")
    os.remove(temp_webm_path)

    # Prétraitement et calcul d'empreinte
    audio_file_wav = preprocess_wav(temp_wav_path)
    embed_received = encoder.embed_utterance(audio_file_wav)

    # Calcul similarité avec les références
    similarities = [np.inner(embed_received, ref) for ref in all_reference_embeds]
    mean_similarity = float(np.mean(similarities))
    similarity_percentage = round(mean_similarity * 100, 2)

    return jsonify({
        "audioName": audio_file_name,
        "score": similarity_percentage
    })

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True, port=5000)
