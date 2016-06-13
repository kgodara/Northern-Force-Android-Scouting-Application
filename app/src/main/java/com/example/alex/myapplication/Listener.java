package com.example.alex.myapplication;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Oombliocarius on 11/4/15.
 */
public class Listener implements Runnable {
    UUID uuid = null;
    BluetoothServerSocket bSS;
    BluetoothSocket bS;
    InputStream in;
    boolean  stopWorker;
    int readBufferPosition;
    byte[] readBuffer;
    Thread workerThread;
    final String FILE_NAME = "custom.txt";
    File file = null;
    ArrayList<Question> questions;
    Listener listen = this;
    Context cont;
    MySQLiteHelper database = null;
    Object ob;

    public void setEda(ExportDataActivity eda) {
        this.exd = eda;
    }

    ExportDataActivity exd;

    public void run() {

        if(cont != null) {
            this.database = new MySQLiteHelper(cont);
        }

        BluetoothAdapter bL  = BluetoothAdapter.getDefaultAdapter();
        try {
            bSS = bL.listenUsingRfcommWithServiceRecord("Server", uuid);
            Log.v("Mac Address", "Listening");
            bS = bSS.accept();
            Log.v("Mac Address", "Connection accepted");
            //   bS.connect();
            in =   bS.getInputStream();
            Log.v("Mac Address", "Sauron's Land");
            beginListenForData();
            //   Updater up = new Updater(in);
            //   Thread t = new Thread(up);
            //  t.start();

        }
        catch(Exception e) {

        }




    }

    public void setContext(Context context) {
        cont = context;
    }

    public void setUUID(UUID id) {
        uuid = id;
    }










