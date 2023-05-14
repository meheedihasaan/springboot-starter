package com.springboot.starter.constants;

import java.util.concurrent.TimeUnit;

public final class SecurityConstant {

    private SecurityConstant() {}

    public static final long REFRESH_TOKEN_EXPIRATION_TIME =  TimeUnit.DAYS.toMillis(10);

}
