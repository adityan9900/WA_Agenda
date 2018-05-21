package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
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

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    ArrayList<Class> currClassList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        currClassList = getIntent().getExtras().getParcelableArrayList(MainActivity.CLASS_LIST_KEY);

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
        final Intent my_Intent = new Intent(this,Alarm_Receiver.class);   //used for notifications
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: error check date too
                if(taskNameInput.getText().toString().equals("") || yearSelected == 0 || monthSelected == 0 || daySelected == 0) {
                    Toast.makeText(AddTaskActivity.this, "Invalid Task Name or Date", Toast.LENGTH_SHORT).show();
                } else {
                    Task newTask = new Task(null, taskNameInput.getText().toString(), yearSelected, monthSelected, daySelected);
                    Intent myIntent = new Intent(v.getContext(), TasksActivity.class);
                    myIntent.putExtra(ClassAdapter.CLASS_KEY, currClass);
                    myIntent.putExtra(TasksActivity.TASK_ACTION_KEY, TasksActivity.ACTION_ADD_TASK);
                    myIntent.putExtra(TaskAdapter.TASK_KEY, newTask);
                    myIntent.putParcelableArrayListExtra(MainActivity.CLASS_LIST_KEY, currClassList);
                    startActivityForResult(myIntent, INSERT_TASK_REQUEST_CODE);
                    //notifications
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    pendingIntent = PendingIntent.getBroadcast(AddTaskActivity.this,0,my_Intent,0);
                    alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()-21600000,pendingIntent);    //6pm day before

                }
            }
        });




    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.putExtra(ClassAdapter.CLASS_KEY, currClass);
                upIntent.putExtra(TasksActivity.TASK_ACTION_KEY, TasksActivity.ACTION_SHOW_TASKS);
                upIntent.putParcelableArrayListExtra(MainActivity.CLASS_LIST_KEY, currClassList);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
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
