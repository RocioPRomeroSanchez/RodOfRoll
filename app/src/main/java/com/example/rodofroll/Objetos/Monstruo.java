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
