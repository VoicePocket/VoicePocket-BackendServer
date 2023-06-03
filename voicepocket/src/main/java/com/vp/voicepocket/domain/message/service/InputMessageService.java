package com.vp.voicepocket.domain.message.service;

import com.vp.voicepocket.domain.message.dto.TTSRequestDto;
import com.vp.voicepocket.domain.message.model.InputMessage;
import com.vp.voicepocket.domain.token.config.JwtProvider;
import com.vp.voicepocket.domain.token.exception.CAccessTokenException;
import com.vp.voicepocket.domain.user.entity.User;
import com.vp.voicepocket.domain.user.exception.CUserNotFoundException;
import com.vp.voicepocket.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InputMessageService {
    private static final Logger log = LoggerFactory.getLogger(InputMessageService.class);
    private final RabbitTemplate rabbitTemplate;    // RabbitTemplate을 통해 Exchange에 메세지를 보내도록 설정
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public void sendMessage(String jwtToken, TTSRequestDto ttsRequestDto) {
        Authentication authentication = getAuthByAccessToken(jwtToken);

        User user = userRepository.findById(Long.parseLong(authentication.getName()))
                .orElseThrow(CUserNotFoundException::new);

        InputMessage inputMessage = InputMessage.builder()
                .type(ttsRequestDto.getType())
                .uuid(ttsRequestDto.getUuid())
                .requestFrom(user.getEmail())
                .requestTo(ttsRequestDto.getRequestTo())
                .text(ttsRequestDto.getText())
                .build();

        rabbitTemplate.convertAndSend("input.exchange", "input.key", inputMessage);
    }

    private Authentication getAuthByAccessToken(String accessToken) {
        // 만료된 access token 인지 확인
        if (!jwtProvider.validationToken(accessToken)) {
            throw new CAccessTokenException();
        }

        // AccessToken 에서 Username (pk) 가져오기
        return jwtProvider.getAuthentication(accessToken);
    }
}