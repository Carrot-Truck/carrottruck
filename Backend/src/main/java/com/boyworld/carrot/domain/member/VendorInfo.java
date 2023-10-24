package com.boyworld.carrot.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사업자 정보 엔티티
 *
 * @author 최영환
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VendorInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_info_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String tradeName;

    @Column(length = 20, nullable = false)
    private String vendorName;

    @Column(nullable = false)
    private String businessNumber;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private VendorInfo(String tradeName, String vendorName, String businessNumber, String phoneNumber, Boolean active, Member member) {
        this.tradeName = tradeName;
        this.vendorName = vendorName;
        this.businessNumber = businessNumber;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.member = member;
    }
}
