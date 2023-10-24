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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
    @JoinTable(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinTable(name = "food_truck_id")
    @Column(nullable = false)
    private FoodTruck foodTruck;

    @ManyToOne
    @JoinTable(name = "orders_id")
    @Column(nullable = false)
    private Order order;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int grade;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private Boolean active;

    @Builder
    private Review(Member member, FoodTruck foodTruck, Order order, String content, int grade, LocalDateTime createdDate, LocalDateTime modifiedDate, Boolean active){
        this.member = member;
        this.foodTruck = foodTruck;
        this.order = order;
        this.content = content;
        this.grade = grade;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.active = active;
    }
}
