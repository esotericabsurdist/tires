package com.spaceshipfreehold.tirecorrector;

public interface ITireModel {

    /**
     * @param position The integer position of the tab where the user left off. [0, N]
     */
    void saveRecentUtilitiesTabPosition(int position);

    /**
     * @param defaultValue
     * @return The index of the most recent utility that was used.
     */
    int getRecentUtility(int defaultValue);

    /**
     * Set to true if metric units are to be used for inputs and outputs. Storage will
     * always be imperial.
     *
     * @param metric If true, metric units will be expected and displayed where relevant.
     */
    void saveUnitTypeAsMetric(boolean metric);

    /**
     *
     * @return True indicates that units should be displayed and expected from inputs as metric.
     */
    boolean getUnitTypeMetric(boolean defaultValue);

    /**
     * Support is only for a light theme and a dark theme. Store the state of the theme.
     * @param enable
     */
    void saveDarkThemeEnabled(boolean enable);

    /**
     *
     * @param defaultValue
     * @return
     */
    boolean getDarkThemeEnabled(boolean defaultValue);

    void saveOriginalTireDiameter(double diameter);

    double getOriginalTireDiameter(double defaultDiameter);

    void saveNewTireDiameter(double diameter);

    double getNewTireDiameter(double defaultDiameter);
}
