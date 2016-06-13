package com.example.alex.myapplication;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;
//WHEN RUN ON DIFFERENT DEVICE WONT STOP CRASHING/ UTILS.MACADDRESS IS SUSPECTED
/**
 * Created by Oombliocarius on 10/22/15.
 */
public class BlueConnect {
    int q = 0;
    ControlledEnterDataActivity ced;
    UUID j = null;
    ArrayList<BluetoothSocket> connections = new ArrayList<BluetoothSocket>(7);
    String android_id;
    public static BluetoothDevice bD;

    public static void setbD(BluetoothDevice b) {
        bD = b;
    }


    public BlueConnect() {

    }

    public BlueConnect(ControlledEnterDataActivity the, UUID uuid, Context leggo) {


    }
    String thisAddress = null;
    public String run(ControlledEnterDataActivity ceda, UUID uuid, Context leggo) {
        ced = ceda;
        String b = "";

     //   Utils.getMACAddress(null);
        j = uuid;
        android_id = Settings.Secure.getString(leggo.getContentResolver(), Settings.Secure.ANDROID_ID);
   //     String thisAddress = Utils.getMACAddress(null);
        BluetoothAdapter bl = BluetoothAdapter.getDefaultAdapter();

     //    Log.v("Mac Address", "B" + thisAddress);

        if (android_id.equalsIgnoreCase("ce5798be02b59464")) {

            Log.v("Mac Address", "MASTER");
            b = "master";

         /*   Listener listen = new Listener();
            listen.setUUID(j);
            listen.setCed(ced);
            Thread t = new Thread(listen);
            t.start();*/
            //  NetworkScanner scanner = new NetworkScanner();
        }

//

        if (!android_id.equalsIgnoreCase("ce5798be02b59464")) {

            Log.v("Mac Address", "servant");
            b = "servant";



            // Log.v("Mac Address", Integer.toString(bl.getScanMode()));
         //   Aggro agr = new Aggro(j, mA);
          //      Thread t = new Thread(agr);
          //      t.start();






        }
    return b;
    }
    public static void unpairDevice() {
        try {
            Method m = bD.getClass()
                    .getMethod("removeBond", (Class[]) null);
            m.invoke(bD, (Object[]) null);
        } catch (Exception e) {
            Log.e("Mac Address", e.getMessage());
        }
    }


    }



