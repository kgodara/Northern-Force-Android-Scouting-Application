package com.example.alex.myapplication;

import java.io.Serializable;

/**
 * Created by Oombliocarius on 4/26/16.
 */
public class NumOnlyQuestion implements Serializable, Question {

    String question;
    int lowBound;
    int highBound;
    int id;

    public int getHighBound() {
        return highBound;
    }

    public void setHighBound(int highBound) {
        this.highBound = highBound;
    }

    public int getLowBound() {
        return lowBound;
    }

    public void setLowBound(int lowBound) {
        this.lowBound = lowBound;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public NumOnlyQuestion(String content, int low, int high, int i) {

        id = i;
        question = content;
        lowBound = low;
        highBound = high;

    }

    public void setID(int i) {
        id = i;
    }

    public int getID() {
        return id;
    }


}
