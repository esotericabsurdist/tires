package com.spaceshipfreehold.tirecorrector;

import android.util.Log;

import java.text.ParseException;

public class IdealAxleRatioPresenter implements IIdealAxleRatio.Presenter {

    private IIdealAxleRatio.View mView;
    private TireUtilitiesModel mModel;
    private double mOriginalDiameter;
    private double mNewDiameter;
    private double mOriginalRatio;

    IdealAxleRatioPresenter(TireUtilitiesModel tireUtilitiesModel, IIdealAxleRatio.View idealAxleRatioView){
        mModel = tireUtilitiesModel;
        mView = idealAxleRatioView;
    }

    @Override
    public void onViewCreated() {
        if(mModel.getUnitTypeMetric(TireJoist.DEFAULT_METRIC_UNITS)){
            mView.setDiameterInputUnitSuffix(TireJoist.METRIC_UNITS_SUFFIX);
        } else {
            mView.setDiameterInputUnitSuffix(TireJoist.IMPERIAL_UNITS_SUFFIX);
        }
    }

    @Override
    public void onViewStarted() {

    }

    @Override
    public void onPaused() {
        // TODO: Persist
    }

    private double getIdealRatio(double originalDiameter, double newDiameter, double originalRatio){
        if(originalDiameter <= 0 || newDiameter <= 0 || originalDiameter <= 0){
            return 0;
        } else {
            return (originalRatio * newDiameter) / originalDiameter;
        }
    }

    private String getIdealRatioString(double originalDiameter, double newDiameter, double originalRatio){
        return "Ideal Ratio: " + Utils.displayifyDecimalNumber(getIdealRatio(originalDiameter, newDiameter, originalRatio), 3) + " : 1";
    }

    @Override
    public void onOriginalTireDiameterEdited(String diameter) {
        try{
            mOriginalDiameter = Double.parseDouble(diameter);
        } catch (NumberFormatException e) {
            mOriginalDiameter = 0d;
        }
        mView.setIdealRatio(getIdealRatioString(mOriginalDiameter, mNewDiameter, mOriginalRatio));
    }

    @Override
    public void onNewTireDiameterEdited(String diameter) {
        try{
            mNewDiameter = Double.parseDouble(diameter);
        } catch (NumberFormatException e){
            mNewDiameter = 0d;
        }
        mView.setIdealRatio(getIdealRatioString(mOriginalDiameter, mNewDiameter, mOriginalRatio));
    }

    @Override
    public void onOriginalRingAndPinionRatioEdited(String ratio) {
        try{
            mOriginalRatio = Double.parseDouble(ratio);
        } catch (NumberFormatException e){
            mOriginalRatio = 0d;
        }
        mView.setIdealRatio(getIdealRatioString(mOriginalDiameter, mNewDiameter, mOriginalRatio));
    }
}
