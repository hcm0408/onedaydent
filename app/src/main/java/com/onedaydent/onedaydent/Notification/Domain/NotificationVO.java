package com.onedaydent.onedaydent.Notification.Domain;

public class NotificationVO {

    private String title;
    private String content;
    private String imgURL;
    private String URL;
    private String type;
    private String date;
    private int isRead;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "NotificationVO{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", URL='" + URL + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", isRead='" + isRead + '\'' +
                "}\n";
    }
}
