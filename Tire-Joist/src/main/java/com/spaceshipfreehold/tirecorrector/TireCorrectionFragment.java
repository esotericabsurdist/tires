package com.spaceshipfreehold.tirecorrector;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TireCorrectionFragment extends TireFragment implements ITireCorrection.View{

    View mRoot;
    ITireCorrection.Presenter mPresenter;
    String mTitle = "Correction";
    TextView mOriginalRevolutionsPerUnitTextTextView;
    TextView mNewRevolutionsPerUnitTextTextView;
    TextView mCorrectionFactorTextView;
    EditText mOriginalDiameterEditText;
    EditText mNewDiameterEditText;

    OriginalDiameterTextWatcher mOriginalDiameterTextWatcher;
    NewDiameterTextWatcher mNewDiameterTextWatcher;

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
        mOriginalDiameterTextWatcher = new OriginalDiameterTextWatcher();
        mOriginalDiameterEditText.addTextChangedListener(mOriginalDiameterTextWatcher);

        mNewDiameterEditText = mRoot.findViewById(R.id.new_tire_diameter_edit_text);
        mNewDiameterTextWatcher = new NewDiameterTextWatcher();
        mNewDiameterEditText.addTextChangedListener(mNewDiameterTextWatcher);

        mCorrectionFactorTextView = mRoot.findViewById(R.id.correction_factor_textview);


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

    @Override
    public void setCorrectionFactor(String correctionFactor) {
        mCorrectionFactorTextView.setText(correctionFactor);
    }

    @Override
    public void setDiameterInputUnitSuffix(String suffix) {
        mOriginalDiameterTextWatcher.setUnitSuffix(suffix);
        mNewDiameterTextWatcher.setUnitSuffix(suffix);
    }

    private class OriginalDiameterTextWatcher implements TextWatcher {
        private String mSuffix;

        OriginalDiameterTextWatcher(){
            mSuffix = "";
        }

        public void setUnitSuffix(String suffix){
            mSuffix = suffix;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Don't edit text here.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Don't edit text here.
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s != null) {
                String text = s.toString();
                String diameter = text.replaceAll("[^0-9.]", "");
                mPresenter.onOriginalDiameterEdited(diameter);
                mOriginalDiameterEditText.removeTextChangedListener(this);
                mOriginalDiameterEditText.setText((diameter.length() == 0) ? "" : diameter + mSuffix);
                mOriginalDiameterEditText.setSelection((diameter.length() == 0) ? 0 : diameter.length());
                mOriginalDiameterEditText.addTextChangedListener(this);
            }
        }
    }

    private class NewDiameterTextWatcher implements TextWatcher {
        private String mSuffix;

        NewDiameterTextWatcher(){
            mSuffix = "";
        }

        public void setUnitSuffix(String suffix){
            mSuffix = suffix;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Don't edit text here.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Don't edit text here.
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s != null) {
                String text = s.toString();
                String diameter = text.replaceAll("[^0-9.]", "");
                mPresenter.onNewDiameterEdited(diameter);
                mNewDiameterEditText.removeTextChangedListener(this);
                mNewDiameterEditText.setText((diameter.length() == 0) ? "" : diameter + mSuffix);
                mNewDiameterEditText.setSelection((diameter.length() == 0) ? 0 : diameter.length());
                mNewDiameterEditText.addTextChangedListener(this);
            }
        }
    }
}
