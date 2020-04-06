package com.example.rodofroll.Vistas.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.example.rodofroll.Objetos.ComunicateToTabsListener;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BiografiaFragment extends Fragment implements ComunicateToTabsListener {

    View v;
    Personaje p;


    public BiografiaFragment(Personaje p){
        this.p=p;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.biografia_layout, container, false);



        return v;
    }

    @Override
    public void onClickParentTab() {
        Toast.makeText(getContext(),"Hola2",Toast.LENGTH_LONG).show();

    }
}
