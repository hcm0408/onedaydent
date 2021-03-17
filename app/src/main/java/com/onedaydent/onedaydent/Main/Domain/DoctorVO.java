package com.onedaydent.onedaydent.Main.Domain;

public class DoctorVO {

    private int doc_IDX;
    private String doc_name;
    private String doc_work;
    private String doc_order;
    private String doc_ImgURL;
    private String doc_timetable;

    public int getDoc_IDX() {
        return doc_IDX;
    }

    public void setDoc_IDX(int doc_IDX) {
        this.doc_IDX = doc_IDX;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_work() {
        return doc_work;
    }

    public void setDoc_work(String doc_work) {
        this.doc_work = doc_work;
    }

    public String getDoc_order() {
        return doc_order;
    }

    public void setDoc_order(String doc_order) {
        this.doc_order = doc_order;
    }

    public String getDoc_ImgURL() {
        return doc_ImgURL;
    }

    public void setDoc_ImgURL(String doc_ImgURL) {
        this.doc_ImgURL = doc_ImgURL;
    }

    public String getDoc_timetable() {
        return doc_timetable;
    }

    public void setDoc_timetable(String doc_timetable) {
        this.doc_timetable = doc_timetable;
    }

    @Override
    public String toString() {
        return "DoctorVO{" +
                "doc_IDX=" + doc_IDX +
                ", doc_name='" + doc_name + '\'' +
                ", doc_work='" + doc_work + '\'' +
                ", doc_order='" + doc_order + '\'' +
                ", doc_ImgURL='" + doc_ImgURL + '\'' +
                ", doc_timetable='" + doc_timetable + '\'' +
                "}\n";
    }
}