    void beginListenForData()
    {
        Log.v("Mac Address", "Devil's Door");
        stopWorker = false;

        workerThread = new Thread(new Runnable()
        {
            public void run()
            {
                Log.v("Mac Address", "STARTED");
                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = in.available();
                        //Log.v("Mac Address", "BYTES: " + bytesAvailable);
                        if(bytesAvailable > 0)
                        {
                            Log.v("Mac Address", "WE'RE UP IN THIS");
                            byte[] packetBytes = new byte[in.available()];
                            //
                            // String data = new String(packetBytes, "UTF-8");
                            Log.v("Mac Address", "AVAILABLE: " + in.available());
                            Log.v("Mac Address", Arrays.toString(packetBytes));
                            //   in.read(packetBytes);
                            ObjectInputStream obin = new ObjectInputStream(in);
                            Object obo = null;
                            try {
                                obo = obin.readObject();
                            }
                            catch(Exception e) {

                            }
                            byte[] hope = (byte[]) obo;
                            Log.v("Mac Address", Arrays.toString(hope));


                            //   Log.v("Mac Address", String.valueOf(baos.size()));
                            ByteArrayInputStream bi = new ByteArrayInputStream(hope);
                            ZipInputStream zis = new ZipInputStream(bi);
                            Log.v("Mac Address", "HOPE: " + zis.available());
                            //      zis.getNextEntry();
                            //     byte[] results = null;
                            //     zis.read(results, 0, 0);
                            //     String please = new String(results, "UTF-8");
                            //     Log.v("Mac Address", please);

                            ZipEntry entry;
                            try {
                                while ((entry = zis.getNextEntry()) != null) {
                                    String s = String.format("Entry: %s len %d added %TD",
                                            entry.getName(), entry.getSize(),
                                            new Date(entry.getTime()));
                                    Log.v("Mac Address", "THIS FAR");
                                    // ZipFile zipFile = new ZipFile("text.zip");
                                    Log.v("Mac Address", "THIS FAR 1");
                                    ObjectInputStream ino = new ObjectInputStream(zis);
                                    ob = ino.readObject();

                                    Log.v("Mac Address", ob.toString());
                                    if(ob instanceof ConfigEntry) {
                                        ConfigEntry con = (ConfigEntry) ob;
                                        Log.v("Mac Address", con.getText());
                                    }
                                    if(ob instanceof SQLiteDatabase) {
                                        SQLiteDatabase con = (SQLiteDatabase) ob;
                                        Log.v("Mac Address", con.toString());
                                    }
                                    if(ob instanceof Cursor) {
                                        Cursor con = (Cursor) ob;
                                        Log.v("Mac Address", con.toString());
                                    }
                                    if(ob instanceof String) {
                                        String str = (String) ob;
                                        Log.v("Mac Address", str);
                                    }
                                    if(ob instanceof SubmissionData) {
                                        SubmissionData str = (SubmissionData) ob;
                                        Log.v("Mac Address", str.getData());

                                        String[] lines = str.getData().split("\n");


                                        String[] cols = lines[0].split("/");
                                        SQLiteDatabase db = UIDatabaseInterface.getDatabase().getReadableDatabase();
                                        Cursor  cursor = db.rawQuery("SELECT * FROM custom", null);

                                        if(cursor.getColumnCount() == cols.length) {
                                            UIDatabaseInterface.mergeToDatabase(str.getData());

                                            new Thread() {
                                                public void run() {
                                                    exd.runOnUiThread(new Runnable() {
                                                        public void run() {
                                                            //Do your UI operations like dialog opening or Toast here
                                                            AlertDialog alertDialog = new AlertDialog.Builder(exd).create();
                                                            alertDialog.setTitle("Alert");
                                                            alertDialog.setMessage("Bluetooth Data Received and Added");
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

                                        }

                                        else {


                                            new Thread() {
                                                public void run() {
                                                    exd.runOnUiThread(new Runnable() {
                                                        public void run() {
                                                            //Do your UI operations like dialog opening or Toast here
                                                            AlertDialog alertDialog = new AlertDialog.Builder(exd).create();
                                                            alertDialog.setTitle("Alert");
                                                            alertDialog.setMessage(" Error: Bluetooth Data Source Layout is Different, Data will not be added");
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
                                        }

                                    }
                                    try
                                    {
                                        if(ob instanceof ArrayList<?>)
                                        {
                                            if(((ArrayList<?>)ob).get(0) instanceof Question)
                                            {


                                                new Thread() {
                                                    public void run() {
                                                        exd.runOnUiThread(new Runnable() {
                                                            public void run() {
                                                                //Do your UI operations like dialog opening or Toast here
                                                                AlertDialog alertDialog = new AlertDialog.Builder(exd).create();
                                                                alertDialog.setTitle("Alert");
                                                                alertDialog.setMessage("Bluetooth Layout Received, Replace Current Layout?");
                                                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                                                        new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                questions = (ArrayList<Question>) ob;
                                                                                writeQuestionsToFile();
                                                                                doSaveProcess();
                                                                                dialog.dismiss();
                                                                            }
                                                                        });
                                                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,  "No", new DialogInterface.OnClickListener() {
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                dialog.dismiss();
                                                                            }

                                                                        }




                                                                );
                                                                alertDialog.show();

                                                            }
                                                        });
                                                    }
                                                }.start();





                                            }
                                        }
                                    }
                                    catch(NullPointerException e)
                                    {
                                        e.printStackTrace();
                                    }







                                }
                            }
                            catch(Exception e) {
                                Log.v("Mac Address", "OH BOI");
                                StringWriter errors = new StringWriter();
                                e.printStackTrace(new PrintWriter(errors));
                                Log.v("Mac Address", errors.toString());
                                new Thread()
                                {
                                    public void run()
                                    {
                                        exd.runOnUiThread(new Runnable() {
                                            public void run() {
                                                //Do your UI operations like dialog opening or Toast here
                                                AlertDialog alertDialog = new AlertDialog.Builder(exd).create();
                                                alertDialog.setTitle("Alert");
                                                alertDialog.setMessage("Bluetooth data transfer failed");
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

                            }



                        }
                    }
                    catch (IOException ex)
                    {

                        new Thread()
                        {
                            public void run()
                            {
                                exd.runOnUiThread(new Runnable() {
                                    public void run() {
                                        //Do your UI operations like dialog opening or Toast here
                                        AlertDialog alertDialog = new AlertDialog.Builder(exd).create();
                                        alertDialog.setTitle("Alert");
                                        alertDialog.setMessage("Bluetooth data transfer failed");
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

                        StringWriter errors = new StringWriter();
                        ex.printStackTrace(new PrintWriter(errors));
                        Log.v("Mac Address", errors.toString());
                        Log.v("Mac Address", "Uprising Failed");
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    public static Object deserialize(byte[] data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        }
        catch (Exception e) {

        }
        return null;
    }

    public void writeQuestionsToFile() {

        try {
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            dir.mkdirs();
            file = new File(dir, FILE_NAME);
            PrintWriter pw = new PrintWriter(file);
            pw.close();

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream obs = new ObjectOutputStream(fos);

            obs.writeObject(questions);

            obs.close();
            fos.close();

            Log.v("Transfer Data", "WOW");
         //   AlertDialog.Builder builder = new AlertDialog.Builder(listen);
         //   builder.setMessage("Questions Saved").setTitle("File Saved").create().show();




        }

        catch(FileNotFoundException e) {
            Log.v("Custom Layout", e.getMessage());
        }

        catch(IOException e) {
            Log.v("Custom Layout", e.getMessage());
        }

    }



    public void doSaveProcess() {
        database.dropTable("android_");
        database.dropTable("custom");

        ArrayList<ConfigEntry> columns = new ArrayList<ConfigEntry>();

        for(int i = 0; i <questions.size(); i++) {

            if(questions.get(i) instanceof BoolQuestion) {

                BoolQuestion info = (BoolQuestion) questions.get(i);
                columns.add(new ConfigEntry("String", info.getQuestion()));

            }

            if(questions.get(i) instanceof NumOnlyQuestion) {

                NumOnlyQuestion info = (NumOnlyQuestion) questions.get(i);
                columns.add(new ConfigEntry("int", info.getQuestion()));

            }

            if(questions.get(i) instanceof TextFieldQuestion) {

                TextFieldQuestion info = (TextFieldQuestion) questions.get(i);
                columns.add(new ConfigEntry("String", info.getQuestion()));

            }

            if(questions.get(i) instanceof MultiChoiceQuestion) {

                MultiChoiceQuestion info = (MultiChoiceQuestion) questions.get(i);
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


                }

            } catch (IOException e) {

                Log.v("Custom Layout", e.getMessage());

            }

        }
    }














}

