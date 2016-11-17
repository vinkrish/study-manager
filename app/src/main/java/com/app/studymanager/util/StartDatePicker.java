package com.app.studymanager.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by Vinay on 17-11-2016.
 */

public class StartDatePicker extends DialogFragment {
    private int year, month, day;

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    public StartDatePicker() {}

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        if(args != null) {
            year = args.getInt("year");
            month = args.getInt("month");
            day = args.getInt("day");
        } else {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        return new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
    }
}
