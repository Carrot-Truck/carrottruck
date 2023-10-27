package com.boyworld.carrot.api.service.menu;

import com.boyworld.carrot.api.controller.menu.MenuResponse;
import com.boyworld.carrot.api.controller.menu.response.MenuDetailResponse;
import com.boyworld.carrot.domain.menu.repository.MenuQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 메뉴 조회 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuQueryService {

    private final MenuQueryRepository menuQueryRepository;

    /**
     * 푸드트럭 메뉴 목록 조회
     *
     * @param foodTruckId 루드트럭 식별키
     * @param lastMenuId  마지막으로 조회된 메뉴 식별키
     * @return 해당 푸드트럭의 메뉴 목록
     */
    public MenuResponse getMenus(Long foodTruckId, Long lastMenuId) {
        return null;
    }

    /**
     * 메뉴 상세 조회 API
     *
     * @param menuId 메뉴 식별키
     * @return 메뉴 상세 정보 (옵션 포함)
     */
    public MenuDetailResponse getMenu(Long menuId) {
        return null;
    }
}
