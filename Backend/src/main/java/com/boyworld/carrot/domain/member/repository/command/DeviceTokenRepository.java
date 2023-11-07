package com.boyworld.carrot.domain.member.repository.command;

import com.boyworld.carrot.domain.member.MemberDeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 디바이스 토큰 레포지토리
 *
 * @author 최영환
 */
@Repository
public interface DeviceTokenRepository extends JpaRepository<MemberDeviceToken, Long> {
}
