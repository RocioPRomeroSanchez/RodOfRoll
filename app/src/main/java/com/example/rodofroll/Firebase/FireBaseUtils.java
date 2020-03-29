package com.example.rodofroll.Firebase;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Objetos.Personaje;
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
import java.util.List;

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





    static public DatabaseReference GetPersonajesRef(){
       return    FireBaseUtils.getRef().child("publico").child(FireBaseUtils.getKey()).child("personajes");
    }

    static public DatabaseReference GetPersonajeRef(String usuariokey,String personajekey){
        return    FireBaseUtils.getRef().child("publico").child(usuariokey).child("personajes").child(personajekey);
    }

       static public void AnyadirCombatiente(Personaje c) throws Exception {
           if(soyunico!=null) {

               if (c instanceof Personaje) {


                   FireBaseUtils.getRef().child("publico").child(FireBaseUtils.getKey()).child("personajes").push().setValue(c.Map());

               }
           }
           else{
               throw new Exception("Excemfasdf");
           }

       }

    static  public void AnyadirCombate(String nombre) throws Exception {

        if(soyunico!=null) {
            ref = FirebaseDatabase.getInstance().getReference();


            Combate combate = new Combate(nombre);
            FireBaseUtils.getRef().child("combates").child(FireBaseUtils.getKey()).push().setValue(combate);

        }
        else{
            throw new Exception("Excemfasdf");
        }
    }

    public static void PersonajeIncluirCombate(Context context, String master, String combate, String usuario,int iniciativa, Personaje p){

        HashMap<String,Object> objeto = new HashMap<>();
        objeto.put("usuariokey",usuario);
        objeto.put("personajekey",p.getKey());
        objeto.put("iniciativa",iniciativa);
        objeto.put("turno",false);
        objeto.put("avisar",false);


        boolean existe = false;
        for(Personaje.CombatesAsociados combatesAsociados: p.getCombates()){
            if(combatesAsociados.getCombatekey().equals(combate)& combatesAsociados.getMasterkey().equals(master)){
                existe = true;
                break;
            }
        }

        if(!existe){
            FireBaseUtils.getRef().child("combates").child(master).child(combate).child("ordenturno").push().setValue(objeto);
            String id =  FireBaseUtils.getRef().child("key").push().getKey();
            FireBaseUtils.getRef().child("publico").child(usuario).child("personajes").child(p.getKey()).child("combates").child(id).child("masterid").setValue(master);
            FireBaseUtils.getRef().child("publico").child(usuario).child("personajes").child(p.getKey()).child("combates").child(id).child("combateid").setValue(combate);
            Toast.makeText(context,"Exito",Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(context,"Este personaje ya esta incluido en este combate", Toast.LENGTH_LONG).show();
        }


    }

    public static String  getKey() {
        return key;
    }


    public static void Borrar(){
        soyunico=null;
        datosUser=null;
        FireBaseUtils.getRef().child("usuarios").child(FireBaseUtils.getUser().getUid()).child("token").setValue(null);
        key=null;
       setEstado(false);

    }


    public static DatabaseReference GetCombates(String key){
        return    FireBaseUtils.getRef().child("combates").child(key);
    }

}
