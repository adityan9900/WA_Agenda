package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{

    //Database name and version
    private static final String DATABASE_NAME = "agenda.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for identifying table and column
    public static final String TABLE_CLASSES = "classes";
    //DO NOT CHANGE THIS VALUE - must be like this for content provider
    public static final String CLASS_ID = "_id";
    //TODO: check if this is needed
    public static final String CLASS_TEXT = "classText";
    public static final String CLASS_CREATED = "classCreated";

    public static final String[] ALL_COLUMNS = {CLASS_ID, CLASS_TEXT, CLASS_CREATED};

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CLASSES + " (" +
                    CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CLASS_TEXT + " TEXT, " +
                    CLASS_CREATED + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    //runs when table is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    //drop current table and remake
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSES);
        onCreate(db);
    }
}
