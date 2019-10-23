package com.mms.datacalculator.util;

import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.net.ConnectivityManager;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

import java.util.Calendar;

public class CalculationUtil {

    private SharedPreferenceUtil sharedPreferenceUtil;
    private TelephonyManager telephonyManager;
    private NetworkStatsManager networkStatsManager;

    public CalculationUtil(SharedPreferenceUtil sharedPreferenceUtil, TelephonyManager telephonyManager, NetworkStatsManager networkStatsManager) {
        this.sharedPreferenceUtil = sharedPreferenceUtil;
        this.telephonyManager = telephonyManager;
        this.networkStatsManager = networkStatsManager;
    }

    public void calculateDataVolume() throws RemoteException {
        Calendar calendar = Calendar.getInstance();

        int days = sharedPreferenceUtil.getDateValueInt("DurationTime");
        int dataVolume = sharedPreferenceUtil.getDateValueInt("DataVolume");
        long startDateLong = sharedPreferenceUtil.getDateValueLong("StartDate");

        float dataPerDay = (float) dataVolume / days;

        long currentDate = calendar.getTimeInMillis();
        long millis = startDateLong - currentDate;
        int hours = (int) (millis / (1000 * 60 * 60));
        float dayNumber = ((float) hours / 24.0F * (-1) + 1);
        dayNumber = (int) dayNumber;
        float todaysDataVolume = dataPerDay * dayNumber;
        todaysDataVolume = (float) (Math.round(100.0 * todaysDataVolume) / 100.0);

//      #########################################################################################################################################
        String subscriberId = telephonyManager.getSubscriberId();

        NetworkStats.Bucket bucket = networkStatsManager.querySummaryForDevice(ConnectivityManager.TYPE_MOBILE, subscriberId, startDateLong, currentDate);
        long bytesRecieved = bucket.getRxBytes();
        long bytesTransmitted = bucket.getTxBytes();
        long mBytes = (bytesRecieved + bytesTransmitted) / 1000 / 1000;
//      #########################################################################################################################################

        sharedPreferenceUtil.saveDataValues(dataPerDay, mBytes, dayNumber, todaysDataVolume);

        }

}
