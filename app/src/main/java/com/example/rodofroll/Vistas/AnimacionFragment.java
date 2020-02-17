package com.example.rodofroll.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.rodofroll.R;

import java.util.Random;

public class AnimacionFragment extends Fragment {
    LottieAnimationView mLottie;
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cargando_layout, container, false);
        mLottie= view.findViewById(R.id.animacion);

        Aleatorio();
        return view;
    }

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

        mLottie.playAnimation();
    }
}
