package com.devshyeon.bustaja;

public class Stopmember {

    String stationName;
    String stationSeq;
    String stationy;
    String stationx;
    String stationId;


    public Stopmember(String stationName, String stationSeq, String stationy, String stationx, String stationId) {
        this.stationName = stationName;
        this.stationSeq = stationSeq;
        this.stationy = stationy;
        this.stationx = stationx;
        this.stationId = stationId;
    }

    public Stopmember() {
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationSeq() {
        return stationSeq;
    }

    public void setStationSeq(String stationSeq) {
        this.stationSeq = stationSeq;
    }

    public String getStationy() {
        return stationy;
    }

    public void setStationy(String stationy) {
        this.stationy = stationy;
    }

    public String getStationx() {
        return stationx;
    }

    public void setStationx(String stationx) {
        this.stationx = stationx;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
}
