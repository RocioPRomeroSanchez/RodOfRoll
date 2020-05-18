package com.example.rodofroll.Objetos;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;

import java.util.List;


public class Combate {

    String nombre;
    String key;

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }

    int ronda;
    List<PersonEnCombate> ordenTurno;

    public Combate(String key, String nombre) {
        this.nombre=nombre;
        this.key=key;
    }

    public String getKey() {
        return key;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Combate(String nombre) {

        this.nombre=nombre;


    }

    public Combate(String key, String nombre,int ronda, List<PersonEnCombate> personEnCombates) {
        this.key=key;
        this.nombre=nombre;
        this.ronda=ronda;
        this.ordenTurno=personEnCombates;
    }


    public static class PersonEnCombate{

        String personajekey;
        String usuariokey;
        int iniciativa;
        Boolean turno;
        String keyprincipal;

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

        int vida;
        int armadura;


        public boolean isIsmonster() {
            return ismonster;
        }

        boolean ismonster;

        public Boolean getTurno() {
            return turno;
        }

        public void setTurno(final Boolean turno1,Combate combate) {
            FirebaseUtilsV1.SET_Turno(combate.getKey(),keyprincipal,turno1);
           turno=turno1;
        }


        public Boolean getAvisar() {
            return avisar;
        }

        public void setAvisar(Boolean avisar) {
            this.avisar = avisar;
        }

        Boolean avisar;

        public String getKeyprincipal() {
            return keyprincipal;
        }

        public void setKeyprincipal(String keyprincipal) {
            this.keyprincipal = keyprincipal;
        }

        public PersonEnCombate(String key, String personajekey, String usuariokey, int iniciativa, Boolean turno, Boolean avisar, Boolean ismonster) {
            this.personajekey=personajekey;
            this.usuariokey=usuariokey;
            this.iniciativa=iniciativa;
            this.turno=turno;
            this.avisar=avisar;
            this.keyprincipal=key;
            this.ismonster=ismonster;
            vida=0;
            armadura=0;

        }
        public PersonEnCombate(String personajekey, String usuariokey,int iniciativa, Boolean turno, Boolean avisar, Boolean ismonster) {
            this.personajekey=personajekey;
            this.usuariokey=usuariokey;
            this.iniciativa=iniciativa;
            this.turno=turno;
            this.avisar=avisar;
            this.ismonster=ismonster;
            vida=0;
            armadura=0;

        }



        public String getPersonajekey() {
            return personajekey;
        }

        public void setPersonajekey(String personajekey) {
            this.personajekey = personajekey;
        }

        public String getUsuariokey() {
            return usuariokey;
        }

        public void setUsuariokey(String usuariokey) {
            this.usuariokey = usuariokey;
        }


        public int getIniciativa() {
            return iniciativa;
        }

        public void setIniciativa(int iniciativa) {
            this.iniciativa = iniciativa;
        }


        public void Avisar(Combate combate) {
            FirebaseUtilsV1.SET_Turno(combate.getKey(),this.keyprincipal,true);
        }
    }


}
