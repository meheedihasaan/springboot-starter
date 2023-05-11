package com.springdata.jpa.services;

import com.springdata.jpa.entities.Privilege;
import com.springdata.jpa.models.requests.CreatePrivilegeRequest;

public interface PrivilegeService {

    public Privilege createPrivilege(CreatePrivilegeRequest request);

    public Privilege findById(Long id);

    public Privilege findByIdWithException(Long id);

    public Privilege findByPrivilegeName(String name);

    public Privilege findByPrivilegeNameWithException(String name);

}
