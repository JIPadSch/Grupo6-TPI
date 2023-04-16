package Modelo;
public class Persona {
    
    private String nombre;
    private int puntaje;
    private Pronostico pronosticoPartidos;
    private int puntoPorPartido; //Ahora se puede elegir cuantos puntos se ganaran con Pronostico Acertado

    /* CONSTRUCTOR */

    public Persona(String nombre, int puntoPorPartido){
        this.nombre = nombre;
        this.puntaje = 0;
        this.pronosticoPartidos = new Pronostico();
        this.puntoPorPartido = puntoPorPartido;
    }

    /* OBSERVADORES */
    
    public  String getNombre(){
        return this.nombre;
    }

    public int getPuntaje(){
        return this.puntaje;
    }

    public Pronostico getPronostico(){
        return this.pronosticoPartidos;
    }

    public int getPuntoPorPartido(){
        return this.puntoPorPartido;
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

    public void setPuntoPartido(int puntoPorPartido){
        this.puntoPorPartido = puntoPorPartido;
    }

    /* PROPIO DEL TIPO */

    public void aumentarPuntaje(){
        this.puntaje += this.puntoPorPartido;
    }

    public void aumentarPuntajeAciertoRondaCompleta(){
        this.puntaje += (this.puntoPorPartido * 2);
    }

    public void aumentarPuntajeAciertoFaseCompleta(){
        this.puntaje += (this.puntoPorPartido * 3);
    }

    public String toString(){
        return "Nombre: "+this.nombre+"\nPuntaje: "+this.puntaje;
    }

    public boolean compararNombre(String nombrePersona){
        return this.nombre.equals(nombrePersona);
    }   

}