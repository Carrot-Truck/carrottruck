package com.boyworld.carrot.domain.member.repository.command;

import com.boyworld.carrot.domain.member.VendorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 사업자 정보 레포지토리
 *
 * @author 최영환
 */
@Repository
public interface VendorInfoRepository extends JpaRepository<VendorInfo, Long> {
}
