package com.mms.datacalculator.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    private static  SharedPreferenceUtil SINGLE_INSTANCE = null;
    private  SharedPreferences calculationData;

    private SharedPreferenceUtil(Context context) {
        calculationData = context.getSharedPreferences("CalculationData", Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance(Context context) {
        if (SINGLE_INSTANCE == null) {
            synchronized(SharedPreferenceUtil.class) {
                SINGLE_INSTANCE = new SharedPreferenceUtil(context);
            }
        }
        return SINGLE_INSTANCE;
    }

    void saveDataValues(float dataPerDay, long mBytes, float dayNumber, float todaysDataVolume){
        SharedPreferences.Editor editor = calculationData.edit();
        editor.putFloat("DataPerDay", dataPerDay);
        editor.putLong("UsedMB", mBytes);
        editor.putFloat("DayNumber", dayNumber);
        editor.putFloat("todaysDataVolume", todaysDataVolume);
        editor.apply();
    }

    public void saveAccountValues(int dataVolume, int days, long startDateLong){
        SharedPreferences.Editor editor = calculationData.edit();
        editor.putInt("DataVolume", dataVolume);
        editor.putInt("DurationTime", days);
        editor.putLong("StartDate", startDateLong);
        editor.apply();
    }

    public long getDateValueLong(String valueName){
        return calculationData.getLong(valueName, 0);
    }

    public int getDateValueInt (String valueName){
        return calculationData.getInt(valueName, 0);
    }

}
