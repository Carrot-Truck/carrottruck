package com.boyworld.carrot.domain.foodtruck;

import com.boyworld.carrot.domain.TimeBaseEntity;
import com.boyworld.carrot.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 푸드트럭 엔티티
 *
 * @author 최영환
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodTruck extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_truck_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 13, nullable = false)
    private String phoneNumber;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "text")
    private String originInfo;

    @Column(nullable = false)
    private Integer prepareTime;

    @Column(nullable = false)
    private Boolean selected;

    private int waitLimits;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vendor_id")
    private Member vendor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    private FoodTruck(String name, String phoneNumber, String content, String originInfo, Integer prepareTime,
                      Boolean selected, int waitLimits, Boolean active, Member vendor, Category category) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.originInfo = originInfo;
        this.prepareTime = prepareTime;
        this.selected = selected;
        this.waitLimits = waitLimits;
        this.active = active;
        this.vendor = vendor;
        this.category = category;
    }

    // == business logic ==//
    public void editFoodTruck(String foodTruckName, Category category, String content, String phoneNumber,
                              String originInfo, Integer prepareTime, Integer waitLimits) {
        this.name = foodTruckName;
        this.content = content;
        this.phoneNumber = phoneNumber;
        this.originInfo = originInfo;
        this.prepareTime = prepareTime;
        this.waitLimits = waitLimits;
    }

    public void deActivate() {
        this.active = false;
    }
}
