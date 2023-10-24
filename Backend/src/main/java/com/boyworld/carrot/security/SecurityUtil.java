package com.boyworld.carrot.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String getCurrentLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information");
        }
        if(authentication.getName().equals("anonymousUser")){
            throw new SecurityException("로그인이 필요합니다.");
        }

        return authentication.getName();
    }
}
