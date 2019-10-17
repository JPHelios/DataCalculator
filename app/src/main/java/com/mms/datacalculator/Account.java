package com.mms.datacalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.support.v4.app.*;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Account extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public TextView pickDataVolume;
    public TextView durationTime;
    private TextView startDate;
    private Button saveButton;
    private long startDateLong;
    public SharedPreferences calculationData;
    private Calendar calendar = Calendar.getInstance();
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        pickDataVolume = findViewById(R.id.pickDataVolume);
        durationTime = findViewById(R.id.durationTime);
        startDate = findViewById(R.id.startDate);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);

        //Open shared Preference file
        calculationData = getSharedPreferences("CalculationData", Context.MODE_PRIVATE);

        //get data from shared preferences
        pickDataVolume.setText(String.valueOf(calculationData.getInt("DataVolume", 500)));
        durationTime.setText(String.valueOf(calculationData.getInt("DurationTime", 28)));

        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd. MMMM yyyy");
        String dateString = formatter.format(calculationData.getLong("StartDate", calendar.getTimeInMillis()));
        startDate.setText(dateString);

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
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dataVolume = Integer.parseInt((String) pickDataVolume.getText());
                int days = Integer.parseInt((String) durationTime.getText());

                //open file
                calculationData = getSharedPreferences("CalculationData", Context.MODE_PRIVATE);
                //open Editor
                SharedPreferences.Editor editor = calculationData.edit();
                //Text schreiben

                if (startDate.getText() != "") {
                    editor.putInt("DataVolume", dataVolume);
                    editor.putInt("DurationTime", days);
                    editor.putLong("StartDate", startDateLong);

                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Data saved",Toast.LENGTH_LONG).show();
                    openMainActivity();
                } else {
                    Toast.makeText(getApplicationContext(),"Please choose a date",Toast.LENGTH_LONG).show();
                }



            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });


    }

    //set Date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String startDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        startDateLong = calendar.getTimeInMillis();
        startDate.setText(startDateString);
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
