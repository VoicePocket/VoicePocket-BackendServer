package com.vp.voicepocket.domain.fcm.repository;

import com.vp.voicepocket.domain.fcm.entity.FCMUserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface FCMRepository extends JpaRepository<FCMUserToken, Long> {

    @Query(value = "select fcm from FCMUserToken fcm where fcm.UserID = :userId")
    FCMUserToken findByUserId(@Param("userId")Long userId);
}