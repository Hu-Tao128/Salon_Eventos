package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexionDB.ConexionBD;

public class MostrarEquipamiento {
    public void showEquipamiento(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM equipamiento";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.printf("%-10s %-40s %-30s %-40s\n", 
            "Numero", "Descripcion", "Precio", "Stock");

                while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String descripcion = resultado.getString("descripcion");
                double precio = resultado.getDouble("precio");
                int stock = resultado.getInt("stock");

                // Imprimir datos en forma de tabla
                System.out.printf("%-10d %-40s %-30.0f %-20d\n", 
                                numero, descripcion, precio, stock);
                }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
