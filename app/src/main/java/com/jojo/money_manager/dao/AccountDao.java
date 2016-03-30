package com.jojo.money_manager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jojo.money_manager.db.AppDataBase;
import com.jojo.money_manager.db.AppDataBaseContract;
import com.jojo.money_manager.pojo.Account;

import java.math.BigDecimal;


public class AccountDao {

    private AppDataBase db;
    private SQLiteDatabase dbInstance;

    public AccountDao(Context context) {
        db = new AppDataBase(context);
        dbInstance = db.getWritableDatabase();
    }

    public void insertAccount(Account account){
        ContentValues values = new ContentValues();

        values.put(AppDataBaseContract.AppDatabaseEntry.ACCOUNT_COLUMN_BALANCE, String.valueOf(account.getBalance()));

        dbInstance.insert(AppDataBaseContract.AppDatabaseEntry.ACCOUNT_TABLE, null, values);
    }

    public Account findLastAccount(){
        String query = "SELECT MAX(" + AppDataBaseContract.AppDatabaseEntry.ACCOUNT_COLUMN_ID + ") AS max_id," +
                AppDataBaseContract.AppDatabaseEntry.ACCOUNT_COLUMN_BALANCE +
                " FROM " + AppDataBaseContract.AppDatabaseEntry.ACCOUNT_TABLE + ";";

        Cursor cursor = dbInstance.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                return new Account(cursor.getFloat(cursor.getColumnIndex(AppDataBaseContract.AppDatabaseEntry.
                        ACCOUNT_COLUMN_BALANCE)));

            }while(cursor.moveToNext());
        }
        cursor.close();

        return null;
    }
}
