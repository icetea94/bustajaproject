package com.devshyeon.bustaja;

public class NoticeItem {


    private String NoticeTitle;
    private String NoticeSubTitle;
    private String NoticeContents;

    public NoticeItem() {
    }

    public NoticeItem(String noticeTitle, String noticeSubTitle, String noticeContents) {
        NoticeTitle = noticeTitle;
        NoticeSubTitle = noticeSubTitle;
        NoticeContents = noticeContents;
    }

    public String getNoticeTitle() {
        return NoticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        NoticeTitle = noticeTitle;
    }

    public String getNoticeSubTitle() {
        return NoticeSubTitle;
    }

    public void setNoticeSubTitle(String noticeSubTitle) {
        NoticeSubTitle = noticeSubTitle;
    }

    public String getNoticeContents() {
        return NoticeContents;
    }

    public void setNoticeContents(String noticeContents) {
        NoticeContents = noticeContents;
    }
}

