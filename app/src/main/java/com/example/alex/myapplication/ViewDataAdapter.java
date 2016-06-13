package com.example.alex.myapplication;

import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by alex on 8/11/15.
 */
public class ViewDataAdapter extends BaseAdapter {

    private MySQLiteHelper mySQLiteHelper;
    private int count;
    private ViewDataActivity viewDataActivity;
    private static String currentTable;

    private static String searchedTeam;

    public ViewDataAdapter(MySQLiteHelper mySQLiteHelper, ViewDataActivity viewDataActivity){
        this.currentTable = UIDatabaseInterface.getCurrentDataViewTable();
        this.mySQLiteHelper = mySQLiteHelper;




        this.viewDataActivity = viewDataActivity;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(viewDataActivity);
        view.setText("");

        Cursor cursor;
        if(searchedTeam != null){
            cursor = mySQLiteHelper.selectFromTableWhere("*", "Performance", "Team_Number = " + searchedTeam);
            this.count = cursor.getCount();
        }
        else{
            cursor = mySQLiteHelper.selectFromTable("*", "Performance");
            this.count = cursor.getCount();
        }

        if(position >= this.getCount()){
            return new TextView(viewDataActivity);
        }
        cursor.moveToPosition(position);

        String[] fullNames = UIDatabaseInterface.getFullNameForTable("Performance");
        //i = 1 to skip _id column
        for (int i = 1; i < cursor.getColumnCount(); i++) {

            view.setText(view.getText() + "\n" + fullNames[i - 1] + ": " + cursor.getString(i));

        }

        return view;
    }

    public static String getSearchedTeam() {
        return searchedTeam;
    }

    public static void setSearchedTeam(String team) {
        searchedTeam = team;
    }

}

