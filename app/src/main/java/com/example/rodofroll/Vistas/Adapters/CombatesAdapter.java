package com.example.rodofroll.Vistas.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;

import java.util.List;

public class CombatesAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    List<Combate> combateList;
    View.OnClickListener listener;

    public CombatesAdapter(List<Combate> combateList) {

        this.combateList=combateList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        v.setOnClickListener(this);
        return new CombatesAdapter .CombateHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Combate combate=combateList.get(position);
        ((CombateHolder)holder).bind(combate);
    }
    public void setOnClickCortoListener(View.OnClickListener listener)
    {
        this.listener=listener;

    }
    @Override
    public int getItemCount() {
        return combateList.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null)listener.onClick(v);
    }

    class CombateHolder extends RecyclerView.ViewHolder{

        TextView nombre;

        public CombateHolder(@NonNull View itemView) {

            super(itemView);
            nombre=itemView.findViewById(R.id.textView);
        }
        public void bind(Combate combate) {
            nombre.setText(combate.getNombre());


        }
    }
}
