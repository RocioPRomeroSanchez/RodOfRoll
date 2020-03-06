package com.example.rodofroll.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;

public class CombatPersonajeFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {

        View v;

        FichaPersonajeFragment f = (FichaPersonajeFragment) ((MainActivity) getActivity()).CurrentFragment();
       Personaje p= f.getPersonaje();

       if(p.getCombateid()==null){
           v= inflater.inflate(R.layout.nocombate, container, false);
       }
       else {
           v= inflater.inflate(R.layout.atributos_layout, container, false);
       }


        return  v;

    }
}
