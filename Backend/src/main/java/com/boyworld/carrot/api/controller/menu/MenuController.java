package com.boyworld.carrot.api.controller.menu;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.menu.request.CreateMenuRequest;
import com.boyworld.carrot.api.controller.menu.request.EditMenuRequest;
import com.boyworld.carrot.api.controller.menu.response.CreateMenuResponse;
import com.boyworld.carrot.api.controller.menu.response.MenuDetailResponse;
import com.boyworld.carrot.api.service.menu.MenuQueryService;
import com.boyworld.carrot.api.service.menu.MenuService;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 푸드트럭 API 컨트롤러
 *
 * @author 최영환
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;
    private final MenuQueryService menuQueryService;

    /**
     * 푸드트럭 메뉴 등록 API
     *
     * @param request 등록 메뉴 정보
     * @return 등록된 메뉴 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CreateMenuResponse> createMenu(@Valid @RequestBody CreateMenuRequest request) {
        log.debug("MenuController#createMenu called");
        log.debug("CreateMenuRequest={}", request);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        CreateMenuResponse response = menuService.createMenu(request.toCreateMenuDto(), email);
        log.debug("CreateMenuResponse={}", response);

        return ApiResponse.created(response);
    }

    /**
     * 푸드트럭 메뉴 목록 조회
     *
     * @param foodTruckId 루드트럭 식별키
     * @param lastMenuId  마지막으로 조회된 메뉴 식별키
     * @return 해당 푸드트럭의 메뉴 목록
     */
    @GetMapping
    public ApiResponse<MenuResponse> getMenus(@RequestParam Long foodTruckId,
                                              @RequestParam(required = false, defaultValue = "0") Long lastMenuId) {
        log.debug("MenuController#getMenus called");
        log.debug("foodTruckId={}", foodTruckId);
        log.debug("lastMenuId={}", lastMenuId);

        MenuResponse response = menuQueryService.getMenus(foodTruckId, lastMenuId);
        log.debug("MenuResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 메뉴 상세 조회 API
     *
     * @param menuId 메뉴 식별키
     * @return 메뉴 상세 정보 (옵션 포함)
     */
    @GetMapping("/{menuId}")
    public ApiResponse<MenuDetailResponse> getMenu(@PathVariable Long menuId) {
        log.debug("MenuController#getMenu called");
        log.debug("menuId={}", menuId);

        MenuDetailResponse response = menuQueryService.getMenu(menuId);
        log.debug("MenuDetailResponse={}", response);

        return ApiResponse.ok(response);
    }

    /**
     * 메뉴 수정 API
     *
     * @param menuId  메뉴 식별키
     * @param request 수정할 메뉴 정보
     * @param file    수정할 메뉴 이미지
     * @return 수정된 메뉴 식별키
     */
    @PatchMapping("/{menuId}")
    public ApiResponse<Long> editMenu(@PathVariable Long menuId,
                                      @Valid @RequestPart(name = "request") EditMenuRequest request,
                                      @RequestPart(required = false, name = "file") MultipartFile file) {
        log.debug("MenuController#editMenu called");
        log.debug("menuId={}", menuId);
        log.debug("EditMenuRequest={}", request);
        log.debug("file={}", file);

        Long editId = menuService.editMenu(request.toEditMenuDto(menuId), file);
        log.debug("editId={}", editId);

        return ApiResponse.ok(editId);
    }

    /**
     * 메뉴 삭제 API
     *
     * @param menuId 메뉴 식별키
     * @return 삭제된 메뉴 식별키
     */
    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.FOUND)
    public ApiResponse<Long> deleteMenu(@PathVariable Long menuId) {
        log.debug("MenuController#editMenu called");
        log.debug("menuId={}", menuId);

        Long deleteId = menuService.deleteMenu(menuId);
        log.debug("deleteId={}", deleteId);

        return ApiResponse.found(deleteId);
    }
}
