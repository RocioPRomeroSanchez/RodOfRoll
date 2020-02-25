package com.example.rodofroll.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.LoginActivity;
import com.example.rodofroll.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MiCuentaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.micuenta,container,false);


        Button eliminarcuentabutton = view.findViewById(R.id.BorrarCuentabutton);

        eliminarcuentabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FireBaseUtils.getRef().child("publico").child(FireBaseUtils.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        FireBaseUtils.getRef().child("usuarios").child(FireBaseUtils.getUser().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {


                        FireBaseUtils.getUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                startActivity(intent);
                               // getActivity().finish();

                            }
                        });

                    }

                });


            }
        });

    }
});
        return view;
    }
}
