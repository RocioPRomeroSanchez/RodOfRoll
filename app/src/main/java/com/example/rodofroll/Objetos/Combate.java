package com.example.rodofroll.Objetos;

import androidx.annotation.NonNull;

import java.util.List;


public class Combate {

    String nombre;
    String key;
    List<PersonEnCombate> ordenTurno;

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
        this.ordenTurno=ordenTurno;

    }


    public Combate(String key, String nombre, List<PersonEnCombate> personEnCombates) {
        this.key=key;
        this.nombre=nombre;
        this.ordenTurno=personEnCombates;
    }

    public static class PersonEnCombate{

        String personajekey;
        String usuariokey;


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

        public PersonEnCombate(String key, String personajekey, String usuariokey) {
            this.personajekey = personajekey;
            this.usuariokey = usuariokey;
        }


    }
}
