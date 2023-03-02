package com.example.photosaveproject.Holder;

import android.graphics.Bitmap;

public class ResimDetails {
    String konum, zaman, aciklama;
    Bitmap resim;

    ResimDetails(){

    }

    public ResimDetails(String konum, String zaman, String aciklama, Bitmap resim) {
        this.konum = konum;
        this.zaman = zaman;
        this.aciklama = aciklama;
        this.resim = resim;
    }

    public String getKonum() {
        return konum;
    }

    public String getZaman() {
        return zaman;
    }

    public String getAciklama() {
        return aciklama;
    }

    public Bitmap getResim() {
        return resim;
    }


}
