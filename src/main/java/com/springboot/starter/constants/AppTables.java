package com.springboot.starter.constants;

public final class AppTables {

    private AppTables(){}

    public static final String USER_TABLE = "USERS";

    public static final String ROLE_TABLE = "ROLES";

    public static final String PRIVILEGE_TABLE = "PRIVILEGES";

    public static final String USER_ROLE_TABLE = "USER_ROLE";

    public static final String ROLE_PRIVILEGE_TABLE = "ROLE_PRIVILEGE";

    public static final String STUDENT_TABLE = "STUDENTS";

    public static final class AuditModelTable {

        private AuditModelTable() {}

        public static final String ID = "ID";

        public static final String CREATED_BY = "CREATED_BY";

        public static final String CREATED_DATE = "CREATED_DATE";

        public static final String LAST_MODIFIED_BY = "LAST_MODIFIED_BY";

        public static final String LAST_MODIFIED_DATE = "LAST_MODIFIED_DATE";

    }

    public static final class UserTable {

        private UserTable() {}

        public static final String USER_ID = "USER_ID";

        public static final String EMAIL = "EMAIL";

        public static final String PASSWORD = "PASSWORD";

        public static final String PASSWORD_RESET_TOKEN = "PASSWORD_RESET_TOKEN";

        public static final String NAME = "NAME";

        public static final String VERIFIED = "VERIFIED";

        public static final String BANNED = "BANNED";

    }

    public static final class RoleTable {

        private RoleTable() {}

        public static final String ROLE_ID = "ROLE_ID";

        public static final String ROLE_TYPE = "ROLE_TYPE";

        public static final String ROLE_NAME = "ROLE_NAME";

        public static final String DESCRIPTION = "DESCRIPTION";

        public static final String IMAGE_URL = "IMAGE_URL";

    }

    public static final class PrivilegeTable {

        private PrivilegeTable() {}

        public static final String PRIVILEGE_ID = "PRIVILEGE_ID";

        public static final String PRIVILEGE_NAME = "PRIVILEGE_NAME";

        public static final String DESCRIPTION = "DESCRIPTION";

    }

    public static final class StudentTable {

        private StudentTable() {}

        public static final String FIRST_NAME = "FIRST_NAME";

        public static final String LAST_NAME = "LAST_NAME";

        public static final String EMAIL = "EMAIL";

        public static final String AGE = "AGE";

    }

}
