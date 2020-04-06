package com.example.rodofroll.Vistas.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Adapters.Adapter;
import com.example.rodofroll.Vistas.Adapters.CombatesAdapter;
import com.example.rodofroll.Vistas.Adapters.CombatesAsociadosAdaper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CombatPersonajeFragment extends Fragment {

    RecyclerView recyclerView;
    TextView nomcombattextview;
    Personaje p;
    CombatesAsociadosAdaper adapter;
    ValueEventListener listener;
    DatabaseReference reference;


    public CombatPersonajeFragment(Personaje p){
        this.p=p;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        View v;
       // FichaPersonajeFragment f = (FichaPersonajeFragment) ((MainActivity) getActivity()).CurrentFragment();
     //   p= f.getPersonaje();
        v= inflater.inflate(R.layout.combatespersonajelayout, container, false);
        nomcombattextview=v.findViewById(R.id.NoCombatestextView);
        recyclerView = v.findViewById(R.id.recycler);
        ComportamientoRecycler();
        EscuchadorDelLosCombates();
        return  v;

    }


    public void ComportamientoRecycler() {
        adapter = new CombatesAsociadosAdaper(p.getCombates());
        adapter.setOnClickCortoListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).RemplazarFragment(new VidaCombatFragment(),true);
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }
    public void EscuchadorDelLosCombates(){

        reference=FireBaseUtils.getRef().child("publico").child(FireBaseUtils.getKey()).child("personajes").child(p.getKey()).child("combates");
       listener= reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               p.getCombates().removeAll(p.getCombates());
                Personaje.CombatesAsociados combateasociado=null;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    HashMap<String,Object> principal= (HashMap<String, Object>) snapshot.getValue();
                    combateasociado = new Personaje.CombatesAsociados((String) principal.get("masterid"),(String) principal.get("combateid"));
                    p.getCombates().add(combateasociado);
                }


                if(p.getCombates().size()==0){
                    nomcombattextview.setVisibility(View.VISIBLE);
                }
                else {
                    nomcombattextview.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(reference!=null)
       reference.removeEventListener(listener);
    }
}
