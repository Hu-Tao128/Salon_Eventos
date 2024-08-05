package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexionDB.ConexionBD;

public class MostrarEquiposEvento {
    public void showEquiposEvento(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM equipos_evento";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de la relacion de equipos y evento");
            System.out.println("===========================");
            System.out.printf("| %-10s | %-10s |\n", "Evento", "Equipamiento");
            System.out.println("===========================");

                while (resultado.next()) {
                // Obtener datos
                int evento = resultado.getInt("evento");
                int equipamiento = resultado.getInt("equipamiento");
                

                // Imprimir datos en forma de tabla
                System.out.printf("%-15s %-10s\n", 
                                evento, equipamiento);
                }
                System.out.println("===========================");
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
