package com.spaceshipfreehold.tirecorrector;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class TireUtilitiesSharedPreferences {

    private volatile static TireUtilitiesSharedPreferences mInstance;
    private Context mContext;
    private static SharedPreferences mSharedPreferences;

    private TireUtilitiesSharedPreferences(Context context){
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static TireUtilitiesSharedPreferences getInstance(@NonNull Context context){
        if(mInstance == null){
            mInstance = new TireUtilitiesSharedPreferences(context);
        }
        return mInstance;
    }

    public int getRecentUtilitiesTabPosition(int defaultValue){
        return mSharedPreferences.getInt(Constants.TAB_POSITION, defaultValue);
    }

    public void setRecentUtilitiesTabPosition(int tabPosition){
        mSharedPreferences.edit().putInt(Constants.TAB_POSITION, tabPosition).apply();
    }

    public void saveNewTireDiameter(double diameter) {
        mSharedPreferences.edit().putLong(Constants.NEW_DIAMETER, Double.doubleToRawLongBits(diameter)).apply();
    }

    public double getNewTireDiameter(double defaultValue){
        long diameter = mSharedPreferences.getLong(Constants.NEW_DIAMETER, Double.doubleToRawLongBits(defaultValue));
        return Double.longBitsToDouble(diameter);
    }

    public void saveOriginalTireDiameter(double diameter){
        mSharedPreferences.edit().putLong(Constants.OLD_DIAMETER, Double.doubleToRawLongBits(diameter)).apply();
    }

    public double getOriginalTireDiameter(double defaultValue){
        long diameter = mSharedPreferences.getLong(Constants.OLD_DIAMETER, Double.doubleToRawLongBits(defaultValue));
        return Double.longBitsToDouble(diameter);
    }

    public void saveUnitTypeAsMetric(boolean metric) {
        mSharedPreferences.edit().putBoolean(Constants.METRIC, metric).apply();
    }

    public boolean getUnitTypeMetric(boolean defaultValue){
        return mSharedPreferences.getBoolean(Constants.METRIC, defaultValue);
    }

    public void saveDarkThemeEnabled(boolean enabled) {
        mSharedPreferences.edit().putBoolean(Constants.DARK_THEME, enabled).apply();
    }

    public boolean getDarkThemeEnabled(boolean defaultValue){
        return mSharedPreferences.getBoolean(Constants.DARK_THEME, defaultValue);
    }

    private static class Constants {
        static String PREFERENCES_NAME = "TIRE_UTILITIES_SHARED_PREFERENCES";
        static String TAB_POSITION = "tab_position";
        static String NEW_DIAMETER = "new_diameter";
        static String OLD_DIAMETER = "old_diameter";
        static String METRIC = "metric";
        static String DARK_THEME = "dark_theme";
    }
}
