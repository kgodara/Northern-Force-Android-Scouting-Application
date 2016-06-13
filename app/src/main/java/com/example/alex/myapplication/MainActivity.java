package com.example.alex.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    boolean textChanged = false;
    boolean hasDoneTests = false;
    private MySQLiteHelper db;
    public static UIDatabaseInterface uiDatabaseInterface;
    boolean isStart = true;
    private Context baseContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiDatabaseInterface = new UIDatabaseInterface(this.getBaseContext());



        this.baseContext = this.getBaseContext();

        this.runTests();

        setContentView(R.layout.activity_main);

        Button expData = (Button) findViewById(R.id.exportData);
        expData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Context context = baseContext;
                Intent i = new Intent(context, ExportDataActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);


            }
        });
        //expData.setVisibility(View.INVISIBLE);


        Button enterData = (Button) (findViewById(R.id.titleScreenEnterData));
        enterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = baseContext;
                Intent i = new Intent(context, DynamicEnterDataActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });
        //enterData.setTextColor(Color.GREEN);

        Button viewData = (Button) (findViewById(R.id.titleScreenViewData));
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = baseContext;
                Intent i = new Intent(context, ViewDataActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });

        Button makeLayout = (Button) (findViewById(R.id.makeLayout));
        makeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = baseContext;
                Intent i = new Intent(context, MakeLayoutActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });

        Button howToUse = (Button) (findViewById(R.id.howToUse));
        howToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = baseContext;
                Intent i = new Intent(context, HowToUseActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });

        Button about = (Button) (findViewById(R.id.about));
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = baseContext;
                Intent i = new Intent(context, AboutActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        }

        if (id == R.id.action_viewData){
            Context context = this.getBaseContext();
            Intent i = new Intent(context, ViewDataActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void runTests(){
        Tests test = new Tests();
        test.testDocumentParserGetLengthReturnsCorrect();
        test.testDocumentParserGetValueReturnsCorrect();
        test.testSQLite(this.getBaseContext());
        test.testConfigParser(this.getBaseContext());
    }

    public UIDatabaseInterface getUiDatabaseInterface(){
        return uiDatabaseInterface;
    }
}
