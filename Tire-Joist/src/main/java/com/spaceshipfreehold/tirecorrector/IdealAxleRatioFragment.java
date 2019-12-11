package com.spaceshipfreehold.tirecorrector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class IdealAxleRatioFragment extends TireFragment implements IIdealAxleRatio.View{

    private View mRoot;
    private IIdealAxleRatio.Presenter mPresenter;

    public IdealAxleRatioFragment(){
        mPresenter = new IdealAxleRatioPresenter(new TireUtilitiesModel(getActivity()), this);
    }

    @Override
    String getUtilityTitle() {
        return "Ideal Axle Ratio";
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ideal_axle_ratio_layout, container,false);


        mPresenter.onViewCreated();
        return mRoot;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
