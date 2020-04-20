package com.example.rodofroll.Vistas.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Objetos.ConversorImagenes;
import com.example.rodofroll.R;

import java.util.ArrayList;

public class BuscarAdapter extends RecyclerView.Adapter<BuscarAdapter.BuscarHolder> implements View.OnClickListener {

    ArrayList<String> NombreList;
    ArrayList<String> FotoList;
    View.OnClickListener listener;

    @Override
    public void onClick(View v) {

        if(listener!=null)listener.onClick(v);

    }

    public void setOnClickCortoListener(View.OnClickListener listener)
    {
        this.listener=listener;

    }


    public BuscarAdapter(ArrayList<String> nombreList, ArrayList<String> fotoList) {

        this.NombreList= nombreList;
        this.FotoList=fotoList;

    }

    @NonNull
    @Override
    public BuscarAdapter.BuscarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.masterscardview,parent,false);

        v.setOnClickListener(this);
        return new BuscarHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BuscarHolder holder, int position) {

       holder.nombre.setText(NombreList.get(position));
       holder.imagen.setImageBitmap(ConversorImagenes.convertirStringBitmap(FotoList.get(position)));


    }

    @Override
    public int getItemCount() {
        return NombreList.size();
    }

    class BuscarHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView nombre,email;


        public BuscarHolder(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.FotoimageView);
            nombre = itemView.findViewById(R.id.ApodotextView);

        }
    }
}
