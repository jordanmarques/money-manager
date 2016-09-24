package com.jojo.money_manager.pojo;


public class History {

    private String value;
    private String comment;
    private String tag;
    private String date;

    public History(String value, String comment, String date, String tag) {
        this.value = value;
        this.comment = comment;
        this.date = date;
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
}
