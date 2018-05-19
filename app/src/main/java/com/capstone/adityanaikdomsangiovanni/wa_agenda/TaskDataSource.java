package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDataSource {
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbHelper;

    public TaskDataSource(Context mContext) {
        this.mContext = mContext;
        mDbHelper = new DBTaskOpenHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDatabase.close();
    }

    public Task createTask(Task t) {
        ContentValues values = t.toValues();
        mDatabase.insert(TaskTable.TABLE_TASKS, null, values);
        return t;
    }

    public long getTaskCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, TaskTable.TABLE_TASKS);
    }
}
