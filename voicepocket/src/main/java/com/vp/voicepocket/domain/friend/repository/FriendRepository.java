package com.vp.voicepocket.domain.friend.repository;

import com.vp.voicepocket.domain.friend.entity.Friend;
import com.vp.voicepocket.domain.friend.entity.Status;
import com.vp.voicepocket.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    // TODO: status 가 1인 것과 2인 것을 찾는 것을 각각 만드는 게 어떨가 싶음
    // TODO: status 를 enum 으로 사용하는 것이 좋아 보임(현재는 너무 하드 코딩이고, 가독성이 떨어집니다.)
    @Query(value = "select f from Friend f where f.request_from = :request_from and f.request_to = :request_to and f.status =:status")
    Optional<Friend> findByRequest(@Param("request_from") User request_from, @Param("request_to")User request_to, Status status);

    @Query(value = "select f from Friend f where f.request_to = :request_to and f.status = 1")
    List<Friend> findByToUser(@Param("request_to") User request_to);
}
