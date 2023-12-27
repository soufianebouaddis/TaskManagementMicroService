package com.microservices.UserSerivce.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import com.microservices.UserSerivce.service.UserDetailsCustom;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${secret.key}")
    private String secretKey;

    private final JwtEncoder jwtEncoder;
    private final UserDetailsCustom detailService;

    public JwtUtils(JwtEncoder jwtEncoder, UserDetailsCustom detailService) {
        this.detailService = detailService;
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(String userName, String userRoleName) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet
                .builder()
                .subject(userName)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(1, ChronoUnit.MINUTES))
                .claim("scope", userRoleName)
                .build();
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                jwtClaimsSet);
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);
        return jwt.getTokenValue();
    }

    public String generateRefreshToken(String userName) {
        UserDetails userDetails = detailService.loadUserByUsername(userName);

        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet
                .builder()
                .subject(userName)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(10, ChronoUnit.MINUTES))
                .claim("roles", roles)
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS512).build(),
                jwtClaimsSet);

        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);
        return jwt.getTokenValue();
    }

}