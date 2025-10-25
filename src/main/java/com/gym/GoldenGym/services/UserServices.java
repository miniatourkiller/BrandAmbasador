package com.gym.GoldenGym.services;

import com.gym.GoldenGym.dtos.ChangePasswordDto;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.StaffUserDto;
import com.gym.GoldenGym.dtos.UserToStaffChangeDto;
import com.gym.GoldenGym.dtos.ValidatePassword;
import com.gym.GoldenGym.dtos.reqdtos.UserReqDto;

public interface UserServices {
    public ResponseDto makeUserAdmin(Long userId);

    public ResponseDto adminCreateStaff(StaffUserDto staffUserDto);

    public ResponseDto changeUserToStaff(UserToStaffChangeDto userToStaffChangeDto);

    public ResponseDto autocompleteUser(String emailQuery);

    public ResponseDto changePassword(ChangePasswordDto changePasswordDto);

    public ResponseDto forgotPassword(String email);

    public ResponseDto validatePasswordReset(ValidatePassword validatePassword);

    public ResponseDto fetchUsers(UserReqDto userReqDto);
}
