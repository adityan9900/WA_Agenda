package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Class implements Parcelable {

    private String classID;
    private String className;
    private List<Task> classTasks;

    public Class(String classID, String className) {
        if(classID == null) {
            classID = UUID.randomUUID().toString();
        }

        classTasks = new ArrayList<>();
        this.classID = classID;
        this.className = className;
    }

    public ContentValues toValues() {
        //TODO might need to change parameter to 3 unsure
        ContentValues values = new ContentValues(2);

        values.put(ClassTable.CLASS_ID, classID);
        values.put(ClassTable.CLASS_TEXT, className);

        return values;
    }

    public String getClassID() {
        return classID;
    }

    public String getClassName() {
        return className;
    }

    public void addTask(Task t) {
        classTasks.add(t);
    }

    public List<Task> getTasks() {
        return classTasks;
    }

    public String toString() {
        return "Class{" +
                "classID='" + classID + "\'" +
                ", className='" + className + "\'" +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.classID);
        dest.writeString(this.className);
        dest.writeList(this.classTasks);
    }

    protected Class(Parcel in) {
        this.classID = in.readString();
        this.className = in.readString();
        this.classTasks = new ArrayList<Task>();
        in.readList(this.classTasks, Task.class.getClassLoader());
    }

    public static final Parcelable.Creator<Class> CREATOR = new Parcelable.Creator<Class>() {
        @Override
        public Class createFromParcel(Parcel source) {
            return new Class(source);
        }

        @Override
        public Class[] newArray(int size) {
            return new Class[size];
        }
    };
}
