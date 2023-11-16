package com.boyworld.carrot.domain.member;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClientInfo {

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(length = 13, nullable = false)
    private String phoneNumber;
}

