package com.example.alex.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Oombliocarius on 12/17/15.
 */
public class Updater implements Runnable {


    InputStream ino;
    public Updater(InputStream in) {
    ino = in;
    }

    public void run() {
        InputStreamReader inr = new InputStreamReader(ino);
        BufferedReader br = new BufferedReader(inr);
        while(true) {
            try {
                Log.v("Mac Address", br.readLine());
            }
            catch(Exception e) {

            }


        }

    }


}
