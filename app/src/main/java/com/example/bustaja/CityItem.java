package com.example.bustaja;

public class CityItem {
    private String Citybusnum;
    private String Citybusstop;

    public CityItem() {
    }

    public CityItem(String Citybusnum, String Citybusstop) {
        this.Citybusnum = Citybusnum;
        this.Citybusstop = Citybusstop;
    }

    public String getCitybusnum() {
        return Citybusnum;
    }

    public void setCitybusnum(String citybusnum) {
        Citybusnum = citybusnum;
    }

    public String getCitybusstop() {
        return Citybusstop;
    }

    public void setCitybusstop(String citybusstop) {
        Citybusstop = citybusstop;
    }
}