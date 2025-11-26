package com.api.fix_and_ride.service;


import com.api.fix_and_ride.entity.RefreshTokenEntity;
import com.api.fix_and_ride.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class RefreshTokenService {

    @Value("${jwt.refresh.expiration-ms}")
    private long refreshExpirationMs;

    private final RefreshTokenRepository repo;

    public RefreshTokenService(RefreshTokenRepository repo) {
        this.repo = repo;
    }

    public RefreshTokenEntity createRefreshToken(String email) {
        repo.deleteByUserEmail(email); // ROTATION

        RefreshTokenEntity rt = new RefreshTokenEntity();
        rt.setUserEmail(email);
        rt.setToken(UUID.randomUUID().toString());
        rt.setExpiryTime(System.currentTimeMillis() + refreshExpirationMs);

        return repo.save(rt);
    }

    public Optional<RefreshTokenEntity> validateRefreshToken(String token) {
        return repo.findByToken(token)
                .filter(t -> t.getExpiryTime() > System.currentTimeMillis());
    }

    public void deleteTokensForUser(String email) {
        repo.deleteByUserEmail(email);
    }
}
