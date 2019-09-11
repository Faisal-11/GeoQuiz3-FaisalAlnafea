package com.bignerdranch.android.GeoQuiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


import static android.widget.Toast.makeText;

public class QuizActivity extends AppCompatActivity {
    public static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWERED = "answered";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private ArrayList<Integer> mAnsweredQuestions = new ArrayList<>();

    private Question[] mQuestionBank = new Question[]
            {
                    new Question(R.string.question_australia, true),
                    new Question(R.string.question_oceans, true),
                    new Question(R.string.question_mideast, false),
                    new Question(R.string.question_africa, false),
                    new Question(R.string.question_americas, true),
                    new Question(R.string.question_asia, true),
            };
    private int mCurrentIndex = 0;
    private int mNumberOfCorrectAnswers = 0;
    private int mNumberOfIncorrectAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);
        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mAnsweredQuestions = savedInstanceState.getIntegerArrayList(KEY_ANSWERED);
        }


        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);


        // True button listener
        mTrueButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTrueButton.setClickable(false);
                    mFalseButton.setClickable(false);
                    checkAnswer(true);
                    mAnsweredQuestions.add(mCurrentIndex);
                }
            }
        );

        // False button listener
        mFalseButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTrueButton.setClickable(false);
                    mFalseButton.setClickable(false);
                    checkAnswer(false);
                    mAnsweredQuestions.add(mCurrentIndex);
                }
            }
        );

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    updateQuestion();
                }
            }
        );
        updateQuestion();
    }



    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putIntegerArrayList(KEY_ANSWERED, mAnsweredQuestions );

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        if(mAnsweredQuestions.contains(mCurrentIndex)) {
            mTrueButton.setClickable(false);
            mFalseButton.setClickable(false);
        } else {
            mTrueButton.setClickable(true);
            mFalseButton.setClickable(true);
        }
    }

    private void checkAnswer(Boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if(userPressedTrue == answerIsTrue) {
            mNumberOfCorrectAnswers +=1;
            messageResId = R.string.correct_toast;
        } else {
            mNumberOfIncorrectAnswers +=1;
            messageResId = R.string.incorrect_toast;

        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        if((mNumberOfCorrectAnswers + mNumberOfIncorrectAnswers) == mQuestionBank.length){
            Toast.makeText(
                    QuizActivity.this,
                    getString(R.string.number_of_correct_answers) + Integer.toString(mNumberOfCorrectAnswers) + "\n" +
                    getString(R.string.number_of_incorrect_answers) + Integer.toString(mNumberOfIncorrectAnswers) + "\n" ,
                    Toast.LENGTH_LONG).show();
        }


    }


}

