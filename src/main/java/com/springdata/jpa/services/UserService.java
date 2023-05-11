package com.springdata.jpa.services;

import com.springdata.jpa.entities.User;

public interface UserService {

    public void saveUser(User user);

    public User findById(Long id);

    public User findByIdWithException(Long id);

    public User findByEmail(String email);

    public User findByEmailWithException(String email);

}
