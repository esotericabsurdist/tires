package com.spaceshipfreehold.tirecorrector;

public interface IIdealAxleRatio {

    interface Presenter {
        void onViewCreated();
        void onViewStarted();
        void onPaused();
    }

    interface View {
        void showToast(String message);
    }

}
