package com.company1.gpasaver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.company1.gpasaver.models.Login;
import com.company1.gpasaver.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    protected EditText etUsername;
    protected EditText etEmail;
    protected EditText etPassword;
    protected String username;
    protected String email;
    protected String password;
    private ProgressDialog pDialog;
    protected User MUser = new User();
    private String login_url = "http://coms-510-01.cs.iastate.edu:80/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        // session = new SessionHandler(getApplicationContext());
//
//        if(session.isLoggedIn()){
//            loadDashboard();
//        }
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etGpa);
        etPassword = findViewById(R.id.etLoginPassword);

        BootstrapButton register = findViewById(R.id.btnLoginRegister);
        BootstrapButton login = findViewById(R.id.btnLogin);
//        BootstrapButton main_app = findViewById(R.id.btnMainApp);

        //Launch Registration screen when Register Button is clicked
        register.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
            finish();
        });

//        main_app.setOnClickListener(v -> {
//            MUser.setFirstName("Ron");
//            MUser.setLastName("Weasley");
//            MUser.setUserName("moore2score");
//            MUser.setId(1);
//            MUser.setEmail("steve@gmail.com");
//            MUser.setPassword("password");
//            MUser.setPhoneNumber("3099877890");
//            MUser.setBalance(20.0);
//            MUser.setPicture("https://randomuser.me/api/portraits/med/men/76.jpg");
//            loadDashboard();
//        });

        login.setOnClickListener(v -> {
            //Retrieve the data entered in the edit texts
            username = etUsername.getText().toString().toLowerCase().trim();
            System.out.println("Username: " + etUsername);
            password = etPassword.getText().toString().trim();
            System.out.println("Password: " + etPassword);
            if (validateInputs()) {
                login();
            }
        });
    }

    /**
     * Launch Dashboard Activity on Successful Login
     */
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("serialize_tutor", MUser);
        startActivity(i);
        finish();

    }

    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void login() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, response -> {
                    pDialog.dismiss();
                    System.out.print("x");
                    try {
                        if (response.getInt(KEY_STATUS) == 0) {
                            String x = response.getString("full_name");
                            if(x.equals("null")){
                                MUser.setFirstName("Ron");
                                MUser.setLastName("Weasley");
                                MUser.setUserName("moore2score");
                                MUser.setId(1);
                                MUser.setIsTutor(0);
                                MUser.setEmail("steve@gmail.com");
                                MUser.setPassword("password");
                                MUser.setPhoneNumber("3099877890");
                                MUser.setBalance(20.0);
                                MUser.setPicture("https://randomuser.me/api/portraits/med/men/76.jpg");
                            } else{
                                String firstName = response.getString("full_name").split(" ")[0];
                                MUser.setFirstName(firstName);
                                String lastName = response.getString("full_name").split(" ")[1];
                                MUser.setLastName(lastName);
                                MUser.setUserName(response.getString("username"));
                                MUser.setId(response.getInt("user_id"));
                                MUser.setIsTutor(response.getInt("isTutor"));
                                MUser.setEmail(response.getString("email"));
                                MUser.setPassword(response.getString("password"));
                                MUser.setPhoneNumber(response.getString("phone"));
                                if(response.getString("balance").equals("null")){
                                    MUser.setBalance(10);
                                }else{
                                    MUser.setBalance(response.getDouble("balance"));
                                }
                                MUser.setPicture(response.getString("image"));
                            }
                            loadDashboard();

                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Username or Password Invalid", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    pDialog.dismiss();

                    //Display error message whenever an error occurs
                    Toast.makeText(getApplicationContext(),
                            "Username or Password Invalid", Toast.LENGTH_SHORT).show();

                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Validates inputs and shows error if any
     * @return
     */
    protected boolean validateInputs() {
        if(KEY_EMPTY.equals(username)){
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }

        if(KEY_EMPTY.equals(password)){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }
}