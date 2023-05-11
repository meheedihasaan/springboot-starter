package com.springdata.jpa.repositories;

import com.springdata.jpa.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>, JpaSpecificationExecutor<Privilege> {

    Optional<Privilege> findByPrivilegeName(String privilegeName);

    Boolean existsPrivilegeByPrivilegeName(String privilegeName);

}
