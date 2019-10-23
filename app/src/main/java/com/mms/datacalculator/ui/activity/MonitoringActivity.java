package com.mms.datacalculator.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mms.datacalculator.R;
import com.mms.datacalculator.util.DateUtil;

public class MonitoringActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        //Widgets
        TextView daysTextView = findViewById(R.id.daysTextView);
        TextView freeMBTextView = findViewById(R.id.freeMBTextView);
        TextView availableMB = findViewById(R.id.availableMB);
        TextView usedMB = findViewById(R.id.usedMB);
        Button backButton = findViewById(R.id.backButton);

        SharedPreferences calculationData = getSharedPreferences("CalculationData", Context.MODE_PRIVATE);
        float dayNumber = calculationData.getFloat("DayNumber", 0);
        long mBytes = calculationData.getLong("UsedMB", 0);
        float todaysDataVolume = calculationData.getFloat("todaysDataVolume", 0);

        int freeMB = DateUtil.calculateFreeMB(mBytes, todaysDataVolume);

        daysTextView.setText(String.valueOf(dayNumber));
        availableMB.setText(todaysDataVolume + " MB");
        usedMB.setText(mBytes + " MB");
        freeMBTextView.setText(freeMB + " MB");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonitoringActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
