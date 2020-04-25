
package com.example.rodofroll.Objetos;


import com.example.rodofroll.Firebase.FirebaseUtilsV1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Personaje extends Combatiente {



    List<CombatesAsociados> combates= new ArrayList<>();

    HashMap<String, Object> inventario;
    Dinero dinero=new Dinero();

    public int getExptope() {
        return exptope;
    }
    public int getExp(){
        return  exp;
    }

    public void setExptope(int exptope) {
        this.exptope = exptope;
    }

   int exptope;

    public double getPeso() {
        return peso;
    }

    public double getPesolimit() {
        return pesolimit;
    }

    double peso;
    double pesolimit;

    String key;
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

    public Personaje(Object atributos, Object biografia, Object inventario, String key) {
        this.atributos = (HashMap<String, Object>) atributos;
        this.biografia = (HashMap<String, Object>)biografia;
        this.inventario = (HashMap<String, Object>)inventario;

        this.key=key;

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
        exp= Integer.parseInt(this.biografia.get("exp").toString());
        level=(Integer.parseInt(this.biografia.get("level").toString()));
        exptope=(Integer.parseInt(this.biografia.get("exptope").toString()));


        //Inventario
        pesolimit = Double.parseDouble(this.inventario.get("pesolimit").toString());
        peso = Double.parseDouble(this.inventario.get("peso").toString());
        HashMap dinero = (HashMap) this.inventario.get("dinero");
        this.dinero.oro = Double.parseDouble(dinero.get("oro").toString());
        this.dinero.plata = Double.parseDouble(dinero.get("plata").toString());
        this.dinero.cobre = Double.parseDouble(dinero.get("cobre").toString());

    }

    public Personaje(String nombre, String imagen) {

        this.nombre = nombre;
        this.imagen = imagen;
        this.raza = "";
        this.clase = "";
        this.alineamiento = "";
        this.descripcion = "";
        this.level = 0;
        this.exp = 0;
       this.dinero = new Dinero();
       this.peso=0d;
       this.pesolimit=0d;
       this.exptope=0;


       ReorganizarPersonaje();

    }

   public static class Cosa {
       public String getNombre() {
           return nombre;
       }

       public void setNombre(String nombre) {
           this.nombre = nombre;
       }

       public double getPeso() {
           return peso;
       }

       public void setPeso(double peso) {
           this.peso = peso;
       }

       String nombre;
        double peso;

        public Cosa(String nombre, double peso) {
            this.nombre = nombre;
            this.peso = peso;
        }

       public Cosa() {
       }
   }

   public static class Dinero {
       public double oro;
       public double plata;
       public double cobre;

        public Dinero() {
            this.oro = 0;
            this.plata = 0;
            this.cobre = 0;
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
        biografia.put("exptope",exptope);

        inventario = new HashMap<>();
        HashMap<String, Object> dinero = new HashMap<>();
        dinero.put("oro", this.dinero.oro);
        dinero.put("plata", this.dinero.plata);
        dinero.put("cobre", this.dinero.cobre);
        inventario.put("dinero", dinero);
        inventario.put("cosas",cosas);
        inventario.put("peso",peso);
        inventario.put("pesolimit",pesolimit);


    }

    public HashMap<String,Object> Map(){

        HashMap<String, HashMap<String, Object>> principal = new HashMap<>();
        principal.put("atributos",atributos);
        principal.put("inventario",inventario);
        principal.put("biografia",biografia);


        HashMap<String, Object> objetPrincipal = new HashMap<String, Object>(principal);
        return objetPrincipal;
    }

    public CombatesAsociados GetCombateAsociado(String key, String masterkey){
        for(CombatesAsociados c : combates){
            if(c.getCombatekey().equals(key)&&c.getMasterkey().equals(masterkey)){
                return c;
            }
        }
       return null;

    }



   public static class CombatesAsociados{
       String masterkey;
       String combatekey;
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



        public CombatesAsociados(String masterkey, String combatekey) {
            this.masterkey = masterkey;
            this.combatekey = combatekey;
            vida=0;
            ca=0;

        }
    }




}
