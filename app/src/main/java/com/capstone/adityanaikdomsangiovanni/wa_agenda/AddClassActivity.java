package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddClassActivity extends AppCompatActivity {

    private EditText classNameInput;

    private Button addClass;

    private static final int INSERT_CLASS_REQUEST_CODE = 3;

    ArrayList<Class> currClassList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        setTitle("Add Class");

        currClassList = getIntent().getExtras().getParcelableArrayList(MainActivity.CLASS_LIST_KEY);

        classNameInput = findViewById(R.id.classNameIn);
        addClass = findViewById(R.id.addClassButton);

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: error check date too
                if(classNameInput.getText().toString().equals("")) {
                    Toast.makeText(AddClassActivity.this, "Invalid Class Name", Toast.LENGTH_SHORT).show();
                } else {
                    Class newClass = new Class(null, classNameInput.getText().toString());
                    currClassList.add(newClass);
                    Intent myIntent = new Intent(v.getContext(), MainActivity.class);
                    myIntent.putExtra(ClassAdapter.CLASS_KEY, newClass);
                    startActivityForResult(myIntent, INSERT_CLASS_REQUEST_CODE);
                }
            }
        });
    }
}
