package com.example.rodofroll.Vistas.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Objetos.ElementoAdapterClick;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TurnoAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    List<Combate.PersonEnCombate> personajeEnCombateoList=new ArrayList<>();
    HolderPersonajesCombate holder;
    Combate combate;
    Context context;
    ElementoAdapterClick listenerimagen;
    View.OnClickListener listenercorto;
    ElementoAdapterClick listeneriniciativa;

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
        holder.setClickBtnImagen(new ElementoAdapterClick() {
            @Override
            public void onElementoClick(Combate.PersonEnCombate personEnCombate) {
                listenerimagen.onElementoClick(personEnCombate);
            }
        });
        holder.setClickBtnIniciativa(new ElementoAdapterClick() {
            @Override
            public void onElementoClick(Combate.PersonEnCombate personEnCombate) {
                listeneriniciativa.onElementoClick(personEnCombate);
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

    public void setClickBtnImagen(ElementoAdapterClick listener) {
        if(listener!=null){
            listenerimagen=listener;
        }
    }

    public void setClicBtnIniciativa(ElementoAdapterClick listener){
        if(listener!=null){
            listeneriniciativa=listener;
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
        LinearLayout bordelayout;
        LinearLayout iniciativalayout;
        ElementoAdapterClick imagenAdapterClick;
        ElementoAdapterClick iniciativaAdapterClick;

        Personaje per=new Personaje();
        Combate.PersonEnCombate personEnCombate;



        public HolderPersonajesCombate(@NonNull View itemView) {
            super(itemView);
            txtNombre=itemView.findViewById(R.id.textView);
            bordelayout =itemView.findViewById(R.id.bordelayout);
            imagenavisar=itemView.findViewById(R.id.AvisarImagen);
            iniciativalayout = itemView.findViewById(R.id.IniciativaLayout);
            imagenavisar.setOnClickListener(this);
            iniciativalayout.setOnClickListener(this);

            txtIniciativa=itemView.findViewById(R.id.IniciativatextView);


        }
        public void bind(final Combate.PersonEnCombate p, Context context) {

            if(p.getTurno()){
                bordelayout.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                imagenavisar.setVisibility(View.VISIBLE);
            }
            else{
                bordelayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
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
            switch (v.getId()){
                case R.id.AvisarImagen:
                    if(imagenAdapterClick !=null) imagenAdapterClick.onElementoClick(personEnCombate);
                    break;

                case R.id.IniciativaLayout:
                    if(iniciativaAdapterClick!=null)  iniciativaAdapterClick.onElementoClick(personEnCombate);
                    break;

            }


        }

        public void setClickBtnImagen(ElementoAdapterClick listener) {
            if(listener!=null){
                this.imagenAdapterClick =listener;
            }
        }

        public void setClickBtnIniciativa(ElementoAdapterClick listener){
            if(listener!=null){
                this.iniciativaAdapterClick=listener;
            }
        }
    }



}
