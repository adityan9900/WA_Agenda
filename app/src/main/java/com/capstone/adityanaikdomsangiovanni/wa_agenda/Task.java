package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;
import java.util.UUID;

public class Task implements Parcelable {

    private String taskName;
    private String taskID;
    private int year, month, day;

    //TODO: figure out data type of date
    //private Date dueDate;

    public Task(String taskID, String taskName, int year, int month, int day) {
        if(taskID == null) {
            taskID = UUID.randomUUID().toString();
        }
        this.year = year;
        this.month = month;
        this.day = day;
        this.taskID = taskID;
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public ContentValues toValues() {
        //TODO might need to change parameter to 3 unsure
        ContentValues values = new ContentValues(2);

        values.put(TaskTable.TASK_ID, taskID);
        values.put(TaskTable.TASK_TEXT, taskName);

        return values;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.taskName);
    }

    protected Task(Parcel in) {
        this.taskName = in.readString();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
