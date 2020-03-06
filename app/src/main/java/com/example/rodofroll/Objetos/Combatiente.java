package com.example.rodofroll.Objetos;

import java.io.Serializable;
import java.util.List;

public abstract class Combatiente implements Serializable {


    int vida;
    int armadura;
    int modiniciativa;

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getArmadura() {
        return armadura;
    }

    public void setArmadura(int armadura) {
        this.armadura = armadura;
    }

    public int getModiniciativa() {
        return modiniciativa;
    }

    public void setModiniciativa(int modiniciativa) {
        this.modiniciativa = modiniciativa;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    int iniciativa;

    public int getIniciativa() {
        return iniciativa;
    }

    public void setIniciativa(int iniciativa) {
        this.iniciativa = iniciativa;
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    public int getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(int concentracion) {
        this.concentracion = concentracion;
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    public int getSabiduria() {
        return sabiduria;
    }

    public void setSabiduria(int sabiduria) {
        this.sabiduria = sabiduria;
    }

    public int getCarisma() {
        return carisma;
    }

    public void setCarisma(int carisma) {
        this.carisma = carisma;
    }

    public int getFortaleza() {
        return fortaleza;
    }

    public void setFortaleza(int fortaleza) {
        this.fortaleza = fortaleza;
    }

    public int getReflejos() {
        return reflejos;
    }

    public void setReflejos(int reflejos) {
        this.reflejos = reflejos;
    }

    public int getVoluntad() {
        return voluntad;
    }

    public void setVoluntad(int voluntad) {
        this.voluntad = voluntad;
    }

    public boolean isTurno() {
        return turno;
    }

    public void setTurno(boolean turno) {
        this.turno = turno;
    }

    int ataque;
    int velocidad;

    int fuerza;
    int destreza;
    int concentracion;
    int inteligencia;
    int sabiduria;
    int carisma;

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

        fuerza=0;
        destreza= 0;
        concentracion= 0;
        inteligencia= 0;
        sabiduria= 0;
        carisma= 0;

        fortaleza=0;
        reflejos=0;
        voluntad=0;

        turno= false;
        iniciativa=0;
    }








}