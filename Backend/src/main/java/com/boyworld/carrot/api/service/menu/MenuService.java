package com.boyworld.carrot.api.service.menu;

import com.boyworld.carrot.api.controller.menu.response.CreateMenuResponse;
import com.boyworld.carrot.api.service.menu.dto.CreateMenuDto;
import com.boyworld.carrot.api.service.menu.dto.EditMenuDto;
import com.boyworld.carrot.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
     * @param email 현재 로그인 중인 회원 이메일
     * @return 등록된 메뉴 정보
     */
    public CreateMenuResponse createMenu(CreateMenuDto dto, String email) {
        return null;
    }

    /**
     * 메뉴 수정
     *
     * @param dto   메뉴 정보
     * @param file  메뉴 이미지
     * @param email 현재 로그인 중인 회원 이메일
     * @return 수정된 메뉴 식별키
     */
    public Long editMenu(EditMenuDto dto, MultipartFile file, String email) {
        return null;
    }

    /**
     * 메뉴 삭제
     *
     * @param menuId 메뉴 식별키
     * @param email  현재 로그인 중인 회원 이메일
     * @return 삭제된 메뉴 식별키
     */
    public Long deleteMenu(Long menuId, String email) {
        return null;
    }
}
