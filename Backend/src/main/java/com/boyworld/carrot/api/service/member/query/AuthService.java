package com.boyworld.carrot.api.service.member.query;

import com.boyworld.carrot.api.service.member.dto.LoginDto;
import com.boyworld.carrot.api.service.member.error.InValidAccessException;
import com.boyworld.carrot.client.mail.EmailMessage;
import com.boyworld.carrot.client.mail.MailSendClient;
import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.Role;
import com.boyworld.carrot.domain.member.repository.command.MemberRepository;
import com.boyworld.carrot.domain.member.repository.query.MemberQueryRepository;
import com.boyworld.carrot.security.JwtTokenProvider;
import com.boyworld.carrot.security.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/**
 * 인증 서비스
 *
 * @author 최영환
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailSendClient mailSendClient;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 로그인
     *
     * @param dto  로그인 정보
     * @param role 로그인 할 사용자 권한
     * @return 로그인한 회원 정보
     */
    public TokenInfo login(LoginDto dto, String role) {
        log.debug("AuthService#login called");
        log.debug("LoginDto={}", dto);
        log.debug("role={}", role);

        checkValidAccess(dto, role);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateToken(authenticate);
    }

    /**
     * 회원 가입시 이메일 중복 체크 API
     *
     * @param email 중복 체크할 이메일
     * @return 존재하면 true, 존재하지 않으면 false
     */
    public Boolean checkEmail(String email) {
        return memberQueryRepository.isExistEmail(email);
    }

    /**
     * 사업자 로그인 요청 시 일반 사용자의 요청인지 확인
     *
     * @param dto  로그인 할 회원 정보
     * @param role 로그인 할 권한
     */
    private void checkValidAccess(LoginDto dto, String role) {
        if (isVendor(role)) {
            Member member = getMemberByEmail(dto.getEmail());

            checkActiveMember(member);

            checkInvalidAccess(member);
        }
    }

    /**
     * 요청한 서비스가 사업자 서비스인지 판별
     *
     * @param role 요청 서비스 권한
     * @return true: 사업자 서비스인 경우 false: 사업자 서비스가 아닌 경우
     */
    private boolean isVendor(String role) {
        return role.equals(Role.VENDOR.toString());
    }

    /**
     * 이메일로 회원 조회
     *
     * @param email 현재 로그인한 사용자의 이메일
     * @return 회원 엔티티
     */
    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);
    }

    /**
     * 회원 탈퇴 여부 확인
     *
     * @param member 회원 엔티티
     * @throws NoSuchElementException 이미 탈퇴한 회원인 경우
     */
    private void checkActiveMember(Member member) {
        if (!member.getActive()) {
            throw new NoSuchElementException("이미 탈퇴한 회원입니다.");
        }
    }

    /**
     * 사업자 서비스에 접근하는게 일반 사용자인지 판별
     *
     * @param member 회원 엔티티
     * @throws InValidAccessException 일반 사용자가 사업자 서비스에 접근하는 경우
     */
    private void checkInvalidAccess(Member member) {
        if (member.getRole().equals(Role.CLIENT)) {
            throw new InValidAccessException("잘못된 접근입니다.");
        }
    }

    /**
     * 이메일 인증 번호 발송
     *
     * @param emailMessage 인증번호를 받을 이메일 (사용자 이메일)
     */
    public void authEmail(EmailMessage emailMessage) {
        String authNumber = mailSendClient.sendEmail(emailMessage, "email");
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        valueOperations.set(emailMessage.getTo(), authNumber, 3, TimeUnit.MINUTES);
    }

    /**
     * 이메일 인증 번호 확인
     *
     * @param email      인증할 이메일
     * @param authNumber 발급된 인증번호
     * @throws IllegalArgumentException 인증 실패 시
     */
    public void authCheckEmail(String email, String authNumber) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String storeAuthNumber = valueOperations.get(email);

        if (!authNumber.equals(storeAuthNumber)) {
            throw new IllegalArgumentException("인증 실패");
        }

        redisTemplate.delete(email);
    }
}
