package com.example.rodofroll.Vistas.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.Objetos.Personaje.CombatesAsociados;
import com.example.rodofroll.R;

import java.util.List;

public class CombatesAsociadosAdaper extends RecyclerView.Adapter implements View.OnClickListener {

    List<CombatesAsociados> listacombates;
    View.OnClickListener listenercorto;



    public CombatesAsociadosAdaper(List<Personaje.CombatesAsociados> listacombates ){
        this.listacombates=listacombates;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        v.setOnClickListener(this);
        return new CombatesAsociadosAdaper.CombatAsociadosHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CombatesAsociados combate=listacombates.get(position);
        ((CombatAsociadosHolder)holder).bind(combate);
    }

    @Override
    public int getItemCount() {
        return listacombates.size();
    }


    @Override
    public void onClick(View v) {
        if(listenercorto!= null) listenercorto.onClick(v);
    }
    public void setOnClickCortoListener(View.OnClickListener listener)
    {
        this.listenercorto=listener;

    }

    class CombatAsociadosHolder extends  RecyclerView.ViewHolder {

        TextView nombre;
        View.OnClickListener listenercorto;

        public CombatAsociadosHolder(@NonNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.textView);
        }
        public void bind(CombatesAsociados combatesAsociados){

            nombre.setText(combatesAsociados.getMasterkey());
        }
        public void onClick(View v) {
            if(listenercorto!= null) listenercorto.onClick(v);
        }

    }
}