
package com.example.rodofroll.Objetos;


import org.json.JSONException;

import java.util.HashMap;
import java.util.List;


public class Personaje extends Combatiente {


    String imagen;
    String nombre;
    String raza;
    String clase;
    String alineamiento;
    String descripcion;
    int level;
    int exp;

    Dinero dinero;
    List<Cosa> cosas;

    HashMap<String, Object> atributos;
    HashMap<String, Object> biografia;
    HashMap<String, Object> inventario;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
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


    public Personaje(Object atributos, Object biografia, Object inventario) {
        this.atributos = (HashMap<String, Object>) atributos;
        this.biografia = (HashMap<String, Object>)biografia;
        this.inventario = (HashMap<String, Object>)inventario;

       /* vida= Integer.parseInt((String) this.atributos.get("vida"));
        armadura =  Integer.parseInt((String) this.atributos.get("armadura"));
        iniciativa= Integer.parseInt((String) this.atributos.get("iniciativa"));
        ataque=  Integer.parseInt((String) this.atributos.get("ataque"));
        velocidad=  Integer.parseInt((String) this.atributos.get("velocidad"));*/

        nombre= (String) this.biografia.get("nombre");
        imagen=(String)this.biografia.get("imagen");

       /* biografia = new HashMap<>();
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
*/

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

       ReorganizarPersonaje();

    }

    public Personaje(String imagen, String nombre, String raza, String clase, String alineamiento, String descripcion, int level, int exp, Dinero dinero, List<Cosa> cosas) {
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
    }

    class Cosa {
        String nombre;
        Dinero valor;
        double peso;

        public Cosa(String nombre, Dinero valor, double peso) {
            this.nombre = nombre;
            this.valor = valor;
            this.peso = peso;
        }
    }

    class Dinero {
        int oro;
        int plata;
        int cobre;

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

    public HashMap<String, HashMap> Map(){

        HashMap<String , HashMap> principal = new HashMap<>();
        principal.put("atributos",atributos);
        principal.put("inventario",inventario);
        principal.put("biografia",biografia);

        return principal;
    }

    public Object ToJson() throws JSONException {
        /*JSONObject principal = new JSONObject();

        JSONObject atributos = new JSONObject();
        atributos.put("vida", vida);
        atributos.put("armadura", armadura);
        atributos.put("iniciativa", iniciativa);
        atributos.put("ataque", ataque);
        atributos.put("velocidad", velocidad);

        JSONObject biografia = new JSONObject();
        biografia.put("imagen", imagen);
        biografia.put("nombre", nombre);
        biografia.put("raza", raza);
        biografia.put("clase", clase);
        biografia.put("alineamiento", alineamiento);
        biografia.put("descripcion", descripcion);
        biografia.put("level", level);
        biografia.put("exp", exp);

        JSONObject inventario = new JSONObject();
        JSONObject dinero = new JSONObject();
        dinero.put("oro", this.dinero.oro);
        dinero.put("plata", this.dinero.plata);
        dinero.put("cobre", this.dinero.cobre);
        inventario.put("dinero", dinero);


        principal.put("atributos", atributos);
        principal.put("biografia", biografia);
        principal.put("inventario", inventario);
*/


        return null;


    }



}
