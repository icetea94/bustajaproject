package com.example.bustaja;

public class MessageBoardItem {
    String title;
    String contents;


    public MessageBoardItem() {
    }

    public MessageBoardItem(String title, String contents) {
        this.title = title;
        this.contents = contents;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

}
