package com.example.alex.myapplication;

import java.io.Serializable;

/**
 * Created by Oombliocarius on 3/7/16.
 */
public class SubmissionData implements Serializable {
    //Auto


    String data = new String();


    public SubmissionData() {



    }

    public SubmissionData(String s){

        this.data = s;
    }

    public String getData(){
        return this.data;
    }





}