package com.example.rodofroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Random;

public class LogOut extends AppCompatActivity {

    LottieAnimationView mLottie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargando_layout);
        mLottie= findViewById(R.id.animacion);

        Aleatorio();
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
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        finish();
    }
}
