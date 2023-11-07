package com.boyworld.carrot.domain.foodtruck;


import com.boyworld.carrot.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 푸드트럭 찜
 *
 * @author 최영환
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodTruckLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_truck_like_id")
    private Long id;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "food_truck_id")
    private FoodTruck foodTruck;

    @Builder
    private FoodTruckLike(Boolean active, Member member, FoodTruck foodTruck) {
        this.active = active;
        this.member = member;
        this.foodTruck = foodTruck;
    }

    // == business logic == //
    public void toggleActive() {
        this.active = !this.active;
    }
}
