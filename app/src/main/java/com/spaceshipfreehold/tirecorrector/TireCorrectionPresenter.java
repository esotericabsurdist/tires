package com.spaceshipfreehold.tirecorrector;

import android.util.Log;

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
    private String mDiameterUnitString;

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
        if(mMetricUnits){
            mDiameterUnitString = " mm";
        } else {
            mDiameterUnitString = " in";
        }

        // We persist only the tire diameters.
        if(mMetricUnits) {
            mOriginalTireDiameter = mModel.getOriginalTireDiameter(0)*25.4;
            mNewTireDiameter = mModel.getNewTireDiameter(0)*25.4;
        } else {
            mOriginalTireDiameter = mModel.getOriginalTireDiameter(0);
            mNewTireDiameter = mModel.getNewTireDiameter(0);
        }
        // Compute everything else and set it into the view.
        mView.setDiameterInputUnitSuffix(mDiameterUnitString);
        mOriginalTireRevolutions = getRevolutionsPerUnit(mOriginalTireDiameter);
        mNewTireRevolutions = getRevolutionsPerUnit(mNewTireDiameter);
        mCorrectionFactor = getCorrectionFactor();

        mView.setCorrectionFactor(displayifyDecimalNumber(mCorrectionFactor,4));
        if(mOriginalTireDiameter > 0){
            // Use raw full value for diameters.
            mView.setOriginalDiameter(displayifyDecimalNumber(mOriginalTireDiameter,2) + mDiameterUnitString);
        }

        if(mNewTireDiameter > 0){
            mView.setNewDiameter(displayifyDecimalNumber(mNewTireDiameter,2) + mDiameterUnitString);
        }

        if(mMetricUnits){
            mView.setOriginalRevolutionsMetric(displayifyDecimalNumber(mOriginalTireRevolutions,2));
            mView.setNewRevolutionsMetric(displayifyDecimalNumber(mNewTireRevolutions,2));
        } else {
            mView.setOriginalRevolutionsImperial(displayifyDecimalNumber(mOriginalTireRevolutions,2));
            mView.setNewRevolutionsImperial(displayifyDecimalNumber(mNewTireRevolutions,2));
        }

    }


    @Override
    public void onPaused() {
        // Persist data for app exit.
        if(mMetricUnits){
            // We only store data in freedom units.
            mModel.saveOriginalTireDiameter(mOriginalTireDiameter/25.4);
            mModel.saveNewTireDiameter(mNewTireDiameter/25.4);
        } else {
            // Already in standard/imperial.
            mModel.saveOriginalTireDiameter(mOriginalTireDiameter);
            mModel.saveNewTireDiameter(mNewTireDiameter);
        }
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
            // rev per km given diameter in millimeters.
            return (1000000/(diameter * Math.PI));
        } else {
            // rev per mile given diameter in inches.
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

    private String displayifyDecimalNumber(double number, int digitsToRightOfDecimal){
        String digits = new String(new char[digitsToRightOfDecimal]).replace('\0', '0');
        NumberFormat formatter = new DecimalFormat("#0." + digits);
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
                mView.setOriginalRevolutionsMetric(displayifyDecimalNumber(mOriginalTireRevolutions,2));
                mView.setCorrectionFactor(displayifyDecimalNumber(getCorrectionFactor(),4));
            } else {
                // treat as inches
                mOriginalTireDiameter = diameter;
                mOriginalTireRevolutions = getRevolutionsPerUnit(mOriginalTireDiameter);
                mView.setOriginalRevolutionsImperial(displayifyDecimalNumber(mOriginalTireRevolutions,2));
                mView.setCorrectionFactor(displayifyDecimalNumber(getCorrectionFactor(),4));
            }
            mOriginalTireDiameter = diameter;
        } catch (NumberFormatException e){
            mOriginalTireDiameter = 0;
            mView.setOriginalRevolutionsMetric("0");
            mView.setCorrectionFactor(displayifyDecimalNumber(getCorrectionFactor(), 4));
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
                mView.setNewRevolutionsMetric(displayifyDecimalNumber(mNewTireRevolutions, 2));
                mView.setCorrectionFactor(displayifyDecimalNumber(getCorrectionFactor(), 4));
            } else {
                // treat as inches
                mNewTireDiameter = diameter;
                mNewTireRevolutions = getRevolutionsPerUnit(mNewTireDiameter);
                mView.setNewRevolutionsImperial(displayifyDecimalNumber(mNewTireRevolutions, 2));
                mView.setCorrectionFactor(displayifyDecimalNumber(getCorrectionFactor(), 4));
            }
        } catch (NumberFormatException e){
            mNewTireDiameter = 0;
            mView.setNewRevolutionsMetric("0");
            mView.setCorrectionFactor(displayifyDecimalNumber(getCorrectionFactor(), 4));
        }
    }
}
