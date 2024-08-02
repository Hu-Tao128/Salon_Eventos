package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexionDB.ConexionBD;

public class MostrarMontaje {
    public void showMontaje() {
        Connection conexion = null;
    
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'montaje'
            String consulta = "SELECT * FROM montaje";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de los Montajes:");
            System.out.println("=========================================================================================================");
            System.out.printf("| %-10s | %-40s | %-45s |\n", "Número", "Nombre del Montaje", "Descripción");
            System.out.println("=========================================================================================================");
    
            // Iterar sobre el resultado de la consulta
            while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombreMontaje = resultado.getString("nombreMontaje");
                String descripcion = resultado.getString("descripcion");
    
                // Imprimir datos en forma de tabla
                System.out.printf("| %-10d | %-40s | %-45s |\n", numero, nombreMontaje, descripcion);
            }
            
            System.out.println("=========================================================================================================");
    
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }
    
    
}
