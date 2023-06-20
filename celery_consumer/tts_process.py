import os, sys, tarfile, pickle

from TTS.utils.synthesizer import Synthesizer
from text_process import normalize_text
from bucket_process import down_model_from_bucket, upload_wav_to_bucket


def is_set(email):
    return os.path.exists(f'./synth/{email}_synth.p')

def add_synth(email):
    # TODO: best 모델 구성이 결정되면 그에 맞춰 코드를 수정할 것!
    # voice_model_path = f"./api_server/voice_model/{email}_best_model.pth.tar"
    
    # if not os.path.isfile(voice_model_path):
    #     down_model_from_bucket(email)
        
    #     best_model_tar = tarfile.open(voice_model_path)
    #     best_model_tar.extractall(f"./api_server/voice_model")
    
    synthesizer = Synthesizer(
        tts_checkpoint = f"./voice_model_new/{email}_model.pth",
        tts_config_path = f"./voice_model_new/{email}_config.json"
    )

    with open(f'./synth/{email}_synth.p', 'wb') as p:
        pickle.dump(synthesizer, p)

    
def make_tts(email, uuid, text):
    wav_path = f'./wav/{uuid}.wav'
    
    with open(f'./synth/{email}_synth.p', 'rb') as p:
        syn = pickle.load(p)

    symbol = syn.tts_config.characters.characters
    text = normalize_text(text, symbol)
    wav = syn.tts(text, None, None)
    syn.save_wav(wav, wav_path)
    
    upload_wav_to_bucket(wav_path, email, uuid)
    
    os.remove(wav_path)