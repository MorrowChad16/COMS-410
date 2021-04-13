package com.company1.gpasaver.ui.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.company1.gpasaver.MainActivity;
import com.company1.gpasaver.MySingleton;
import com.company1.gpasaver.PaymentActivity;
import com.company1.gpasaver.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class SettingsFragment extends Fragment {
    private EditText firstName;
    private EditText lastName;
    private EditText userName;
    private EditText email;
    private Button submitChanges;

    //TODO figure out what this is for
    private static final String KEY_STATUS = "status";

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstName = Objects.requireNonNull(getActivity()).findViewById(R.id.account_first_name_dynamic);
        Button firstNameEditButton = getActivity().findViewById(R.id.edit_first_name);

        lastName = getActivity().findViewById(R.id.account_last_name_dynamic);
        Button lastNameEditButton = getActivity().findViewById(R.id.edit_last_name);

        userName = getActivity().findViewById(R.id.account_user_name_dynamic);
        Button userNameEditButton = getActivity().findViewById(R.id.edit_username);

        email = getActivity().findViewById(R.id.account_email_dynamic);
        Button emailEditButton = getActivity().findViewById(R.id.edit_email);

        TextView changePassword = getActivity().findViewById(R.id.account_password);
        Button paymentMethod = getActivity().findViewById(R.id.payment_method);
        TextView paymentHistory = getActivity().findViewById(R.id.payment_history_text);
        ImageView paymentHistoryButton = getActivity().findViewById(R.id.payment_history_arrow);
        submitChanges = getActivity().findViewById(R.id.submit_account_changes);

        setFirstName();
        setLastName();
        setUserName();
        setEmail();

        if(MySingleton.mainUser.getIsTutor() == 1){
            paymentMethod.setVisibility(View.INVISIBLE);
            paymentHistory.setVisibility(View.INVISIBLE);
            paymentHistoryButton.setVisibility(View.INVISIBLE);
        } else{
            paymentMethod.setVisibility(View.VISIBLE);
            paymentHistory.setVisibility(View.VISIBLE);
            paymentHistoryButton.setVisibility(View.VISIBLE);
        }

        //TODO
        //Person clicks on the payment method tab, so go to the paymentMethod screen
        paymentMethod.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                new PaymentActivity(getActivity()).requestPayment();
            }
        });

        //TODO
//        /*
//        Person clicks on the payment method arrow, so go to the paymentMethod screen
//         */
//        paymentMethodButton.setOnClickListener(view1 -> {
//            Intent intent = new Intent(getActivity(), paymentMethod.class);
//            startActivity(intent);
//        });

        //TODO
//        /*
//        Person clicks on the payment history tab, so go to the paymentHistory screen
//         */
//        paymentHistory.setOnClickListener(view15 -> {
//            Intent intent = new Intent(getActivity(), paymentHistory.class);
//            startActivity(intent);
//        });

        //TODO
