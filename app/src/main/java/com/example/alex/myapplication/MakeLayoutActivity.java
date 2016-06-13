package com.example.alex.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Oombliocarius on 4/22/16.
 */
public class MakeLayoutActivity extends ActionBarActivity {

    MySQLiteHelper database = null;

    ArrayList<Question> savedQuestions = new ArrayList<Question>();

    View toBeAdded = null;
    final String FILE_NAME = "custom.txt";
    Button create = null;
    List<Integer> viewIDs = new ArrayList<Integer>();
    int viewNum = 1;

    ArrayAdapter<CharSequence> adapter = null;
    ArrayAdapter<CharSequence> multipleChoiceAdapter = null;

    int[] multiChoiceIDs = new int[11];
    int[] numOnlyIDs = new int[6];
    int[] boolQuestionIDs = new int[6];
    int[] textOnlyIDs = new int[4];
    MakeLayoutActivity mla = null;

    View.OnClickListener saveQuestion = null;
    View.OnClickListener deleteQuestion = null;

    File file = null;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.make_layout);
        this.database = new MySQLiteHelper(this.getBaseContext());
        mla = this;



        multiChoiceIDs[0] = getResources().getIdentifier("multipleChoiceNum", "id", getPackageName());
        multiChoiceIDs[1] = getResources().getIdentifier("multipleChoicePrompt", "id", getPackageName());
        multiChoiceIDs[2] = getResources().getIdentifier("multipleChoiceLabel1", "id", getPackageName());
        multiChoiceIDs[3] = getResources().getIdentifier("multipleChoiceLabel2", "id", getPackageName());
        multiChoiceIDs[4] = getResources().getIdentifier("multipleChoiceLabel3", "id", getPackageName());
        multiChoiceIDs[5] = getResources().getIdentifier("multipleChoiceLabel4", "id", getPackageName());
        multiChoiceIDs[6] = getResources().getIdentifier("multipleChoiceLabel5", "id", getPackageName());
        multiChoiceIDs[7] = getResources().getIdentifier("multipleChoiceLabel6", "id", getPackageName());
        multiChoiceIDs[8] = getResources().getIdentifier("saveQuestion", "id", getPackageName());
        multiChoiceIDs[9] = getResources().getIdentifier("userQuestion", "id", getPackageName());
        multiChoiceIDs[10] = getResources().getIdentifier("deleteQuestion", "id", getPackageName());


        numOnlyIDs[0] = getResources().getIdentifier("rangePrompt", "id", getPackageName());
        numOnlyIDs[1] = getResources().getIdentifier("lowRangeInput", "id", getPackageName());
        numOnlyIDs[2] = getResources().getIdentifier("highRangeInput", "id", getPackageName());
        numOnlyIDs[3] = getResources().getIdentifier("saveQuestion", "id", getPackageName());
        numOnlyIDs[4] = getResources().getIdentifier("userQuestion", "id", getPackageName());
        numOnlyIDs[5] = getResources().getIdentifier("deleteQuestion", "id", getPackageName());

        boolQuestionIDs[0] = getResources().getIdentifier("trueFalsePrompt", "id", getPackageName());
        boolQuestionIDs[1] = getResources().getIdentifier("trueLabelInput", "id", getPackageName());
        boolQuestionIDs[2] = getResources().getIdentifier("falseLabelInput", "id", getPackageName());
        boolQuestionIDs[3] = getResources().getIdentifier("saveQuestion", "id", getPackageName());
        boolQuestionIDs[4] = getResources().getIdentifier("userQuestion", "id", getPackageName());
        boolQuestionIDs[5] = getResources().getIdentifier("deleteQuestion", "id", getPackageName());


        textOnlyIDs[0] = getResources().getIdentifier("textFieldHintInput", "id", getPackageName());
        textOnlyIDs[1] = getResources().getIdentifier("saveQuestion", "id", getPackageName());
        textOnlyIDs[2] = getResources().getIdentifier("userQuestion", "id", getPackageName());
        textOnlyIDs[3] = getResources().getIdentifier("deleteQuestion", "id", getPackageName());


        deleteQuestion = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parentLayout = (View) view.getParent();
                int id = parentLayout.getId();

                Question tempo;
                for(int i = 0; i < savedQuestions.size(); i++) {

                    tempo = savedQuestions.get(i);
                    if(tempo.getID() == id) {
                        savedQuestions.remove(i);
                    }
                }
                View spinner = parentLayout.findViewById(R.id.inputOptions);
                spinner.setVisibility(View.GONE);


                View multiChecker = parentLayout.findViewById(multiChoiceIDs[0]);
                View boolChecker = parentLayout.findViewById(boolQuestionIDs[0]);
                View textChecker = parentLayout.findViewById(textOnlyIDs[0]);
                View numChecker =  parentLayout.findViewById(numOnlyIDs[0]);

                if(multiChecker.getVisibility() == View.VISIBLE) {
                    Log.v("Custom Layout", "CLEARING MULTI QUESTION");
                    for(int j = 0; j < multiChoiceIDs.length; j++) {

                        View temp = parentLayout.findViewById(multiChoiceIDs[j]);
                        temp.setVisibility(View.GONE);

                    }

                }

                if(boolChecker.getVisibility() == View.VISIBLE) {

                    Log.v("Custom Layout", "CLEARING BOOL QUESTION");
                    for(int j = 0; j < boolQuestionIDs.length; j++) {

                        View temp = parentLayout.findViewById(boolQuestionIDs[j]);
                        temp.setVisibility(View.GONE);

                    }

                }

                if(textChecker.getVisibility() == View.VISIBLE) {
                    Log.v("Custom Layout", "CLEARING TEXT QUESTION");

                    for(int j = 0; j < textOnlyIDs.length; j++) {

                        View temp = parentLayout.findViewById(textOnlyIDs[j]);
                        temp.setVisibility(View.GONE);

                    }

                }

                if(numChecker.getVisibility() == View.VISIBLE) {
                    Log.v("Custom Layout", "CLEARING NUM QUESTION");

                    for(int j = 0; j < numOnlyIDs.length; j++) {

                        View temp = parentLayout.findViewById(numOnlyIDs[j]);
                        temp.setVisibility(View.GONE);

                    }

                }


            }


        };


         saveQuestion = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View parentLayout = (View) view.getParent();

                TextView numOnly = (TextView) parentLayout.findViewById(numOnlyIDs[0]);
                TextView boolLabel = (TextView) parentLayout.findViewById(boolQuestionIDs[0]);
                Spinner multipleChoice = (Spinner) parentLayout.findViewById(multiChoiceIDs[0]);
                EditText textOnly = (EditText) parentLayout.findViewById(textOnlyIDs[0]);

                if(numOnly.getVisibility() == View.VISIBLE) {

                    EditText quest = (EditText) parentLayout.findViewById(R.id.userQuestion);
                    EditText low = (EditText) parentLayout.findViewById(R.id.lowRangeInput);
                    EditText high = (EditText) parentLayout.findViewById(R.id.highRangeInput);

                    String question;
                    int lowBound = -2;
                    int highBound = -2;
                    String lowCheck, highCheck;



                    question = quest.getText().toString();

                    if(question.equals("")) {

                        Button saveButton = (Button) parentLayout.findViewById(R.id.saveQuestion);
                        saveButton.setTextColor(Color.RED);
                        saveButton.setText("Error: No Question");

                    }
                    else {



                        lowCheck = low.getText().toString();
                        highCheck = high.getText().toString();

                        if(!lowCheck.equals("") && !highCheck.equals("")) {

                            lowBound = Integer.valueOf(lowCheck);
                            highBound = Integer.valueOf(highCheck);

                        }

                        if(lowCheck.equals("") && highCheck.equals("")) {

                                lowBound = -1;
                                highBound = -1;
                        }

                        if(lowCheck.equals("") && !highCheck.equals("")) {

                            lowBound = 0;
                            highBound = Integer.valueOf(highCheck);
                        }

                        if(!lowCheck.equals("") && highCheck.equals("")) {

                            lowBound = Integer.valueOf(lowCheck);
                            highBound = -1;
                        }
                        Log.v("Custom Layout", "[" + lowBound + ", " + highBound + "]");

                        int actualID = parentLayout.getId();
                        Log.v("Custom Layout", "ACTUAL ID: " + actualID);
                        NumOnlyQuestion num = new NumOnlyQuestion(question, lowBound, highBound, actualID);
                        savedQuestions.add(num);

                        Button saveButton = (Button) parentLayout.findViewById(R.id.saveQuestion);
                        saveButton.setTextColor(Color.GREEN);
                        saveButton.setEnabled(false);
                        saveButton.setText("Question Saved");

                    }




                }
