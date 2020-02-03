package com.example.rodofroll.Vistas;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Objetos.MisMetodos;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;

import static com.example.rodofroll.Objetos.MisMetodos.convertirStringBitmap;

public class HolderCombatientes extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView txtNombre;
    ImageView imagen;
    View.OnClickListener listener;
   // MiInterfaz listenerinterfaz;;


    public HolderCombatientes(View itemView){
        super(itemView);
        txtNombre=itemView.findViewById(R.id.textView);
        imagen=itemView.findViewById(R.id.CardviewimageView);

    }

    public void bind(Personaje p) {
        txtNombre.setText(p.getNombre());
        Bitmap b = convertirStringBitmap(p.getImagen());
        imagen.setImageBitmap(b);

    }

    @Override
    public void onClick(View v) {
        if(listener!= null) listener.onClick(v);
    }
}
