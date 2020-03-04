package com.example.rodofroll.Firebase;

import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Snapshot;

import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Objetos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public  class FireBaseUtils {

   static DatabaseReference ref;
   private static FireBaseUtils soyunico;
   private static FirebaseUser user;
   static String key=null;


    static private boolean estado;


    public synchronized static void setEstado(boolean estado) {
        FireBaseUtils.estado = estado;
    }

    public synchronized static boolean isEstado() {
        return estado;
    }


    public static Usuario getDatosUser() {
        return datosUser;
    }


    static Usuario datosUser;


    public static DatabaseReference getRef() {
        return ref;
    }

    public static void setRef(DatabaseReference ref) {
        FireBaseUtils.ref = ref;
    }

    public static FirebaseUser getUser() {
        return user;
    }





    private FireBaseUtils() {

        if(soyunico==null){
            user = FirebaseAuth.getInstance().getCurrentUser();
            ref = FirebaseDatabase.getInstance().getReference();



        }
    }

    static public void CrearRef() {

        if(soyunico==null){
          soyunico= new FireBaseUtils();


        }

    }

    public static void  GetDatosUsuario(){

         FireBaseUtils.getRef().child("usuarios").child(FireBaseUtils.getUser().getUid()).child("id").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key = ( dataSnapshot.getValue(String.class));

                FireBaseUtils.getRef().child("publico").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //   HashMap<String,Object> principal= (HashMap<String, Object>) snapshot.getValue();

                        datosUser = new Usuario((String)dataSnapshot.child("nombre").getValue(),(String) dataSnapshot.child("email").getValue(), (String) dataSnapshot.child("foto").getValue());


                        setEstado(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    static  public void anyadircombate() throws Exception {

        if(soyunico!=null) {
            ref = FirebaseDatabase.getInstance().getReference();


            Combate combate = new Combate("Hola",true);
            ref.child(user.getUid()).child("combates").push().setValue(combate);

        }
        else{
            throw new Exception("Excemfasdf");
        }
    }

    public static String  getKey() {
        return key;
    }


    public static void Borrar(){
        soyunico=null;
        datosUser=null;
        key=null;
       setEstado(false);

    }



}
