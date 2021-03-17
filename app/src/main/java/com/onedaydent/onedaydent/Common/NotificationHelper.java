package com.onedaydent.onedaydent.Common;

public class NotificationHelper {

    private static final String TB_NAME = "TB_NOTIFICATION";
    private static final String COL_AUTO = "ID";
    private static final String COL_1 = "NOTI_title";
    private static final String COL_2 = "NOTI_content";
    private static final String COL_3 = "NOTI_imgURL";
    private static final String COL_4 = "NOTI_URL";
    private static final String COL_5 = "NOTI_type";
    private static final String COL_6 = "NOTI_date";
    private static final String COL_7 = "NOTI_isRead";

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
