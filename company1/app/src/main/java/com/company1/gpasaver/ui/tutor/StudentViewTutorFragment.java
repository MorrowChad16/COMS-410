package com.company1.gpasaver.ui.tutor;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.company1.gpasaver.R;

public class StudentViewTutorFragment extends Fragment implements View.OnClickListener {
    private ImageButton searchTutor;
    private ImageButton viewTutor;
    private ImageButton requestTutor;
    private Context context;
    private String TAG = "StudentViewTutorFragment";
    private StudentViewTutorFragmentViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tutor_new, container, false);
        mViewModel = ViewModelProviders.of(this).get(StudentViewTutorFragmentViewModel.class);
        context = getActivity().getApplicationContext();
        searchTutor = view.findViewById(R.id.search_tutor_button);
        viewTutor = view.findViewById(R.id.view_tutor_button);
        requestTutor = view.findViewById(R.id.request_tutor_button);
        searchTutor.setOnClickListener(this::onClick);
        viewTutor.setOnClickListener(this::onClick);
        requestTutor.setOnClickListener(this::onClick);
        return view;
    }

    @Override
    public void onClick(View v) {
        NavController navController = Navigation.findNavController(v);
        switch (v.getId()){
            case R.id.search_tutor_button:
                //Toast.makeText(context,"Clicked on Search Tutor",Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_nav_tutor_to_nav_search_tutor);
                break;
            case R.id.view_tutor_button:
                //Toast.makeText(context,"Clicked on View Tutor",Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_nav_tutor_to_nav_view_tutor);
                break;
            case R.id.request_tutor_button:
                //Toast.makeText(context,"Clicked on Request Tutor",Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_nav_tutor_to_nav_request_tutor);
                break;
            default:
                break;
        }
    }
}