package com.example.bustaja;

public class BoardVO {
    String bTitle;
    String bContents;
    String bNick;
    String bDate;

    public BoardVO() {

    }
    public BoardVO(String bTitle, String bContents, String bNick, String bDate) {
        this.bTitle = bTitle;
        this.bContents = bContents;
        this.bNick = bNick;
        this.bDate = bDate;
    }

    public String getbTitle() {
        return bTitle;
    }

    public void setbTitle(String bTitle) {
        this.bTitle = bTitle;
    }

    public String getbContents() {
        return bContents;
    }

    public void setbContents(String bContents) {
        this.bContents = bContents;
    }

    public String getbNick() {
        return bNick;
    }

    public void setbNick(String bNick) {
        this.bNick = bNick;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }
}
