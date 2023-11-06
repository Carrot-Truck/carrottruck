package com.boyworld.carrot.api.service.menu;

import com.boyworld.carrot.api.controller.menu.response.CreateMenuResponse;
import com.boyworld.carrot.api.controller.menu.response.MenuOptionResponse;
import com.boyworld.carrot.api.service.member.error.InValidAccessException;
import com.boyworld.carrot.api.service.menu.dto.CreateMenuDto;
import com.boyworld.carrot.api.service.menu.dto.CreateMenuOptionDto;
import com.boyworld.carrot.api.service.menu.dto.EditMenuDto;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.menu.Menu;
import com.boyworld.carrot.domain.menu.MenuImage;
import com.boyworld.carrot.domain.menu.MenuOption;
import com.boyworld.carrot.domain.menu.repository.command.MenuImageRepository;
import com.boyworld.carrot.domain.menu.repository.command.MenuOptionRepository;
import com.boyworld.carrot.domain.menu.repository.command.MenuRepository;
import com.boyworld.carrot.domain.menu.repository.query.MenuImageQueryRepository;
import com.boyworld.carrot.file.S3Uploader;
import com.boyworld.carrot.file.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

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
    private final FoodTruckRepository foodTruckRepository;
    private final MemberRepository memberRepository;
    private final MenuImageRepository menuImageRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuImageQueryRepository menuImageQueryRepository;
    private final S3Uploader s3Uploader;

    /**
     * 메뉴 등록
     *
     * @param dto   메뉴 정보
     * @param file  이미지 파일
     * @param email 현재 로그인 중인 회원 이메일
     * @return 등록된 메뉴 정보
     */
    public CreateMenuResponse createMenu(CreateMenuDto dto, MultipartFile file, String email) throws IOException {
        Member member = getMemberByEmail(email);
        checkValidMemberAccess(member);

        FoodTruck foodTruck = getFoodTruckById(dto.getFoodTruckId());
        checkOwnerAccess(member, foodTruck);

        Menu menu = menuRepository.save(dto.toEntity(foodTruck));

        createMenuImage(file, menu);

        List<MenuOption> menuOptions = createAllMenuOptions(menu, dto.getMenuOptionDtos());

        return CreateMenuResponse.of(menu, menuOptions.size());
    }

    /**
     * 메뉴 수정
     *
     * @param dto   메뉴 정보
     * @param file  이미지 파일
     * @param email 현재 로그인 중인 회원 이메일
     * @return 수정된 메뉴 식별키
     */
    public Long editMenu(EditMenuDto dto, MultipartFile file, String email) throws IOException {
        Member member = getMemberByEmail(email);
        checkValidMemberAccess(member);

        Menu menu = getMenuByMenuId(dto.getMenuId());

        FoodTruck foodTruck = menu.getFoodTruck();
        checkOwnerAccess(member, foodTruck);

        menu.editMenu(dto.getMenuName(), dto.getMenuDescription(), dto.getMenuPrice());

        deleteMenuImage(dto.getMenuId());
        createMenuImage(file, menu);

        return menu.getId();
    }

    /**
     * 메뉴 삭제
     *
     * @param menuId 메뉴 식별키
     * @param email  현재 로그인 중인 회원 이메일
     * @return 삭제된 메뉴 식별키
     */
    public Long deleteMenu(Long menuId, String email) {
        Member member = getMemberByEmail(email);
        checkValidMemberAccess(member);

        Menu menu = getMenuByMenuId(menuId);

        FoodTruck foodTruck = menu.getFoodTruck();
        checkOwnerAccess(member, foodTruck);

        menu.deActivate();

        return menu.getId();
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
     * @throws InValidAccessException 해당 회원의 권한이 CLIENT 이거나 비활성화 상태인 경우
     */
    private void checkValidMemberAccess(Member member) {
        if (isClient(member.getRole()) || !member.getActive()) {
            throw new InValidAccessException("잘못된 접근입니다.");
        }
    }

    /**
     * 소유주 접근 여부 판별
     *
     * @param member    접근하려는 회원
     * @param foodTruck 접근하려는 푸드트럭
     * @throws InValidAccessException 해당 회원이 해당 푸드트럭의 소유주가 아닌 경우
     */
    private void checkOwnerAccess(Member member, FoodTruck foodTruck) {
        if (!foodTruck.getVendor().getId().equals(member.getId())) {
            throw new InValidAccessException("잘못된 접근입니다.");
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
     * 푸드트럭 식별키로 푸드트럭 조회
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 푸드트럭 엔티티
     * @throws NoSuchElementException 식별키에 해당하는 푸드트럭이 없는 경우
     */
    private FoodTruck getFoodTruckById(Long foodTruckId) {
        return foodTruckRepository.findById(foodTruckId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 푸드트럭입니다."));
    }

    /**
     * 메뉴 식별키로 메뉴 조회
     *
     * @param menuId 메뉴 식별키
     * @return 메뉴 엔티티
     */
    private Menu getMenuByMenuId(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 메뉴 입니다."));
    }

    /**
     * 메뉴 옵션 일괄 생성
     *
     * @param menu 메뉴 엔티티
     * @param dtos 메뉴 옵션 DTO 리스트
     * @return 저장된 메뉴 옵션 리스트
     */
    private List<MenuOption> createAllMenuOptions(Menu menu, List<CreateMenuOptionDto> dtos) {
        List<MenuOption> menuOptions = dtos.stream()
                .map(menuOptionDto -> menuOptionDto.toEntity(menu))
                .toList();
        return menuOptionRepository.saveAll(menuOptions);
    }

    /**
     * 메뉴 이미지 생성
     *
     * @param file 업로드된 파일
     * @param menu 저장된 메뉴
     * @throws IOException 파일 변환에 실패했을 경우
     */
    private void createMenuImage(MultipartFile file, Menu menu) throws IOException {
        if (file != null && !file.isEmpty()) {
            String storeFileName = s3Uploader.uploadFiles(file, "food-truck-menu");
            UploadFile uploadFile = createUploadFile(storeFileName, file.getOriginalFilename());

            saveMenuImage(menu, uploadFile);
        }
    }

    /**
     * 메뉴 식별키에 해당하는 메뉴 이미지를 찾은 뒤, 존재한다면 삭제
     *
     * @param menuId 메뉴 식별키
     */
    private void deleteMenuImage(Long menuId) {
        MenuImage menuImage = menuImageQueryRepository.getMenuImageByMenuId(menuId);
        if (menuImage != null) {
            menuImage.deActivate();
        }
    }

    /**
     * 업로드할 파일 정보 객체 생성
     *
     * @param storeFileName    저장될 파일명
     * @param originalFilename 원본 파일명
     * @return 업로드할 파일 정보 객체
     */
    private UploadFile createUploadFile(String storeFileName, String originalFilename) {
        return UploadFile.builder()
                .uploadFileName(originalFilename)
                .storeFileName(storeFileName).build();
    }

    /**
     * 메뉴 이미지 저장
     *
     * @param menu       저장된 푸드트럭 엔티티
     * @param uploadFile 업로드할 파일 정보
     */
    private void saveMenuImage(Menu menu, UploadFile uploadFile) {
        MenuImage image = MenuImage.builder()
                .menu(menu)
                .uploadFile(uploadFile)
                .active(true)
                .build();
        menuImageRepository.save(image);
    }

    /**
     * 메뉴 옵션 등록
     *
     * @param dto    메뉴 옵션 정보
     * @param email  현재 로그인 중인 회원 이메일
     * @param menuId 메뉴 식별키
     * @return 등록된 메뉴 옵션 정보
     */
    public MenuOptionResponse createMenuOption(CreateMenuOptionDto dto, String email, Long menuId) {
        return null;
    }

    /**
     * 메뉴 옵션 삭제
     *
     * @param menuOptionId 삭제할 메뉴 옵션 식별키
     * @param email        현재 로그인 중인 회원 이메일
     * @return 삭제된 메뉴 옵션 식별키
     */
    public Long deleteMenuOption(Long menuOptionId, String email) {
        return null;
    }
}
