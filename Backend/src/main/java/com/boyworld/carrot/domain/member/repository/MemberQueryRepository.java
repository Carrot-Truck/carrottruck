package com.boyworld.carrot.domain.member.repository;

import com.boyworld.carrot.domain.member.Role;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.boyworld.carrot.domain.member.QMember.member;
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

    private BooleanExpression isEqualEmail(String email) {
        return hasText(email) ? member.email.eq(email) : null;
    }

    private BooleanExpression isEqualRole(String role) {
        return hasText(role) ? member.role.eq(Role.valueOf(role)) : null;
    }
}
