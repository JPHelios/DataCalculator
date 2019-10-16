package com.mms.datacalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.support.v4.app.*;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Account extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public TextView pickDataVolume;
    public TextView durationTime;
    private TextView startDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        pickDataVolume = findViewById(R.id.pickDataVolume);
        durationTime = findViewById(R.id.durationTime);
        startDate = findViewById(R.id.startDate);


        //Open Dialog for Data Volume
        pickDataVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDataVolume dialog = new DialogDataVolume();
                dialog.show(getSupportFragmentManager(),"DialogDataVolume");
            }
        });

        //Open dialog for duration time
        durationTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDurationTime dialog = new DialogDurationTime();
                dialog.show(getSupportFragmentManager(),"DialogDurationTime");
            }
        });

        //open dialog for start date
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment date = new DialogStartDay();
                date.show(getSupportFragmentManager(),"DialogStartDay");
            }
        });



        //Shared Preferences


    }

    //set Date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String startDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        startDate.setText(startDateString);
    }
}
