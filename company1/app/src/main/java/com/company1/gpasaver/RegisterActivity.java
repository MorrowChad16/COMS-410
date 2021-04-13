package com.company1.gpasaver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.company1.gpasaver.models.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {
  private static final String TAG = "RegisterActivity";
  private static final String KEY_STATUS = "status";
  private static final String KEY_MESSAGE = "message";
  private static final String KEY_FULL_NAME = "name";
  private static final String KEY_PASSWORD = "password";
  private static final String KEY_EMAIL = "email";
  private static final String KEY_EMPTY = "";
  protected EditText etFirstName;
  protected EditText etLastName;
  protected EditText etUsername;
  protected EditText etPassword;
  protected EditText etConfirmPassword;
  protected EditText etEmail;
  protected EditText etUniversity;
  protected EditText etMajor;
  protected String firstName;
  protected String lastName;
  protected String username;
  protected String password;
  protected String confirmPassword;
  protected String university;
  protected String major;
  protected String email;
  protected Boolean isTutor=false;
  private  Boolean isStudent=false;
  private CheckBox tutorCheckBox;
  private CheckBox studentCheckBox;

  ArrayList<Course> coursesList;
  protected ProgressDialog pDialog;
  //private String register_url = "http://10.0.2.2:8080/register";
 // protected String register_url = "http://coms-510-01.cs.iastate.edu:8080/register";
  protected String register_url = "http://coms-510-01.cs.iastate.edu:80/registration.php";
  //private SessionHandler session;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    TypefaceProvider.registerDefaultIconSets();
    // session = new SessionHandler(getApplicationContext());
    setContentView(R.layout.activity_registration);


    etPassword = findViewById(R.id.etPassword);
    etConfirmPassword = findViewById(R.id.etConfirmPassword);
    etUsername = findViewById(R.id.etGpa);
    etEmail = findViewById(R.id.etEmail);
    etFirstName = findViewById(R.id.etUniversity);
    etLastName  = findViewById(R.id.etLname);
    etUniversity=findViewById(R.id.etmajor);
    etMajor=findViewById(R.id.etUni);
    tutorCheckBox = (CheckBox) findViewById(R.id.tutorCheckBox);
    studentCheckBox=(CheckBox) findViewById(R.id.studentCheckBox);



   ImageButton backButton = findViewById(R.id.btnRegisterLogin);
    BootstrapButton register = findViewById(R.id.btnAdditionalRegister);


    // uncheck student checkbox and set isStudent to false
    tutorCheckBox.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        isTutor = ((CheckBox) view).isChecked();
        if( isStudent && isTutor){
          studentCheckBox.toggle();
          isStudent =studentCheckBox.isChecked();
        }
      }
    });

    // uncheck tutor checkbox and set isTutor to false
    studentCheckBox.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        isStudent = ((CheckBox) view).isChecked();
        if(isStudent && isTutor){
          tutorCheckBox.toggle();
          isTutor =tutorCheckBox.isChecked();
        }
      }
    });

    // Takes user back to login page
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
      }
    });

    register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Retrieve the data entered in the edit texts
        firstName = etFirstName.getText().toString().trim();
        lastName  = etLastName.getText().toString().trim();
        username = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        confirmPassword = etConfirmPassword.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        major=etMajor.getText().toString().trim();
        university=etUniversity.getText().toString().trim();
        isTutor = tutorCheckBox.isChecked();

        if (validateInputs()) {
          registerUser();
        }

      }
    });


  }


  //@TODO pass the userId to the next page
  private void navigateToNextPage(){
    Intent i = new Intent(RegisterActivity.this,AdditionalRegisterActivity.class);
    i.putExtra("isStudent", isStudent);
    i.putExtra("isTutor", isTutor);
    startActivity(i);
    finish();
  }


  /**
   * Display Progress bar while registering
   */
  private void displayLoader() {
    pDialog = new ProgressDialog(RegisterActivity.this);
    pDialog.setMessage("Signing Up.. Please wait...");
    pDialog.setIndeterminate(false);
    pDialog.setCancelable(false);
    pDialog.show();

  }

  /**
   * Launch Dashboard Activity on Successful Sign Up
   */



  private void registerUser() {
    displayLoader();
    JSONObject request = new JSONObject();
    try {
      request.put("firstname",firstName);
      request.put("lastname",lastName);
      if (isTutor) {
        request.put("tutor", 1);
      } else {
        request.put("tutor", 0);
      }
      request.put("username", username);
      request.put("email", email);
      request.put("password", password);
      request.put("School",university);
      request.put("Department",major);

    } catch (JSONException e) {
      e.printStackTrace();
    }
    JsonObjectRequest jsArrayRequest = new JsonObjectRequest
            (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                pDialog.dismiss();
                try {
                  System.out.println();
                  //Check if user got registered successfully
                  if (response.getInt(KEY_STATUS) == 0) {
                    //Set the user session
                    //session.loginUser(username,fullName);
                    Toast.makeText(getApplicationContext(),
                            "Registration Successful", Toast.LENGTH_SHORT).show();

                    Log.i(TAG, "onResponse: "+response.toString());
                    navigateToNextPage();

                  }else {

                    Log.i(TAG, "onResponse: "+request.toString());
//                    String message= request.getString("message");
                    Toast.makeText(getApplicationContext(),
                            "Registration Successful", Toast.LENGTH_SHORT).show();

                  }
                } catch (JSONException e) {
                  e.printStackTrace();
                }
              }
            }, new Response.ErrorListener() {

              @Override
              public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();

                Log.i(TAG, "onErrorResponse: "+error.getMessage());
                //Display error message whenever an error occurs
                Toast.makeText(getApplicationContext(),
                        "Unable to register. Please try after some time", Toast.LENGTH_SHORT).show();

              }
            });

    // Access the RequestQueue through your singleton class.
     MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
  }



  /**
   * Validates inputs and shows error if any
   * @TODO Need to validate the Tutor and Student checkbox
   *
   * @return
   */
  protected boolean validateInputs() {
    if (KEY_EMPTY.equals(firstName)) {
      etFirstName.setError("First Name cannot be empty");
      etFirstName.requestFocus();
      return false;
    }
    if (KEY_EMPTY.equals(lastName)) {
      etLastName.setError("Last Name cannot be empty");
      etLastName.requestFocus();
      return false;
    }
    if (KEY_EMPTY.equals(username)) {
      etUsername.setError("Username cannot be empty");
      etUsername.requestFocus();
      return false;
    }
    if (KEY_EMPTY.equals(email)) {
      etEmail.setError("Email cannot be empty");
      etEmail.requestFocus();
      return false;
    }
    if (KEY_EMPTY.equals(password)) {
      etPassword.setError("Password cannot be empty");
      etPassword.requestFocus();
      return false;
    }
    if (KEY_EMPTY.equals(confirmPassword)) {
      etConfirmPassword.setError("Confirm Password cannot be empty");
      etConfirmPassword.requestFocus();
      return false;
    }
    if (!password.equals(confirmPassword)) {
      etConfirmPassword.setError("Password and Confirm Password does not match");
      etConfirmPassword.requestFocus();
      return false;
    }
    if (KEY_EMPTY.equals(major)) {
      etMajor.setError("Major cannot be empty");
      etMajor.requestFocus();
      return false;
    }
    if (KEY_EMPTY.equals(university)) {
      etUniversity.setError("university cannot be empty");
      etUniversity.requestFocus();
      return false;
    }



    if(!isStudent && !isTutor){
      //Show a toast or some kind of error
      Toast toast = Toast.makeText(this, "message", Toast.LENGTH_SHORT);
      toast.setText("please check the  account type");
      toast.setGravity(Gravity.CENTER, 0, 0);
      //other setters
      toast.show();
      return  false;
    }


    return true;
  }


}