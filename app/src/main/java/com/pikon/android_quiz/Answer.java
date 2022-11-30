package com.pikon.android_quiz;

import java.io.Serializable;

public class Answer implements Serializable {
    private String text;
    private boolean correct;
    private boolean answered = false;

    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public boolean answer(){
        this.answered = true;
        return this.correct;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getText() {
        return text;
    }

    public boolean isAnswered() {
        return answered;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "text='" + text + '\'' +
                ", correct=" + correct +
                ", answered=" + answered +
                '}';
    }
}
