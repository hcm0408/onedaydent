package com.onedaydent.onedaydent.Common;

public class TreatListHelper {

    private static final String TB_NAME = "TB_TREATLIST";
    private static final String COL_AUTO = "ID";
    private static final String COL_1 = "TL_ListKey";
    private static final String COL_2 = "TL_Masterkey";
    private static final String COL_3 = "TL_TxName";
    private static final String COL_4 = "TL_Doctor";
    private static final String COL_5 = "TL_Date";
    private static final String COL_6 = "TL_TxDetail";
    private static final String COL_7 = "TL_Result";

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

    public static String getCol7() {
        return COL_7;
    }
}
