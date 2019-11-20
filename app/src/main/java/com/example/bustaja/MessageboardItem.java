package com.example.bustaja;


import java.io.Serializable;

public class MessageboardItem {

     String BoardTitle;
     String BoardContents;
     String BoardDate;
     String BoardNick;

    public MessageboardItem(String boardTitle, String boardContents, String boardDate, String boardNick) {
        BoardTitle = boardTitle;
        BoardContents = boardContents;
        BoardDate = boardDate;
        BoardNick = boardNick;
    }

    public String getBoardTitle() {
        return BoardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        BoardTitle = boardTitle;
    }

    public String getBoardContents() {
        return BoardContents;
    }

    public void setBoardContents(String boardContents) {
        BoardContents = boardContents;
    }

    public String getBoardDate() {
        return BoardDate;
    }

    public void setBoardDate(String boardDate) {
        BoardDate = boardDate;
    }

    public String getBoardNick() {
        return BoardNick;
    }

    public void setBoardNick(String boardNick) {
        BoardNick = boardNick;
    }

    public MessageboardItem() {
    }


}
