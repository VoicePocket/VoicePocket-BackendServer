import os, sys, tarfile, pickle

from TTS.utils.synthesizer import Synthesizer
from text_process import normalize_text
from bucket_process import down_model_from_bucket, upload_wav_to_bucket

import redis

# 레디스 연결
rd = redis.Redis(host='voicepocket_redis', port=6379, db=0)


def is_set(email):
    return rd.get(email) != None

def add_synth(email):
    # TODO: best 모델 구성이 결정되면 그에 맞춰 코드를 수정할 것!
    # voice_model_path = f"./api_server/voice_model/{email}_best_model.pth.tar"
    
    # if not os.path.isfile(voice_model_path):
    #     down_model_from_bucket(email)
        
    #     best_model_tar = tarfile.open(voice_model_path)
    #     best_model_tar.extractall(f"./api_server/voice_model")

    if email == "ssh@gmail.com":
        synthesizer = Synthesizer(
            tts_checkpoint = "./voice_model_new/ssh_checkpoint_84000.pth",
            tts_config_path = "./voice_model_new/ssh_config.json"
        )
    else:
        synthesizer = Synthesizer(
            tts_checkpoint = "./voice_model_new/best_model.pth",
            tts_config_path = "./voice_model_new/config.json"
        )

    serialized_syn = pickle.dumps(synthesizer)
    rd.set(email, serialized_syn)
    
def make_tts(email, uuid, text):
    wav_path = f'./wav/{uuid}.wav'
    
    serialized_syn = rd.get(email)
    syn = pickle.loads(serialized_syn)

    symbol = syn.tts_config.characters.characters
    text = normalize_text(text, symbol)
    wav = syn.tts(text, None, None)
    syn.save_wav(wav, wav_path)
    
    upload_wav_to_bucket(wav_path, email, uuid)
    
    os.remove(wav_path)