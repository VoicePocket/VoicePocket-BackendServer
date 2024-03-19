package com.vp.voicepocket.domain.user.repository;


import com.vp.voicepocket.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @Query("select u from User u where u.id = ?1 and u.deletedAt is null")
    Optional<User> findById(@NonNull Long id);

    @Query("select u from User u where u.email = ?1 and u.deletedAt is null")
    Optional<User> findByEmail(@NonNull String email);
}
