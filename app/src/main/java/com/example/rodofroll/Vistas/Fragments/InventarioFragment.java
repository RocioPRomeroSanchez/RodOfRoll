package com.example.rodofroll.Vistas.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.ComunicateToTabsListener;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;

public class InventarioFragment extends Fragment  {

    View v;
    RecyclerView recyclerView;
    AdapterInventario adapter;
    Personaje p;

    public InventarioFragment(Personaje p) {
        this.p=p;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.inventario_layout, container, false);
        recyclerView= v.findViewById(R.id.recycler);

        adapter = new AdapterInventario(getContext());


        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));



        return v;
    }


    class HolderInventario extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtNombre;
        ImageView imagen;
        View.OnClickListener listener;

        public HolderInventario(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
    class AdapterInventario extends RecyclerView.Adapter {

        Context context;

        public AdapterInventario(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(context).inflate(R.layout.inventario_cardview,parent,false);
            return new HolderInventario(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 1;
        }
    }

}
