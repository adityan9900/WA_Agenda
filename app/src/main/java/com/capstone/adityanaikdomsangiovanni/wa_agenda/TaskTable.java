package com.capstone.adityanaikdomsangiovanni.wa_agenda;

public class TaskTable {


    //Constants for identifying table and column
    public static final String TABLE_TASKS = "tasks";
    //DO NOT CHANGE THIS VALUE - must be like this for content provider
    public static final String TASK_ID = "_id";
    public static final String TASK_TEXT = "taskText";
    //probably useless
    public static final String TASK_CREATED = "taskCreated";

    //SQL to create table
    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TASKS + "(" +
                    TASK_ID + " TEXT PRIMARY KEY," +
                    TASK_TEXT + " TEXT" +
                    ");";

    public static final String TABLE_DELETE = "DROP TABLE " + TABLE_TASKS;

}
