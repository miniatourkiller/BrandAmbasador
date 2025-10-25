package com.gym.GoldenGym.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class Username {
    public static String getUsername(String token) {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
