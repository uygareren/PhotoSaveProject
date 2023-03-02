package com.example.photosaveproject.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.DropBoxManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photosaveproject.MainActivity;
import com.example.photosaveproject.R;
import com.example.photosaveproject.Holder.Resim;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ResimAdapter extends RecyclerView.Adapter<ResimAdapter.ResimHolder> {

    Context context;
    ArrayList<Resim> resimArrayList;
    OnItemClickListener listener;
    SQLiteDatabase sqLiteDatabase;


    public ResimAdapter(Context context, ArrayList<Resim> resimArrayList) {
        this.context = context;
        this.resimArrayList = resimArrayList;
    }

    @NonNull
    @Override
    public ResimHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_img_item, parent, false);
        return new ResimHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResimHolder holder, int position) {

        Resim resim = resimArrayList.get(position);
        holder.setData(resim);


    }

    @Override
    public int getItemCount() {
        return resimArrayList.size();
    }

    class ResimHolder extends RecyclerView.ViewHolder{

        ImageView deleteImg, resimImg;
        TextView konumTxt, zamanTxt, aciklamaTxt;


        public ResimHolder(@NonNull View itemView) {
            super(itemView);

            deleteImg = itemView.findViewById(R.id.home_img_item_DeleteImg);
            resimImg = itemView.findViewById(R.id.home_img_item_ImageView);
            konumTxt = itemView.findViewById(R.id.home_img_item_Konum);
            zamanTxt = itemView.findViewById(R.id.home_img_item_Zaman);
            aciklamaTxt = itemView.findViewById(R.id.home_img_item_Aciklama);

            resimImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(resimArrayList.get(position));
                    }
                }
            });

            deleteImg.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    try {
                        deleteImg.setImageResource(R.drawable.ic_check);

                        int position = getAdapterPosition();


                        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Fotoğraflar Database",MODE_PRIVATE,null);

                        String sqlSorgu = "DELETE FROM Fotograflarım WHERE konum = ? AND zaman = ? AND aciklama = ? AND resim = ? ";

                        SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sqlSorgu);




                        sqLiteStatement.bindString(1,resimArrayList.get(position).getKonum());
                        sqLiteStatement.bindString(2,resimArrayList.get(position).getZaman());
                        sqLiteStatement.bindString(3,resimArrayList.get(position).getAciklama());

                        Bitmap gelenResim = resimArrayList.get(position).getResim();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        gelenResim.compress(Bitmap.CompressFormat.PNG, 70, stream);
                        byte[] gelenResimArray = stream.toByteArray();

                        sqLiteStatement.bindBlob(4,gelenResimArray);
                        sqLiteStatement.execute();

                        resimArrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, resimArrayList.size());



                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });



        }

        public void setData(Resim resim){
            this.konumTxt.setText(resim.getKonum());
            this.zamanTxt.setText(resim.getZaman());
            this.aciklamaTxt.setText(resim.getAciklama());
            this.resimImg.setImageBitmap(resim.getResim());
        }

    }

    public interface OnItemClickListener{
        void onItemClick(Resim resim);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
