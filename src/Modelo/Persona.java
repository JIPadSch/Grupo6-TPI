package Modelo;
public class Persona {
    
    private String nombre;
    private int puntaje;
    private Pronostico pronosticoPartidos;

    /* CONSTRUCTOR */

    public Persona(String nombre){
        this.nombre = nombre;
        this.puntaje = 0;
    }

    /* OBSERVADORES */
    
    public  String getNombre(){
        return this.nombre;
    }

    public int getPuntaje(){
        return this.puntaje;
    }

    /* MODIFICADORES */

    public void setPuntaje(int puntajeNuevo){
        this.puntaje = puntajeNuevo;
    }

    public void setPronosticoPartidos(Pronostico pronosticoPartidos){
        this.pronosticoPartidos = pronosticoPartidos;
    }

    public void agregarPronostico(Equipo e1, Equipo e2, int eleccionPronostico){
        this.pronosticoPartidos.agregarPartido(e1,e2,eleccionPronostico);
    }

    /* PROPIO DEL TIPO */

    public void aumentarPuntaje(){
        this.puntaje++;
    }

    public String toString(){
        return "Nombre: "+this.nombre+"\nPuntaje: "+this.puntaje;
    }

    public boolean compararNombre(String nombrePersona){
        return this.nombre.equals(nombrePersona);
    }   

}
