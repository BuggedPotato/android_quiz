package com.pikon.android_quiz.ui.home;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pikon.android_quiz.Quiz;
import com.pikon.android_quiz.QuizFileReader;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<Quiz> mQuiz;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue( "This is home fragment" );
        mQuiz = new MutableLiveData<Quiz>();
    }

    public LiveData<Quiz> getQuiz() {
        return mQuiz;
    }

    public void setQuiz( Context context, Uri uri ) {
        this.mQuiz.setValue( QuizFileReader.getFileData( context, uri ) );
    }

    public LiveData<String> getText() {
        return mText;
    }
}