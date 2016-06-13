package com.example.alex.myapplication;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by Oombliocarius on 3/12/16.
 */
public class ExportDataActivity extends ActionBarActivity {




    BluetoothAdapter bl;
    ExportDataActivity eda;
    public static TextView status;
    File file = null;
    final String FILE_NAME = "custom.txt";

    String blueAddress;

    UUID uuid = UUID.fromString("e720951a-a29e-4772-b32e-7c60264d5c9b");
    private BroadcastReceiver mReceiver;
    ArrayList<Question> questions = null;
    static boolean layoutOrNot = true;


















    SQLiteDatabase db;
    Context context;
    EditText ed = null;
    RadioButton sendData, getData, sendLayout, getLayout;
    EditText[] address = new EditText[6];
    TextView[] colons = new TextView[5];
    RadioButton[] bluetoothActions = new RadioButton[3];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eda = this;
        context = this.getBaseContext();

        setContentView(R.layout.transfer_data);


        View.OnClickListener oneToggled = new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                RadioButton temp = (RadioButton) v;

                bluetoothActions[0].setChecked(false);
                bluetoothActions[1].setChecked(false);

                bluetoothActions[2].setChecked(false);

                temp.setChecked(true);

                if(temp.equals(bluetoothActions[2])) {

                    for(int i = 0; i < address.length; i++) {
                        EditText vo = address[i];
                        vo.setVisibility(View.GONE);
                    }

                    for(int i = 0; i < colons.length; i++) {
                        TextView vo = colons[i];
                        vo.setVisibility(View.GONE);
                    }
                    TextView tvo = (TextView) findViewById(R.id.bluetoothAddressHeader);
                    tvo.setVisibility(View.GONE);

                }

