package com.springdata.jpa.constants;

public final class AppTables {

    private AppTables(){}

    public static final String STUDENT = "STUDENT";

    public static final class AuditModelTable {

        private AuditModelTable(){}

        public static final String ID = "ID";

        public static final String CREATED_BY = "CREATED_BY";

        public static final String CREATED_DATE = "CREATED_DATE";

        public static final String LAST_MODIFIED_BY = "LAST_MODIFIED_BY";

        public static final String LAST_MODIFIED_DATE = "LAST_MODIFIED_DATE";

    }

    public static final class StudentTable {

        private StudentTable(){}

        public static final String FIRST_NAME = "FIRST_NAME";

        public static final String LAST_NAME = "LAST_NAME";

        public static final String EMAIL = "EMAIL";

        public static final String AGE = "AGE";

    }

}
