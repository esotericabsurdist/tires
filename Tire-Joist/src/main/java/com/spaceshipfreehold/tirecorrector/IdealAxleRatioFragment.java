package com.spaceshipfreehold.tirecorrector;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IdealAxleRatioFragment extends TireFragment implements IIdealAxleRatio.View{

    private View mRoot;
    private IIdealAxleRatio.Presenter mPresenter;

    private EditText mOriginalDiameterEditText;
    private EditText mNewTireDiameterEditText;
    private EditText mOriginalRingAndPinionRatioEditText;
    private TextView mIdealRingAndPinionRatioTextView;

    private OriginalDiameterTextWatcher mOriginalTireDiameterTextWatcher;
    private NewTireDiameterTextWatcher mNewTireDiameterTextWatcher;
    private SimpleTextWatcher mRingAndPinionRatioTextWatcher;

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

        mOriginalDiameterEditText = mRoot.findViewById(R.id.ideal_ratio_original_tire_diameter);
        mNewTireDiameterEditText = mRoot.findViewById(R.id.ideal_ratio_new_tire_diameter_edit_text);
        mOriginalRingAndPinionRatioEditText = mRoot.findViewById(R.id.ideal_ratio_original_ring_and_pinion_ratio);

        mIdealRingAndPinionRatioTextView = mRoot.findViewById(R.id.ideal_ratio_text_view);

        mOriginalTireDiameterTextWatcher = new OriginalDiameterTextWatcher();
        mNewTireDiameterTextWatcher = new NewTireDiameterTextWatcher();
        mRingAndPinionRatioTextWatcher = new RingAndPinionRatioTextWatcher();

        mOriginalDiameterEditText.addTextChangedListener(mOriginalTireDiameterTextWatcher);
        mNewTireDiameterEditText.addTextChangedListener(mNewTireDiameterTextWatcher);
        mOriginalRingAndPinionRatioEditText.addTextChangedListener(mRingAndPinionRatioTextWatcher);

        mPresenter.onViewCreated();
        return mRoot;
    }

    @Override
    public void onStart() {
        super.onStart();

        mPresenter.onViewStarted();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setDiameterInputUnitSuffix(String suffix) {
        mOriginalTireDiameterTextWatcher.setSuffix(suffix);
        mNewTireDiameterTextWatcher.setSuffix(suffix);
    }


    @Override
    public void setIdealRatio(String ratio) {
        // TODO Prefix with "Ideal Ratio:" ? Or something descriptive
        mIdealRingAndPinionRatioTextView.setText(ratio);
    }

    private class OriginalDiameterTextWatcher extends SimpleTextWatcher {
        private String mSuffix;

        OriginalDiameterTextWatcher(){
            mSuffix = "";
        }

        public void setSuffix(String suffix){
            mSuffix = suffix;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s != null) {
                String text = s.toString();
                String diameter = text.replaceAll("[^0-9.]", "");
                mPresenter.onOriginalTireDiameterEdited(diameter);
                mOriginalDiameterEditText.removeTextChangedListener(this);
                mOriginalDiameterEditText.setText((diameter.length() == 0) ? "" : diameter + mSuffix);
                mOriginalDiameterEditText.setSelection((diameter.length() == 0) ? 0 : diameter.length());
                mOriginalDiameterEditText.addTextChangedListener(this);
            }
        }
    }

    private class NewTireDiameterTextWatcher extends SimpleTextWatcher {
        private String mSuffix;

        NewTireDiameterTextWatcher(){
            mSuffix = "";
        }

        public void setSuffix(String suffix){
            mSuffix = suffix;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s != null) {
                String text = s.toString();
                String diameter = text.replaceAll("[^0-9.]", "");
                mPresenter.onNewTireDiameterEdited(diameter);
                mNewTireDiameterEditText.removeTextChangedListener(this);
                mNewTireDiameterEditText.setText((diameter.length() == 0) ? "" : diameter + mSuffix);
                mNewTireDiameterEditText.setSelection((diameter.length() == 0) ? 0 : diameter.length());
                mNewTireDiameterEditText.addTextChangedListener(this);
            }
        }
    }

    private class RingAndPinionRatioTextWatcher extends SimpleTextWatcher {
        RingAndPinionRatioTextWatcher(){}

        @Override
        public void afterTextChanged(Editable s) {
            if(s != null) {
                mPresenter.onOriginalRingAndPinionRatioEdited(s.toString());
            }
        }
    }

}
