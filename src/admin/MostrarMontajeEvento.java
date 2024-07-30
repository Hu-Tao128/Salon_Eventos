package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            
            System.out.printf("%-10s %-10s\n", 
            "Evento", "Montaje");

                while (resultado.next()) {
                // Obtener datos
                int evento = resultado.getInt("evento");
                int montaje = resultado.getInt("montaje");  

                // Imprimir datos en forma de tabla
                System.out.printf("%-10d %-10d\n", 
                        evento, montaje);
                }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
