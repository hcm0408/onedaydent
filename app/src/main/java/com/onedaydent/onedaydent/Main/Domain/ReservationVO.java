package com.onedaydent.onedaydent.Main.Domain;

public class ReservationVO implements Comparable<ReservationVO>{

    private String reserv_Date;
    private String reserv_Time;
    private String reserv_TxName;
    private String reserv_Next;

    public String getReserv_Date() {
        return reserv_Date;
    }

    public void setReserv_Date(String reserv_Date) {
        this.reserv_Date = reserv_Date;
    }

    public String getReserv_Time() {
        return reserv_Time;
    }

    public void setReserv_Time(String reserv_Time) {
        this.reserv_Time = reserv_Time;
    }

    public String getReserv_TxName() {
        return reserv_TxName;
    }

    public void setReserv_TxName(String reserv_TxName) {
        this.reserv_TxName = reserv_TxName;
    }

    public String getReserv_Next() {
        return reserv_Next;
    }

    public void setReserv_Next(String reserv_Next) {
        this.reserv_Next = reserv_Next;
    }

    @Override
    public String toString() {
        return "ReservationVO{" +
                "reserv_Date='" + reserv_Date + '\'' +
                ", reserv_Time='" + reserv_Time + '\'' +
                ", reserv_TxName='" + reserv_TxName + '\'' +
                ", reserv_Next='" + reserv_Next + '\'' +
                "}\n";
    }

    @Override
    public int compareTo(ReservationVO o) {
        return this.reserv_Date.compareTo(o.getReserv_Date());
    }
}
