package com.boyworld.carrot.domain.member.repository;

import com.boyworld.carrot.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원 CRUD 레포지토리
 *
 * @author 최영환
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String loginId);
}
