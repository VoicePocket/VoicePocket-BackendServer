package com.vp.voicepocket.domain.user.service;


import com.vp.voicepocket.domain.user.dto.request.UserRequestDto;
import com.vp.voicepocket.domain.user.dto.response.UserResponseDto;
import com.vp.voicepocket.domain.user.entity.User;
import com.vp.voicepocket.domain.user.entity.vo.Email;
import com.vp.voicepocket.domain.user.exception.CUserNotFoundException;
import com.vp.voicepocket.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    public UserResponseDto findUserByEmail(Email email) {
        User user = userRepository.findByEmail(email.getValue())
            .orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    public List<UserResponseDto> findAllUser() {
        return userRepository.findAll()
            .stream()
            .map(UserResponseDto::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public Long updateUserData(Long id, UserRequestDto userRequestDto) {
        User modifiedUser = userRepository.findById(id).orElseThrow(CUserNotFoundException::new);
        modifiedUser.updateNickName(userRequestDto.getNickName());
        return id;
    }

}
