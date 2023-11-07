package com.boyworld.carrot.domain.menu.repository.query;

import com.boyworld.carrot.domain.menu.MenuImage;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.boyworld.carrot.domain.menu.QMenuImage.menuImage;

/**
 * 메뉴 이미지 조회 레포지토리
 *
 * @author 최영환
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class MenuImageQueryRepository {

    private final JPAQueryFactory queryFactory;


    /**
     * 메뉴 식별키로 메뉴 이미지 조회
     *
     * @param menuId 메뉴 식별키
     * @return 메뉴 식별키에 해당하는 메뉴 이미지
     */
    public MenuImage getMenuImageByMenuId(Long menuId) {
        return queryFactory
                .selectFrom(menuImage)
                .where(
                        isEqualMenuId(menuId),
                        menuImage.active
                )
                .fetchOne();
    }

    private BooleanExpression isEqualMenuId(Long menuId) {
        return menuId != null ? menuImage.menu.id.eq(menuId) : null;
    }
}