//        /*
//        Person clicks on the payment history arrow, so go to the paymentHistory screen
//         */
//        paymentHistoryButton.setOnClickListener(view112 -> {
//            Intent intent = new Intent(getActivity(), paymentHistory.class);
//            startActivity(intent);
//        });

        /*
        Goes to change password screen when the user clicks the change password tab
         */
        changePassword.setOnClickListener(view114 -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });

        /*
        if the first name clicks the username edit button then the focus changes to the first name text box
         */
        firstNameEditButton.setOnClickListener(view17 -> {
            firstName.setEnabled(true);
            firstName.requestFocus(firstName.getText().length());
        });

        /*
        if the user clicks the last name edit button then the focus changes to the last name text box
         */
        lastNameEditButton.setOnClickListener(view18 -> {
            lastName.setEnabled(true);
            lastName.requestFocus(lastName.getText().length());
        });

        /*
        if the user clicks the username edit button then the focus changes to the username text box
         */
        userNameEditButton.setOnClickListener(view19 -> {
            userName.setEnabled(true);
            userName.requestFocus(userName.getText().length());
        });

        /*
        if the user clicks the email edit button then the focus changes to the email text box
         */
        emailEditButton.setOnClickListener(view110 -> {
            email.setEnabled(true);
            email.requestFocus(email.getText().length());
        });

        /*
        If the first name text box loses focus and the user changed the info then show the submit changes button
         */
        firstName.setOnFocusChangeListener((view13, hasFocus) -> {
            //if the first name edit box loses focus and the user changes the first name from its original then show the submit changes button if not already set
            if(!hasFocus && !firstName.getText().toString().equals(MySingleton.mainUser.getFirstName()) && submitChanges.getVisibility() == View.INVISIBLE){
                submitChanges.setVisibility(View.VISIBLE);
            }
        });

        /*
        If the last name text box loses focus and the user changed the info then show the submit changes button
         */
        lastName.setOnFocusChangeListener((view14, hasFocus) -> {
            //if the lastName edit box loses focus and the user changes the last name from its original then show the submit changes button if not already set
            if(!hasFocus && !lastName.getText().toString().equals(MySingleton.mainUser.getLastName()) && submitChanges.getVisibility() == View.INVISIBLE){
                submitChanges.setVisibility(View.VISIBLE);
            }
        });

        /*
        If the username text box loses focus and the user changed the info then show the submit changes button
         */
        userName.setOnFocusChangeListener((view15, hasFocus) -> {
            //if the username edit box loses focus and the user changes the username from its original then show the submit changes button if not already set
            if(!hasFocus && !userName.getText().toString().equals(MySingleton.mainUser.getUserName()) && submitChanges.getVisibility() == View.INVISIBLE){
                submitChanges.setVisibility(View.VISIBLE);
            }
        });

        /*
        If the email text box loses focus and the user changed the info then show the submit changes button
         */
        email.setOnFocusChangeListener((view16, hasFocus) -> {
            //if the email edit box loses focus and the user changes the email from its original then show the submit changes button if not already set
            if(!hasFocus && !email.getText().toString().equals(MySingleton.mainUser.getEmail()) && submitChanges.getVisibility() == View.INVISIBLE){
                submitChanges.setVisibility(View.VISIBLE);
            }
        });

        /*
        Sends the updated user info the logged in user container
        Then user the homeViewModel to submit these changes to the backend
        Then hides the 'submit changes' button
         */
        submitChanges.setOnClickListener(view111 -> {
            //if the user put in a different first name then change it
            if(!firstName.getText().toString().equals(MySingleton.mainUser.getFirstName())){
                MySingleton.mainUser.setFirstName(firstName.getText().toString());
            }

            //if the user put in a different last name then change it
            if(!lastName.getText().toString().equals(MySingleton.mainUser.getLastName())){
                MySingleton.mainUser.setLastName(lastName.getText().toString());
            }

            //if the user put in a different email then change it
            if(!email.getText().toString().equals(MySingleton.mainUser.getEmail())){
                MySingleton.mainUser.setEmail(email.getText().toString());
            }

            updateUserInfoUsers();
            updateUserInfoCredentials();

            firstName.setEnabled(false);
            lastName.setEnabled(false);
            userName.setEnabled(false);
            email.setEnabled(false);

            //set the button back to invisible
            submitChanges.setVisibility(View.INVISIBLE);
        });

        /*
        Reduces the keyboard when the user clicks outside of the current text box they are in
        Keeps the focus on the current text box so it doesn't default back to the top
         */
        RelativeLayout touchInterceptor = Objects.requireNonNull(getActivity()).findViewById(R.id.account_fragment);
        touchInterceptor.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (firstName.isFocused()) {
                    Rect outRect = new Rect();
                    firstName.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                        firstName.clearFocus();
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                } else if (lastName.isFocused()) {
                    Rect outRect = new Rect();
                    lastName.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                        lastName.clearFocus();
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                } else if (email.isFocused()) {
                    Rect outRect = new Rect();
                    email.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                        email.clearFocus();
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                } else if (userName.isFocused()) {
                    Rect outRect = new Rect();
                    userName.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                        userName.clearFocus();
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
            return false;
        });
    }

    /**
     * @return the JSON file with all the information needed to update the user information
     */
    private JSONObject addJsonUsers(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("id", MySingleton.mainUser.getId());
            request.put("first name", firstName.toString().trim());
            request.put("last name", lastName.toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    /**
     * posts the json request to update the user information in the users table
     */
    private void updateUserInfoUsers(){
        JSONObject updateUsers = addJsonUsers();

        JsonObjectRequest jsonObjectRequestUsers = new JsonObjectRequest
            (Request.Method.POST, getResources().getString(R.string.update_user_users), updateUsers, response -> {
            //Check if user got registered successfully
            if (response.toString().equals("1")) {
                Toast.makeText(getContext(), "Updated Account!", Toast.LENGTH_SHORT).show();
                MySingleton.mainUser.setFirstName(firstName.toString().trim());
                MySingleton.mainUser.setLastName(lastName.toString().trim());
            } else {
                //Display error message whenever an error occurs
                Toast.makeText(getContext(), "Error Updating Account Users", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            //Display error message whenever an error occurs
            Toast.makeText(getContext(), "Error Updating Account Users", Toast.LENGTH_SHORT).show();
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequestUsers);
    }

    /**
     * @return the JSON object with all the information needed to update the user information
     */
    private JSONObject addJsonCredentials(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("id", MySingleton.mainUser.getId());
            request.put("username", MySingleton.mainUser.getUserName());
            request.put("email", email.toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    /**
     * posts the json request to update the user information in the credentials table
     */
    private void updateUserInfoCredentials(){
        JSONObject updateCredentials = addJsonCredentials();

        JsonObjectRequest jsonObjectRequestCredentials = new JsonObjectRequest
            (Request.Method.POST, getResources().getString(R.string.update_user_credentials), updateCredentials, response -> {
            //Check if user got registered successfully
            if (response.toString().equals("1")) {
                Toast.makeText(getContext(), "Updated Account!", Toast.LENGTH_SHORT).show();
                MySingleton.mainUser.setUserName(userName.toString().trim());
                MySingleton.mainUser.setEmail(email.toString().trim());
            } else {
                //Display error message whenever an error occurs
                Toast.makeText(getContext(), "Error Updating Account Credentials", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            //Display error message whenever an error occurs
            Toast.makeText(getContext(), "Error Updating Account Credentials", Toast.LENGTH_SHORT).show();
        });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequestCredentials);
    }

    /**
     * grabs the first name of the logged in user and places it in the account first name section
     */
    private void setFirstName(){
        firstName.setText(MySingleton.mainUser.getFirstName());
    }

    /**
     * grabs the last name of the logged in user and places it in the account last name section
     */
    private void setLastName(){
        lastName.setText(MySingleton.mainUser.getLastName());
    }

    /**
     * grabs the username of the logged in user and places it in the account username section
     */
    private void setUserName(){
        userName.setText(MySingleton.mainUser.getUserName());
    }

    /**
     * grabs the email of the logged in user and places it in the account email section
     */
    private void setEmail(){
        email.setText(MySingleton.mainUser.getEmail());
    }
}
