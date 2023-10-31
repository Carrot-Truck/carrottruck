package com.boyworld.carrot.security;

import com.boyworld.carrot.domain.member.Member;
import com.boyworld.carrot.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> findMember = memberRepository.findByEmail(email);

        return findMember
            .map(this::createMemberUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    private UserDetails createMemberUserDetails(Member member) {
        return User.builder()
            .username(member.getUsername())
            .password(member.getPassword())
            .roles(member.getRole().toString())
            .build();
    }
}
