package com.gym.GoldenGym.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import com.gym.GoldenGym.entities.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JWTService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public String generateToken(User user){
        JwtClaimsSet claims = JwtClaimsSet.builder()
        .subject(user.getEmail())
        .claim("authoritiess", user.getRole())
        .claim("name", user.getFullName())
        .issuer("Brand Ambasador")
        .expiresAt(Instant.now().plus(24, ChronoUnit.HOURS))
        .issuedAt(Instant.now())
        .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }


    @SuppressWarnings("unchecked")
    public Collection<SimpleGrantedAuthority> getAuthorities(String token)throws TokenError{
        log.info("Getting authorities from token");
        Jwt jwt = decoder.decode(token);
        if(jwt.getExpiresAt().isBefore(Instant.now())){
            throw new TokenError("Token expired");
        }
        List<String> authorities = (List<String>) jwt.getClaims().get("authoritiess");
        Collection<SimpleGrantedAuthority> authoritiesList = authorities.stream().map((one)->{return new SimpleGrantedAuthority(one);}).toList();
        return authoritiesList;
    }

    public String getUsername(String token){
        Jwt jwt = decoder.decode(token);
        String username = jwt.getSubject();
        log.info("Getting username from token: {}", username);
        return username;
    }
}