package com.example.erfan_delavari_hw14_maktab36.controller.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.erfan_delavari_hw14_maktab36.controller.database.UserTaskDBSchema.*;

public class UserTaskDBBaseHelper extends SQLiteOpenHelper {


    public UserTaskDBBaseHelper(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + UserTable.NAME + "(" +
                UserTable.COLS.ID + " integer primary key autoincrement," +
                UserTable.COLS.UUID + " text," +
                UserTable.COLS.USERNAME + " text,"+
                UserTable.COLS.PASSWORD + " text,"+
                UserTable.COLS.DATE + " long" +
                ")");

        db.execSQL("CREATE TABLE "+ TaskTable.NAME + "("+
                TaskTable.COLS.ID + " integer primary key autoincrement,"+
                TaskTable.COLS.USER_ID + " integer,"+
                TaskTable.COLS.UUID + " text,"+
                TaskTable.COLS.NAME + " text,"+
                TaskTable.COLS.DESCRIPTION + " text,"+
                TaskTable.COLS.DATE + " long,"+
                TaskTable.COLS.TASK_STATE + " integer" +
                ")");
    }



    // later
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
