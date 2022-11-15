package com.pikon.android_quiz;

import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Quiz {
    private final ArrayList<Question> questions;
    private int currentQuestion = -1;
    private Uri uri;

    public Quiz( ArrayList<Question> questions ) {
        this.questions = questions;
    }

    public Quiz( Uri uri ) {
        this.questions = new ArrayList<>();
        this.uri = uri;
    }

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

    public Uri getUri() {
        return uri;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "questions=" + questions +
                '}';
    }
}
