package com.example.rodofroll.Vistas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Objetos.MisMetodos;
import com.example.rodofroll.R;

import java.util.ArrayList;

class BuscarAdapter extends RecyclerView.Adapter<BuscarAdapter.BuscarHolder> implements View.OnClickListener {

    Context context;
    ArrayList<String> NombreList;
    ArrayList<String> EmailList;
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

    class BuscarHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView nombre,email;


        public BuscarHolder(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.FotoimageView);
            nombre = itemView.findViewById(R.id.ApodotextView);
            email = itemView.findViewById(R.id.EmailtextView);

        }
    }
    public BuscarAdapter(Context context, ArrayList<String> nombreList, ArrayList<String> emailList, ArrayList<String> fotoList) {

        this.context=context;
        this.NombreList= nombreList;
        this.EmailList=emailList;
        this.FotoList=fotoList;

    }

    @NonNull
    @Override
    public BuscarAdapter.BuscarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.masterscardview,parent,false);

        v.setOnClickListener(this);
        return new BuscarHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BuscarHolder holder, int position) {

       holder.nombre.setText(NombreList.get(position));
       holder.email.setText(EmailList.get(position));
       holder.imagen.setImageBitmap(MisMetodos.convertirStringBitmap(FotoList.get(position)));


    }

    @Override
    public int getItemCount() {
        return NombreList.size();
    }
}
