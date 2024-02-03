package com.vp.voicepocket.domain.friend.entity;

import com.vp.voicepocket.domain.user.entity.User;
import com.vp.voicepocket.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @JoinColumn(name="request_from", nullable = false, referencedColumnName = "userId")
    private User request_from;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity=User.class)
    @JoinColumn(name="request_to", nullable = false, referencedColumnName = "userId")
    private User request_to;

    @Column(name="status",nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public void updateStatus(Status status){
        this.status = status;
    }

}

