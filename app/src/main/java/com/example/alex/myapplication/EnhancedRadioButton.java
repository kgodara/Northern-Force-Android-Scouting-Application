package com.example.alex.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by Oombliocarius on 2/19/16.
 */
public class EnhancedRadioButton extends RadioButton {


    private String colName = null;
    private Context context;
    int status = 0;

    protected EnhancedRadioButton(Context context) {
        super(context);
        this.context = context;
    }

  /*  @Override
    public void toggle() {
        if(isChecked() && status == 0) {
      //      if(getParent() instanceof RadioGroup) {
        //        ((RadioGroup)getParent()).clearCheck();
            status = 1;

            }
         else {
            setChecked(true);
        status = 0;
        }
    }
*/

    public EnhancedRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EnhancedRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }
}