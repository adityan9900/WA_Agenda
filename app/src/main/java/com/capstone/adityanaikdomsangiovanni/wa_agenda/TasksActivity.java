package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity {

    private static final int ADD_TASK_REQUEST_CODE = 1;
    private SwipeMenuListView taskList;

    private FloatingActionButton addTaskButton;

    private ArrayList<Task> tasks;

    private TaskDataSource taskDataSource;

    private Class currClass;

    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(340);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        taskList = findViewById(android.R.id.list);
        addTaskButton = findViewById(R.id.addNewTask);

        taskList.setMenuCreator(creator);

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

        adapter = new TaskAdapter(this, tasks);

        taskList = findViewById(android.R.id.list);

        taskList.setAdapter(adapter);

        taskList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                tasks.remove(position);
                adapter.notifyDataSetChanged();
                //returning false closes the menu
                return false;
            }
        });

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
