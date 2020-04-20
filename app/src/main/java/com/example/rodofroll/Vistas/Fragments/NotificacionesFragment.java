package com.example.rodofroll.Vistas.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

public class NotificacionesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.notificacioneslayout,container,false);
        final ListView lista = view.findViewById(R.id.notiflista);
     //   DatabaseReference ref = FireBaseUtils.getRef().child("notificaciones").child(FireBaseUtils.getKey());

        DatabaseReference ref = FirebaseUtilsV1.GET_RefNotificaiones();
      /*  ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        lista.setAdapter(new FirebaseListAdapter<String>(getActivity(), String.class,android.R.layout.simple_list_item_1,ref) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView text = (TextView) v.findViewById(android.R.id.text1);
                text.setText(model);

            }
        });




        return view;
    }
}
