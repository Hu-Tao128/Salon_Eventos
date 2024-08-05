package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarMontajeEvento {
    public void showMontajeEvento(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM montaje_evento";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de la relacion montaje y evento");
            System.out.println("=====================================");
            System.out.printf("| %-15s | %-15s |\n", "Monatje", "Evento");
            System.out.println("=====================================");

            

                while (resultado.next()) {

                    int evento = resultado.getInt("evento");
                int montaje = resultado.getInt("montaje"); 

                   
                System.out.printf("%-20s %-15s\n", 
                                    evento, montaje);
                }
                System.out.println("=====================================");

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }

    public int elegirMontajeEvento() {
        Scanner Leer = new Scanner(System.in);
        int IDEventoM = 0;

        do {
            System.out.println("Ingresar el número del evento:");

            try {
                IDEventoM = Leer.nextInt();
                if (IDEventoM <= 0) {
                    System.out.println("El número del montaje debe ser un número positivo.");
                    IDEventoM = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next();
            }

        } while (IDEventoM == 0);

        return IDEventoM;
    }

    public int elegirEventoMontaje() {
        Scanner Leer = new Scanner(System.in);
        int IDMontajeE = 0;

        do {
            System.out.println("Ingresar el número del evento:");

            try {
                IDMontajeE = Leer.nextInt();
                if (IDMontajeE <= 0) {
                    System.out.println("El número del montaje debe ser un número positivo.");
                    IDMontajeE = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next();
            }

        } while (IDMontajeE == 0);

        return IDMontajeE;
    }
}
