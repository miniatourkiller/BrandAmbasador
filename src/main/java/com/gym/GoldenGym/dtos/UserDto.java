package com.gym.GoldenGym.dtos;

import com.gym.GoldenGym.entities.enums.Roles;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String firstName;
    private String lastName;
    private String contact;
    private Roles role;
}
