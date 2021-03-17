package com.onedaydent.onedaydent.Main.Domain;

public class TreatDetailListVO implements  Cloneable {
    private String TDL_Listkey;
    private String TDL_Detailkey;
    private String TDL_TxName;
    private int TDL_Order;
    private int TDL_Depth;
    private int TDL_Result;
    private String TDL_Date;
    private String TDL_Doctor;

    public String getTDL_Listkey() {
        return TDL_Listkey;
    }

    public void setTDL_Listkey(String TDL_Listkey) {
        this.TDL_Listkey = TDL_Listkey;
    }

    public String getTDL_Detailkey() {
        return TDL_Detailkey;
    }

    public void setTDL_Detailkey(String TDL_Detailkey) {
        this.TDL_Detailkey = TDL_Detailkey;
    }

    public String getTDL_TxName() {
        return TDL_TxName;
    }

    public void setTDL_TxName(String TDL_TxName) {
        this.TDL_TxName = TDL_TxName;
    }

    public int getTDL_Order() {
        return TDL_Order;
    }

    public void setTDL_Order(int TDL_Order) {
        this.TDL_Order = TDL_Order;
    }

    public int getTDL_Depth() {
        return TDL_Depth;
    }

    public void setTDL_Depth(int TDL_Depth) {
        this.TDL_Depth = TDL_Depth;
    }

    public int getTDL_Result() {
        return TDL_Result;
    }

    public void setTDL_Result(int TDL_Result) {
        this.TDL_Result = TDL_Result;
    }

    public String getTDL_Date() {
        return TDL_Date;
    }

    public void setTDL_Date(String TDL_Date) {
        this.TDL_Date = TDL_Date;
    }

    public String getTDL_Doctor() {
        return TDL_Doctor;
    }

    public void setTDL_Doctor(String TDL_Doctor) {
        this.TDL_Doctor = TDL_Doctor;
    }

    @Override
    public String toString() {
        return "TreatDetailListVO{" +
                "TDL_Listkey='" + TDL_Listkey + '\'' +
                ", TDL_Detailkey='" + TDL_Detailkey + '\'' +
                ", TDL_TxName='" + TDL_TxName + '\'' +
                ", TDL_Order=" + TDL_Order +
                ", TDL_Depth=" + TDL_Depth +
                ", TDL_Result=" + TDL_Result +
                ", TDL_Date='" + TDL_Date + '\'' +
                ", TDL_Doctor='" + TDL_Doctor + '\'' +
                "}\n";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}