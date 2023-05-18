package com.springboot.starter.repositories;

import com.springboot.starter.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);

    Boolean existsUserByEmail(String email);

    Optional<User> findByPasswordResetToken(String passwordResetToken);
}
