package com.onedaydent.onedaydent.Notice.Domain;

public class NoticeVO {

    private int wr_id;
    private String wr_subject;
    private String wr_content;
    private long wr_datetime;
    private String bf_source;
    private String bf_file;

    public int getWr_id() {
        return wr_id;
    }

    public void setWr_id(int wr_id) {
        this.wr_id = wr_id;
    }

    public String getWr_subject() {
        return wr_subject;
    }

    public void setWr_subject(String wr_subject) {
        this.wr_subject = wr_subject;
    }

    public String getWr_content() {
        return wr_content;
    }

    public void setWr_content(String wr_content) {
        this.wr_content = wr_content;
    }

    public long getWr_datetime() {
        return wr_datetime;
    }

    public void setWr_datetime(long wr_datetime) {
        this.wr_datetime = wr_datetime;
    }

    public String getBf_source() {
        return bf_source;
    }

    public void setBf_source(String bf_source) {
        this.bf_source = bf_source;
    }

    public String getBf_file() {
        return bf_file;
    }

    public void setBf_file(String bf_file) {
        this.bf_file = bf_file;
    }

    @Override
    public String toString() {
        return "NoticeVO{" +
                "wr_id=" + wr_id +
                ", wr_subject='" + wr_subject + '\'' +
                ", wr_content='" + wr_content + '\'' +
                ", wr_datetime=" + wr_datetime +
                ", bf_source='" + bf_source + '\'' +
                ", bf_file='" + bf_file + '\'' +
                "}\n";
    }
}