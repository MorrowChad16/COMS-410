package com.company1.gpasaver.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.company1.gpasaver.R;
import com.company1.gpasaver.models.Course;

import java.util.ArrayList;

public class CourseAdapter extends ArrayAdapter<Course> {

    public CourseAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Course> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.class_data_layout, parent, false);
        TextView courseName = (TextView) row.findViewById(R.id.tvClassName);
        courseName.setText(this.getItem(position).getCourse_name());

        TextView courseDes = (TextView) row.findViewById(R.id.tvClassDescription);
        //courseDes.setText(this.getItem(position).getCourse_des());

        return row;
    }
}
