package com.vp.voicepocket.domain.message.service;

import com.vp.voicepocket.domain.message.model.Message;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private static final Logger log = LoggerFactory.getLogger(MessageService.class);
    private final RabbitTemplate rabbitTemplate;    // RabbitTemplate을 통해 Exchange에 메세지를 보내도록 설정
    private final StringRedisTemplate stringRedisTemplate;

    public void sendMessage(Message message) {
        rabbitTemplate.convertAndSend("default", "input.key", message);
    }

    public String getTaskStatus(String taskId) {
        String value = stringRedisTemplate.opsForValue().get("celery-task-meta-" + taskId);
        if (value == null) return null;

        JSONObject json = new JSONObject(value);
        return json.optString("result");
    }

    public String getTaskId(String uuid) {
        String taskId = stringRedisTemplate.opsForValue().get(uuid);
        if (taskId == null) return null;

        JSONObject json = new JSONObject(taskId);
        return json.optString("task_id");
    }
}