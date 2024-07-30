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
            
            System.out.printf("%-20s %-10s %-10s %-10s\n", 
            "Equipamiento", "Renta", "Cantidad", "Importe");

                while (resultado.next()) {
                // Obtener datos
                int equipamiento = resultado.getInt("equipamiento");
                int renta = resultado.getInt("renta");
                double cantidad = resultado.getDouble("cantidad");
                double importe = resultado.getDouble("importe");

                // Imprimir datos en forma de tabla
                System.out.printf("%-20d %-10d %-10.0f %-10.0f\n", 
                    equipamiento, renta, cantidad, importe);
                }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
