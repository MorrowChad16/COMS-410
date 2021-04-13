package com.company1.gpasaver.ui.viewStudent;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company1.gpasaver.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewStudentHolder extends IViewHolder {
    public TextView studentName;
    public TextView className;
    public TextView email;
    public TextView number;
    public CircleImageView image;
    public Button button;
    public RelativeLayout viewStudentLayout;

    public ViewStudentHolder(View itemView){
        super(itemView);
        studentName = itemView.findViewById(R.id.view_student_name);
        className = itemView.findViewById(R.id.view_student_classtaught);
        email = itemView.findViewById(R.id.view_student_mail);
        image = itemView.findViewById(R.id.view_student_picture);
        number = itemView.findViewById(R.id.view_student_number);
        viewStudentLayout = itemView.findViewById(R.id.viewStudentLayout);
    }
}
