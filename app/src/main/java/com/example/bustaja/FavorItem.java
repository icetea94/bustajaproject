package com.example.bustaja;

public class FavorItem {
    private String BusNum;
    private String BusstopFF;

    public FavorItem(){
    }
    public FavorItem(String BusNum, String BusstopFF){
        this.BusNum = BusNum;
        this.BusstopFF = BusstopFF;
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
