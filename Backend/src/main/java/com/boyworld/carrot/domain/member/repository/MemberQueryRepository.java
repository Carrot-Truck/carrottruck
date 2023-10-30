package com.boyworld.carrot.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.boyworld.carrot.domain.member.QMember.member;

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
                .where(member.email.eq(email))
                .fetchFirst();
        return result != null;
    }
}
