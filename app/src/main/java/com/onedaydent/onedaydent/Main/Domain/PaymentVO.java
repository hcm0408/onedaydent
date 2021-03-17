package com.onedaydent.onedaydent.Main.Domain;

public class PaymentVO {

    private int payCash;
    private int payCard;
    private int payOnline;
    private int payMisu;
    private String payType;
    private String payDate;

    public int getPayCash() {
        return payCash;
    }

    public void setPayCash(int payCash) {
        this.payCash = payCash;
    }

    public int getPayCard() {
        return payCard;
    }

    public void setPayCard(int payCard) {
        this.payCard = payCard;
    }

    public int getPayOnline() {
        return payOnline;
    }

    public void setPayOnline(int payOnline) {
        this.payOnline = payOnline;
    }

    public int getPayMisu() {
        return payMisu;
    }

    public void setPayMisu(int payMisu) {
        this.payMisu = payMisu;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return "PaymentVO{" +
                "payCash=" + payCash +
                ", payCard=" + payCard +
                ", payOnline=" + payOnline +
                ", payMisu=" + payMisu +
                ", payType='" + payType + '\'' +
                ", payDate='" + payDate + '\'' +
                "}\n";
    }
}
