package com.gym.GoldenGym.services;

import com.gym.GoldenGym.dtos.GoogleAuthDto;
import com.gym.GoldenGym.dtos.LoginDto;
import com.gym.GoldenGym.dtos.ResponseDto;

public interface AuthService {
    public ResponseDto processIdTokenAndCreateJwt(GoogleAuthDto googleAuthDto);
    public ResponseDto loginNormal(LoginDto loginDto);
}
