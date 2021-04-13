package com.company1.gpasaver.ui.tutor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StudentViewTutorFragmentViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public StudentViewTutorFragmentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Tutor fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}