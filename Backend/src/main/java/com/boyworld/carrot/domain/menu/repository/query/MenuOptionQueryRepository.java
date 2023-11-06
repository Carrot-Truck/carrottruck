package com.boyworld.carrot.domain.menu.repository.query;

import com.boyworld.carrot.api.controller.menu.response.MenuOptionResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.boyworld.carrot.domain.menu.QMenuOption.menuOption;

/**
 * 메뉴 옵션 조회 레포지토리
 *
 * @author 최영환
 */
@Repository
@RequiredArgsConstructor
public class MenuOptionQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 메뉴 식별키로 메뉴 옵션 리스트 조회
     *
     * @param menuId 메뉴 식별키
     * @return 메뉴 식별키에 해당하는 메뉴 옵션 리스트
     */
    public List<MenuOptionResponse> getMenuOptionsByMenuId(Long menuId) {
        List<Long> ids = queryFactory
                .select(menuOption.id)
                .from(menuOption)
                .where(
                        menuOption.menu.id.eq(menuId),
                        menuOption.menu.active,
                        menuOption.active
                )
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(MenuOptionResponse.class,
                        menuOption.id,
                        menuOption.menuInfo.name,
                        menuOption.menuInfo.price,
                        menuOption.menuInfo.description,
                        menuOption.menuInfo.soldOut
                ))
                .from(menuOption)
                .where(
                        menuOption.id.in(ids)
                )
                .fetch();
    }
}
