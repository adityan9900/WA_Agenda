package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBTaskOpenHelper extends SQLiteOpenHelper {

    //Database name and version
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    //Constants for identifying table and column
    public static final String TABLE_TASKS = "tasks";
    //DO NOT CHANGE THIS VALUE - must be like this for content provider
    public static final String TASK_ID = "_id";
    //TODO: check if this is needed
    public static final String TASK_TEXT = "taskText";
    public static final String TASK_CREATED = "taskCreated";

    public static final String[] ALL_COLUMNS = {TASK_ID, TASK_TEXT, TASK_CREATED};

    public DBTaskOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TASKS + " (" +
                    TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TASK_TEXT + " TEXT, " +
                    TASK_CREATED + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    //runs when table is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    //drop current table and remake
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }
}

