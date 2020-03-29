package com.example.rodofroll.Objetos;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;


public class Combate {

    String nombre;
    String key;

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }

    int ronda;
    List<PersonEnCombate> ordenTurno;

    public Combate(String key, String nombre) {
        this.nombre=nombre;
        this.key=key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Combate(String nombre) {

        this.nombre=nombre;


    }

    public Combate(String key, String nombre,int ronda, List<PersonEnCombate> personEnCombates) {
        this.key=key;
        this.nombre=nombre;
        this.ronda=ronda;
        this.ordenTurno=personEnCombates;
    }

    public static class PersonEnCombate{

        String personajekey;
        String usuariokey;
        Long iniciativa;
        Boolean turno;
        String key="";

        public Boolean getTurno() {
            return turno;
        }

        public void setTurno(final Boolean turno1,Combate combate) {
           final Query q = FireBaseUtils.getRef().child("combates").child(FireBaseUtils.getKey()).child(combate.getKey()).child("ordenturno").orderByChild("personajekey").equalTo(this.personajekey);


                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            HashMap hashMap = (HashMap) dataSnapshot.getValue();
                            key = (String) hashMap.keySet().toArray()[0];
                            DatabaseReference dt = q.getRef().child(key).child("turno");
                            dt.setValue(turno1);

                            turno = turno1;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





        }

        public Boolean getAvisar() {
            return avisar;
        }

        public void setAvisar(Boolean avisar) {
            this.avisar = avisar;
        }

        Boolean avisar;

        public PersonEnCombate(String personajekey, String usuariokey,Long iniciativa, Boolean turno, Boolean avisar) {
            this.personajekey=personajekey;
            this.usuariokey=usuariokey;
            this.iniciativa=iniciativa;
            this.turno=turno;
            this.avisar=avisar;
        }


        public String getPersonajekey() {
            return personajekey;
        }

        public void setPersonajekey(String personajekey) {
            this.personajekey = personajekey;
        }

        public String getUsuariokey() {
            return usuariokey;
        }

        public void setUsuariokey(String usuariokey) {
            this.usuariokey = usuariokey;
        }


        public Long getIniciativa() {
            return iniciativa;
        }

        public void setIniciativa(Long iniciativa) {
            this.iniciativa = iniciativa;
        }


    }
}
