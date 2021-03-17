package com.onedaydent.onedaydent.Common;

public class PaymentHelper {

    private static final String TB_NAME = "TB_PAY";
    private static final String COL_AUTO = "ID";
    private static final String COL_1 = "pay_Cash";
    private static final String COL_2 = "pay_Card";
    private static final String COL_3 = "pay_Online";
    private static final String COL_4 = "pay_Misu";
    private static final String COL_5 = "pay_Type";
    private static final String COL_6 = "pay_Date";

    public static String getTbName() {
        return TB_NAME;
    }

    public static String getColAuto() {
        return COL_AUTO;
    }

    public static String getCol1() {
        return COL_1;
    }

    public static String getCol2() {
        return COL_2;
    }

    public static String getCol3() {
        return COL_3;
    }

    public static String getCol4() {
        return COL_4;
    }

    public static String getCol5() {
        return COL_5;
    }

    public static String getCol6() {
        return COL_6;
    }
}
