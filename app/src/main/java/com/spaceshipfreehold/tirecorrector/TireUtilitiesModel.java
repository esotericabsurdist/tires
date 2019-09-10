package com.spaceshipfreehold.tirecorrector;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

public class TireUtilitiesModel implements ITireModel {

    Context mContext;


    TireUtilitiesModel(@NonNull Context context){
        mContext = context;
    }

    @Override
    public void saveRecentUtilitiesTabPosition(int position) {
        TireUtilitiesSharedPreferences.getInstance(mContext).setRecentUtilitiesTabPosition(position);
    }

    @Override
    public int getRecentUtility(int defaultValue) {
        int position = TireUtilitiesSharedPreferences.getInstance(mContext).getRecentUtilitiesTabPosition(defaultValue);
        return position;
    }

    @Override
    public void saveUnitTypeAsMetric(boolean metric) {
        TireUtilitiesSharedPreferences.getInstance(mContext).saveUnitTypeAsMetric(metric);
    }

    @Override
    public boolean getUnitTypeMetric(boolean defaultValue) {
        return TireUtilitiesSharedPreferences.getInstance(mContext).getUnitTypeMetric(defaultValue);
    }

    @Override
    public void saveDarkThemeEnabled(boolean enabled) {
        TireUtilitiesSharedPreferences.getInstance(mContext).saveDarkThemeEnabled(enabled);
    }

    @Override
    public boolean getDarkThemeEnabled(boolean defaultValue){
        return TireUtilitiesSharedPreferences.getInstance(mContext).getDarkThemeEnabled(defaultValue);
    }

    @Override
    public void saveOriginalTireDiameter(double diameter) {
        TireUtilitiesSharedPreferences.getInstance(mContext).saveOriginalTireDiameter(diameter);
    }

    @Override
    public double getOriginalTireDiameter(double defaultDiameter) {
        return TireUtilitiesSharedPreferences.getInstance(mContext).getOriginalTireDiameter(defaultDiameter);
    }

    @Override
    public void saveNewTireDiameter(double diameter) {
        TireUtilitiesSharedPreferences.getInstance(mContext).saveNewTireDiameter(diameter);
    }

    @Override
    public double getNewTireDiameter(double defaultValue) {
        return TireUtilitiesSharedPreferences.getInstance(mContext).getNewTireDiameter(defaultValue);
    }

    @Override
    public void saveTireWidth(double width) {
        TireUtilitiesSharedPreferences.getInstance(mContext).saveTireWidth(width);
    }

    @Override
    public double getTireWidth(double defaultWidth) {
        return TireUtilitiesSharedPreferences.getInstance(mContext).getTireWidth(defaultWidth);
    }

    @Override
    public void saveAspectRatio(double aspectRatio) {
        TireUtilitiesSharedPreferences.getInstance(mContext).saveAspectRatio(aspectRatio);
    }

    @Override
    public double getAspectRatio(double defaultAspectRatio) {
        return TireUtilitiesSharedPreferences.getInstance(mContext).getAspectRatio(defaultAspectRatio);
    }

    @Override
    public void saveRimSize(double rimSize) {
        TireUtilitiesSharedPreferences.getInstance(mContext).saveRimSize(rimSize);
    }

    @Override
    public double getRimSize(double defaultRimSize) {
        return TireUtilitiesSharedPreferences.getInstance(mContext).getRimSize(defaultRimSize);
    }
}
