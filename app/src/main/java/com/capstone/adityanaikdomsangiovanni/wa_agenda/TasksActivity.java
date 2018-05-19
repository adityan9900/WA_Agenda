package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private String action;
    private ListView taskList;

    List<Task> tasks;

    TaskDataSource taskDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        taskList = findViewById(android.R.id.list);

        Intent intent = getIntent();
        Class currClass = intent.getExtras().getParcelable(ClassAdapter.CLASS_KEY);

        if(currClass == null) {
            Toast.makeText(this, "No Data Received", Toast.LENGTH_SHORT);
        } else {
            tasks = currClass.getTasks();
            setTitle(currClass.getClassName());
        }

        taskDataSource = new TaskDataSource(this);
        taskDataSource.open();

        //This method chekc for num items in the database
        long numItems = taskDataSource.getTaskCount();
        if(numItems == 0) {
            //only load data if it is empty
            for(Task t : tasks) {
                //catches exception for repeated id's
                try {
                    taskDataSource.createTask(t);
                } catch(SQLiteException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(this, "Data inserted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data already inserted!", Toast.LENGTH_SHORT).show();
        }

        TaskAdapter adapter = new TaskAdapter(this, tasks);

        taskList = findViewById(android.R.id.list);

        taskList.setAdapter(adapter);
    }
}
