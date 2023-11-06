package com.boyworld.carrot.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 알림용 디바이스 토큰 엔티티
 *
 * @author 최영환
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_device_token")
public class MemberDeviceToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_device_token_id")
    private Long id;

    @Column(nullable = false)
    private String deviceToken;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private MemberDeviceToken(String deviceToken, Member member) {
        this.deviceToken = deviceToken;
        this.member = member;
    }
}
