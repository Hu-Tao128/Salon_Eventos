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
            
            System.out.printf("%-10s %-20s %-10s %-10s %-10s %-20s %-20s %-10s\n", 
            "Numero", "Nombre", "Capacidad", "Tamaño", "Precio", "Colonia", "Calle", "Numero");

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

                // Imprimir datos en forma de tabla
                System.out.printf("%-10d %-20s %-10d %-10.2f %-10d %-20s %-20s %-10s\n", 
                                numero, nombre, capacidad, tamanio, precio, dirColonia, dirCalle, dirNumero);
                }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
