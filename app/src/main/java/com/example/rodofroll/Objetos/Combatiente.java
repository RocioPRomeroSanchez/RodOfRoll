package com.example.rodofroll.Objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Combatiente implements Serializable {


    int vida;
    int armadura;
    int modiniciativa;
    int ataque;
    int velocidad;
    int fuerza;
    int destreza;
    int constitucion;
    int inteligencia;
    int sabiduria;
    int carisma;
    int fortaleza;
    int reflejos;
    int voluntad;
    int iniciativa;
    String imagen;
    String nombre;
    String raza;
    String clase;
    int alineamiento;
    String descripcion;
    int level;
    int exp;
    String key;

    List<CombatesAsociados> combates= new ArrayList<>();


    HashMap<String, Object> atributos;
    HashMap<String, Object> biografia;


    public int getLevel() {
        return level;
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


    public int getExp(){
        return  exp;
    }

    public double getVida() {
        return vida;
    }


    public double getArmadura() {
        return armadura;
    }


    public double getModiniciativa() {
        return modiniciativa;
    }


    public double getAtaque() {
        return ataque;
    }


    public double getVelocidad() {
        return velocidad;
    }

    public String getKey() {
        return key;
    }

    public int getFuerza() {
        return fuerza;
    }


    public int getDestreza() {
        return destreza;
    }


    public int getConstitucion() {
        return constitucion;
    }


    public int getInteligencia() {
        return inteligencia;
    }


    public int getSabiduria() {
        return sabiduria;
    }


    public int getCarisma() {
        return carisma;
    }


    public int getFortaleza() {
        return fortaleza;
    }


    public int getReflejos() {
        return reflejos;
    }


    public int getVoluntad() {
        return voluntad;
    }



    boolean turno;

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


    public int getAlineamiento() {
        return alineamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<CombatesAsociados> getCombates() {
        return combates;
    }

    public Combatiente() {

        vida=0;
        armadura=0;
        modiniciativa=0;
        ataque=0;
        velocidad=0;

        fuerza=0;
        destreza= 0;
        constitucion= 0;
        inteligencia= 0;
        sabiduria= 0;
        carisma= 0;

        fortaleza=0;
        reflejos=0;
        voluntad=0;

        turno= false;
        iniciativa=0;

        this.raza = "";
        this.clase = "";
        this.alineamiento = 0;
        this.descripcion = "";
        this.level = 0;
        this.exp = 0;
    }

    protected void  ReorganizarPersonaje(){
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
        atributos.put("constitucion",constitucion);
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


    }
    public HashMap<String,Object> Map(){
        HashMap<String, HashMap<String, Object>> principal = new HashMap<>();
        principal.put("atributos",atributos);
        principal.put("biografia",biografia);


        HashMap<String, Object> objetPrincipal = new HashMap<String, Object>(principal);
        return objetPrincipal;
    }

    public static class CombatesAsociados{
        String masterkey;
        String combatekey;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        String id;
        double vida;
        double ca;

        public double getVida() {
            return vida;
        }

        public double getCa() {
            return ca;
        }



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

        public CombatesAsociados() {
        }

        public CombatesAsociados(String masterkey, String combatekey, String id) {
            this.masterkey = masterkey;
            this.combatekey = combatekey;
            this.id=id;
            vida=0;
            ca=0;

        }
    }









}