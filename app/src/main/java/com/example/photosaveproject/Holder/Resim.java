package com.example.photosaveproject.Holder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class Resim {

    String konum, zaman, aciklama;
    Bitmap resim;

    Resim(){

    }

    public Resim(String konum, String zaman, String aciklama, Bitmap resim) {
        this.konum = konum;
        this.zaman = zaman;
        this.aciklama = aciklama;
        this.resim = resim;


    }

    public String getKonum() {
        return konum;
    }

    public void setKonum(String konum) {
        this.konum = konum;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zaman) {
        this.zaman = zaman;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Bitmap getResim() {
        return resim;
    }

    public void setResim(Bitmap resim) {
        this.resim = resim;
    }


    static public ArrayList<Resim> getData(Context context){


        ArrayList<Resim> ResimList = new ArrayList<>();

        ArrayList<String> KonumList = new ArrayList<>();
        ArrayList<String> ZamanList = new ArrayList<>();
        ArrayList<String> AciklamaList = new ArrayList<>();
        ArrayList<Bitmap> ImageList = new ArrayList<>();


        try {
            SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Fotoğraflar Database", Context.MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Fotograflarım", null);

            int konumIndex = cursor.getColumnIndex("konum");
            int zamanIndex = cursor.getColumnIndex("zaman");
            int aciklamaIndex = cursor.getColumnIndex("aciklama");
            int imgIndex = cursor.getColumnIndex("resim");

            while (cursor.moveToNext()){
                KonumList.add(cursor.getString(konumIndex));
                ZamanList.add(cursor.getString(zamanIndex));
                AciklamaList.add(cursor.getString(aciklamaIndex));
                
                byte[] gelenResimByte = cursor.getBlob(imgIndex);
                Bitmap gelenResim = BitmapFactory.decodeByteArray(gelenResimByte, 0, gelenResimByte.length);
                ImageList.add(gelenResim);



            }

            cursor.close();

            for (int i=0; i<KonumList.size(); i++){
                Resim resim = new Resim();

                resim.setKonum(KonumList.get(i));
                resim.setZaman(ZamanList.get(i));
                resim.setAciklama(AciklamaList.get(i));
                resim.setResim(ImageList.get(i));

                ResimList.add(resim);

            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return ResimList;
    }



}
