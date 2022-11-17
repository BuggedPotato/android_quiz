package com.pikon.android_quiz.ui.result;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pikon.android_quiz.Quiz;

public class ResultViewModel extends ViewModel {

    private MutableLiveData<Quiz> mQuiz;

    public ResultViewModel(){
    }

    public MutableLiveData<Quiz> getQuiz() {
        return mQuiz;
    }

    public void setQuiz(Quiz quiz) {
        this.mQuiz.setValue( quiz );
    }
}
