package com.boyworld.carrot.domain.address;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 읍면동 엔티티
 *
 * @author 양진형
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dong")
public class Dong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dong_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sigungu_id")
    private Sigungu sigungu;

    @Column(length = 10, nullable = false)
    private String name;

    @Builder
    private Dong(Sigungu sigungu, String name) {
        this.sigungu = sigungu;
        this.name = name;
    }
}
