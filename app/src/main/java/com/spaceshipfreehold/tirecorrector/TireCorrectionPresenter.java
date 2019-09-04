package com.spaceshipfreehold.tirecorrector;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import androidx.annotation.NonNull;

/**
 * Presents data to the Tire corrector fragment and performs correction logic for new and old tire
 * sizes.
 */
class TireCorrectionPresenter implements ITireCorrection.Presenter {

    private ITireModel mModel;
    private ITireCorrection.View mView;

    private boolean mMetricUnits = false;
    private double mOriginalTireDiameter;
    private double mNewTireDiameter;
    private double mOriginalTireRevolutions;
    private double mNewTireRevolutions;

    TireCorrectionPresenter(@NonNull ITireModel model, @NonNull ITireCorrection.View view){
        mModel = model;
        mView = view;
    }

    @Override
    public void onViewCreated() {}

    @Override
    public void onViewStarted() {

        // Firstly, we must know how to compute figures for display.
        mMetricUnits = mModel.getUnitTypeMetric(false);

        // We persist only the tire diameters.
        mOriginalTireDiameter = mModel.getOriginalTireDiameter(0);
        mNewTireDiameter  = mModel.getNewTireDiameter(0);

        // Compute everything else.
        mOriginalTireRevolutions = getRevolutionsPerUnit(mOriginalTireDiameter);
        mNewTireRevolutions = getRevolutionsPerUnit(mNewTireDiameter);
        // TODO: compute correction factor.
        // TODO: compute distribution of speedo values. Perhaps imperial will be 20, 40, 60, 80, 100 (mph), same for kph?

        // Set data into view.
        if(mOriginalTireDiameter > 0){
            // Use raw full value for diameters.
            mView.setOriginalDiameter(String.valueOf(mOriginalTireDiameter));
        }

        if(mNewTireDiameter > 0){
            mView.setNewDiameter(String.valueOf(mNewTireDiameter));
        }

        if(mMetricUnits){
            mView.setOriginalRevolutionsMetric(displayifyDecimalNumber(mOriginalTireRevolutions));
            mView.setNewRevolutionsMetric(displayifyDecimalNumber(mNewTireRevolutions));
        } else {
            mView.setOriginalRevolutionsImperial(displayifyDecimalNumber(mOriginalTireRevolutions));
            mView.setNewRevolutionsImperial(displayifyDecimalNumber(mNewTireRevolutions));
        }

        // TODO: Set correction factor on view.
        // TODO: set distribution onto view.
    }

    @Override
    public void onPaused() {
        // Persist data for app exit.
        mModel.saveOriginalTireDiameter(mOriginalTireDiameter);
        mModel.saveNewTireDiameter(mNewTireDiameter);
    }

    private double getRevolutionsPerUnit(double diameter){
        if(diameter <= 0){
            return 0;
        } else if(mMetricUnits){
            return (1000/(diameter * Math.PI));
        } else {
            return (5280/((diameter * Math.PI)/12));
        }
    }

    private String displayifyDecimalNumber(double number){
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(number);
    }

    @Override
    public void onOriginalDiameterEdited(String diameterString) {
        double diameter = 0;
        try {
            diameter = Double.valueOf(diameterString);
            if (mMetricUnits) {
                // convert meters to inches
                mOriginalTireDiameter = diameter / 2.54;
                mOriginalTireRevolutions = getRevolutionsPerUnit(mOriginalTireDiameter);
                mView.setOriginalRevolutionsMetric(displayifyDecimalNumber(mOriginalTireRevolutions));
            } else {
                // treat as inches
                mOriginalTireDiameter = diameter;
                mOriginalTireRevolutions = getRevolutionsPerUnit(mOriginalTireDiameter);
                mView.setOriginalRevolutionsImperial(displayifyDecimalNumber(mOriginalTireRevolutions));
            }
            mOriginalTireDiameter = diameter;
        } catch (NumberFormatException e){
            mOriginalTireDiameter = 0;
            mView.setOriginalRevolutionsMetric("0");
        }
    }

    @Override
    public void onNewDiameterEdited(String diameterString) {
        double diameter = 0d;
        try {
            diameter = Double.valueOf(diameterString);
            if (mMetricUnits) {
                // convert meters to inches
                mNewTireDiameter = diameter / 2.54f;
                mNewTireRevolutions = getRevolutionsPerUnit(mNewTireDiameter);
                mView.setNewRevolutionsMetric(displayifyDecimalNumber(mNewTireRevolutions));
            } else {
                // treat as inches
                mNewTireDiameter = diameter;
                mNewTireRevolutions = getRevolutionsPerUnit(mNewTireDiameter);
                mView.setNewRevolutionsImperial(displayifyDecimalNumber(mNewTireRevolutions));
            }
        } catch (NumberFormatException e){
            mNewTireDiameter = 0;
            mView.setNewRevolutionsMetric("0");
        }
    }
}
