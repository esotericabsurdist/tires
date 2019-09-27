package com.spaceshipfreehold.tirecorrector;

public interface ITireUtility {
    interface Presenter{
        void onStarted();
        void imperialUnitsSelected();
        void metricUnitsSelected();
        void darkThemeSelected();
        void lightThemeSelected();
        void onUtilitySelected(int position);
        void onOptionsMenuPrepared();
        void aboutOptionSelected();
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
        void displayAboutMenu();
    }
}
