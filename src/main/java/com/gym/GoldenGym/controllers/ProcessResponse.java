package com.gym.GoldenGym.controllers;

import org.springframework.http.ResponseEntity;

import com.gym.GoldenGym.dtos.ResponseDto;

public class ProcessResponse {
    public static ResponseEntity<ResponseDto> processResponse(ResponseDto responseDto) {
        return responseDto.getStatus() == 200 ? ResponseEntity.ok(responseDto)
                : ResponseEntity.badRequest().body(responseDto);
    }
}
