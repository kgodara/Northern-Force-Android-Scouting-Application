package com.example.alex.myapplication;

/**
 * Created by Alex on 2/24/2015.
 */
public class TeamTable {

    String _id;
    String _team_number;
    int _averageScore;

    public TeamTable(){

    }

    public TeamTable(String id, String teamNumber, int averageScore){
        this._id = id;
        this._team_number = teamNumber;
        this._averageScore = averageScore;
    }

    // constructor
    public TeamTable(String teamNumber, int averageScore){
        this._team_number = teamNumber;
        this._averageScore = averageScore;
    }

    public String getID(){
        return this._id;
    }


    public void setID(String id){
        this._id = id;
    }

    public String getTeamNumber(){
        return this._team_number;
    }

    public void setTeamNumber(String teamNumber){
        this._team_number = teamNumber;
    }

    public int getAverageScore(){
        return this._averageScore;
    }

    public void setAverageScore(int averageScore){
        this._averageScore = averageScore;
    }
}
