package com.capstone.adityanaikdomsangiovanni.wa_agenda;

public class ClassTable {

    //Constants for identifying table and column
    public static final String TABLE_CLASSES = "classes";
    //DO NOT CHANGE THIS VALUE - must be like this for content provider
    public static final String CLASS_ID = "_id";
    public static final String CLASS_TEXT = "classText";
    public static final String CLASS_CREATED = "classCreated";
    //test
    public static final String CLASS_TASKS = "allTasks";

    //SQL to create table
    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CLASSES + "(" +
                    CLASS_ID + " TEXT PRIMARY KEY," +
                    CLASS_TEXT + " TEXT" +
                    ");";

    public static final String TABLE_DELETE = "DROP TABLE " + TABLE_CLASSES;

}
