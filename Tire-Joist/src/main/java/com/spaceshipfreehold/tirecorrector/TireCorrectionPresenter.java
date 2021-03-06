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
         // TODO: Organize method calls into if-else for metric or non metric set up.
        mMetricUnits = mModel.getUnitTypeMetric(false);
        if(mMetricUnits){
            mDiameterUnitString = TireJoist.METRIC_UNITS_SUFFIX;
        } else {
            mDiameterUnitString = TireJoist.IMPERIAL_UNITS_SUFFIX;
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

        mView.setCorrectionFactor(Utils.displayifyDecimalNumber(mCorrectionFactor,4));
        if(mOriginalTireDiameter > 0){
            // Use raw full value for diameters.
            mView.setOriginalDiameter(Utils.displayifyDecimalNumber(mOriginalTireDiameter,2) + mDiameterUnitString);
        }

        if(mNewTireDiameter > 0){
            mView.setNewDiameter(Utils.displayifyDecimalNumber(mNewTireDiameter,2) + mDiameterUnitString);
        }

        if(mMetricUnits){
            mView.setOriginalRevolutionsMetric(Utils.displayifyDecimalNumber(mOriginalTireRevolutions,2));
            mView.setNewRevolutionsMetric(Utils.displayifyDecimalNumber(mNewTireRevolutions,2));
        } else {
            mView.setOriginalRevolutionsImperial(Utils.displayifyDecimalNumber(mOriginalTireRevolutions,2));
            mView.setNewRevolutionsImperial(Utils.displayifyDecimalNumber(mNewTireRevolutions,2));
        }

    }


    @Override
    public void onPaused() {
        // Persist data for app exit.
        if(mMetricUnits){
            // We only store data in freedom units.
            mModel.saveOriginalTireDiameter(mOriginalTireDiameter/25.4); // millimeters to inches.
            mModel.saveNewTireDiameter(mNewTireDiameter/25.4);
        } else {
            mModel.saveOriginalTireDiameter(mOriginalTireDiameter);
            mModel.saveNewTireDiameter(mNewTireDiameter);
        }
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

    boolean diameterExceedsRange(double lowerInches, double upperInches, boolean metricUnits, double diameter){
        if(metricUnits){
            if(diameter/25.4 > upperInches || diameter < lowerInches){
                return true;
            } else {
                return false;
            }
        } else {
            if(diameter > upperInches || diameter < 0){
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void onOriginalDiameterEdited(String diameterString) {
        double diameter = 0;
        try {
            diameter = Double.valueOf(diameterString);
            if(diameterExceedsRange(0, 10000, mMetricUnits, diameter)){
                return;
            }
            else if (mMetricUnits) {
                mOriginalTireDiameter = diameter;
                mOriginalTireRevolutions = getRevolutionsPerUnit(mOriginalTireDiameter);
                mView.setOriginalRevolutionsMetric(Utils.displayifyDecimalNumber(mOriginalTireRevolutions,2));
                mView.setCorrectionFactor(Utils.displayifyDecimalNumber(getCorrectionFactor(),4));
            } else {
                mOriginalTireDiameter = diameter;
                mOriginalTireRevolutions = getRevolutionsPerUnit(mOriginalTireDiameter);
                mView.setOriginalRevolutionsImperial(Utils.displayifyDecimalNumber(mOriginalTireRevolutions,2));
                mView.setCorrectionFactor(Utils.displayifyDecimalNumber(getCorrectionFactor(),4));
            }
            mOriginalTireDiameter = diameter;
        } catch (NumberFormatException e){
            mOriginalTireDiameter = 0;
            if(mMetricUnits) {
                mView.setOriginalRevolutionsMetric("0");
            } else {
                mView.setNewRevolutionsImperial("0");
            }
            mView.setCorrectionFactor(Utils.displayifyDecimalNumber(getCorrectionFactor(), 4));
        }
    }

    @Override
    public void onNewDiameterEdited(String diameterString) {
        double diameter = 0;
        try {
            diameter = Double.valueOf(diameterString);
            if(diameterExceedsRange(0, 10000, mMetricUnits, diameter)){
                return;
            }
            else if (mMetricUnits) {
                mNewTireDiameter = diameter;
                mNewTireRevolutions = getRevolutionsPerUnit(mNewTireDiameter);
                mView.setNewRevolutionsMetric(Utils.displayifyDecimalNumber(mNewTireRevolutions, 2));
                mView.setCorrectionFactor(Utils.displayifyDecimalNumber(getCorrectionFactor(), 4));
            } else {
                mNewTireDiameter = diameter;
                mNewTireRevolutions = getRevolutionsPerUnit(mNewTireDiameter);
                mView.setNewRevolutionsImperial(Utils.displayifyDecimalNumber(mNewTireRevolutions, 2));
                mView.setCorrectionFactor(Utils.displayifyDecimalNumber(getCorrectionFactor(), 4));
            }
        } catch (NumberFormatException e){
            mNewTireDiameter = 0;
            if(mMetricUnits) {
                mView.setNewRevolutionsMetric("0");
            } else {
                mView.setNewRevolutionsImperial("0");
            }
            mView.setCorrectionFactor(Utils.displayifyDecimalNumber(getCorrectionFactor(), 4));
        }
    }
}
