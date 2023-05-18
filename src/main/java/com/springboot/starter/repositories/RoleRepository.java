package com.springboot.starter.repositories;

import com.springboot.starter.entities.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    Optional<Role> findByRoleName(String roleName);

    Boolean existsRoleByRoleName(String roleName);
}
