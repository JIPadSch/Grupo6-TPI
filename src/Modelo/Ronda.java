package Modelo;

import java.util.ArrayList;

public class Ronda {

    private ArrayList<Partido> arrayListPartidos;

    /* CONSTRUCTOR */

    public Ronda(){
        this.arrayListPartidos = new ArrayList<>();
    }

    /* OBSERVADOR */

    public ArrayList<Partido> getArrayListPartidos(){
        return this.arrayListPartidos;
    }

    public int resultadoEnPosicion(int posicion){
        return this.arrayListPartidos.get(posicion).getResultado();
    }

    /* PROPIO DEL TIPO */

    public void agregarPartido(Equipo e1, Equipo e2, int resultado){
        Partido partido = new Partido(e1, e2, resultado);
        this.arrayListPartidos.add(partido);
    }
    
}
