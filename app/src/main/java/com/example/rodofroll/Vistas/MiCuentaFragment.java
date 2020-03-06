package com.example.rodofroll.Vistas;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.LoginActivity;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Dialogos;
import com.example.rodofroll.Objetos.MisMetodos;
import com.example.rodofroll.Objetos.Usuario;
import com.example.rodofroll.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class MiCuentaFragment extends Fragment implements View.OnClickListener {

    Button eliminarcuentabutton;
    Button modificarbutton;
    ImageView userimageView;
    EditText editText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.micuenta,container,false);


        userimageView = view.findViewById(R.id.UserimageView);
        userimageView.setOnClickListener(this);

        editText = view.findViewById(R.id.apodetext);


        eliminarcuentabutton = view.findViewById(R.id.BorrarCuentabutton);
        eliminarcuentabutton.setOnClickListener(this);

        modificarbutton = view.findViewById(R.id.ModifButton);
        modificarbutton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case  R.id.BorrarCuentabutton:

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


                                    }
                                });

                            }

                        });


                    }
                });

               /* Function<String,Void> function = new Function<String, Void>() {
                    @Override
                    public Void apply(String input) {
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


                                            }
                                        });

                                    }

                                });


                            }
                        });
                        return null;
                    }
                };*/

               // Dialogos.showEliminar("tu perfil",getActivity(), function);


                break;

            case R.id.ModifButton:

                String key = FireBaseUtils.getDatosUser().getKey();
                Usuario usuario = new Usuario(editText.getText().toString(),FireBaseUtils.getUser().getEmail(),MisMetodos.convertirImagenString(((BitmapDrawable)userimageView.getDrawable()).getBitmap()));

               /* Map<String,Object> update = new HashMap<String, Object>();
                update.put("email", usuario.getEmail());
                update.put("foto",usuario.getFoto());
                update.put("nombre",usuario.getNombre());

                FireBaseUtils.getRef().child("publico").child(key).updateChildren(update);*/

                break;

            case R.id.UserimageView:
                ((MainActivity)getActivity()).MenuEmergenteImagen(userimageView);

                break;
        }
    }
}
