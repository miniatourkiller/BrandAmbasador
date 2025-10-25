package com.gym.GoldenGym.services.imp;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.gym.GoldenGym.dtos.AuthResponse;
import com.gym.GoldenGym.dtos.GoogleAuthDto;
import com.gym.GoldenGym.dtos.LoginDto;
import com.gym.GoldenGym.dtos.ResponseDto;
import com.gym.GoldenGym.entities.User;
import com.gym.GoldenGym.entities.enums.Roles;
import com.gym.GoldenGym.repos.UserRepo;
import com.gym.GoldenGym.services.AuthService;
import com.gym.GoldenGym.utils.JWTService;
import com.gym.GoldenGym.utils.ObjectHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServicesImp implements AuthService {
    private final GoogleIdTokenVerifier verifier;
    private final UserRepo userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseDto processIdTokenAndCreateJwt(GoogleAuthDto googleAuthDto) {
        try {
            log.info("Login Attempt");
            GoogleIdToken idToken = verifier.verify(googleAuthDto.getIdToken());
            if (idToken == null) {
                log.error("Invalid token ID");
                return new ResponseDto(400, "Invalid token ID");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String givenName = ObjectHelper.getString(payload.get("given_name"));
            String familyName = ObjectHelper.getString(payload.get("family_name"));
            // fallback to name if separate names missing

            log.info("User found given name: >>>>>>>>> {}", givenName);
            log.info("User found family name: >>>>>>>>> {}", familyName);

            Optional<User> existing = userRepository.findByEmail(email);
            User user = existing.orElseGet(() -> {
                log.info("User is new and is being created as a CLIENT");
                User u = new User();
                u.setEmail(email);
                u.setFullName(familyName != null ? familyName + " " + givenName : givenName);
                u.setRole(Roles.CLIENT); // choose default role
                return userRepository.save(u);
            });

            // 24 hrs in milliseconds = 86400000
            return new ResponseDto(200, "Success", getAuthResponse(user));
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseDto(400, e.getMessage());
        }
    }

    private AuthResponse getAuthResponse(User user) {
        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt, user.getFullName(), user.getRole().name(), 86400000);
    }

    @Override
    public ResponseDto loginNormal(LoginDto loginDto) {
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
        if (user.isPresent()) {
            if (user.get().getPassword() == null) {
                return new ResponseDto(400, "Invalid credentials");
            }
            if (passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
                return new ResponseDto(200, "Success", getAuthResponse(user.get()));
            } else {
                return new ResponseDto(400, "Invalid credentials");
            }
        } else {
            return new ResponseDto(400, "User not found");
        }
    }

}
