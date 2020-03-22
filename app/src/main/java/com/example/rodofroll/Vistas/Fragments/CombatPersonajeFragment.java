package com.example.rodofroll.Vistas.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Adapters.Adapter;

public class CombatPersonajeFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        View v;
        FichaPersonajeFragment f = (FichaPersonajeFragment) ((MainActivity) getActivity()).CurrentFragment();
       Personaje p= f.getPersonaje();

       if(p.getCombates().size()==0){
           v= inflater.inflate(R.layout.nocombate, container, false);
       }
       else {
           v= inflater.inflate(R.layout.combatespersonajelayout, container, false);
           RecyclerView recyclerView = v.findViewById(R.id.recycler);
         // Adapter adapter = new Adapter(p.getCombates());
       }


        return  v;

    }
}
