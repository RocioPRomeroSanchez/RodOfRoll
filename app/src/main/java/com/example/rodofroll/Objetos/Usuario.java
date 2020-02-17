package com.example.rodofroll.Objetos;

import com.example.rodofroll.Objetos.Combate;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {

  String nombre;
  String email ;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    String foto;

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
}
