import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Modelo.Equipo;
import Modelo.Partido;
import Modelo.Persona;

/**
 * @author
 *         Juan Ignacio Padron Schneider
 *         Victoria de Goycoechea
 *         Maximiliano Sorano
 *         Agustina Birn
 *         Mailen da Silva
 */

public class TPI {

    public static ArrayList<Equipo> todosLosEquipos = new ArrayList<>();   // Instanciare y guardare los Equipos acá
    public static ArrayList<Persona> todasLasPersonas = new ArrayList<>(); // Instanciare y guardare las Personas acá 
    public static ArrayList<Partido> todosLosPartidos = new ArrayList<>(); // Instanciare y guardare los Partidos acá

    /*
     * public static void main(String[] args) {
     * 
     * try {
     * 
     * Scanner scan = new Scanner(System.in);
     * 
     * BufferedReader lectorResultados = new BufferedReader(new
     * FileReader("src\\Archivos\\resultados.csv"));
     * BufferedReader lectorPronosticos = new BufferedReader(new
     * FileReader("src\\Archivos\\pronosticos.csv"));
     * 
     * System.out.println("Ingrese el nombre de la Persona:");
     * Persona pers = new Persona(scan.nextLine());
     * 
     * sumarMostrarResultadoPronostico(lectorResultados, lectorPronosticos, pers);
     * 
     * scan.close();
     * lectorPronosticos.close();
     * lectorResultados.close();
     * 
     * } catch (Exception e) {
     * // TODO: handle exception
     * }
     * 
     * 
     * }
     */
    public static void sumarMostrarResultadoPronostico(BufferedReader lectorResultados,
            BufferedReader lectorPronosticos, Persona pers) {

        try {

            String lineaPronosticos;

            while ((lineaPronosticos = lectorPronosticos.readLine()) != null) { // Si la linea no es nula
                String lineaResultados = lectorResultados.readLine();

                String[] arrPronosticoLinea = lineaPronosticos.split(";");
                /*
                 * Formato de posición de la información:
                 * EQUIPO 1;GANA E1;EMPATE;GANA E2;EQUIPO2
                 * Por lo tanto, la pos 0 y 4 solo nos dicen los nombres de los equipos, y en
                 * este caso solo
                 * necesito la información provista de las posiciones 1 a la 3, donde según
                 * donde este marcada
                 * la "X" me dirá cuál equipo marcó como ganador
                 */
                String[] arrResultadoLinea = lineaResultados.split(";");
                /*
                 * Misma lógica que en pronosticos, con resultados tmb separamos por ";" en un
                 * arreglo,
                 * pero la información dada acá es distinta, nos dice el resultado (en goles de
                 * cada equipo),
                 * por lo tanto debemos analizar en cada caso cual gano.
                 * Formato de posición de la informació:
                 * EQUIPO 1;GOLES E1;GOLES E2;EQUIPO 2
                 * Por lo tanto, la pos 0 y 3 nos dicen los nombres de los equipos, y en este
                 * caso
                 * solo necesito la información de la pos 1 y 2
                 */

                if (Integer.parseInt(arrResultadoLinea[1]) > Integer.parseInt(arrResultadoLinea[2])) { // Si el EQUIPO 1
                                                                                                       // ganó
                    if (arrPronosticoLinea[1].equals("X")) { // Si la persona acertó
                        pers.aumentarPuntaje();
                    }
                } else if (Integer.parseInt(arrResultadoLinea[1]) < Integer.parseInt(arrResultadoLinea[2])) { // Si el
                                                                                                              // EQUIPO
                                                                                                              // 2 ganó
                    if (arrPronosticoLinea[3].equals("X")) { // Si la persona acertó
                        pers.aumentarPuntaje();
                    }
                } else { // Si EMPATARON
                    if (arrPronosticoLinea[2].equals("X")) { // Si la persona acertó
                        pers.aumentarPuntaje();
                    }
                }

            }

            System.out.println(pers.toString());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        try {           

            BufferedReader lectorPronosticos = new BufferedReader(new FileReader("src\\Archivos\\pronosticos.csv"));
            BufferedReader lectorResultados = new BufferedReader(new FileReader("src\\Archivos\\resultados.csv"));
            
            recorrerArchivoPronostico(lectorPronosticos);
            recorrerArchivoResultado(lectorResultados);

        } catch (Exception e) {
            // TODO: handle exception
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
                 * Por lo tanto, la pos 1 y 5 solo nos dicen los nombres de los equipos, y en
                 * este caso solo necesito la información provista de las posiciones 1 a la 3,
                 * donde según donde este marcada
                 * la "X" me dirá cuál equipo marcó como ganador
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
            // TODO: handle exception
        }
            

    }

    public static void recorrerArchivoResultado( BufferedReader lectorResultados){
        
        try {

            String lineaResultados;
            while ((lineaResultados = lectorResultados.readLine()) != null) {
    
                String[] arrResultadoLinea = lineaResultados.split(";");
                /*
                 * Misma lógica que en pronosticos, con resultados tmb separamos por ";" en un
                 * arreglo,
                 * pero la información dada acá es distinta, nos dice el resultado (en goles de
                 * cada equipo),
                 * por lo tanto debemos analizar en cada caso cual gano.
                 * Formato de posición de la informació:
                 * EQUIPO 1;GOLES E1;GOLES E2;EQUIPO 2
                 * Por lo tanto, la pos 0 y 3 nos dicen los nombres de los equipos, y en este
                 * caso
                 * solo necesito la información de la pos 1 y 2
                 */
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }

 
    }

}
