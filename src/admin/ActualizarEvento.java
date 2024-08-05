package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import cliente.MostrarEventos;
import conexionDB.ConexionBD;

public class ActualizarEvento {
    private int IDEvento;

    public void updateEvento(){
        Connection connection = null;

        MostrarEventos evento = new MostrarEventos();

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String opcion3 = "";
        String nombre = "";      

        System.out.println("Modificacion de un evento");

        System.out.println("Seleccione el evento que sera modificado");
        evento.showEventos();
        IDEvento = evento.elegirEvento();

        System.out.println("Esta seguro de querer actualizar toda la informacion del evento numero " + IDEvento + " (s/n)");
        opcion3 = datos.nextLine();

        if(opcion3.equals("s")){
            do {
           
                System.out.println("Actualice el nombre del evento");
                nombre = datos.nextLine();
    
                System.out.println("Escribio bien los datos? (s/n)");
                opcion = datos.next();
    
                if(opcion.equals("s")){
                    opcion2 = "s";
                }
                
            } while (!opcion2.equals("s"));
    
            try {
    
                connection = ConexionBD.obtenerConexion();
                
                String updateEvento = "UPDATE evento SET nombre = ? WHERE numero = ?";
                PreparedStatement statement = connection.prepareStatement(updateEvento);
                statement.setString(1, nombre);
                statement.setInt(2, IDEvento);
 
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han actualizado correctamente en la tabla evento.");
                } else {
                    System.out.println("No se pudo acctualizar los datos en la tabla evento.");
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