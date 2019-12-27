package com.spaceshipfreehold.tirecorrector;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public final class Utils {

    private Utils(){}

    public static String displayifyDecimalNumber(double number, int digitsToRightOfDecimal){
        String digits = new String(new char[digitsToRightOfDecimal]).replace('\0', '0');
        NumberFormat formatter = new DecimalFormat((Math.floor(number) == number && digitsToRightOfDecimal == 0) ? ("#0") : ("#0." + digits));
        return formatter.format(number);
    }

    public static double getIdealRatio(double originalDiameter, double newDiameter, double originalRatio) {
        if(originalDiameter <= 0 || newDiameter <= 0 || originalDiameter <= 0){
            return 0;
        } else {
            return (originalRatio * newDiameter) / originalDiameter;
        }
    }
}
