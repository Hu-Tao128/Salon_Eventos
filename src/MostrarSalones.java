import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostrarSalones {
    public void showSalones() {
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM salon";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            // Iterar sobre el resultado
            while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombre = resultado.getString("nombre");
                int capacidad = resultado.getInt("capacidad");
                double tamanio = resultado.getDouble("tamanio");
                int precio = resultado.getInt("precio");
                String dirColonia = resultado.getString("dirColonia");
                String dirCalle = resultado.getString("dirCalle");
                String dirNumero = resultado.getString("dirNumero");
                System.out.println("Numero: " + numero + ", Nombre: " + nombre + ", Capacidad: " + capacidad + ", Tamanio: " + tamanio + ", Precio: " + precio);
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
