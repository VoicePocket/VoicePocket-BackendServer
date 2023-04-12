# celeryconfig.py

# broker_url = 'amqp://sample:sample!@localhost:5672/rabbit_example' # local
broker_url = 'amqp://sample:sample!@rabbit:5672/rabbit_example'   # docker
task_serializer = 'json'
result_serializer = 'json'
accept_content = ['json']
timezone = 'Asia/Seoul'
enable_utc = True