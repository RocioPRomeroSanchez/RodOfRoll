package com.example.rodofroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;

public class Adapter extends RecyclerView.Adapter {

    Context context;
    HolderCombatientes holder;
    JsonReader jr=null;
    JsonArray jA = null;


    public Adapter(Context context) throws IOException {
        this.context=context;
        jr = Json.createReader(context.getAssets().open("datos/personajes"));
        jA = jr.readArray();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        holder=new HolderCombatientes(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {


            Personaje p = new Personaje(jA.getJsonObject(position).getString("Nombre"),jA.getJsonObject(position).getString("Imagen"));
            ((HolderCombatientes)viewHolder).bind(p);


    }

    @Override
    public int getItemCount() {
        return jA.size();
    }
}
