package com.example.alex.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by alex on 3/8/15.
 */
public class DataViewTable extends TableLayout {

    public DataViewTable(Context context) {
        super(context);

        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(tableParams);

        TableRow tableRow = new TableRow(context);
        tableRow.setLayoutParams(tableParams);

        TextView textView = new TextView(context);
        textView.setLayoutParams(rowParams);

        tableRow.addView(textView);
    }

}
