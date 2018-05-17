package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ClassProvider extends ContentProvider {

    private static final String AUTHORITY = "com.capstone.adityanaikdomsangiovanni.wa_agenda.classprovider";
    private static final String BASE_PATH = "classes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation, arbitrary
    private static final int CLASSES = 1;
    private static final int CLASSES_ID = 2;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH, CLASSES);
        // /# means any path
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH + "/#", CLASSES_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DBOpenHelper helper = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return database.query(DBOpenHelper.TABLE_CLASSES, DBOpenHelper.ALL_COLUMNS, selection,
                             null, null, null, DBOpenHelper.CLASS_CREATED + " DESC");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = database.insert(DBOpenHelper.TABLE_CLASSES, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return database.delete(DBOpenHelper.TABLE_CLASSES, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return database.update(DBOpenHelper.TABLE_CLASSES, values, selection, selectionArgs);
    }
}
