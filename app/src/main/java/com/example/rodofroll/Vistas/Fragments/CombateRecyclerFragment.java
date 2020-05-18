package com.example.rodofroll.Vistas.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Vistas.Adapters.CombatesAdapter;
import com.example.rodofroll.Vistas.Dialogos.DialogoCambiarDatos;
import com.example.rodofroll.Vistas.Dialogos.Dialogos;
import com.example.rodofroll.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CombateRecyclerFragment extends Fragment {

    ValueEventListener listenercombat;
    DatabaseReference db;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.recycler, container, false);

        FloatingActionButton b = view.findViewById(R.id.recyclerFloatingButton);
        final RecyclerView recyclerView =view.findViewById(R.id.recycler);
        final CombatesAdapter adapter;
        final List<Combate> combateList=new ArrayList<>();
        adapter = new CombatesAdapter(combateList);
        db = FirebaseUtilsV1.GET_RefCombates(FirebaseUtilsV1.getKey());

        db.addValueEventListener(listenercombat =new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                combateList.removeAll(combateList);
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    HashMap<String,Object> hashMap= (HashMap<String, Object>) snapshot.getValue();
                    Combate combate = new Combate(snapshot.getKey(),(String) hashMap.get("nombre"));
                    combateList.add(combate);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        adapter.setOnClickCortoListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion=recyclerView.getChildAdapterPosition(v);

                ((MainActivity)getActivity()).RemplazarFragment(new TurnoFragment(combateList.get(posicion),(MainActivity)getActivity()),true);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Dialogos.showDialogNuevoCombate((MainActivity) getActivity());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT


        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {


                return false;
            }



            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                 Combate c = combateList.get(viewHolder.getAdapterPosition());

                Function<String,Void> function = new Function<String, Void>() {
                    @Override
                    public Void apply(String input) {
                        db.child(input).removeValue();
                        return null;
                    }
                };


                Dialogos.showEliminar(c.getNombre(), getActivity(),c.getKey(),function).show();
                adapter.notifyDataSetChanged();

            }
        } ).attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(listenercombat!=null)
        db.removeEventListener(listenercombat);
    }
}
