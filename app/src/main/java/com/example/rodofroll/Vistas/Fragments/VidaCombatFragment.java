package com.example.rodofroll.Vistas.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.animation.Animator;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Objetos.Combatiente;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Dialogos.DialogoCambiarDatos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class VidaCombatFragment extends Fragment  {

    Combatiente.CombatesAsociados combate;
    Personaje combatiente;
    DatabaseReference referenceinfo;
    ValueEventListener listener;
    TextView VidaActualTextView;
    TextView ArmaduraActualTextVeiw;
    TextView VidaTextView;
    TextView ArmaduraTextView;
    String nombrecombate;

    int vida;
    int armadura;

    public VidaCombatFragment(Combatiente.CombatesAsociados combate, Combatiente combatiente1) {
        this.combate = combate;
        this.combatiente=(Personaje) combatiente1;
        this.nombrecombate=nombrecombate;



    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vidacombate_layout, container, false);
        ImageView VidaimageView = view.findViewById(R.id.vidaimageview);
        ImageView CAimageView = view.findViewById(R.id.armaduraimageview);
        VidaActualTextView = view.findViewById(R.id.VidaActualTextView);
        ArmaduraActualTextVeiw = view.findViewById(R.id.ArmaduraActualTextView);



        ArmaduraTextView = view.findViewById(R.id.ArmaduraTextView);
        VidaTextView = view.findViewById(R.id.VidaTextView);
        ArmaduraTextView.setText(String.format("%.0f",combatiente.getArmadura()));
        VidaTextView.setText(String.format("%.0f",combatiente.getVida()));

        referenceinfo=  FirebaseUtilsV1.GET_RefPersonaje(FirebaseUtilsV1.getKey(),combatiente.getKey()).child("combates").child(combate.getId());


        Animation anim = new ScaleAnimation(
                1f, 1.03f, // Start and end values for the X axis scaling
                1f,1.03f // Start and end values for the Y axis scaling
        );
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(2000);
        anim.setRepeatCount(Animation.INFINITE);
        VidaimageView.startAnimation(anim);



       referenceinfo.addValueEventListener(listener =new ValueEventListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("vida").getValue()!=null&&dataSnapshot.child("armadura").getValue()!=null){

                    vida = Integer.parseInt(String.valueOf(dataSnapshot.child("vida").getValue()));
                    armadura = Integer.parseInt(String.valueOf(dataSnapshot.child("armadura").getValue()));
                    VidaActualTextView.setText(String.valueOf(vida));
                    ArmaduraActualTextVeiw.setText(String.valueOf(armadura));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        VidaimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoCambiarDatos.newInstance(VidaActualTextView,Integer.parseInt(VidaTextView.getText().toString())+1,CrearFuncionVida(),getActivity(),true).show(getFragmentManager(),"Vida");
            }
        });

        CAimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoCambiarDatos.newInstance(ArmaduraActualTextVeiw,Integer.parseInt(ArmaduraTextView.getText().toString())+1,CrearFuncionCA(),getActivity(),true).show(getFragmentManager(),"Armadura");
            }
        });



        return  view;
    }

    public Function CrearFuncionVida(){


        Function f= new Function() {
            @Override
            public Object apply(Object input) {

                FirebaseUtilsV1.GET_RefPersonaje(FirebaseUtilsV1.getKey(),combatiente.getKey()).child("combates").child(combate.getId()).child("vida").setValue(input);


                return null;
            }
        };
        return f;
    }

    public Function CrearFuncionCA(){


        Function f= new Function() {
            @Override
            public Object apply(Object input) {

                FirebaseUtilsV1.GET_RefPersonaje(FirebaseUtilsV1.getKey(),combatiente.getKey()).child("combates").child(combate.getId()).child("armadura").setValue(input);


                return null;
            }
        };
        return f;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        referenceinfo.removeEventListener(listener);
    }
}
