package com.springboot.starter.repositories;

import com.springboot.starter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);

    Boolean existsUserByEmail(String email);

    Optional<User> findByPasswordResetToken(String passwordResetToken);

}
