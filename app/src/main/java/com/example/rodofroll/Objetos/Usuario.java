package com.example.rodofroll.Objetos;

import com.example.rodofroll.Objetos.Combate;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {

  String nombre;
  String email ;
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
        nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario(String nombre, String email,String foto) {
        this.nombre = nombre;
        this.email = email;
        this.foto=foto;
    }

    public Usuario(String nombre, String email,String foto, String key) {
        this.nombre = nombre;
        this.email = email;
        this.foto=foto;
        this.key=key;

    }
}
