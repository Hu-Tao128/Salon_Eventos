package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexionDB.ConexionBD;

public class MostrarEquiposRenta {
    public void showEquiposRenta(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM equipos_renta";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de la relacion de equipos y renta");
            System.out.println("===================================================================");
            System.out.printf("| %-10s | %-15s | %-15s | %-15s | \n", "Equipamiento", "Renta", "Cantidad", "Importe");
            System.out.println("===================================================================");
                while (resultado.next()) {
                // Obtener datos
                int equipamiento = resultado.getInt("equipamiento");
                int renta = resultado.getInt("renta");
                double cantidad = resultado.getDouble("cantidad");
                double importe = resultado.getDouble("importe");

                // Imprimir datos en forma de tabla
                System.out.printf("|%-13d | %-15s | %-15s | %-12s |\n", 
                    equipamiento, renta, cantidad, importe);
                }

                System.out.println("===================================================================");

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
