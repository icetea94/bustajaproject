package com.example.bustaja;

public class BusstopItem {
    private String Busstopnum;
    private String Busstopname;

    public BusstopItem() {
    }

    public BusstopItem(String Busstopnum, String Busstopname) {
//dsfasfdfadfsfgfhfxdghfdsh
        this.Busstopnum = Busstopnum;
        this.Busstopname = Busstopname;

    }

    public String getBusstopnum() {
        return Busstopnum;
    }

    public void setBusstopnum(String busstopnum) {
        Busstopnum = busstopnum;
    }

    public String getBusstopname() {
        return Busstopname;
    }

    public void setBusstopname(String busstopname) {
        Busstopname = busstopname;
    }

}
