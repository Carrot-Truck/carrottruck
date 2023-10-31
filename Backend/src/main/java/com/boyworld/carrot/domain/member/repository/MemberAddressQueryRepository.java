package com.boyworld.carrot.domain.member.repository;

import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;
import static com.boyworld.carrot.domain.member.QMember.member;
import static com.boyworld.carrot.domain.member.QMemberAddress.memberAddress;
import static org.springframework.util.StringUtils.hasText;

/**
 * 회원 주소 쿼리 레포지토리
 *
 * @author 최영환
 */
@Repository
@RequiredArgsConstructor
public class MemberAddressQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 이메일에 해당하는 회원의 회원 주소 목록 조회
     *
     * @param email               사용자 이메일
     * @param lastMemberAddressId 마지막으로 조회된 회원 주소
     * @return 회원 주소 목록
     */
    public List<MemberAddressDetailResponse> getMemberAddressesByEmail(String email, Long lastMemberAddressId) {
        List<Long> ids = queryFactory
                .select(memberAddress.id)
                .from(memberAddress)
                .join(memberAddress.member, member)
                .where(
                        isEqualEmail(email),
                        isGreaterThanLastId(lastMemberAddressId),
                        isActiveMemberAddress()
                )
                .limit(PAGE_SIZE + 1)
                .fetch();

        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return queryFactory
                .select(Projections.constructor(MemberAddressDetailResponse.class,
                        memberAddress.id,
                        memberAddress.address
                ))
                .from(memberAddress)
                .where(
                        memberAddress.id.in(ids)
                )
                .fetch();
    }

    private BooleanExpression isEqualEmail(String email) {
        return hasText(email) ? memberAddress.member.email.eq(email) : null;
    }

    private BooleanExpression isGreaterThanLastId(Long lastMemberAddressId) {
        return lastMemberAddressId != null ? memberAddress.id.gt(lastMemberAddressId) : null;
    }

    private BooleanExpression isActiveMemberAddress() {
        return memberAddress.active.eq(true);
    }
}
