package com.example.rodofroll.Vistas.Fragments;

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
import com.google.firebase.database.DatabaseReference;

public class VidaCombatFragment extends Fragment  {

    Combatiente.CombatesAsociados combate;
    Combatiente combatiente;
    DatabaseReference reference;
    TextView textView;

    public VidaCombatFragment(Combatiente.CombatesAsociados combate, Combatiente combatiente) {
        this.combate = combate;
        this.combatiente=combatiente;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vidacombate_layout, container, false);
        ImageView imageView = view.findViewById(R.id.vidaimageview);
        textView = view.findViewById(R.id.VidaTextView);


        Animation anim = new ScaleAnimation(
                1f, 1.03f, // Start and end values for the X axis scaling
                1f,1.03f // Start and end values for the Y axis scaling
        );
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(2000);
        anim.setRepeatCount(Animation.INFINITE);
        imageView.startAnimation(anim);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoCambiarDatos.newInstance(textView,200,CrearFuncionVida(),getActivity(),true).show(getFragmentManager(),"Vida");
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




}
