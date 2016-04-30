package com.example.alex.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alex on 3/9/15.
 */
public class ViewDataActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener{

    private CursorAdapter dataSource;
    private MySQLiteHelper mySQLiteHelper;
    private static UIDatabaseInterface uiDatabaseInterface;
    private String[] cleanNames = new String[17];
    private String[] defenses = new String[9];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_view);

        cleanNames[0] = "Team Number";
        cleanNames[1] = "Match Number";
        cleanNames[2] = "(Auto) Percentage of times Outworks crossed";
        cleanNames[3] = "(Auto) Did they breach any defenses?";
        cleanNames[4] = "(Auto) If so which one?";
        cleanNames[5] = "(Auto) Did they score?";
        cleanNames[6] = "(Auto) Percentage of high goals";
        cleanNames[7] = "(Tele) Did they breach any defenses, which?";
        cleanNames[8] = "(Tele) Which obstacles did they overcome?";
        cleanNames[9] = "(Tele) Did they fail to overcome any defences?";
        cleanNames[10] = "(Tele) Which obstacles did they fail?";
        cleanNames[11] = "(Tele) Percentage of high shooting";
        cleanNames[12] = "(Tele) Percentage of reliable scoring";
        cleanNames[13] = "(Tele) Percentage of offensively played matches";
        cleanNames[14] = "(Tele) Percentages of tower challenges";
        cleanNames[15] = "(Tele) Percentages of tower scales";
        cleanNames[16] = "Other Comments";


        mySQLiteHelper = UIDatabaseInterface.getDatabase();

        final ViewDataActivity viewDataActivity = this;
        ArrayList<String> tables = UIDatabaseInterface.getTableNames();

        tables.remove("android_metadata");

        this.createGridView();
    }

    private void createGridView(){
        GridView gridView = (GridView) (findViewById(R.id.dataViewGridView));

        final ViewDataAdapter viewDataAdapter = new ViewDataAdapter(mySQLiteHelper, this);


        gridView.setAdapter(viewDataAdapter);
    }

    public void searchForTeam(View v){
        EditText editText= (EditText) findViewById(R.id.seachedTeam);
        TextView stats = (TextView) findViewById(R.id.teamStats);



        if(editText == null){
            Log.v("Mac Address", "editText is null");
        }
        String text = editText.getText().toString();
        if(text.equals("")){
            stats.setText("");
            Log.v("Mac Address", "setting searched team to null");
            ViewDataAdapter.setSearchedTeam(null);
        }
        else{
            stats.setText("");
            computeDisplayStats(stats, text);
            Log.v("Mac Address", "searching for team " + text);
            ViewDataAdapter.setSearchedTeam(editText.getText().toString());
        }

        this.createGridView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_enterData){
            Intent i = new Intent(this, MainActivity.class);

            startActivity(i);
        }

        if (id == R.id.action_viewData){

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedTable = (String) adapterView.getItemAtPosition(i);

        Log.v("EnterDataActivity", "The spinner selected the table " + selectedTable);

        UIDatabaseInterface.setCurrentDataViewTable(selectedTable);

        this.createGridView();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




    public void computeDisplayStats(TextView view, String searchedTeam) {
        Log.v("DataBase", "SEARCHING TEAM: " + searchedTeam);
        Cursor cursor = mySQLiteHelper.selectFromTableWhere("*", "Performance", "Team_Number = " + searchedTeam);
        Cursor c = mySQLiteHelper.selectFromTable("*", "Performance");
        String[][] data = new String[cursor.getCount()][cursor.getColumnCount() - 4];


        int row = 0;
        int col = 0;

        int dataCol = 3;

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {


            while(dataCol < (cursor.getColumnCount() - 1)) {
                //   Log.v("Mac Address", "ADDED: " + cursor.getString(dataCol) + ",    TO: "+ row + ", " + col +"     FROM COL: " + dirtyNames[dataCol]);
                data[row][col] = cursor.getString(dataCol);
                col++;
                dataCol++;

            }




            cursor.moveToNext();
            col = 0;
            dataCol = 3;
            row++;
        }

Log.v("DataBase", "DATA ARRAY HAS ROW SIZE: " + row);
        Log.v("DataBase", "DATA ARRAY HAS ROWS: " + data.length);


//       data = Arrays.copyOf(data, fin);

      //  Log.v("DataBase", Array)


        TwoNum[] percents = new TwoNum[14];
        for (int i = 0; i < percents.length; i++) {
            percents[i] = new TwoNum();
        }
        String[] dirt = new String[14];
        dirt[0] = cleanNames[2];
        dirt[1] = cleanNames[3];
        dirt[2] = cleanNames[4];
        dirt[3] = cleanNames[5];
        dirt[4] = cleanNames[6];
        dirt[5] = cleanNames[7];
        dirt[6] = cleanNames[8];
        dirt[7] = cleanNames[9];
        dirt[8] = cleanNames[10];
        dirt[9] = cleanNames[11];
        dirt[10] = cleanNames[12];
        dirt[11] = cleanNames[13];
        dirt[12] = cleanNames[14];
        dirt[13] = cleanNames[15];

        //0, 1, 3, 5, 7, 10, 11, 12, 13
        //14 params in all, 9-10 are T/F
        int divisor = data.length;
        int percentIndex = 0;
        int highest = 0;
        for (int i = 0; i < data.length; i++) {


            for (int l = 0; l < data[i].length; l++) {

                if (data[i][l].contains("Hiigh")) {
                    percents[percentIndex].setFirstNum(percents[percentIndex].getFirstNum() + 1);
                    percents[percentIndex].setTwoNum(l);
                    percentIndex++;
                }

                if (data[i][l].contains("Loow")) {
                    percents[percentIndex].setTwoNum(l);
                    percentIndex++;
                }

                if (data[i][l].contains("high")) {
                    percents[percentIndex].setFirstNum(percents[percentIndex].getFirstNum() + 1);
                    percents[percentIndex].setTwoNum(l);
                    percentIndex++;
                }

                if (data[i][l].contains("low")) {
                    percents[percentIndex].setTwoNum(l);
                    percentIndex++;
                }


                if (data[i][l].equals("true")) {


                    percents[percentIndex].setFirstNum(percents[percentIndex].getFirstNum() + 1);
                    percents[percentIndex].setTwoNum(l);
                    percentIndex++;
                }
                if (data[i][l].equals("false")) {
                    percents[percentIndex].setTwoNum(l);
                    percentIndex++;
                }


            }
            percentIndex = 0;


        }





     //   Log.v("DataBase", "YO SUP");


        Log.v("DataBase", "DIVISOR: " + divisor);
        for (int g = 0; g < percents.length; g++) {
            int result = 0;

            Log.v("DataBase", "BASE ABOUT TO BE COMPUTED: " + percents[g].getFirstNum());
            percents[g].setFirstNum((int) ((((double) percents[g].getFirstNum()) / divisor) * 100));

            Log.v("DataBase", "PERCENT: " + percents[g].getFirstNum());
            Log.v("DataBase", "\n");

        }

        for (int g = 0; g < percents.length; g++) {


            if (percents[g].getFirstNum() > 0) {
                view.setText(view.getText() + dirt[percents[g].getTwoNum()] + ": " + percents[g].getTwoNum() + "%\n");
            }

        }





    }









}
