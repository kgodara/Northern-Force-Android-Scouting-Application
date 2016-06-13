package com.example.alex.myapplication;

import java.io.Serializable;

/**
 * Created by Oombliocarius on 4/26/16.
 */
public class TextFieldQuestion implements Serializable, Question {
    String hint;
    String question;
    int id;

    public String getQuestion() {
        return question;
    }


    public void setQuestion(String question) {
        this.question = question;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }



    public TextFieldQuestion(String suggestion, String content, int i) {

        id = i;
        hint = suggestion;
        question = content;

    }

    public void setID(int i) {
        id = i;
    }

    public int getID() {
        return id;
    }










}
