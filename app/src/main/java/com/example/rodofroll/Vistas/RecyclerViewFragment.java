package com.example.rodofroll.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Dialogos;
import com.example.rodofroll.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class RecyclerViewFragment extends Fragment {

    Adapter adapter= null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerpersonajes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.recyclerFloatingButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // ((MainActivity)getActivity()).RemplazarFragment(new FichaPersonajeFragment(),true);

                Dialogos.showDialogoNuevoUsuario(getActivity(),getContext());

            }
        });

        try {
            adapter = new Adapter(getContext());

        } catch (IOException e) {
            e.printStackTrace();
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        return view;
    }





}
