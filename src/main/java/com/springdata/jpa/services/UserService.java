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
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    public User findByIdWithException(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException(User.class));
        return user;
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }

    public User findByEmailWithException(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException(User.class));
        return user;
    }

}
