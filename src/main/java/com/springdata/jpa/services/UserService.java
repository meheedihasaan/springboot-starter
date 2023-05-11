package com.springdata.jpa.services;

import com.springdata.jpa.entities.User;
import com.springdata.jpa.exceptions.NotFoundException;
import com.springdata.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByIdWithException(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException(User.class));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findByEmailWithException(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException(User.class));
    }

}
