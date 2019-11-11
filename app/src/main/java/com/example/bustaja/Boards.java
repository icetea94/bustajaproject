package com.example.bustaja;

public class Boards {
    private String title;
    private String date;
    private String contents;
    private String nickname;

    public Boards() {
    }

    public Boards(String title, String date, String contents, String nickname) {
        this.title = title;
        this.date = date;
        this.contents = contents;
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
