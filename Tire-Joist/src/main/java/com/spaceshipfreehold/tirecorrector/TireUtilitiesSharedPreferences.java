package com.spaceshipfreehold.tirecorrector;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

    public void saveTireWidth(double width) {
        mSharedPreferences.edit().putLong(Constants.TIRE_WIDTH, Double.doubleToRawLongBits(width)).apply();
    }

    public double getTireWidth(double defaultTireWidth){
        long width = mSharedPreferences.getLong(Constants.TIRE_WIDTH, Double.doubleToRawLongBits(defaultTireWidth));
        return Double.longBitsToDouble(width);
    }

    public void saveAspectRatio(double aspectRatio) {
        mSharedPreferences.edit().putLong(Constants.ASPECT_RATIO, Double.doubleToRawLongBits(aspectRatio)).apply();
    }

    public double getAspectRatio(double defaultAspectRatio){
        long aspectRatio = mSharedPreferences.getLong(Constants.ASPECT_RATIO, Double.doubleToRawLongBits(defaultAspectRatio));
        return Double.longBitsToDouble(aspectRatio);
    }

    public void saveRimSize(double rimSize) {
        mSharedPreferences.edit().putLong(Constants.RIM_SIZE, Double.doubleToRawLongBits(rimSize)).apply();
    }

    public double getRimSize(double defaultRimSize){
        long rimSize = mSharedPreferences.getLong(Constants.RIM_SIZE, Double.doubleToRawLongBits(defaultRimSize));
        return Double.longBitsToDouble(rimSize);
    }

    public void saveOriginalRAndPRatio(double ratio) {
        mSharedPreferences.edit().putLong(Constants.R_AND_P_RATIO, Double.doubleToRawLongBits(ratio)).apply();
    }

    public double getOriginalRAndPRatio(double defaultRatio){
        long ratio = mSharedPreferences.getLong(Constants.R_AND_P_RATIO, Double.doubleToRawLongBits(defaultRatio));
        return Double.longBitsToDouble(ratio);
    }

    public void saveIdealAxleRatioNewTireDiameter(double diameter) {
        mSharedPreferences.edit().putLong(Constants.IDEAL_AXLE_RATIO_NEW_TIRE_DIAMETER, Double.doubleToRawLongBits(diameter)).apply();
    }

    public double getIdealRatioNewTireDiameter(double defaultDiameter) {
        long diameter = mSharedPreferences.getLong(Constants.IDEAL_AXLE_RATIO_NEW_TIRE_DIAMETER, Double.doubleToRawLongBits(defaultDiameter));
        return Double.longBitsToDouble(diameter);
    }

    public void saveIdealAxleRatioOriginalTireDiameter(double diameter) {
        mSharedPreferences.edit().putLong(Constants.IDEAL_AXLE_RATIO_ORIGINAL_TIRE_DIAMETER, Double.doubleToRawLongBits(diameter)).apply();
    }

    public double getIdealRatioOriginalTireDiameter(double defaultDiameter) {
        long diameter = mSharedPreferences.getLong(Constants.IDEAL_AXLE_RATIO_ORIGINAL_TIRE_DIAMETER, Double.doubleToRawLongBits(defaultDiameter));
        return Double.longBitsToDouble(diameter);
    }

    private static class Constants {
        static String PREFERENCES_NAME = "TIRE_UTILITIES_SHARED_PREFERENCES";
        static String TAB_POSITION = "tab_position";
        static String NEW_DIAMETER = "new_diameter";
        static String OLD_DIAMETER = "old_diameter";
        static String METRIC = "metric";
        static String DARK_THEME = "dark_theme";
        static String TIRE_WIDTH = "tire_width";
        static String ASPECT_RATIO = "aspect_ratio";
        static String RIM_SIZE = "rim_size";
        static String R_AND_P_RATIO = "r_and_p_ratio";
        static String IDEAL_AXLE_RATIO_NEW_TIRE_DIAMETER = "ideal_axle_ratio_new_tire_diameter";
        static String IDEAL_AXLE_RATIO_ORIGINAL_TIRE_DIAMETER = "ideal_axle_ratio_original_tire_diameter";
    }
}
