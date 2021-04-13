package com.company1.gpasaver.ui.requestTutor;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.company1.gpasaver.R;
import com.company1.gpasaver.availableAccount;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RequestTutorFragment extends Fragment {
    static EditText fromTime, endTime, startDate, endDate;
    static String fromDate, toDate, fromTimeStr, toTimeStr,cost_insert;
    static Boolean flag = false;
    private RequestTutorViewModel requestTutorViewModel;
    static int hourly_rate=10;
    static Boolean first=false;
    static TextView tvCost;
    static String ft,tt;
    String dt;
    long stDate, enDate;
    //static float cost;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requestTutorViewModel =
                ViewModelProviders.of(this).get(RequestTutorViewModel.class);
        View root = inflater.inflate(R.layout.activity_request_tutor, container, false);
        final TextView textView = root.findViewById(R.id.tvRequestTutor);
        startDate = (EditText) root.findViewById(R.id.etStartDate);
        //endDate = (EditText) root.findViewById(R.id.etEndDate);
        fromTime = (EditText) root.findViewById(R.id.etStartTime);
        endTime = (EditText) root.findViewById(R.id.etEndTime);
        Button cost=(Button) root.findViewById(R.id.btnCalculateCost);
        tvCost=(TextView) root.findViewById(R.id.tvShowCost);
        Button btnSubmitRequest=(Button) root.findViewById(R.id.btnSubmitRequestTutor);
        requestTutorViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        startDate.setOnTouchListener (new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                first=true;
                flag=false;
                showPicker(v);
                //first=false;
                return false;
            }
        });

//        endDate.setOnTouchListener (new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                flag=true;
//                showPicker(v);
//                return false;
//            }
//        });

        fromTime.setOnTouchListener (new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flag=false;
                first=true;
                showtimePicker(v);
                //first=false;
                return false;
            }
        });

        endTime.setOnTouchListener (new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flag=true;
                first=true;
                showtimePicker(v);
                //first=false;
                return false;
            }
        });
        cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startDate.getText().toString().isEmpty() ||
                        //endDate.getText().toString().isEmpty() ||
                        fromTime.getText().toString().isEmpty() ||
                        endTime.getText().toString().isEmpty()) { //EditText is empty
                    Toast.makeText(getActivity(), "Please fill in mandatory text box(es)", Toast.LENGTH_LONG).show();
                } else {
                    int hourDiff=Integer.parseInt(tt.toString().split(":")[0])-Integer.parseInt(ft.toString().split(":")[0]);
                    int minDiff=Integer.parseInt(tt.toString().split(":")[1])-Integer.parseInt(ft.toString().split(":")[1]);
                    float cost=(hourDiff+(minDiff/60))*hourly_rate;
                    if(cost>0) {
                        tvCost.setText(Float.toString(cost));

                        /* added account check*/
                        availableAccount accountCheck = new availableAccount();
                        accountCheck.getUserBalance();
                        if(!accountCheck.accountCheck()){ //unavailable account
                            Toast.makeText(getActivity(), "Unavailable account", Toast.LENGTH_SHORT).show();
                        }
                        else if(!accountCheck.balanceCheck(cost)){ //balance not enough
                            Toast.makeText(getActivity(), "Balance Not Enough", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Please fill correct time", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        btnSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startDate.getText().toString().isEmpty() ||
                        //endDate.getText().toString().isEmpty() ||
                        fromTime.getText().toString().isEmpty() ||
                        endTime.getText().toString().isEmpty()) { //EditText is empty
                    Toast.makeText(getActivity(), "Please fill in mandatory text box(es)", Toast.LENGTH_LONG).show();
                } else {
                    getData();
                    insertData();
                    Toast.makeText(getActivity(), "Thank you for your interest", Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }
    public void getData()
    {
        cost_insert=tvCost.getText().toString();

    }
    private void insertData() {
        class InsertData extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show();

            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    String databaseLink = "http://coms-510-01.cs.iastate.edu/request_tutor.php?" +
                            "userid=" + 1 +
                            "&tutorid="+2+
                            "&courseid="+3+
                            "&date=" + fromDate +
                            "&ftime=" + fromTimeStr +
                            "&etime=" + toTimeStr+
                            "&cost="+cost_insert;

                    URL url = new URL(databaseLink);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    //wr.write(data);
                    //wr.flush();

                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    Toast.makeText(getActivity().getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
                    return sb.toString();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        InsertData insertDataObj = new InsertData();
        insertDataObj.execute();
    }
    public void showtimePicker(View v) {
        //DialogFragment df = new DatePick();
        //df.show(getFragmentManager(), "DatePicker");
        DialogFragment tf = new TimePick();
        tf.show(getFragmentManager(), "DatePicker");
    }
    public void showPicker(View v) {
        DialogFragment df = new DatePick();
        df.show(getFragmentManager(), "DatePicker");
        //DialogFragment tf = new TimePick();
        //tf.show(getFragmentManager(), "DatePicker");
    }
    public static class DatePick extends DialogFragment implements
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
            if(first)
            {
            if (flag == false)
                first=false;
                startDate.setText(year+"/"+ (month + 1) +"/" +dayOfMonth);
                fromDate=String.valueOf(year)+String.valueOf(month + 1)+String.valueOf(dayOfMonth);
            //else
             //   endDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                //
            }
        }
    }
    public static class TimePick extends DialogFragment implements
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
            if(first) {
                if (flag == false){
                    first=false;
                    String meridianNess = (hourOfDay/12==0?"AM":"PM");
                    int h = hourOfDay%12;
                    if (h==0) h = 12;
                    fromTime.setText(h + ":" + minute+" "+meridianNess);
                    ft=hourOfDay+":"+minute;
                    fromTimeStr=fromDate+String.valueOf(hourOfDay)+String.valueOf(minute)+"00";

                }

                else
                {
                    first=false;
                    String meridianNess = (hourOfDay/12==0?"AM":"PM");
                    int h = hourOfDay%12;
                    if (h==0) h = 12;
                    endTime.setText(h + ":" + minute+" "+meridianNess);
                    tt=hourOfDay+":"+minute;
                    toTimeStr=fromDate+String.valueOf(hourOfDay)+String.valueOf(minute)+"00";
                }
            }
            first=false;
        }
    }
}