                else {

                    for(int i = 0; i < address.length; i++) {
                        EditText vo = address[i];
                        vo.setVisibility(View.VISIBLE);
                    }

                    for(int i = 0; i < colons.length; i++) {
                        TextView vo = colons[i];
                        vo.setVisibility(View.VISIBLE);
                    }
                    TextView tvo = (TextView) findViewById(R.id.bluetoothAddressHeader);
                    tvo.setVisibility(View.VISIBLE);

                }


            }


        };


        address[0] = (EditText) findViewById(R.id.address1);
        address[1] = (EditText) findViewById(R.id.address2);
        address[2] = (EditText) findViewById(R.id.address3);
        address[3] = (EditText) findViewById(R.id.address4);
        address[4] = (EditText) findViewById(R.id.address5);
        address[5] = (EditText) findViewById(R.id.address6);


        InputFilter[] filters = new InputFilter[] { new InputFilter.AllCaps() };

        address[0].setFilters(filters);
        address[1].setFilters(filters);
        address[2].setFilters(filters);
        address[3].setFilters(filters);
        address[4].setFilters(filters);
        address[5].setFilters(filters);

        address[0].setTextColor(Color.BLACK);
        address[1].setTextColor(Color.BLACK);
        address[2].setTextColor(Color.BLACK);
        address[3].setTextColor(Color.BLACK);
        address[4].setTextColor(Color.BLACK);
        address[5].setTextColor(Color.BLACK);

        colons[0] = (TextView) findViewById(R.id.colon1);
        colons[1] = (TextView) findViewById(R.id.colon2);
        colons[2] = (TextView) findViewById(R.id.colon3);
        colons[3] = (TextView) findViewById(R.id.colon4);
        colons[4] = (TextView) findViewById(R.id.colon5);


        //address = (EditText) findViewById(R.id.address);

        //address.setFilters(new InputFilter[] {new InputFilter.AllCaps()});





        bluetoothActions[0] = (RadioButton) findViewById(R.id.sendData);
        bluetoothActions[1] = (RadioButton) findViewById(R.id.sendLayout);
        bluetoothActions[2] = (RadioButton) findViewById(R.id.receiveLayout);


        bluetoothActions[0].setOnClickListener(oneToggled);
        bluetoothActions[1].setOnClickListener(oneToggled);
        bluetoothActions[2].setOnClickListener(oneToggled);




        Button beginBluetooth = (Button) findViewById(R.id.beginProcess);

        beginBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean canContinue = true;
                boolean canContinueAction = true;
                blueAddress = "";
                String action = "";
                boolean needCheck = false;

                for (int i = 0; i < bluetoothActions.length; i++) {

                    if (bluetoothActions[i].isChecked()) {
                        action = bluetoothActions[i].getText().toString();
                    }

                }

                if (action.equals("Send Custom Layout") || action.equals("Send Collected Data")) {
                    needCheck = true;
                    for (int i = 0; i < address.length; i++) {

                        if (i == address.length - 1) {
                            Log.v("Transfer Data", "DATA SEGMENT: " + address[i].getText().toString());
                            blueAddress += address[i].getText().toString();

                            if (address[i].getText().toString().length() != 2) {
                                canContinue = false;
                            }

                        } else {
                            Log.v("Transfer Data", "DATA SEGMENT: " + address[i].getText().toString());
                            blueAddress += address[i].getText().toString() + ":";


                            if (address[i].getText().toString().length() != 2) {
                                canContinue = false;
                            }

                        }

                    }
                }
                Log.v("Transfer Data", blueAddress);


                if (action.equals("")) {
                    canContinueAction = false;
                }


                //GETTING AND CHECKING USER INPUT SECTION ENDING HERE
                //GETTING AND CHECKING USER INPUT SECTION ENDING HERE
                //GETTING AND CHECKING USER INPUT SECTION ENDING HERE
                Log.v("Transfer Data", "CAN CONTINUE ACTION: " + canContinueAction);
                Log.v("Transfer Data", "CAN CONTINUE: " + canContinue);
                Log.v("Transfer Data", "NEED CHECK: " + needCheck);

                boolean go = true;
                if (needCheck) {

                    if (!canContinue) {
                        go = false;
                    }

                }

                if (canContinueAction) {

                    if (go) {
                        address[0].setText("");
                        address[0].setHintTextColor(Color.BLACK);
                        address[0].setHint("");

                        address[1].setText("");
                        address[1].setHintTextColor(Color.BLACK);
                        address[1].setHint("");

                        address[2].setText("");
                        address[2].setTextColor(Color.BLACK);
                        address[2].setHint("");

                        address[3].setText("");
                        address[3].setHintTextColor(Color.BLACK);
                        address[3].setHint("");

                        address[4].setText("");
                        address[4].setHintTextColor(Color.BLACK);
                        address[4].setHint("");

                        address[5].setText("");
                        address[5].setHintTextColor(Color.BLACK);
                        address[5].setHint("");

                        if (action.equals("Receive Sent Layout/Data")) {

                            new Thread() {
                                public void run() {
                                    eda.runOnUiThread(new Runnable() {
                                        public void run() {
                                            //Do your UI operations like dialog opening or Toast here

                                            AlertDialog alertDialog = new AlertDialog.Builder(eda).create();
                                            alertDialog.setTitle("Alert");
                                            alertDialog.setMessage("Beginning bluetooth layout receiving process");
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

                            Listener listen = new Listener();
                            listen.setUUID(uuid);
                            listen.setEda(eda);
                            listen.setContext(eda.getBaseContext());
                            Thread t = new Thread(listen);
                            t.start();

                            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
                            startActivity(discoverableIntent);


                        }


                        if (action.equals("Send Custom Layout")) {

                            if (checkForLayout()) {
                                questions = readQuestionsFromFile();
                                mReceiver = new BroadcastReceiver() {
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
                                            if (device.getAddress().equalsIgnoreCase(blueAddress)) {
                                                layoutOrNot = true;
                                                Log.v("Mac Address", device.getName() + "\n" + device.getAddress());
                                                Aggro ag = new Aggro(uuid, eda, device, questions, blueAddress);
                                                Thread t = new Thread(ag);
                                                t.start();
                                                bl.cancelDiscovery();
                                                unregisterReceiver(mReceiver);
                                            }
                                        }
                                    }
                                };


                                new Thread() {
                                    public void run() {
                                        eda.runOnUiThread(new Runnable() {
                                            public void run() {
                                                //Do your UI operations like dialog opening or Toast here

                                                AlertDialog alertDialog = new AlertDialog.Builder(eda).create();
                                                alertDialog.setTitle("Alert");
                                                alertDialog.setMessage("Beginning bluetooth Layout sending process");
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
                                Log.v("Transfer Data", "Made it to listener");

                                if (!bl.isEnabled()) {
                                    bl.enable();
                                    Log.v("Transfer Data", "Had to");
                                }
                                if (!bl.isEnabled()) {
                                    bl.enable();
                                    Log.v("Transfer Data", "WTF");
                                }
                                bl.startDiscovery();


                            }
                        }


                        if (action.equals("Send Collected Data")) {


                            mReceiver = new BroadcastReceiver() {
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
                                        if (device.getAddress().equalsIgnoreCase(blueAddress)) {
                                            layoutOrNot = false;
                                            Log.v("Mac Address", device.getName() + "\n" + device.getAddress());
                                            Aggro ag = new Aggro(uuid, eda, device, blueAddress);
                                            Thread t = new Thread(ag);
                                            t.start();
                                            bl.cancelDiscovery();
                                            unregisterReceiver(mReceiver);
                                        }
                                    }
                                }
                            };


                            new Thread() {
                                public void run() {
                                    eda.runOnUiThread(new Runnable() {
                                        public void run() {
                                            //Do your UI operations like dialog opening or Toast here

                                            AlertDialog alertDialog = new AlertDialog.Builder(eda).create();
                                            alertDialog.setTitle("Alert");
                                            alertDialog.setMessage("Beginning bluetooth Layout sending process");
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
                            Log.v("Transfer Data", "Made it to listener");

                            if (!bl.isEnabled()) {
                                bl.enable();
                                Log.v("Transfer Data", "Had to");
                            }
                            if (!bl.isEnabled()) {
                                bl.enable();
                                Log.v("Transfer Data", "WTF");
                            }
                            bl.startDiscovery();


                        }

                    }
                } else if (needCheck && !canContinue) {
                    address[0].setText("");
                    address[0].setHintTextColor(Color.RED);
                    address[0].setHint("!!");

                    address[1].setText("");
                    address[1].setHintTextColor(Color.RED);
                    address[1].setHint("!!");

                    address[2].setText("");
                    address[2].setHintTextColor(Color.RED);
                    address[2].setHint("!!");

                    address[3].setText("");
                    address[3].setHintTextColor(Color.RED);
                    address[3].setHint("!!");

                    address[4].setText("");
                    address[4].setHintTextColor(Color.RED);
                    address[4].setHint("!!");

                    address[5].setText("");
                    address[5].setHintTextColor(Color.RED);
                    address[5].setHint("!!");

                }
            }
        });





        Button export = (Button) findViewById(R.id.exportButton);



        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ed = (EditText) findViewById(R.id.fileName);
                if(ed.getText().toString().equals("")) {
                    ed.setHintTextColor(Color.RED);
                    ed.setHint("Enter File Name");
                }
                else {
                    String entered = ed.getText().toString();
                    backupDatabaseCSV(entered + ".csv");
                 //   readFile(entered + "magio.csv");
                }
            }
        });

    }









    private void readFile(String outFileName) {

        File root = android.os.Environment.getExternalStorageDirectory();
        Log.v("Mac Address", root.getAbsolutePath());
        
        File dir = new File (root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, outFileName);
       // dir.mkdirs();
            Log.v("Mac Address", file.getAbsolutePath());

       root = context.getExternalFilesDir("magic");
        Log.v("Mac Address", root.getAbsolutePath());

        try {
            FileReader fileWriter = new FileReader(file);
            BufferedReader out = new BufferedReader(fileWriter);
            String line = out.readLine();
           while( line != null) {
      //         Log.v("Mac Address", line);
               line = out.readLine();
           }
        //    file.delete();
        }
        catch (Exception e) {

        }

    }








    private Boolean backupDatabaseCSV(String outFileName) {



        File root = android.os.Environment.getExternalStorageDirectory();
        ArrayList<String> allRows = new ArrayList<String>();
        ArrayList<Integer> rowIndexes = new ArrayList<Integer>();


        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        dir.mkdirs();
        File file = new File(dir, outFileName);
        if(file.exists()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("File Already Exists, Please Choose a Different Name").setTitle("File Name Error")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        ed.setText("");

                        }
                    }).create().show();

        }

        else {


            //       String filePath = context.getFilesDir().getPath().toString() + "/" + outFileName;
            db = UIDatabaseInterface.getDatabase().getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM custom", null);
            String[] colNames = cursor.getColumnNames();
            int cols = cursor.getColumnCount();
            Log.v("Mac Address", "backupDatabaseCSV");
            boolean returnCode = false;
            int i = 0;
            String csvHeader = "";
            String csvValues = "";
            for (i = 0; i < colNames.length; i++) {
                if (csvHeader.length() > 0) {
                    csvHeader += ",";
                }
                csvHeader += "\"" + colNames[i] + "\"";
            }

            csvHeader += "\n";
            Log.v("Mac Address", "header=" + csvHeader);

            try {
                //   File outFile = new File(filePath);

                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter out = new BufferedWriter(fileWriter);

                if (cursor != null) {
                    Log.v("Mac Address", "Made it past null");
                    out.write(csvHeader);
                    cursor.moveToFirst();
                    int io = 0;
                    while (!cursor.isAfterLast()) {
                        csvValues = "";

                        for (int l = 0; l < cursor.getColumnCount(); l++) {
                            csvValues += "\"" + cursor.getString(l).replaceAll("\n", " ") + "\",";
                        }
                        Log.v("Transfer Data", "CSV VALUES BEING ADDED" + csvValues);
                        allRows.add(io, csvValues);
                        rowIndexes.add(io, cursor.getInt(1));
                        cursor.moveToNext();
                        io++;

                    }
                    allRows.trimToSize();
                    rowIndexes.trimToSize();
                    String[] y = allRows.toArray(new String[0]);
                    int[] x = convertIntegers(rowIndexes);
                    Log.v("Mac Address", "ROWS: " + Arrays.toString(y));
                    Log.v("Mac Address", "INDEXES: " + Arrays.toString(x));
                    Arrays.sort(x);
                    int holder = 0;
                    int magic = -1;
           /*         for(int ol = 0; ol < x.length; ol++) {

                        holder = x[ol];

                        int magic1 = -1;
                        for(int lo = 0; lo < y.length; lo++) {

                            String part = y[lo].substring(y[lo].indexOf(",")+1, y[lo].length());
                            String part1 = part.substring(0, part.indexOf(","));

                            if(part1.equals(Integer.toString(holder))) {

                                magic1 = lo;
                                break;

                            }

                        }

                        String temp = y[ol];
                        y[ol] = y[magic1];
                        y[magic1] = temp;




                    }
*/
                    for (int m = 0; m < y.length; m++) {
                        out.write(y[m]);
                        out.write("\n");

                    }


                    cursor.close();

                }
                out.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Data Successfully Written To File").setTitle("Data Written")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ed.setText("");

                            }
                        }).create().show();

                returnCode = true;
            } catch (IOException e) {
                returnCode = false;
                Log.v("Mac Address", "IOException: " + e.getMessage());
            }
            //   dbAdapter.close();
            return returnCode;
        }
        return false;
    }

    public static int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = iterator.next().intValue();
        }
        return ret;

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










}