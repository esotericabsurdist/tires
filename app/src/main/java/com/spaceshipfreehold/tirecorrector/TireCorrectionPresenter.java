package com.spaceshipfreehold.tirecorrector;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

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
    private double mCorrectionFactor;

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
        mCorrectionFactor = getCorrectionFactor();

        // Set data into view.
        mView.setCorrectionFactor(displayifyDecimalNumber(mCorrectionFactor));
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

    }


    @Override
    public void onPaused() {
        // Persist data for app exit.
        mModel.saveOriginalTireDiameter(mOriginalTireDiameter);
        mModel.saveNewTireDiameter(mNewTireDiameter);
    }


    private List<Number> getLinearRange(double slope, double step, double lowerBound, double upperBound) {
        List<Number> out = new ArrayList<>();
        if(lowerBound < upperBound) {
            double x = lowerBound;
            while (x <= upperBound) {
                out.add(x*slope);
                x += step;
            }
        }
        return out;
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

    private double getCorrectionFactor(){
        if(mNewTireDiameter > 0 && mOriginalTireDiameter > 0){
            double correctionFactor = 0;
            double difference = mNewTireDiameter - mOriginalTireDiameter;
            double percentChange = difference/mOriginalTireDiameter;
            // New tire is larger, thus correction factor shall be greater than 1.
            // New tire is smaller, thus factor shall be less than one.
            correctionFactor = 1 + percentChange;
            return correctionFactor;
        } else {
            return 0;
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
                mView.setCorrectionFactor(displayifyDecimalNumber(getCorrectionFactor()));
            } else {
                // treat as inches
                mOriginalTireDiameter = diameter;
                mOriginalTireRevolutions = getRevolutionsPerUnit(mOriginalTireDiameter);
                mView.setOriginalRevolutionsImperial(displayifyDecimalNumber(mOriginalTireRevolutions));
                mView.setCorrectionFactor(displayifyDecimalNumber(getCorrectionFactor()));
            }
            mOriginalTireDiameter = diameter;
        } catch (NumberFormatException e){
            mOriginalTireDiameter = 0;
            mView.setOriginalRevolutionsMetric("0");
            mView.setCorrectionFactor(displayifyDecimalNumber(getCorrectionFactor()));
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
                mView.setCorrectionFactor(displayifyDecimalNumber(getCorrectionFactor()));
            } else {
                // treat as inches
                mNewTireDiameter = diameter;
                mNewTireRevolutions = getRevolutionsPerUnit(mNewTireDiameter);
                mView.setNewRevolutionsImperial(displayifyDecimalNumber(mNewTireRevolutions));
                mView.setCorrectionFactor(displayifyDecimalNumber(getCorrectionFactor()));
            }
        } catch (NumberFormatException e){
            mNewTireDiameter = 0;
            mView.setNewRevolutionsMetric("0");
            mView.setCorrectionFactor(displayifyDecimalNumber(getCorrectionFactor()));
        }
    }
}
