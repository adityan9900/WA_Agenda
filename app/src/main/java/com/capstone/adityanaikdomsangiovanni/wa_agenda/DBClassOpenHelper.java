package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBClassOpenHelper extends SQLiteOpenHelper{

    //Database name and version
    private static final String DATABASE_NAME = "classes.db";
    private static final int DATABASE_VERSION = 1;

    //public static final String[] ALL_COLUMNS = {CLASS_ID, CLASS_TEXT, CLASS_CREATED};

    public DBClassOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //runs when table is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClassTable.TABLE_CREATE);
    }

    //drop current table and remake
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ClassTable.TABLE_DELETE);
        onCreate(db);
    }
}
