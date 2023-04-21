package com.vp.voicepocket.domain.message.service;

import com.vp.voicepocket.domain.message.dto.TaskIdResponseDto;
import com.vp.voicepocket.domain.message.dto.TaskInfoResponseDto;
import com.vp.voicepocket.domain.message.exception.CTaskNotFinishedException;
import com.vp.voicepocket.domain.message.exception.CTaskNotFoundException;
import com.vp.voicepocket.domain.message.model.Message;
import lombok.Builder;
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

    public TaskIdResponseDto getTaskId(String uuid) {
        String taskId = stringRedisTemplate.opsForValue().get(uuid);
        if (taskId == null) throw new CTaskNotFoundException();

        JSONObject json = new JSONObject(taskId);
        return TaskIdResponseDto.builder()
                .taskId(json.optString("task_id"))
                .build();
    }

    public TaskInfoResponseDto getTaskStatus(String taskId) {
        String value = stringRedisTemplate.opsForValue().get("celery-task-meta-" + taskId);
        if (value == null) throw new CTaskNotFinishedException();

        JSONObject json = new JSONObject(value);
        return TaskInfoResponseDto.builder()
                .taskId(json.optString("task_id"))
                .status(json.optString("status"))
                .result(json.optString("result"))
                .build();
    }
}