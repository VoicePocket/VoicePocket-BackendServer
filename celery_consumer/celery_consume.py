import redis, json

from celery import Celery
from celery import bootsteps
from kombu import Consumer, Exchange, Queue, Connection

# RabbitMQ Connection URL
CONNECTION_URL = 'amqp://sample:sample!@rabbit:5672/rabbit_example'

# Redis Connection URL
rd = redis.Redis(host='voicepocket_redis', port=6379, db=0)

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
def text_to_speech(uuid, email, text):
    from tts_process import add_synth, is_set, make_tts
    if not is_set(email):
        add_synth(email)

    make_tts(email, uuid, text)
    url_path = f"{email}/{uuid}.wav"

    test_message = {"url": url_path}
    publish_message(test_message)
        
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
        _email = body["email"]
        _text = body["text"]
        task = text_to_speech.delay(_uuid, _email, _text)
        _task_json = json.dumps({"task_id":task.id})
        rd.set(_uuid, _task_json)


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