package com.spaceshipfreehold.tirecorrector;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class TireUtilityPresenter implements ITireUtility.Presenter {

    ITireModel mModel;
    ITireUtility.View mView;
    List<Integer> mSettingsOptions;

    TireUtilityPresenter(ITireModel model, ITireUtility.View view){
        mModel = model;
        mView = view;
        mSettingsOptions = new ArrayList<>();
        if (mModel.getDarkThemeEnabled(false)) {
            mView.setThemeDark();
        } else {
            mView.setThemeLight();
        }
    }

    @Override
    public void onStarted() {
        // TODO set theme, get unit type, retrieve recent utility page number, set data into view.
        mView.goToUtility(mModel.getRecentUtility(0));
    }

    @Override
    public void imperialUnitsSelected() {
        mModel.saveUnitTypeAsMetric(false);
        mView.enableImperialUnitsOption(false);
        mView.enableMetricUnitsOption(true);
        mView.recreateView();
    }

    @Override
    public void metricUnitsSelected() {
        mModel.saveUnitTypeAsMetric(true);
        mView.enableImperialUnitsOption(true);
        mView.enableMetricUnitsOption(false);
        mView.recreateView();
    }

    @Override
    public void darkThemeSelected() {
        mModel.saveDarkThemeEnabled(true);
        mView.setThemeDark();
        mView.enableDarkThemeOption(false);
        mView.enableLightThemeOption(true);
        mView.recreateView();
    }

    @Override
    public void lightThemeSelected() {
        mModel.saveDarkThemeEnabled(false);
        mView.setThemeLight();
        mView.enableDarkThemeOption(true);
        mView.enableLightThemeOption(false);
        mView.recreateView();
    }

    @Override
    public void onUtilitySelected(int position) {
        mModel.saveRecentUtilitiesTabPosition(position);
    }

    @Override
    public void onOptionsMenuPrepared() {
        if(mModel.getDarkThemeEnabled(false)){
            mView.enableLightThemeOption(true);
            mView.enableDarkThemeOption(false);
        } else {
            mView.enableLightThemeOption(false);
            mView.enableDarkThemeOption(true);
        }

        if(mModel.getUnitTypeMetric(false)){
            mView.enableImperialUnitsOption(true);
            mView.enableMetricUnitsOption(false);
        } else {
            mView.enableImperialUnitsOption(false);
            mView.enableMetricUnitsOption(true);
        }
    }
}
