package com.springboot.starter.services;

import com.springboot.starter.exceptions.NotFoundException;
import com.springboot.starter.repositories.UserRepository;
import com.springboot.starter.entities.User;
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
