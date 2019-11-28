package com.example.bustaja;




public class MessageboardItem {

     String BoardTitle;
     String BoardContents;
     String BoardDate;
     String BoardNick;
//     int Hitcount;
    public MessageboardItem(String boardTitle, String boardContents, String boardDate, String boardNick) { //,int hitcount
        BoardTitle = boardTitle;
        BoardContents = boardContents;
        BoardDate = boardDate;
        BoardNick = boardNick;
//        Hitcount=hitcount;
    }

//    public int getHitcount() {
//        return Hitcount;
//    }
//
//    public void setHitcount(int hitcount) {
//        Hitcount = hitcount;
//    }

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
