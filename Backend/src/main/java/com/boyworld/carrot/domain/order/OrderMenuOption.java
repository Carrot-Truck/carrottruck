package com.boyworld.carrot.domain.order;

import com.boyworld.carrot.domain.TimeBaseEntity;
import com.boyworld.carrot.domain.menu.MenuOption;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문-메뉴-옵션 엔티티
 *
 * @author 박은규
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders_menu_option")
public class OrderMenuOption extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_menu_option_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "menu_option_id", nullable = false)
    private MenuOption menuOption;

    @ManyToOne
    @JoinColumn(name = "orders_menu_id", nullable = false)
    private OrderMenu orderMenu;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Boolean active;
}
