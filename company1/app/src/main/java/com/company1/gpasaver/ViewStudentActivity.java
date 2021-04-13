package com.company1.gpasaver;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.company1.gpasaver.models.User;
import com.company1.gpasaver.ui.UserView.UserListViewAdapter;
import com.company1.gpasaver.ui.viewStudent.Student;
import com.company1.gpasaver.ui.viewStudent.ViewStudentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ViewStudentActivity extends Fragment {
    static ArrayAdapter<String> dataAdapter;
    protected String email;
    protected ProgressDialog pDialog;
    Spinner spinnerView;
    protected ArrayList<User> user = new ArrayList<>();
    protected UserListViewAdapter arrayAdapter;
    protected ListView listView;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Student> students;
    private TextView noStudentView;
    private boolean textVisibility = false;


    private View root;
    private View popup;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        students = new ArrayList<>();

        TypefaceProvider.registerDefaultIconSets();
        root = inflater.inflate(R.layout.activity_view_student, container, false);
        popup = inflater.inflate(R.layout.layout_viewstudent, container, false);

        initStudentList();

        noStudentView = (TextView) root.findViewById(R.id.viewStudentTextView);
        spinnerView = (Spinner) root.findViewById(R.id.spinnerCourse);
        BootstrapButton refresh = root.findViewById(R.id.btnRefresh);

        return root;
    }

    private void initViewStudentView(){
        textVisibility = false;
        StudentTextView(textVisibility);
        recyclerView = (RecyclerView) root.findViewById(R.id.viewStudentRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ViewStudentAdapter(students, getContext(), getView());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    public void initStudentList () {
        int tutorID;
        if(MySingleton.mainUser == null){
            tutorID = 1;
        }else {
            tutorID = MySingleton.mainUser.getId();
        }

        String baseURL = "http://coms-510-01.cs.iastate.edu:80/tutor_showAllStudent.php?tutor_id=";
        String URL = baseURL + tutorID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray newArray = response;
                            for(int i = 0; i < newArray.length(); i++){
                                JSONObject student = newArray.getJSONObject(i);
                                String id = student.getString("id");
                                String name = student.getString("full_name");
                                String className = student.getString("school");
                                String email = student.getString("dept");
                                String picture = student.getString("image_url");
                                String number = student.getString("phone");
                                Student newStudent = new Student(id, name, className, email, picture, number);
                                students.add(newStudent);
                            }
                            initViewStudentView();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textVisibility = true;
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(getContext()).addToRequestQueue(request);
    }
    public void StudentTextView  (boolean visibility){
        if (visibility){
            noStudentView.setVisibility(View.VISIBLE);
        }else {
            noStudentView.setVisibility(View.INVISIBLE);
        }
    }
}