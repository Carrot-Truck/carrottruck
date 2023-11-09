package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckOverview;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckClientDetailDto;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckVendorDetailDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruck.foodTruck;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruckImage.foodTruckImage;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruckLike.foodTruckLike;
import static com.boyworld.carrot.domain.foodtruck.QSchedule.schedule;
import static com.boyworld.carrot.domain.member.QMember.member;
import static com.boyworld.carrot.domain.member.QVendorInfo.vendorInfo;
import static com.boyworld.carrot.domain.menu.QMenu.menu;
import static com.boyworld.carrot.domain.review.QReview.review;
import static com.boyworld.carrot.domain.sale.QSale.sale;
import static com.querydsl.jpa.JPAExpressions.select;
import static org.springframework.util.StringUtils.hasText;

/**
 * 푸드트럭 조회 레포지토리
 *
 * @author 최영환
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class FoodTruckQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 사용자 이메일로 선택된 푸드트럭의 개수 조회 쿼리
     *
     * @param email 사용자 이메일
     * @return 해당 사용자의 선택된 푸드트럭 개수
     */
    public Long getSelectedCountByEmail(String email) {
        return queryFactory
                .select(foodTruck.count())
                .from(foodTruck)
                .join(foodTruck.vendor, member)
                .where(
                        isEqualEmail(email),
                        foodTruck.active,
                        foodTruck.selected
                )
                .fetchOne();

    }

    /**
     * 이메일로 보유 푸드트럭 조회 쿼리
     *
     * @param lastFoodTruckId 마지막으로 조회된 푸드트럭 식별키
     * @param email           사업자 이메일
     * @return 해당 사업자 보유 푸드트럭 목록
     */
    public List<FoodTruckOverview> getFoodTruckOverviewsByEmail(Long lastFoodTruckId, String email) {
        List<Long> ids = queryFactory
                .select(foodTruck.id)
                .from(foodTruck)
                .join(foodTruck.vendor, member)
                .where(
                        isEqualEmail(email),
                        isGreaterThanLastId(lastFoodTruckId),
                        foodTruck.active
                )
                .limit(PAGE_SIZE + 1)
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(FoodTruckOverview.class,
                        foodTruck.id,
                        foodTruck.name,
                        foodTruck.selected
                ))
                .from(foodTruck)
                .where(
                        foodTruck.id.in(ids)
                )
                .fetch();
    }

    /**
     * 푸드트럭 상세 정보 조회 쿼리 (일반 사용자용)
     *
     * @param foodTruckId 푸드트럭 식별키
     * @param email       현재 로그인 중인 사용자 이메일
     * @param currentLat  위도
     * @param currentLng  경도
     * @return 푸드트럭 식별키에 해당하는 푸드트럭 상세 정보 (메뉴, 리뷰 포함)
     */
    public FoodTruckClientDetailDto getFoodTruckByIdAsClient(Long foodTruckId, String email, BigDecimal currentLat, BigDecimal currentLng) {
        NumberExpression<BigDecimal> distance = new CaseBuilder()
                .when(sale.endTime.isNull())
                .then(calculateDistance(currentLat, sale.latitude, currentLng, sale.longitude))
                .otherwise(calculateDistance(currentLat, schedule.latitude, currentLng, schedule.longitude));

        StringExpression address = new CaseBuilder()
                .when(sale.endTime.isNull())
                .then(sale.address)
                .otherwise(schedule.address);

        return queryFactory
                .selectDistinct(Projections.constructor(FoodTruckClientDetailDto.class,
                        foodTruck.id,
                        foodTruck.name,
                        foodTruck.phoneNumber,
                        foodTruck.content,
                        foodTruck.originInfo,
                        sale.isNotNull().and(sale.endTime.isNull()),
                        isLiked(email),
                        foodTruck.prepareTime,
                        getAvgGrade(),
                        getLikeCount(),
                        getReviewCount(),
                        distance,
                        address,
                        foodTruckImage.uploadFile.storeFileName,
                        foodTruck.selected,
                        isNew(LocalDateTime.now().minusMonths(1)),
                        vendorInfo.vendorName,
                        vendorInfo.tradeName,
                        vendorInfo.businessNumber
                ))
                .from(foodTruck)
                .join(foodTruck.vendor, member)
                .leftJoin(vendorInfo).on(vendorInfo.member.eq(member), vendorInfo.active)
                .leftJoin(menu).on(menu.foodTruck.eq(foodTruck), menu.active)
                .leftJoin(foodTruckLike).on(foodTruckLike.foodTruck.eq(foodTruck), foodTruckLike.active)
                .leftJoin(schedule).on(schedule.foodTruck.eq(foodTruck), schedule.active)
                .leftJoin(review).on(review.foodTruck.eq(foodTruck), review.active)
                .leftJoin(sale).on(sale.foodTruck.eq(foodTruck), sale.active)
                .leftJoin(foodTruckImage).on(foodTruckImage.foodTruck.eq(foodTruck), foodTruckImage.active)
                .where(
                        isEqualFoodTruckId(foodTruckId),
                        foodTruck.active
                )
                .fetchOne();
    }

    /**
     * 푸드트럭 식별키로 푸드트럭 상세 조회 쿼리 (사업자용)
     *
     * @param foodTruckId 푸드트럭 식별키
     * @param email       현재 로그인 중인 사용자 이메일
     * @return 푸드트럭 식별키에 해당하는 푸드트럭 상세 정보 (메뉴, 리뷰 포함)
     */
    public FoodTruckVendorDetailDto getFoodTruckByIdAsVendor(Long foodTruckId, String email) {
        StringExpression address = new CaseBuilder()
                .when(sale.endTime.isNull())
                .then(sale.address)
                .otherwise(schedule.address);

        return queryFactory
                .selectDistinct(Projections.constructor(FoodTruckVendorDetailDto.class,
                        foodTruck.id,
                        foodTruck.name,
                        foodTruck.phoneNumber,
                        foodTruck.content,
                        foodTruck.originInfo,
                        sale.isNotNull().and(sale.endTime.isNull()),
                        isLiked(email),
                        foodTruck.prepareTime,
                        getAvgGrade(),
                        getLikeCount(),
                        getReviewCount(),
                        address,
                        foodTruckImage.uploadFile.storeFileName,
                        foodTruck.selected,
                        isNew(LocalDateTime.now().minusMonths(1)),
                        isEqualEmail(email),
                        vendorInfo.vendorName,
                        vendorInfo.tradeName,
                        vendorInfo.businessNumber
                ))
                .from(foodTruck)
                .join(foodTruck.vendor, member)
                .leftJoin(vendorInfo).on(vendorInfo.member.eq(member), vendorInfo.active)
                .leftJoin(menu).on(menu.foodTruck.eq(foodTruck), menu.active)
                .leftJoin(foodTruckLike).on(foodTruckLike.foodTruck.eq(foodTruck), foodTruckLike.active)
                .leftJoin(schedule).on(schedule.foodTruck.eq(foodTruck), schedule.active)
                .leftJoin(review).on(review.foodTruck.eq(foodTruck), review.active)
                .leftJoin(sale).on(sale.foodTruck.eq(foodTruck), sale.active)
                .leftJoin(foodTruckImage).on(foodTruckImage.foodTruck.eq(foodTruck), foodTruckImage.active)
                .where(
                        isEqualFoodTruckId(foodTruckId),
                        foodTruck.active
                )
                .fetchOne();
    }

    public Boolean isFoodTruckOwner(Long foodTruckId, String email) {
        return queryFactory
                .select(foodTruck.id.count().goe(1L))
                .from(foodTruck)
                .join(foodTruck.vendor, member)
                .where(
                        member.email.eq(email),
                        isEqualFoodTruckId(foodTruckId)
                )
                .fetchFirst();
    }

    private BooleanExpression isEqualEmail(String email) {
        return hasText(email) ? foodTruck.vendor.email.eq(email) : null;
    }

    private BooleanExpression isGreaterThanLastId(Long lastFoodTruckId) {
        return lastFoodTruckId != null ? foodTruck.id.gt(lastFoodTruckId) : null;
    }

    private NumberTemplate<BigDecimal> calculateDistance(BigDecimal currentLat, NumberPath<BigDecimal> targetLat,
                                                         BigDecimal currentLng, NumberPath<BigDecimal> targetLng) {
        return Expressions.numberTemplate(BigDecimal.class,
                "ST_DISTANCE_SPHERE(POINT({0}, {1}), POINT({2}, {3}))",
                currentLng, currentLat, targetLng, targetLat);
    }

    private JPQLQuery<Boolean> isLiked(String email) {
        return select(foodTruckLike.count().goe(1L))
                .from(foodTruckLike)
                .where(
                        foodTruckLike.foodTruck.eq(foodTruck),
                        foodTruckLike.member.email.eq(email),
                        foodTruckLike.active,
                        member.active
                );
    }

    private BooleanExpression isNew(LocalDateTime lastMonth) {
        return new CaseBuilder()
                .when(foodTruck.createdDate.after(lastMonth))
                .then(true)
                .otherwise(false);
    }

    private JPQLQuery<Integer> getLikeCount() {
        return select(foodTruckLike.count().intValue())
                .from(foodTruckLike)
                .where(
                        foodTruckLike.foodTruck.eq(foodTruck),
                        foodTruckLike.active
                );
    }

    private JPQLQuery<Double> getAvgGrade() {
        return select(review.grade.sum().divide(review.count()).doubleValue())
                .from(review)
                .where(
                        review.foodTruck.eq(foodTruck),
                        review.active
                );
    }

    private JPQLQuery<Integer> getReviewCount() {
        return select(review.count().intValue())
                .from(review)
                .where(
                        review.foodTruck.eq(foodTruck),
                        review.active
                );
    }

    private BooleanExpression isEqualFoodTruckId(Long foodTruckId) {
        return foodTruckId != null ? foodTruck.id.eq(foodTruckId) : null;
    }
}
