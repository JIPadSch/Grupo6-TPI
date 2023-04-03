package Modelo;

import java.util.ArrayList;

public class Ronda {

    private ArrayList<Partido> arrayListPartidos;

    /* CONSTRUCTOR */

    public Ronda(){
        this.arrayListPartidos = new ArrayList<>();
    }

    /* PROPIO DEL TIPO */

    public void agregarPartido(Equipo e1, Equipo e2, int resultado){
        Partido partido = new Partido(e1, e2);
        partido.setResultado(resultado);
        this.arrayListPartidos.add(partido);
    }
    
}
