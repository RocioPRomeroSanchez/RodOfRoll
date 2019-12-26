package com.example.rodofroll;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class Holder extends RecyclerView.ViewHolder {

    TextView txtNombre;
    ImageView imagen;

    public Holder(View itemView){
        super(itemView);
        txtNombre=itemView.findViewById(R.id.textView);
        imagen=itemView.findViewById(R.id.imageView);
    }
    public void bind(Personaje p){
        txtNombre.setText(p.getNombre());
        imagen.setImageBitmap(convertirStringBitmap(p.getImagen()));
    }



    static public Bitmap convertirStringBitmap(String imagen) {
        byte[] decodedString = Base64.decode(imagen, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
