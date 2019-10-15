package com.spaceshipfreehold.tirecorrector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TireFitmentFragment extends TireFragment implements ITireFitment.View{

    View mRoot;
    ITireFitment.Presenter mPresenter;
    String mTitle = "Fitment";
    public TireFitmentFragment(){
        super();
    }

    @Override
    String getUtilityTitle() {
        return mTitle;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new TireFitmentPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.tire_fitment_layout, container, false);
        // TODO initialize view.

        mPresenter.onViewCreated();
        return mRoot;
    }
}
