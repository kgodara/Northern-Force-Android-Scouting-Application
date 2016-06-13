package com.example.alex.myapplication;

import java.io.Serializable;

/**
 * Created by alex on 4/18/15.
 */
public class ConfigEntry implements Serializable{

    private String type;
    private String colName;
    private String text;
    private String options;

    public ConfigEntry(){

    }

    public ConfigEntry(String type, String colName, String text, int table){
        this.type = type;
        this.colName = colName;
        this.text = text;
    }

    public ConfigEntry(String type, String colName, String text, String options){
        this.type = type;
        this.colName = colName;
        this.text = text;
        this.options = options;
    }

    public ConfigEntry(String type, String colName){
        this.type = type;
        this.colName = colName;
       

    }

    public ConfigEntry(String type, String colName, String text, String options,  int table){
        this.type = type;
        this.colName = colName;
        this.text = text;
        this.options = options;
    }
    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }


}
