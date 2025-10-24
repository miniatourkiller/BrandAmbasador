package com.gym.GoldenGym.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.GoldenGym.dtos.GoogleAuthDto;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.services.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("brand-manager/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/google")
    public ResponseDto processIdTokenAndCreateJwt(GoogleAuthDto googleAuthDto) {
        return authService.processIdTokenAndCreateJwt(googleAuthDto);
    };
}
