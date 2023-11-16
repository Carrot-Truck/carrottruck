package com.boyworld.carrot.domain.foodtruck.repository.query;

import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckItem;
import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckOverview;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckClientDetailDto;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckVendorDetailDto;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
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
import static com.boyworld.carrot.domain.foodtruck.QCategory.category;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruck.foodTruck;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruckImage.foodTruckImage;
import static com.boyworld.carrot.domain.foodtruck.QFoodTruckLike.foodTruckLike;
import static com.boyworld.carrot.domain.foodtruck.QSchedule.schedule;
import static com.boyworld.carrot.domain.member.QMember.member;
import static com.boyworld.carrot.domain.member.QVendorInfo.vendorInfo;
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
        Boolean isOpen = queryFactory
                .select(sale.endTime.isNull())
                .from(sale)
                .where(
                        sale.foodTruck.id.eq(foodTruckId),
                        sale.endTime.isNull(),
                        sale.foodTruck.active,
                        sale.active
                )
                .fetchFirst();

        JPQLQuery<String> address = queryFactory
                .select(sale.address)
                .from(sale)
                .where(
                        sale.foodTruck.id.eq(foodTruckId),
                        sale.endTime.isNull(),
                        sale.foodTruck.active,
                        sale.active
                );

        JPQLQuery<Double> distance = queryFactory
                .select(calculateDistance(currentLat, sale.latitude, currentLng, sale.longitude).doubleValue())
                .from(sale)
                .where(
                        sale.foodTruck.id.eq(foodTruckId),
                        sale.endTime.isNull(),
                        sale.foodTruck.active,
                        sale.active
                );

        if (isOpen == null || !isOpen) {
            address = queryFactory
                    .select(schedule.address)
                    .from(schedule)
                    .where(
                            schedule.foodTruck.id.eq(foodTruckId),
                            schedule.dayOfWeek.eq(LocalDateTime.now().getDayOfWeek()),
                            schedule.active,
                            schedule.foodTruck.active
                    );

            distance = queryFactory
                    .select(calculateDistance(currentLat, schedule.latitude, currentLng, schedule.longitude).doubleValue())
                    .from(schedule)
                    .where(
                            schedule.foodTruck.id.eq(foodTruckId),
                            schedule.dayOfWeek.eq(LocalDateTime.now().getDayOfWeek()),
                            schedule.active,
                            schedule.foodTruck.active
                    );
        }

        return queryFactory
                .selectDistinct(Projections.constructor(FoodTruckClientDetailDto.class,
                        foodTruck.id,
                        foodTruck.category.id,
                        foodTruck.name,
                        foodTruck.phoneNumber,
                        foodTruck.content,
                        foodTruck.originInfo,
                        sale.isNotNull().and(sale.endTime.isNull()),
                        isLiked(email),
                        foodTruck.prepareTime,
                        getAvgGrade(foodTruckId),
                        getLikeCount(foodTruckId),
                        getReviewCount(foodTruckId),
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
                .join(foodTruck.category, category)
                .leftJoin(vendorInfo).on(vendorInfo.member.eq(member), vendorInfo.active)
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
     * @return 푸드트럭 식별키에 해당하는 푸드트럭 상세 정보 (메뉴, 리뷰 포함)
     */
    public FoodTruckVendorDetailDto getFoodTruckByIdAsVendor(Long foodTruckId) {
        return queryFactory
                .select(Projections.constructor(FoodTruckVendorDetailDto.class,
                        foodTruck.id,
                        foodTruck.name,
                        foodTruck.phoneNumber,
                        foodTruck.category.id,
                        foodTruck.content,
                        foodTruck.originInfo,
                        foodTruck.prepareTime,
                        foodTruck.waitLimits,
                        getAvgGrade(foodTruckId),
                        getReviewCount(foodTruckId),
                        foodTruckImage.uploadFile.storeFileName,
                        foodTruck.selected,
                        vendorInfo.vendorName,
                        vendorInfo.tradeName,
                        vendorInfo.businessNumber
                ))
                .from(foodTruck)
                .join(foodTruck.vendor, member)
                .join(foodTruck.category, category)
                .leftJoin(vendorInfo).on(vendorInfo.member.eq(member), vendorInfo.active)
                .leftJoin(review).on(review.foodTruck.eq(foodTruck), review.active)
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

    /**
     * 사업자별 선택된 푸드트럭 조회 쿼리
     *
     * @param email 현재 로그인한 사용자 이메일
     * @return selected 가 true 인 푸드트럭 엔티티
     */
    public FoodTruck getSelectedFoodTruckByEmail(String email) {
        return queryFactory
                .selectFrom(foodTruck)
                .join(foodTruck.vendor, member)
                .where(
                        isEqualEmail(email),
                        foodTruck.selected,
                        foodTruck.active
                )
                .fetchOne();
    }

    /**
     * 푸드트럭 식별키에 해당하는 푸드트럭의 영업 여부 조회 쿼리
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 해당 푸드트럭의 영업 여부
     */
    public Boolean isOpenFoodTruckById(Long foodTruckId) {
        Long id = queryFactory
                .select(foodTruck.id)
                .from(foodTruck)
                .leftJoin(sale).on(sale.foodTruck.id.eq(foodTruckId), sale.active)
                .where(
                        isEqualFoodTruckId(foodTruckId),
                        sale.isNotNull(),
                        sale.endTime.isNull(),
                        foodTruck.active
                )
                .fetchFirst();

        return id != null;
    }

    /**
     * 찜한 푸드트럭 목록 조회 쿼리
     *
     * @param email 사용자 이메일
     * @return 이메일에 해당하는 사용자가 찜한 푸드트럭 목록
     */
    public List<FoodTruckItem> getLikedFoodTrucksByEmail(String email) {

        List<Long> ids = queryFactory
                .select(foodTruckLike.foodTruck.id)
                .from(foodTruckLike)
                .join(foodTruckLike.member, member)
                .where(
                        foodTruckLike.member.email.eq(email),
                        foodTruckLike.active
                )
                .fetch();
        log.debug("ids={}", ids);

        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);

        return queryFactory
                .select(Projections.constructor(FoodTruckItem.class,
                        foodTruck.category.id,
                        foodTruck.id,
                        foodTruck.name,
                        sale.isNotNull().and(sale.endTime.isNull()),
                        isLiked(email),
                        foodTruckLike.id.count().intValue(),
                        review.grade.sum().divide(review.count()).doubleValue(),
                        review.id.count().intValue(),
                        foodTruckImage.uploadFile.storeFileName,
                        isNew(lastMonth)
                ))
                .from(foodTruck)
                .leftJoin(member).on(foodTruck.vendor.eq(member), member.active)
                .leftJoin(review).on(review.foodTruck.eq(foodTruck))
                .leftJoin(sale).on(sale.foodTruck.eq(foodTruck))
                .leftJoin(foodTruckLike).on(foodTruckLike.foodTruck.eq(foodTruck))
                .leftJoin(foodTruckImage).on(foodTruckImage.foodTruck.eq(foodTruck))
                .where(
                        foodTruck.id.in(ids)
                )
                .groupBy(
                        foodTruck.id
                )
                .fetch();

    }

    private BooleanExpression isEqualEmail(String email) {
        return hasText(email) ? foodTruck.vendor.email.eq(email) : null;
    }

    private BooleanExpression isGreaterThanLastId(Long lastFoodTruckId) {
        return lastFoodTruckId != null ? foodTruck.id.gt(lastFoodTruckId) : null;
    }

    private NumberTemplate<Double> calculateDistance(BigDecimal currentLat, NumberPath<BigDecimal> targetLat,
                                                     BigDecimal currentLng, NumberPath<BigDecimal> targetLng) {
        return Expressions.numberTemplate(Double.class,
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

    private JPQLQuery<Integer> getLikeCount(Long foodTruckId) {
        return select(foodTruckLike.count().intValue())
                .from(foodTruckLike)
                .where(
                        foodTruckLike.foodTruck.id.eq(foodTruckId),
                        foodTruckLike.active
                );
    }

    private JPQLQuery<Double> getAvgGrade(Long foodTruckId) {
        return select(review.grade.sum().divide(review.count()).doubleValue())
                .from(review)
                .where(
                        review.foodTruck.id.eq(foodTruckId),
                        review.active
                );
    }

    private JPQLQuery<Integer> getReviewCount(Long foodTruckId) {
        return select(review.count().intValue())
                .from(review)
                .where(
                        review.foodTruck.id.eq(foodTruckId),
                        review.active
                );
    }

    private BooleanExpression isEqualFoodTruckId(Long foodTruckId) {
        return foodTruckId != null ? foodTruck.id.eq(foodTruckId) : null;
    }
}
