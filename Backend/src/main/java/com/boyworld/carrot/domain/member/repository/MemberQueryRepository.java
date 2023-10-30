package com.boyworld.carrot.domain.member.repository;

import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.api.controller.member.response.VendorResponse;
import com.boyworld.carrot.domain.member.Role;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.boyworld.carrot.domain.member.QMember.member;
import static com.boyworld.carrot.domain.member.QVendorInfo.vendorInfo;
import static org.springframework.util.StringUtils.hasText;

/**
 * 회원 조회 레포지토리
 *
 * @author 최영환
 */
@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 이메일 중복 검사
     *
     * @param email 조회할 이메일
     * @param role  조회할 권한
     * @return true: 중복일 경우 / false: 중복이 없을 경우
     */
    public Boolean isExistEmail(String email, String role) {
        Integer result = queryFactory
                .selectOne()
                .from(member)
                .where(
                        isEqualEmail(email),
                        isEqualRole(role)
                )
                .fetchFirst();
        return result != null;
    }

    /**
     * email 로 일반 사용자 정보 조회
     *
     * @param email 현재 로그인 중인 회원 이메일
     * @return email 에 해당하는 사용자 정보
     */
    public ClientResponse getClientInfoByEmail(String email) {
        return queryFactory
                .select(Projections.constructor(ClientResponse.class,
                        member.name,
                        member.nickname,
                        member.email,
                        member.phoneNumber,
                        member.role
                ))
                .from(member)
                .where(
                        isEqualEmail(email),
                        isEqualRole(Role.CLIENT.toString())
                )
                .fetchOne();
    }

    /**
     * 이메일로 사업자 정보 조회 쿼리
     *
     * @param email 현재 로그인 중인 회원 이메일
     * @return 이메일에 해당하는 사업자 정보
     */
    public VendorResponse getVendorInfoByEmail(String email) {
        return queryFactory.select(Projections.constructor(VendorResponse.class,
                        member.name,
                        member.nickname,
                        member.email,
                        member.phoneNumber,
                        vendorInfo.businessNumber,
                        member.role
                ))
                .from(member)
                .join(vendorInfo.member, member)
                .where(
                        isEqualEmail(email),
                        isEqualRole(Role.VENDOR.toString())
                )
                .fetchOne();
    }

    private BooleanExpression isEqualEmail(String email) {
        return hasText(email) ? member.email.eq(email) : null;
    }

    private BooleanExpression isEqualRole(String role) {
        return hasText(role) ? member.role.eq(Role.valueOf(role)) : null;
    }
}
