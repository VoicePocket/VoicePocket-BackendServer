import json

from celery import Celery
from celery import bootsteps
from kombu import Consumer, Exchange, Queue, Connection

# RabbitMQ Connection URL
CONNECTION_URL = 'amqp://sample:sample!@rabbit:5672/rabbit_example'

# RabbitMQ 연결 설정
connection = Connection(CONNECTION_URL)
producer = connection.Producer()

# Exchange, Queue 설정
input_exchange = Exchange("input.exchange")
input_queue = Queue("input.queue", input_exchange, "input.key")
output_exchange = Exchange("output.exchange")
output_queue = Queue('output.queue', output_exchange, "output.key")

app = Celery()
app.config_from_object("celery_config")

@app.task
def text_to_speech(uuid, email, text, sender_email):
    try:
        from tts_process import add_synth, is_set, make_tts
        url_path = f"{email}/{uuid}.wav"

        if not is_set(email):
            add_synth(email)

        make_tts(email, uuid, text)
        
        message = {"requestFrom": sender_email, "requestTo":email,"result": "TTS Request Success!", "url":url_path, "uuid": uuid}
    
    except Exception as e:
        message = {"requestFrom": sender_email, "requestTo":email,"result": e, "url":url_path, "uuid":uuid}
    
    finally:
        publish_message(message)

        return url_path


# publish message when TTS done
def publish_message(message):
    json_message = json.dumps(message)
    
    producer.publish(json_message, 
                        exchange=output_exchange, 
                        routing_key="output.key", 
                        content_type="application/json")


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
        sender_email = body["requestFrom"]
        reciever_email = body["requestTo"]
        _text = body["text"]
        
        task = text_to_speech.delay(_uuid, reciever_email, _text, sender_email)


# Declaring the bootstep for our purposes
class InputMessageConsumerStep(bootsteps.ConsumerStep):
    def get_consumers(self, channel):
        return [Consumer(channel,
                         queues=[input_queue],
                         callbacks=[self.handle_message],
                         accept=["json"])]

    def handle_message(self, body, message):
        InputMessageHandler().handle(body)
        message.ack()


app.steps["consumer"].add(InputMessageConsumerStep)