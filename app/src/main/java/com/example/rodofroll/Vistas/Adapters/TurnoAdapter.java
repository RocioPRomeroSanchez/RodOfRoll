package com.example.rodofroll.Vistas.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Objetos.ImagenAdapterClick;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import static com.example.rodofroll.Objetos.ConversorImagenes.convertirStringBitmap;

public class TurnoAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    List<Combate.PersonEnCombate> personajeEnCombateoList=new ArrayList<>();
    HolderPersonajesCombate holder;
    Combate combate;
    Context context;
    ImagenAdapterClick listenerimagen;
    View.OnClickListener listenercorto;

    public TurnoAdapter(Context context,List<Combate.PersonEnCombate> personajeEnCombateoList,Combate combate) {

        this.context=context;
        this.personajeEnCombateoList=personajeEnCombateoList;
        this.combate=combate;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewturno,parent,false);
        holder = new TurnoAdapter.HolderPersonajesCombate(v);
        holder.setClickBtnImagen(new ImagenAdapterClick() {
            @Override
            public void onImagenClick(Combate.PersonEnCombate personEnCombate) {
                listenerimagen.onImagenClick(personEnCombate);
            }
        });

       v.setOnClickListener(this);


     
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int numero = Math.abs(position-(personajeEnCombateoList.size()-1));

       Combate.PersonEnCombate p=personajeEnCombateoList.get(numero);
        ((HolderPersonajesCombate)holder).bind(p,context);
    }



    @Override
    public int getItemCount() {
        return personajeEnCombateoList.size();
    }

    public void setClickBtnImagen(ImagenAdapterClick listener) {
        if(listener!=null){
            listenerimagen=listener;
        }
    }
    @Override
    public void onClick(View v) {
        if(listenercorto!=null)listenercorto.onClick(v);
    }

    public void setOnClickCortoListener(View.OnClickListener listener)
    {
        this.listenercorto=listener;
    }

    class HolderPersonajesCombate extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtNombre;
        TextView txtIniciativa;
        ImageView imagenavisar;
        LinearLayout linearLayout;
        ImagenAdapterClick imagenAdapterClick;
        Personaje per=new Personaje();
        Combate.PersonEnCombate personEnCombate;



        public HolderPersonajesCombate(@NonNull View itemView) {
            super(itemView);
            txtNombre=itemView.findViewById(R.id.textView);
            linearLayout=itemView.findViewById(R.id.linearlayout);
            imagenavisar=itemView.findViewById(R.id.AvisarImagen);
            imagenavisar.setOnClickListener(this);
            txtIniciativa=itemView.findViewById(R.id.IniciativatextView);


        }
        public void bind(final Combate.PersonEnCombate p, Context context) {

            if(p.getTurno()){
                linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                imagenavisar.setVisibility(View.VISIBLE);
            }
            else{
                linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                imagenavisar.setVisibility(View.INVISIBLE);
            }
            personEnCombate=p;
            FireBaseUtils.GetPersonajeRef(p.getUsuariokey(),p.getPersonajekey()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String,Object> principal= (HashMap<String, Object>) snapshot.getValue();

                  per= new Personaje(principal.get("atributos"),principal.get("biografia"),principal.get("inventario"), snapshot.getKey());
                  txtNombre.setText(per.getNombre());
                  txtIniciativa.setText(p.getIniciativa().toString());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        @Override
        public void onClick(View v) {
            if(imagenAdapterClick!=null) imagenAdapterClick.onImagenClick(personEnCombate);

        }

        public void setClickBtnImagen(ImagenAdapterClick listener) {
            if(listener!=null){
                this.imagenAdapterClick=listener;
            }
        }
    }



}
