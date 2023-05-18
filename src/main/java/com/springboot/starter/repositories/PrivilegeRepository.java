package com.springboot.starter.repositories;

import com.springboot.starter.entities.Privilege;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>, JpaSpecificationExecutor<Privilege> {

    Optional<Privilege> findByPrivilegeName(String privilegeName);

    Boolean existsPrivilegeByPrivilegeName(String privilegeName);
}
