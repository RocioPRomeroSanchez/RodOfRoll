
package com.example.rodofroll.Objetos;


import com.example.rodofroll.Firebase.FirebaseUtilsV1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Personaje extends Combatiente {


    HashMap<String, Object> inventario;
    Dinero dinero=new Dinero();
    double peso;
    double pesolimit;
    int exptope;
    List<Cosa> cosas;



    public int getExptope() {
        return exptope;
    }




    public double getPeso() {
        return peso;
    }

    public double getPesolimit() {
        return pesolimit;
    }

    public void setPesolimit(double pesolimit){
        this.pesolimit=pesolimit;
    }


    public Personaje() {
    }




    public void setCombates(List<CombateAsociado> combates) {
        this.combates = combates;
    }

    public Dinero getDinero() {
        return dinero;
    }

    public static String[] getAlineamientos() {
        return alineamientos;
    }


    public Personaje(Object atributos, Object biografia, Object inventario, String key) {
        this.atributos = (HashMap<String, Object>) atributos;
        this.biografia = (HashMap<String, Object>)biografia;
        this.inventario = (HashMap<String, Object>)inventario;
        this.key=key;

       //Atributos
        vida = Integer.parseInt(this.atributos.get("vida").toString());
        modiniciativa = Integer.parseInt(this.atributos.get("iniciativa").toString());
        armadura = Integer.parseInt(this.atributos.get("armadura").toString());
        ataque = Integer.parseInt(this.atributos.get("ataque").toString());
        velocidad = Integer.parseInt(this.atributos.get("velocidad").toString());
        fuerza = Integer.parseInt(this.atributos.get("fuerza").toString());
        destreza = Integer.parseInt(this.atributos.get("destreza").toString());
        constitucion = Integer.parseInt(this.atributos.get("constitucion").toString());
        inteligencia = Integer.parseInt(this.atributos.get("inteligencia").toString());
        sabiduria = Integer.parseInt(this.atributos.get("sabiduria").toString());
        carisma = Integer.parseInt(this.atributos.get("carisma").toString());
        fortaleza = Integer.parseInt(this.atributos.get("fortaleza").toString());
        reflejos = Integer.parseInt(this.atributos.get("reflejos").toString());
        voluntad = Integer.parseInt(this.atributos.get("voluntad").toString());

       //Biografia
        nombre= (String) this.biografia.get("nombre");
        imagen=(String)this.biografia.get("imagen");
        raza=(String) this.biografia.get("raza");
        clase=(String)this.biografia.get("clase");
        alineamiento=Integer.parseInt(this.biografia.get("alineamiento").toString());
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
       this.dinero = new Dinero();
       this.peso=0d;
       this.pesolimit=0d;
       this.exptope=0;


       ReorganizarPersonaje();

    }

    protected void ReorganizarPersonaje(){
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


    @Override
    public HashMap<String,Object> Map(){

        HashMap<String, HashMap<String, Object>> principal = new HashMap<>();
        principal.put("atributos",atributos);
        principal.put("inventario",inventario);
        principal.put("biografia",biografia);


        HashMap<String, Object> objetPrincipal = new HashMap<String, Object>(principal);
        return objetPrincipal;
    }



    public static class Cosa {
        String key="";
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




}
