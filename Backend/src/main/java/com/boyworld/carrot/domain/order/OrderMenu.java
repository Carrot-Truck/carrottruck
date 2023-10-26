package com.boyworld.carrot.domain.order;

import com.boyworld.carrot.domain.TimeBaseEntity;
import com.boyworld.carrot.domain.menu.Menu;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문-메뉴 엔티티
 * 
 * @author 박은규
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders_menu")
public class OrderMenu extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_menu_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Order order;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Boolean active;

    @Builder
    private OrderMenu(Order order, Menu menu, Integer quantity, Boolean active) {
        this.order = order;
        this.menu = menu;
        this.quantity = quantity;
        this.active = active;
    }
}
