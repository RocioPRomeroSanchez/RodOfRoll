package com.example.rodofroll.Vistas.Adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;

import java.util.List;

import static com.example.rodofroll.Objetos.ConversorImagenes.convertirStringBitmap;


public class Adapter extends RecyclerView.Adapter implements  View.OnClickListener{

    List<Personaje> personajes;
    HolderCombatientes holder;
    View.OnClickListener listener,listenerimagen;


    public Adapter(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
       holder = new HolderCombatientes(v);
       v.setOnClickListener(this);


       return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Personaje p=personajes.get(position);


        ((HolderCombatientes)holder).bind(p);
    }

    @Override
    public int getItemCount() {
        return personajes.size() ;
    }


    public void setOnClickCortoListener(View.OnClickListener listener)
    {
        this.listener=listener;

    }

    @Override
    public void onClick(View v) {
        if(listener!=null)listener.onClick(v);
    }
    public void setOnClickImagenListener(View.OnClickListener listener)
    {
        this.listenerimagen=listener;
    }

    public class HolderCombatientes extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtNombre;
        ImageView imagen;
        View.OnClickListener listener;



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

}
