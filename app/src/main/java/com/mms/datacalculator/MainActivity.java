package com.mms.datacalculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private LinearLayout goToMonitoring;
    private LinearLayout goToAccount;
    private LinearLayout loadData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToMonitoring = findViewById(R.id.monitor);
        goToAccount = findViewById(R.id.setData);
        loadData = findViewById(R.id.calcData);

        //Open Activity for Monitoring data
        goToMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openMonitoring();
            }
        });

        //Open Activity for setting data
        goToAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSetData();
            }
        });

        //Load and Calculate the mobile data
        loadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

    }

    public void openMonitoring(){
        Intent intent = new Intent(this, Monitoring.class);
        startActivity(intent);
    }

    public void openSetData(){
        Intent intent = new Intent(this, Account.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void loadData(){

        Calendar calendar = Calendar.getInstance();
        int days, dataVolume;
        long date;
        float dataPerDay, todaysDataVolume;

        String success = "Data loaded and calculated";

        //load sharedPreferences data
        SharedPreferences calculationData = getSharedPreferences("CalculationData", Context.MODE_PRIVATE);
        days = calculationData.getInt("DurationTime", 28);
        dataVolume = calculationData.getInt("DataVolume", 500);

        date = calculationData.getLong("StartDate", calendar.getTimeInMillis());

        //Data per Day Calculation
        dataPerDay = (float) dataVolume / days;

        //DayNumber since start of calculation
        long millis = date - calendar.getTimeInMillis();
        int hours = (int) (millis/(1000*60*60));         //int mins = (int) (millis%(1000*60*60));
        float dayNumber = ((float) hours/24.0F * (-1) + 1);
        todaysDataVolume = dataPerDay * dayNumber;
        //Round
        todaysDataVolume = (float)(Math.round(100.0 * todaysDataVolume) / 100.0);

        //NetworkStatsManager netStatManager = context.getSystemSer(NetworkStatsManager.class);






        //Toast for a successfull task
        Toast.makeText(getApplicationContext(), String.valueOf(todaysDataVolume + " MB"/* success*/),Toast.LENGTH_LONG).show();
    }






}
