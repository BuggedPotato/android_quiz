package com.pikon.android_quiz.ui.result;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pikon.android_quiz.Quiz;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ResultViewModel extends ViewModel {

    private MutableLiveData<Quiz> mQuiz;

    public ResultViewModel() {
        mQuiz = new MutableLiveData<Quiz>();
        mQuiz.setValue( new Quiz() );
    }

    public MutableLiveData<Quiz> getQuiz() {
        return mQuiz;
    }

    public void setQuiz( Quiz quiz ) {
        this.mQuiz.setValue( quiz );
    }
}
