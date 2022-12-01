package com.pikon.android_quiz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Question implements Serializable {
    private String text;
    private ArrayList<Answer> answers;
    private AnswerResult result = AnswerResult.INCORRECT;

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
            this.result = AnswerResult.MIXED;
        else if( hasFalse && !hasTrue )
            this.result = AnswerResult.INCORRECT;
        else if( hasTrue && !hasFalse )
            this.result = AnswerResult.CORRECT;
    }

    public void shuffleAnswers(){
        Collections.shuffle( answers );
    }

    public String getText() {
        return text;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public AnswerResult getResult() {
        return result;
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
