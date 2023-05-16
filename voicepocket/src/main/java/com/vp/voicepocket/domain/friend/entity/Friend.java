package com.vp.voicepocket.domain.friend.entity;

import com.vp.voicepocket.domain.user.entity.User;
import com.vp.voicepocket.global.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Friends")
@Entity
public class Friend extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity=User.class)
    @JoinColumn(name="request_from", nullable = false, referencedColumnName = "userId")
    private User request_from;

    //@Column(name="request_to", nullable = false, columnDefinition="bigint")
    @ManyToOne(fetch = FetchType.LAZY, targetEntity=User.class)
    @JoinColumn(name="request_to", nullable = false, referencedColumnName = "userId")
    private User request_to;

    @Column(name="status",nullable = false)
    private Long status;

    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modifiedDate")
    private LocalDateTime modifiedDate;

    public void updateStatus(Long status){
        this.status = status;
        this.modifiedDate = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
