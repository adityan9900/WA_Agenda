package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_WRITE = 1001;
    private boolean permissionGranted;
    private ListView listViewClasses;
    private TimePicker timePicker;


    public Notification.Builder notificationBuilder;

    public static final int SHOW_TASKS_REQUEST_CODE = 0;

    ClassDataSource classDataSource;

    //TODO: maybe temporary?
    List<Class> classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = new Intent();
        PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this,0,intent,0 );
        Notification noti = new Notification.Builder(MainActivity.this)
                .setContentTitle("TITLE OF THING")  ///this will be the title of the alert
                .setTicker("Ticker Text")
                .setSmallIcon(R.drawable.ic_info_black_24dp)
                .setContentIntent(pIntent).getNotification();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0,noti);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        classes = new ArrayList<>();

        //TODO: make it so you can add classes and delete current temporary code
        classes.add(new Class(null, "english"));
        classes.add(new Class(null, "math"));
        classes.add(new Class(null, "chem"));
        classes.get(0).addTask(new Task(null, "Do Homework", 2018, 1, 1));

        classDataSource = new ClassDataSource(this);
        classDataSource.open();

        //This method check for num items in the database
        long numItems = classDataSource.getClassCount();
        if(numItems == 0) {
            //only load data if it is empty
            for(Class c : classes) {
                //catches exception for repeated id's
                try {
                    classDataSource.createClass(c);
                } catch(SQLiteException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(this, "Data inserted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data already inserted!", Toast.LENGTH_SHORT).show();
        }

        //Leave commented out screws up list thing --> idk if this even does anything
//        if(!permissionGranted) {
//            checkPermissions();
//            return;
//        }

        ClassAdapter adapter = new ClassAdapter(this, classes);

        listViewClasses = findViewById(android.R.id.list);

        listViewClasses.setAdapter(adapter);

        listViewClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent myIntent = new Intent(view.getContext(), TasksActivity.class);
                    Class clickedClass = classes.get(position);
                    myIntent.putExtra(ClassAdapter.CLASS_KEY, clickedClass);
                    startActivityForResult(myIntent, SHOW_TASKS_REQUEST_CODE);
               }
           });

        //loads cursor and data -  use 'this' class to manage the loader
 //       getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Checks if external storage is available for read and write
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    // Initiate request for permissions.
    private boolean checkPermissions() {

        if (!isExternalStorageWritable()) {
            Toast.makeText(this, "This app only works on devices with usable external storage",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
            return false;
        } else {
            return true;
        }
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "External storage permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        classDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        classDataSource.open();
    }
}
