package com.boyworld.carrot.api.service.foodtruck;

import com.boyworld.carrot.api.controller.foodtruck.response.FoodTruckLikeResponse;
import com.boyworld.carrot.api.service.foodtruck.dto.CreateFoodTruckDto;
import com.boyworld.carrot.api.service.foodtruck.dto.FoodTruckLikeDto;
import com.boyworld.carrot.api.service.foodtruck.dto.UpdateFoodTruckDto;
import com.boyworld.carrot.api.service.member.error.InValidAccessException;
import com.boyworld.carrot.domain.foodtruck.Category;
import com.boyworld.carrot.domain.foodtruck.FoodTruck;
import com.boyworld.carrot.domain.foodtruck.FoodTruckImage;
import com.boyworld.carrot.domain.foodtruck.FoodTruckLike;
import com.boyworld.carrot.domain.foodtruck.repository.command.CategoryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckImageRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckLikeRepository;
import com.boyworld.carrot.domain.foodtruck.repository.command.FoodTruckRepository;
import com.boyworld.carrot.domain.foodtruck.repository.query.FoodTruckImageQueryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.query.FoodTruckLikeQueryRepository;
import com.boyworld.carrot.domain.foodtruck.repository.query.FoodTruckQueryRepository;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.file.S3Uploader;
import com.boyworld.carrot.file.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * 푸드트럭 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FoodTruckService {

    private final FoodTruckRepository foodTruckRepository;
    private final FoodTruckQueryRepository foodTruckQueryRepository;
    private final FoodTruckImageRepository foodTruckImageRepository;
    private final FoodTruckImageQueryRepository foodTruckImageQueryRepository;
    private final FoodTruckLikeRepository foodTruckLikeRepository;
    private final FoodTruckLikeQueryRepository foodTruckLikeQueryRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;

    /**
     * 푸드트럭 등록
     *
     * @param dto   푸드트럭 정보
     * @param email 로그인 중인 회원 이메일
     * @param file  등록할 푸드트럭 이미지
     * @return 등록된 푸드트럭 식별키
     * @throws IOException 이미지 파일 변환을 할 수 없을 경우
     */
    public Long createFoodTruck(CreateFoodTruckDto dto, String email, MultipartFile file) throws IOException {
        log.debug("CreateFoodTruckDto={}", dto);
        log.debug("email={}", email);

        Member member = getMemberByEmail(email);
        checkValidMemberAccess(member);

        Category category = getCategoryById(dto.getCategoryId());

        Boolean selected = checkSelected(email);

        FoodTruck savedFoodTruck = foodTruckRepository.save(dto.toEntity(member, category, selected));

        createFoodTruckImage(file, savedFoodTruck);

        return savedFoodTruck.getId();
    }

    /**
     * 푸드트럭 수정
     *
     * @param dto   수정할 푸드트럭 정보
     * @param file  수정할 푸드트럭 이미지
     * @param email 로그인 중인 회원 이메일
     * @return 수정된 푸드트럭 식별키
     * @throws IOException 이미지 파일 변환을 할 수 없을 경우
     */
    public Long editFoodTruck(UpdateFoodTruckDto dto, MultipartFile file, String email) throws IOException {
        Member member = getMemberByEmail(email);
        checkValidMemberAccess(member);

        FoodTruck foodTruck = getFoodTruckById(dto.getFoodTruckId());

        checkOwnerAccess(member, foodTruck);

        Category category = getCategoryById(dto.getCategoryId());

        foodTruck.editFoodTruck(dto.getFoodTruckName(), category, dto.getContent(), dto.getPhoneNumber(),
                dto.getOriginInfo(), dto.getPrepareTime(), dto.getWaitLimits());

        deleteFoodTruckImage(dto.getFoodTruckId());
        createFoodTruckImage(file, foodTruck);

        return foodTruck.getId();
    }

    /**
     * 푸드트럭 삭제
     *
     * @param foodTruckId 푸드트럭 식별키
     * @param email       로그인 중인 회원 이메일
     * @return 삭제된 푸드트럭 식별키
     */
    public Long deleteFoodTruck(Long foodTruckId, String email) {
        Member member = getMemberByEmail(email);
        checkValidMemberAccess(member);

        FoodTruck foodTruck = getFoodTruckById(foodTruckId);
        checkOwnerAccess(member, foodTruck);

        foodTruck.deActivate();

        return foodTruck.getId();
    }

    /**
     * 푸드트럭 찜
     *
     * @param dto   푸드트럭 식별키
     * @param email 로그인 중인 회원 이메일
     * @return 푸드트럭 찜 정보
     */
    public FoodTruckLikeResponse foodTruckLike(FoodTruckLikeDto dto, String email) {
        // TODO: 2023-11-07 리팩토링
        Member member = getMemberByEmail(email);
        FoodTruck foodTruck = getFoodTruckById(dto.getFoodTruckId());

        // 활성화 체크
        if (!member.getActive() && !foodTruck.getActive()) {
            throw new InValidAccessException("잘못된 접근입니다.");
        }

        // 존재여부 체크
        FoodTruckLike foodTruckLike = foodTruckLikeQueryRepository
                .getFoodTruckLikeByMemberIdAndFoodTruckId(member.getId(), foodTruck.getId());

        if (foodTruckLike == null) {
            foodTruckLike = foodTruckLikeRepository.save(dto.toEntity(member, foodTruck));
        } else {
            foodTruckLike.toggleActive();
        }

        return FoodTruckLikeResponse.of(foodTruckLike);
    }

    /**
     * 사업자별 선택된 푸드트럭 변경
     *
     * @param foodTruckId 푸드트럭 식별키
     * @param email       현재 로그인한 사용자 이메일
     * @return 선택된 푸드트럭 식별키
     */
    public Long editSelected(Long foodTruckId, String email) {
        Member member = getMemberByEmail(email);
        checkValidMemberAccess(member);

        FoodTruck selectedFoodTruck = foodTruckQueryRepository.getSelectedFoodTruckByEmail(email);
        if (selectedFoodTruck != null) {
            selectedFoodTruck.unSelect();
        }

        FoodTruck foodTruck = getFoodTruckById(foodTruckId);

        checkOwnerAccess(member, foodTruck);
        foodTruck.select();

        return foodTruck.getId();
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
     * 카테고리 식별키로 카테고리 조회
     *
     * @param categoryId 조회할 카테고리 식별키
     * @return 카테고리 엔티티
     * @throws NoSuchElementException 존재하지 않거나 활성화되지 않은 카테고리인 경우
     */
    private Category getCategoryById(Long categoryId) {
        // TODO: 2023-11-01 카테고리 active 여부 포함 조회
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리입니다."));
    }

    /**
     * 선택 푸드트럭 여부 판별
     *
     * @param email 현재 로그인 중인 회원 이메일
     * @return true: 해당 회원의 푸드트럭 중 선택된 것이 없는 경우 / false: 선택된 푸드트럭이 이미 존재하는 경우
     */
    private Boolean checkSelected(String email) {
        Long selectedCount = foodTruckQueryRepository.getSelectedCountByEmail(email);
        return selectedCount == null || selectedCount == 0L;
    }

    /**
     * 푸드트럭 이미지 수정
     *
     * @param foodTruckId 푸드트럭 식별키
     */
    private void deleteFoodTruckImage(Long foodTruckId) {
        FoodTruckImage foodTruckImage = getFoodTruckImageByFoodTruckId(foodTruckId);
        if (foodTruckImage != null) {
            foodTruckImage.deActivate();
        }
    }

    /**
     * 푸드트럭 식별키로 푸드트럭 이미지 조회
     *
     * @param foodTruckId 푸드트럭 식별키
     * @return 푸드트럭 이미지 엔티티
     */
    private FoodTruckImage getFoodTruckImageByFoodTruckId(Long foodTruckId) {
        return foodTruckImageQueryRepository.getFoodTruckImageByFoodTruckId(foodTruckId);
    }

    /**
     * 푸드트럭 이미지 생성
     *
     * @param file           업로드된 파일
     * @param savedFoodTruck 저장된 푸드트럭
     * @throws IOException 파일 변환에 실패했을 경우
     */
    private void createFoodTruckImage(MultipartFile file, FoodTruck savedFoodTruck) throws IOException {
        if (file != null && !file.isEmpty()) {
            String storeFileName = s3Uploader.uploadFiles(file, "food-truck");
            UploadFile uploadFile = createUploadFile(storeFileName, file.getOriginalFilename());

            saveFoodTruckImage(savedFoodTruck, uploadFile);
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
     * 푸드트럭 이미지 저장
     *
     * @param savedFoodTruck 저장된 푸드트럭 엔티티
     * @param uploadFile     업로드할 파일 정보
     */
    private void saveFoodTruckImage(FoodTruck savedFoodTruck, UploadFile uploadFile) {
        FoodTruckImage image = FoodTruckImage.builder()
                .foodTruck(savedFoodTruck)
                .uploadFile(uploadFile)
                .active(true)
                .build();
        foodTruckImageRepository.save(image);
    }
}
