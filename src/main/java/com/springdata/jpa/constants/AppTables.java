package com.springdata.jpa.constants;

public final class AppTables {

    private AppTables(){}

    public static final String STUDENT = "STUDENT";

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

        private static final String USER_ID = "USER_ID";

        private static final String EMAIL = "EMAIL";

        private static final String PASSWORD = "PASSWORD";

        private static final String PASSWORD_REFRESH_TOKEN = "PASSWORD_REFRESH_TOKEN";

        private static final String NAME = "NAME";

        private static final String VERIFIED = "VERIFIED";

        private static final String BANNED = "BANNED";

    }

    public static final class RoleTable {

        private RoleTable() {}

        private static final String ROLE_ID = "ROLE_ID";

        private static final String ROLE_TYPE = "ROLE_TYPE";

        private static final String ROLE_NAME = "ROLE_NAME";

        private static final String DESCRIPTION = "DESCRIPTION";

        private static final String IMAGE_URL = "IMAGE_URL";

    }

    public static final class PrivilegeTable {

        private PrivilegeTable() {}

        public static final String PRIVILEGE_ID = "PRIVILEGE_ID";

        public static final String PRIVILEGE_NAME = "PRIVILEGE_NAME";

        public static final String DESC_NAME = "DESC_NAME";

    }

    public static final class StudentTable {

        private StudentTable() {}

        public static final String FIRST_NAME = "FIRST_NAME";

        public static final String LAST_NAME = "LAST_NAME";

        public static final String EMAIL = "EMAIL";

        public static final String AGE = "AGE";

    }

}
