package com.pikon.android_quiz;

import android.net.Uri;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {
    private final ArrayList<Question> questions;
    private int currentQuestion = -1;
//    private Uri uri;
    private String uri;

    public Quiz( ArrayList<Question> questions ) {
        this.questions = questions;
    }

    public Quiz( String uri ) {
        this.questions = new ArrayList<>();
        this.uri = uri;
    }

    public Quiz(){
        this.questions = new ArrayList<>();
    };

    public void addQuestion( Question q ) {
        this.questions.add( q );
    }

    @Nullable
    public Question getNextQuestion() {
        if( currentQuestion + 1 >= questions.size() )
            return null;
        return questions.get( ++currentQuestion );
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public String getUri() {
        return uri;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public int getCorrectAnswersCount(){
        return this.questions.stream().filter( (question) -> question.getResult() == AnswerResult.CORRECT ).toArray().length;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "questions=" + questions +
                '}';
    }
}
