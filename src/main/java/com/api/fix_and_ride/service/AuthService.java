package com.api.fix_and_ride.service;


import com.api.fix_and_ride.dto.AuthResponseDTO;
import com.api.fix_and_ride.dto.LoginRequestDTO;
import com.api.fix_and_ride.dto.SignupRequestDTO;
import com.api.fix_and_ride.dto.UserDTO;
import com.api.fix_and_ride.entity.RefreshTokenEntity;
import com.api.fix_and_ride.entity.UserEntity;
import com.api.fix_and_ride.repository.UserRepository;
import com.api.fix_and_ride.security.JwtService;
import com.api.fix_and_ride.security.utils.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    private final CookieUtil cookieUtil;

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public void signup(SignupRequestDTO req) {
        if (userRepo.existsByEmail(req.email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        userRepo.save(
                new UserEntity(
                        req.firstName + " " + req.lastName,
                        req.address,
                        req.houseNo,
                        req.post,
                        req.city,
                        req.email,
                        encoder.encode(req.password)
                )
        );
    }

    public AuthResponseDTO login(String email, String password, HttpServletResponse res) {
        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        // Access token
        String accessToken = jwtService.generateAccessToken(user.getEmail(), "USER");

        // Refresh token
        RefreshTokenEntity rt = refreshTokenService.createRefreshToken(user.getEmail());

        // Set cookie
        new CookieUtil().addRefreshTokenCookie(res, rt.getToken());

        return new AuthResponseDTO(
                accessToken,
                UserDTO.fromEntityToDTO(user),
                null
        );
    }

    public AuthResponseDTO refresh(String refreshToken, HttpServletResponse res) {
        RefreshTokenEntity rt = refreshTokenService.validateRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        UserEntity user = userRepo.findByEmail(rt.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate new tokens
        String accessToken = jwtService.generateAccessToken(user.getEmail(), "USER");
        RefreshTokenEntity newRt = refreshTokenService.createRefreshToken(user.getEmail());

        new CookieUtil().addRefreshTokenCookie(res, newRt.getToken());
        return new AuthResponseDTO(
                accessToken,
                UserDTO.fromEntityToDTO(user),
                null
        );
    }

    public void logout(String email, HttpServletResponse res) {
        refreshTokenService.deleteTokensForUser(email);
        new CookieUtil().clearRefreshTokenCookie(res);
    }
}
