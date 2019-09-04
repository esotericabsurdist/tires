package com.spaceshipfreehold.tirecorrector;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TireCorrectionFragment extends TireFragment implements ITireCorrection.View{

    View mRoot;
    String mTitle = "Correction";
    ITireCorrection.Presenter mPresenter;

    TextView mOriginalRevolutionsPerUnitTextTextView;
    TextView mNewRevolutionsPerUnitTextTextView;
    EditText mOriginalDiameterEditText;
    EditText mNewDiameterEditText;

    public TireCorrectionFragment(){
        super();
    }

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
        mPresenter = new TireCorrectionPresenter(new TireUtilitiesModel(getActivity()), this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.tire_correction_factor_layout, container,false);
        mOriginalRevolutionsPerUnitTextTextView = mRoot.findViewById(R.id.original_revolutions_per_unit);
        mNewRevolutionsPerUnitTextTextView = mRoot.findViewById(R.id.new_revolutions_per_unit);
        mOriginalDiameterEditText = mRoot.findViewById(R.id.original_tire_diameter_edit_text);
        mOriginalDiameterEditText.addTextChangedListener(new OriginalDiameterTextWatcher());
        mNewDiameterEditText = mRoot.findViewById(R.id.new_tire_diameter_edit_text);
        mNewDiameterEditText.addTextChangedListener(new NewDiameterTextWatcher());

        mPresenter.onViewCreated();
        return mRoot;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onViewStarted();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPaused();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG);
    }

    @Override
    public void setOriginalDiameter(String text) {
        mOriginalDiameterEditText.setText(text);
    }

    @Override
    public void setNewDiameter(String text) {
        mNewDiameterEditText.setText(text);
    }

    @Override
    public void setOriginalRevolutionsMetric(String revolutions) {
        mOriginalRevolutionsPerUnitTextTextView.setText(revolutions);
        mOriginalRevolutionsPerUnitTextTextView.append(" " + getResources().getString(R.string.revolutions_per_kilometer_suffix));
    }

    @Override
    public void setOriginalRevolutionsImperial(String revolutions){
        mOriginalRevolutionsPerUnitTextTextView.setText(revolutions);
        mOriginalRevolutionsPerUnitTextTextView.append(" " + getResources().getString(R.string.revolutions_per_mile_suffix));
    }

    @Override
    public void setNewRevolutionsMetric(String revolutions) {
        mNewRevolutionsPerUnitTextTextView.setText(revolutions);
        mNewRevolutionsPerUnitTextTextView.append(" " + getResources().getString(R.string.revolutions_per_kilometer_suffix));
    }

    @Override
    public void setNewRevolutionsImperial(String revolutions){
        mNewRevolutionsPerUnitTextTextView.setText(revolutions);
        mNewRevolutionsPerUnitTextTextView.append(" " + getResources().getString(R.string.revolutions_per_mile_suffix));
    }

    private class OriginalDiameterTextWatcher  implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mPresenter.onOriginalDiameterEdited(s.toString());
        }
    }

    private class NewDiameterTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mPresenter.onNewDiameterEdited(s.toString());
        }
    }
}
