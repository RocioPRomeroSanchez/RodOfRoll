package com.example.rodofroll.Vistas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;

import java.util.List;


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
       /* holder.setOnClickImagenListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listenerimagen!=null) listenerimagen.onClick(view);
            }
        });*/

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
}
