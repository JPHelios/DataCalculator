package com.mms.datacalculator.ui.activity;

import android.Manifest;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.mms.datacalculator.R;
import com.mms.datacalculator.util.CalculationUtil;
import com.mms.datacalculator.util.SharedPreferenceUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 1;

    private boolean twice = false;

    private CalculationUtil calculationUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkStatsManager networkStatsManager = (NetworkStatsManager) getApplicationContext().getSystemService(Context.NETWORK_STATS_SERVICE);
        TelephonyManager manager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        SharedPreferenceUtil sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getApplicationContext());
        calculationUtil = new CalculationUtil(sharedPreferenceUtil, manager, networkStatsManager);

        LinearLayout monitoringActivityLayout = findViewById(R.id.monitorLayout);
        LinearLayout accountActivityLayout = findViewById(R.id.setDataLayout);
        LinearLayout dataLoadingActivityLayout = findViewById(R.id.calcDataLayout);

        try {
            calculationUtil.calculateDataVolume();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.start_text), Toast.LENGTH_LONG).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        monitoringActivityLayout.setOnClickListener(this);
        accountActivityLayout.setOnClickListener(this);
        dataLoadingActivityLayout.setOnClickListener(this);
    }

    public void openMonitoring() {
        Intent intent = new Intent(this, MonitoringActivity.class);
        startActivity(intent);
    }

    public void openSetData() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void loadData() throws RemoteException {

        calculationUtil.calculateDataVolume();
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.success_text), Toast.LENGTH_LONG).show();

        openMonitoring();
    }

    @Override
    public void onBackPressed() {
        if (twice) {
            System.exit(0);
        }

        Toast.makeText(getApplicationContext(), "Please press BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
            }
        }, 300);
        twice = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.monitorLayout:
                openMonitoring();
                break;

            case R.id.setDataLayout:
                openSetData();
                break;

            case R.id.calcDataLayout:
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    loadData();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
