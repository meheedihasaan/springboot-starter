package com.springboot.starter.services;

import com.springboot.starter.configs.AppProperties;
import com.springboot.starter.constants.AppConstant;
import com.springboot.starter.constants.AppUtils;
import com.springboot.starter.entities.Role;
import com.springboot.starter.entities.Secret;
import com.springboot.starter.entities.User;
import com.springboot.starter.enums.RoleType;
import com.springboot.starter.enums.UserTokenPurpose;
import com.springboot.starter.exceptions.NotFoundException;
import com.springboot.starter.exceptions.ResponseException;
import com.springboot.starter.models.PaginationArgs;
import com.springboot.starter.models.requests.*;
import com.springboot.starter.models.responses.PasswordValidationResponse;
import com.springboot.starter.models.responses.TokenResponse;
import com.springboot.starter.repositories.UserRepository;
import com.springboot.starter.security.JwtTokenProvider;
import com.springboot.starter.specification.AppSpecification;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecretService secretService;

    @Autowired
    private MailService mailService;

    @Autowired
    private AppProperties appProperties;

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByIdWithException(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findByEmailWithException(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(User.class));
    }

    public User findByPasswordResetToken(String passwordResetToken) {
        return userRepository.findByPasswordResetToken(passwordResetToken).orElse(null);
    }

    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }

    public TokenResponse signIn(SignInRequest request) {
        User user = findByEmailWithException(request.getEmail());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        if (jwt == null) {
            throw new ResponseException(HttpStatus.FORBIDDEN, "Unknown error! Please try again later.");
        }

        String refreshToken =
                refreshTokenService.createRefreshToken(user.getId()).getToken();
        return new TokenResponse(jwt, refreshToken, user);
    }

    public void createNewUser(SignUpRequest request) {
        Boolean isExistUser = userRepository.existsUserByEmail(request.getEmail());
        if (isExistUser) {
            throw new ResponseException("User already exists with email " + request.getEmail()
                    + ". Please try again with different email address.");
        }

        Boolean isValidEmail = AppUtils.isValidEmail(request.getEmail());
        if (!isValidEmail) {
            throw new ResponseException(HttpStatus.EXPECTATION_FAILED, "Your email format is not correct.");
        }

        PasswordValidationResponse passwordValidationResponse =
                AppUtils.getPasswordValidationResponse(request.getPassword());
        if (!passwordValidationResponse.isValid()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, passwordValidationResponse.getMessage());
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = roleService.findByRoleNameWithException(AppConstant.USER_ROLE);
        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);

        Secret secret = new Secret();
        secret.setUserId(savedUser.getId());
        String token = UUID.randomUUID().toString();
        secret.setUserToken(token);
        secret.setUserTokenPurpose(UserTokenPurpose.EMAIL_VERIFICATION);
        secretService.createSecret(secret);
        mailService.sendMail(
                savedUser.getEmail(),
                appProperties.getName() + " User Verification",
                "Please follow to this link to verify your email for " + appProperties.getName() + "./n /t"
                        + appProperties.getBackendUrl() + AppConstant.VERIFICATION_SUBURL + token);
    }

    public Page<User> getPaginatedUsers(PaginationArgs paginationArgs) {
        Pageable pageable = AppUtils.getPageable(paginationArgs);

        Map<String, Object> specParameters = AppUtils.getSpecParameters(paginationArgs.getParameters());
        if (!specParameters.isEmpty()) {
            Specification<User> userSpecification = AppSpecification.getSpecification(specParameters);
            return userRepository.findAll(userSpecification, pageable);
        }

        return userRepository.findAll(pageable);
    }

    public User getLoggedInUserInfo(UserService userService) {
        User user = AppUtils.getLoggedInUser(userService);
        if (!user.isVerified()) {
            throw new ResponseException("This user is not verified yet.");
        }
        return userService.findById(user.getId());
    }

    public User createAdmin(CreateAdminRequest request) {
        Role role = roleService.findById(request.getRoleId());
        if (role == null) {
            throw new NotFoundException(Role.class);
        }

        if (role.getRoleType() != RoleType.ADMIN) {
            throw new ResponseException("Invalid role type!");
        }

        Boolean isValidEmail = AppUtils.isValidEmail(request.getEmail());
        if (!isValidEmail) {
            throw new ResponseException(HttpStatus.EXPECTATION_FAILED, "Your email format is not correct.");
        }

        PasswordValidationResponse passwordValidationResponse =
                AppUtils.getPasswordValidationResponse(request.getPassword());
        if (!passwordValidationResponse.isValid()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, passwordValidationResponse.getMessage());
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));
        return userRepository.save(user);
    }

    public User updateUserInfoByAdmin(UpdateUserInfoByAdminRequest request) {
        User user = findByIdWithException(request.getUserId());

        for (Role role : user.getRoles()) {
            if (role.getRoleType().equals(RoleType.ADMIN)) {
                throw new ResponseException("You can not update other admin type information.");
            }
        }

        user.setName(request.getName());
        return userRepository.save(user);
    }

    public Boolean changePassword(ChangePasswordRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new ResponseException("User not found!");
        }

        String previousPassword = user.getPassword();
        if (previousPassword == null) {
            throw new ResponseException("Password not available for OAuth2 user.");
        }

        if (!passwordEncoder.matches(request.getPreviousPassword(), previousPassword)) {
            throw new ResponseException("Incorrect old password!");
        }

        PasswordValidationResponse passwordValidationResponse =
                AppUtils.getPasswordValidationResponse(request.getNewPassword());
        if (!passwordValidationResponse.isValid()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, passwordValidationResponse.getMessage());
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return true;
    }
}
