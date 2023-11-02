package com.boyworld.carrot.domain.survey;

import com.boyworld.carrot.domain.TimeBaseEntity;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 수요조사 엔티티
 *
 * @author 양진형
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "survey")
public class Survey extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(length = 20, nullable = false)
    private String sido;

    @Column(length = 20, nullable = false)
    private String sigungu;

    @Column(length = 20, nullable = false)
    private String dong;

    @Column(columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private Boolean active;

    @Builder
    private Survey(Category category, Member member, String sido, String sigungu, String dong, String content, Boolean active) {
        this.category = category;
        this.member = member;
        this.sido = sido;
        this.sigungu = sigungu;
        this.dong = dong;
        this.content = content;
        this.active = active;
    }

    public void deActivate() {
        this.active = false;
    }
}
