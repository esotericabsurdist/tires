package com.spaceshipfreehold.tirecorrector;

public class IdealAxleRatioPresenter implements IIdealAxleRatio.Presenter {

    private IIdealAxleRatio.View mView;
    private TireUtilitiesModel mModel;
    private double mOriginalDiameter;
    private double mNewDiameter;
    private double mOriginalRAndPRatio;

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

        mOriginalDiameter = mModel.getIdealRatioOriginalTireDiameter(0);
        if(mOriginalDiameter > 0){
            mView.setOriginalTireDiameter(String.valueOf(mOriginalDiameter));
        }

        mNewDiameter = mModel.getIdealRatioNewTireDiameter(0);
        if(mNewDiameter > 0){
            mView.setNewTireDiameter(String.valueOf(mNewDiameter));
        }

        mOriginalRAndPRatio = mModel.getOriginalRAndPRatio(0);
        if(mOriginalRAndPRatio > 0){
            mView.setOriginalRAndPRatio(String.valueOf(mOriginalRAndPRatio));
        }

        mView.setIdealRatio(getIdealRatioString(mOriginalDiameter, mNewDiameter, mOriginalRAndPRatio));
    }

    @Override
    public void onViewStarted() {

    }

    @Override
    public void onPaused() {
        mModel.saveIdealRatioOriginalTireDiameter(mOriginalDiameter);
        mModel.saveIdealRatioNewTireDiameter(mNewDiameter);
        mModel.saveOriginalRAndPRatio(mOriginalRAndPRatio);
    }

    private String getIdealRatioString(double originalDiameter, double newDiameter, double originalRatio){
        double idealRatio = Utils.getIdealRatio(originalDiameter, newDiameter, originalRatio);
        return Utils.displayifyDecimalNumber(idealRatio, 3);
    }

    @Override
    public void onOriginalTireDiameterEdited(String diameter) {
        try{
            mOriginalDiameter = Double.parseDouble(diameter);
        } catch (NumberFormatException e) {
            mOriginalDiameter = 0d;
        }
        mModel.saveIdealRatioOriginalTireDiameter(mOriginalDiameter);
        mView.setIdealRatio(getIdealRatioString(mOriginalDiameter, mNewDiameter, mOriginalRAndPRatio));
    }

    @Override
    public void onNewTireDiameterEdited(String diameter) {
        try{
            mNewDiameter = Double.parseDouble(diameter);
        } catch (NumberFormatException e){
            mNewDiameter = 0d;
        }
        mModel.saveIdealRatioNewTireDiameter(mNewDiameter);
        mView.setIdealRatio(getIdealRatioString(mOriginalDiameter, mNewDiameter, mOriginalRAndPRatio));
    }

    @Override
    public void onOriginalRingAndPinionRatioEdited(String ratio) {
        try{
            mOriginalRAndPRatio = Double.parseDouble(ratio);
        } catch (NumberFormatException e){
            mOriginalRAndPRatio = 0d;
        }
        mModel.saveOriginalRAndPRatio(mOriginalRAndPRatio);
        mView.setIdealRatio(getIdealRatioString(mOriginalDiameter, mNewDiameter, mOriginalRAndPRatio));
    }
}
