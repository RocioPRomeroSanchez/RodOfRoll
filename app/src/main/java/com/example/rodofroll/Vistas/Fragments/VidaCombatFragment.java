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
import androidx.fragment.app.Fragment;

import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Dialogos.DialogoCambiarDatos;

public class VidaCombatFragment extends Fragment  {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vidacombate_layout, container, false);
        ImageView imageView = view.findViewById(R.id.vidaimageview);
        final TextView textView = view.findViewById(R.id.VidaTextView);



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
                DialogoCambiarDatos.newInstance(textView,0,null,getActivity(),true).show(getFragmentManager(),"Vida");
            }
        });

        return  view;
    }


}
