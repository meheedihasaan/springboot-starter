package com.springboot.starter.security;

import com.springboot.starter.entities.User;
import com.springboot.starter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username or password is wrong."));
        return CustomUserDetails.create(user);
    }
}
