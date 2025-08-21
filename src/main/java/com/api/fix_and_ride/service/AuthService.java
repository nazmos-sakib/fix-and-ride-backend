package com.api.fix_and_ride.service;


import com.api.fix_and_ride.dto.LoginRequest;
import com.api.fix_and_ride.dto.SignupRequest;
import com.api.fix_and_ride.model.User;
import com.api.fix_and_ride.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public void signup(SignupRequest req) {
        if (userRepo.existsByEmail(req.email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        userRepo.save(
                new User(
                        req.firstName+" "+req.lastName,
                        req.address,
                        req.houseNo,
                        req.post,
                        req.city,
                        req.email,
                        encoder.encode(req.password)
                )
        );
    }

    public User validateLogin(LoginRequest req) {
        var user = userRepo.findByEmail(req.email)
                .orElseThrow(() -> new IllegalArgumentException("Bad credentials: Email not found"));
        if (!encoder.matches(req.password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Bad credentials: Password Does not match");
        }
        return user;
    }
}
