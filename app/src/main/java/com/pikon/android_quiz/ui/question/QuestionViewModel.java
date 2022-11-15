package com.pikon.android_quiz.ui.question;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pikon.android_quiz.Question;
import com.pikon.android_quiz.Quiz;
import com.pikon.android_quiz.QuizFileReader;

public class QuestionViewModel extends ViewModel {

    private MutableLiveData<Question> mQuestion;

    public QuestionViewModel() {
        mQuestion = new MutableLiveData<Question>();
    }

    public LiveData<Question> getQuestion() {
        return mQuestion;
    }

    public void setQuestion( Quiz q ) {
        this.mQuestion.setValue( q.getNextQuestion() );
    }
}