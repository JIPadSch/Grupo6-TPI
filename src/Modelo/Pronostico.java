package Modelo;

import java.util.ArrayList;

public class Pronostico {

    private ArrayList<Partido> arrayListPartidos;

    /* CONSTRUCTOR */

    public Pronostico(){
        this.arrayListPartidos = new ArrayList<>();
    }

    /* MODIFICADOR */

    public void agregarPartido(Equipo e1, Equipo e2, int eleccionPronostico){
        Partido partido = new Partido(e1, e2);
        partido.setResultado(eleccionPronostico);
        this.arrayListPartidos.add(partido);
    }
    
}