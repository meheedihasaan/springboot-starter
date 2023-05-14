package com.springboot.starter.constants;

import io.jsonwebtoken.SignatureAlgorithm;

import java.util.concurrent.TimeUnit;

public final class SecurityConstant {

    private SecurityConstant() {}

    public static final long JWT_TOKEN_EXPIRATION_TIME = TimeUnit.DAYS.toMillis(3);
    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
    public static final String SECRET_KEY = "2034f6e32958647fdff75d265b455ebf2034f6e32958647fdff75d265b455ebf2034f6e32958647fdff75d265b455ebf";
    public static final long REFRESH_TOKEN_EXPIRATION_TIME =  TimeUnit.DAYS.toMillis(10);

}
