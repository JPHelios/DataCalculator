package com.mms.datacalculator.ui.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.mms.datacalculator.R;
import com.mms.datacalculator.ui.dialog.DialogDataVolume;
import com.mms.datacalculator.ui.dialog.DialogDurationTime;
import com.mms.datacalculator.ui.dialog.DialogStartDay;
import com.mms.datacalculator.util.DateUtil;
import com.mms.datacalculator.util.SharedPreferenceUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AccountActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, DialogDataVolume.DataVolumeListener, DialogDurationTime.DurationTimeListener,View.OnClickListener {

    public TextView pickDataVolume;
    public TextView startDate;
    public TextView durationTime;
    private long startDateLong, date;


    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getApplicationContext());

        pickDataVolume = findViewById(R.id.pickDataVolumeDialogOpener);
        durationTime = findViewById(R.id.durationTimeDialogOpener);
        startDate = findViewById(R.id.startDateDialogOpener);
        Button saveButton = findViewById(R.id.saveButton);
        Button backButton = findViewById(R.id.backButton);

        //Open shared Preference file and get data
        pickDataVolume.setText(String.valueOf(sharedPreferenceUtil.getDateValueInt("DataVolume")));
        durationTime.setText(String.valueOf(sharedPreferenceUtil.getDateValueInt("DurationTime")));

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd. MMMM yyyy");
        date = sharedPreferenceUtil.getDateValueLong("StartDate");
        startDate.setText(formatter.format(date));

        pickDataVolume.setOnClickListener(this);
        durationTime.setOnClickListener(this);
        startDate.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    //set Date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        startDateLong = DateUtil.convertDateToMilliSeconds(calendar);

        String startDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        startDate.setText(startDateString);
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pickDataVolumeDialogOpener:
                DialogDataVolume dialogDataVolume = new DialogDataVolume(AccountActivity.this);
                dialogDataVolume.show(getSupportFragmentManager(), "DialogDataVolume");
                break;

            case R.id.durationTimeDialogOpener:
                DialogDurationTime dialogDurationTime = new DialogDurationTime(AccountActivity.this);
                dialogDurationTime.show(getSupportFragmentManager(), "DialogDurationTime");
                break;

            case R.id.startDateDialogOpener:
                DialogFragment dateSetter = new DialogStartDay();
                dateSetter.show(getSupportFragmentManager(), "DialogStartDay");
                break;

            case R.id.saveButton:
                int dataVolume = Integer.parseInt((String) pickDataVolume.getText());
                int days = Integer.parseInt((String) durationTime.getText());

                if (startDateLong != 0) {
                    sharedPreferenceUtil.saveAccountValues(dataVolume, days, startDateLong);
                    Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_LONG).show();
                    openMainActivity();
                } else {
                    if (date != 0) {
                        sharedPreferenceUtil.saveAccountValues(dataVolume, days, date);
                        openMainActivity();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please choose a date", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.backButton:
                openMainActivity();
                break;
        }
    }

    @Override
    public void onVolumeSelected(int dataVolume) {
        pickDataVolume.setText(String.valueOf(dataVolume));
    }

    @Override
    public void onDurationTimeSelected(int days) {
        durationTime.setText(String.valueOf(days));
    }
}
