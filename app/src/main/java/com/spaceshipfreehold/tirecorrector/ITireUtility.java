package com.spaceshipfreehold.tirecorrector;

import java.util.List;

public interface ITireUtility {
    interface Presenter{
        void onStarted();
        void imperialUnitsSelected();
        void metricUnitsSelected();
        void darkThemeSelected();
        void lightThemeSelected();
        void onUtilitySelected(int position);
        void onOptionsMenuPrepared();
    }

    interface View{
        void showToast(String text);
        void goToUtility(int tabPosition);
        void setThemeDark();
        void setThemeLight();
        void enableLightThemeOption(boolean enabled);
        void enableDarkThemeOption(boolean enabled);
        void enableImperialUnitsOption(boolean enabled);
        void enableMetricUnitsOption(boolean enabled);
        void recreateView();
    }
}
