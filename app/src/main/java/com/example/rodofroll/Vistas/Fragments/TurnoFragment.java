package com.example.rodofroll.Vistas.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TurnoFragment extends Fragment {

    Combate combate;

    public TurnoFragment(Combate combate){
        this.combate=combate;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.combatturnos, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);

        Query ref = FireBaseUtils.getRef().child("combates").child(FireBaseUtils.getKey()).child(combate.getKey()).child("ordenturno").orderByChild("iniciativa");
        final List<Combate.PersonEnCombate> personajeEnCombateoList=new ArrayList<>();


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                personajeEnCombateoList.removeAll(personajeEnCombateoList);

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        HashMap<String, Object> valor = (HashMap<String, Object>) snapshot.getValue();

                        Combate.PersonEnCombate persona = new Combate.PersonEnCombate((String) valor.get("personajekey"),(String) valor.get("usuariokey"),  (Long) valor.get("iniciativa"));
                        personajeEnCombateoList.add(persona);

                    }


                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



        return view;



    }
}
