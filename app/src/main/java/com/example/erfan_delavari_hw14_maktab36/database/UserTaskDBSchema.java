package com.example.erfan_delavari_hw14_maktab36.database;

public class UserTaskDBSchema {
    public static final String NAME = "UserTaskDB.db";
    public static final int VERSION = 1;

    public static class UserTable{
        public static final String NAME = "UserTable";

        public static class COLS{
            public static final String ID = "user_db_id";
            public static final String UUID = "user_uuid";
            public static final String USERNAME = "user_username";
            public static final String PASSWORD = "user_password";
            public static final String DATE = "user_register_date";
        }
    }

    public static class TaskTable{
        public static final String NAME = "TaskTable";

        public static class COLS{
            public static final String USER_ID = "user_id";
            public static final String ID = "task_db_id";
            public static final String UUID = "task_uuid";
            public static final String NAME = "task_name";
            public static final String DESCRIPTION = "task_description";
            public static final String DATE = "task_date";
            public static final String TASK_STATE = "task_state";
        }
    }



}
