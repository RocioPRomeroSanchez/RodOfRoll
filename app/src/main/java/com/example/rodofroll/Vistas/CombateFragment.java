package com.example.rodofroll.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rodofroll.R;
import com.example.rodofroll.Firebase.FireBaseUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CombateFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.anyadircombate, container, false);
        FloatingActionButton b = view.findViewById(R.id.floatingActionButton2);



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FireBaseUtils.anyadircombate();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }
}
