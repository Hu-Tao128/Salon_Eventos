package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class AgregarServicios {
    public void agregarServiciosF(){
        Connection connection = null;

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String nombreServicio = "";   
        String descripcion = "";
        double precio = 0.0;
        int disponibilidad = 0;

        System.out.println("Registro de servicio");

        do {
           
            System.out.println("Ingrese el nombre del servicio");
            nombreServicio = datos.nextLine();
            
            System.out.println("Ingrese la descripcion del servicio");
            descripcion = datos.nextLine();

            System.out.println("Ingrese el precio del servicio");
            precio = datos.nextDouble();

            System.out.println("Ingrese si esta disponible o no (1/0)");
            disponibilidad = datos.nextInt();

            System.out.println("Escribio bien los datos? (s/n)");
            opcion = datos.next();

            if(opcion.equals("s")){
                opcion2 = "s";
            }
            
        } while (!opcion2.equals("s"));

        try {

            connection = ConexionBD.obtenerConexion();
            
            String agregarUsuario = "INSERT INTO servicios (numero, nombreServicio, descripcion, precio, disponibilidad) VALUES (null, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(agregarUsuario);
            statement.setString(1, nombreServicio);
            statement.setString(2, descripcion);
            statement.setDouble(3, precio);
            statement.setInt(4, disponibilidad);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Los datos se han insertado correctamente en la tabla servicio.");
            } else {
                System.out.println("No se pudo insertar los datos en la tabla servicio.");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
        }finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(connection);
        }
    }
}
