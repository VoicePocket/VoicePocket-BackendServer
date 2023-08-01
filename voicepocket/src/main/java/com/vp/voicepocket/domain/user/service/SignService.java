package com.vp.voicepocket.domain.user.service;



import com.vp.voicepocket.domain.firebase.entity.FCMUserToken;
import com.vp.voicepocket.domain.firebase.repository.FCMRepository;

import com.vp.voicepocket.domain.token.config.JwtProvider;
import com.vp.voicepocket.domain.token.dto.TokenDto;
import com.vp.voicepocket.domain.token.dto.TokenRequestDto;
import com.vp.voicepocket.domain.token.entity.RefreshToken;
import com.vp.voicepocket.domain.token.exception.CRefreshTokenException;
import com.vp.voicepocket.domain.token.repository.RefreshTokenRepository;
import com.vp.voicepocket.domain.user.dto.request.UserLoginRequestDto;
import com.vp.voicepocket.domain.user.dto.request.UserSignupRequestDto;
import com.vp.voicepocket.domain.user.entity.User;
import com.vp.voicepocket.domain.user.exception.CEmailLoginFailedException;
import com.vp.voicepocket.domain.user.exception.CEmailSignUpFailedException;
import com.vp.voicepocket.domain.user.exception.CUserNotFoundException;
import com.vp.voicepocket.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final FCMRepository fcmRepository;

    @Transactional
    public Long signup(UserSignupRequestDto userSignupRequestDto) {
        if (userRepository.findByEmail(userSignupRequestDto.getEmail()).isPresent()) {
            throw new CEmailSignUpFailedException();
        }
        return userRepository.save(userSignupRequestDto.toEntity(passwordEncoder)).getUserId();
    }

    @Transactional
    public TokenDto login(String fcmToken, UserLoginRequestDto userLoginRequestDto) {
        // 회원이 존재하는지 확인
        User user =
                userRepository
                        .findByEmail(userLoginRequestDto.getEmail())
                        .orElseThrow(CEmailLoginFailedException::new);

        // password 일치 여부 확인
        if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new CEmailLoginFailedException();
        }

        // token 발급
        TokenDto tokenDto = jwtProvider.createTokenDto(user.getUserId(), user.getRoles(), user.getEmail());
        if(fcmRepository.findByUserId(user).isEmpty()){
            fcmRepository.save(FCMUserToken.builder().userId(user).FireBaseToken(fcmToken).build());
        }else{
            fcmRepository.findByUserId(user).orElseThrow().update(fcmToken);
        }

        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 만료된 refresh token 인지 확인
        if (!jwtProvider.validationToken(tokenRequestDto.getRefreshToken())) {
            throw new CRefreshTokenException();
        }

        // AccessToken 에서 Username (pk) 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        // user pk로 유저 검색 / repo 에 저장된 Refresh Token 가져오기
        User user =
                userRepository
                        .findById(Long.parseLong(authentication.getName()))
                        .orElseThrow(CUserNotFoundException::new);
        RefreshToken refreshToken =
                refreshTokenRepository.findByKey(user.getUserId()).orElseThrow(CRefreshTokenException::new);

        // 리프레시 토큰 불일치 에러
        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken()))
            throw new CRefreshTokenException();

        // AccessToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 저장
        TokenDto newCreatedToken = jwtProvider.createTokenDto(user.getUserId(), user.getRoles(), user.getEmail());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newCreatedToken.getRefreshToken());
        refreshTokenRepository.save(updateRefreshToken);

        return newCreatedToken;
    }
}
