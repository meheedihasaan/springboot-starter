package com.springboot.starter.services;

import com.springboot.starter.constants.SecurityConstant;
import com.springboot.starter.entities.RefreshToken;
import com.springboot.starter.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryData(Instant.now().plusMillis(SecurityConstant.REFRESH_TOKEN_EXPIRATION_TIME));
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if(Instant.now().compareTo(refreshToken.getExpiryData()) > 0) {
            refreshTokenRepository.delete(refreshToken);
        }
        return refreshToken;
    }

    public Boolean deleteByUserId(Long userId) {
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByUserIdAndExpiryDateIsBefore(userId, Instant.now());
        refreshTokenRepository.deleteAll(refreshTokens);
        return true;
    }

    public Boolean deleteRefreshToken(RefreshToken refreshToken) {
        try {
            refreshTokenRepository.delete(refreshToken);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

}
