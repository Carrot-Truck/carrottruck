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
import java.time.LocalTime;

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

    @Column(precision = 15, scale = 13, nullable = false)
    private BigDecimal latitude;

    @Column(precision = 15, scale = 12, nullable = false)
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime endTime;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "food_truck_id")
    private FoodTruck foodTruck;

    @Builder
    private Schedule(String address, BigDecimal latitude, BigDecimal longitude, String dayOfWeek,
                     LocalTime startTime, LocalTime endTime, Boolean active, FoodTruck foodTruck) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek);
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
        this.foodTruck = foodTruck;
    }

    // == business logic == //
    public void editSchedule(String address, String dayOfWeek, String startTime, String endTime) {
        this.address = address;
        this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek);
        this.startTime = LocalTime.parse(startTime);
        this.endTime = LocalTime.parse(endTime);
    }

    public void deActivate() {
        this.active = false;
    }
}
