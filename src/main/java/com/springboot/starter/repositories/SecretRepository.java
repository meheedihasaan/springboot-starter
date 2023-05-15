package com.springboot.starter.repositories;

import com.springboot.starter.entities.Secret;
import com.springboot.starter.enums.UserTokenPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SecretRepository extends JpaRepository<Secret, Long> {

    Optional<Secret> findByUserId(Long userId);

    Optional<Secret> findByUserIdAndUserTokenPurpose(Long userId, UserTokenPurpose userTokenPurpose);

    Optional<Secret> findByUserTokenAndUserTokenPurpose(String userToken, UserTokenPurpose userTokenPurpose);

    @Transactional
    void deleteAllByUserId(Long userId);

}
