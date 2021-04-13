package com.company1.gpasaver.ui.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.company1.gpasaver.MainActivity;
import com.company1.gpasaver.MySingleton;
import com.company1.gpasaver.R;
import com.company1.gpasaver.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfile extends AppCompatActivity {
    public User tutor;
    public EditText get_sms;
    public  TextView amount;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile);
        //UserViewModel userViewModel = new UserViewModel(tutor, getApplicationContext());
        TextView name_text = (TextView) findViewById(R.id.show_name);
        name_text.setText(MySingleton.mainUser.getFirstName());

        TextView mail_text = (TextView) findViewById(R.id.show_mail);
        mail_text.setText(MySingleton.mainUser.getEmail());

        TextView number_text = (TextView) findViewById(R.id.show_number);
        number_text.setText(MySingleton.mainUser.getPhoneNumber());

        amount = (TextView) findViewById(R.id.show_classes);
        amount.setText("Current Balance is: " + MySingleton.mainUser.getBalance().toString() );

        TextView userRole = (TextView) findViewById(R.id.userRole);
        userRole.setText("Tutor");

        Button send_SMS = (Button) findViewById(R.id.send_sms);

        get_sms = (EditText) findViewById(R.id.balance);
        get_sms.setHint("Current Balance is: " + MySingleton.mainUser.getBalance().toString() );

        send_SMS.setOnClickListener(view -> {
            int balance = 0;

            try {
                balance  = Integer.parseInt(get_sms.getText().toString());
                System.out.println("x");
            } catch (NumberFormatException | NullPointerException nfe) {
                get_sms.setError("Not a Valid Number");
                get_sms.requestFocus();
            }

            JSONObject request = new JSONObject();
            try {
                //Populate the request parameters
                request.put("id", MySingleton.mainUser.getId());
                request.put("balance", balance + MySingleton.mainUser.getBalance());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                    (Request.Method.POST, getResources().getString(R.string.update_user_balance), request, response -> {
                System.out.println("x");
                get_sms.setError("updated successfully");
            }, error -> {
                System.out.println("x");
                //Display error message whenever an error occurs
                Toast.makeText(getApplicationContext(),
                        "Error", Toast.LENGTH_SHORT).show();
            });

            MySingleton.mainUser.setBalance(balance + MySingleton.mainUser.getBalance());
            amount.setText("Current Balance is: " + MySingleton.mainUser.getBalance().toString());
            get_sms.setHint("Current Balance is: " + MySingleton.mainUser.getBalance().toString());
        });

    }





}