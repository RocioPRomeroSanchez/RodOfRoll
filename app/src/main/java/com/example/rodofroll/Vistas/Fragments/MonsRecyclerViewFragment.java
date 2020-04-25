package com.example.rodofroll.Vistas.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Adapters.Adapter;
import com.example.rodofroll.Vistas.Dialogos.Dialogos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonsRecyclerViewFragment extends Fragment{
    Adapter adapter= null;
    List<Personaje> personajes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.recyclerFloatingButton);

        personajes=new ArrayList<>();
        adapter = new Adapter(personajes);


        final DatabaseReference personajesdb = FirebaseUtilsV1.GET_RefPersonajes();


        personajesdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                personajes.removeAll(personajes);
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    HashMap<String,Object> principal= (HashMap<String, Object>) snapshot.getValue();

                    Personaje p = new Personaje(principal.get("atributos"),principal.get("biografia"),principal.get("inventario"), snapshot.getKey());

                    HashMap<String,Object> combates = (HashMap<String, Object>)principal.get("combates");
                    List<Personaje.CombatesAsociados> combatesAsociados= new ArrayList<>();
                    if(combates!=null){
                        for (Map.Entry<String,Object> s :combates.entrySet()) {
                            String combateid = (String) ((HashMap<String,Object>)s.getValue()).get("combateid");
                            String masterid = (String) ((HashMap<String,Object>)s.getValue()).get("masterid");
                            combatesAsociados.add(new Personaje.CombatesAsociados(masterid,combateid));
                        }
                        p.setCombates(combatesAsociados);
                    }

                    personajes.add(p);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter.setOnClickCortoListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion=recyclerView.getChildAdapterPosition(v);

                FichaPersonajeFragment fichaPersonajeFragment= new FichaPersonajeFragment(personajes.get(posicion));
                ((MainActivity)getActivity()).RemplazarFragment(fichaPersonajeFragment,true);

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ((MainActivity)getActivity()).RemplazarFragment(new FichaPersonajeFragment(),true);
                Dialogos.showDialogoNuevoCombatiente((MainActivity) getActivity(),getContext());

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT


        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {


                return false;
            }



            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Personaje p = personajes.get(viewHolder.getAdapterPosition());

                Function<String,Void> function = new Function<String, Void>() {
                    @Override
                    public Void apply(String input) {
                        personajesdb.child(input).removeValue();
                        return null;
                    }
                };


                Dialogos.showEliminar(p.getNombre(), getActivity(),p.getKey(),function).show();
                adapter.notifyDataSetChanged();

            }
        } ).attachToRecyclerView(recyclerView);



        return view;
    }

}
