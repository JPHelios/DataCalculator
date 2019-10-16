package com.mms.datacalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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

    public void loadData(){

        Reload reload = new Reload();
        String success = "Data loaded and calculated";
        Toast.makeText(getApplicationContext(),success,Toast.LENGTH_LONG).show();
    }






}
