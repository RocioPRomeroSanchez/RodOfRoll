package com.example.rodofroll.Vistas.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Objetos.Combatiente;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.rodofroll.Objetos.ConversorImagenes.convertirStringBitmap;


public class Adapter extends RecyclerView.Adapter implements  View.OnClickListener{

    List<Combatiente> combatientes;
    HolderCombatientes holder;
    View.OnClickListener listener,listenerimagen;


    public Adapter(List<Combatiente>combatientes) {
        this.combatientes = combatientes;
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
        Combatiente p=combatientes.get(position);
        ((HolderCombatientes)holder).bind(p);
    }

    @Override
    public int getItemCount() {
        return combatientes.size() ;
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
        // MiInterfaz listenerinterfaz;;


        public HolderCombatientes(View itemView){
            super(itemView);
            txtNombre=itemView.findViewById(R.id.textView);
            imagen=itemView.findViewById(R.id.CardviewimageView);

        }

        public void bind(Combatiente p) {
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