package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Button addClass = (Button) findViewById(R.id.btnAddTask);
    }
}
