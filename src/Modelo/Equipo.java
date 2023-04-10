package Modelo;
public class Equipo {

    private String nombre;

    /* CONSTRUCTOR */

    public Equipo(String nombre){
        this.nombre = nombre;
    }

    /* OBSERVADOR */
    
    public String getNombre(){
        return nombre;
    }

    /* PROPIO DEL TIPO */

    public boolean compararNombre(String nombreEquipo){
        return this.nombre.equals(nombreEquipo);
    }   
}