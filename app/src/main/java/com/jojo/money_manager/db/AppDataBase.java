package com.jojo.money_manager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDataBase extends SQLiteOpenHelper{

    public AppDataBase(Context context) {
        super(context, AppDataBaseContract.AppDatabaseEntry.DATABASE_NAME, null, AppDataBaseContract.AppDatabaseEntry.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AppDataBaseContract.AppDatabaseEntry.CREATE_ACCOUNT_TABLE);
        db.execSQL(AppDataBaseContract.AppDatabaseEntry.CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AppDataBaseContract.AppDatabaseEntry.DELETE_DB_TABLES);
        onCreate(db);
    }
}
