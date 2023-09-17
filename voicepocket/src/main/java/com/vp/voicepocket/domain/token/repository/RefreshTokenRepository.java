package com.vp.voicepocket.domain.token.repository;


import com.vp.voicepocket.domain.token.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByKey(Long key);

    Optional<RefreshToken> findByToken(String token);
}

