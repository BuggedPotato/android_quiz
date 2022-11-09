package com.pikon.android_quiz;

import java.util.ArrayList;

public class Quiz {
    private ArrayList<Question> questions;

    public Quiz(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Quiz() {
        this.questions = new ArrayList<>();
    }

    public void addQuestion( Question q ){
        this.questions.add( q );
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
