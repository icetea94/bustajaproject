package com.example.bustaja;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", BoardTitle);
        result.put("date", BoardDate);
        result.put("nick", BoardNick);
        result.put("contents", BoardContents);
        return result;
    }
}
