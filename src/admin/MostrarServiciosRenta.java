package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexionDB.ConexionBD;

public class MostrarServiciosRenta {
    public void showServiciosRenta(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM servicios_renta";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.printf("%-10s %-30s\n", 
            "Servicio", "Renta");

                while (resultado.next()) {
                // Obtener datos
                int servicios = resultado.getInt("servicios");
                int renta = resultado.getInt("renta");

                // Imprimir datos en forma de tabla
                System.out.printf("%-10d %-10d\n", 
                                servicios, renta);
                }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
