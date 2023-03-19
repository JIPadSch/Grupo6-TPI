public class Persona {
    
    private String nombre;
    private int puntaje;

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

    /* MODIFICADOR */
    public void setPuntaje(int puntajeNuevo){
        this.puntaje = puntajeNuevo;
    }

    /* PROPIO */
    public void aumentarPuntaje(){
        this.puntaje++;
    }
    public String toString(){
        return "Nombre: "+this.nombre+"\nPuntaje: "+this.puntaje;
    }

}
