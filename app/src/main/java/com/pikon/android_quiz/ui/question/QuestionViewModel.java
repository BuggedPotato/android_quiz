package com.pikon.android_quiz.ui.question;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pikon.android_quiz.Answer;
import com.pikon.android_quiz.Question;
import com.pikon.android_quiz.Quiz;
import com.pikon.android_quiz.QuizFileReader;

import java.util.ArrayList;

public class QuestionViewModel extends ViewModel {

    private MutableLiveData<Question> mQuestion;
    private MutableLiveData<ArrayList<Answer>> mCheckedAnswers;
    private MutableLiveData<Quiz> mQuiz;

    public QuestionViewModel() {
        mQuestion = new MutableLiveData<Question>();
        mCheckedAnswers = new MutableLiveData<ArrayList<Answer>>(){};
        mQuiz = new MutableLiveData<Quiz>();
        clearCheckedAnswers();
    }

    public LiveData<Question> getQuestion() {
        return mQuestion;
    }

    public MutableLiveData<ArrayList<Answer>> getCheckedAnswers() {
        return mCheckedAnswers;
    }

    public void setCheckedAnswers(ArrayList<Answer> arr) {
        this.mCheckedAnswers.setValue( arr );
    }

    public void onCheckAnswer( Answer a, boolean b ){
        ArrayList<Answer> foo = mCheckedAnswers.getValue();
         if( b ) foo.add( a );
         else foo.remove( a );
        mCheckedAnswers.setValue( foo );
    }

    public void clearCheckedAnswers(){
        this.mCheckedAnswers.setValue( new ArrayList<>() );
    }

    public MutableLiveData<Quiz> getQuiz() {
        return mQuiz;
    }

    public void setQuiz( Context context, Uri uri ) {
        this.mQuiz.setValue( QuizFileReader.getFileData( context, uri ) );
    }
    
    public void setQuiz(Quiz quiz) {
        this.mQuiz.setValue( quiz );
    }

    public void setQuestion( @Nullable Quiz q ) {
        if( q != null )
            this.mQuestion.setValue( q.getNextQuestion() );
        else
            this.mQuestion.setValue( null );
    }
}