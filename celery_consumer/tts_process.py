import os, pickle

from TTS.utils.synthesizer import Synthesizer
from text_process import normalize_text
from bucket_process import download_model_from_bucket, upload_wav_to_bucket, check_model_exist, upload_model_to_bucket


def is_set(email):
    return check_model_exist(email)

def add_synth(email):
    # TODO: download original_wav file from bucket

    # TODO: setting synthesizer from original_wav
    synthesizer = Synthesizer(
        tts_checkpoint = f"./voice_model_new/{email}_model.pth",
        tts_config_path = f"./voice_model_new/{email}_config.json"
    )

    # upload synthesizer to bucket
    upload_model_to_bucket(email, pickle.dumps(synthesizer))

    
def make_tts(email, uuid, text):
    wav_path = f'./wav/{uuid}.wav'
    model = download_model_from_bucket(email)
    syn = pickle.loads(model)

    symbol = syn.tts_config.characters.characters
    text = normalize_text(text, symbol)
    wav = syn.tts(text, None, None)
    syn.save_wav(wav, wav_path)
    
    upload_wav_to_bucket(wav_path, email, uuid)
    
    os.remove(wav_path)