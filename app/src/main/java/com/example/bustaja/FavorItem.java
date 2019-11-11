package com.example.bustaja;

public class FavorItem {
    private String busNum;
    private String busstopFF;

    public FavorItem(){
    }
    public FavorItem(String busNum, String busstopFF){
        this.busNum = busNum;
        this.busstopFF = busstopFF;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }
    public String busstopFF() {
        return busstopFF;
    }

    public void busstopFF(String busstopFF) {
        this.busstopFF = busstopFF;
    }

    public String getBusstopFF() {
        return busstopFF;
    }

    public void setBusstopFF(String busstopFF) {
        this.busstopFF = busstopFF;
    }
}
