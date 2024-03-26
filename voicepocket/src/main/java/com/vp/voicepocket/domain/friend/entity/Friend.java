package com.vp.voicepocket.domain.friend.entity;

import com.vp.voicepocket.domain.user.entity.User;
import com.vp.voicepocket.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Friends", uniqueConstraints = {
    @UniqueConstraint(
        columnNames = {"request_from", "request_to"}
    )
})
@Entity
public class Friend extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity=User.class)
    @JoinColumn(name="request_from", nullable = false)
    private User requestFrom;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity=User.class)
    @JoinColumn(name="request_to", nullable = false)
    private User requestTo;

    @Column(name="status",nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public void updateStatus(Status status){
        this.status = status;
    }

}

