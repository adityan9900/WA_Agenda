package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private static final int ADD_TASK_REQUEST_CODE = 1;
    private ListView taskList;

    private FloatingActionButton addTaskButton;

    private List<Task> tasks;

    private TaskDataSource taskDataSource;

    private Class currClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        taskList = findViewById(android.R.id.list);
        addTaskButton = findViewById(R.id.addNewTask);

        Intent intent = getIntent();

        currClass = intent.getExtras().getParcelable(ClassAdapter.CLASS_KEY);

        if(currClass == null) {
            Toast.makeText(this, "No Data Received", Toast.LENGTH_SHORT);
        } else {
            tasks = currClass.getTasks();
            setTitle(currClass.getClassName());
        }

        taskDataSource = new TaskDataSource(this);
        taskDataSource.open();

        //This method check for num items in the database
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

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), AddTaskActivity.class);
                myIntent.putExtra(ClassAdapter.CLASS_KEY, currClass);
                startActivityForResult(myIntent, ADD_TASK_REQUEST_CODE);
            }
        });
    }
}
