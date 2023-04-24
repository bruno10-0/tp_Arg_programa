package com.mycompany.tp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class main {

    public static void main(String[] args) throws SQLException {
        //Herramientas auxiliares 
        conexion ObjetoConexion = new conexion();
        ObjetoConexion.establecerconexion();
        int[] gol_equipos = new int[2];
        ArrayList<String[]> datosCSV = new ArrayList<String[]>();
        ArrayList<Pronostico> datosBD = new ArrayList<Pronostico>();
        datosCSV = leerCSV();
        
        //datosBD=imprimirCampos();
        //creamos Equipos
        Equipo equipo1 = new Equipo(" Argentina ", "Histórico equipo de fútbol con estilo técnico y ofensivo.", 0);
        Equipo equipo2 = new Equipo(" Brasil ", "Equipo icónico y exitoso, con estilo de juego ofensivo.", 1);
        Equipo equipo3 = new Equipo(" Polonia ", "Equipo competitivo, liderado por el goleador Robert Lewandowski.", 2);
        Equipo equipo4 = new Equipo(" Uruguay ", "Histórico equipo con garra charrúa y estilo defensivo destacado.", 3);
        Equipo equipo5 = new Equipo(" Nueva Zelanda ", "Equipo en ascenso con enfoque en juego físico.", 4);
        Equipo equipo6 = new Equipo(" Mexico ", "Equipo con gran pasión y habilidad en el fútbol.", 5);
        System.out.println("\nRonda 1");

        //Creamos Partidos, los mostramos con un metodo y asignamos goles de manera aleatoria
        main.goles(gol_equipos);
        Partido partido1 = new Partido(equipo1, equipo2, gol_equipos[0], gol_equipos[1], 1);
        partido1.MostarPartido();
        main.goles(gol_equipos);
        Partido partido2 = new Partido(equipo3, equipo4, gol_equipos[0], gol_equipos[1], 2);
        partido2.MostarPartido();
        main.goles(gol_equipos);
        Partido partido3 = new Partido(equipo5, equipo6, gol_equipos[0], gol_equipos[1], 3);
        
        partido3.MostarPartido();
        //Guardamos los Registros de la Base de datos :D
        datosBD=main.imprimirCampos(partido1, partido2, partido3);
        //Creamos una Ronda
        Ronda ronda1 = new Ronda(partido1, partido2, partido3, 1);
        //Cargar los datos del archivo CSV
        //main.CargarDatosCSV(partido1, partido2, partido3, datosCSV);
        
        //Realiza la conexion con la BD
        try {
            conexion conectar = new conexion();
            JOptionPane.showMessageDialog(null, "Se pudo conectar con la base de datos");
            conectar.establecerconexion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con la base de datos, error: " + e.toString());
        }
        
        
        main.MostrarResultadoPronosticosBD(datosBD);
        //FIN DEL MAIN
        System.out.println(partido1.resultadoPartido());
    }
    public static void MostrarResultadoPronosticosBD(ArrayList<Pronostico> datosBD ){
        
        for(int i=0;i<datosBD.size();i++) {
            System.out.println("id_partido :"+datosBD.get(i).getPartido().getIdPartido()+" | usuario: "
            +datosBD.get(i).getUsuario()+" | id_partido: "+datosBD.get(i).getPartido().getIdPartido()+" | equipo1: "
            +datosBD.get(i).getPartido().getEquipo1Nom()+" | equipo2"+datosBD.get(i).getPartido().getEquipo2Nom()+" | votó por el equipo: "
            +datosBD.get(i).getVoto());
            System.out.println();
            datosBD.get(i).mostrarResultadoPartido();
            System.out.println();
        }
}

    //metodo para mostrar los campos de la BD y guarlos
    public static ArrayList<Pronostico> imprimirCampos(Partido partido1, Partido partido2, Partido partido3) throws SQLException {
        Map<Integer, Partido> partidos = new HashMap<>();
        ArrayList<Pronostico> pronosticos = new ArrayList<Pronostico>();
        // Crear los mapas de partido  
        partidos.put(1, partido1);
        partidos.put(2, partido2);
        partidos.put(3, partido3);
        // Crear los mapas de equipo
        Map<Integer, Equipo> equipos = new HashMap<>();
        equipos.put(1, partido1.getEquipo1());
        equipos.put(2, partido1.getEquipo2());
        equipos.put(3, partido2.getEquipo1());
        equipos.put(4, partido2.getEquipo2());
        equipos.put(5, partido3.getEquipo1());
        equipos.put(6, partido3.getEquipo2());

        String url = "jdbc:mysql://localhost/bd_pronosticos";
        String usuario = "root";
        String contrasena = "3540";
        String consulta = "SELECT usuario,id_partido,equipo_ganador,puntos FROM bd_pronosticos.pronosticos;";
        try (Connection conexion = DriverManager.getConnection(url, usuario, contrasena); Statement declaracion = conexion.createStatement(); ResultSet resultado = declaracion.executeQuery(consulta)) {
            ResultSetMetaData metadata = resultado.getMetaData();
            int columnas = metadata.getColumnCount();
            System.out.println();
            // Creamos la lista auxiliar de pronósticos
            List<Pronostico> pronosticosAux = new ArrayList<>();
            while (resultado.next()) {
                String[] registro = new String[columnas];
                for (int i = 1; i <= columnas; i++) {
                    Object valor = resultado.getObject(i);
                    //registro es el array de String que se usará para almacenar los valores de cada registro 
                    // Si el valor obtenido es nulo, se almacena una cadena vacía en su lugar
                    registro[i - 1] = valor != null ? valor.toString() : "";
                   // System.out.print(registro[i - 1] + " ");
                }
                String persona = registro[0];
                int partidoId = Integer.parseInt(registro[1]);
                int equipoId = Integer.parseInt(registro[2]);
                int puntos= Integer.parseInt(registro[3]);
                Partido partido = partidos.getOrDefault(partidoId, null);
                Equipo equipo = equipos.getOrDefault(equipoId, null);
                // Verificar que los objetos existan y crear el pronóstico
                if (partido != null && equipo != null) {
                    Pronostico pronostico = new Pronostico(partido, equipo, persona,equipoId,puntos);
                    pronosticosAux.add(pronostico);
                } else {
                    System.out.println("ERROR: Equipo o partido no encontrado");
                }
              //  System.out.println();
            }
            // Agregar los pronósticos a la lista final
            pronosticos.addAll(pronosticosAux);

        }

        return pronosticos;
    }

    public static ArrayList<Pronostico> CargarDatosCSV(Partido partido1, Partido partido2, Partido partido3, ArrayList<String[]> datosCSV) {
        // lector de PartidoCSV
        ArrayList<Pronostico> pronosticos = new ArrayList<>();
        // Crear los mapas de partido  
        Map<Integer, Partido> partidos = new HashMap<>();
        partidos.put(1, partido1);
        partidos.put(2, partido2);
        partidos.put(3, partido3);
        // Crear los mapas de equipo
        Map<Integer, Equipo> equipos = new HashMap<>();
        equipos.put(1, partido1.getEquipo1());
        equipos.put(2, partido1.getEquipo2());
        equipos.put(3, partido2.getEquipo1());
        equipos.put(4, partido2.getEquipo2());
        equipos.put(5, partido3.getEquipo1());
        equipos.put(6, partido3.getEquipo2());
        // Creamos la lista auxiliar de pronósticos
        List<Pronostico> pronosticosAux = new ArrayList<>();
        // recorrer la lista de datos CSV
        for (String[] datos : datosCSV) {
            //obtenemos los valores de la matriz}
            String persona = datos[0];
            int partidoId = Integer.parseInt(datos[1]);
            int equipoId = Integer.parseInt(datos[2]);
            int puntos= Integer.parseInt(datos[3]);
            // Obtener los objetos correspondientes de partido y equipo
            Partido partido = partidos.getOrDefault(partidoId, null);
            Equipo equipo = equipos.getOrDefault(equipoId, null);
            
            // Verificar que los objetos existan y crear el pronóstico
            if (partido != null && equipo != null) {
                Pronostico pronostico = new Pronostico(partido, equipo, persona,equipoId,puntos);
                pronosticosAux.add(pronostico);
            } else {
                System.out.println("ERROR: Equipo o partido no encontrado");
            }
        }

        // Agregar los pronósticos a la lista final
        pronosticos.addAll(pronosticosAux);
        return pronosticos;

    }

    //Metodo para asignar Goles de manera aleatoria
    public static int[] goles(int gol_equipos[]) {
        gol_equipos[0] = ((int) (Math.random() * 10) + 1);
        gol_equipos[1] = ((int) (Math.random() * 10) + 1);
        return gol_equipos;
    }

    //Metodo para leer el archivo CSV
    public static ArrayList<String[]> leerCSV() {
        String archivoCSV = "C:\\Users\\Bruno\\Documents\\NetBeansProjects\\tp\\src\\Pronostico.csv";
        BufferedReader lector = null;
        ArrayList<String[]> datosCSV = new ArrayList<>();
        try {
            lector = new BufferedReader(new FileReader(archivoCSV));
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(";");
                datosCSV.add(datos);
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: No se encontró el archivo.");
        } catch (IOException e) {
            System.out.println("ERROR: Ocurrió un error al leer el archivo.");
        } finally {
            if (lector != null) {
                try {
                    lector.close();
                } catch (IOException e) {
                    System.out.println("ERROR: Ocurrió un error al cerrar el lector.");
                }
            }
        }
        return datosCSV;

    }
}
