package com.example.photosaveproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photosaveproject.Holder.ResimDetails;

public class DetailsActivity extends AppCompatActivity {

    ImageView detail_resim;
    TextView detail_konum, detail_zaman, detail_aciklama;

    String str_detailKonum, str_detailZaman, str_detailAciklama;
    Bitmap detailResim;



    public void init(){
        detail_resim = findViewById(R.id.activityDetails_Resim);
        detail_konum = findViewById(R.id.activityDetails_Konum);
        detail_zaman = findViewById(R.id.activityDetails_Zaman);
        detail_aciklama = findViewById(R.id.activityDetails_Aciklama);

        str_detailKonum = MainActivity.resimDetails.getKonum();
        str_detailZaman = MainActivity.resimDetails.getZaman();
        str_detailAciklama = MainActivity.resimDetails.getAciklama();
        detailResim = MainActivity.resimDetails.getResim();





    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().hide();
        init();

        detail_resim.setImageBitmap(detailResim);
        detail_konum.setText(str_detailKonum);
        detail_zaman.setText(str_detailZaman);
        detail_aciklama.setText(str_detailAciklama);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
        super.onBackPressed();
    }
}