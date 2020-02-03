package com.example.rodofroll.Objetos;

import java.io.Serializable;
import java.util.List;

public abstract class Combatiente implements Serializable {


    int vida;
    int armadura;
    int modiniciativa;
    int iniciativa;
    int ataque;
    int velocidad;

    Caracteristica fuerza;
    Caracteristica destreza;
    Caracteristica concentracion;
    Caracteristica inteligencia;
    Caracteristica sabiduria;
    Caracteristica carisma;

    int fortaleza;
    int reflejos;
    int voluntad;

    boolean turno;


    public Combatiente() {

        vida=0;
        armadura=0;
        modiniciativa=0;
        ataque=0;
        velocidad=0;

        fuerza= new Caracteristica(0,0);
        destreza= new Caracteristica(0,0);
        concentracion= new Caracteristica(0,0);
        inteligencia= new Caracteristica(0,0);
        sabiduria= new Caracteristica(0,0);
        carisma= new Caracteristica(0,0);

        fortaleza=0;
        reflejos=0;
        voluntad=0;

        turno= false;
        iniciativa=0;
    }

    class Caracteristica{

        int numero;
        int res;

        public Caracteristica(int numero, int res) {

            this.numero = numero;
            this.res = res;
        }
    }







}