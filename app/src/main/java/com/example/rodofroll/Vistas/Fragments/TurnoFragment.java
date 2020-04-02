package com.example.rodofroll.Vistas.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Objetos.ConversorImagenes;
import com.example.rodofroll.Objetos.ImagenAdapterClick;
import com.example.rodofroll.Objetos.InicializarVistas;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Adapters.TurnoAdapter;
import com.example.rodofroll.Vistas.Dialogos.DialogoCambiarDatos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class TurnoFragment extends Fragment implements View.OnClickListener , InicializarVistas {

    Combate combate;
    ImageButton turnobutton;
    List<Combate.PersonEnCombate> personajeEnCombateoList=new ArrayList<Combate.PersonEnCombate>();
    TextView ronda;
    RecyclerView recyclerView;
    DatabaseReference refronda;
    ImageView personajeImageView;
    TextView nombreTextView;
    TextView vidaTextView;
    TextView caTextView;
    TextView iniciativaTextView;
    TextView ataqueBaseTextView;
    TextView velociadTextView;




    public TurnoFragment(Combate combate){
        this.combate=combate;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.combatturnos, container, false);

        InicializarComponentes(view);
        Query ref = FireBaseUtils.getRef().child("combates").child(FireBaseUtils.getKey()).child(combate.getKey()).child("ordenturno").orderByChild("iniciativa");
        refronda = FireBaseUtils.getRef().child("combates").child(FireBaseUtils.getKey()).child(combate.getKey()).child("ronda");
        refronda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                combate.setRonda((int)(long)dataSnapshot.getValue());
                ronda.setText(String.valueOf(combate.getRonda()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ComportamientoRecycler();

        return view;



    }

    public void PasarTurno(List<Combate.PersonEnCombate> personajeEnCombateoList,Combate combate){

            boolean todosfalso= true;
            Combate.PersonEnCombate pos=null;
            int posi=0;
            for(int i =0;i<personajeEnCombateoList.size();i++){
                if(personajeEnCombateoList.get(i).getTurno()){
                    todosfalso=false;
                    pos=personajeEnCombateoList.get(i);
                    posi = i;
                }
            }
            if(todosfalso){
                personajeEnCombateoList.get(personajeEnCombateoList.size()-1).setTurno(true, combate);
                FireBaseUtils.getRef().child("combates").child(FireBaseUtils.getKey()).child(combate.getKey()).child("ronda").setValue(1);


            }
            else{

                personajeEnCombateoList.get(posi).setTurno(false, combate);
                if(posi==0){
                    personajeEnCombateoList.get(personajeEnCombateoList.size()-1).setTurno(true, combate);
                    int ronda = combate.getRonda();
                    ronda++;
                    FireBaseUtils.getRef().child("combates").child(FireBaseUtils.getKey()).child(combate.getKey()).child("ronda").setValue(ronda);

                   // ronda++;
                }
                else{

                    personajeEnCombateoList.get(posi-1 ).setTurno(true, combate);
                }

               // pos.get(0).setTurno(true,combate);

            }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.turnobutton:
                if(personajeEnCombateoList.size()!=0){
                    PasarTurno(personajeEnCombateoList,combate);
                }

                break;

            case R.id.rondatextView:

                DialogoCambiarDatos.newInstance((TextView) v,100,null,  FuncionRonda(),getActivity()).show(getFragmentManager(),"Ronda");
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        //Eliminar Escuchadores
    }

    public Function FuncionRonda(){

        Function f = new Function() {
            @Override
            public Object apply(Object input) {
                FireBaseUtils.getRef().child("combates").child(FireBaseUtils.getKey()).child(combate.getKey()).child("ronda").setValue(input);
                return null;
            }
        };
        return f;

    }

    @Override
    public void InicializarComponentes(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        ronda  = view.findViewById(R.id.rondatextView);

        personajeImageView = view.findViewById(R.id.PersonajeimageView);
        nombreTextView = view.findViewById(R.id.NombreText);
        vidaTextView = view.findViewById(R.id.VidaTextView);
        caTextView = view.findViewById(R.id.CATextView);
        iniciativaTextView = view.findViewById(R.id.IniciativaTextView);
        ataqueBaseTextView = view.findViewById(R.id.AtaqueBaseTextView);
        velociadTextView = view.findViewById(R.id.VelocidadTextView);

        ronda.setOnClickListener(this);
        turnobutton=view.findViewById(R.id.turnobutton);
        turnobutton.setOnClickListener(this);

        personajeEnCombateoList= new ArrayList<>();

    }

    public void SeleccionPersonaje(Combate.PersonEnCombate personEnCombate){
        FireBaseUtils.getRef().child("publico").child(personEnCombate.getUsuariokey()).child("personajes").child(personEnCombate.getPersonajekey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap principal = (HashMap) dataSnapshot.getValue();
                Personaje p = new Personaje(principal.get("atributos"),principal.get("biografia"),principal.get("inventario"), dataSnapshot.getKey());
                nombreTextView.setText(p.getNombre());
                personajeImageView.setImageBitmap(ConversorImagenes.convertirStringBitmap(p.getImagen()));
                vidaTextView.setText(String.valueOf(p.getVida()));
                caTextView.setText(String.valueOf(p.getArmadura()));
                iniciativaTextView.setText(String.valueOf(p.getIniciativa()));
                ataqueBaseTextView.setText(String.valueOf((p.getAtaque())));
                velociadTextView.setText(String.valueOf(p.getVelocidad()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void ComportamientoRecycler(){
        Query ref = FireBaseUtils.getRef().child("combates").child(FireBaseUtils.getKey()).child(combate.getKey()).child("ordenturno").orderByChild("iniciativa");
        personajeEnCombateoList=new ArrayList<>();
        final TurnoAdapter adapter = new TurnoAdapter(getContext(),personajeEnCombateoList,combate);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                personajeEnCombateoList.removeAll(personajeEnCombateoList);

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    HashMap<String, Object> valor = (HashMap<String, Object>) snapshot.getValue();
                    Combate.PersonEnCombate persona = new Combate.PersonEnCombate(snapshot.getKey(),(String) valor.get("personajekey"),(String) valor.get("usuariokey"),  (Long) valor.get("iniciativa"), (Boolean) valor.get("turno"),(Boolean) valor.get("avisar"));
                    personajeEnCombateoList.add(persona);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        adapter.setClickBtnImagen(new ImagenAdapterClick() {
            @Override
            public void onImagenClick(Combate.PersonEnCombate personEnCombate) {
                Toast.makeText(getContext(),"Avisando..."+personEnCombate.getIniciativa(), Toast.LENGTH_SHORT).show();
                personEnCombate.Avisar(combate);
            }
        });
        adapter.setOnClickCortoListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int posicion=recyclerView.getChildAdapterPosition(v);
                Combate.PersonEnCombate p = personajeEnCombateoList.get(personajeEnCombateoList.size()-1-posicion);


                //Metodo que coloque las cosas en su sitio

                SeleccionPersonaje(p);

                Toast.makeText(getContext(),"Seleccionando..."+p.getIniciativa(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }
}
