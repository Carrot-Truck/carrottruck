package com.boyworld.carrot.domain.address;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 시도 엔티티
 *
 * @author 양진형
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sido")
public class Sido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sido_id")
    private Long id;

    @Column
    private String name;

    @Builder
    private Sido(String name) {
        this.name = name;
    }
}
