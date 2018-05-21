package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.Manifest;
import android.app.LoaderManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.database.Cursor;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private static final int REQUEST_PERMISSION_WRITE = 1001;
    private boolean permissionGranted;
    private SwipeMenuListView listViewClasses;

    public static final String CLASS_ACTION_KEY = "class_action_key";
    public static final int ACTION_SHOW_CLASSES = 0;
    public static final int ACTION_ADD_CLASS = 1;
    public static final int ACTION_MODIFY_CLASS = 2;

    private FloatingActionButton addClassButton;

    public static final int SHOW_TASKS_REQUEST_CODE = 0;
    public static final int ADD_CLASS_REQUEST_CODE = 2;

    public static final String CLASS_LIST_KEY = "class_list_key";

    ClassDataSource classDataSource;

    ClassAdapter adapter;

    //TODO: maybe temporary?
    ArrayList<Class> classes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                //set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                //set item width
                deleteItem.setWidth(340);
                //set a icon
                deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
                //add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        addClassButton = findViewById(R.id.addNewClass);

        //TODO: make it so you can add classes and delete current temporary code
//        classes.add(new Class(null, "english"));
//        classes.add(new Class(null, "math"));
//        classes.add(new Class(null, "chem"));
//        classes.get(0).addTask(new Task(null, "Do Homework", 2018, 1, 1));

        if(getIntent().getExtras() != null) {
            classes = getIntent().getExtras().getParcelableArrayList(MainActivity.CLASS_LIST_KEY);
            if (((Integer) getIntent().getExtras().get(CLASS_ACTION_KEY)).intValue() == ACTION_MODIFY_CLASS) {
                Class newClass = (Class) getIntent().getExtras().get(ClassAdapter.CLASS_KEY);
                for (int i = 0; i < classes.size(); i++) {
                    if (classes.get(i).getClassID().equals(newClass.getClassID())) {
                        classes.set(i, newClass);
                        break;
                    }
                }
            } else if (((Integer) getIntent().getExtras().get(CLASS_ACTION_KEY)).intValue() == ACTION_ADD_CLASS) {
                classes.add((Class) getIntent().getExtras().get(ClassAdapter.CLASS_KEY));
                Toast.makeText(this, "Class added!", Toast.LENGTH_SHORT).show();
            }
        }

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
        }

        //Leave commented out screws up list thing --> idk if this even does anything
//        if(!permissionGranted) {
//            checkPermissions();
//            return;
//        }

        adapter = new ClassAdapter(this, classes);

        listViewClasses = findViewById(android.R.id.list);

        listViewClasses.setMenuCreator(creator);

        listViewClasses.setAdapter(adapter);

        listViewClasses.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                classes.remove(position);
                adapter.notifyDataSetChanged();
                //returning false closes the menu
                return false;
            }
        });

        listViewClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent myIntent = new Intent(view.getContext(), TasksActivity.class);
                    Class clickedClass = classes.get(position);
                    myIntent.putExtra(TasksActivity.TASK_ACTION_KEY, TasksActivity.ACTION_SHOW_TASKS);
                    myIntent.putExtra(ClassAdapter.CLASS_KEY, clickedClass);
                    myIntent.putParcelableArrayListExtra(MainActivity.CLASS_LIST_KEY, classes);
                    startActivityForResult(myIntent, SHOW_TASKS_REQUEST_CODE);
               }
           });

        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), AddClassActivity.class);
                myIntent.putParcelableArrayListExtra(CLASS_LIST_KEY, classes);
                startActivityForResult(myIntent, ADD_CLASS_REQUEST_CODE);
            }
        });

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
