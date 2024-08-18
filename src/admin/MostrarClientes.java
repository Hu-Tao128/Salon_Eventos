package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
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

                while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombreFiscal = resultado.getString("nombreFiscal");
                String nomContacto = resultado.getString("nomContacto");
                String primerApellido = resultado.getString("primerApellido");
                String segundoApellido = resultado.getString("segundoApellido");
                String numTel = resultado.getString("numTel");
                String email = resultado.getString("email");
                

                System.out.println("\n========================================================================");
                System.out.printf("| %-25s | %-40d |\n", "Cliente", numero);
                System.out.printf("| %-25s | %-40s |\n", "Nombre Fiscal", nombreFiscal);
                System.out.printf("| %-25s | %-40s |\n", "Nombre", nomContacto);
                System.out.printf("| %-25s | %-40s |\n", "Primer apellido", primerApellido);
                System.out.printf("| %-25s | %-40s |\n", "Segundo apellido", segundoApellido);
                System.out.printf("| %-25s | %-40s |\n", "Numero de telefono", numTel);
                System.out.printf("| %-25s | %-40s |\n", "Correo electronico", email);
                System.out.println("========================================================================");
                }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }

    public int elegirCliente() {
        Scanner Leer = new Scanner(System.in);
        int IDCliente = 0;

        do {
            System.out.println("Ingresar el número del cliente:");

            try {
                IDCliente = Leer.nextInt();
                if (IDCliente <= 0) {
                    System.out.println("El número del cliente debe ser un número positivo.");
                    IDCliente = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next();
            }

        } while (IDCliente == 0);

        return IDCliente;
    }

    public void mostrarReservacionesCliente(int IDCliente){
        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();

            String consulta = "select concat(c.nomContacto,' ',c.primerApellido,' ',\n" + //
                                "        ifnull(concat (c.segundoApellido,' '),' ')) as Cliente,\n" + //
                                "DATE_FORMAT(r.fechaReservacion, \"%d-%m-%y\") as \"Fecha de reservacion\",\n" + //
                                "s.nombre as Salon,\n" + //
                                "e.nombre as \"Tipo de evento\",\n" + //
                                "s.capacidad as \"Cantidad de invitados\"\n" + //
                                "from salon as s\n" + //
                                "inner join renta as r on s.numero = r.salon\n" + //
                                "inner join cliente as c on c.numero = r.cliente\n" + //
                                "inner join evento as e on e.numero = r.evento\n" + //
                                "where c.numero = ?";
            
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, IDCliente);
            ResultSet resultado = statement.executeQuery();

            boolean reservaciones = false;
            if (resultado.next()) {
                String nombreCliente = resultado.getString("Nombre del Cliente");
                System.out.println("\nCliente: " + nombreCliente);
                System.out.println("==============================================================================================================================");
                System.out.printf("| %-17s | %-22s | %-17s | %-32s | %-22s |\n", 
                                  "No. Reservación", "Fecha Reservación", "Nombre del Salón", "Tipo de Evento", "Cantidad de Invitados");
                System.out.println("==============================================================================================================================");
                
                do {
                    reservaciones = true;

                    int noRenta = resultado.getInt("No. Reservacion");
                    String fechaReservacion = resultado.getString("Fecha de reservacion");
                    String nombreSalon = resultado.getString("Salon");
                    String tipoEvento = resultado.getString("Tipo de evento");
                    int cantidadInvitados = resultado.getInt("Cantidad de invitados");

                    // Filas de datos
                    System.out.printf("| %-17d | %-22s | %-17s | %-32s | %-22d |\n", 
                                      noRenta, fechaReservacion, nombreSalon, tipoEvento, cantidadInvitados);
                } while (resultado.next());

                System.out.println("==============================================================================================================================");
            }

            if (!reservaciones) {
                System.out.println("|                                              No tienes reservaciones aún                                                   |");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
