package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarEventos {
    public void showEventos() {
        Scanner leer = new Scanner(System.in);
        int opcion;
        int evento = 0;
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM evento";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Escoge tu evento:");
            // Iterar sobre el resultado
            // Imprimir la cabecera de la tabla
            System.out.printf("%-10s %-20s\n", "Numero", "Nombre");

            // Imprimir una línea separadora
            System.out.printf("%-10s %-20s\n", "----------", "--------------------");

            while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombre = resultado.getString("nombre");

                // Imprimir datos en forma de tabla
                System.out.printf("%-10d %-20s\n", numero, nombre);
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }

    public int elegirEvento() {
        Scanner Leer = new Scanner(System.in);
        int IDEvento = 0;

        do {
            System.out.println("Ingresar el número del evento:");

            try {
                IDEvento = Leer.nextInt();
                if (IDEvento <= 0) {
                    System.out.println("El número del evento debe ser un número positivo.");
                    IDEvento = 0; // Resetear IDEvento para asegurar que el bucle continúe
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next(); // Limpiar el buffer de entrada
            }

        } while (IDEvento == 0);

        return IDEvento;
    }
}
