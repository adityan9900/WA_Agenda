package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    List<Task> tasks;
    LayoutInflater inflator;

    public static final String TASK_KEY = "task_key";

    public TaskAdapter(@NonNull Context context, @NonNull List<Task> objects) {
        //TODO: maybe not simple list item??
        super(context, android.R.layout.simple_list_item_1, objects);

        tasks = objects;
        inflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = inflator.inflate(R.layout.list_item, parent, false);
        }

        //TODO: remove if unneccessary
        Task currTask = tasks.get(position);

        TextView tv = convertView.findViewById(R.id.nameText);

        tv.setText(currTask.getTaskName());

        return convertView;
    }
}
