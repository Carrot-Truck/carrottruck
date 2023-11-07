package com.boyworld.carrot.domain.adong;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 행정동 코드 엔티티
 *
 * @author 양진형
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "adong_code")
public class AdongCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adong_code_id")
    private Long id;

    @Column(length = 8, nullable = false)
    private String adong_code;

    @Column(length = 10, nullable = false)
    private String sido;

    @Column(length = 10)
    private String sigungu;

    @Column(length = 20)
    private String dong;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Builder
    public AdongCode(String adongCode, String sido, String sigungu, String dong, LocalDateTime createdDate) {
        this.adong_code = adongCode;
        this.sido = sido;
        this.sigungu = sigungu;
        this.dong = dong;
        this.createdDate = createdDate;
    }
}
