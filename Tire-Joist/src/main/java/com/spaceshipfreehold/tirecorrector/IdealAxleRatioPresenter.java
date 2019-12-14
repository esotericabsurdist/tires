package com.spaceshipfreehold.tirecorrector;

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
        // TODO Load from storage
    }

    @Override
    public void onViewStarted() {

    }

    @Override
    public void onPaused() {
        // TODO: Persist
    }

    private double getIdealRatio(double originalDiameter, double newDiameter, double originalRatio){
        if(originalDiameter == 0){
            return 0;
        } else {
            return (originalRatio * newDiameter) / originalDiameter;
        }
    }

    private String getIdealRatioString(double originalDiameter, double newDiameter, double originalRatio){
        if(originalDiameter <= 0 || newDiameter <= 0 || originalDiameter <= 0){
            return "0 : 0";
        }
        return getIdealRatio(originalDiameter, newDiameter, originalRatio) + " : 1";
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
