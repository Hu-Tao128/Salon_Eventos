package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarPago {
    public void showPagos(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'montaje'
            String consulta = "SELECT * FROM pago";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de los Pagos:");
            System.out.println("=========================================================================================================");
            System.out.printf("| %-10s | %-20s | %-20s | %-20s | %-10s | %-10s |\n", "Número", "Fecha pago", "Monto", "Concepto", "Renta", "Metodo pago");
            System.out.println("=========================================================================================================");
    
            // Iterar sobre el resultado de la consulta
            while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String fechaPago = resultado.getString("fechaPago");
                double monto = resultado.getDouble("monto");
                String concepto = resultado.getString("concepto");
                int renta = resultado.getInt("renta");
                int metodoPago = resultado.getInt("metodoPago");
    
                // Imprimir datos en forma de tabla
                System.out.printf("| %-10s | %-20s | %-20s | %-20s | %-10s | %-10s |\n" , numero, fechaPago, monto, concepto, renta, metodoPago);
            }
            
            System.out.println("=========================================================================================================");
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
    }

