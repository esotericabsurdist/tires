package com.spaceshipfreehold.tirecorrector;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TireSizeFragment extends TireFragment implements ITireSize.View {

    View mRoot;
    String mTitle = "Size";
    ITireSize.Presenter mPresenter;

    public TireSizeFragment(){ }

    @Override
    public String getUtilityTitle() {
        return mTitle;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new TIreSizePresenter(new TireUtilitiesModel(getActivity()), this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.tire_size_layout, container, false);
        // TODO: inform presenter
        return mRoot;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
