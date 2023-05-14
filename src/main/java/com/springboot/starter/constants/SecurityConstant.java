package com.springboot.starter.constants;

import io.jsonwebtoken.SignatureAlgorithm;

import java.util.concurrent.TimeUnit;

public final class SecurityConstant {

    private SecurityConstant() {}

    public static final long JWT_TOKEN_EXPIRATION_TIME = TimeUnit.DAYS.toMillis(3);
    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME =  TimeUnit.DAYS.toMillis(10);

}
