package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import cliente.MostrarServicios;
import conexionDB.ConexionBD;

public class ActualizarServicios {
    private int IDServicio;

    public void updateSalon(){
        Connection connection = null;

        MostrarServicios servicio = new MostrarServicios();

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String opcion3 = "";
        String nombreServicio = "";      
        String descripcion = "";
        int precio = 0;
        int disponibilidad = 0;

        System.out.println("Modificacion de un servicio");

        System.out.println("Seleccione el servicio que sera modificado");
        servicio.showServicios();
        IDServicio = servicio.elegirServicio();

        System.out.println("Esta seguro de querer actualizar toda la informacion del salon numero " + IDServicio + " (s/n)");
        opcion3 = datos.nextLine();

        if(opcion3.equals("s")){
            do {
           
                System.out.println("Actualice el nombre del servicio");
                nombreServicio = datos.nextLine();

                System.out.println("Actualice la descripcion del servicio");
                descripcion = datos.nextLine();

                System.out.println("Actualice el precio del servicio");
                precio = datos.nextInt();

                System.out.println("Actualice la disponibilidad del servicios (1=disponible / 0=no disponible)");
                disponibilidad = datos.nextInt();
    
                System.out.println("Escribio bien los datos? (s/n)");
                opcion = datos.next();
    
                if(opcion.equals("s")){
                    opcion2 = "s";
                }
                
            } while (!opcion2.equals("s"));
    
            try {
    
                connection = ConexionBD.obtenerConexion();
                
                String updateServicio = "UPDATE servicios SET nombreServicio = ?, descripcion = ?, precio = ?, disponibilidad = ? WHERE numero = ?";
                PreparedStatement statement = connection.prepareStatement(updateServicio);
                statement.setString(1, nombreServicio);
                statement.setString(2, descripcion);
                statement.setInt(3, precio);
                statement.setInt(4, disponibilidad);
                statement.setInt(5, IDServicio);
 
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han actualizado correctamente en la tabla servicio.");
                } else {
                    System.out.println("No se pudo acctualizar los datos en la tabla servicio.");
                }
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
            }finally {
                // Cerrar la conexión
                ConexionBD.cerrarConexion(connection);
            }
        }
    }
}
