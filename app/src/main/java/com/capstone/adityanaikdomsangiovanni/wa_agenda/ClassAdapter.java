package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ClassAdapter extends ArrayAdapter<Class> {

    List<Class> classes;
    LayoutInflater inflator;

    public static final String CLASS_KEY = "class_key";

    public ClassAdapter(@NonNull Context context, @NonNull List<Class> objects) {
        //TODO: maybe not simple list item??
        super(context, android.R.layout.simple_list_item_1, objects);

        classes = objects;
        inflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = inflator.inflate(R.layout.list_item, parent, false);
        }

        //TODO: remove if unneccessary
        Class currClass = classes.get(position);

        TextView tv = convertView.findViewById(R.id.nameText);

        tv.setText(currClass.getClassName());

        return convertView;
    }
}
