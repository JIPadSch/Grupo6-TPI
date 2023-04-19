package Modelo;

import java.util.ArrayList;

public class Fase {

    private ArrayList<Ronda> arrayRonda;

    /* CONSTRUCTOR */

    public Fase(){
        arrayRonda = new ArrayList<>();
    }
    
    /* OBSERVADOR */

    public ArrayList<Ronda> getArrayRonda(){
        return this.arrayRonda;
    }

    public Ronda getRondaEnPosicion(int pos){
        return arrayRonda.get(pos);
    }

    public int tamanio(){
        return this.arrayRonda.size();
    }

    public int tamanioRondaEnPos(int pos){
        return this.arrayRonda.get(pos).tamanio();
    }

    /* MODIFICADOR */

    public void agregarRonda(Ronda nuevaRonda){
        this.arrayRonda.add(nuevaRonda);
    }

}