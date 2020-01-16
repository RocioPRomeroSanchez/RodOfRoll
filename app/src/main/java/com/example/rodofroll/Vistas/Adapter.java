package com.example.rodofroll.Vistas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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


        JsonObject atributos =  jA.getJsonObject(position).getJsonObject("biografia");
            Personaje p = new Personaje(atributos.getString("nombre"),atributos.getString("imagen"));


            ((HolderCombatientes)viewHolder).bind(p);


    }

    @Override
    public int getItemCount() {
        return jA.size();
    }
}
