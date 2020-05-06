package com.example.rodofroll.Objetos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Monstruo extends Combatiente {
    List<Personaje.CombatesAsociados> combates= new ArrayList<>();

    public Monstruo(Object atributos, Object biografia, String key) {
        this.atributos = (HashMap<String, Object>) atributos;
        this.biografia = (HashMap<String, Object>)biografia;
        this.key=key;

        //Atributos
        vida = Double.parseDouble(this.atributos.get("vida").toString());
        modiniciativa = Double.parseDouble(this.atributos.get("iniciativa").toString());
        armadura = Double.parseDouble(this.atributos.get("armadura").toString());
        ataque = Double.parseDouble(this.atributos.get("ataque").toString());
        velocidad = Double.parseDouble(this.atributos.get("velocidad").toString());
        fuerza = Double.parseDouble(this.atributos.get("fuerza").toString());
        destreza = Double.parseDouble(this.atributos.get("destreza").toString());
        constitucion = Double.parseDouble(this.atributos.get("constitucion").toString());
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
        alineamiento=Integer.parseInt(this.biografia.get("alineamiento").toString());
        descripcion=(String)this.biografia.get("descripcion");
        exp= Integer.parseInt(this.biografia.get("exp").toString());
        level=(Integer.parseInt(this.biografia.get("level").toString()));



    }

    public Monstruo(String nombre, String imagen) {

        this.nombre = nombre;
        this.imagen = imagen;
        ReorganizarPersonaje();

    }

    public Monstruo() {

    }

    public List<Personaje.CombatesAsociados> getCombates() {
        return combates;
    }

    public void setCombates(List<Personaje.CombatesAsociados> combates) {
        this.combates = combates;
    }


    public Personaje.CombatesAsociados GetCombateAsociado(String key, String masterkey){
        for(Personaje.CombatesAsociados c : combates){
            if(c.getCombatekey().equals(key)&&c.getMasterkey().equals(masterkey)){
                return c;
            }
        }
        return null;

    }

}
