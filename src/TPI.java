import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author 
 * Nicolas Guidara
 * Juan Ignacio Padron Schneider
 * Victoria de Goycochea
 * Javier Peron
 * Gabriel Alberto Ferreyra
 * Ciro Nicolas Molina
 *
 */

public class TPI{


    public static void main(String[] args) {

        try {

            Scanner scan = new Scanner(System.in);
            BufferedReader lectorResultados = new BufferedReader(new FileReader("src\\Archivos\\resultados.csv"));
            BufferedReader lectorPronosticos = new BufferedReader(new FileReader("src\\Archivos\\pronosticos.csv"));

            System.out.println("Ingrese el nombre de la Persona:");
            Persona pers = new Persona(scan.nextLine());

            String lineaPronosticos;

			while ((lineaPronosticos = lectorPronosticos.readLine()) != null){
                //Leo linea del archivo y la separo con split (";") en un arreglo
				String[] arrPronosticoLinea = lineaPronosticos.split(";"); 
                /* Formato de posición de la información:
                 * EQUIPO 1;GANA E1;EMPATE;GANA E2;EQUIPO2
                 * Por lo tanto, la pos 0 y 4 solo nos dicen los nombres de los equipos, y en este caso solo
                 * necesito la información provista de las posiciones 1 a la 3, donde según donde este marcada
                 * la "X" me dirá cuál equipo marcó como ganador
                 */
                
                String lineaResultados = lectorResultados.readLine();
                String[] arrResultadoLinea = lineaResultados.split(";");
                /* Misma lógica que en pronosticos, con resultados tmb separamos por ";" en un arreglo,
                 * pero la información dada acá es distinta, nos dice el resultado (en goles de cada equipo),
                 * por lo tanto debemos analizar en cada caso cual gano.
                 * Formato de posición de la informació:
                 * EQUIPO 1;GOLES E1;GOLES E2;EQUIPO 2
                 * Por lo tanto, la pos 0 y 3 nos dicen los nombres de los equipos, y en este caso
                 * solo necesito la información de la pos 1 y 2
                 */

                 if(Integer.parseInt(arrResultadoLinea[1]) > Integer.parseInt(arrResultadoLinea[2])){ //Si el EQUIPO 1 ganó
                    if(arrPronosticoLinea[1].equals("X")){ //Si la persona acertó
                        pers.aumentarPuntaje();
                    }
                 }else if (Integer.parseInt(arrResultadoLinea[1]) < Integer.parseInt(arrResultadoLinea[2])){ //Si el EQUIPO 2 ganó
                    if(arrPronosticoLinea[3].equals("X")){ //Si la persona acertó
                        pers.aumentarPuntaje();
                    }
                 }else{ //Si EMPATARON
                    if(arrPronosticoLinea[2].equals("X")){ //Si la persona acertó
                        pers.aumentarPuntaje();
                    }
                 }

			}

            System.out.println(pers.toString());
            scan.close();
            lectorPronosticos.close();
            lectorResultados.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }

}
