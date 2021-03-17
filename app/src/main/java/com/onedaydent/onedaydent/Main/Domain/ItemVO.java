package com.onedaydent.onedaydent.Main.Domain;

public class ItemVO {
    private String left;
    private String center;
    private String right;
    private String content;

    public ItemVO(String left, String center, String right, String content) {
        this.left = left;
        this.center = center;
        this.right = right;
        this.content = content;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ItemVO{" +
                "left='" + left + '\'' +
                ", center='" + center + '\'' +
                ", right='" + right + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
