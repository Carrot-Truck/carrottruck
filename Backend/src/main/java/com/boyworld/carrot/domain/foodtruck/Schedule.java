package com.boyworld.carrot.domain.foodtruck;


import com.boyworld.carrot.domain.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * 푸드트럭 스케쥴 엔티티
 *
 * @author 최영환
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "food_truck_schedule")
public class Schedule extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_truck_schedule_id")
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(precision = 10, scale = 6, nullable = false)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 6, nullable = false)
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "food_truck_id")
    private FoodTruck foodTruck;

    @Builder
    private Schedule(String address, BigDecimal latitude, BigDecimal longitude, String days, LocalDateTime startTime, LocalDateTime endTime, Boolean active, FoodTruck foodTruck) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dayOfWeek = DayOfWeek.valueOf(days);
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.foodTruck = foodTruck;
    }

}
