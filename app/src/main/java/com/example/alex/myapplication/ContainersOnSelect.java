package com.example.alex.myapplication;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by alex on 3/6/15.
 */

public class ContainersOnSelect implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String selected = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView parent) {
        // Do nothing.
    }
}
