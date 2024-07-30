package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarSalones {
    public void showSalones() {
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM salon";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.printf("%-10s %-20s %-10s %-10s %-10s %-20s %-20s %-10s\n", 
            "Numero", "Nombre", "Capacidad", "Tamaño", "Precio", "Colonia", "Calle", "Numero");

                while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombre = resultado.getString("nombre");
                int capacidad = resultado.getInt("capacidad");
                double tamanio = resultado.getDouble("tamanio");
                int precio = resultado.getInt("precio");
                String dirColonia = resultado.getString("dirColonia");
                String dirCalle = resultado.getString("dirCalle");
                String dirNumero = resultado.getString("dirNumero");

                // Imprimir datos en forma de tabla
                System.out.printf("%-10d %-20s %-10d %-10.2f %-10d %-20s %-20s %-10s\n", 
                                numero, nombre, capacidad, tamanio, precio, dirColonia, dirCalle, dirNumero);
                }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }

   public int elegirSalon() {
        Scanner Leer = new Scanner(System.in);
        int ID = -1;
        boolean entradaValida = false;

        do {
            System.out.println("Ingresar el número de salón");
            try {
                ID = Leer.nextInt();
                Leer.nextLine(); // Limpiar el buffer de entrada
                entradaValida = true; // Marcar la entrada como válida
            } catch (InputMismatchException e) {
                // Manejar excepción de entrada incorrecta
                System.out.println("Ingrese números por favor.");
                Leer.nextLine(); // Limpiar el buffer de entrada
            }
        } while (!entradaValida);

        return ID;
    }

    public float obtenerPrecioSalon(int numeroSalon) {
        Connection conexion = null;
        float precio = 0;

        String consulta = "SELECT precio FROM salon WHERE numero = ?";

        try {
            conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, numeroSalon); // Establece el parámetro del número de salón
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                precio = resultado.getFloat("precio");
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }

        return precio;
    }
}
