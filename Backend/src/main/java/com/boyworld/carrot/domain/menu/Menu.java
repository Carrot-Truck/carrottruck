package com.boyworld.carrot.domain.menu;

import com.boyworld.carrot.domain.TimeBaseEntity;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 메뉴 엔티티
 *
 * @author 최영환
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Embedded
    private MenuInfo menuInfo;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "food_truck_id")
    private FoodTruck foodTruck;

    @Builder
    private Menu(MenuInfo menuInfo, Boolean active, FoodTruck foodTruck) {
        this.menuInfo = menuInfo;
        this.active = active;
        this.foodTruck = foodTruck;
    }


    // == business logic ==//
    public void editMenu(String menuName, String menuDescription, Integer menuPrice) {
        this.menuInfo = MenuInfo.builder()
                .name(menuName)
                .description(menuDescription)
                .price(menuPrice)
                .soldOut(this.getMenuInfo().getSoldOut())
                .build();
    }

    public void deActivate() {
        this.active = false;
    }
}
