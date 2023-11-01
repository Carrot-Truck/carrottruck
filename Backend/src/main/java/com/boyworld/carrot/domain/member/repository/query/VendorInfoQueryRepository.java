package com.boyworld.carrot.domain.member.repository.query;

import com.boyworld.carrot.api.controller.member.response.VendorInfoResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.boyworld.carrot.domain.member.QMember.member;
import static com.boyworld.carrot.domain.member.QVendorInfo.vendorInfo;
import static org.springframework.util.StringUtils.hasText;

/**
 * 사업자 정보 쿼리 레포지토리
 *
 * @author 최영환
 */
@Repository
@RequiredArgsConstructor
public class VendorInfoQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 이메일로 사업자 정보 조회
     *
     * @param email 이메일
     * @return 이메일에 해당하는 활성화된 사업자 정보
     */
    public Optional<VendorInfoResponse> getVendorInfoByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(VendorInfoResponse.class,
                        vendorInfo.id,
                        vendorInfo.tradeName,
                        vendorInfo.vendorName,
                        vendorInfo.businessNumber,
                        vendorInfo.phoneNumber
                ))
                .from(vendorInfo)
                .join(vendorInfo.member, member)
                .where(
                        isEmailEqual(email),
                        isActiveVendorInfo()
                )
                .fetchOne());
    }

    private BooleanExpression isEmailEqual(String email) {
        return hasText(email) ? vendorInfo.member.email.eq(email) : null;
    }

    private BooleanExpression isActiveVendorInfo() {
        return vendorInfo.active.eq(true);
    }
}
