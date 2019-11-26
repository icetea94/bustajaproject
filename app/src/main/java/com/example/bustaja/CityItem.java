package com.example.bustaja;

public class CityItem {
    private String Citybusnum;
    private String Citybusstop;
    private String CityRouteId;

    public CityItem() {
    }

    public CityItem(String Citybusnum, String Citybusstop, String CityRouteId) {
        this.Citybusnum = Citybusnum;
        this.Citybusstop = Citybusstop;
        this.CityRouteId = CityRouteId;
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

    public String getCityRouteId() {
        return CityRouteId;
    }

    public void setCityRouteId(String cityRouteId) {
        CityRouteId = cityRouteId;
    }
}