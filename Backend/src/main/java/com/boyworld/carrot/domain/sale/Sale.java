package com.boyworld.carrot.domain.sale;

import com.boyworld.carrot.domain.TimeBaseEntity;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 영업 엔티티
 *
 * @author 박은규
 */

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "sales")
public class Sale extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_truck_id", nullable = false)
    private FoodTruck foodTruck;

    @Column
    private String address;

    @Column(precision = 15, scale = 13, nullable = false)
    private BigDecimal latitude;

    @Column(precision = 15, scale = 12, nullable = false)
    private BigDecimal longitude;

    @Column(nullable = false)
    private Integer orderNumber;

    @Column(nullable = false)
    private Integer totalAmount;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private Boolean orderable;

    @Column(nullable = false)
    private Boolean active;

    @Builder
    private Sale(String address, FoodTruck foodTruck, BigDecimal latitude, BigDecimal longitude, Integer orderNumber,
        Integer totalAmount, LocalDateTime startTime, LocalDateTime endTime, Boolean active) {
        this.address = address;
        this.foodTruck = foodTruck;
        this.latitude = latitude;
        this.longitude = longitude;
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
    }

    // == business logic == //

    public Sale editOrderable (Boolean orderable) {
        this.orderable = orderable;
        return this;
    }

    public void editEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void incrementOrderNumber() { this.orderNumber++; }

    public void editTotalAmount(Integer price) { this.totalAmount += price; }

}
