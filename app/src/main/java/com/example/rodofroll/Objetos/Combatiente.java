package com.example.rodofroll.Objetos;

import java.io.Serializable;
import java.util.List;

public abstract class Combatiente implements Serializable {


    double vida;
    double armadura;
    double modiniciativa;
    double ataque;
    double velocidad;
    double fuerza;
    double destreza;
    double concentracion;
    double inteligencia;
    double sabiduria;
    double carisma;
    double fortaleza;
    double reflejos;
    double voluntad;
    double iniciativa;


    public double getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public double getArmadura() {
        return armadura;
    }

    public void setArmadura(int armadura) {
        this.armadura = armadura;
    }

    public double getModiniciativa() {
        return modiniciativa;
    }

    public void setModiniciativa(int modiniciativa) {
        this.modiniciativa = modiniciativa;
    }

    public double getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }



    public double getIniciativa() {
        return iniciativa;
    }

    public void setIniciativa(int iniciativa) {
        this.iniciativa = iniciativa;
    }

    public double getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public double getDestreza() {
        return destreza;
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    public double getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(int concentracion) {
        this.concentracion = concentracion;
    }

    public double getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    public double getSabiduria() {
        return sabiduria;
    }

    public void setSabiduria(int sabiduria) {
        this.sabiduria = sabiduria;
    }

    public double getCarisma() {
        return carisma;
    }

    public void setCarisma(int carisma) {
        this.carisma = carisma;
    }

    public double getFortaleza() {
        return fortaleza;
    }

    public void setFortaleza(int fortaleza) {
        this.fortaleza = fortaleza;
    }

    public double getReflejos() {
        return reflejos;
    }

    public void setReflejos(int reflejos) {
        this.reflejos = reflejos;
    }

    public double getVoluntad() {
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