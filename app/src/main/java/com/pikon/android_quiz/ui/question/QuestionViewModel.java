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

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class QuestionViewModel extends ViewModel {

    private MutableLiveData<Question> mQuestion;
    private MutableLiveData<ArrayList<Answer>> mCheckedAnswers;
    private MutableLiveData<Quiz> mQuiz;

    private MutableLiveData<Instant> mStartTime;
    private MutableLiveData<Instant> mMaxTime;

    public QuestionViewModel() {
        mQuestion = new MutableLiveData<Question>();
        mCheckedAnswers = new MutableLiveData<ArrayList<Answer>>(){};
        mQuiz = new MutableLiveData<Quiz>();
        clearCheckedAnswers();

        mStartTime = new MutableLiveData<Instant>();
        mMaxTime = new MutableLiveData<Instant>();
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

    public void setQuestion( @Nullable Quiz q, Instant startTime, int maxDuration ) {
//        if( q != null )
            this.mQuestion.postValue(q.getNextQuestion());
//            this.mQuestion.setValue( q.getNextQuestion() );
            this.setStartTime( startTime, maxDuration );
//        else
//            this.mQuestion.setValue( null );
    }

    public boolean hasTimePassed(){
        if( mMaxTime.getValue() == null )
            return false;
        return Instant.now().isAfter( mMaxTime.getValue() );
    }

    public Duration getTimePassed(){
        if( mStartTime.getValue() != null )
            return Duration.between( mStartTime.getValue(), Instant.now() );
        else
            return null;
    }

    public void setStartTime(Instant startTime, int maxDuration) {
        this.mStartTime.postValue( startTime );
        Instant endTime = startTime.plus( maxDuration, ChronoUnit.SECONDS );
        this.mMaxTime.postValue( endTime );
    }

    public void setMaxTime(Instant maxTime) {
        this.mMaxTime.setValue( maxTime );
    }

    public MutableLiveData<Instant> getStartTime() {
        return mStartTime;
    }

    public MutableLiveData<Instant> getMaxTime() {
        return mMaxTime;
    }
}