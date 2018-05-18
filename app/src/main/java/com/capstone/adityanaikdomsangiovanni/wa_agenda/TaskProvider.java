package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TaskProvider extends ContentProvider {

    private static final String AUTHORITY = "com.capstone.adityanaikdomsangiovanni.wa_agenda.taskprovider";
    private static final String BASE_PATH = "tasks";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation, arbitrary
    private static final int TASKS = 1;
    private static final int TASKS_ID = 2;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    public static final String CONTENT_ITEM_TYPE = "Task";

    static {
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH, TASKS);
        // /# means any path
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/#", TASKS_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DBTaskOpenHelper helper = new DBTaskOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return database.query(DBTaskOpenHelper.TABLE_TASKS, DBTaskOpenHelper.ALL_COLUMNS, selection,
                null, null, null, DBTaskOpenHelper.TASK_CREATED + " DESC");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = database.insert(DBTaskOpenHelper.TABLE_TASKS, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return database.delete(DBTaskOpenHelper.TABLE_TASKS, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return database.update(DBTaskOpenHelper.TABLE_TASKS, values, selection, selectionArgs);
    }
}
