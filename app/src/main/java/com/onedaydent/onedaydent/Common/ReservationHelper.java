package com.onedaydent.onedaydent.Common;

public class ReservationHelper {

    private static final String TB_NAME = "TB_RESERV";
    private static final String COL_AUTO = "ID";
    private static final String COL_1 = "reserv_Date";
    private static final String COL_2 = "reserv_Time";
    private static final String COL_3 = "reserv_TxName";
    private static final String COL_4 = "reserv_Next";

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

}
