package com.spaceshipfreehold.tirecorrector;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TIreSizePresenter implements ITireSize.Presenter {

    private ITireModel mModel;
    private ITireSize.View mView;
    private double mWidth;
    private int mAspectRatio;
    private int mRimSize;

    private double mConvertedWidth;
    private double mTireDiameter;

    public TIreSizePresenter(ITireModel model, ITireSize.View view) {
        mModel = model;
        mView = view;
    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public void onViewStarted() {
        mWidth = mModel.getTireWidth(0);
        mConvertedWidth = mWidth/25.4; // saved values are in mm in this case.
        mAspectRatio = (int)mModel.getAspectRatio(0); // We may want to express the ratio as actual decimal in the future.
        mRimSize = (int)mModel.getRimSize(0); // We may want to allow for odd rim sizes, eg. 17.5" rim in the future.
        mTireDiameter = getUSTireDiameterForEUDimensions(mWidth, mAspectRatio, mRimSize);

        // Set converted tire size string.
        mView.setConvertedTireSize(getConvertedTireSizeString(mTireDiameter, mConvertedWidth, mRimSize));

        mView.setWidthSuffix(" mm");
        mView.setRimPrefix("R");
        if(mWidth > 0){
            mView.setWidth(String.valueOf(mWidth));
        }

        if (mAspectRatio > 0) {
            mView.setAspectRatio(String.valueOf(mAspectRatio));
        }

        if (mRimSize > 0) {
            mView.setRimSize(String.valueOf(mRimSize));
        }
    }

    @Override
    public void onPaused() {
        mModel.saveTireWidth(mWidth);
        mModel.saveAspectRatio(mAspectRatio);
        mModel.saveRimSize(mRimSize);
    }

    private String displayifyDecimalNumber(double number, int digitsToRightOfDecimal){
        String digits = new String(new char[digitsToRightOfDecimal]).replace('\0', '0');
        NumberFormat formatter = new DecimalFormat((Math.floor(number) == number && digitsToRightOfDecimal == 0) ? ("#0") : ("#0." + digits));
        return formatter.format(number);
    }

    private double getUSTireDiameterForEUDimensions(double width, double aspectRatio, double rimSize){
        if(width <= 0 || aspectRatio <= 0 || rimSize <= 0){
            return 0;
        }
        return (2*(width/25.4)*(aspectRatio/100)) + rimSize;
    }

    private String getConvertedTireSizeString(double diameter, double width, double rimSize){
        String out = "";
        out += displayifyDecimalNumber(diameter, 2);
        out += "\" x ";
        out += displayifyDecimalNumber(width, 2);
        out += "\" R";
        out += (int)rimSize;
        return out;
    }

    boolean diameterExceedsRange(double lowerInches, double upperInches, boolean metricUnits, double diameter){
        if(metricUnits){
            if(diameter/25.4 > upperInches || diameter < lowerInches){
                return true;
            } else {
                return false;
            }
        } else {
            if(diameter > upperInches || diameter < 0){
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void onWidthEdited(String widthString) {
        try {
            double width = 0;
            width = Double.valueOf(widthString);
            if(width > 0){
                mWidth = width;
                mConvertedWidth = mWidth / 25.4;
                mTireDiameter = getUSTireDiameterForEUDimensions(mWidth, mAspectRatio, mRimSize);
            } else {
                mWidth = 0;
                mConvertedWidth = 0;
                mTireDiameter = 0;
            }
        } catch (NumberFormatException e){
            mWidth = 0;
            mConvertedWidth = 0;
            mTireDiameter = 0;
        }
        mView.setConvertedTireSize(getConvertedTireSizeString(mTireDiameter, mConvertedWidth, mRimSize));
    }

    @Override
    public void onAspectRatioEdited(String aspectRatioString) {
        try {
            int aspectRatio = 0;
            aspectRatio = Integer.valueOf(aspectRatioString);
            mAspectRatio = aspectRatio;
            mTireDiameter = getUSTireDiameterForEUDimensions(mWidth, mAspectRatio, mRimSize);
        } catch (NumberFormatException e){
            mAspectRatio = 0;
            mTireDiameter = 0;
        }
        mView.setConvertedTireSize(getConvertedTireSizeString(mTireDiameter, mConvertedWidth, mRimSize));
    }

    @Override
    public void onRimSizeEdited(String rimSizeString) {
        try{
            int rimSize = 0;
            rimSize = Integer.valueOf(rimSizeString);
            if(diameterExceedsRange(0, 999, false, rimSize)){
                return;
            } else {
                mRimSize = rimSize;
                mTireDiameter = getUSTireDiameterForEUDimensions(mWidth, mAspectRatio, mRimSize);
            }
        } catch (NumberFormatException e){
            mRimSize = 0;
            mTireDiameter = 0;
        }
        mView.setConvertedTireSize(getConvertedTireSizeString(mTireDiameter, mConvertedWidth, mRimSize));
    }
}
