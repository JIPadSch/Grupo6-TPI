import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

import Modelo.Equipo;
import Modelo.Persona;
import Modelo.Ronda;
import Modelo.Fase;

/**
 * @author
 *         Juan Ignacio Padron Schneider
 */

public class TPI {

    public static ArrayList<Equipo> todosLosEquipos = new ArrayList<>();   // Instanciaré y guardaré los Equipos acá
    public static ArrayList<Persona> todasLasPersonas = new ArrayList<>(); // Instanciaré y guardaré las Personas acá 
    public static ArrayList<Fase> todasLasFases = new ArrayList<>();       // Instanciaré y guardaré las Fases
    public static void main(String[] args) {

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/tpi", "root", "root");
            Statement stmt = con.createStatement();
            System.out.println("Nos logramos conectar bro");

            /* ResultSet rs = stmt.executeQuery("PONGA AQUI SU QUERY");
            while(rs.next());
            System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));

            int result = stmt.executeUpdate("delete from emp where id=33");
            System.out.println(result + " records affected"); */

            con.close();

        }catch(SQLException e){
            System.out.println(e.getMessage()+": ERROR al conectarse a la BD");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage()+": OTRO ERROR");
        }

        try {           

            BufferedReader lectorPronosticos = new BufferedReader(new FileReader("src\\Archivos\\pronosticos.csv"));
            BufferedReader lectorResultados = new BufferedReader(new FileReader("src\\Archivos\\resultados.csv"));

            int puntajePartidoAcertado = 0;
            Scanner scan = new Scanner(System.in);
            do{
                System.out.println("¿Cuantos puntos por Partido acertado deberá ganar una persona?:");
                puntajePartidoAcertado = scan.nextInt();

                if(puntajePartidoAcertado < 1) System.out.println("ERROR: El puntaje no puede ser menor de 1");

            }while (puntajePartidoAcertado < 1);
            
            recorrerArchivoPronostico(lectorPronosticos, puntajePartidoAcertado); //Instancio Equipos y Personas (con sus Pronosticos)
            recorrerArchivoResultado(lectorResultados); //Instancio Rondas (con sus Partidos)
            analizarPartidosConPronosticos(); //Comparo Partidos (ubicado en Rondas) con Pronosticos (ubicado en Personas) y aumento puntaje de las personas según corresponda
            mostrarPuntajePersonas();

        } catch (Exception e) {
            System.out.println(e.getMessage()+": en el MAIN");
        }

    }

    public static void recorrerArchivoPronostico(BufferedReader lectorPronosticos, int puntajePartidoAcertado){

        String lineaPronosticos;

        try {
            while ((lineaPronosticos = lectorPronosticos.readLine()) != null) {

                int i = 0; //Entero que utilizare para iterar más adelante
                String[] arrPronosticoLinea = lineaPronosticos.split(";");
                /*
                 * Formato de posición de la información:
                 * PARTICIPANTE;EQUIPO 1;GANA E1;EMPATE;GANA E2;EQUIPO2
                 */               

                 if(todasLasPersonas.size() == 0){ //Si es el caso de la 1er Persona, la instancio y guardo
                    Persona primerPersona = new Persona(arrPronosticoLinea[0],puntajePartidoAcertado);
                    todasLasPersonas.add(primerPersona);
                 }
                 if(todosLosEquipos.size() == 0){ //Si es el caso del 1er Equipo, lo instancio y guardo
                    Equipo primerEquipo = new Equipo(arrPronosticoLinea[1]);
                    todosLosEquipos.add(primerEquipo);
                 }


                boolean encontrado1 = false, encontrado2 = false, bandera = false; //Boolean para saber si encontre Equipo 1 y/o Equipo 2
                int dondeEquipo1 = 0, dondeEquipo2 = 0; //Variables para saber donde tengo guardados (en el arrayList) los Equipos
                while (i < todosLosEquipos.size() && !bandera) { //Recorro hasta que sea menor a la longitud o ya haya encontrado ambos equipos
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

                    if(encontrado1 && encontrado2){
                        bandera = true; //Puedo cortar el while si ya encontre ambos equipos
                    } 

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
                i = 0; //Reutilizo la variable para iterar
                while (i < todasLasPersonas.size() && !personaExiste){ //Recorro hasta que sea igual a la longitud o encuentre a la persona en el arreglo de Personas
                    if(todasLasPersonas.get(i).compararNombre(arrPronosticoLinea[0])){ //Si encuentro a la persona
                        //Aviso que NO DEBO crear uno nuevo y guardo la posición para más adelante
                        personaExiste = true;
                        dondePersona = i;
                    }
                    i++;
                }

                if(!personaExiste){ //Si no encontre a la persona, la creo y agrego al arrayList de Personas
                    Persona pers = new Persona(arrPronosticoLinea[0],puntajePartidoAcertado);
                    todasLasPersonas.add(pers);
                    dondePersona = todasLasPersonas.size()-1; //Guardo la posición para más adelante
                }

                //Agrego el Pronostico del Partido en esta linea a la Persona
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
        
        String lineaResultados;

        try {

            while ((lineaResultados = lectorResultados.readLine()) != null) {
    
                String[] arrResultadoLinea = lineaResultados.split(";");
                /*
                 * Misma lógica que en pronosticos, con resultados tmb separamos por ";" en un
                 * arreglo, pero la información dada acá es distinta.
                 * Formato de posición de la informació:
                 * FASE;RONDA;EQUIPO 1;GOLES E1;GOLES E2;EQUIPO 2
                 * MUY IMPORTANTE: Asumiré que las fases y rondas estan ordenadas (de menor a mayor, y arrancando desde 1) en el archivo
                 */

                 //Si la fase es mayor a las que tengo guardadas en el arrayList de Fases significa que aún no la cree
                 if(Integer.parseInt(arrResultadoLinea[0]) > todasLasFases.size()){
                    Fase nuevaFase = new Fase();
                    todasLasFases.add(nuevaFase);
                 }

                 //Si la ronda es mayor a las que tengo guardadas en la Fase que estoy, significa que aún no la cree
                 if(Integer.parseInt(arrResultadoLinea[1]) > todasLasFases.get(Integer.parseInt(arrResultadoLinea[0])).tamanio()){ 
                    Ronda nuevaRonda = new Ronda();
                    todasLasFases.get(Integer.parseInt(arrResultadoLinea[0])).agregarRonda(nuevaRonda);
                 }

                    int posicionE1 = 0, posicionE2 = 0, i = 0;
                    boolean encontreE1 = false, encontreE2 = false, encontreAmbos = false;
                    while (i < todosLosEquipos.size() && !encontreAmbos) { //Recorro hasta que sea menor a la longitud o ya haya encontrado ambos equipos
                        if (todosLosEquipos.get(i).compararNombre(arrResultadoLinea[2])){ //Si encontro instancia de EQUIPO 1 
                            //Aviso que lo encontré y guardo la posición
                            encontreE1 = true;
                            posicionE1 = i; 
                        }                       
    
                        if (todosLosEquipos.get(i).compararNombre(arrResultadoLinea[5])){ //Si encontro instancia de EQUIPO 2
                            //Aviso que lo encontré y guardo la posición
                            encontreE2 = true;
                            posicionE2 = i;
                        }
    
                        if(encontreE1 && encontreE2) encontreAmbos = true; //Puedo cortar el while si ya encontre ambos equipos
    
                        i++;
                    }

                    if (Integer.parseInt(arrResultadoLinea[3]) > Integer.parseInt(arrResultadoLinea[4])) {        // Si el EQUIPO 1 ganó
                        todasLasFases.get(Integer.parseInt(arrResultadoLinea[0])).getRondaEnPosicion(Integer.parseInt(arrResultadoLinea[1])-1).agregarPartido(todosLosEquipos.get(posicionE1), todosLosEquipos.get(posicionE2), -1);;
                    } else if (Integer.parseInt(arrResultadoLinea[3]) < Integer.parseInt(arrResultadoLinea[4])) { // Si el EQUIPO 2 ganó
                        todasLasFases.get(Integer.parseInt(arrResultadoLinea[0])).getRondaEnPosicion(Integer.parseInt(arrResultadoLinea[1])-1).agregarPartido(todosLosEquipos.get(posicionE1), todosLosEquipos.get(posicionE2), 1);;
                    } else {                                                                                      // Si EMPATARON
                        todasLasFases.get(Integer.parseInt(arrResultadoLinea[0])).getRondaEnPosicion(Integer.parseInt(arrResultadoLinea[1])-1).agregarPartido(todosLosEquipos.get(posicionE1), todosLosEquipos.get(posicionE2), 0);;      
                    }

            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage()+": en recorrerArchivoResultado");
        }

 
    }

    public static void analizarPartidosConPronosticos(){
        int contadorPartidoPorRondaAcertado = 0, contadorRondaCompletasAcertadas = 0;
        //Si Persona acerto todos los resultados en la ROnda; persona.aumentarPuntajeAciertoRondaCompleta();
        //Si Persona acerto todos los resultados en la Fase; persona.aumentarPuntajeAciertoFaseCompleta();
        for (int e = 0; e < todasLasFases.size(); e++){ //Itero las Fases

            for (int i = 0; i < todasLasFases.get(e).tamanio(); i++) { // Itero las Rondas

                for (int j = 0; j < todasLasPersonas.size(); j++) { // Itero las Personas
    
                    for (int t = 0; t < todasLasPersonas.get(j).getPronostico().cantidadPartidosPronosticados(); t++) { // Itero Pronosticos
                        // Si la Persona acertó el resultado del Partido
                        if(todasLasPersonas.get(j).getPronostico().eleccionPronostico(t) == todasLasFases.get(e).getRondaEnPosicion(i).resultadoEnPosicion(t)){
                            todasLasPersonas.get(j).aumentarPuntaje(); // Le aumento el puntaje
                            contadorPartidoPorRondaAcertado++;
                        }
                    }
            
                    /* if(contadorPartidoPorRondaAcertado == todasLasFases.get(e).getRondaEnPosicion(i).tamanio()){
                        todasLasPersonas.get(j).aumentarPuntajeAciertoRondaCompleta();
                        contadorRondaCompletasAcertadas++;
                        
                        if(contadorRondaCompletasAcertadas == todasLasFases.get(e).tamanio()){
                            todasLasPersonas.get(j).aumentarPuntajeAciertoFaseCompleta();
                        }
                        
                    }

                    contadorPartidoPorRondaAcertado = 0; */
                    
                }

    
            }
            
        }

    }

    public static void mostrarPuntajePersonas(){
        // Iteracion de Personas y muestro sus datos
        for (int i = 0; i < todasLasPersonas.size(); i++) {
            System.out.println(todasLasPersonas.get(i).toString());
        }
    }

}