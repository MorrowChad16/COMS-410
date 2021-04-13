package com.company1.gpasaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.company1.gpasaver.models.Course;
import com.company1.gpasaver.util.service.CourseAdapter2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AdditionalRegisterActivity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "name";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_EMPTY = "";
    private static final String TAG = "AddRegisterActivity";
    protected EditText etUniversity;
    protected EditText etRate;
    protected EditText etGpa;
    protected String university;
    protected String rate;
    protected String GPA;
    private RecyclerView rView;
    private TextView textView;
    private BootstrapButton register;
    private ImageButton backButton;

    CourseAdapter2 courseAdapter2;
    ArrayList<Course> coursesList;
    private boolean isStudent;
    private boolean isTutor;
    protected ProgressDialog pDialog;
    protected String registerCourse_url = "http://coms-510-01.cs.iastate.edu:80/registerCoursesToUser.php";
    //private SessionHandler session;
    int coursePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_additional_register);

        // gets the passed value from the RegisterActivity
        Intent intent = getIntent();
        isStudent = intent.getBooleanExtra("isStudent", false);
        isTutor = intent.getBooleanExtra("isTutor", false);

        textView = (TextView) findViewById(R.id.tvSelectCourse);
        etGpa = (EditText) findViewById(R.id.etGpa);
        etRate = (EditText) findViewById(R.id.etRate);

        setTextView();
        hideOrDisplayGpaField();


        // spinner for the drop down to select classes
        // We need to change this @TODO
        rView = findViewById(R.id.rView);

        courseAdapter2= new CourseAdapter2(new ArrayList<>());


         register = findViewById(R.id.btnAdditionalRegister);
        backButton = findViewById(R.id.btnRegisterLogin);

         register.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(validateInputs()){
                     displayLoader();
                     addCourseToBackend();

                 }

             }
         });

        // Takes user to main page
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        getCourses();

    }

    private List<Course> getCourses(){
        ArrayList<Course> list = new ArrayList<>();
        String url="http://coms-510-01.cs.iastate.edu:80/getCourses.php";
        Log.i(TAG, "getCourses: Is called");
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    String course_id="course_id";
                    String course_desc="course_desc";
                    String course_name="course_name";



                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, "onResponse: In on response");

                        try {

                            for(int i=0; i<response.length(); i++){
                                JSONObject obj= (JSONObject) response.get(i);
                                int id=obj.getInt(course_id);
                                String name =obj.getString(course_name);
                                String des=obj.getString(course_desc);

                                list.add(new Course(name,des,id));
                            }
                            courseAdapter2 = new CourseAdapter2(list);

                            rView.setAdapter(courseAdapter2);
                            rView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rView.setNestedScrollingEnabled(true);

                            Log.i(TAG, "onResponse: ELEMENT 1"+list.get(0).getCourse_name());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i(TAG, "onErrorResponse: "+error.getMessage());

                            }
                        }
                );
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


        return list;
    }

    private JSONObject getSelectedCourse(){
        JSONObject request = new JSONObject();

        ArrayList<Integer> clist= new ArrayList<>();
       int[] select= courseAdapter2.getSelectedCourse();

       for(int i=0; i<select.length; i++){
           if(select[i]==1){
              clist.add(courseAdapter2.getCourse(i).getCourseId());
           }
       }
        try {
            if(isTutor){
                request.put("istutor", 1);
            }else {
                request.put("istutor", 0);
            }
            request.put("gpa",GPA);
            request.put("rate",rate);
            request.put("courses",clist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return request;
    }

    // @TODO make all the user response to a json object
    private  void addCourseToBackend(){
        JSONObject request=getSelectedCourse();
        Log.i(TAG, "addCourseToBackend: "+request.toString());
        JsonObjectRequest
                jsonObjReq
                = new JsonObjectRequest(
                Request.Method.POST,
                registerCourse_url,
                request,
                new Response.Listener() {

                    @Override
                    public void onResponse(Object response) {

                        pDialog.hide();
                        Toast.makeText(getApplicationContext(),
                                "Registration Successful", Toast.LENGTH_SHORT).show();

                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.i(TAG, "onErrorResponse: "+error.toString());
                        VolleyLog.d(TAG, "Error: "
                                + error.getMessage());
                        pDialog.hide();
                    }
                }) {


        };

        MySingleton.getInstance(this).addToRequestQueue(jsonObjReq);
    }

    /**
     * This is to hide or display
     * the Gpa field base on if
     * the user is a tutor or student
     */
    private void hideOrDisplayGpaField() {
        if (isTutor) {
            etGpa.setVisibility(View.VISIBLE);
            etRate.setVisibility(View.VISIBLE);

        }
        else{
            etGpa.setVisibility(View.GONE);
            etRate.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * This is to change the textView base on if the user
     * is a tutor or student
     */
    private void setTextView() {
        if (isTutor) {
            textView.setText("Select the course you want to tutor");
        } else {
            textView.setText("Select the course you need a tutor for");
        }
    }


    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(com.company1.gpasaver.AdditionalRegisterActivity.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     * @TODO
     */


    /**
     * Validates inputs and shows error if any
     * @return
     */

    protected boolean validateInputs() {
        if (KEY_EMPTY.equals(university)) {
            etUniversity.setError("University cannot be empty");
            etUniversity.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(rate)) {
            etRate.setError("Major cannot be empty");
            etRate.requestFocus();
            return false;
        }
        if(isTutor){
            if (KEY_EMPTY.equals(GPA)) {
                etGpa.setError("GPA cannot be empty");
                etGpa.requestFocus();
                return false;
            }
        }
        return true;
    }


}