package com.jojo.money_manager.db;

import android.provider.BaseColumns;

public class AppDataBaseContract {

    public AppDataBaseContract() {}

    public static abstract class AppDatabaseEntry implements BaseColumns{

        public static final String DATABASE_NAME = "money-manager-db.db";
        public static final int DATABASE_VERSION = 1;

        public static final String ACCOUNT_TABLE = "Account";
        public static final String ACCOUNT_COLUMN_ID = "id";
        public static final String ACCOUNT_COLUMN_BALANCE = "balance";

        public static final String HISTORY_TABLE = "History";
        public static final String HISTORY_COLUMN_ID = "id";
        public static final String HISTORY_COLUMN_VALUE = "value";
        public static final String HISTORY_COLUMN_COMMENT = "comment";
        public static final String HISTORY_COLUMN_DATE = "date";


        public static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE IF NOT EXISTS " + ACCOUNT_TABLE + "(" +
                ACCOUNT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ACCOUNT_COLUMN_BALANCE + " VARCHAR);";

        public static final String CREATE_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS " + HISTORY_TABLE + "(" +
                HISTORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                HISTORY_COLUMN_VALUE + " VARCHAR," +
                HISTORY_COLUMN_DATE + " VARCHAR," +
                HISTORY_COLUMN_COMMENT +");";



        public static final String DELETE_DB_TABLES = "DROP TABLE IF EXISTS " + ACCOUNT_TABLE + ";" +
                "DROP TABLE IF EXISTS " + HISTORY_TABLE + ";";
    }
}
