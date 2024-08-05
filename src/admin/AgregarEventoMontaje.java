package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import cliente.MostrarEventos;
import conexionDB.ConexionBD;

public class AgregarEventoMontaje {
    private int IDMontaje;
    private int IDEvento;
    public void agregarEventoMontajeF(){
        Connection connection = null;

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String nombreMontaje = "";   
        String descripcion = "";

        MostrarMontaje montaje = new MostrarMontaje();
        MostrarEventos evento = new MostrarEventos();

        System.out.println("Registro de montaje");

        do {
           
            System.out.println("Eliga el evento");
            evento.showEventos();
            IDEvento = evento.elegirEvento();

            System.out.println("Elija el montaje a agregar al evento");
            montaje.showMontaje();
            IDMontaje = montaje.elegirMontaje();

            System.out.println("Escogio bien los datos? (s/n)");
            opcion = datos.next();

            if(opcion.equals("s")){
                opcion2 = "s";
            }
            
        } while (!opcion2.equals("s"));

        try {

            connection = ConexionBD.obtenerConexion();
            
            String agregarUsuario = "INSERT INTO montaje_evento (evento, montaje) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(agregarUsuario);
            statement.setInt(1, IDEvento);
            statement.setInt(2, IDMontaje);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Los datos se han insertado correctamente en la tabla montaje_evento.");
            } else {
                System.out.println("No se pudo insertar los datos en la tabla montaje_evento.");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
        }finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(connection);
        }
    }
}
