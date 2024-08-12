package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarMetodoPago {
    public void showMetodoPago(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'montaje'
            String consulta = "SELECT * FROM metodo_pago";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de los Montajes:");
            System.out.println("======================================");
            System.out.printf("| %-10s | %-20s |\n", "Número", "Nombre");
            System.out.println("======================================");
            // Iterar sobre el resultado de la consulta
            while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombre = resultado.getString("nombre");
    
                // Imprimir datos en forma de tabla
                System.out.printf("| %-10s | %-20s |\n" , numero, nombre);
            }
            
            System.out.println("======================================");
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
