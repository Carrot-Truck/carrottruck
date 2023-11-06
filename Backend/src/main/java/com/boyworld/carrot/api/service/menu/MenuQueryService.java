package com.boyworld.carrot.api.service.menu;

import com.boyworld.carrot.api.controller.menu.response.MenuDetailResponse;
import com.boyworld.carrot.api.controller.menu.response.MenuOptionResponse;
import com.boyworld.carrot.api.controller.menu.response.MenuResponse;
import com.boyworld.carrot.api.service.menu.dto.MenuDto;
import com.boyworld.carrot.domain.menu.repository.query.MenuOptionQueryRepository;
import com.boyworld.carrot.domain.menu.repository.query.MenuQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final MenuOptionQueryRepository menuOptionQueryRepository;

    /**
     * 푸드트럭 메뉴 목록 조회
     *
     * @param foodTruckId 루드트럭 식별키
     * @return 해당 푸드트럭의 메뉴 목록
     */
    public MenuResponse getMenus(Long foodTruckId) {
        List<MenuDto> menus = menuQueryRepository.getMenusByFoodTruckId(foodTruckId);
        return MenuResponse.of(menus);
    }

    /**
     * 메뉴 상세 조회 API
     *
     * @param menuId 메뉴 식별키
     * @return 메뉴 상세 정보 (옵션 포함)
     */
    public MenuDetailResponse getMenu(Long menuId) {
        MenuDto menu = menuQueryRepository.getMenuById(menuId);
        List<MenuOptionResponse> menuOptions = menuOptionQueryRepository.getMenuOptionsByMenuId(menuId);

        return MenuDetailResponse.of(menu, menuOptions);
    }
}