//END OF NUM QUESTION SECTION






                if(boolLabel.getVisibility() == View.VISIBLE) {

                    EditText quest = (EditText) parentLayout.findViewById(R.id.userQuestion);
                    EditText falseLabel = (EditText) parentLayout.findViewById(R.id.falseLabelInput);
                    EditText trueLabel = (EditText) parentLayout.findViewById(R.id.trueLabelInput);

                    String question, trueText, falseText;

                    question = quest.getText().toString();
                    falseText = falseLabel.getText().toString();
                    trueText = trueLabel.getText().toString();

                    if(!question.equals("") && !falseText.equals("") && !trueText.equals("")) {

                        Log.v("Custom Layout", "ENTERING BOOL QUESTION INTO LIST");

                        int actualID = parentLayout.getId();
                        Log.v("Custom Layout", "ACTUAL ID: " + actualID);
                        BoolQuestion bool = new BoolQuestion(question, falseText, trueText, actualID);
                        savedQuestions.add(bool);

                        Button saveButton = (Button) parentLayout.findViewById(R.id.saveQuestion);
                        saveButton.setTextColor(Color.GREEN);
                        saveButton.setText("Question Saved");
                        saveButton.setEnabled(false);

                    }

                    else {
                        Button saveButton = (Button) parentLayout.findViewById(R.id.saveQuestion);
                        saveButton.setTextColor(Color.RED);
                        saveButton.setText("Error: Missing Field");
                    }


                }

                if(multipleChoice.getVisibility() == View.VISIBLE) {

                    EditText quest = (EditText) parentLayout.findViewById(R.id.userQuestion);
                    EditText[] allLabels = new EditText[6];

                    String question = quest.getText().toString();

                    allLabels[0] = (EditText) parentLayout.findViewById(R.id.multipleChoiceLabel1);
                    allLabels[1] = (EditText) parentLayout.findViewById(R.id.multipleChoiceLabel2);
                    allLabels[2] = (EditText) parentLayout.findViewById(R.id.multipleChoiceLabel3);
                    allLabels[3] = (EditText) parentLayout.findViewById(R.id.multipleChoiceLabel4);
                    allLabels[4] = (EditText) parentLayout.findViewById(R.id.multipleChoiceLabel5);
                    allLabels[5] = (EditText) parentLayout.findViewById(R.id.multipleChoiceLabel6);

                    int numVisible = 0;

                    for(int i = 0; i < allLabels.length; i++) {

                        if(allLabels[i].getVisibility() == View.VISIBLE) {
                            numVisible++;
                        }

                    }

                    String[] labels = new String[numVisible];
                    int canGo = 1;
                    for(int i = 0; i < labels.length; i++) {
                        labels[i] = allLabels[i].getText().toString();
                        if(labels[i].equals("")) {
                            canGo = 0;
                        }
                    }
                    if(canGo == 1 && !question.equals("")) {
                        Log.v("Custom Layout", "ENTERING MULTI QUESTION INTO LIST");

                        int actualID = parentLayout.getId();
                        Log.v("Custom Layout", "ACTUAL ID: " + actualID);
                        MultiChoiceQuestion multi = new MultiChoiceQuestion(question, labels, actualID);
                        savedQuestions.add(multi);

                        Button saveButton = (Button) parentLayout.findViewById(R.id.saveQuestion);
                        saveButton.setTextColor(Color.GREEN);
                        saveButton.setText("Question Saved");
                        saveButton.setEnabled(false);

                    }

                    if(canGo == 0 || question.equals("")) {
                        Button saveButton = (Button) parentLayout.findViewById(R.id.saveQuestion);
                        saveButton.setTextColor(Color.RED);
                        saveButton.setText("Error: Missing Field");
                    }


                    Log.v("Custom Layout", "NUM VISIBLE IS: " + numVisible);



                }

                if(textOnly.getVisibility() == View.VISIBLE) {


                    EditText quest = (EditText) parentLayout.findViewById(R.id.userQuestion);
                    EditText hintInput = (EditText) parentLayout.findViewById(R.id.textFieldHintInput);

                    String question, hint;
                    question = quest.getText().toString();
                    hint = hintInput.getText().toString();

                    if(!question.equals("") && !hint.equals("")) {

                        Log.v("Custom Layout", "ENTERING TEXT QUESTION INTO LIST");

                        int actualID = parentLayout.getId();
                        Log.v("Custom Layout", "ACTUAL ID: " + actualID);
                        TextFieldQuestion text = new TextFieldQuestion(hint, question, actualID);
                        savedQuestions.add(text);

                        Button saveButton = (Button) parentLayout.findViewById(R.id.saveQuestion);
                        saveButton.setTextColor(Color.GREEN);
                        saveButton.setText("Question Saved");
                        saveButton.setEnabled(false);


                    }

                    if(question.equals("") || hint.equals("")) {

                        Button saveButton = (Button) parentLayout.findViewById(R.id.saveQuestion);
                        saveButton.setTextColor(Color.RED);
                        saveButton.setText("Error: Missing Field");

                    }



                }



                printQuestions();

            }
        };








        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);


        create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mla);
                builder.setMessage("Finalizing this layout will delete all previous layouts, as well as data not stored to a file. Proceed?").setTitle("Finalize Layout")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Handle Ok
                                doSaveProcess();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Handle Cancel
                            }
                        })
                        .create().show();

            }
        });


        create.post(new Runnable() {
            @Override
            public void run() {
                Display display = getWindowManager().getDefaultDisplay();
                DisplayMetrics outMetrics = new DisplayMetrics();
                display.getMetrics(outMetrics);

                float density = getResources().getDisplayMetrics().density;
                float dpHeight = outMetrics.heightPixels / density;
                float dpWidth = outMetrics.widthPixels / density;
                Log.v("Custom Layout", "DP WIDTH: " + dpWidth);
                Log.v("Custom Layout", "SETTING LEFT MARGIN TO: " + (dpWidth / 2) + "  -  " + (pxToDp(create.getWidth()) / 2));
                Log.v("Custom Layout", "CREATE WIDTH RETURNED: " + create.getWidth());

                setMargins(create, (int) ((dpWidth / 2) - (pxToDp(create.getWidth()) / 2)), 0, 0, 0);

            }
        });


        Button add = (Button) findViewById(R.id.addQuestion);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ViewGroup bigger = (ViewGroup) findViewById(R.id.content);


                addLayout(bigger, R.layout.custom_question);


            }
        });

    }


    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = this.getBaseContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public void addLayout(ViewGroup source, int id) {
         adapter = ArrayAdapter.createFromResource(this, R.array.components_array, android.R.layout.simple_spinner_item);
        multipleChoiceAdapter = ArrayAdapter.createFromResource(this, R.array.multi_num_array, android.R.layout.simple_spinner_item);



        toBeAdded = LayoutInflater.from(this).inflate(id, source, false);
        int newID = View.generateViewId();
        viewIDs.add(newID);
        toBeAdded.setId(newID);

        if(id == R.layout.custom_question) {
            Spinner spinner = (Spinner) toBeAdded.findViewById(R.id.inputOptions);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    String selected = adapter.getItem(i).toString();

                   View parentLayout = (View) adapterView.getParent();
                   View multiChecker = parentLayout.findViewById(multiChoiceIDs[0]);
                   View boolChecker = parentLayout.findViewById(boolQuestionIDs[0]);
                   View textChecker = parentLayout.findViewById(textOnlyIDs[0]);
                   View numChecker =  parentLayout.findViewById(numOnlyIDs[0]);


                    if(multiChecker.getVisibility() == View.VISIBLE) {

                        for(int j = 0; j < multiChoiceIDs.length; j++) {

                            View temp = parentLayout.findViewById(multiChoiceIDs[j]);
                            temp.setVisibility(View.GONE);

                        }

                    }

                    if(boolChecker.getVisibility() == View.VISIBLE) {

                        for(int j = 0; j < boolQuestionIDs.length; j++) {

                            View temp = parentLayout.findViewById(boolQuestionIDs[j]);
                            temp.setVisibility(View.GONE);

                        }

                    }

                    if(textChecker.getVisibility() == View.VISIBLE) {

                        for(int j = 0; j < textOnlyIDs.length; j++) {

                            View temp = parentLayout.findViewById(textOnlyIDs[j]);
                            temp.setVisibility(View.GONE);

                        }

                    }

                    if(numChecker.getVisibility() == View.VISIBLE) {

                        for(int j = 0; j < numOnlyIDs.length; j++) {

                            View temp = parentLayout.findViewById(numOnlyIDs[j]);
                            temp.setVisibility(View.GONE);

                        }

                    }


                    View question = (View) parentLayout.findViewById(R.id.userQuestion);
                    question.setVisibility(View.VISIBLE);
                    if(selected.equals("Numbers Only Input")) {

                        Button save = (Button) parentLayout.findViewById(R.id.saveQuestion);
                        Button delete = (Button) parentLayout.findViewById(R.id.deleteQuestion);
                        delete.setVisibility(View.VISIBLE);
                        save.setVisibility(View.VISIBLE);

                        save.setEnabled(true);
                        save.setTextColor(Color.BLACK);
                        save.setText("Save Question");

                        Log.v("Custom Layout", "VISIBILITY CALLED");

                        View rangePrompt = (View) parentLayout.findViewById(R.id.rangePrompt);
                        View lowRangeField = (View) parentLayout.findViewById(R.id.lowRangeInput);
                        View highRangeField = (View) parentLayout.findViewById(R.id.highRangeInput);

                        rangePrompt.setVisibility(View.VISIBLE);
                        lowRangeField.setVisibility(View.VISIBLE);
                        highRangeField.setVisibility(View.VISIBLE);
                    }


                    if(selected.equals("True/False Input")) {

                        Button delete = (Button) parentLayout.findViewById(R.id.deleteQuestion);
                        delete.setVisibility(View.VISIBLE);

                        Button save = (Button) parentLayout.findViewById(R.id.saveQuestion);

                        save.setVisibility(View.VISIBLE);

                        save.setEnabled(true);
                        save.setTextColor(Color.BLACK);
                        save.setText("Save Question");



                        View labelPrompt = (View) parentLayout.findViewById(R.id.trueFalsePrompt);
                        View trueLabelInput = (View) parentLayout.findViewById(R.id.trueLabelInput);
                        View falseLabelInput = (View) parentLayout.findViewById(R.id.falseLabelInput);

                        labelPrompt.setVisibility(View.VISIBLE);
                        trueLabelInput.setVisibility(View.VISIBLE);
                        falseLabelInput.setVisibility(View.VISIBLE);

                    }

                    if(selected.equals("Text Field")) {

                        Button delete = (Button) parentLayout.findViewById(R.id.deleteQuestion);
                        delete.setVisibility(View.VISIBLE);

                        Button save = (Button) parentLayout.findViewById(R.id.saveQuestion);
                        save.setVisibility(View.VISIBLE);

                        save.setEnabled(true);
                        save.setTextColor(Color.BLACK);
                        save.setText("Save Question");


                        View textFieldHintInput = (View) parentLayout.findViewById(R.id.textFieldHintInput);

                        textFieldHintInput.setVisibility(View.VISIBLE);

                    }

                    if(selected.equals("Multiple Choice")) {





                        Spinner spinno = (Spinner) parentLayout.findViewById(R.id.multipleChoiceNum);

                        spinno.setVisibility(View.VISIBLE);

                        spinno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                Log.v("Custom Layout", "I IS: " + i);
                                String selected = multipleChoiceAdapter.getItem(i).toString();
                                Log.v("Custom Layout", "PRINTING SELECTED NUM: |" + selected + "|");

                                View parentLayout = (View) adapterView.getParent();
                                if (selected.equals("1")) {
                                    View view1 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel1);
                                    View view2 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel2);
                                    View view3 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel3);
                                    View view4 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel4);
                                    View view5 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel5);
                                    View view6 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel6);

                                    view1.setVisibility(View.VISIBLE);
                                    view2.setVisibility(View.GONE);
                                    view3.setVisibility(View.GONE);
                                    view4.setVisibility(View.GONE);
                                    view5.setVisibility(View.GONE);
                                    view6.setVisibility(View.GONE);
                                }
                                if (selected.equals("2")) {
                                    View view1 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel1);
                                    View view2 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel2);
                                    View view3 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel3);
                                    View view4 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel4);
                                    View view5 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel5);
                                    View view6 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel6);

                                    view1.setVisibility(View.VISIBLE);
                                    view2.setVisibility(View.VISIBLE);
                                    view3.setVisibility(View.GONE);
                                    view4.setVisibility(View.GONE);
                                    view5.setVisibility(View.GONE);
                                    view6.setVisibility(View.GONE);
                                }
                                if (selected.equals("3")) {
                                    View view1 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel1);
                                    View view2 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel2);
                                    View view3 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel3);
                                    View view4 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel4);
                                    View view5 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel5);
                                    View view6 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel6);

                                    view1.setVisibility(View.VISIBLE);
                                    view2.setVisibility(View.VISIBLE);
                                    view3.setVisibility(View.VISIBLE);
                                    view4.setVisibility(View.GONE);
                                    view5.setVisibility(View.GONE);
                                    view6.setVisibility(View.GONE);
                                }
                                if (selected.equals("4")) {


                                    View view1 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel1);
                                    View view2 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel2);
                                    View view3 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel3);
                                    View view4 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel4);
                                    View view5 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel5);
                                    View view6 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel6);

                                    view1.setVisibility(View.VISIBLE);
                                    view2.setVisibility(View.VISIBLE);
                                    view3.setVisibility(View.VISIBLE);
                                    view4.setVisibility(View.VISIBLE);
                                    view5.setVisibility(View.GONE);
                                    view6.setVisibility(View.GONE);


                                }
                                if (selected.equals("5")) {
                                    View view1 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel1);
                                    View view2 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel2);
                                    View view3 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel3);
                                    View view4 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel4);
                                    View view5 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel5);
                                    View view6 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel6);

                                    view1.setVisibility(View.VISIBLE);
                                    view2.setVisibility(View.VISIBLE);
                                    view3.setVisibility(View.VISIBLE);
                                    view4.setVisibility(View.VISIBLE);
                                    view5.setVisibility(View.VISIBLE);
                                    view6.setVisibility(View.GONE);
                                }
                                if (selected.equals("6")) {
                                    View view1 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel1);
                                    View view2 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel2);
                                    View view3 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel3);
                                    View view4 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel4);
                                    View view5 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel5);
                                    View view6 = (View) parentLayout.findViewById(R.id.multipleChoiceLabel6);

                                    view1.setVisibility(View.VISIBLE);
                                    view2.setVisibility(View.VISIBLE);
                                    view3.setVisibility(View.VISIBLE);
                                    view4.setVisibility(View.VISIBLE);
                                    view5.setVisibility(View.VISIBLE);
                                    view6.setVisibility(View.VISIBLE);
                                }

                            }


                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }


                        });



                        multipleChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinno.setAdapter(multipleChoiceAdapter);

                        Button save = (Button) parentLayout.findViewById(R.id.saveQuestion);
                        save.setVisibility(View.VISIBLE);

                        save.setEnabled(true);
                        save.setTextColor(Color.BLACK);
                        save.setText("Save Question");


                    }


                    Button delete = (Button) parentLayout.findViewById(R.id.deleteQuestion);
                    delete.setVisibility(View.VISIBLE);
                    delete.setOnClickListener(deleteQuestion);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {



                }
            });


            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }




        Button saveButton = (Button) toBeAdded.findViewById(R.id.saveQuestion);
        saveButton.setOnClickListener(saveQuestion);
        View topBorder = LayoutInflater.from(this).inflate(R.layout.border_line, source, false);
        source.addView(topBorder);
        source.addView(toBeAdded);
    }

    public void printQuestions() {


        for(int i = 0; i < savedQuestions.size(); i++) {

            Question temp = savedQuestions.get(i);

            if(temp instanceof MultiChoiceQuestion) {
                MultiChoiceQuestion tempo = (MultiChoiceQuestion) temp;
            Log.v("Custom Layout", "\n" + "MULTIPLE CHOICE" + "\n" + tempo.getQuestion() + "\n" + Arrays.toString(tempo.getButtonLabels()) + "\n" + "------------");


            }

            if(temp instanceof BoolQuestion) {
                BoolQuestion tempo = (BoolQuestion) temp;
                Log.v("Custom Layout", "\n" + "BOOL QUESTION" +"\n" + tempo.getQuestion() + "\n" +  tempo.getFalseText() + "\n" + tempo.getTrueText() + "\n" + "------------");


            }

            if(temp instanceof TextFieldQuestion) {
                TextFieldQuestion tempo = (TextFieldQuestion) temp;
                Log.v("Custom Layout","\n" + "TEXT FIELD QUESTION" + "\n" + tempo.getQuestion() + "\n" +  tempo.getHint() + "\n" + "------------");


            }

            if(temp instanceof NumOnlyQuestion) {
                NumOnlyQuestion tempo = (NumOnlyQuestion) temp;
                Log.v("Custom Layout","\n" + "NUMBER ONLY QUESTION" + "\n" + tempo.getQuestion() + "\n" +  tempo.getLowBound() + "\n" + tempo.getHighBound() + "\n" + "------------");


            }



        }

    }

    public void doSaveProcess() {
        database.dropTable("android_");
        database.dropTable("custom");

        ArrayList<ConfigEntry> columns = new ArrayList<ConfigEntry>();

        for(int i = 0; i <savedQuestions.size(); i++) {

            if(savedQuestions.get(i) instanceof BoolQuestion) {

                BoolQuestion info = (BoolQuestion) savedQuestions.get(i);
                columns.add(new ConfigEntry("String", info.getQuestion()));

            }

            if(savedQuestions.get(i) instanceof NumOnlyQuestion) {

                NumOnlyQuestion info = (NumOnlyQuestion) savedQuestions.get(i);
                columns.add(new ConfigEntry("int", info.getQuestion()));

            }

            if(savedQuestions.get(i) instanceof TextFieldQuestion) {

                TextFieldQuestion info = (TextFieldQuestion) savedQuestions.get(i);
                columns.add(new ConfigEntry("String", info.getQuestion()));

            }

            if(savedQuestions.get(i) instanceof MultiChoiceQuestion) {

                MultiChoiceQuestion info = (MultiChoiceQuestion) savedQuestions.get(i);
                columns.add(new ConfigEntry("String", info.getQuestion()));

            }


        }


        DatabaseTable table = new DatabaseTable("custom", columns);
        database.createTable(table);
        Log.v("Custom Layout", "Listener called");
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        dir.mkdirs();
        file = new File(dir, FILE_NAME);
        if (!file.exists()) {
            try {
                Log.v("Custom Layout", "No file found");
                file.createNewFile();
                writeQuestionsToFile();

            } catch (IOException e) {
                Log.v("Custom Layout", e.getMessage());
            }
        } else {

            try {

                BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
                if (br.readLine() == null) {
                    Log.v("Custom Layout", "Empty File Found");
                    Log.v("Custom Layout", "NO PREVIOUS DATA FOUND");
                    writeQuestionsToFile();
                } else {

                    Log.v("Custom Layout", "Used File Found");
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Previous Saved Layout Found, Override?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Handle Ok
                                    writeQuestionsToFile();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Handle Cancel
                                }
                            })
                            .create().show();

                }

            } catch (IOException e) {

                Log.v("Custom Layout", e.getMessage());

            }

        }
    }

        public void writeQuestionsToFile() {

            try {

                PrintWriter pw = new PrintWriter(file);
                pw.close();

                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream obs = new ObjectOutputStream(fos);

                obs.writeObject(savedQuestions);

                obs.close();
                fos.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Questions Saved").setTitle("File Saved").create().show();




            }

            catch(FileNotFoundException e) {
                Log.v("Custom Layout", e.getMessage());
            }

            catch(IOException e) {
                Log.v("Custom Layout", e.getMessage());
            }

    }



}
