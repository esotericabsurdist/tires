package com.spaceshipfreehold.tirecorrector;

public interface ITireSize {

    interface Presenter{
        void onViewCreated();
        void onViewStarted();
        void onPaused();
        void onWidthEdited(String widthString);
        void onAspectRatioEdited(String sideWall);
        void onRimSizeEdited(String rimSize);
    }

    interface View {
        void showToast(String message);
        void setConvertedTireSize(String size);
        void setWidth(String width);
        void setAspectRatio(String ratio);
        void setRimSize(String rimSize);
        void setWidthSuffix(String suffix);
        void setRimPrefix(String prefix);
    }
}
