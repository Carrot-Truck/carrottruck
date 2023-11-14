package com.boyworld.carrot.domain.menu.repository.query;

import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import com.boyworld.carrot.api.service.sale.dto.SaleMenuItem;
import com.boyworld.carrot.domain.foodtruck.repository.query.FoodTruckQueryRepository;
import com.boyworld.carrot.domain.menu.MenuOption;
import com.boyworld.carrot.domain.menu.repository.command.MenuOptionRepository;
import com.boyworld.carrot.domain.menu.repository.command.MenuRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.boyworld.carrot.domain.menu.QMenu.menu;
import static com.boyworld.carrot.domain.menu.QMenuImage.menuImage;
import static com.boyworld.carrot.domain.menu.QMenuOption.menuOption;

/**
 * 메뉴 조회 레포지토리
 *
 * @author 최영환
 */
@Repository
@RequiredArgsConstructor
public class MenuQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final FoodTruckQueryRepository foodTruckQueryRepository;

    /**
     * 푸드트럭 식별키로 메뉴 목록 조회 쿼리
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 해당 푸드트럭의 메뉴 목록
     */
    public List<MenuDto> getMenusByFoodTruckId(Long foodTruckId) {
        List<Long> ids = queryFactory
                .select(menu.id)
                .from(menu)
                .leftJoin(menuImage).on(menuImage.menu.eq(menu), menuImage.active)
                .where(
                        isEqualFoodTruckId(foodTruckId),
                        menu.active
                )
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(MenuDto.class,
                        menu.id,
                        menu.menuInfo.name,
                        menu.menuInfo.price,
                        menu.menuInfo.description,
                        menu.menuInfo.soldOut,
                        menuImage.uploadFile.storeFileName
                ))
                .from(menu)
                .leftJoin(menuImage).on(menuImage.menu.eq(menu), menuImage.active)
                .where(
                        menu.id.in(ids)
                )
                .fetch();
    }

    /**
     * 메뉴 식별키로 메뉴 상세 조회 쿼리
     *
     * @param menuId 메뉴 식별키
     * @return 메뉴 상세 정보
     */
    public MenuDto getMenuById(Long menuId) {
        return queryFactory
                .select(Projections.constructor(MenuDto.class,
                        menu.id,
                        menu.menuInfo.name,
                        menu.menuInfo.price,
                        menu.menuInfo.description,
                        menu.menuInfo.soldOut,
                        menuImage.uploadFile.storeFileName
                ))
                .from(menu)
                .join(menuImage).on(menuImage.menu.eq(menu))
                .where(
                        isEqualMenuId(menuId),
                        menu.foodTruck.active,
                        menu.active
                )
                .fetchOne();
    }

    @Transactional
    public void setSaleMenuSoldOut(Long foodTruckId, List<SaleMenuItem> saleMenuItems) {
        List<Long> menuIds = queryFactory
            .select(menu.id)
            .from(menu)
            .where(menu.foodTruck.id.eq(foodTruckId))
            .fetch();

        for (Long menuId: menuIds) {
            menuRepository.findById(menuId).ifPresent(menu -> menu.getMenuInfo().soldOut());
            List<Long> menuOptionIds = queryFactory
                .select(menuOption.id)
                .from(menuOption)
                .where(menuOption.menu.id.eq(menuId))
                .fetch();
            for (Long menuOptionId: menuOptionIds) {
                menuOptionRepository.findById(menuOptionId).ifPresent(menuOption -> menuOption.getMenuInfo().soldOut());
            }
        }

        for (SaleMenuItem item: saleMenuItems) {
            Long menuId = item.getMenuId();
            if (menuIds.contains(menuId)) {
                menuRepository.findById(menuId).ifPresent(menu -> menu.getMenuInfo().onSale());
                for (Long optionId: item.getMenuOptionId()) {
                    MenuOption option = menuOptionRepository.findById(optionId).orElse(null);
                    if (option != null && option.getMenu().getId().equals(menuId)) {
                        option.getMenuInfo().onSale();
                    }
                }
            }
        }
    }

    private BooleanExpression isEqualMenuId(Long menuId) {
        return menuId != null ? menu.id.eq(menuId) : null;
    }


    public Boolean isMenuOwner(Long menuId, String email) {
        Long foodTruckId = queryFactory.select(menu.foodTruck.id)
            .from(menu)
            .where(menu.id.eq(menuId))
            .fetchOne();

        if (foodTruckId != null) {
            return foodTruckQueryRepository.isFoodTruckOwner(foodTruckId, email);
        }

        return false;
    }

    private BooleanExpression isEqualFoodTruckId(Long foodTruckId) {
        return foodTruckId != null ? menu.foodTruck.id.eq(foodTruckId) : null;
    }
}
