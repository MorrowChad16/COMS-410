package com.company1.gpasaver.ui.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.company1.gpasaver.MySingleton;
import com.company1.gpasaver.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText oldPassword;
    private EditText newPassword;
    private EditText newPasswordVerificaton;

    //TODO figure out what this is for
    private static final String KEY_STATUS = "status";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Button submitChanges = findViewById(R.id.submit_password_change);
        oldPassword = findViewById(R.id.old_password_verify);
        newPassword = findViewById(R.id.new_password);
        newPasswordVerificaton = findViewById(R.id.new_password_verify);

        //if the password text box gains focus and loses it again, then we will check for errors.
        oldPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus){
                try {
                    //TODO check if old password input is correct
                    if(!MySingleton.mainUser.getPassword().equals(oldPassword.getText().toString())){
                        oldPassword.setError("Incorrect Old Password");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //if the password text box gains focus and loses it again, then we will check for errors.
        newPassword.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus){
                String newPass = newPassword.getText().toString();

                //Password needs to be greater than 8
                if(newPass.trim().length() < 8){
                    newPassword.setError("Password must be 8 characters");
                } else if(!newPass.matches("[a-zA-Z0-9!]{8,}")) {
                    newPassword.setError("Password contains an invalid symbol");
                }
            }
        });

        //if the password text box gains focus and loses it again, then we will check for errors.
        newPasswordVerificaton.setOnFocusChangeListener((view, hasFocus) -> {
            if(!hasFocus){
                String newPassVer = newPasswordVerificaton.getText().toString();

                //Password doesn't equal other new password
                if(!newPassVer.equals(newPassword.getText().toString())){
                    newPasswordVerificaton.setError("Passwords do not match");
                }
            }
        });

        /*
        Person clicked the submit changes button so check all the info is correct and submit it to the backend
         */
        submitChanges.setOnClickListener(view -> {
            //no errors for the new passwords, so submit the changes
            if(oldPassword.getError() == null &&  newPassword.getError() == null && newPasswordVerificaton.getError() == null){
                updateUserPassword();
            }
        });

        /*
        Person clicked outside the current text box, so reduce the keyboard
         */
        RelativeLayout touchInterceptor = this.findViewById(R.id.change_password_screen);
        touchInterceptor.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Rect outRect = new Rect();
                if (oldPassword.isFocused()) {
                    oldPassword.clearFocus();
                    oldPassword.requestFocus();
                    oldPassword.getGlobalVisibleRect(outRect);
                } else if (newPassword.isFocused()) {
                    newPassword.clearFocus();
                    newPassword.requestFocus();
                    newPassword.getGlobalVisibleRect(outRect);

                } else if (newPasswordVerificaton.isFocused()) {
                    newPasswordVerificaton.clearFocus();
                    newPasswordVerificaton.requestFocus();
                    newPasswordVerificaton.getGlobalVisibleRect(outRect);
                }

                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return false;
        });
    }

    /**
     * @return the JSON object with our updated password for user id
     */
    private JSONObject addJsonPassword(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("id", MySingleton.mainUser.getId());
            request.put("password", newPassword.toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    /**
     * Updates the user password on the back end
     */
    private void updateUserPassword() {
        JSONObject updatePassword = addJsonPassword();

        JsonObjectRequest jsonObjectRequestPassword = new JsonObjectRequest
            (Request.Method.POST, getResources().getString(R.string.update_user_password), updatePassword, response -> {
            //Check if user got registered successfully
            if (response.toString().equals("1")) {
                Toast.makeText(ChangePasswordActivity.this, "Updated Password!", Toast.LENGTH_SHORT).show();
                MySingleton.mainUser.setPassword(newPassword.toString().trim());
            } else {
                //Display error message whenever an error occurs
                Toast.makeText(ChangePasswordActivity.this, "Error Updating Password", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            //Display error message whenever an error occurs
            Toast.makeText(ChangePasswordActivity.this, "Error Updating Password", Toast.LENGTH_SHORT).show();
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequestPassword);
    }
}