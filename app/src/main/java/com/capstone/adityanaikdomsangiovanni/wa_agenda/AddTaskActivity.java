package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText taskNameInput;

    private Button taskDateInput;
    private Button addTask;

    private DatePickerDialog dpd;
    private Calendar calendar;

    private static final int INSERT_TASK_REQUEST_CODE = 2;

    private int yearSelected = 0;
    private int monthSelected = 0;
    private int daySelected = 0;

    private Class currClass;
    private Task currTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        setTitle("Add Task");

        currClass = getIntent().getExtras().getParcelable(ClassAdapter.CLASS_KEY);

        taskNameInput = findViewById(R.id.taskNameIn);
        taskDateInput = findViewById(R.id.chooseDate);
        addTask = findViewById(R.id.addTaskButton);

        taskDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                dpd = new DatePickerDialog(AddTaskActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        yearSelected = year;
                        //TODO: double check that month is correct
                        monthSelected = month + 1;
                        daySelected = dayOfMonth;
                        taskDateInput.setText((month + 1) + "/" + dayOfMonth + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: error check date too
                if(taskNameInput.getText().toString().equals("") || yearSelected == 0 || monthSelected == 0 || daySelected == 0) {
                    Toast.makeText(AddTaskActivity.this, "Invalid Class Name or Date", Toast.LENGTH_SHORT).show();
                } else {
                    Task newTask = new Task(null, taskNameInput.getText().toString(), yearSelected, monthSelected, daySelected);
                    currClass.getTasks().add(newTask);
                    Intent myIntent = new Intent(v.getContext(), TasksActivity.class);
                    myIntent.putExtra(ClassAdapter.CLASS_KEY, currClass);
                    startActivityForResult(myIntent, INSERT_TASK_REQUEST_CODE);
                }
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());

        taskDateInput.setText(currentDateString);
    }
}
