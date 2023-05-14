package com.springboot.starter.constants;

import io.jsonwebtoken.SignatureAlgorithm;

import java.util.concurrent.TimeUnit;

public final class SecurityConstant {

    private SecurityConstant() {}

    public static final long JWT_TOKEN_EXPIRATION_TIME = TimeUnit.DAYS.toMillis(3);
    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
    public static final String SECRET_KEY = "2034f6e32958647fdff75d265b455ebf2034f6e32958647fdff75d265b455ebf2034f6e32958647fdff75d265b455ebf";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long REFRESH_TOKEN_EXPIRATION_TIME =  TimeUnit.DAYS.toMillis(10);

    public static final String[] JWT_DISABLE_ANTMATCHERS = {
            "/swagger-ui.html",
            "/resetpassword/**",
            "/pass-reset",
            "/api/signin",
            "/api/signup",
            "/api/forgetpassword",
            "/api-documentation.html",
            "/swagger-ui/**",
            "/api-docs/**",
            "/api/verify/**",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/webjars/**",
            "/verify*",
            "/api/oauth2/**",
            "/api/refreshtoken",
            "/api/db-file/id/**",
            "/api/page/tag/**",
            "/api/page/predefine-tags",
    };

}
