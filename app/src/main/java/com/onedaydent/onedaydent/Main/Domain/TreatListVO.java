package com.onedaydent.onedaydent.Main.Domain;

public class TreatListVO {
    private String TL_Listkey;
    private String TL_Masterkey;
    private String TL_TxName;
    private String TL_Doctor;
    private String TL_Date;
    private String TL_TxDetail;
    private String TL_Result;

    public String getTL_Listkey() {
        return TL_Listkey;
    }

    public void setTL_Listkey(String TL_Listkey) {
        this.TL_Listkey = TL_Listkey;
    }

    public String getTL_Masterkey() {
        return TL_Masterkey;
    }

    public void setTL_Masterkey(String TL_Masterkey) {
        this.TL_Masterkey = TL_Masterkey;
    }

    public String getTL_TxName() {
        return TL_TxName;
    }

    public void setTL_TxName(String TL_TxName) {
        this.TL_TxName = TL_TxName;
    }

    public String getTL_Doctor() {
        return TL_Doctor;
    }

    public void setTL_Doctor(String TL_Doctor) {
        this.TL_Doctor = TL_Doctor;
    }

    public String getTL_Date() {
        return TL_Date;
    }

    public void setTL_Date(String TL_Date) {
        this.TL_Date = TL_Date;
    }

    public String getTL_TxDetail() {
        return TL_TxDetail;
    }

    public void setTL_TxDetail(String TL_TxDetail) {
        this.TL_TxDetail = TL_TxDetail;
    }

    public String getTL_Result() {
        return TL_Result;
    }

    public void setTL_Result(String TL_Result) {
        this.TL_Result = TL_Result;
    }

    @Override
    public String toString() {
        return "TreatListVO{" +
                "TL_Listkey='" + TL_Listkey + '\'' +
                ", TL_Masterkey='" + TL_Masterkey + '\'' +
                ", TL_TxName='" + TL_TxName + '\'' +
                ", TL_Doctor='" + TL_Doctor + '\'' +
                ", TL_Date='" + TL_Date + '\'' +
                ", TL_TxDetail='" + TL_TxDetail + '\'' +
                ", TL_Result='" + TL_Result + '\'' +
                "}\n";
    }
}
