package com.vp.voicepocket.domain.fcm.entity;

import com.vp.voicepocket.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FCMUserToken")
@Entity
public class FCMUserToken{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name="userId", nullable = false, referencedColumnName = "userId")
    private User userId;

    @Column(nullable = false)
    private String FireBaseToken;

    public void update(String FireBaseToken){
        this.FireBaseToken = FireBaseToken;
    }
}
