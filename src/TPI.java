import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import Modelo.Equipo;
import Modelo.Persona;
import Modelo.Ronda;

/**
 * @author
 *         Juan Ignacio Padron Schneider
 *         Victoria de Goycoechea
 *         Maximiliano Sorano
 *         Agustina Birn
 *         Mailen da Silva
 */

public class TPI {

    public static ArrayList<Equipo> todosLosEquipos = new ArrayList<>();   // Instanciaré y guardaré los Equipos acá
    public static ArrayList<Persona> todasLasPersonas = new ArrayList<>(); // Instanciaré y guardaré las Personas acá 
    public static ArrayList<Ronda> todasLasRondas = new ArrayList<>();     // Instanciaré y guardaré las Rondas acá

    public static void main(String[] args) {

        try {           

            BufferedReader lectorPronosticos = new BufferedReader(new FileReader("src\\Archivos\\pronosticos.csv"));
            BufferedReader lectorResultados = new BufferedReader(new FileReader("src\\Archivos\\resultados.csv"));
            
            recorrerArchivoPronostico(lectorPronosticos); //Instancio Equipos y Personas (con sus Pronosticos)
            recorrerArchivoResultado(lectorResultados); //Instancio Rondas (con sus Partidos)
            analizarPartidosConPronosticos(); //Comparo Partidos (ubicado en Rondas) con Pronosticos (ubicado en Personas) y aumento puntaje de las personas según corresponda
            mostrarPuntajePersonas();

        } catch (Exception e) {
            System.out.println(e.getMessage()+": en el MAIN");
        }

    }

    public static void recorrerArchivoPronostico(BufferedReader lectorPronosticos){

        String lineaPronosticos;

        try {
            while ((lineaPronosticos = lectorPronosticos.readLine()) != null) {

                int i = 0; //Entero que utilizare para iterar más adelante
                String[] arrPronosticoLinea = lineaPronosticos.split(";");
                /*
                 * Formato de posición de la información:
                 * PARTICIPANTE;EQUIPO 1;GANA E1;EMPATE;GANA E2;EQUIPO2
                 */               

                boolean encontrado1 = false, encontrado2 = false, bandera = false; //Boolean para saber si encontre Equipo 1 y/o Equipo 2
                int dondeEquipo1 = 0, dondeEquipo2 = 0; //Variables para saber donde tengo guardados (en el arrayList) los Equipos
                while (i < todosLosEquipos.size() || bandera) { //Recorro hasta que sea menor a la longitud o ya haya encontrado ambos equipos
                    if (todosLosEquipos.get(i).compararNombre(arrPronosticoLinea[1])){ //Si encontro instancia de EQUIPO 1 
                        //Aviso que NO DEBO crear uno nuevo y guardo la posición para más adelante
                        encontrado1 = true;
                        dondeEquipo1 = i; 
                    }                       

                    if (todosLosEquipos.get(i).compararNombre(arrPronosticoLinea[5])){ //Si encontro instancia de EQUIPO 2
                        //Aviso que NO DEBO crear uno nuevo y guardo la posición para más adelante
                        encontrado2 = true;
                        dondeEquipo2 = i;
                    }

                    if(encontrado1 && encontrado2) bandera = true; //Puedo cortar el while si ya encontre ambos equipos

                    i++;
                }

                if (!encontrado1) { //Si no encontre EQUIPO 1, lo creo y agrego al arrayList de Equipos
                    Equipo equipo = new Equipo(arrPronosticoLinea[1]);
                    todosLosEquipos.add(equipo);
                    dondeEquipo1 = todosLosEquipos.size()-1; //Guardo la posición para más adelante
                }

                if (!encontrado2) { //Si no encontre EQUIPO 2, lo creo y agrego al arrayList de Equipos
                    Equipo equipo = new Equipo(arrPronosticoLinea[5]);
                    todosLosEquipos.add(equipo);
                    dondeEquipo2 = todosLosEquipos.size()-1; //Guardo la posición para más adelante
                }

                boolean personaExiste = false; //Boolean para saber si encontre a la persona
                int dondePersona = 0; //Variable para saber donde tengo guardado (en el arrayList) la Persona
                i=0; //Reutilizo la variable para iterar
                while (i < todasLasPersonas.size() && !personaExiste){ //Recorro hasta que sea menor a la longitud o encuentre a la persona en el arreglo de Personas
                    if(todasLasPersonas.get(i).compararNombre(arrPronosticoLinea[0])){ //Si encuentro a la persona
                        //Aviso que NO DEBO crear uno nuevo y guardo la posición para más adelante
                        personaExiste = true;
                        dondePersona = i;
                    }
                    i++;
                }

                if(!personaExiste){ //Si no encontre a la persona, la creo y agrego al arrayList de Personas
                    Persona pers = new Persona(arrPronosticoLinea[0]);
                    todasLasPersonas.add(pers);
                    dondePersona = todasLasPersonas.size()-1; //Guardo la posición para más adelante
                }


                if (arrPronosticoLinea[2].equals("X")) {       // Si la persona eligió gana EQUIPO 1
                    todasLasPersonas.get(dondePersona).agregarPronostico(todosLosEquipos.get(dondeEquipo1),todosLosEquipos.get(dondeEquipo2),-1);
                }else if (arrPronosticoLinea[3].equals("X")) { // Si la persona eligió EMPATE
                    todasLasPersonas.get(dondePersona).agregarPronostico(todosLosEquipos.get(dondeEquipo1),todosLosEquipos.get(dondeEquipo2),0);
                }else if (arrPronosticoLinea[4].equals("X")) { // Si la persona eligió gana EQUIPO 2
                    todasLasPersonas.get(dondePersona).agregarPronostico(todosLosEquipos.get(dondeEquipo1),todosLosEquipos.get(dondeEquipo2),1);
                }


            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage()+": en recorrerArchivoPronostico");
        }
            

    }

