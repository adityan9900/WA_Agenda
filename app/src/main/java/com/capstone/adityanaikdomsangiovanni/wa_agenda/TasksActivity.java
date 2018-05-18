package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class TasksActivity extends AppCompatActivity {

    private String action;
    private ListView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        taskList = findViewById(android.R.id.list);

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra(ClassProvider.CONTENT_ITEM_TYPE);

        //TODO: add title as class name
        setTitle("");

        if(uri == null) {
            action = Intent.ACTION_INSERT;
        }
//        Button addClass = (Button) findViewById(R.id.btnAddTask);
    }
}
