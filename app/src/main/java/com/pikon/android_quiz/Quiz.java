package com.pikon.android_quiz;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Quiz implements Serializable {
    private final ArrayList<Question> questions;
    private ArrayList<Question> questionsToRepeat;
    private int currentQuestion = -1;
    private String uri;
    private float pointsPer = 1;
    private CountPointsFor countPointsFor;

    public Quiz( ArrayList<Question> questions ) {
        this.questions = questions;
    }

    public Quiz( String uri ) {
        this.questions = new ArrayList<>();
        this.uri = uri;
    }

    public Quiz( String uri, int pointsPer, CountPointsFor countPointsFor ) {
        this.questions = new ArrayList<>();
        this.uri = uri;
        this.pointsPer = pointsPer;
        this.countPointsFor = countPointsFor;
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

    public void shuffleQuestions(){
        Collections.shuffle( questions );
    }

    public void shuffleAnswers(){
        for ( Question q : questions ){
            q.shuffleAnswers();
        }
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

    public int getAnsweredOfResultCount( AnswerResult result ){
        int sum = 0;
        for( Question q : questions ){
            if( q.getResult() == result )
                sum++;
        }
        return sum;
    }

    public float getScore(){
        float sum = 0;
        if( countPointsFor == CountPointsFor.ANSWER ){
            for( Question q : questions ){
                for( Answer a : q.getAnswers() ){
                    sum += (a.isCorrect() && a.isAnswered()) ? pointsPer : 0;
                }
            }
        }
        else if( countPointsFor == CountPointsFor.QUESTION ){
            for( Question q : questions ){
                float add = 0;
                if( q.getResult() == AnswerResult.CORRECT )
                    add = pointsPer;
                else if ( q.getResult() == AnswerResult.MIXED )
                    add = pointsPer / 2;
                sum += add;
            }
        }
        return sum;
    }

    public float getTotalScore(){
        float sum = 0;
        if( countPointsFor == CountPointsFor.ANSWER ){
            for( Question q : questions ){
                for( Answer a : q.getAnswers() ){
                    sum += a.isCorrect() ? pointsPer : 0;
                }
            }
        }
        else if( countPointsFor == CountPointsFor.QUESTION ){
            sum = questions.size() * pointsPer;
        }
        return sum;
    }

    public void setPointsPer(int pointsPer) {
        this.pointsPer = pointsPer;
    }

    public void setCountPointsFor(CountPointsFor countPointsFor) {
        this.countPointsFor = countPointsFor;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "questions=" + questions +
                '}';
    }
}
