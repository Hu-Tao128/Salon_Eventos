package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class AgregarEvento {
    public void agregarEventoF(){
        Connection connection = null;

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String nombre = "";

        System.out.println("Registro de equipamiento");

        do {
           
            System.out.println("Ingrese el nombre del evento");
            nombre = datos.nextLine();

            System.out.println("Escribio bien los datos? (s/n)");
            opcion = datos.next();

            if(opcion.equals("s")){
                opcion2 = "s";
            }
            
        } while (!opcion2.equals("s"));

        try {

            connection = ConexionBD.obtenerConexion();
            
            String agregarUsuario = "INSERT INTO evento (numero, nombre) VALUES (null, ?)";
            PreparedStatement statement = connection.prepareStatement(agregarUsuario);
            statement.setString(1, nombre);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Los datos se han insertado correctamente en la tabla evento.");
            } else {
                System.out.println("No se pudo insertar los datos en la tabla evento.");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
        }finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(connection);
        }
    }
}
