package com.example.rodofroll.Vistas.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.Vistas.Adapters.BuscarAdapter;
import com.example.rodofroll.Vistas.Dialogos.DialogoBuscarCombate;
import com.example.rodofroll.Objetos.Usuario;
import com.example.rodofroll.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BuscarCombateFragment  extends Fragment {

    EditText MasterEditTesxt;
    RecyclerView MasterrecyclerView;
    ArrayList<String> NombreList;
    ArrayList<String> EmailList;
    ArrayList<String> FotoList;
    BuscarAdapter searchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.busqusuarios_layout,container,false);
        MasterEditTesxt = view.findViewById(R.id.Mastereditext);
        MasterrecyclerView = view.findViewById(R.id.Masterrecycler);

        NombreList = new ArrayList<>();
        EmailList = new ArrayList<>();
        FotoList = new ArrayList<>();


        MasterEditTesxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!s.toString().isEmpty()){

                    setAdapter(s.toString());

                }
                else{
                    NombreList.clear();
                    EmailList.clear();
                    FotoList.clear();
                    MasterrecyclerView.removeAllViews();
                }

            }
        });



        return view;
    }
    private void setAdapter(final String campo){


       // DatabaseReference db  = FirebaseDatabase.getInstance().getReference();

        //No funciona porque patata
        DatabaseReference db =FirebaseDatabase.getInstance().getReference().child("publico");



        Query query = db;


        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NombreList.clear();
                EmailList.clear();
                FotoList.clear();
                MasterrecyclerView.removeAllViews();

                int contador = 0;


                final ArrayList<Usuario> usuarios= new ArrayList<>();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String id = snapshot.getKey();
                    String nombre = snapshot.child("nombre").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String foto = snapshot.child("foto").getValue(String.class);

                    if(email.equals(FireBaseUtils.getUser().getEmail())){

                    }


                    else if (nombre.toLowerCase().contains(campo.toLowerCase())) {
                        NombreList.add(nombre);
                        EmailList.add(email);
                        FotoList.add(foto);

                        usuarios.add(new Usuario(nombre,email,foto,id));
                        contador++;
                    } else if (email.toLowerCase().contains(campo.toLowerCase())) {
                        NombreList.add(nombre);
                        EmailList.add(email);
                        FotoList.add(foto);
                        usuarios.add(new Usuario(nombre,email,foto,id));
                        contador++;
                    }

                    /*
                     * Get maximum of 15 searched results only
                     * */
                    if (contador == 15)
                        break;

                }
                searchAdapter = new BuscarAdapter(NombreList, EmailList,FotoList);
                searchAdapter.setOnClickCortoListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = MasterrecyclerView.getChildAdapterPosition(v);
                        Usuario usuario  = usuarios.get(position);

                      //  FireBaseUtils.getRef().child("asfd").
                        DialogoBuscarCombate.newInstance(getActivity(),usuario).show(getFragmentManager(),"BuscarCombate");
                    }
                });

                MasterrecyclerView.setAdapter(searchAdapter);
                MasterrecyclerView.setHasFixedSize(true);
                MasterrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
