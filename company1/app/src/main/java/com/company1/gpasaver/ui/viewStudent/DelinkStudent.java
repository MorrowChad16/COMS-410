package com.company1.gpasaver.ui.viewStudent;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.company1.gpasaver.MySingleton;

import org.json.JSONArray;

public class DelinkStudent {
    private String student_id;
    private String tutor_id;
    private String statusOfDelink;
    public DelinkStudent(String student_id, String tutor_id){
        this.student_id = student_id;
        this.tutor_id = tutor_id;
    }

    public String delinkStudent (Context context) {
        String student_Url = "http://coms-510-01.cs.iastate.edu:80/delink_studentAsTutor.php?tutor_id=";
        String tutor_URL = "&user_id=";
        String URL = student_Url + student_id  + tutor_URL + tutor_id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        statusOfDelink = "Worked";
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                statusOfDelink = "Failed";
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
        return statusOfDelink;
    }
}
