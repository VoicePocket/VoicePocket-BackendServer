FROM python:3.10
USER root 

ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1
ENV PYTHONPATH "${PYTHONPATH}:/voice_pocket"

WORKDIR /voice_pocket
COPY requirements.txt /tts_server/

RUN apt-get update && apt-get install -y python3-pip libffi-dev python3-dev && apt-get install -y ffmpeg

RUN pip install --upgrade pip
RUN pip install -r /tts_server/requirements.txt

WORKDIR /voice_pocket