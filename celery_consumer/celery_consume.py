import json, time

from celery import Celery
from celery import bootsteps
from kombu import Consumer, Exchange, Queue

queue = Queue("input.queue", Exchange("default"), "input.key")

app = Celery()
app.config_from_object("celery_config")

@app.task
def text_to_speech(uuid, email, text):
    from tts_process import add_synth, is_set, make_tts
    if not is_set(email):
        add_synth(email)

    make_tts(email, uuid, text)
        
    return f"{email}/{uuid}.wav"


# Decalring the general input message handler
class InputMessageHandler(object):
    def handle(self, body):
        _type = body["type"]
        
        # if you want to accept only specific type of message, you need below
        if _type == "ETL":
            ETLMessageHandler().handle(body)
        
        # if body is json type, you need below
        '''
        body_json = json.loads(body)  
        _type = body_json["type"]

        if _type == "ETL":
            ETLMessageHandler().handle(body_json)
        '''


# Declaring the ETL message handler
class ETLMessageHandler(object):
    def handle(self, body):
        print(f"Working on ETL for message: {body}")
        # TODO: Calling out your Celery tasks here
        _uuid = body["uuid"]
        _email = body["email"]
        _text = body["text"]
        task = text_to_speech.delay(_uuid, _email, _text)


# Declaring the bootstep for our purposes
class InputMessageConsumerStep(bootsteps.ConsumerStep):
    def get_consumers(self, channel):
        return [Consumer(channel,
                         queues=[queue],
                         callbacks=[self.handle_message],
                         accept=["json"])]

    def handle_message(self, body, message):
        InputMessageHandler().handle(body)
        message.ack()


app.steps["consumer"].add(InputMessageConsumerStep)