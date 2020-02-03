package com.example.rodofroll.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Objetos.Dialogos;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;

public class RecyclerViewFragment extends Fragment {

    Adapter adapter= null;
    List<Personaje> personajes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerpersonajes, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recycler);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.recyclerFloatingButton);

        personajes=new ArrayList<>();
        adapter = new Adapter(personajes);

        final DatabaseReference personajesdb =   FireBaseUtils.getRef().child(FireBaseUtils.getUser().getUid()).child("personajes");


        personajesdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                personajes.removeAll(personajes);
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    HashMap<String,Object> principal= (HashMap<String, Object>) snapshot.getValue();

                    Personaje p = new Personaje(principal.get("atributos"),principal.get("biografia"),principal.get("inventario"));
                   p.setKey(snapshot.getKey());
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
                Dialogos.showDialogoNuevoUsuario((MainActivity) getActivity(),getContext());

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
                personajesdb.child(p.getKey()).removeValue();
            }
        } ).attachToRecyclerView(recyclerView);



        return view;
    }





}
