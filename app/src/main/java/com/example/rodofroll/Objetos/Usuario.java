package com.example.rodofroll.Objetos;

import com.example.rodofroll.Objetos.Combate;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {

  String nombre;
  String key;
  String foto;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public Usuario(String nombre,String foto, String key) {
        this.nombre = nombre;
        this.foto=foto;
        this.key=key;

    }
}
