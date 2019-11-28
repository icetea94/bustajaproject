package com.example.bustaja;

public class FavorItem {
    private String BusNum;
    private String BusstopFF;
    private String FavorRouteId;

    public FavorItem(String busNum, String busstopFF, String favorRouteId) {
        BusNum = busNum;
        BusstopFF = busstopFF;
        FavorRouteId = favorRouteId;
    }

    public FavorItem(){
    }

    public String getFavorRouteId() {
        return FavorRouteId;
    }

    public void setFavorRouteId(String favorRouteId) {
        FavorRouteId = favorRouteId;
    }

    public String getBusNum() {
        return BusNum;
    }

    public void setBusNum(String BusNum) {
        this.BusNum = BusNum;
    }

    public String getBusstopFF() {
        return BusstopFF;
    }

    public void setBusstopFF(String BusstopFF) {
        this.BusstopFF = BusstopFF;
    }
}
