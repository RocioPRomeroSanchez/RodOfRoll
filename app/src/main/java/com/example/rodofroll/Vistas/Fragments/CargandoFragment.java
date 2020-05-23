package com.example.rodofroll.Vistas.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.rodofroll.R;

import java.util.Random;

//Es el Fragment que controla la animacion de incio
public class CargandoFragment extends Fragment {

    //Es el objeto Lottie que sirve para contener la informacion la animacion
    LottieAnimationView mLottie;

    public LottieAnimationView getmLottie() {
        return mLottie;
    }

    @Nullable
    @Override
    //Al crear el fragment se carga la vista del fragment y se inicializa el objeto lottie
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cargando_layout,container,false);
        mLottie= view.findViewById(R.id.animacion);

       //Se llama al metodo aleatorio
        Aleatorio();
        return view;
    }

    //Se procede a decidir aleatoriamente entre las tres animaciones aleatorias que hay
    private void Aleatorio() {

        Random aleatorio = new Random();
        int n = aleatorio.nextInt(3);

        switch (n) {
            case 0:
                mLottie.setAnimation("brujo1.json");

                break;
            case 1:
                mLottie.setAnimation("brujo2.json");
                break;
            case 2:
                mLottie.setAnimation("brujo3.json");
                break;
        }

        //Se ejecuta la animacion
        PlayAnimacion();
    }
    public void PlayAnimacion(){
        mLottie.playAnimation();
    }
    public void StopAnimacion(){
        mLottie.cancelAnimation();
    }

    //Al destruir el fragment se para la animacion para prevenir errores
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        StopAnimacion();
    }
}
