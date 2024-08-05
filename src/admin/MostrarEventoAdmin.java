package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexionDB.ConexionBD;

public class MostrarEventoAdmin {
    public void showEventosAdmin(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM evento";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de los eventos");
            System.out.println("=========================================");
            System.out.printf("| %-10s | %-25s |\n", "Numero", "Nombre");
            System.out.println("=========================================");
                while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombre = resultado.getString("nombre");

                // Imprimir datos en forma de tabla
                System.out.printf("%-15s %-30s\n", 
                                numero, nombre);
                }
                System.out.println("=========================================");

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
