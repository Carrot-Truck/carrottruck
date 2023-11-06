package com.boyworld.carrot.domain.menu.repository;

import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import com.boyworld.carrot.domain.foodtruck.repository.query.FoodTruckQueryRepository;
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

/**
 * 메뉴 조회 레포지토리
 *
 * @author 최영환
 */
@Repository
@RequiredArgsConstructor
public class MenuQueryRepository {

    private final JPAQueryFactory queryFactory;
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

    @Transactional
    public void deactivateMenu(Long menuId) {
        queryFactory
            .update(menu)
            .set(menu.active, false)
            .where(menu.id.eq(menuId))
            .execute();
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
