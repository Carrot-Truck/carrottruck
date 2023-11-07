package com.boyworld.carrot.domain.address.repository;

import com.boyworld.carrot.api.service.address.dto.AddressInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.boyworld.carrot.domain.address.QDong.dong;
import static com.boyworld.carrot.domain.address.QSido.sido;
import static com.boyworld.carrot.domain.address.QSigungu.sigungu;

/**
 * 주소 조회 레포지토리
 * 
 * @author 양진형
 */
@Repository
@RequiredArgsConstructor
public class AddressQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 시도 조회
     * 
     * @return 시도 ID, 시도 이름
     */
    public List<AddressInfoDto> getSido() {
        return queryFactory
                .select(Projections.constructor(AddressInfoDto.class,
                        sido.id,
                        sido.name
                ))
                .from(sido)
                .fetch();
    }

    /**
     * 시군구 조회
     * 
     * @param sidoId 조회할 시도
     * @return 시군구 ID, 시군구 이름
     */
    public List<AddressInfoDto> getSigungu(Long sidoId) {
        return queryFactory
                .select(Projections.constructor(AddressInfoDto.class,
                        sigungu.id,
                        sigungu.name
                ))
                .from(sigungu)
                .where(sigungu.sido.id.eq(sidoId))
                .fetch();
    }

    /**
     * 읍면동 조회
     * 
     * @param sigunguId 조회할 시군구
     * @return 읍면동 ID, 읍면동 이름
     */
    public List<AddressInfoDto> getDong(Long sigunguId) {
        return queryFactory
                .select(Projections.constructor(AddressInfoDto.class,
                        dong.id,
                        dong.name
                ))
                .from(dong)
                .where(dong.sigungu.id.eq(sigunguId))
                .fetch();
    }
}
