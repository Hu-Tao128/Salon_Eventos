package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexionDB.ConexionBD;

public class MostrarMontaje {
    public void showMontaje(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM montaje";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.printf("%-10s %-40s %-40s\n", 
            "Numero", "Nombre del montaje", "Descripcion");

                while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombreMontaje = resultado.getString("nombreMontaje");
                String descripcion = resultado.getString("descripcion");

                // Imprimir datos en forma de tabla
                System.out.printf("%-10d %-40s %-40s\n", 
                                numero, nombreMontaje, descripcion);
                }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
