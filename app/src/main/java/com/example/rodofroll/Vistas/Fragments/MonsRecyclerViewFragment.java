package com.example.rodofroll.Vistas.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Combatiente;
import com.example.rodofroll.Objetos.Monstruo;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Adapters.Adapter;
import com.example.rodofroll.Vistas.Dialogos.Dialogos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonsRecyclerViewFragment extends Fragment{
    Adapter adapter= null;
    List<Combatiente> monstruos;
    boolean existecombat = false;
    DatabaseReference monstruosdb;
    ValueEventListener monstruoslistener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.recycler, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.recyclerFloatingButton);

        monstruos=new ArrayList<>();
        adapter = new Adapter(monstruos);


        monstruosdb = FirebaseUtilsV1.GET_RefMonstruos();


        monstruosdb.addValueEventListener(monstruoslistener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                monstruos.removeAll(monstruos);
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    HashMap<String,Object> principal= (HashMap<String, Object>) snapshot.getValue();

                    Monstruo monstruo = new Monstruo(principal.get("atributos"),principal.get("biografia"), snapshot.getKey());

                    HashMap<String,Object> combates = (HashMap<String, Object>)principal.get("combates");
                    List<Combatiente.CombateAsociado> combatesAsociados= new ArrayList<>();
                    if(combates!=null){
                        for (Map.Entry<String,Object> s :combates.entrySet()) {
                            String combateid = (String) ((HashMap<String,Object>)s.getValue()).get("combateid");
                            String masterid = (String) ((HashMap<String,Object>)s.getValue()).get("masterid");
                            combatesAsociados.add(new Personaje.CombateAsociado(masterid,combateid,snapshot.getKey()));
                        }
                        monstruo.setCombates(combatesAsociados);
                    }

                   monstruos.add(monstruo);
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

                FichaPersonajeFragment fichaPersonajeFragment= new FichaPersonajeFragment((Monstruo)monstruos.get(posicion));
                ((MainActivity)getActivity()).RemplazarFragment(fichaPersonajeFragment,true);

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialogos.showDialogoNuevoCombatiente((MainActivity) getActivity(),getContext(),new Monstruo());

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
                final Monstruo mons = (Monstruo) monstruos.get(viewHolder.getAdapterPosition());

                existecombat=false;



                FirebaseUtilsV1.GET_RefCombates(FirebaseUtilsV1.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                      for(DataSnapshot d : dataSnapshot.getChildren()){
                          DataSnapshot ordenturno = d.child("ordenturno");
                          for(DataSnapshot d1 : ordenturno.getChildren()){
                              if(d1.child("personajekey").getValue().equals(mons.getKey())){
                                  existecombat=true;
                              }
                          }

                      }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                Function<String,Void> function = new Function<String, Void>() {
                    @Override
                    public Void apply(String input) {

                        if(!existecombat){
                            monstruosdb.child(input).removeValue();
                        }
                        else{
                            Snackbar.make(view,"No puede eliminar este personaje porque esta en algun combate", Snackbar.LENGTH_LONG).show();
                        }

                        return null;
                    }
                };


                Dialogos.showEliminar(mons.getNombre(), getActivity(),mons.getKey(),function).show();
                adapter.notifyDataSetChanged();

            }
        } ).attachToRecyclerView(recyclerView);



        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(monstruoslistener!=null)
        monstruosdb.removeEventListener(monstruoslistener);
    }
}
