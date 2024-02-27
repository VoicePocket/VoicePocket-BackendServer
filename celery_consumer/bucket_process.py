import os
from google.cloud import storage

os.environ["GOOGLE_APPLICATION_CREDENTIALS"]="./voicepocket-bucketKey.json"

BUCKET_NAME = 'bucket_vp'

storage_client = storage.Client()
bucket = storage_client.bucket(BUCKET_NAME)

# model이 bucket에 존재하는지 검사하는 메소드
def check_model_exist(email):
    model_name = f'{email}/{email}_best_model'
    
    return bucket.exists(model_name)

# voice model를 bucket에서 다운로드하는 메소드
def download_model_from_bucket(email):
    source_blob_name = f'{email}/{email}_best_model'

    blob = bucket.blob(source_blob_name)
    model = blob.download_as_string()

    return model

# 만들어진 음성 model을 bucket에 업로드하는 메소드
def upload_model_to_bucket(email, model):
    destination_blob_name = f'{email}/{email}_best_model'

    blob = bucket.blob(destination_blob_name)
    blob.upload_from_string(model)

# 생성한 음성 wav 파일을 bucket에 업로드하는 메소드
def upload_wav_to_bucket(wav_path, email, uuid):
    destination_blob_name = f'{email}/{uuid}.wav'
    
    blob = bucket.blob(destination_blob_name)
    blob.upload_from_filename(wav_path)
