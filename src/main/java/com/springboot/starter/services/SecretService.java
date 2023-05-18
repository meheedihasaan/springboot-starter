package com.springboot.starter.services;

import com.springboot.starter.entities.Secret;
import com.springboot.starter.enums.UserTokenPurpose;
import com.springboot.starter.repositories.SecretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecretService {

    @Autowired
    private SecretRepository secretRepository;

    public Secret createSecret(Secret secret) {
        return secretRepository.save(secret);
    }

    public Secret findByUserId(Long userId) {
        return secretRepository.findByUserId(userId).orElse(null);
    }

    public Secret findByUserIdAndUserTokenPurpose(Long userId, UserTokenPurpose userTokenPurpose) {
        return secretRepository
                .findByUserIdAndUserTokenPurpose(userId, userTokenPurpose)
                .orElse(null);
    }

    public Secret findByUserTokenAndUserTokenPurpose(String userToken, UserTokenPurpose userTokenPurpose) {
        return secretRepository
                .findByUserTokenAndUserTokenPurpose(userToken, userTokenPurpose)
                .orElse(null);
    }

    public Boolean deleteSecret(Secret secret) {
        try {
            secretRepository.delete(secret);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void deleteAllByUserId(Long userId) {
        secretRepository.deleteAllByUserId(userId);
    }
}
