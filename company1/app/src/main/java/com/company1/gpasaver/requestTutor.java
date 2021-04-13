package com.company1.gpasaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;

import java.util.Calendar;

public class requestTutor extends AppCompatActivity {
    static EditText fromTime, endTime;
    static String fromDate, toDate, fromTimeStr, toTimeStr;
    static Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_tutor);
        fromTime = (EditText) findViewById(R.id.etStartDate);
        //endTime = (EditText) findViewById(R.id.etEndDate);
        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(v);
            }
        });
    }

    public void showPicker(View v) {
        DialogFragment df = new DatePick();
        df.show(getSupportFragmentManager(), "datePicker");
        DialogFragment tf = new TimePick();
        tf.show(getSupportFragmentManager(), "datePicker");
    }

    class DatePick extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calender = Calendar.getInstance();
            int year = calender.get(Calendar.YEAR);
            int month = calender.get(Calendar.MONTH);
            int day = calender.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            if (flag == false)
                fromTime.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            else
                endTime.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
        }
    }
    class TimePick extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calender = Calendar.getInstance();
            int hour = calender.get(Calendar.HOUR_OF_DAY);
            int minute = calender.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (flag == false)
                fromTime.setText(fromTime.getText() + "-" + hourOfDay + ":" + minute);
            //else
                //endTime.setText(endTime.getText() + "-" + hourOfDay + ":" + minute);
        }
    }
}