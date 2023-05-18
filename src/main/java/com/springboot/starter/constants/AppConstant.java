package com.springboot.starter.constants;

import java.util.HashMap;
import java.util.Map;

public final class AppConstant {

    private AppConstant() {}

    public enum Environment {
        DEVELOPMENT,

        STAGING,

        PRODUCTION
    }

    public static final String PAGE_NUMBER = "pageNumber";

    public static final String PAGE_SIZE = "pageSize";

    public static final String SORT_BY = "sortBy";

    public static final String ASC_OR_DESC_TYPE = "ascOrDescType";

    public static final String PARAMETERS = "parameters";

    public static final String INITIAL_USERNAME = "super_admin@gmail.com";

    public static final String INITIAL_PASSWORD = "12345678";

    public static final String INITIAL_ROLE = "SUPER_ADMIN";

    public static final String USER_ROLE = "USER";

    public static final String CONSUMER_PERMISSION = "USER";

    public static final String CONSUMER_PERMISSION_DESCRIPTION = "User Generalized Permission";

    public static final String VERIFICATION_SUBURL = "/verify?verificationToken=";

    public static String RESET_PASSWORD_SUBURL = "/resetpassword?passResetToken=";

    public static final String FORGET_PASSWORD_SUBJECT = "Spring Boot Starter Password Reset Link";

    public static final String FORGET_PASSWORD_TEXT =
            " To reset your password in Spring Boot Starter please click on the following url \n \t";

    public static final Map<String, String> PERMISSIONS = new HashMap<>() {
        {
            put("GENERAL", "GENERAL CONSUMER");

            put("USER_CREATE", "USER CREATE");
            put("USER_READ", "USER READ");
            put("USER_UPDATE", "USER UPDATE");
            put("USER_DELETE", "USER DELETE");

            put("ROLE_CREATE", "ROLE CREATE");
            put("ROLE_READ", "ROLE DELETE");
            put("ROLE_UPDATE", "ROLE UPDATE");
            put("ROLE_DELETE", "ROLE DELETE");

            put("USER_UPDATE_FROM_ADMIN", "USER UPDATE FROM ADMIN");
        }
    };
}
