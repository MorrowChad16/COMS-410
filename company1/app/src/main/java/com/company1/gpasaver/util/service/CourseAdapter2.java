package com.company1.gpasaver.util.service;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company1.gpasaver.R;
import com.company1.gpasaver.models.Course;
import com.company1.gpasaver.ui.home.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class CourseAdapter2 extends RecyclerView.Adapter<CourseAdapter2.ViewHolder>  {

    private static ClickListener clickListener;

    public class ViewHolder extends RecyclerView.ViewHolder  {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView courseName;
        public TextView CourseDes;
        public CheckBox checkBox;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            courseName = (TextView) itemView.findViewById(R.id.tvClassName);
            CourseDes = (TextView) itemView.findViewById(R.id.tvClassDescription);
            checkBox= (CheckBox) itemView.findViewById(R.id.checkB);
        }

    }

    private List<Course> courses;
    private List<Integer> radio= new ArrayList<>();
    private int[] pos;

    public CourseAdapter2(List<Course> courses){
        this.courses=courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.class_data_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course =courses.get(position);
        TextView coursName=holder.courseName;
        TextView coursDes=holder.CourseDes;
        CheckBox checkBox= holder.checkBox;

        if(!courses.isEmpty()){
            pos= new int[courses.size()];
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos[position]==0){
                    pos[position]=1;
                }else{
                    pos[position]=0;

                }
                Log.i(TAG, "onClick: Pos"+position+" value:"+pos[position]);

            }
        });
        coursName.setText(course.getCourse_name());
        coursDes.setText(course.getCourse_des());


    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public Course getCourse(int position){
        return  courses.get(position);
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        CourseAdapter2.clickListener = clickListener;
    }

    public int[] getSelectedCourse(){
        return  pos;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }



}
