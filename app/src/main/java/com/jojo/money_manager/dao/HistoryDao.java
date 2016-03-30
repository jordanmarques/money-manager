package com.jojo.money_manager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jojo.money_manager.db.AppDataBase;
import com.jojo.money_manager.db.AppDataBaseContract;
import com.jojo.money_manager.pojo.Account;
import com.jojo.money_manager.pojo.History;

import java.util.ArrayList;
import java.util.List;


public class HistoryDao {

    private AppDataBase db;
    private SQLiteDatabase dbInstance;

    public HistoryDao(Context context) {
        db = new AppDataBase(context);
        dbInstance = db.getWritableDatabase();
    }

    public void insertHistory(History history){
        ContentValues values = new ContentValues();

        values.put(AppDataBaseContract.AppDatabaseEntry.HISTORY_COLUMN_VALUE, String.valueOf(history.getValue()));
        values.put(AppDataBaseContract.AppDatabaseEntry.HISTORY_COLUMN_COMMENT, String.valueOf(history.getComment()));
        values.put(AppDataBaseContract.AppDatabaseEntry.HISTORY_COLUMN_DATE, String.valueOf(history.getDate()));

        dbInstance.insert(AppDataBaseContract.AppDatabaseEntry.HISTORY_TABLE, null, values);
    }

    public List<History> findAll(){

        Cursor  cursor = dbInstance.rawQuery("SELECT * FROM " + AppDataBaseContract.AppDatabaseEntry.HISTORY_TABLE ,null);

        List<History> historyList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                History history = new History(
                        cursor.getString(cursor.getColumnIndex(AppDataBaseContract.AppDatabaseEntry.HISTORY_COLUMN_VALUE)),
                        cursor.getString(cursor.getColumnIndex(AppDataBaseContract.AppDatabaseEntry.HISTORY_COLUMN_COMMENT)),
                        cursor.getString(cursor.getColumnIndex(AppDataBaseContract.AppDatabaseEntry.HISTORY_COLUMN_DATE)));

                historyList.add(history);
            }while(cursor.moveToNext());
        }

        return historyList;
    }
}
