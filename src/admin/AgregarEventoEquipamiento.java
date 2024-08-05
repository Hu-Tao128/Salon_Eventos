package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import cliente.MostrarEventos;
import conexionDB.ConexionBD;

public class AgregarEventoEquipamiento {

    private int IDEvento;
    private int IDEquipamiento;

    public void agregarEventoEquipamientoF(){
        Connection connection = null;

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String nombreMontaje = "";   
        String descripcion = "";

        MostrarEventos evento = new MostrarEventos();
        MostrarEquipamiento equipamiento = new MostrarEquipamiento();

        System.out.println("Registro de montaje");

        do {
           
            System.out.println("Eliga el evento");
            evento.showEventos();
            IDEvento = evento.elegirEvento();

            System.out.println("Elija el montaje a agregar al evento");
            equipamiento.showEquipamiento();
            IDEquipamiento = equipamiento.elegirEquipamiento();

            System.out.println("Escogio bien los datos? (s/n)");
            opcion = datos.next();

            if(opcion.equals("s")){
                opcion2 = "s";
            }
            
        } while (!opcion2.equals("s"));

        try {

            connection = ConexionBD.obtenerConexion();
            
            String agregarUsuario = "INSERT INTO equipos_evento (evento, equipamiento) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(agregarUsuario);
            statement.setInt(1, IDEvento);
            statement.setInt(2, IDEquipamiento);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Los datos se han insertado correctamente en la tabla evento_equipamiento.");
            } else {
                System.out.println("No se pudo insertar los datos en la tabla evento_equipamiento.");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
        }finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(connection);
        }
    }
}
