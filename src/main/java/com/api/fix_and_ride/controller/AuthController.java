package com.api.fix_and_ride.controller;

import com.api.fix_and_ride.dto.AuthResponseDTO;
import com.api.fix_and_ride.dto.LoginRequestDTO;
import com.api.fix_and_ride.dto.SignupRequestDTO;
import com.api.fix_and_ride.dto.UserDTO;
import com.api.fix_and_ride.entity.UserEntity;
import com.api.fix_and_ride.security.JwtService;
import com.api.fix_and_ride.security.utils.CookieUtil;
import com.api.fix_and_ride.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDTO req) {
        authService.signup(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @CrossOrigin(origins = "https://localhost:5500", allowCredentials = "true")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO req, HttpServletResponse res) {
        return ResponseEntity.ok(
                authService.login(req.email, req.password, res)
        );
    }

    @CrossOrigin(origins = "https://localhost:5500", allowCredentials = "true")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@CookieValue(name = "refreshtoken", required = true) String refreshToken,
                                                   HttpServletResponse res) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO(
                                    null,
                                    null,
                                    "Refresh token missing"
                            )
                    );
        }
        try {
            return ResponseEntity.ok(
                    authService.refresh(refreshToken, res)
            );
        } catch (Exception e) {
            new CookieUtil().clearRefreshTokenCookie(res);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO(
                                null,
                                null,
                                "Invalid refresh token"
                            )
                    );
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication auth, HttpServletResponse res) {
        authService.logout(auth.getName(), res);
        return ResponseEntity.ok("Logged out");
    }

    // simple protected check: get current username from JWT
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        return ResponseEntity.ok(java.util.Map.of("username", auth.getName()));
    }
}
