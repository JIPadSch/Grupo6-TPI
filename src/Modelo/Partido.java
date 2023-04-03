package Modelo;
public class Partido {

    private Equipo equipo1;
    private Equipo equipo2;
    private int resultado; //Gano equipo 1 = -1, gano equipo 2 = 1, empate = 0

    /* CONSTRUCTOR */

    public Partido(Equipo e1, Equipo e2){
        this.equipo1 = e1;
        this.equipo2 = e2;
        this.resultado = 0;
    }
    
    /* OBSERVADORES */

    public String getEquipo1(){
        return this.equipo1.getNombre();
    }

    public String getEquipo2(){
        return this.equipo2.getNombre();
    }

    public int getResultado(){
        return this.resultado;
    }

    /* MODIFICADOR */

    public void setResultado(int resultado){
        this.resultado = resultado;
    }


    /* PROPIO DEL TIPO */

    public boolean compararResultado(Partido partidoPronostico){
        return (this.resultado == partidoPronostico.getResultado());
    }

}
