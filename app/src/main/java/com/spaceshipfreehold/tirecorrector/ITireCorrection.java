package com.spaceshipfreehold.tirecorrector;

import java.util.List;

public interface ITireCorrection {
    interface Presenter{
        void onViewCreated();
        void onViewStarted();
        void onPaused();
        void onOriginalDiameterEdited(String diameterString);
        void onNewDiameterEdited(String diameterString);
    }

    interface View{
        void showToast(String message);
        void setOriginalDiameter(String text);
        void setNewDiameter(String text);
        void setOriginalRevolutionsMetric(String revolutions);
        void setOriginalRevolutionsImperial(String revolutions);
        void setNewRevolutionsMetric(String revolutions);
        void setNewRevolutionsImperial(String revolutions);
        void setCorrectionFactor(String correctionFactor);
    }
}
