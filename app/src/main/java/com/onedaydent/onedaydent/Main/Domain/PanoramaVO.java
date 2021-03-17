package com.onedaydent.onedaydent.Main.Domain;

import java.sql.Timestamp;

public class PanoramaVO {
    private String Chart;
    private Timestamp C_Date;
    private String C_IPName;
    private String C_Url;

    public String getChart() {
        return Chart;
    }

    public void setChart(String chart) {
        Chart = chart;
    }

    public Timestamp getC_Date() {
        return C_Date;
    }

    public void setC_Date(Timestamp c_Date) {
        C_Date = c_Date;
    }

    public String getC_IPName() {
        return C_IPName;
    }

    public void setC_IPName(String c_IPName) {
        C_IPName = c_IPName;
    }

    public String getC_Url() {
        return C_Url;
    }

    public void setC_Url(String c_Url) {
        C_Url = c_Url;
    }

    @Override
    public String toString() {
        return "PanoramaVO{" +
                "Chart='" + Chart + '\'' +
                ", C_Date=" + C_Date +
                ", C_IPName='" + C_IPName + '\'' +
                ", C_Url='" + C_Url + '\'' +
                "}\n";
    }
}
