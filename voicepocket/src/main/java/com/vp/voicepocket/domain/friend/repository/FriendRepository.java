package com.vp.voicepocket.domain.friend.repository;

import com.vp.voicepocket.domain.friend.entity.Friend;
import com.vp.voicepocket.domain.friend.entity.Status;
import com.vp.voicepocket.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query(value = "select f from Friend f where f.requestFrom = :request_from and f.requestTo = :request_to and f.status =:status")
    Optional<Friend> findByRequest(@Param("request_from") User request_from, @Param("request_to")User request_to, @Param("status")Status status);

    @Query(value = "select f from Friend f where f.requestTo = :request_to and f.status = :status")
    List<Friend> findByToUser(@Param("request_to") User request_to, @Param("status")Status status);

    @Query(value = "select f from Friend f where f.requestFrom = :request_from and f.status = :status")
    List<Friend> findByFromUser(@Param("request_from") User request_from, @Param("status")Status status);

    @Query("select f from Friend f where f.requestFrom.id = ?1 and f.requestTo = ?2 and (f.status = 'ONGOING' or f.status = 'ACCEPT')")
    Optional<Friend> findByRequestUsers(Long requestFromId, User requestToId);
}
