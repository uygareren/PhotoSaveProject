package com.example.photosaveproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.photosaveproject.Adapter.ResimAdapter;
import com.example.photosaveproject.Holder.Resim;
import com.example.photosaveproject.Holder.ResimDetails;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ResimAdapter resimAdapter;
    static ResimDetails resimDetails;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addPhoto){
            Intent intent = new Intent(getApplicationContext(), AddPhotoActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Fotoğrafımız");


        recyclerView = findViewById(R.id.main_RecyclerView);
        resimAdapter = new ResimAdapter(this, Resim.getData(this));


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(resimAdapter);

        resimAdapter.setOnItemClickListener(new ResimAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Resim resim) {
                resimDetails = new ResimDetails(resim.getKonum(),resim.getZaman(),resim.getAciklama(),resim.getResim());

                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                startActivity(intent);

            }
        });
    }
}