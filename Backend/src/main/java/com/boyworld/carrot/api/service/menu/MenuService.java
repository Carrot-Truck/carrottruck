package com.boyworld.carrot.api.service.menu;

import com.boyworld.carrot.api.controller.menu.response.CreateMenuResponse;
import com.boyworld.carrot.api.service.menu.dto.CreateMenuDto;
import com.boyworld.carrot.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 메뉴 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;

    /**
     * 메뉴 등록
     *
     * @param dto   메뉴 정보
     * @param email
     * @return 등록된 메뉴 정보
     */
    public CreateMenuResponse createMenu(CreateMenuDto dto, String email) {
        return null;
    }
}
