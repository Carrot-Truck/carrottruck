package com.boyworld.carrot.api.controller.menu;

import com.boyworld.carrot.api.ApiResponse;
import com.boyworld.carrot.api.controller.menu.request.CreateMenuOptionRequest;
import com.boyworld.carrot.api.controller.menu.request.CreateMenuRequest;
import com.boyworld.carrot.api.controller.menu.request.EditMenuRequest;
import com.boyworld.carrot.api.controller.menu.response.CreateMenuResponse;
import com.boyworld.carrot.api.controller.menu.response.MenuDetailResponse;
import com.boyworld.carrot.api.controller.menu.response.MenuOptionResponse;
import com.boyworld.carrot.api.controller.menu.response.MenuResponse;
import com.boyworld.carrot.api.service.menu.MenuQueryService;
import com.boyworld.carrot.api.service.menu.MenuService;
import com.boyworld.carrot.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public ApiResponse<CreateMenuResponse> createMenu(@Valid @RequestPart CreateMenuRequest request,
                                                      @RequestPart(required = false) MultipartFile file) throws IOException {
        log.debug("MenuController#createMenu called");
        log.debug("CreateMenuRequest={}", request);
        log.debug("MultipartFile={}", file);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        CreateMenuResponse response = menuService.createMenu(request.toCreateMenuDto(), file, email);
        log.debug("CreateMenuResponse={}", response);

        return ApiResponse.created(response);
    }

    /**
     * 푸드트럭 메뉴 목록 조회
     *
     * @param foodTruckId 루드트럭 식별키
     * @return 해당 푸드트럭의 메뉴 목록
     */
    @GetMapping
    public ApiResponse<MenuResponse> getMenus(@RequestParam Long foodTruckId) {
        log.debug("MenuController#getMenus called");
        log.debug("foodTruckId={}", foodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        MenuResponse response = menuQueryService.getMenus(foodTruckId);
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
    public ApiResponse<MenuDetailResponse> getMenu(@PathVariable Long menuId, @RequestParam Long foodTruckId) {
        log.debug("MenuController#getMenu called");
        log.debug("menuId={}", menuId);
        log.debug("foodTruckId={}", foodTruckId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        MenuDetailResponse response = menuQueryService.getMenu(foodTruckId);
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
                                      @Valid @RequestPart EditMenuRequest request,
                                      @RequestPart MultipartFile file) throws IOException {
        log.debug("MenuController#editMenu called");
        log.debug("menuId={}", menuId);
        log.debug("EditMenuRequest={}", request);
        log.debug("file={}", file);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long editId = menuService.editMenu(request.toEditMenuDto(menuId), file, email);
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
        log.debug("MenuController#deleteMenu called");
        log.debug("menuId={}", menuId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long deleteId = menuService.deleteMenu(menuId, email);
        log.debug("deleteId={}", deleteId);

        return ApiResponse.found(deleteId);
    }

    /**
     * 메뉴 옵션 등록 API
     *
     * @param request 메뉴 옵션 정보
     * @return 등록된 메뉴 옵션 정보
     */
    @PostMapping("/{menuId}/option")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MenuOptionResponse> createMenuOption(@PathVariable Long menuId,
                                                            @Valid @RequestBody CreateMenuOptionRequest request) {
        log.debug("MenuController#createMenuOption called");
        log.debug("CreateMenuOptionRequest={}", request);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        MenuOptionResponse response = menuService.createMenuOption(request.toCreateMenuOptionDto(), email, menuId);
        log.debug("MenuOptionResponse={}", response);

        return ApiResponse.created(response);
    }

    /**
     * 메뉴 옵션 삭제 API
     *
     * @param menuOptionId 삭제할 메뉴 옵션 식별키
     * @return 삭제된 메뉴 옵션 식별키
     */
    @DeleteMapping("/option/{menuOptionId}")
    @ResponseStatus(HttpStatus.FOUND)
    public ApiResponse<Long> deleteMenuOption(@PathVariable Long menuOptionId) {
        log.debug("MenuController#deleteMenuOption called");
        log.debug("optionId={}", menuOptionId);

        String email = SecurityUtil.getCurrentLoginId();
        log.debug("email={}", email);

        Long deleteId = menuService.deleteMenuOption(menuOptionId, email);
        log.debug("deleteId={}", deleteId);

        return ApiResponse.found(deleteId);
    }
}
