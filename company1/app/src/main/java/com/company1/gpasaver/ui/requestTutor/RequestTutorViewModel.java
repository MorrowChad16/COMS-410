package com.company1.gpasaver.ui.requestTutor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RequestTutorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RequestTutorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}