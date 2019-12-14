package com.spaceshipfreehold.tirecorrector;

public interface IIdealAxleRatio {

    interface Presenter {
        void onViewCreated();
        void onViewStarted();
        void onPaused();
        void onOriginalTireDiameterEdited(String diameter);
        void onNewTireDiameterEdited(String diameter);
        void onOriginalRingAndPinionRatioEdited(String ratio);
    }

    interface View {
        void showToast(String message);
        void setDiameterInputUnitSuffix(String suffix);
        void setIdealRatio(String ratio);
    }

}
