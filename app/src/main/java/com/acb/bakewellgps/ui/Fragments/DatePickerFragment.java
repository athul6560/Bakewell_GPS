package com.acb.bakewellgps.ui.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.acb.bakewellgps.TimeConverters.EpochConverters;
import com.acb.bakewellgps.Tools.IntentConstants;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    EditText tlExpiryDate;
    public DatePickerFragment(EditText tlExpiryDate) {
        this.tlExpiryDate=tlExpiryDate;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        tlExpiryDate.setText(day+"/"+(month+1)+"/"+year);

        IntentConstants.TL_EXPIRY_EPOCH= EpochConverters.humanReadabletoEpoch(day,(month+1),year);
    }
}