package com.springdata.jpa.services.implementation;

import com.springdata.jpa.entities.User;
import com.springdata.jpa.exceptions.NotFoundException;
import com.springdata.jpa.repositories.UserRepository;
import com.springdata.jpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public User findByIdWithException(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException(User.class));
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user;
    }

    @Override
    public User findByEmailWithException(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException(User.class));
        return user;
    }

}
