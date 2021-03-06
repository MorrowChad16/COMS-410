package com.company1.gpasaver.ui.review;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReviewViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public ReviewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Review");
    }

    // LiveData<> Is "Observed" in the fragment.
    public LiveData<String> getText() {
        return mText;
    }
}
