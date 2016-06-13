package com.example.alex.myapplication;

import java.io.Serializable;

/**
 * Created by Oombliocarius on 4/26/16.
 */
public class BoolQuestion implements Serializable, Question {

    String question;
    String trueText;
    String falseText;
    int id = 0;


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTrueText() {
        return trueText;
    }

    public void setTrueText(String trueText) {
        this.trueText = trueText;
    }

    public String getFalseText() {
        return falseText;
    }

    public void setFalseText(String falseText) {
        this.falseText = falseText;
    }


    public BoolQuestion(String content, String wrong, String right, int i) {

        id = i;
        question = content;
        trueText = right;
        falseText = wrong;


    }

    public void setID(int i) {
        id = i;
    }

    public int getID() {
      return id;
    }


}
