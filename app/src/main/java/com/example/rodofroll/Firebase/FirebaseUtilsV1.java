package com.example.rodofroll.Firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rodofroll.LoginActivity;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Objetos.Combatiente;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.Objetos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.HashMap;

public class FirebaseUtilsV1 {

    private static FirebaseUser user;
    private static String key=null;
    private static DatabaseReference ref;
    private static String token=null;
    static Usuario datosUser;
    static private boolean estado=false;

    //GetersPropios
    public synchronized static void setEstado(boolean estado) {
        FirebaseUtilsV1.estado = estado;
    }
    public synchronized static boolean isEstado() {
        return estado;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        FirebaseUtilsV1.token = token;
    }
    public static String  getKey() {
        return key;
    }

    public static Usuario getDatosUser() {
        return datosUser;
    }

    public static void setUserDetalls(String nombre, String foto){
        datosUser.setNombre(nombre);
        datosUser.setFoto(foto);
        FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.getKey()).child("nombre").setValue(nombre);
        FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.getKey()).child("foto").setValue(foto);
    }


    private static  FirebaseUtilsV1 ourInstance = new FirebaseUtilsV1();

    public static FirebaseUtilsV1 getInstance() {
        return ourInstance;
    }
    public static void RecreateInstance(){
        ourInstance= new FirebaseUtilsV1();
    }

    private FirebaseUtilsV1() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();
    }



    public static String GeneradorKeys(){
        return FirebaseUtilsV1.ref.child("key").push().getKey();
    }

    public static void RegistroUsuario(final String key, final Usuario usuario, final Activity activity){
        FirebaseUtilsV1.ref.child("usuarios").child(FirebaseUtilsV1.user.getUid()).child("id").setValue(key, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                FirebaseUtilsV1.ref.child("publico").child(key).setValue(usuario, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        AddDevice(activity);
                    }
                });
            }
        });
    }




    public static void  GetDatosUsuario(){

        FirebaseUtilsV1.ref.child("usuarios").child(FirebaseUtilsV1.user.getUid()).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                key =  (String)dataSnapshot.child("id").getValue();
                token =(String)dataSnapshot.child("token").getValue();

                FirebaseUtilsV1.ref.child("publico").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        datosUser = new Usuario((String)dataSnapshot.child("nombre").getValue(), (String) dataSnapshot.child("foto").getValue(),dataSnapshot.getKey());
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



    static public DatabaseReference GET_ReferenciaToken(){
        return FirebaseUtilsV1.ref.child("usuarios").child(FirebaseUtilsV1.user.getUid()).child("token");
    }
    static public DatabaseReference GET_RefPersonajes(){
        return    FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("personajes");
    }
    static public  DatabaseReference GET_RefMonstruos(){
        return  FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("monstruos");
    }

    static public DatabaseReference GET_RefPersonaje(String usuariokey,String personajekey){
        return    FirebaseUtilsV1.ref.child("publico").child(usuariokey).child("personajes").child(personajekey);
    }
    static public DatabaseReference GET_RefMonstruo(String usuariokey,String monstruokey){
        return    FirebaseUtilsV1.ref.child("publico").child(usuariokey).child("monstruos").child(monstruokey);
    }
    static public DatabaseReference GET_RefCombates(String masterkey){
        return FirebaseUtilsV1.ref.child("combates").child(masterkey);
    }
    static public DatabaseReference GET_RefCombate(String idcombate){
        return FirebaseUtilsV1.ref.child("combates").child(key).child(idcombate);
    }

    static public DatabaseReference GET_RefCombatesPersonaje(String personajekey){
        return  FirebaseUtilsV1.ref.child("publico").child(key).child("personajes").child(personajekey).child("combates");
    }

    static public DatabaseReference GET_RefNotificaiones(){
       return FirebaseUtilsV1.ref.child("notificaciones").child(FirebaseUtilsV1.key);
    }
    static public DatabaseReference GET_RefRonda(Combate combate){
       return FirebaseUtilsV1.ref.child("combates").child(FirebaseUtilsV1.getKey()).child(combate.getKey()).child("ronda");
    }
    static public DatabaseReference GET_RefCosas(Usuario usuario,Personaje p){
        return FirebaseUtilsV1.ref.child("publico").child(usuario.getKey()).child("personajes").child(p.getKey()).child("inventario").child("cosas");
    }
    static public void SET_TOKEN(Object o){
        FirebaseUtilsV1.ref.child("usuarios").child(user.getUid()).child("token").setValue(o);
    }
    static public void SET_Turno(String combatekey,String keypersonaje,boolean turno){
        FirebaseUtilsV1.ref.child("combates").child(key).child(combatekey).child("ordenturno").child(keypersonaje).child("turno").setValue(turno);
    }
    static public void SET_Ronda(Object o,Combate combate){


        FirebaseUtilsV1.ref.child("combates").child(FirebaseUtilsV1.key).child(combate.getKey()).child("ronda").setValue(o);


    }
    static public void SET_Atributo(String atributo, Object nuevovalor, Combatiente combatiente){
        if(combatiente instanceof Personaje){
            FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("personajes").child(combatiente.getKey()).child("atributos").child(atributo).setValue(nuevovalor);

        }
        else {
            FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("monstruos").child(combatiente.getKey()).child("atributos").child(atributo).setValue(nuevovalor);
        }

    }
    static public void SET_Peso(Object nuevovalor, Personaje personaje){
        FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("personajes").child(personaje.getKey()).child("inventario").child("peso").setValue(nuevovalor);
    }
    static public void SET_PesoLimit( Object nuevovalor, Personaje personaje){
        FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("personajes").child(personaje.getKey()).child("inventario").child("pesolimit").setValue(nuevovalor);
    }
    static public void SET_Dinero(String tipodinero, Object nuevovalor, Personaje personaje){
        FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("personajes").child(personaje.getKey()).child("inventario").child("dinero").child(tipodinero).setValue(nuevovalor);
    }

    public static void SET_PersonjeINTOCombat(Context context, String master, String combate, int iniciativa, Personaje p){



        Combate.DatosCombatiente personEnCombate = new Combate.DatosCombatiente(p.getKey(),FirebaseUtilsV1.getKey(),iniciativa,false,false,false);

        boolean existe = false;
        for(Personaje.CombateAsociado combatesAsociados: p.getCombates()){
            if(combatesAsociados.getCombatekey().equals(combate)& combatesAsociados.getMasterkey().equals(master)){
                existe = true;
                break;
            }
        }

        if(!existe){
            FirebaseUtilsV1.ref.child("combates").child(master).child(combate).child("ordenturno").push().setValue(personEnCombate);
            String id =  FirebaseUtilsV1.ref.child("key").push().getKey();
            FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("personajes").child(p.getKey()).child("combates").child(id).child("masterid").setValue(master);
            FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("personajes").child(p.getKey()).child("combates").child(id).child("combateid").setValue(combate);
            FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("personajes").child(p.getKey()).child("combates").child(id).child("vida").setValue(p.getVida());
            FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("personajes").child(p.getKey()).child("combates").child(id).child("armadura").setValue(p.getArmadura());
            Toast.makeText(context,"Exito",Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(context,"Este personaje ya esta incluido en este combate", Toast.LENGTH_LONG).show();
        }


    }
    public static  void SET_Inicaitiva(String key, Combate combate, double init){
         FirebaseUtilsV1.ref.child("combates").child(FirebaseUtilsV1.getKey()).child(combate.getKey()).child("ordenturno").child(key).child("iniciativa").setValue(init);

    }
    static  public void AddCombate(String nombre) throws Exception {


            ref = FirebaseDatabase.getInstance().getReference();
            Combate combate = new Combate(nombre);
            FirebaseUtilsV1.ref.child("combates").child(FirebaseUtilsV1.key).push().setValue(combate);

        }

    static public void AnyadirCombatiente(Combatiente c) throws Exception {


            if (c instanceof Personaje) {

                FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("personajes").push().setValue(c.Map());

            }
            else {
                FirebaseUtilsV1.ref.child("publico").child(FirebaseUtilsV1.key).child("monstruos").push().setValue(c.Map());
            }


    }

    static public Query OrdenarCombatePorIniciativa(Combate combate){
        return  FirebaseUtilsV1.ref.child("combates").child(key).child(combate.getKey()).child("ordenturno").orderByChild("iniciativa");
    }


    public static void Borrar(){

        if(FirebaseUtilsV1.user!=null){
            datosUser = null;
            key = null;
            setEstado(false);

        }

    }
    public static void BorrarCuenta(final Activity activity){
        FirebaseUtilsV1.ref.child("publico").child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                FirebaseUtilsV1.ref.child("usuarios").child(FirebaseUtilsV1.user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        FirebaseUtilsV1.user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(activity, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                activity.startActivity(intent);


                            }
                        });

                    }

                });


            }
        });


    }

    public static void AddDevice(final Activity activity){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {

                    return;

                }
                final String token = task.getResult().getToken();

                FirebaseUtilsV1.ref.child("usuarios").child(FirebaseUtilsV1.user.getUid()).child("token").setValue(token);
                Intent intent=new Intent(activity, MainActivity.class);

                activity.startActivity(intent);

            }
        });
    }



}
