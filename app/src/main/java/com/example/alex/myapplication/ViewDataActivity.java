package com.example.alex.myapplication;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alex on 3/9/15.
 */
public class ViewDataActivity extends ActionBarActivity {

    private CursorAdapter dataSource;
    private MySQLiteHelper mySQLiteHelper;
    private static UIDatabaseInterface uiDatabaseInterface;
    private String[] cleanNames = new String[17];
    private String[] defenses = new String[9];
    ViewGroup layoutMaster = null;
    ArrayList<View> allAddedViews = new ArrayList<View>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_view);
        layoutMaster = (ViewGroup) findViewById(R.id.dataViewContent);

        mySQLiteHelper = UIDatabaseInterface.getDatabase();

        final ViewDataActivity viewDataActivity = this;



        this.createGridView();


    }

    private void createGridView(){

        Cursor cursor = mySQLiteHelper.selectFromTable("*", "custom");
        int cols = cursor.getColumnCount();
        View[] smallBorders = new View[cols - 1];


        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {

                SpannedString[] row = new SpannedString[cols-1];
                for (int i = 1; i < cols; i++) {

                    Spannable question = new SpannableString(cursor.getColumnName(i) + ": ");
                    String colData = cursor.getColumnName(i);
                    Log.v("Dynamic Layout", "COL DATA LENGTH: " + colData.length());
                    Log.v("Dynamic Layout", "FULL QUESTION STRING:" + cursor.getColumnName(i) + ":");
                    question.setSpan(new ForegroundColorSpan(Color.GRAY), 0, colData.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    question.setSpan(new ForegroundColorSpan(Color.RED), colData.length(), colData.length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);



                    Spannable answer = new SpannableString(cursor.getString(i));
                    String answerData = cursor.getString(i);
                    answer.setSpan(new ForegroundColorSpan(Color.BLACK), 0, answerData.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    SpannedString result = (SpannedString) TextUtils.concat(question, answer);
                    row[i-1] = result;
             //       row[i] = cursor.getColumnName(i) +"<font color=#ff0000>: </font>" +  cursor.getString(i);
                        Log.v("Dynamic Layout", cursor.getColumnName(i));

                        if (i - 1 < smallBorders.length) {
                            Log.v("Dynamic Layout", "INFLATING SMALL BORDER AT POS " + i);
                            smallBorders[i - 1] = LayoutInflater.from(this).inflate(R.layout.data_border, layoutMaster, false);

                        }

                }



                View bigBorder = LayoutInflater.from(this).inflate(R.layout.data_border_gray, layoutMaster, false);
                allAddedViews.add(bigBorder);
                layoutMaster.addView(bigBorder);




                View dataEntr = (View) LayoutInflater.from(this).inflate(R.layout.data_view_entry, layoutMaster, false);
                TextView colDispl = (TextView) dataEntr.findViewById(R.id.colDisp);

                colDispl.setText("#" + cursor.getString(0));
                allAddedViews.add(dataEntr);
                layoutMaster.addView(dataEntr);

                View big = LayoutInflater.from(this).inflate(R.layout.data_border, layoutMaster, false);

                allAddedViews.add(big);
                layoutMaster.addView(big);

                for(int i = 0; i < row.length; i++) {
                    View dataEntryRow = (View) LayoutInflater.from(this).inflate(R.layout.data_view_entry, layoutMaster, false);
                    TextView colDisplay = (TextView) dataEntryRow.findViewById(R.id.colDisp);

                    colDisplay.setText(row[i]);
                    allAddedViews.add(dataEntryRow);
                    layoutMaster.addView(dataEntryRow);


                    if(i < smallBorders.length) {
                        allAddedViews.add(smallBorders[i]);
                        layoutMaster.addView(smallBorders[i]);
                    }
                }
                cursor.moveToNext();
            }
        }

    }


}