    public static void recorrerArchivoResultado(BufferedReader lectorResultados){
        
        try {

            String lineaResultados;
            while ((lineaResultados = lectorResultados.readLine()) != null) {
    
                String[] arrResultadoLinea = lineaResultados.split(";");
                /*
                 * Misma lógica que en pronosticos, con resultados tmb separamos por ";" en un
                 * arreglo, pero la información dada acá es distinta, 
                 * nos dice el resultado (en goles de cada equipo),
                 * por lo tanto debemos analizar en cada caso cual gano.
                 * Formato de posición de la informació:
                 * RONDA;EQUIPO 1;GOLES E1;GOLES E2;EQUIPO 2
                 * MUY IMPORTANTE: Asumiré que las rondas estan ordenadas (de menor a mayor, y arrancando desde 1) en el archivo
                 */

                 //Si la ronda es mayor a las que tengo guardadas en el arrayList de Rondas significa que aún no la cree
                 if(Integer.parseInt(arrResultadoLinea[0]) > todasLasPersonas.size()){ 
                    Ronda unaRonda = new Ronda();
                    todasLasRondas.add(unaRonda);
                 }

                    int posicionE1 = 0, posicionE2 = 0, i = 0;
                    boolean encontreE1 = false, encontreE2 = false, encontreAmbos = false;
                    while (i < todosLosEquipos.size() || !encontreAmbos) { //Recorro hasta que sea menor a la longitud o ya haya encontrado ambos equipos
                        if (todosLosEquipos.get(i).compararNombre(arrResultadoLinea[1])){ //Si encontro instancia de EQUIPO 1 
                            encontreE1 = true;
                            posicionE1 = i; 
                        }                       
    
                        if (todosLosEquipos.get(i).compararNombre(arrResultadoLinea[5])){ //Si encontro instancia de EQUIPO 2
                            encontreE2 = true;
                            posicionE2 = i;
                        }
    
                        if(encontreE1 && encontreE2) encontreAmbos = true; //Puedo cortar el while si ya encontre ambos equipos
    
                        i++;
                    }

                    if (Integer.parseInt(arrResultadoLinea[2]) > Integer.parseInt(arrResultadoLinea[3])) { // Si el EQUIPO 1
                        todasLasRondas.get(Integer.parseInt(arrResultadoLinea[0])-1).agregarPartido(todosLosEquipos.get(posicionE1), todosLosEquipos.get(posicionE2), -1);;
                    } else if (Integer.parseInt(arrResultadoLinea[2]) < Integer.parseInt(arrResultadoLinea[3])) { // Si el EQUIPO 2 ganó
                        todasLasRondas.get(Integer.parseInt(arrResultadoLinea[0])-1).agregarPartido(todosLosEquipos.get(posicionE1), todosLosEquipos.get(posicionE2), 1);;
                    } else { // Si EMPATARON
                        todasLasRondas.get(Integer.parseInt(arrResultadoLinea[0])-1).agregarPartido(todosLosEquipos.get(posicionE1), todosLosEquipos.get(posicionE2), 0);;      
                    }

            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage()+": en recorrerArchivoResultado");
        }

 
    }

    public static void analizarPartidosConPronosticos(){

        for (int i = 0; i < todasLasPersonas.size(); i++) { //Itero Personas

            for (int j = 0; j < todasLasRondas.get(i).getArrayListPartidos().size(); j++) { //Itero Rondas

                //Si la Persona acertó el resultado del Partido
                if(todasLasPersonas.get(i).getPronostico().eleccionPronostico(j) == todasLasRondas.get(i).resultadoEnPosicion(j)){
                    todasLasPersonas.get(i).aumentarPuntaje();
                }
                
            }

        }

    }

    public static void mostrarPuntajePersonas(){
        for (int i = 0; i < todasLasPersonas.size(); i++) {
            System.out.println(todasLasPersonas.get(i).toString());
        }
    }

}