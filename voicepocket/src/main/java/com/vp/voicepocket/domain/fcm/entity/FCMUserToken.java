package com.vp.voicepocket.domain.fcm.entity;

import com.vp.voicepocket.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name="FCMUserToken")
public class FCMUserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name="UserId", nullable = false, referencedColumnName = "userId")
    private Long UserId;

    @Column(nullable = false)
    private String FireBaseToken;

    public void update(String FireBaseToken){
        this.FireBaseToken = FireBaseToken;
    }
}
