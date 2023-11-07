package com.boyworld.carrot.domain.address;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 시군구 엔티티
 *
 * @author 양진형
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sigungu")
public class Sigungu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sigungu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sido_id")
    private Sido sido;

    @Column
    private String name;

    @Builder
    private Sigungu(Sido sido, String name) {
        this.sido = sido;
        this.name = name;
    }
}
