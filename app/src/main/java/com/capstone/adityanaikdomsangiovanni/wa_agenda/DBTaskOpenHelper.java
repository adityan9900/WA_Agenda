package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class is almost identical to the DBClassOpenHelper class. It is also likely
 * redundant, but for the sake of causing no errors it will be used.
 */
public class DBTaskOpenHelper extends SQLiteOpenHelper {

    //Database name and version
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    public DBTaskOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //runs when table is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaskTable.TABLE_CREATE);
    }

    //drop current table and remake
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TaskTable.TABLE_DELETE);
        onCreate(db);
    }
}

