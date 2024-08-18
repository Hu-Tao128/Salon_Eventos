package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarEventos {
    
    private Scanner Leer = new Scanner(System.in);
    private int IDEvento = 0;

    public void showEventos() {
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'evento'
            String consulta = "SELECT * FROM evento";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de los Eventos:");
            System.out.println("===========================================");
            System.out.printf("| %-6s | %-30s |\n", "Numero", "Nombre");
            System.out.println("===========================================");
    
            // Iterar sobre el resultado de la consulta
            while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombre = resultado.getString("nombre");
    
                // Imprimir datos en forma de tabla
                System.out.printf("| %-6d | %-30s |\n", numero, nombre);
            }
            
            System.out.println("===========================================");
    
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }
    

    public int elegirEvento() {

        do {
            System.out.println("Ingresar el número del evento:");

            try {
                IDEvento = Leer.nextInt();
                if (IDEvento <= 0) {
                    System.out.println("El número del evento debe ser un número positivo.");
                    IDEvento = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next();
            }

        } while (IDEvento == 0);

        return IDEvento;
    }
}
