package com.boyworld.carrot.domain.member.repository;

import com.boyworld.carrot.domain.member.MemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 회원 주소 레포지토리
 *
 * @author 최영환
 */
@Repository
public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long> {
}
