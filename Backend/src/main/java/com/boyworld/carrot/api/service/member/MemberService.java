package com.boyworld.carrot.api.service.member;

import com.boyworld.carrot.api.controller.member.response.ClientResponse;
import com.boyworld.carrot.api.controller.member.response.JoinMemberResponse;
import com.boyworld.carrot.api.controller.member.response.VendorResponse;
import com.boyworld.carrot.api.service.member.dto.EditMemberDto;
import com.boyworld.carrot.api.service.member.dto.JoinMemberDto;
import com.boyworld.carrot.api.service.member.error.DuplicateException;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.repository.MemberQueryRepository;
import com.boyworld.carrot.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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
        Member findMember = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));

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
        Member findMember = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));

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
        return true;
    }
}
