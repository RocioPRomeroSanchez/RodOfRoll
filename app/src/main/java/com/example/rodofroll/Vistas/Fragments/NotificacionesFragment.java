package com.example.rodofroll.Vistas.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Dialogos.Dialogos;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;

public class NotificacionesFragment extends Fragment {

    DatabaseReference ref;
    FloatingActionButton BorrarButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.notificacioneslayout,container,false);
        final ListView lista = view.findViewById(R.id.notiflista);
        BorrarButton = view.findViewById(R.id.BorrarFloating);


        ref = FirebaseUtilsV1.GET_RefNotificaiones();


        lista.setAdapter(new FirebaseListAdapter<String>(getActivity(), String.class,android.R.layout.simple_list_item_1,ref) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView text = (TextView) v.findViewById(android.R.id.text1);
                text.setText(model);


            }
        });
        BorrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.removeValue();
            }
        });




        return view;
    }
}
