package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexionDB.ConexionBD;
public class MostrarClientes {
    public void showClientes(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM cliente";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.printf("%-10s %-30s %-40s %-40s %-30s %-20s %-20s\n", 
            "Numero", "Nombre Fiscal", "Nombre", "Apellido Paterno", "Apellido Materno", "Numero Celular", "Email");

                while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombreFiscal = resultado.getString("nombreFiscal");
                String nomContacto = resultado.getString("nomContacto");
                String primerApellido = resultado.getString("primerApellido");
                String segundoApellido = resultado.getString("segundoApellido");
                String numTel = resultado.getString("numTel");
                String email = resultado.getString("email");
                

                // Imprimir datos en forma de tabla
                System.out.printf("%-10d %-30s %-40s %-40s %-30s %-20s %-20s\n", 
                                numero, nombreFiscal, nomContacto, primerApellido, segundoApellido, numTel, email);
                }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
