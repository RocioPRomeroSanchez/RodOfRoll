package com.example.rodofroll.Objetos;

import android.graphics.Bitmap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Personaje {

    String nombre;
    String imagen;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Personaje(String nombre, String imagen){
        this.nombre=nombre;
        this.imagen=imagen;
    }


    public JsonObject ToJson(){
        JsonObjectBuilder jo =  Json.createObjectBuilder();
        jo.add("nombre",nombre);
        jo.add("imagen",imagen);

        return jo.build();



    }
}
