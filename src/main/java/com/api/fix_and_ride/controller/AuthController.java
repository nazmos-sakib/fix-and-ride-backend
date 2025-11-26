package com.api.fix_and_ride.controller;

import com.api.fix_and_ride.dto.AuthResponseDTO;
import com.api.fix_and_ride.dto.LoginRequestDTO;
import com.api.fix_and_ride.dto.SignupRequestDTO;
import com.api.fix_and_ride.dto.UserDTO;
import com.api.fix_and_ride.entity.UserEntity;
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
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDTO req) {
        authService.signup(req);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO req) {
        UserEntity userEntity = authService.validateLogin(req);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG: Authenticated user = " + auth);

        String token = jwtService.generateToken(userEntity.getEmail(),"USER");
        return ResponseEntity.ok(new AuthResponseDTO(token, UserDTO.fromEntityToDTO(userEntity) ));
    }

    // simple protected check: get current username from JWT
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        return ResponseEntity.ok(java.util.Map.of("username", auth.getName()));
    }
}
