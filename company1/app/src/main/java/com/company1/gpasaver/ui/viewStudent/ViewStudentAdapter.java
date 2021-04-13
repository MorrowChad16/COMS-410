package com.company1.gpasaver.ui.viewStudent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.company1.gpasaver.MySingleton;
import com.company1.gpasaver.R;

import java.util.ArrayList;

public class ViewStudentAdapter extends RecyclerView.Adapter<ViewStudentHolder> {
    private ArrayList<Student> studentList;
    private Context mContext;
    private TextView studentText;
    private Button yesButton;
    private  Button noButton;
    private String student_id="";
    private String tutor_id="";
    private int Position = 0;
    public ViewStudentAdapter(ArrayList<Student> studentList, Context context, View view)
    {
        this.studentList = studentList;
        mContext = context;
        studentText = view.findViewById(R.id.pop_up_message_student);
        yesButton = view.findViewById(R.id.current_students_remove);
        noButton = view.findViewById(R.id.current_students_cancel);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YesButtonClick(Position);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoButtonClicked();
            }
        });
    }
    private void turnEverythingInvisible () {
        studentText.setVisibility(View.INVISIBLE);
        yesButton.setVisibility(View.INVISIBLE);
        noButton.setVisibility(View.INVISIBLE);
    }

    private void NoButtonClicked () {
        turnEverythingInvisible();
    }


    private void YesButtonClick (int position) {
        studentList.remove(position);
        DelinkStudent delink = new DelinkStudent(student_id, tutor_id);
        delink.delinkStudent(mContext);
        student_id = "";
        tutor_id = "";
        notifyDataSetChanged();
        turnEverythingInvisible();
    }

    @NonNull
    @Override
    public ViewStudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_viewstudent, parent, false);
        ViewStudentHolder vsh =  new ViewStudentHolder(view);
        return vsh;
    }

    public void onBindViewHolder (@NonNull  ViewStudentHolder holder, int position){
        Student currentStudent = studentList.get(position);
        holder.studentName.setText(currentStudent.getName());
        holder.className.setText(currentStudent.getClassName());
        holder.email.setText(currentStudent.getEmail());
        holder.number.setText(currentStudent.getPhoneNumber());
        Glide.with(mContext)
                .asBitmap()
                .load(currentStudent.getPicture())
                .into(holder.image);

        holder.viewStudentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Position = position;
                student_id += currentStudent.getId();
                if (MySingleton.mainUser == null){
                    tutor_id += "1";
                }else {
                    tutor_id += MySingleton.mainUser.getId();
                }
                String text = "Cancel " + currentStudent.getName() + " ?";
                studentText.setVisibility(View.VISIBLE);
                studentText.setText(text);
                yesButton.setVisibility(View.VISIBLE);
                noButton.setVisibility(View.VISIBLE);
            }
        });
    }
    public int getItemCount() {return studentList.size();}
}
