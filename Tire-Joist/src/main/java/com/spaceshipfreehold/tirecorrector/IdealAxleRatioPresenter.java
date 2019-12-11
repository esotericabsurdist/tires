package com.spaceshipfreehold.tirecorrector;

public class IdealAxleRatioPresenter implements IIdealAxleRatio.Presenter {

    private IIdealAxleRatio.View mView;
    private TireUtilitiesModel mModel;

    IdealAxleRatioPresenter(TireUtilitiesModel tireUtilitiesModel, IIdealAxleRatio.View idealAxleRatioView){
        mModel = tireUtilitiesModel;
        mView = idealAxleRatioView;
    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public void onViewStarted() {

    }

    @Override
    public void onPaused() {

    }
}
