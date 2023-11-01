package com.boyworld.carrot.api.service.foodtruck;

import com.boyworld.carrot.api.controller.foodtruck.response.*;
import com.boyworld.carrot.api.service.member.error.InvalidAccessException;
import com.boyworld.carrot.domain.foodtruck.repository.dto.SearchCondition;
import com.boyworld.carrot.domain.foodtruck.repository.query.FoodTruckQueryRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.boyworld.carrot.domain.SizeConstants.PAGE_SIZE;

/**
 * 푸드트럭 조회 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodTruckQueryService {

    private final FoodTruckQueryRepository foodTruckQueryRepository;

    private final MemberRepository memberRepository;

    /**
     * 푸드트럭 지도 검색 API
     *
     * @param condition 검색 조건
     * @param email     현재 로그인 중인 사용자 이메일
     * @return 푸드트럭 지도에 표시될 마커 정보
     */
    public FoodTruckMarkerResponse getFoodTruckMarkers(SearchCondition condition, String email) {
        return null;
    }

    /**
     * 푸드트럭 목록 조회 API
     *
     * @param condition 검색 조건
     * @param email     현재 로그인 중인 사용자 이메일
     * @return 식별키 리스트에 해당하는 푸드트럭 리스트 (거리순 정렬)
     */
    public FoodTruckResponse<List<FoodTruckItem>> getFoodTrucks(SearchCondition condition, String lastFoodTruckId, String email) {
        return null;
    }

    /**
     * 보유 푸드트럭 목록 조회 API
     *
     * @param lastFoodTruckId 마지막으로 조회된 푸드트럭 식별키
     * @param email           현재 로그인 중인 사용자 이메일
     * @return 보유한 푸드트럭 목록
     */
    public FoodTruckResponse<List<FoodTruckOverview>> getFoodTruckOverviews(Long lastFoodTruckId, String email) {
        Member member = getMemberByEmail(email);
        checkValidAccess(member);

        List<FoodTruckOverview> overviews =
                foodTruckQueryRepository.getFoodTruckOverviewsByEmail(lastFoodTruckId, email);

        return FoodTruckResponse.of(checkHasNext(overviews), overviews);
    }

    /**
     * 이메일로 회원 엔티티 조회
     *
     * @param email 현재 로그인한 사용자 이메일
     * @return 이메일에 해당하는 회원 엔티티
     */
    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    /**
     * 접근 유효성 판별
     *
     * @param member 회원 엔티티
     * @throws InvalidAccessException 해당 회원의 권한이 CLIENT 이거나 비활성화 상태인 경우
     */
    private void checkValidAccess(Member member) {
        if (isClient(member.getRole()) || !member.getActive()) {
            throw new InvalidAccessException("잘못된 접근입니다.");
        }
    }

    /**
     * 회원 권한이 CLIENT 인지 여부 판별
     *
     * @param role 회원 권한
     * @return true : CLIENT 인 경우 false: 그 외 경우
     */
    private boolean isClient(Role role) {
        return role.equals(Role.CLIENT);
    }

    /**
     * 다음 페이지 존재여부 확인
     *
     * @param list 객체 리스트
     * @param <T>  응답 객체
     * @return true: 다음 페이지가 존재하는 경우 / false: 다음 페이지가 존재하지 않는 경우
     */
    private <T> boolean checkHasNext(List<T> list) {
        if (list.size() > PAGE_SIZE) {
            list.remove(PAGE_SIZE);
            return true;
        }
        return false;
    }

    /**
     * 푸드트럭 상세 조회 API
     *
     * @param truckId 푸드트럭 식별키
     * @param email   현재 로그인 중인 사용자 이메일
     * @return 푸드트럭 식별키에 해당하는 푸드트럭 상세 정보 (메뉴, 리뷰 포함)
     */
    public FoodTruckDetailResponse getFoodTruck(Long truckId, String email) {
        return null;
    }
}
