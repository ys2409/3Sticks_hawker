package com.myapplicationdev.android.a3sticks_hawker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperProfile extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "profile.db";
    private static final String TABLE_PROFILE = "profile";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_STALL = "stall";
    private static final String COLUMN_OWNER = "owner";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_START = "start";
    private static final String COLUMN_END = "end";

    public DBHelperProfile(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProfileTableSql = "CREATE TABLE " + TABLE_PROFILE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_STALL + " TEXT,"
                + COLUMN_OWNER + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_START + " TEXT,"
                + COLUMN_END + " TEXT ) ";
        db.execSQL(createProfileTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
