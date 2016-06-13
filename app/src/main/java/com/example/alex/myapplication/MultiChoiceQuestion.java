package com.example.alex.myapplication;

import java.io.Serializable;

/**
 * Created by Oombliocarius on 4/26/16.
 */
public class MultiChoiceQuestion implements Serializable, Question {

    String[] buttonLabels;
    String question;
    int id;

    public String[] getButtonLabels() {
        return buttonLabels;
    }

    public void setButtonLabels(String[] buttonLabels) {
        this.buttonLabels = buttonLabels;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public MultiChoiceQuestion(String request, String[] labels, int i) {

        id = i;
        question = request;
        buttonLabels = labels;

    }

    public void setID(int i) {
        id = i;
    }

    public int getID() {
        return id;
    }



}
