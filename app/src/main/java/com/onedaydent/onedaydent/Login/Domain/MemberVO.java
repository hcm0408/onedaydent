package com.onedaydent.onedaydent.Login.Domain;

public class MemberVO {
    private String LM_patientNo;
    private String LM_chartID;
    private String LM_name;
    private String LM_token;
    private int LM_push;
    private int LM_event;
    private String LM_rubberBandChangeDate;

    public String getLM_patientNo() {
        return LM_patientNo;
    }

    public void setLM_patientNo(String LM_patientNo) {
        this.LM_patientNo = LM_patientNo;
    }

    public String getLM_chartID() {
        return LM_chartID;
    }

    public void setLM_chartID(String LM_chartID) {
        this.LM_chartID = LM_chartID;
    }

    public String getLM_name() {
        return LM_name;
    }

    public void setLM_name(String LM_name) {
        this.LM_name = LM_name;
    }

    public String getLM_token() {
        return LM_token;
    }

    public void setLM_token(String LM_token) {
        this.LM_token = LM_token;
    }

    public int getLM_push() {
        return LM_push;
    }

    public void setLM_push(int LM_push) {
        this.LM_push = LM_push;
    }

    public int getLM_event() {
        return LM_event;
    }

    public void setLM_event(int LM_event) {
        this.LM_event = LM_event;
    }

    public String getLM_rubberBandChangeDate() {
        return LM_rubberBandChangeDate;
    }

    public void setLM_rubberBandChangeDate(String LM_rubberBandChangeDate) {
        this.LM_rubberBandChangeDate = LM_rubberBandChangeDate;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "LM_patientNo='" + LM_patientNo + '\'' +
                ", LM_chartID='" + LM_chartID + '\'' +
                ", LM_name='" + LM_name + '\'' +
                ", LM_token='" + LM_token + '\'' +
                ", LM_push=" + LM_push +
                ", LM_event=" + LM_event +
                ", LM_rubberBandChangeDate='" + LM_rubberBandChangeDate + '\'' +
                "\n";
    }

}
