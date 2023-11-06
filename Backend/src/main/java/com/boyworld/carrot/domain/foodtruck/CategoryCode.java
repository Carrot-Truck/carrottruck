package com.boyworld.carrot.domain.foodtruck;

import com.boyworld.carrot.domain.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 푸드트럭 카테고리 코드 엔티티
 *
 * @author 양진형
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryCode extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_code_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, length = 6)
    private String code;

    @Column(nullable = false)
    private Boolean active;

    @Builder
    private CategoryCode(Category category, String code, Boolean active) {
        this.category = category;
        this.code = code;
        this.active = active;
    }
}
