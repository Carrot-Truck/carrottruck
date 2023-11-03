package com.boyworld.carrot.domain.sale.repository.query;

import com.boyworld.carrot.domain.sale.QSale;
import com.boyworld.carrot.domain.sale.Sale;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 영업 조회 레포지토리
 *
 * @author 박은규
 */
@Repository
@RequiredArgsConstructor
public class SaleQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 최신 영업 조회
     *
     * @param
     * @return
     */
    public Optional<Sale> getSaleOrderByCreatedTimeDesc(Long foodTruckId) {

        QSale sale = QSale.sale;

        Sale firstSale = queryFactory
            .selectFrom(sale)
            .orderBy(sale.createdDate.desc())
            .fetchFirst();

        return Optional.ofNullable(firstSale);
    }
}
