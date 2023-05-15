package com.vp.voicepocket.domain.friend.repository;

import com.vp.voicepocket.domain.friend.entity.Friend;
import com.vp.voicepocket.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query(value = "select f from Friend f where f.request_from = :request_from and f.request_to = :request_to and f.status in (1,2)")
    Optional<Friend> findByRequest(@Param("request_from") User request_from, @Param("request_to")User request_to);
}
