package com.example.alex.myapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Oombliocarius on 4/29/16.
 */
public class DynamicEnterDataActivity extends ActionBarActivity {

    MySQLiteHelper database = null;

    final String FILE_NAME = "custom.txt";
    File file = null;
    ArrayList<Question> questionsFromFile = new ArrayList<Question>();
    ViewGroup layoutMaster = null;
    View.OnClickListener onlyOneToggled = null;
    Button submit = null;

    int first = 0;
    int second = 0;
    int third = 0;
    int fourth = 0;


    ArrayList<View> viewText = new ArrayList<View>();
    ArrayList<View> viewNum = new ArrayList<View>();
    ArrayList<View> viewBool = new ArrayList<View>();
    ArrayList<View> viewMulti = new ArrayList<View>();

    RadioButton[] labels = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_enter_data);

        this.database = new MySQLiteHelper(this.getBaseContext());


        onlyOneToggled = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View parentLayout = (View) view.getParent();
                if (view instanceof RadioButton) {
                    RadioButton clicked = (RadioButton) view;
                    if (view.getId() == R.id.multiChoiceButton1 || view.getId() == R.id.multiChoiceButton2 || view.getId() == R.id.multiChoiceButton3 || view.getId() == R.id.multiChoiceButton4 || view.getId() == R.id.multiChoiceButton5 || view.getId() == R.id.multiChoiceButton6) {
                        for (int i = 0; i < labels.length; i++) {
                            labels[i].setChecked(false);
                        }
                        clicked.setChecked(true);

                    }

                    //End of Multi Button Toggler

                    //Bool Question Button Toggler
                    if (view.getId() == R.id.trueButton) {
                        RadioButton falseButton = (RadioButton) parentLayout.findViewById(R.id.falseButton);
                        falseButton.setChecked(false);
                    } else if (view.getId() == R.id.falseButton) {
                        RadioButton trueButton = (RadioButton) parentLayout.findViewById(R.id.trueButton);
                        trueButton.setChecked(false);
                    }
                }

            }
        };


        layoutMaster = (ViewGroup) findViewById(R.id.layoutMaster);

        submit = new Button(this);
        submit.setText("Submit");


        boolean begin = checkForLayout();
        Log.v("Dynamic Layout", Boolean.toString(begin));

        if (begin == false) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Make and Finalize a Data Layout in the 'Make a Data Layout' Tab First").setTitle("File Saved").create().show();

        }

        if (begin == true) {
            questionsFromFile = readQuestionsFromFile();

            for (int i = 0; i < questionsFromFile.size(); i++) {
                Question temp = questionsFromFile.get(i);

                addLayout(layoutMaster, temp);

            }
            layoutMaster.addView(submit);


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ArrayList<String> textInput = new ArrayList<String>();
                    ArrayList<String> numInput = new ArrayList<String>();
                    ArrayList<String> boolInput = new ArrayList<String>();
                    ArrayList<String> multiInput = new ArrayList<String>();

                    int canContinue = 0;

                    for (int i = 0; i < viewText.size(); i++) {


                        View parentQuestion = (View) viewText.get(i);
                        EditText textField = (EditText) parentQuestion.findViewById(R.id.textQuestionHint);
                        if (textField.getText().toString().equals("")) {
                            textField.setHintTextColor(Color.RED);
                            textField.setHint("Error: No Entry");
                            canContinue++;
                        } else {
                            textInput.add(textField.getText().toString());
                        }

                    }

                    for (int i = 0; i < viewNum.size(); i++) {

                        View parentQuestion = (View) viewNum.get(i);
                        EditText numField = (EditText) parentQuestion.findViewById(R.id.numQuestionHint);
                        if (numField.getText().toString().equals("")) {
                            numField.setHintTextColor(Color.RED);
                            numField.setHint("Error: No Entry");
                            canContinue++;
                        } else {
                            numInput.add(numField.getText().toString());
                        }

                    }

                    for (int i = 0; i < viewBool.size(); i++) {

                        View parentQuestion = (View) viewBool.get(i);

                        RadioButton trueButton = (RadioButton) parentQuestion.findViewById(R.id.trueButton);
                        RadioButton falseButton = (RadioButton) parentQuestion.findViewById(R.id.falseButton);

                        if (trueButton.isChecked() == false && falseButton.isChecked() == false) {
                            TextView questionText = (TextView) parentQuestion.findViewById(R.id.boolQuestionContent);
                            String text = questionText.getText().toString();
                            if (!text.contains(" - Error: No Entry")) {
                                text = text + "<font color=#ff0000> - Error: No Entry</font>";
                                questionText.setText(Html.fromHtml(text));
                            }


                            canContinue++;
                        } else {
                            if (trueButton.isChecked()) {
                                boolInput.add(trueButton.getText().toString());
                            } else {
                                boolInput.add(falseButton.getText().toString());
                            }


                        }

                    }

                    for (int i = 0; i < viewMulti.size(); i++) {

                        Log.v("Dynamic Layout", "In Multi Submit Listener Section");
                        View parentQuestion = (View) viewMulti.get(i);
                        RadioButton[] options = new RadioButton[6];

                        options[0] = (RadioButton) parentQuestion.findViewById(R.id.multiChoiceButton1);
                        options[1] = (RadioButton) parentQuestion.findViewById(R.id.multiChoiceButton2);
                        options[2] = (RadioButton) parentQuestion.findViewById(R.id.multiChoiceButton3);
                        options[3] = (RadioButton) parentQuestion.findViewById(R.id.multiChoiceButton4);
                        options[4] = (RadioButton) parentQuestion.findViewById(R.id.multiChoiceButton5);
                        options[5] = (RadioButton) parentQuestion.findViewById(R.id.multiChoiceButton6);

                        int numVisible = 0;
                        for (int l = 0; l < options.length; l++) {

                            if (options[i].getVisibility() == View.VISIBLE) {
                                numVisible++;
                            }
                        }
                        int oneIsChecked = 0;
                        int checked = 0;
                        Log.v("Dynamic Layout", "NUM VISIBLE IS: " + numVisible);
                        for (int l = 0; l < numVisible; l++) {
                            if (options[l].isChecked()) {
                                checked = l;
                                oneIsChecked = 1;
                            }
                        }
                        if (oneIsChecked == 0) {
                            Log.v("Dynamic Layout", "FOUND NOTHIN CHECKED");
                            TextView multiQuestion = (TextView) parentQuestion.findViewById(R.id.multiChoiceContent);

                            String text = multiQuestion.getText().toString();

                            if (!text.contains(" - Error: No Entry")) {
                                text = text + "<font color=#ff0000> - Error: No Entry</font>";
                                multiQuestion.setText(Html.fromHtml(text));
                            }
                            canContinue++;
                        }

                        if (oneIsChecked == 1) {

                            multiInput.add(options[checked].getText().toString());

                            TextView multiQuestion = (TextView) parentQuestion.findViewById(R.id.multiChoiceContent);
                            String text = multiQuestion.getText().toString();
                            text = text.replaceAll(" - Error: No Entry", "");


                            multiQuestion.setText(text);
                        }


                    }

                    if (canContinue > 0) {

                    } else {
                        ContentValues values = new ContentValues();

                        for (int i = 0; i < viewText.size(); i++) {
                            View parent = viewText.get(i);
                            TextView question = (TextView) parent.findViewById(R.id.textQuestionContent);
                            values.put(question.getText().toString(), textInput.get(i));

                        }

                        for (int i = 0; i < viewNum.size(); i++) {

                            View parent = viewNum.get(i);
                            TextView question = (TextView) parent.findViewById(R.id.numQuestionText);
                            Log.v("Dynamic Layout", "FIRST CUSTOM VALUE PARAM: " + question.getText().toString());
                            values.put(question.getText().toString(), numInput.get(i));

                        }

                        for (int i = 0; i < viewBool.size(); i++) {

                            View parent = viewBool.get(i);
                            TextView question = (TextView) parent.findViewById(R.id.boolQuestionContent);
                            values.put(question.getText().toString(), boolInput.get(i));

                        }

                        for (int i = 0; i < viewMulti.size(); i++) {

                            View parent = viewMulti.get(i);
                            TextView question = (TextView) parent.findViewById(R.id.multiChoiceContent);
                            values.put(question.getText().toString(), multiInput.get(i));

                        }

                        Log.v("Dynamic Layout", "ADDING VALUES TO DATABASE");
                        submit.setText("Data Submitted");
                        UIDatabaseInterface.getDatabase().addValues("custom", values);
                        printDatabase();

                        for (int i = 0; i < viewText.size(); i++) {
                            View parent = viewText.get(i);
                            TextView question = (TextView) parent.findViewById(R.id.textQuestionContent);
                            values.put(question.getText().toString(), textInput.get(i));

                        }

                        for (int i = 0; i < viewNum.size(); i++) {

                            View parent = viewNum.get(i);
                            TextView question = (TextView) parent.findViewById(R.id.numQuestionText);
                            Log.v("Dynamic Layout", "FIRST CUSTOM VALUE PARAM: " + question.getText().toString());
                            values.put(question.getText().toString(), numInput.get(i));

                        }

                        for (int i = 0; i < viewBool.size(); i++) {

                            View parent = viewBool.get(i);
                            TextView question = (TextView) parent.findViewById(R.id.boolQuestionContent);
                            values.put(question.getText().toString(), boolInput.get(i));

                        }

                        for (int i = 0; i < viewMulti.size(); i++) {

                            View parent = viewMulti.get(i);
                            TextView question = (TextView) parent.findViewById(R.id.multiChoiceContent);
                            values.put(question.getText().toString(), multiInput.get(i));

                        }




                    }


                }
            });


        }


    }


    public void addLayout(ViewGroup source, Question questionObject) {

        View toBeAdded = null;
        View topBorder = null;

        if (questionObject instanceof NumOnlyQuestion) {

            first++;
            toBeAdded = LayoutInflater.from(this).inflate(R.layout.num_only_question, source, false);

            NumOnlyQuestion info = (NumOnlyQuestion) questionObject;

            TextView question = (TextView) toBeAdded.findViewById(R.id.numQuestionText);
            EditText rangeHint = (EditText) toBeAdded.findViewById(R.id.numQuestionHint);

            question.setText(info.getQuestion());

            rangeHint.setHint("Enter a number between " + info.getLowBound() + " and " + info.getHighBound());


        }

        if (questionObject instanceof BoolQuestion) {
            second++;
            BoolQuestion info = (BoolQuestion) questionObject;
            toBeAdded = LayoutInflater.from(this).inflate(R.layout.bool_question, source, false);

            TextView question = (TextView) toBeAdded.findViewById(R.id.boolQuestionContent);

            RadioButton trueButton = (RadioButton) toBeAdded.findViewById(R.id.trueButton);
            trueButton.setOnClickListener(onlyOneToggled);

            RadioButton falseButton = (RadioButton) toBeAdded.findViewById(R.id.falseButton);
            falseButton.setOnClickListener(onlyOneToggled);

            question.setText(info.getQuestion());
            trueButton.setText(info.getTrueText());
            falseButton.setText(info.getFalseText());


        }

        if (questionObject instanceof TextFieldQuestion) {
            third++;
            TextFieldQuestion info = (TextFieldQuestion) questionObject;
            toBeAdded = LayoutInflater.from(this).inflate(R.layout.text_question, source, false);
            TextView question = (TextView) toBeAdded.findViewById(R.id.textQuestionContent);
            question.setText(info.getQuestion());

            EditText hint = (EditText) toBeAdded.findViewById(R.id.textQuestionHint);
            hint.setHint(info.getHint());

        }

        if (questionObject instanceof MultiChoiceQuestion) {

            fourth++;
            toBeAdded = LayoutInflater.from(this).inflate(R.layout.multi_choice_question, source, false);

            MultiChoiceQuestion info = (MultiChoiceQuestion) questionObject;

            TextView question = (TextView) toBeAdded.findViewById(R.id.multiChoiceContent);

            question.setText(info.getQuestion());

            labels = new RadioButton[6];

            labels[0] = (RadioButton) toBeAdded.findViewById(R.id.multiChoiceButton1);
            labels[1] = (RadioButton) toBeAdded.findViewById(R.id.multiChoiceButton2);
            labels[2] = (RadioButton) toBeAdded.findViewById(R.id.multiChoiceButton3);
            labels[3] = (RadioButton) toBeAdded.findViewById(R.id.multiChoiceButton4);
            labels[4] = (RadioButton) toBeAdded.findViewById(R.id.multiChoiceButton5);
            labels[5] = (RadioButton) toBeAdded.findViewById(R.id.multiChoiceButton6);

            for (int i = 0; i < labels.length; i++) {
                labels[i].setOnClickListener(onlyOneToggled);
            }

            int labelNum = info.getButtonLabels().length;

            for (int i = 0; i < labelNum; i++) {

                labels[i].setText(info.getButtonLabels()[i]);
                labels[i].setVisibility(View.VISIBLE);

            }


        }


        topBorder = LayoutInflater.from(this).inflate(R.layout.border_line, source, false);

        source.addView(topBorder);
        source.addView(toBeAdded);

        if (first == 1) {
            viewNum.add(toBeAdded);
        }

        if (second == 1) {
            viewBool.add(toBeAdded);
        }

        if (third == 1) {
            viewText.add(toBeAdded);
        }

        if (fourth == 1) {
            viewMulti.add(toBeAdded);
        }


        first = 0;
        second = 0;
        third = 0;
        fourth = 0;


    }


    public ArrayList<Question> readQuestionsFromFile() {

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream obs = new ObjectInputStream(fis);

            Object ob = obs.readObject();

            ArrayList<Question> temp = (ArrayList<Question>) ob;
            return temp;

        } catch (FileNotFoundException e) {
            Log.v("Dynamic Layout", e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.v("Dynamic Layout", e.getMessage());
        } catch (IOException e) {
            Log.v("Dynamic Layout", e.getMessage());
        }
        return null;

    }

    public boolean checkForLayout() {

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        dir.mkdirs();
        file = new File(dir, FILE_NAME);

        if (!file.exists()) {
            return false;
        } else {

            try {

                BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
                if (br.readLine() == null) {
                    Log.v("Dynamic Layout", "Empty File Found");
                    return false;
                }
                br.close();
                return true;

            } catch (FileNotFoundException e) {
                Log.v("Dynamic Layout", e.getMessage());
            } catch (IOException e) {
                Log.v("Dynamic Layout", e.getMessage());
            }


        }
        Log.v("Dynamic Layout", "Reached end of checker method - weird");
        return true;

    }


    public void printDatabase() {

        Log.v("Dynamic Layout", "MADE IT TO PRINT DB METHOD");
        SQLiteDatabase db = UIDatabaseInterface.getDatabase().getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM custom", null);
        int cols = cursor.getColumnCount();

        Log.v("Dynamic Layout", "COLUMN NUM: " + cols);
        Log.v("Dynamic Layout",  cursor.getColumnName(1));



                String data = "";
        SubmissionData sb;

        if (cursor.moveToFirst()) {
            Log.v("Dynamic Layout", "CURSOR MOVED TO FIRST");
            cursor.moveToFirst();
            if (cursor.getCount()>0 && cursor!=null) {
            Log.v("Dynamic Layout", "Hail Mary");

                while (cursor.isAfterLast() == false) {


                    String row = "";
                    for (int i = 0; i < cols; i++) {

                            row += cursor.getString(i) + "\n";
                     //       Log.v("Dynamic Layout", "get string " + cursor.getString(i));



                    }
                    Log.v("Dynamic Layout", "row is: " + row);


                    data = data + row;
                    cursor.moveToNext();
                }


            }
        }


    }
}
