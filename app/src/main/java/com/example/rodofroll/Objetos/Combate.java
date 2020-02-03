package com.example.rodofroll.Objetos;

import androidx.annotation.NonNull;



public class Combate {

    boolean privado;
    String nombre;

    public Combate(String nombre, boolean privado) {

        this.nombre=nombre;
        this.privado = privado;
    }


    public Combate() {

    }

    public boolean isPrivado() {
        return privado;
    }

    public void setPrivado(boolean privado) {
        this.privado = privado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
