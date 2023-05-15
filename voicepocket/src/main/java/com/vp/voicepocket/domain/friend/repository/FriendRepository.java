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
    // 조건
    // 1L or 2L 있다 ! -> Exception
    // 3L or X -> 정상
    //@Query("select f from Friend f where f.request_from = :request_from and f.request_to = :request_to and (f.status = 1L or f.status = 2L)")
    //@Query("select f from Friend f where f.request_from = :request_from and f.request_to = :request_to and (f.status = 1 or f.status = 2)")
    @Query("select f from Friend f where f.request_from = :request_from")
    Optional<Friend> findByRequest(@Param("request_from") User request_from);
}
