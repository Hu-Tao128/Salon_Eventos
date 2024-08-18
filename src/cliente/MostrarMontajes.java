package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarMontajes {
    private List<Integer> montajesDisponibles = new ArrayList<>();

    private Scanner leer = new Scanner(System.in);
    private int ID = 0;

    public void showMontajes(int evento) {
        Connection conexion = null;
    
        String consultaMontaje = "SELECT " +
                "m.numero AS Numero, " +
                "m.nombreMontaje AS NombreMontaje, " +
                "m.descripcion AS Descripcion " +
                "FROM montaje_evento AS me " +
                "INNER JOIN montaje AS m ON me.montaje = m.numero " +
                "WHERE me.evento = ?;";
    
        try {
            conexion = ConexionBD.obtenerConexion();
            
            try (PreparedStatement statement = conexion.prepareStatement(consultaMontaje)) {
                statement.setInt(1, evento);
                ResultSet resultado = statement.executeQuery();
                
                // Limpiar la lista de montajes disponibles antes de agregar nuevos
                montajesDisponibles.clear();

                System.out.println("Detalles de los Montajes:");
                System.out.println("======================================================================================");
                System.out.printf("| %-6s | %-25s | %-45s |\n", "Número", "Nombre del Montaje", "Descripción");
                System.out.println("======================================================================================");
    
                while (resultado.next()) {
                    int numero = resultado.getInt("Numero");
                    String nombreMontaje = resultado.getString("NombreMontaje");
                    String descripcion = resultado.getString("Descripcion");

                    // Agregar el número del montaje a la lista de montajes disponibles
                    montajesDisponibles.add(numero);
    
                    System.out.printf("| %-6d | %-25s | %-45s |\n", numero, nombreMontaje, descripcion);
                }
                
                System.out.println("======================================================================================");
                
                // Verificación de depuración
                System.out.println("Montajes Disponibles (IDs): " + montajesDisponibles);
            }
    
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }
    }
    
    public int elegirMontaje() {

        do {
            System.out.println("Ingresar el número de montaje:");

            try {
                ID = leer.nextInt();
                if (ID <= 0) {
                    System.out.println("El número de montaje debe ser un número positivo.");
                    ID = 0;
                } else if (!montajesDisponibles.contains(ID)) {
                    System.out.println("El número de montaje ingresado no está disponible. Por favor, elija un número de la lista.");
                    ID = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                leer.next(); // Limpiar el buffer del scanner
            }

        } while (ID == 0);

        return ID;
    }
}
