package com.company1.gpasaver.ui.review;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.company1.gpasaver.R;

public class ReviewActivity extends AppCompatActivity{
    RatingBar mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
    TextView mRatingScale = (TextView) findViewById(R.id.textReview);
    EditText mFeedback = (EditText) findViewById(R.id.editFeedback);
    Button mSendFeedback = (Button) findViewById(R.id.btnSubmit);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //rating scale changes with rating bar changing
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b){
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Awful");
                        break;
                    case 2:
                        mRatingScale.setText("Poor");
                        break;
                    case 3:
                        mRatingScale.setText("Average");
                        break;
                    case 4:
                        mRatingScale.setText("Good");
                        break;
                    case 5:
                        mRatingScale.setText("Awesome");
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });

        //send feedback
        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFeedback.getText().toString().isEmpty()) { //EditText is empty
                    Toast.makeText(ReviewActivity.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                } else {
                    mFeedback.setText("");
                    mRatingBar.setRating(0);
                    Toast.makeText(ReviewActivity.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                    finish(); //close activity after submitting feedback
                }
            }
        });
    }
}
