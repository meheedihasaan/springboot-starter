package com.springboot.starter.services;

import com.springboot.starter.exceptions.NotFoundException;
import com.springboot.starter.repositories.PrivilegeRepository;
import com.springboot.starter.entities.Privilege;
import com.springboot.starter.models.requests.CreatePrivilegeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public Privilege createPrivilege(CreatePrivilegeRequest request) {
        Privilege privilege = new Privilege();
        privilege.setPrivilegeName(request.getPrivilegeName());
        privilege.setDescription(request.getDescription());
        return privilegeRepository.save(privilege);
    }

    public Privilege findById(Long id) {
        return privilegeRepository.findById(id).orElse(null);
    }

    public Privilege findByIdWithException(Long id) {
        return privilegeRepository.findById(id).orElseThrow(()-> new NotFoundException(Privilege.class));
    }

    public Privilege findByPrivilegeName(String privilegeName) {
        return privilegeRepository.findByPrivilegeName(privilegeName).orElse(null);
    }

    public Privilege findByPrivilegeNameWithException(String privilegeName) {
        return privilegeRepository.findByPrivilegeName(privilegeName).orElseThrow(()-> new NotFoundException(Privilege.class));
    }

    public Boolean existsPrivilegeByPrivilegeName(String privilegeName) {
        return privilegeRepository.existsPrivilegeByPrivilegeName(privilegeName);
    }


}
