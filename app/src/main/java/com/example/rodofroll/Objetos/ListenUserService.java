package com.example.rodofroll.Objetos;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class ListenUserService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Al iniciar el servicio se crea un escuchado que apunta al token del usuario en base de datos
       FirebaseUtilsV1.GET_ReferenciaToken().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    //Si el token que hay en base de datos no es el mismo que el de este dispositivo y este token en base de datos no esta vacio se entra a la condicion
                    if ((!((String) dataSnapshot.getValue()).equals(FirebaseUtilsV1.getToken())) && !((String) dataSnapshot.getValue()).isEmpty()) {

                        //Se muestra un toast de que se ha iniciado en otro dispositvo
                        Toast.makeText(getApplicationContext(), "Has iniciado sesion en otro dispositivo", Toast.LENGTH_LONG).show();

                        //Se pausa 3 segundos para poder mostrar el toast
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //Se sale de la aplicacion

                        FirebaseAuth.getInstance().signOut();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                        FirebaseUtilsV1.Borrar();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        Toast.makeText(getApplicationContext(), "Has iniciado sesion en otro dispositivo", Toast.LENGTH_LONG).show();
                        startActivity(intent);

                    }

                }catch (NullPointerException ex){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return  START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();


    }




}
