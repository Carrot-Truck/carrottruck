package com.boyworld.carrot.domain.member.repository.query;

import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.api.controller.member.response.VendorResponse;
import com.boyworld.carrot.domain.member.Role;
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
     * @return true: 중복일 경우 / false: 중복이 없을 경우
     */
    public Boolean isExistEmail(String email) {
        Integer result = queryFactory
                .selectOne()
                .from(member)
                .where(
                        isEqualEmail(email)
                )
                .fetchFirst();
        return result != null;
    }

    /**
     * 이메일로 일반 사용자 정보 조회
     *
     * @param email 현재 로그인 중인 회원 이메일
     * @return email 에 해당하는 사용자 정보
     */
    public Optional<ClientResponse> getClientInfoByEmail(String email) {
        return Optional.ofNullable(queryFactory
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
                        isActiveMember()
                )
                .fetchOne());
    }

    /**
     * 이메일로 사업자 정보 조회 쿼리
     *
     * @param email 현재 로그인 중인 회원 이메일
     * @return 이메일에 해당하는 사업자 정보
     */
    public Optional<VendorResponse> getVendorInfoByEmail(String email) {
        return Optional.ofNullable(queryFactory.select(Projections.constructor(VendorResponse.class,
                        vendorInfo.member.name,
                        vendorInfo.member.nickname,
                        vendorInfo.member.email,
                        vendorInfo.member.phoneNumber,
                        vendorInfo.businessNumber,
                        vendorInfo.member.role
                ))
                .from(vendorInfo)
                .join(vendorInfo.member, member)
                .where(
                        isEqualEmail(email),
                        isVendor(),
                        isActiveMember()
                )
                .fetchOne());
    }

    private BooleanExpression isEqualEmail(String email) {
        return hasText(email) ? member.email.eq(email) : null;
    }

    private BooleanExpression isVendor() {
        return member.role.eq(Role.VENDOR);
    }

    private BooleanExpression isActiveMember() {
        return member.active.eq(true);
    }
}
