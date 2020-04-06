
package com.example.rodofroll.Objetos;


import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.rodofroll.Firebase.FireBaseUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Personaje extends Combatiente {



    String imagen;
    String nombre;
    String raza;
    String clase;
    String alineamiento;
    String descripcion;
    Long level;
    Long exp;


    List<CombatesAsociados> combates= new ArrayList<>();
    HashMap<String, Object> atributos;
    HashMap<String, Object> biografia;
    HashMap<String, Object> inventario;
    Dinero dinero;
    List<Cosa> cosas;

    public Personaje() {

        nombre="";
    }


    public List<CombatesAsociados> getCombates() {
        return combates;
    }

    public void setCombates(List<CombatesAsociados> combates) {
        this.combates = combates;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;


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

    public long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
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


    public static String[] alineamientos = new String[]{
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


    public Personaje(Object atributos, Object biografia, Object inventario, String key) {
        this.atributos = (HashMap<String, Object>) atributos;
        this.biografia = (HashMap<String, Object>)biografia;
        this.inventario = (HashMap<String, Object>)inventario;

       /* vida= Integer.parseInt((String) this.atributos.get("vida"));
        armadura =  Integer.parseInt((String) this.atributos.get("armadura"));
        iniciativa= Integer.parseInt((String) this.atributos.get("iniciativa"));
        ataque=  Integer.parseInt((String) this.atributos.get("ataque"));
        velocidad=  Integer.parseInt((String) this.atributos.get("velocidad"));*/

       //Atributos
        vida = Double.parseDouble(this.atributos.get("vida").toString());
        modiniciativa = Double.parseDouble(this.atributos.get("iniciativa").toString());
        armadura = Double.parseDouble(this.atributos.get("armadura").toString());
        ataque = Double.parseDouble(this.atributos.get("ataque").toString());
        velocidad = Double.parseDouble(this.atributos.get("velocidad").toString());
        fuerza = Double.parseDouble(this.atributos.get("fuerza").toString());
        destreza = Double.parseDouble(this.atributos.get("destreza").toString());
        concentracion = Double.parseDouble(this.atributos.get("concentracion").toString());
        inteligencia = Double.parseDouble(this.atributos.get("inteligencia").toString());
        sabiduria = Double.parseDouble(this.atributos.get("sabiduria").toString());
        carisma = Double.parseDouble(this.atributos.get("carisma").toString());
        fortaleza = Double.parseDouble(this.atributos.get("fortaleza").toString());
        reflejos = Double.parseDouble(this.atributos.get("reflejos").toString());
        voluntad = Double.parseDouble(this.atributos.get("voluntad").toString());



       //Biografia
        nombre= (String) this.biografia.get("nombre");
        imagen=(String)this.biografia.get("imagen");
        raza=(String) this.biografia.get("raza");
        clase=(String)this.biografia.get("clase");
        alineamiento=(String)this.biografia.get("alineamiento");
        descripcion=(String)this.biografia.get("descripcion");
        try {
            level = (long) this.biografia.get("level");
        }catch (NullPointerException ex){

            level=0L;
        }
        try {
            exp = (long) this.biografia.get("exp");
        }catch (NullPointerException ex){

            exp=0L;
        }

        //Inventario
        dinero= new Dinero();
        try{
            dinero.oro = (long)((HashMap<String,Object>)(this.inventario.get("dinero"))).get("oro");
        }
        catch (NullPointerException ex){
            exp=0L;
        }
        try{
            dinero.plata = (long)((HashMap<String,Object>)(this.inventario.get("dinero"))).get("plata");
        }
        catch (NullPointerException ex){
            exp=0L;
        }
        try{
            dinero.cobre = (long)((HashMap<String,Object>)(this.inventario.get("dinero"))).get("cobre");
        }
        catch (NullPointerException ex){
            exp=0L;
        }
        cosas= (List<Cosa>) this.inventario.get("cosas");

        this.key= key;




    }

    public Personaje(String nombre, String imagen) {

        this.nombre = nombre;
        this.imagen = imagen;
        this.raza = "";
        this.clase = "";
        this.alineamiento = "";
        this.descripcion = "";
        this.level = 0L;
        this.exp = 0L;
       this.dinero = new Dinero();


       ReorganizarPersonaje();

    }
    /*

    public Personaje(String imagen, String nombre, String raza, String clase, String alineamiento, String descripcion, long level, long exp, Dinero dinero, List<Cosa> cosas) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.raza = raza;
        this.clase = clase;
        this.alineamiento = alineamiento;
        this.descripcion = descripcion;
        this.level = level;
        this.exp = exp;
        this.dinero = dinero;
        this.cosas = cosas;

        ReorganizarPersonaje();
    }*/

    class Cosa {
        String nombre;
        double peso;

        public Cosa(String nombre, double peso) {
            this.nombre = nombre;
            this.peso = peso;
        }
    }

    class Dinero {
        long oro;
        long plata;
        long cobre;

        public Dinero() {
            this.oro = 0L;
            this.plata = 0L;
            this.cobre = 0L;
        }
    }
    private void ReorganizarPersonaje(){
        atributos = new HashMap<>();
        atributos.put("vida", vida);
        atributos.put("armadura", armadura);
        atributos.put("iniciativa", iniciativa);
        atributos.put("ataque", ataque);
        atributos.put("velocidad", velocidad);
        atributos.put("fuerza",fuerza);
        atributos.put("destreza",destreza);
        atributos.put("carisma",carisma);
        atributos.put("inteligencia",inteligencia);
        atributos.put("sabiduria",sabiduria);
        atributos.put("concentracion",concentracion);
        atributos.put("fortaleza",fortaleza);
        atributos.put("reflejos",reflejos);
        atributos.put("voluntad",voluntad);



        biografia = new HashMap<>();
        biografia.put("imagen", imagen);
        biografia.put("nombre", nombre);
        biografia.put("raza", raza);
        biografia.put("clase", clase);
        biografia.put("alineamiento", alineamiento);
        biografia.put("descripcion", descripcion);
        biografia.put("level", level);
        biografia.put("exp", exp);

        inventario = new HashMap<>();
        HashMap<String, Object> dinero = new HashMap<>();
        dinero.put("oro", this.dinero.oro);
        dinero.put("plata", this.dinero.plata);
        dinero.put("cobre", this.dinero.cobre);
        inventario.put("dinero", dinero);
        inventario.put("cosas",cosas);




    }

    public HashMap<String,Object> Map(){

        HashMap<String, HashMap<String, Object>> principal = new HashMap<>();
        principal.put("atributos",atributos);
        principal.put("inventario",inventario);
        principal.put("biografia",biografia);

        HashMap<String, Object> objetPrincipal = new HashMap<String, Object>(principal);
        objetPrincipal.put("iniciativa",iniciativa);


        return objetPrincipal;
    }


    public void ModificarAtributosPersonaje(String atributo, Object nuevovalor, Personaje personaje) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        if(key!=null&!key.isEmpty()){


            FireBaseUtils.getRef().child("publico").child(FireBaseUtils.getKey()).child("personajes").child(key).child("atributos").child(atributo).setValue(nuevovalor);


        /*    try {
                Field f = Personaje.class.getDeclaredField(atributo);

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }*/
        }

        //

/*
        personaje.getClass().getField(atributo).set(personaje,nuevovalor);
        String s ="hola";*/
    }

   public static class CombatesAsociados{
       String masterkey;
       String combatekey;
       int vida;

       public String getMasterkey() {
           return masterkey;
       }

       public void setMasterkey(String masterkey) {
           this.masterkey = masterkey;
       }

       public String getCombatekey() {
           return combatekey;
       }

       public void setCombatekey(String combatekey) {
           this.combatekey = combatekey;
       }



        public CombatesAsociados(String masterkey, String combatekey) {
            this.masterkey = masterkey;
            this.combatekey = combatekey;

        }
    }




}
