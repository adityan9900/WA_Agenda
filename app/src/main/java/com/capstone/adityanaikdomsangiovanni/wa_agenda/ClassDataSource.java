package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClassDataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbHelper;

    public ClassDataSource(Context mContext) {
        this.mContext = mContext;
        mDbHelper = new DBClassOpenHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDatabase.close();
    }

    public Class createClass(Class c) {
        ContentValues values = c.toValues();
        mDatabase.insert(ClassTable.TABLE_CLASSES, null, values);
        return c;
    }

    public long getClassCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, ClassTable.TABLE_CLASSES);
    }
}
