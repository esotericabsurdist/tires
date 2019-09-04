package com.spaceshipfreehold.tirecorrector;

import androidx.fragment.app.FragmentActivity;

public class TIreSizePresenter implements ITireSize.Presenter {

    ITireModel mModel;
    ITireSize.View mView;

    public TIreSizePresenter(ITireModel model, ITireSize.View view) {
        mModel = model;
        mView = view;
    }
}
