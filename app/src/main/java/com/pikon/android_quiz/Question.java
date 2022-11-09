package com.pikon.android_quiz;

import java.util.ArrayList;

public class Question {
    private String text;
    private ArrayList<Answer> answers;
    private int result;

    public Question(String question, ArrayList<Answer> answers) {
        this.text = question;
        this.answers = answers;
    }

    public void answer( Answer[] answers ){
        ArrayList<Boolean> results = new ArrayList<>();
        for( Answer a : answers ){
            if( this.answers.contains(a) ){
                a.answer();
                results.add( a.isCorrect() );
            }
        }
        boolean hasFalse = results.contains(false);
        boolean hasTrue = results.contains(true);
        if( hasTrue && hasFalse )
            this.result = 50;
        else if( hasFalse && !hasTrue )
            this.result = -100;
        else if( hasTrue && !hasFalse )
            this.result = 100;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", answers=" + answers +
                ", result=" + result +
                '}';
    }
}
