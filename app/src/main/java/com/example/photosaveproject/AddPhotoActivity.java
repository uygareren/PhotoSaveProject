package com.example.photosaveproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

public class AddPhotoActivity extends AppCompatActivity {

    ImageView imgKitapSec;
    EditText editTextKonum, editTextZaman, editTextAciklama;
    AppCompatButton ekleBtn;

    String strKonum, strZaman, strAciklama;

    int izinVerilmeKodu = 1;
    Bitmap secilenResim, updatedResim, defaultImage;

    SQLiteDatabase sqLiteDatabase;

    public void init() {
        //İmageView
        imgKitapSec = findViewById(R.id.addPhoto_ImageView);

        //Edittext
        editTextKonum = findViewById(R.id.addPhoto_edittextKonum);
        editTextZaman = findViewById(R.id.addPhoto_edittextZaman);
        editTextAciklama = findViewById(R.id.addPhoto_edittextAciklama);

        ekleBtn = findViewById(R.id.addPhoto_btnEkle);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        init();


        // ActionBar Title Değiştirme.
        getSupportActionBar().setTitle("Fotoğraf Seç");



    }


    // Main Activity'e geri dönüş.
    public void goMainActivityImage(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }




    public void kitapEkleBtnClick(View view) {
        strKonum = editTextKonum.getText().toString().toUpperCase(Locale.ROOT);

        strZaman = editTextZaman.getText().toString();
        strAciklama = editTextAciklama.getText().toString();

        if(strKonum.isEmpty()){
            strKonum = "Konum yok";
        }
        if(strZaman.isEmpty()){
            strZaman = "Zaman yok";
        }
        if(strAciklama.isEmpty()){
            strAciklama = "Açıklama yok";
        }


        // Resimi Byte'a dönüştürüyoruz sqlite'a kaydetmek için.

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        updatedResim = imgBoyutAyarla(secilenResim);
        updatedResim.compress(Bitmap.CompressFormat.PNG, 70,outputStream);
        byte[] kayitEdilcekResim = outputStream.toByteArray();

        try {
            sqLiteDatabase = this.openOrCreateDatabase("Fotoğraflar Database",MODE_PRIVATE,null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Fotograflarım (id INTEGER PRIMARY KEY, konum VARCHAR, zaman VARCHAR, aciklama VARCHAR, resim BLOB)");


            String sqlSorgu = "INSERT INTO Fotograflarım(konum, zaman, aciklama ,resim) VALUES(?,?,?,?)";

            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sqlSorgu);


            sqLiteStatement.bindString(1,strKonum);
            sqLiteStatement.bindString(2,strZaman);
            sqLiteStatement.bindString(3,strAciklama);
            sqLiteStatement.bindBlob(4,kayitEdilcekResim);
            sqLiteStatement.execute();

            showToast("Fotoğraf Eklendi!");
            sıfırla();




        }catch (Exception e){
            e.printStackTrace();
        }



    }


    public void resimSecClick(View view) {

        // sqLiteDatabase = this.openOrCreateDatabase("Fotoğraflar Database",MODE_PRIVATE,null);
        // sqLiteDatabase.execSQL("DELETE FROM Fotograflarım WHERE konum = 'x' OR zaman = 'x'");


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Title"), izinVerilmeKodu);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            Uri uri = data.getData();

            try {
                if(Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source imgSource = ImageDecoder.createSource(this.getContentResolver(),uri);
                    secilenResim = ImageDecoder.decodeBitmap(imgSource);
                    imgKitapSec.setImageBitmap(secilenResim);
                }else{
                    secilenResim = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    imgKitapSec.setImageBitmap(secilenResim);
                }

                ekleBtn.setEnabled(true);


            } catch (IOException e) {
                e.printStackTrace();
            }
            imgKitapSec.setImageBitmap(secilenResim);

        }

    }

    public void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public Bitmap imgBoyutAyarla(Bitmap bitmap){

        return Bitmap.createScaledBitmap(bitmap,125,150,true);
    }

    public void sıfırla(){
        editTextKonum.setText("");
        editTextZaman.setText("");
        editTextAciklama.setText("");
        defaultImage = BitmapFactory.decodeResource(this.getResources(),R.drawable.gorsel_sec);
        imgKitapSec.setImageBitmap(defaultImage);
        ekleBtn.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        startActivity(intent);
        super.onBackPressed();
    }
}