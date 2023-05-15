package com.springboot.starter.services;

import com.springboot.starter.exceptions.NotFoundException;
import com.springboot.starter.exceptions.ResponseException;
import com.springboot.starter.models.requests.SignInRequest;
import com.springboot.starter.models.responses.TokenResponse;
import com.springboot.starter.repositories.UserRepository;
import com.springboot.starter.entities.User;
import com.springboot.starter.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RefreshTokenService refreshTokenService;

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

    public TokenResponse signIn(SignInRequest request) {
        User user = findByEmailWithException(request.getEmail());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        if(jwt == null) {
            throw new ResponseException(HttpStatus.FORBIDDEN, "Unknown error! Please try again later.");
        }

        String refreshToken = refreshTokenService.createRefreshToken(user.getId()).getToken();
        return new TokenResponse(jwt, refreshToken, user);
    }

}
