package com.gym.GoldenGym.utils;

import com.gym.GoldenGym.dtos.ResponseDto;

public class TokenError extends RuntimeException {
    public ResponseDto responseDto;
    public TokenError(String message){
        super(message);
        responseDto = new ResponseDto(300, message);
    }
}
