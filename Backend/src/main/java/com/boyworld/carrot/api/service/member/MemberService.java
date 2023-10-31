package com.boyworld.carrot.api.service.member;

import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.api.controller.member.response.JoinMemberResponse;
import com.boyworld.carrot.api.controller.member.response.MemberAddressDetailResponse;
import com.boyworld.carrot.api.controller.member.response.VendorResponse;
import com.boyworld.carrot.api.service.member.dto.EditMemberDto;
import com.boyworld.carrot.api.service.member.dto.JoinMemberDto;
import com.boyworld.carrot.api.service.member.error.DuplicateException;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.MemberAddress;
import com.boyworld.carrot.domain.member.repository.MemberAddressRepository;
import com.boyworld.carrot.domain.member.repository.MemberQueryRepository;
import com.boyworld.carrot.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 회원 서비스
 *
 * @author 최영환
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberAddressRepository memberAddressRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     *
     * @param dto 가입할 회원 정보
     * @return 가입한 회원 정보
     */
    public JoinMemberResponse join(JoinMemberDto dto) {
        // 중복체크
        checkDuplicateEmail(dto.getEmail());

        Member saveMember = createMember(dto);

        return JoinMemberResponse.of(saveMember);
    }

    /**
     * 중복 이메일 체크
     *
     * @param email 체크할 이메일
     * @throws DuplicateException 이미 사용중인 이메일일 경우
     */
    private void checkDuplicateEmail(String email) {
        Boolean isExistEmail = memberQueryRepository.isExistEmail(email);
        if (isExistEmail) {
            throw new DuplicateException("이미 사용중인 이메일 입니다.");
        }
    }

    /**
     * 회원 엔티티 생성
     *
     * @param dto 회원 정보
     * @return 등록된 회원 엔티티
     */
    private Member createMember(JoinMemberDto dto) {
        String encryptedPwd = passwordEncoder.encode(dto.getPassword());
        Member member = dto.toEntity(encryptedPwd);
        return memberRepository.save(member);
    }

    /**
     * 일반 사용자 정보 수정
     *
     * @param dto 수정할 회원 정보
     * @return 수정된 회원 정보
     * @throws NoSuchElementException 이메일에 해당하는 회원이 존재하지 않을 경우
     */
    public ClientResponse editClient(EditMemberDto dto) {
        Member findMember = findMemberByEmail(dto.getEmail());

        findMember.editMemberInfo(dto.getName(), dto.getNickname(), dto.getPhoneNumber());

        return ClientResponse.of(findMember);
    }

    /**
     * 사업자 정보 수정
     *
     * @param dto 수정할 회원 정보
     * @return 수정된 회원 정보
     * @throws NoSuchElementException 이메일에 해당하는 사업자가 존재하지 않을 경우
     */
    public VendorResponse editVendor(EditMemberDto dto) {
        Member findMember = findMemberByEmail(dto.getEmail());

        findMember.editMemberInfo(dto.getName(), dto.getNickname(), dto.getPhoneNumber());

        return memberQueryRepository.getVendorInfoByEmail(findMember.getEmail());
    }

    /**
     * 회원탈퇴
     *
     * @param email    탈퇴할 회원 이메일
     * @param password 탈퇴할 회원 비밀번호
     * @return 탈퇴여부
     */
    public Boolean withdrawal(String email, String password) {
        Member findMember = findMemberByEmail(email);

        if (isMatchPassword(password, findMember.getEncryptedPwd())) {
            findMember.deActivate();
            return true;
        }
        return false;
    }

    /**
     * 회원 주소 등록
     *
     * @param address 등록할 주소
     * @param email   현재 로그인 중인 회원 이메일
     * @return 등록된 회원 주소 정보
     */
    public MemberAddressDetailResponse createMemberAddress(String address, String email) {
        Member findMember = findMemberByEmail(email);

        MemberAddress savedMemberAddress = saveMemberAddress(address, findMember);

        return MemberAddressDetailResponse.of(savedMemberAddress);
    }

    /**
     * 회원 주소 저장
     *
     * @param address 등록할 주소
     * @param member  회원 엔티티
     * @return 저장된 회원 엔티티
     */
    private MemberAddress saveMemberAddress(String address, Member member) {
        MemberAddress memberAddress = MemberAddress.builder()
                .address(address)
                .member(member)
                .active(true)
                .build();
        return memberAddressRepository.save(memberAddress);
    }

    /**
     * 회원 주소 수정
     *
     * @param memberAddressId 수정할 주소 식별키
     * @param address         수정할 주소 정보
     * @param email           로그인 중인 회원 이메일
     * @return 수정된 주소 정보
     */
    public MemberAddressDetailResponse editMemberAddress(Long memberAddressId, String address, String email) {
        MemberAddress memberAddress = getMemberAddressById(memberAddressId);

        memberAddress.editAddress(address);

        return MemberAddressDetailResponse.of(memberAddress);
    }

    /**
     * 회원 주소 삭제
     *
     * @param memberAddressId 삭제할 회원 주소
     * @param email           로그인 중인 회원 이메일
     * @return true: 삭제 완료 / false: 삭제 실패
     */
    public Boolean deleteMemberAddress(Long memberAddressId, String email) {
        MemberAddress memberAddress = getMemberAddressById(memberAddressId);

        memberAddress.deActivate();

        return true;
    }

    /**
     * 회원 주소 식별키로 식별키로 회원 주소 조회
     *
     * @param memberAddressId 회원 주소 식별키
     * @return 회원 주소 엔티티
     * @throws 회원 주소 식별키에 해당하는 회원 주소가 존재하지 않을 경우
     */
    private MemberAddress getMemberAddressById(Long memberAddressId) {
        return memberAddressRepository.findById(memberAddressId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원 주소입니다."));
    }

    /**
     * 이메일로 회원 조회
     *
     * @param email 찾을 회원 이메일
     * @return 이메일에 해당하는 회원
     * @throws NoSuchElementException 이메일에 해당하는 회원이 없는 경우
     */
    private Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }

    /**
     * 비밀번호 일치 여부
     *
     * @param password     입력받은 비밀번호
     * @param encryptedPwd 저장된 비밀번호
     * @return 비밀번호 일치 여부
     */
    private boolean isMatchPassword(String password, String encryptedPwd) {
        return passwordEncoder.matches(password, encryptedPwd);
    }
}
