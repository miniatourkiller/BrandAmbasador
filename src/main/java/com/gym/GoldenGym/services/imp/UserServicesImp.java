package com.gym.GoldenGym.services.imp;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gym.GoldenGym.dtos.ChangePasswordDto;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.dtos.StaffUserDto;
import com.gym.GoldenGym.dtos.UserToStaffChangeDto;
import com.gym.GoldenGym.dtos.ValidatePassword;
import com.gym.GoldenGym.dtos.reqdtos.UserReqDto;
import com.gym.GoldenGym.entities.StoreLocation;
import com.gym.GoldenGym.entities.User;
import com.gym.GoldenGym.entities.enums.Roles;
import com.gym.GoldenGym.repos.CriteriaRepo;
import com.gym.GoldenGym.repos.StoreLocationRepo;
import com.gym.GoldenGym.repos.UserRepo;
import com.gym.GoldenGym.services.UserServices;
import com.gym.GoldenGym.utils.DateUtils;
import com.gym.GoldenGym.utils.Encryptor;
import com.gym.GoldenGym.utils.RandomGenerator;
import com.gym.GoldenGym.utils.Username;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServicesImp implements UserServices {
    private final UserRepo userRepo;
    private final StoreLocationRepo storeLocationRepo;
    private final PasswordEncoder passwordEncoder;
    private final CriteriaRepo criteriaRepo;

    @Override
    public ResponseDto makeUserAdmin(Long userId) {
        User actionUser = userRepo.findByEmail(Username.getUsername()).orElse(null);

        if (actionUser == null) {
            log.info("No context user");
            return new ResponseDto(400, "User not found");
        }

        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            log.info("User not found");
            return new ResponseDto(400, "User not found");
        }
        user.setRole(Roles.ADMIN);
        userRepo.save(user);
        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto adminCreateStaff(StaffUserDto staffUserDto) {
        User actionUser = userRepo.findByEmail(Username.getUsername()).orElse(null);

        if (actionUser == null) {
            log.info("No context user");
            return new ResponseDto(400, "User not found");
        }

        User user = userRepo.findByEmail(staffUserDto.getEmail()).orElse(null);
        if (user != null) {
            log.info("User already exists");
            return new ResponseDto(400, "User already exists");
        }

        String password = RandomGenerator.generateRandomString(8);

        // check store location
        StoreLocation storeLocation = storeLocationRepo.findById(staffUserDto.getStoreId()).orElse(null);
        if (storeLocation == null) {
            log.info("Store Location not found");
            return new ResponseDto(400, "Store Location not found");
        }

        user = new User();
        user.setEmail(staffUserDto.getEmail());
        user.setFullName(staffUserDto.getFullName());
        user.setContact(staffUserDto.getContact());
        user.setIdNumber(Encryptor.encrypt(staffUserDto.getIdNumber()));
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Roles.STAFF);
        user.setStoreLocation(storeLocation);
        userRepo.save(user);

        // mailing service comes here

        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto changeUserToStaff(UserToStaffChangeDto userToStaffChangeDto) {
        User actionUser = userRepo.findByEmail(Username.getUsername()).orElse(null);

        if (actionUser == null) {
            log.info("No context user");
            return new ResponseDto(400, "User not found");
        }

        User user = userRepo.findById(userToStaffChangeDto.getStaffId()).orElse(null);
        if (user == null) {
            log.info("User not found");
            return new ResponseDto(400, "User not found");
        }
        user.setRole(Roles.STAFF);
        userRepo.save(user);
        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto autocompleteUser(String emailQuery) {
        User actionUser = userRepo.findByEmail(Username.getUsername()).orElse(null);

        if (actionUser == null) {
            log.info("No context user");
            return new ResponseDto(400, "User not found");
        }

        Pageable pageable = PageRequest.of(0, 10);

        return new ResponseDto(200, "Success", userRepo.autocompleteUser(emailQuery, pageable));
    }

    @Override
    public ResponseDto changePassword(ChangePasswordDto changePasswordDto) {
        User actionUser = userRepo.findByEmail(Username.getUsername()).orElse(null);

        if (actionUser == null) {
            log.info("No context user");
            return new ResponseDto(400, "User not found");
        }

        // check old password
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), actionUser.getPassword())) {
            log.info("Invalid old password");
            return new ResponseDto(400, "Invalid old password");
        }

        actionUser.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepo.save(actionUser);
        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto forgotPassword(String email) {
        // create otp password
        User user = userRepo.findByEmail(email).orElse(null);
        if (user == null) {
            log.info("User not found");
            return new ResponseDto(400, "User not found");
        }
        String password = RandomGenerator.generateRandomString(8);

        // mailing service comes here

        user.setOtp(passwordEncoder.encode(password));
        userRepo.save(user);
        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto validatePasswordReset(ValidatePassword validatePassword) {
        User user = userRepo.findByEmail(validatePassword.getEmail()).orElse(null);
        if (user == null) {
            log.info("User not found");
            return new ResponseDto(400, "User not found");
        }

        // check if there is otp and if expired
        if (user.getOtp() == null) {
            log.info("No OTP found");
            return new ResponseDto(400, "No OTP requested");
        } else {
            if (!DateUtils.isDateInFuture(user.getOtpExpiry())) {
                log.info("OTP expired");
                return new ResponseDto(400, "OTP expired");
            }
        }

        if (!passwordEncoder.matches(validatePassword.getOtp(), user.getOtp())) {
            log.info("Invalid OTP");
            return new ResponseDto(400, "Invalid OTP");
        }
        user.setPassword(passwordEncoder.encode(validatePassword.getNewPassword()));
        user.setOtp(null);
        userRepo.save(user);
        return new ResponseDto(200, "Success");
    }

    @Override
    public ResponseDto fetchUsers(UserReqDto userReqDto) {
        log.info("Fetch users => User responsible >>>: {}", Username.getUsername());
        return new ResponseDto(200, "Success", criteriaRepo.fetchUsers(userReqDto));
    }

}
