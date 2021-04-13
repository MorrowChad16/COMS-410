package com.company1.gpasaver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.company1.gpasaver.models.User;
import com.company1.gpasaver.ui.UserView.UserListViewAdapter;
import com.company1.gpasaver.ui.profile.ProfileView_Real;
import com.company1.gpasaver.ui.tutor.TutorFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class current_tutors extends Fragment {
    static ArrayAdapter<String> dataAdapter;
    protected String email;
    protected ProgressDialog pDialog;
    Spinner spinnerView;
    protected ArrayList<User> user = new ArrayList<>();
    protected UserListViewAdapter arrayAdapter;
    protected ListView listView;


    protected String users_list = "http://coms-510-01.cs.iastate.edu:80/get_tutor.php";
    protected String studentTutor = "http://coms-510-01.cs.iastate.edu:80/get_tutor_course.php";
    private String studentTutor_list= "";
    private View root;
    private View popup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TypefaceProvider.registerDefaultIconSets();
        root = inflater.inflate(R.layout.activity_current_tutors, container, false);
        popup = inflater.inflate(R.layout.popup_current_tutor, container, false);


        listView = (ListView) root.findViewById(R.id.listview);
        spinnerView = (Spinner) root.findViewById(R.id.spinnerCourse);
        downloadJSON("http://coms-510-01.cs.iastate.edu/courseRequest.php");
        arrayAdapter = new UserListViewAdapter(getContext(), R.layout.item_tutor, user);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onButtonShowPopupWindowClick(view, user,  i);
//                // First get the user
//                User tutor = user.get(i);
//                System.out.println("x");
//                // send to dialog
//                Intent startIntent = new Intent(getContext(), ProfileView_Real.class);
//                startIntent.putExtra("serialize_tutor", tutor);
//                startActivity(startIntent);
            }
        });

        BootstrapButton refresh = root.findViewById(R.id.btnRefresh);


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_users();
            }
        });
        get_users();
        return root;
    }

    public void onButtonShowPopupWindowClick(View view, ArrayList<User> user, int i) {

        // inflate the layout of the popup window

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popup, width, height, focusable);
        Button cancel = popup.findViewById(R.id.current_tutors_cancel);
        Button remove = popup.findViewById(R.id.current_tutors_remove);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        TextView popupMessage = popup.findViewById(R.id.pop_up_message);
        popupMessage.setText("Would you like to cancel tutoring services with:\n\n "+user.get(i).firstName+"\n");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveData(MySingleton.mainUser.getId(), user.get(i).getTutorid(), user.get(i).course_id);
                get_users();
                popupWindow.dismiss();
            }
        });
    }


    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Getting users.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void get_users() {
        downloadJSONStudentTutor(studentTutor);
        displayLoader();
        downloadJSON1(users_list);
    }

    private void downloadJSON(final String urlWebService) {
        class DownloadJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadintoSpinnerView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int a = con.getResponseCode();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");

                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    private void loadintoSpinnerView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        TutorFragment.courses = new String[jsonArray.length() + 1];
        TutorFragment.courses[0] = "Show All";
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            TutorFragment.courses[i + 1] = obj.getString("course_name");
        }
        dataAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, TutorFragment.courses);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinnerView.setAdapter(dataAdapter);
    }

    private void downloadJSON1(final String urlWebService) {
        class DownloadJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {

                    loadintoUser(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int a = con.getResponseCode();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    private void downloadJSONStudentTutor(final String urlWebService) {
        class DownloadJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
                studentTutor_list = s;
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int a = con.getResponseCode();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    private void loadintoUser(String json) throws JSONException {
        user.clear();
        String class_name = spinnerView.getSelectedItem().toString();
        JSONArray jsonArray = new JSONArray(json);
        TutorFragment.courses = new String[jsonArray.length()];
        JSONArray studentTutorJson = new JSONArray(studentTutor_list);
        for(int j = 0; j < studentTutorJson.length(); j++) {
            int tutor_id = 0;
            int course_id = 0;
            JSONObject objectST = studentTutorJson.getJSONObject(j);
            if(objectST.getInt("user_id")== MySingleton.mainUser.getId()) {
                tutor_id = objectST.getInt("tutor_id");
                course_id = objectST.getInt("course_id");
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                if (object.getInt("id") == tutor_id && object.getInt("course_id") == course_id) {
                    if (class_name.equals("Show All")) {
                        user.add(new User()
                                .setFirstName(object.getString("full_name"))
                                .setEmail(object.getString("email"))
                                .setPhoneNumber(object.getString("phone"))
                                .addSubjectWillTutor(object.getString("course_name"))
                                .setPicture(object.getString("image_url"))
                                .setGPA(object.getString("GPA"))
                                .setRate(object.getString("rate"))
                                .setCourse(object.getInt("course_id"))
                                .setTutorid(object.getInt("id"))
                        );

                    } else {
                        if (object.getString("course_name").equals(class_name)) {
                            user.add(new User()
                                    .setFirstName(object.getString("full_name"))
                                    .setEmail(object.getString("email"))
                                    .setPhoneNumber(object.getString("phone"))
                                    .addSubjectWillTutor(object.getString("course_name"))
                                    .setPicture(object.getString("image_url"))
                                    .setGPA(object.getString("GPA"))
                                    .setRate(object.getString("rate"))
                                    .setCourse(object.getInt("course_id"))
                                    .setTutorid(object.getInt("id"))
                            );
                        }
                    }
                }
            }
        }
        pDialog.dismiss();
        arrayAdapter.notifyDataSetInvalidated();
        listView.invalidateViews();
    }


    private void RemoveData(int user, int tutor, int course) {
        class RemoveData extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    String databaseLink = "http://coms-510-01.cs.iastate.edu/delink_studentAsTutor.php?" +
                            "user_id=" + user +
                            "&tutor_id=" + tutor +
                            "&course_id=" + course;
                    URL url = new URL(databaseLink);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//                    wr.write(data);
                    wr.flush();
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
        RemoveData insertDataObj = new RemoveData();
        insertDataObj.execute();
    }
}