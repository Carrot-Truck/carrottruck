package com.boyworld.carrot.domain.member;

import com.boyworld.carrot.domain.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 회원 엔티티
 *
 * @author 최영환
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends TimeBaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String nickname;

    @Column
    private String encryptedPwd;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 13, nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role role;

    @Column(nullable = false)
    private Boolean active;

    @Builder
    private Member(String email, String nickname, String encryptedPwd, String name, String phoneNumber, Role role, Boolean active) {
        this.email = email;
        this.nickname = nickname;
        this.encryptedPwd = encryptedPwd;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.active = active;
    }

    //== security info ==//
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.toString()));
    }

    @Override
    public String getPassword() {
        return this.encryptedPwd;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
