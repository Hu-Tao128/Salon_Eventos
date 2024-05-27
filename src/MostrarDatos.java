import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostrarDatos {
    public static void main(String[] args) {
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM salones";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            // Iterar sobre el resultado
            while (resultado.next()) {
                // Obtener datos
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                Double precio = resultado.getDouble("precio");
                int Patrones = resultado.getInt("acomodo");
                // Hacer algo con los datos, por ejemplo, imprimirlos
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Precio por Noche: " + precio + ", Patron de mesa: " + Patrones);
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
