package com.example.alex.myapplication;
//need to make checkboxes required

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by Oombliocarius on 3/6/16.
 *
 */
public class ControlledEnterDataActivity extends ActionBarActivity {


    BluetoothAdapter bl;
    ControlledEnterDataActivity ceda = this;
    public static TextView status;

    UUID uuid = UUID.fromString("e720951a-a29e-4772-b32e-7c60264d5c9b");
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.v("Mac Address", "weird");
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //discovery starts, we can show progress dialog or perform other tasks

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //discovery finishes, dismis progress dialog

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


                //     Log.v("Mac Address", device.getName());
                if(device.getAddress().equalsIgnoreCase("18:3b:d2:e1:88:59")) {
                    Log.v("Mac Address", device.getName() + "\n" + device.getAddress());
                  //  Aggro ag = new Aggro(uuid, ceda, device);
                  //  Thread t = new Thread(ag);
                  //  t.start();
                    bl.cancelDiscovery();
                    unregisterReceiver(mReceiver);
                }
            }
        }
    };





























    int l = 0;
    EnhancedRadioButton testo = null;

    String[] boolQuestions = new String[8];
    View[] allBoolSets = new View[8];
    View[][] boolOptions = new View[10][2];
    CheckBox[][] defenseCheck = new CheckBox[3][8];
    View[] defenseOptions = new View[3];
    Button blueButton;
    Button submitButton;
    CheckBox scoreNone;
    Button noShow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.data_entry_controlled);
      //
        status = (TextView)  findViewById(R.id.bluetoothStatus);
        TextView type = (TextView) findViewById(R.id.deviceType);
        type.setTypeface(Typeface.DEFAULT_BOLD);

        noShow = (Button) findViewById(R.id.noShow);
        CheckBox noScore = (CheckBox) findViewById(R.id.teleNone);
        noScore.setVisibility(View.INVISIBLE);



        String oh = (new BlueConnect().run(ceda, uuid, ceda));
        if(!oh.equals("master")) {
            TextView typo = (TextView) findViewById(R.id.deviceType);
            typo.setText("Sending Device");
        }
        else {
            TextView typo = (TextView) findViewById(R.id.deviceType);
            typo.setText("Receiving Device");
        }

      //  status.setTextColor(Color.BLACK);
       // status.setText("No Bluetooth Operations");





        View.OnClickListener checks = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Mac Address", "LISTENED");

                int result = findFrom2DArray(view, defenseCheck);
                int row = result / 100;
                int col = result % 10;
                Log.v("Mac Address", Integer.toString(result));
                if ((col % 2) == 0) {
                    CheckBox cb = (CheckBox) defenseCheck[row][col+1];
                    CheckBox orig = (CheckBox) defenseCheck[row][col];
                    Log.v("Mac Address", "YOU HIT: " + orig.getText() + " OF ROW: " + row);
                    if (cb.isChecked()) {
                        cb.setChecked(false);
                    }


                }
                if ((col % 2) == 1) {
                    Log.v("Mac Address", "REMAINDER 1");
                    //    Log.v("Mac Address", "ONE BEFORE IS: " + ("defense" + (col + 1)));
                    //    int magicId = defenseOptions[row].getResources().getIdentifier("defense" + (col - 1), "id", getPackageName());
                    CheckBox cb = (CheckBox) defenseCheck[row][col-1];
                    CheckBox orig = (CheckBox) defenseCheck[row][col];
                    Log.v("Mac Address", "YOU HIT: " + orig.getText() + " OF ROW: " + row);
                    //    Log.v("Mac Address", "YOU HIT: " + cb.getText());
                    if (cb.isChecked()) {
                        cb.setChecked(false);
                    }


                }

        Log.v("Mac Address", "Exiting");
            }
        };


        defenseOptions[0] = (View) findViewById(R.id.defenses1);
        defenseOptions[1] = (View) findViewById(R.id.defenses2);

        defenseOptions[2] = (View) findViewById(R.id.defenses3);

        int numo = 2;


             for(int l = 0; l < defenseCheck[0].length; l++) {
                 if(numo != 0) {
                     int magicId = defenseOptions[0].getResources().getIdentifier("defense" + numo, "id", getPackageName());
                 //    Log.v("Mac Address", "GETTING: " + "defense" + numo);
                     defenseCheck[0][l] = (CheckBox) findViewById(magicId);
                    defenseCheck[0][l].setOnClickListener(checks);
                     numo++;
                 }



        }

        defenseCheck[1][0] = (CheckBox) defenseOptions[1].findViewById(R.id.defense2);
        defenseCheck[1][1] = (CheckBox) defenseOptions[1].findViewById(R.id.defense3);
        defenseCheck[1][2] = (CheckBox) defenseOptions[1].findViewById(R.id.defense4);
        defenseCheck[1][3] = (CheckBox) defenseOptions[1].findViewById(R.id.defense5);
        defenseCheck[1][4] = (CheckBox) defenseOptions[1].findViewById(R.id.defense6);
        defenseCheck[1][5] = (CheckBox) defenseOptions[1].findViewById(R.id.defense7);
        defenseCheck[1][6] = (CheckBox) defenseOptions[1].findViewById(R.id.defense8);
        defenseCheck[1][7] = (CheckBox) defenseOptions[1].findViewById(R.id.defense9);

        defenseCheck[2][0] = (CheckBox) defenseOptions[2].findViewById(R.id.defense2);
        defenseCheck[2][1] = (CheckBox) defenseOptions[2].findViewById(R.id.defense3);
        defenseCheck[2][2] = (CheckBox) defenseOptions[2].findViewById(R.id.defense4);
        defenseCheck[2][3] = (CheckBox) defenseOptions[2].findViewById(R.id.defense5);
        defenseCheck[2][4] = (CheckBox) defenseOptions[2].findViewById(R.id.defense6);
        defenseCheck[2][5] = (CheckBox) defenseOptions[2].findViewById(R.id.defense7);
        defenseCheck[2][6] = (CheckBox) defenseOptions[2].findViewById(R.id.defense8);
        defenseCheck[2][7] = (CheckBox) defenseOptions[2].findViewById(R.id.defense9);







        ((CheckBox)defenseOptions[1].findViewById(R.id.defense2)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[1].findViewById(R.id.defense3)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[1].findViewById(R.id.defense4)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[1].findViewById(R.id.defense5)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[1].findViewById(R.id.defense6)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[1].findViewById(R.id.defense7)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[1].findViewById(R.id.defense8)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[1].findViewById(R.id.defense9)).setOnClickListener(checks);

        ((CheckBox)defenseOptions[2].findViewById(R.id.defense2)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[2].findViewById(R.id.defense3)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[2].findViewById(R.id.defense4)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[2].findViewById(R.id.defense5)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[2].findViewById(R.id.defense6)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[2].findViewById(R.id.defense7)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[2].findViewById(R.id.defense8)).setOnClickListener(checks);
        ((CheckBox)defenseOptions[2].findViewById(R.id.defense9)).setOnClickListener(checks);








        submitButton = (Button) findViewById(R.id.submit);


        boolQuestions[0] = "Did they cross the Outworks?";
        boolQuestions[1] = "Did they breach a Defense?";
        boolQuestions[2] = "Did they score?";
        boolQuestions[3] = "Did they breach any defenses, which?";
        boolQuestions[4] = "Did they fail to overcome any defenses, which?";
        boolQuestions[5] = "Are they a reliable scorer?";
        boolQuestions[6] = "Did they challenge the tower?";
        boolQuestions[7] = "Did they scale the tower?";
        int num = 1;
        for(int i = 0; i < 8; i++) {
        String id = "yesOrNo" + num;
            int resID = getResources().getIdentifier(id, "id", getPackageName());
            View test = findViewById(resID);
            allBoolSets[i] = test;
            TextView text = (TextView) test.findViewById(R.id.yes_or_no_entry_textView);
            text.setText(boolQuestions[i].toCharArray(), 0, boolQuestions[i].length());
            num++;
        }

        for(int i = 0; i < 8; i++) {

            boolOptions[i][0] = allBoolSets[i].findViewById(R.id.yes_or_no_entry_yesButton);
            boolOptions[i][1] = allBoolSets[i].findViewById(R.id.yes_or_no_entry_noButton);

        }
        View offense = findViewById(R.id.offense);
        View defense = findViewById(R.id.defense);

        final View low = findViewById(R.id.teleLow);
        View high = findViewById(R.id.teleHigh);


        boolOptions[8][0] = high;
        boolOptions[8][1] = low;
        boolOptions[9][0] = offense;
        boolOptions[9][1] = defense;




        View test = findViewById(R.id.yesOrNo1);
        final TextView next = (TextView) test.findViewById(R.id.yes_or_no_entry_textView);
        blueButton = (Button) findViewById(R.id.bluetoothSync);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int teamNum = -1;
                int matchNum = -1;
                boolean isFirst = true;
                //YorNs
                String outworks = "no";
                String autoBreachDef = "no";
                String autoScore = "no";
                String teleBreachDef = "no";
                String attemptedDef = "no";
                String reliable = "no";
                String challenged = "no";
                String scaled = "no";
                //checkboxes
                String whereShoot = "no";
                String playStyle = "no";
                //weirdo
                String highOrLowA = "no";
                //defense list composite strings
                String whichBreached = "no";
                String obstaclesOvercome = "no";
                String obstaclesFailed = "no";
                //comments
                String comments = "no";

                //GET DATA SECTION ************************************************************************
                EditText specifics = null;
                String temp = "";
                EnhancedRadioButton eh = null;
                CheckBox junkCheck = null;

                View defLists = null;
                CheckBox noScore = (CheckBox) findViewById(R.id.teleNone);

                boolean canContinue = true;





                specifics = (EditText) findViewById(R.id.teamNumber);

                temp = specifics.getText().toString();
                    if(!temp.equals("")) {
                        teamNum = Integer.valueOf(temp);
                    }
                    else {
                        canContinue = false;
                    }


                specifics = (EditText) findViewById(R.id.matchNumber);
                    temp = specifics.getText().toString();
                    if(!temp.equals("")) {
                        matchNum = Integer.valueOf(temp);
                    }
                    else {
                        canContinue = false;
                    }



                specifics = (EditText) findViewById(R.id.comments);
                comments = specifics.getText().toString();


                eh = (EnhancedRadioButton) boolOptions[0][0].findViewById(R.id.yes_or_no_entry_yesButton);

                if(eh.isChecked()) {
                outworks = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[0][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                outworks = "false";
                }


                eh = (EnhancedRadioButton) boolOptions[1][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    autoBreachDef = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[1][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    autoBreachDef = "false";
                }

                eh = (EnhancedRadioButton) boolOptions[2][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    autoScore = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[2][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    autoScore = "false";
                }

                eh = (EnhancedRadioButton) boolOptions[3][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    teleBreachDef = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[3][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    teleBreachDef = "false";
                }

                eh = (EnhancedRadioButton) boolOptions[4][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    attemptedDef = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[4][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    attemptedDef = "false";
                }

                eh = (EnhancedRadioButton) boolOptions[5][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    reliable = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[5][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    reliable = "false";
                }

                eh = (EnhancedRadioButton) boolOptions[6][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    challenged = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[6][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    challenged = "false";
                }

                eh = (EnhancedRadioButton) boolOptions[7][0].findViewById(R.id.yes_or_no_entry_yesButton);
                if(eh.isChecked()) {
                    scaled = "true";
                }
                eh = (EnhancedRadioButton) boolOptions[7][1].findViewById(R.id.yes_or_no_entry_noButton);
                if(eh.isChecked()) {
                    scaled = "false";
                }
                junkCheck = (CheckBox) boolOptions[8][0];
                if(junkCheck.isChecked()) {
                    whereShoot = "tHiigh";
                    Log.v("Mac Address", "Where shoot check 1");
                }
                junkCheck = (CheckBox) boolOptions[8][1];
                if(junkCheck.isChecked()) {
                    whereShoot = "tLoow";
                    Log.v("Mac Address", "Where shoot check 2");
                }

                junkCheck = (CheckBox) boolOptions[9][0];
                if(junkCheck.isChecked()) {
                    playStyle = "true";
                }
                junkCheck = (CheckBox) boolOptions[9][1];
                if(junkCheck.isChecked()) {
                    playStyle = "false";
                }

                //Special checkboxes ( did they score in auto) and defense lists to go


                defLists = (View) findViewById(R.id.defenses1);
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense1);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        whichBreached = "";
                        whichBreached = whichBreached + "Low Bar";
                        isFirst = false;
                    }
                    else {
                        whichBreached = whichBreached + "," + "Low Bar";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense2);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        whichBreached = "";
                        whichBreached = whichBreached + "Portcullis";
                        isFirst = false;
                    }
                    else {
                        whichBreached = whichBreached + "," + "Portcullis";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense3);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        whichBreached = "";
                        whichBreached = whichBreached + "Cheval de Frise";
                        isFirst = false;
                    }
                    else {
                        whichBreached = whichBreached + "," + "Cheval de Frise";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense4);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        whichBreached = "";
                        whichBreached = whichBreached + "Moat";
                        isFirst = false;
                    }
                    else {
                        whichBreached = whichBreached + "," + "Moat";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense5);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        whichBreached = "";
                        whichBreached = whichBreached + "Ramparts";
                        isFirst = false;
                    }
                    else {
                        whichBreached = whichBreached + "," + "Ramparts";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense6);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        whichBreached = "";
                        whichBreached = whichBreached + "Drawbridge";
                        isFirst = false;
                    }
                    else {
                        whichBreached = whichBreached + "," + "Drawbridge";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense7);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        whichBreached = "";
                        whichBreached = whichBreached + "Sally Port";
                        isFirst = false;
                    }
                    else {
                        whichBreached = whichBreached + "," + "Sally Port";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense8);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        whichBreached = "";
                        whichBreached = whichBreached + "Rock Wall";
                        isFirst = false;
                    }
                    else {
                        whichBreached = whichBreached + "," + "Rock Wall";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense9);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        whichBreached = "";
                        whichBreached = whichBreached + "Rough Terrain";
                        isFirst = false;
                    }
                    else {
                        whichBreached = whichBreached + "," + "Rough Terrain";
                    }
                }

                isFirst = true;


                defLists = (View) findViewById(R.id.defenses2);
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense1);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesOvercome = "";
                        obstaclesOvercome = obstaclesOvercome + "Low Bar";
                        isFirst = false;
                    }
                    else {
                        obstaclesOvercome = obstaclesOvercome + "," + "Low Bar";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense2);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesOvercome = "";
                        obstaclesOvercome = obstaclesOvercome + "Portcullis";
                        isFirst = false;
                    }
                    else {
                        obstaclesOvercome = obstaclesOvercome + "," + "Portcullis";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense3);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesOvercome = "";
                        obstaclesOvercome = obstaclesOvercome + "Cheval de Frise";
                        isFirst = false;
                    }
                    else {
                        obstaclesOvercome = obstaclesOvercome + "," + "Cheval de Frise";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense4);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesOvercome = "";
                        obstaclesOvercome = obstaclesOvercome + "Moat";
                        isFirst = false;
                    }
                    else {
                        obstaclesOvercome = obstaclesOvercome + "," + "Moat";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense5);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesOvercome = "";
                        obstaclesOvercome = obstaclesOvercome + "Ramparts";
                        isFirst = false;
                    }
                    else {
                        obstaclesOvercome = obstaclesOvercome + "," + "Ramparts";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense6);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesOvercome = "";
                        obstaclesOvercome = obstaclesOvercome + "Drawbridge";
                        isFirst = false;
                    }
                    else {
                        obstaclesOvercome = obstaclesOvercome + "," + "Drawbridge";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense7);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesOvercome = "";
                        obstaclesOvercome = obstaclesOvercome + "Sally Port";
                        isFirst = false;
                    }
                    else {
                        obstaclesOvercome = obstaclesOvercome + "," + "Sally Port";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense8);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesOvercome = "";
                        obstaclesOvercome = obstaclesOvercome + "Rock Wall";
                        isFirst = false;
                    }
                    else {
                        obstaclesOvercome = obstaclesOvercome + "," + "Rock Wall";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense9);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesOvercome = "";
                        obstaclesOvercome = obstaclesOvercome + "Rough Terrain";
                        isFirst = false;
                    }
                    else {
                        obstaclesOvercome = obstaclesOvercome + "," + "Rough Terrain";
                    }
                }

                isFirst = true;

                defLists = (View) findViewById(R.id.defenses3);
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense1);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesFailed = "";
                        obstaclesFailed = obstaclesFailed + "Low Bar";
                        isFirst = false;
                    }
                    else {
                        obstaclesFailed = obstaclesFailed + "," + "Low Bar";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense2);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesFailed = "";
                        obstaclesFailed = obstaclesFailed + "Portcullis";
                        isFirst = false;
                    }
                    else {
                        obstaclesFailed = obstaclesFailed + "," + "Portcullis";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense3);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesFailed = "";
                        obstaclesFailed = obstaclesFailed + "Cheval de Frise";
                        isFirst = false;
                    }
                    else {
                        obstaclesFailed = obstaclesFailed + "," + "Cheval de Frise";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense4);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesFailed = "";
                        obstaclesFailed = obstaclesFailed + "Moat";
                        isFirst = false;
                    }
                    else {
                        obstaclesFailed = obstaclesFailed + "," + "Moat";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense5);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesFailed = "";
                        obstaclesFailed = obstaclesFailed + "Ramparts";
                        isFirst = false;
                    }
                    else {
                        obstaclesFailed = obstaclesFailed + "," + "Ramparts";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense6);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesFailed = "";
                        obstaclesFailed = obstaclesFailed + "Drawbridge";
                        isFirst = false;
                    }
                    else {
                        obstaclesFailed = obstaclesFailed + "," + "Drawbridge";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense7);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesFailed = "";
                        obstaclesFailed = obstaclesFailed + "Sally Port";
                        isFirst = false;
                    }
                    else {
                        obstaclesFailed = obstaclesFailed + "," + "Sally Port";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense8);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesFailed = "";
                        obstaclesFailed = obstaclesFailed + "Rock Wall";
                        isFirst = false;
                    }
                    else {
                        obstaclesFailed = obstaclesFailed + "," + "Rock Wall";
                    }
                }
                junkCheck = (CheckBox) defLists.findViewById(R.id.defense9);
                if(junkCheck.isChecked()) {
                    if(isFirst) {
                        obstaclesFailed = "";
                        obstaclesFailed = obstaclesFailed + "Rough Terrain";
                        isFirst = false;
                    }
                    else {
                        obstaclesFailed = obstaclesFailed + "," + "Rough Terrain";
                    }
                }



                junkCheck = (CheckBox) findViewById(R.id.autoLow);
                if(junkCheck.isChecked()) {
                    highOrLowA = "";
                    highOrLowA += "low";
                }

                junkCheck = (CheckBox) findViewById(R.id.autoHigh);
                if(junkCheck.isChecked()) {
                    if(highOrLowA.equals("no")) {
                        highOrLowA = "";
                    }
                    highOrLowA += ", high";
                }

                junkCheck = (CheckBox) findViewById(R.id.teleNone);
                if(junkCheck.isChecked()) {
                    whereShoot = "NONE";
                }








                //END GET DATA SECTION ********************************************************************
                //VERIFY ENTRY SECTION ********************************************************************


                    if(teamNum == -1){canContinue = false;};
                    if(matchNum == -1){canContinue = false;};
                    if(outworks.equals("no")){canContinue = false;};
                    if(autoBreachDef.equals("no")){canContinue = false;};
                    if(autoScore.equals("no")){canContinue = false;};
                    if(teleBreachDef.equals("no")){canContinue = false;};
                    if(attemptedDef.equals("no")){canContinue = false;};
                    if(reliable.equals("no")){canContinue = false;};
                    if(challenged.equals("no")){canContinue = false;};
                    if(scaled.equals("no")){canContinue = false;};
                    if(playStyle.equals("no")){canContinue = false;};
                    if(whereShoot.equals("no")) {
                        Log.v("Mac Address", "unexpected");
                        canContinue = false;};

                    if(autoScore.equals("true")) {
                        if(highOrLowA.equals("no")) {
                            canContinue = false;
                        }
                    }

                    if(autoBreachDef.equals("false")) {
                        whichBreached = "";
                    }

                    if(teleBreachDef.equals("false")) {
                        obstaclesOvercome = "";
                    }
                    if(attemptedDef.equals("false")) {
                        obstaclesFailed = "";
                    }

                if(autoBreachDef.equals("true")) {
                    if(whichBreached.equals("no")) {
                        canContinue = false;
                    }
                }

                if(autoScore.equals("false")) {
                    highOrLowA = "";
                }

                if(teleBreachDef.equals("true")) {
                    if(obstaclesOvercome.equals("no")) {
                        canContinue = false;
                    }
                }
                if(attemptedDef.equals("true")) {
                    if(obstaclesFailed.equals("no")) {
                        canContinue = false;
                    }
                }




                //END VERIFY ENTRY SECTION ****************************************************************
                //RESET LAYOUT SECTION ********************************************************************
                if(canContinue) {
                    String bigId = "defenses";
                    int bigNumId = 0;

                    for (int l = 0; l < 3; l++) {

                        bigId = bigId + (l + 1);
                        bigNumId = getResources().getIdentifier(bigId, "id", getPackageName());
                        Log.v("Mac Address", "The big view is: " + bigId);
                        View defOptions = findViewById(bigNumId);

                        CheckBox cb = null;

                        cb = (CheckBox) defOptions.findViewById(R.id.defense1);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense2);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense3);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense4);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense5);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense6);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense7);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense8);
                        cb.setChecked(false);
                        cb = (CheckBox) defOptions.findViewById(R.id.defense9);
                        cb.setChecked(false);


                        cb = (CheckBox) findViewById(R.id.teleNone);
                        cb.setChecked(false);

                        bigId = "defenses";
                    }
                    CheckBox cb = null;
                    cb = (CheckBox) findViewById(R.id.autoHigh);
                    cb.setChecked(false);
                    cb = (CheckBox) findViewById(R.id.autoLow);
                    cb.setChecked(false);




                    for (int i = 0; i < boolOptions.length; i++) {

                        if (boolOptions[i][0] instanceof EnhancedRadioButton) {
                            EnhancedRadioButton ehr = (EnhancedRadioButton) boolOptions[i][0];
                            ehr.setChecked(false);
                            ehr = (EnhancedRadioButton) boolOptions[i][1];
                            ehr.setChecked(false);

                        }
                        if (boolOptions[i][0] instanceof CheckBox) {


                                CheckBox ehr = (CheckBox) boolOptions[i][0];
                                ehr.setChecked(false);
                                ehr = (CheckBox) boolOptions[i][1];
                                ehr.setChecked(false);


                        }


                    }

                    EditText nums = (EditText) findViewById(R.id.matchNumber);

                    nums.setText("".toCharArray(), 0, 0);
                    nums = (EditText) findViewById(R.id.teamNumber);
                    nums.setText("".toCharArray(), 0, 0);
                    nums = (EditText) findViewById(R.id.comments);
                    nums.setText("".toCharArray(), 0, 0);


                    //END OF RESET LAYOUT SECTION ********************************************************************

                    //PRINT TIME ******************************************************************************************
                    Log.v("Mac Address", String.valueOf(teamNum));
                    Log.v("Mac Address", String.valueOf(matchNum));
                    Log.v("Mac Address", outworks);
                    Log.v("Mac Address", autoBreachDef);
                    Log.v("Mac Address", autoScore);
                    Log.v("Mac Address", teleBreachDef);
                    Log.v("Mac Address", attemptedDef);
                    Log.v("Mac Address", reliable);
                    Log.v("Mac Address", challenged);
                    Log.v("Mac Address", scaled);
                    Log.v("Mac Address", whereShoot);
                    Log.v("Mac Address", playStyle);
                    Log.v("Mac Address", highOrLowA);
                    Log.v("Mac Address", whichBreached);
                    Log.v("Mac Address", obstaclesOvercome);
                    Log.v("Mac Address", obstaclesFailed);
                    Log.v("Mac Address", comments);

                    ContentValues values = new ContentValues();

                    values.put("Team_Number", teamNum);
                    values.put("Match_Number", matchNum);
                    //values.put("Event_Name",);
                    values.put("crossOutW", outworks);
                    values.put("breachD", autoBreachDef);
                    values.put("whichBreached", whichBreached);
                    values.put("didScore", autoScore);
                    values.put("highOrLowA", highOrLowA);
                    values.put("didOvercome", teleBreachDef);
                    values.put("obstaclesOvercome", obstaclesOvercome);
                    values.put("failed", attemptedDef);
                    values.put("obstaclesFailed", obstaclesFailed);
                    values.put("highOrLowT", whereShoot);
                    values.put("reliability", reliable);
                    values.put("offOrDef", playStyle);
                    values.put("didChallenge", challenged);
                    values.put("didScale", scaled);
                    values.put("Comments", comments);

                    UIDatabaseInterface.getDatabase().addValues("Performance", values);
                    TextView status = (TextView) findViewById(R.id.submitStatus);
                    status.setTextColor(Color.GREEN);
                    status.setText("Success: Submission Entered");

                }
                else {
                    TextView status = (TextView) findViewById(R.id.submitStatus);
                    status.setTextColor(Color.RED);
                    status.setText("Failed: Missing Data".toCharArray(), 0, "Failed: Missing Data".length());
                }

            }
        });


        View.OnClickListener noPresence = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText teamNum = (EditText) findViewById(R.id.teamNumber);
                EditText matchNum = (EditText) findViewById(R.id.matchNumber);
                if(!teamNum.getText().toString().equals("")) {
                    if(!matchNum.getText().toString().equals("")) {

                        String bigId = "defenses";
                        int bigNumId = 0;


                        for (int l = 0; l < 3; l++) {

                            bigId = bigId + (l + 1);
                            bigNumId = getResources().getIdentifier(bigId, "id", getPackageName());
                            Log.v("Mac Address", "The big view is: " + bigId);
                            View defOptions = findViewById(bigNumId);

                            CheckBox cb = null;

                            cb = (CheckBox) defOptions.findViewById(R.id.defense1);
                            cb.setChecked(false);
                            cb = (CheckBox) defOptions.findViewById(R.id.defense2);
                            cb.setChecked(false);
                            cb = (CheckBox) defOptions.findViewById(R.id.defense3);
                            cb.setChecked(false);
                            cb = (CheckBox) defOptions.findViewById(R.id.defense4);
                            cb.setChecked(false);
                            cb = (CheckBox) defOptions.findViewById(R.id.defense5);
                            cb.setChecked(false);
                            cb = (CheckBox) defOptions.findViewById(R.id.defense6);
                            cb.setChecked(false);
                            cb = (CheckBox) defOptions.findViewById(R.id.defense7);
                            cb.setChecked(false);
                            cb = (CheckBox) defOptions.findViewById(R.id.defense8);
                            cb.setChecked(false);
                            cb = (CheckBox) defOptions.findViewById(R.id.defense9);
                            cb.setChecked(false);

                            bigId = "defenses";
                        }
                        CheckBox cb = null;
                        cb = (CheckBox) findViewById(R.id.autoHigh);
                        cb.setChecked(false);
                        cb = (CheckBox) findViewById(R.id.autoLow);
                        cb.setChecked(false);




                        for (int i = 0; i < boolOptions.length; i++) {

                            if (boolOptions[i][0] instanceof EnhancedRadioButton) {
                                EnhancedRadioButton ehr = (EnhancedRadioButton) boolOptions[i][0];
                                ehr.setChecked(false);
                                ehr = (EnhancedRadioButton) boolOptions[i][1];
                                ehr.setChecked(false);

                            }
                            if (boolOptions[i][0] instanceof CheckBox) {


                                CheckBox ehr = (CheckBox) boolOptions[i][0];
                                ehr.setChecked(false);
                                ehr = (CheckBox) boolOptions[i][1];
                                ehr.setChecked(false);


                            }
                        }
                            ContentValues values = new ContentValues();

                            values.put("Team_Number", teamNum.getText().toString());
                            values.put("Match_Number", matchNum.getText().toString());
                            //values.put("Event_Name",);
                            values.put("crossOutW", " ");
                            values.put("breachD", " ");
                            values.put("whichBreached", " ");
                            values.put("didScore", " ");
                            values.put("highOrLowA", " ");
                            values.put("didOvercome", " ");
                            values.put("obstaclesOvercome", " ");
                            values.put("failed", " ");
                            values.put("obstaclesFailed", " ");
                            values.put("highOrLowT", " ");
                            values.put("reliability", " ");
                            values.put("offOrDef", " ");
                            values.put("didChallenge", " ");
                            values.put("didScale", " ");
                            values.put("Comments", "NO SHOW");

                            UIDatabaseInterface.getDatabase().addValues("Performance", values);










                        TextView edo = (TextView) findViewById(R.id.noShowStatus);
                        edo.setTextColor(Color.GREEN);
                        edo.setText("Submission Successfully Entered");

                    }

                }
                if(teamNum.getText().toString().equals("") || matchNum.getText().toString().equals("")) {

                    TextView ed = (TextView) findViewById(R.id.noShowStatus);
                    ed.setTextColor(Color.RED);
                    ed.setText("Missing Data");

                }
                EditText nums = (EditText) findViewById(R.id.matchNumber);

                nums.setText("".toCharArray(), 0, 0);
                nums = (EditText) findViewById(R.id.teamNumber);
                nums.setText("".toCharArray(), 0, 0);
                nums = (EditText) findViewById(R.id.comments);
                nums.setText("".toCharArray(), 0, 0);

            }
        };






        View.OnClickListener checkToggles = new View.OnClickListener(){
            public void  onClick  (View  v) {
                TextView status = (TextView) findViewById(R.id.submitStatus);
                status.setText("");
                status.setTextColor(Color.BLACK);
                int row = 0;
                int col = 0;

                    int con = 0;

                    con = findButtonInArray(v);
                    row = con / 10;
                    col = con % 10;
                    Log.v("Mac Address", "ROW IS: " + row);
                    Log.v("Mac Address", "COL IS: " + col);

                    if (v instanceof EnhancedRadioButton) {
                        Log.v("Mac Address", "Button");
                        if (col == 0) {
                            EnhancedRadioButton ehr = (EnhancedRadioButton) boolOptions[row][1];
                            if (ehr.isChecked()) {
                                Log.v("Mac Address", "Tried to toggle the " + ehr.getText() + " button");
                                ehr.setChecked(false);
                            }
                        }

                        if (col == 1) {
                            EnhancedRadioButton ehr = (EnhancedRadioButton) boolOptions[row][0];
                            if (ehr.isChecked()) {
                                Log.v("Mac Address", "Tried to toggle the " + ehr.getText() + " button");
                                ehr.setChecked(false);
                            }
                        }

                    }

                    if (v instanceof CheckBox) {
                        Log.v("Mac Address", "CheckBox");


                        if (col == 0) {
                            CheckBox cb = (CheckBox) boolOptions[row][1];

                            if (cb.isChecked()) {
                                cb.setChecked(false);
                            }
                        }
                        if (col == 1) {
                            CheckBox cb = (CheckBox) boolOptions[row][0];

                            if (cb.isChecked()) {
                                cb.setChecked(false);
                            }
                        }
                    }

                }

        };

        for(int i = 0; i < boolOptions.length; i++) {

            for(int l = 0; l < boolOptions[i].length; l++) {
                boolOptions[i][l].setOnClickListener(checkToggles);

            }

        }
        noShow.setOnClickListener(noPresence);

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status.setText("Trying to establish connection");



                String oh = (new BlueConnect().run(ceda, uuid, ceda));
                if(!oh.equals("master")) {
                    new Thread()
                    {
                        public void run()
                        {
                            ceda.runOnUiThread(new Runnable() {
                                public void run() {
                                    //Do your UI operations like dialog opening or Toast here
                                    status.setText("Trying to send...");
                                    AlertDialog alertDialog = new AlertDialog.Builder(ceda).create();
                                    alertDialog.setTitle("Alert");
                                    alertDialog.setMessage("Beginning bluetooth data sending process");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();

                                }
                            });
                        }
                    }.start();




                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(mReceiver, filter);
                    bl = BluetoothAdapter.getDefaultAdapter();
                    Log.v("Mac Address", "Made it to listener");

                    if (!bl.isEnabled()) {
                        bl.enable();
                        Log.v("Mac Address", "Had to");
                    }
                    if(!bl.isEnabled()) {
                        bl.enable();
                        Log.v("Mac Address", "WTF");
                    }
                    bl.startDiscovery();
                }
                else {

                    new Thread()
                    {
                        public void run()
                        {
                            ceda.runOnUiThread(new Runnable() {
                                public void run() {
                                    //Do your UI operations like dialog opening or Toast here
                                    status.setText("Listening...");
                                    AlertDialog alertDialog = new AlertDialog.Builder(ceda).create();
                                    alertDialog.setTitle("Alert");
                                    alertDialog.setMessage("Beginning bluetooth data receiving process");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();

                                }
                            });
                        }
                    }.start();

                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
                    startActivity(discoverableIntent);
                }


            }
        });












    }

    public int findButtonInArray(View view) {
        for(int i = 0; i < boolOptions.length; i++) {

            for(int l = 0; l < boolOptions[i].length; l++) {

                if(boolOptions[i][l].equals(view)) {
                    return ((i*10) + l);
                }

            }

        }
        return 0;


    }

    public int findFrom2DArray(View v,  CheckBox[][] data) {
        CheckBox vo = (CheckBox) v;
        for(int i = 0; i < data.length; i++) {

            for(int l = 0; l < data[0].length; l++) {

                if(vo.equals(data[i][l])) {
                    return (i*100 + l);
                }

            }


        }
        return -1;


    }



}
