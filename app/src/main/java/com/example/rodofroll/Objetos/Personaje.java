package com.example.rodofroll.Objetos;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Personaje extends Combatiente  {

    String imagen;
    String nombre;
    String raza;
    String clase;
    String alineamiento;
    String descripcion;
    int level;
    int exp;

    List<String> inventario;
    Dinero dinero;
    List<Cosa> cosas;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getAlineamiento() {
        return alineamiento;
    }

    public void setAlineamiento(String alineamiento) {
        this.alineamiento = alineamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public List<String> getInventario() {
        return inventario;
    }

    public void setInventario(List<String> inventario) {
        this.inventario = inventario;
    }

    public Dinero getDinero() {
        return dinero;
    }

    public void setDinero(Dinero dinero) {
        this.dinero = dinero;
    }

    public List<Cosa> getCosas() {
        return cosas;
    }

    public void setCosas(List<Cosa> cosas) {
        this.cosas = cosas;
    }

    public static String[] getAlineamientos() {
        return alineamientos;
    }

    public static void setAlineamientos(String[] alineamientos) {
        Personaje.alineamientos = alineamientos;
    }



    public static String [] alineamientos = new String[]{
      "Legal bueno",
      "Neutral bueno",
      "Caótico bueno",
      "Legal neutral",
      "Neutral",
      "Caótico neutral",
      "Legal malvado",
      "Neutral malvado",
      "Caótico malvado"
    };

    public Personaje(String nombre, String imagen) {
        this.nombre=nombre;
        this.imagen=imagen;
        this.raza="";
        this.clase="";
        this.alineamiento="";
        this.descripcion="";
        this.level=0;
        this.exp=0;
        Dinero dinero= new Dinero();
    }

    public Personaje(String imagen, String nombre, String raza, String clase, String alineamiento, String descripcion, int level, int exp, List<String> inventario, Dinero dinero, List<Cosa> cosas) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.raza = raza;
        this.clase = clase;
        this.alineamiento = alineamiento;
        this.descripcion = descripcion;
        this.level = level;
        this.exp = exp;
        this.inventario = inventario;
        this.dinero = dinero;
        this.cosas = cosas;
    }

    class Cosa{
        String nombre;
        Dinero valor;
        double peso;

    }
    class Dinero{
        int oro;
        int plata;
        int cobre;
    }

    public JsonObject ToJson(){
        JsonObjectBuilder principal = Json.createObjectBuilder();

        JsonObjectBuilder atributos = Json.createObjectBuilder();
        atributos.add("vida",vida);
        atributos.add("armadura",armadura);
        atributos.add("iniciativa",iniciativa);
        atributos.add("ataque",ataque);
        atributos.add("velocidad",velocidad);

        JsonObjectBuilder biografia = Json.createObjectBuilder();
        biografia.add("imagen",imagen);
        biografia.add("nombre",nombre);
        biografia.add("raza",raza);
        biografia.add("clase",clase);
        biografia.add("alineamiento",alineamiento);
        biografia.add("descripcion",descripcion);
        biografia.add("level",level);
        biografia.add("exp",exp);

        JsonObjectBuilder inventario = Json.createObjectBuilder();
        JsonObjectBuilder dinero = Json.createObjectBuilder();
        dinero.add("oro",this.dinero.oro);
        dinero.add("plata",this.dinero.plata);
        dinero.add("cobre",this.dinero.cobre);
        inventario.add("dinero",dinero);


        principal.add("atributos",atributos);
        principal.add("biografia",biografia);
        principal.add("inventario",inventario);

        return principal.build();
    }


}
