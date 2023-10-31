package com.boyworld.carrot.domain.member;

import com.boyworld.carrot.domain.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 주소 엔티티
 *
 * @author 최영환
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddress extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_address_id")
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private MemberAddress(String address, Boolean active, Member member) {
        this.address = address;
        this.active = active;
        this.member = member;
    }

    // == business logic ==//
    public void editAddress(String address) {
        this.address = address;
    }

    public void deActivate() {
        this.active = false;
    }
}
