package com.boyworld.carrot.domain.foodtruck;


import com.boyworld.carrot.domain.TimeBaseEntity;
import com.boyworld.carrot.file.UploadFile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 푸드트럭 이미지
 *
 * @author 최영환
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodTruckImage extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_truck_image_id")
    private Long id;

    @Embedded
    private UploadFile uploadFile;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "food_truck_id")
    private FoodTruck foodTruck;

    @Column(nullable = false)
    private Boolean active;

    @Builder
    private FoodTruckImage(UploadFile uploadFile, FoodTruck foodTruck, Boolean active) {
        this.uploadFile = uploadFile;
        this.foodTruck = foodTruck;
        this.active = active;
    }

    // == business logic ==//
    public void deActivate() {
        this.active = false;
    }
}
