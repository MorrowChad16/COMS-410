package com.company1.gpasaver.ui.requests;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.company1.gpasaver.MySingleton;
import com.company1.gpasaver.R;
import com.company1.gpasaver.base.AppConstants;
import com.company1.gpasaver.models.TutorRequest;
import com.company1.gpasaver.models.User;
import com.company1.gpasaver.ui.paymenthistory.PaymentHistoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class RequestsFragment extends Fragment {
    private RequestsAdapter adapter;
    private RecyclerView request_list_view;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_requests, container, false);

        Log.d("RequestsFragment", "Created RequestsFragment view.");

        request_list_view = root.findViewById(R.id.request_list);
        adapter = new RequestsAdapter(root.getContext(), request_list_view);

        request_list_view.setAdapter(adapter);
        return root;
    }
}