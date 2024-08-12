package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexionDB.ConexionBD;

public class MostrarDisponibilidad {
    public void showDispobilidad(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM disponibilidad";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de la disponibilidad");
            System.out.println("================================");
            System.out.printf("| %-10s | %-15s |\n", "Número", "Estado");
            System.out.println("================================");

                while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String estado = resultado.getString("estado");
  
                System.out.printf("|%-12d | %-15s| \n", 
                                numero, estado);
                }

            System.out.println("================================");

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
