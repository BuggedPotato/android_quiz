package com.pikon.android_quiz.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pikon.android_quiz.Quiz;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Quiz> mQuiz;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue( "This is home fragment" );

        mQuiz = new MutableLiveData<>();
        // TODO
    }

    public MutableLiveData<Quiz> getQuiz() {
        return mQuiz;
    }

    public LiveData<String> getText() {
        return mText;
    }
}