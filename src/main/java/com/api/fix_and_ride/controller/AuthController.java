package com.api.fix_and_ride.controller;

import com.api.fix_and_ride.dto.AuthResponse;
import com.api.fix_and_ride.dto.LoginRequest;
import com.api.fix_and_ride.dto.SignupRequest;
import com.api.fix_and_ride.dto.UserDTO;
import com.api.fix_and_ride.model.User;
import com.api.fix_and_ride.security.JwtService;
import com.api.fix_and_ride.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest req) {
        authService.signup(req);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest req) {
        User user = authService.validateLogin(req);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG: Authenticated user = " + auth);

        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, UserDTO.fromEntity(user) ));
    }

    // simple protected check: get current username from JWT
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        return ResponseEntity.ok(java.util.Map.of("username", auth.getName()));
    }
}
