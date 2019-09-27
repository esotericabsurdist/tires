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

public class TireSizeFragment extends TireFragment implements ITireSize.View {

    private View mRoot;
    private ITireSize.Presenter mPresenter;
    private String mTitle = "Size";
    private EditText mWidthEditText;
    private EditText mAspectRatioEditText;
    private EditText mRimSizeEditText;
    private WidthTextWatcher mWidthTextWatcher;
    private AspectRatioTextWatcher mAspectRatioTextWatcher;
    private RimSizeTextWatcher mRimSizeTextWatcher;
    private TextView mComputedTireSizeTextView;

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
        mWidthEditText = mRoot.findViewById(R.id.width_edit_text);
        mWidthTextWatcher = new WidthTextWatcher();
        mWidthEditText.addTextChangedListener(mWidthTextWatcher);

        mAspectRatioEditText = mRoot.findViewById(R.id.aspect_ratio_edit_text);
        mAspectRatioTextWatcher = new AspectRatioTextWatcher();
        mAspectRatioEditText.addTextChangedListener(mAspectRatioTextWatcher);

        mRimSizeEditText = mRoot.findViewById(R.id.rim_diameter_edit_text);
        mRimSizeTextWatcher = new RimSizeTextWatcher();
        mRimSizeEditText.addTextChangedListener(mRimSizeTextWatcher);

        mComputedTireSizeTextView = mRoot.findViewById(R.id.computed_tire_size_text_view);
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setConvertedTireSize(String size) {
        mComputedTireSizeTextView.setText(size);
    }

    @Override
    public void setWidth(String width) {
        mWidthEditText.setText(width);
    }

    @Override
    public void setAspectRatio(String aspectRatio) {
        mAspectRatioEditText.setText(aspectRatio);
    }

    @Override
    public void setRimSize(String rimSize) {
        mRimSizeEditText.setText(rimSize);
    }

    @Override
    public void setWidthSuffix(String suffix) {
        mWidthTextWatcher.setSuffix(suffix);
    }

    @Override
    public void setRimPrefix(String prefix) {
        mRimSizeTextWatcher.setPrefix(prefix);
    }

    private class WidthTextWatcher implements TextWatcher {
        private String mSuffix;

        WidthTextWatcher(){
            mSuffix = "";
        }

        public void setSuffix(String suffix){
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
                mWidthEditText.removeTextChangedListener(this);
                String text = s.toString();
                String width = text.replaceAll("[^0-9.]", "");
                mPresenter.onWidthEdited(width);
                mWidthEditText.setText((width.length()== 0) ? "": (width + mSuffix));
                mWidthEditText.setSelection(width.length());
                mWidthEditText.addTextChangedListener(this);
            }
        }
    }

    private class AspectRatioTextWatcher implements TextWatcher {
        private String mSuffix;

        AspectRatioTextWatcher(){
            mSuffix = "";
        }

        public void setSuffix(String suffix){
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
                String aspectRatio = text.replaceAll("[^0-9]", "");
                mPresenter.onAspectRatioEdited(aspectRatio);
            }
        }
    }

    private class RimSizeTextWatcher implements TextWatcher {
        private String mPrefix;

        RimSizeTextWatcher(){
            mPrefix = "";
        }

        public void setPrefix(String suffix){
            mPrefix = suffix;
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
                String rimSize = text.replaceAll("[^0-9]", "");
                mPresenter.onRimSizeEdited(rimSize);
                mRimSizeEditText.removeTextChangedListener(this);
                mRimSizeEditText.setText((rimSize.length()==0)? "" : (mPrefix + rimSize));
                mRimSizeEditText.setSelection((rimSize.length()==0)? 0 : (mPrefix + rimSize).length());
                mRimSizeEditText.addTextChangedListener(this);
            }
        }
    }
}
