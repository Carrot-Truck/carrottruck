package com.boyworld.carrot.domain.review;

import com.boyworld.carrot.domain.TimeBaseEntity;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.order.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Review Entity
 *
 * @author Gunhoo Park
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "food_truck_id", nullable = false)
    private FoodTruck foodTruck;

    @ManyToOne
    @JoinColumn(name = "orders_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int grade;

    @Column(nullable = false)
    private Boolean active;

    @Builder
    private Review(Member member, FoodTruck foodTruck, Order order, String content, int grade, Boolean active){
        this.member = member;
        this.foodTruck = foodTruck;
        this.order = order;
        this.content = content;
        this.grade = grade;
        this.active = active;
    }
}
