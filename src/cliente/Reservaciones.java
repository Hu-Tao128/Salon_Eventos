package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class Reservaciones {

    Scanner Leer = new Scanner(System.in);
    private int opcion;
    private int IDRenta;

    public void verMisReservaciones(int NoCliente) {
        System.out.println("Aquí se muestran las reservaciones que tiene registradas");

        try (Connection conexion = ConexionBD.obtenerConexion()) {

            String consultaReservaciones = "SELECT " + 
                                           "   r.numero AS 'No. Reservacion', " + 
                                           "   DATE_FORMAT(r.fechaReservacion, '%d-%m-%y') AS 'Fecha de reservacion', " +
                                           "   s.nombre AS Salon, " +
                                           "   e.nombre AS 'Tipo de evento', " +
                                           "   s.capacidad AS 'Cantidad de invitados' " +
                                           "FROM salon AS s " +
                                           "INNER JOIN renta AS r ON s.numero = r.salon " +
                                           "INNER JOIN cliente AS c ON c.numero = r.cliente " +
                                           "INNER JOIN evento AS e ON e.numero = r.evento " +
                                           "WHERE c.numero = ?";

            try (PreparedStatement statement = conexion.prepareStatement(consultaReservaciones)) {
                statement.setInt(1, NoCliente);
                ResultSet resultado = statement.executeQuery();

                boolean reservaciones = false;

                // Encabezados
                System.out.println("======================================================================================================");
                System.out.printf("| %-15s | %-20s | %-15s | %-30s | %-20s |\n", 
                                    "No. Reservación", "Fecha Reservación", "Nombre del Salón", "Tipo de Evento", "Cantidad de Invitados");
                System.out.println("======================================================================================================");

                while (resultado.next()) {
                    reservaciones = true;

                    int noRenta = resultado.getInt("No. Reservacion");
                    String fechaReservacion = resultado.getString("Fecha de reservacion");
                    String nombreSalon = resultado.getString("Salon");
                    String tipoEvento = resultado.getString("Tipo de evento");
                    int cantidadInvitados = resultado.getInt("Cantidad de invitados");

                    // Filas de datos
                    System.out.printf("| %-15d | %-20s | %-15s | %-30s | %-20d |\n", 
                                        noRenta, fechaReservacion, nombreSalon, tipoEvento, cantidadInvitados);
                }

                if (!reservaciones) {
                    System.out.println("|                                 No tienes reservaciones aún                                              |");
                }else{
                    System.out.println("Introduzca el numero de reservacion para conocer mas detalles o modificar su reservacion");
                    do {
                        System.out.println("Introdusca 0 si desea salir de este apartado");

                        try {
                            IDRenta = Leer.nextInt();
                            if (IDRenta <= 0) {
                                System.out.println("El número debe ser un número positivo.");
                                IDRenta = 0;
                            }else{
                                verDetallesRenta(IDRenta, NoCliente);
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Ingrese números por favor.");
                            Leer.next();
                        }

                    } while (IDRenta != 0);
                }

                System.out.println("======================================================================================================");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }

    public void verDetallesRenta(int IDRenta, int NoCliente) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {

            String consultaRenta = "SELECT " + 
                                   "   DATE_FORMAT(r.fechaReservacion, '%d-%m-%y') AS 'Fecha de reservacion', " +
                                   "   DATE_FORMAT(r.horaInicio, '%H:%i') AS HoraReservacion, " + 
                                   "   e.nombre AS 'Tipo de evento', " +
                                   "   s.nombre AS Salon, " +
                                   "   CONCAT(s.dirCalle, ' ', s.dirNumero, ', ', s.dirColonia) AS Direccion, " +
                                   "   m.nombreMontaje AS TipoMontaje, " +
                                   "   s.capacidad AS 'Cantidad de invitados', " +
                                   "   r.total AS 'Costo Total' " +
                                   "FROM salon AS s " +
                                   "INNER JOIN renta AS r ON s.numero = r.salon " +
                                   "INNER JOIN cliente AS c ON c.numero = r.cliente " +
                                   "INNER JOIN evento AS e ON e.numero = r.evento " +
                                   "INNER JOIN montaje AS m ON r.montaje = m.numero " +
                                   "WHERE c.numero = ? AND r.numero = ?";

            try (PreparedStatement statement = conexion.prepareStatement(consultaRenta)) {
                statement.setInt(1, NoCliente);
                statement.setInt(2, IDRenta);
                ResultSet resultado = statement.executeQuery();
                boolean valid = false;

                while (resultado.next()) {
                    valid = true;

                    String fechaReservacion = resultado.getString("Fecha de reservacion");
                    String horaReservacion = resultado.getString("HoraReservacion");
                    String tipoEvento = resultado.getString("Tipo de evento");
                    String salon = resultado.getString("Salon");
                    String direccion = resultado.getString("Direccion");
                    String montaje = resultado.getString("TipoMontaje");
                    int cantInvitados = resultado.getInt("Cantidad de invitados");
                    double costoTotal = resultado.getDouble("Costo Total");

                    // Mostrar detalles
                    System.out.println("Detalles de la Renta:");
                    System.out.println("Fecha de Reservación: " + fechaReservacion);
                    System.out.println("Hora de Reservación: " + horaReservacion);
                    System.out.println("Tipo de Evento: " + tipoEvento);
                    System.out.println("Salón: " + salon);
                    System.out.println("Dirección: " + direccion);
                    System.out.println("Tipo de Montaje: " + montaje);
                    System.out.println("Cantidad de Invitados: " + cantInvitados);
                    System.out.println("Costo Total: " + costoTotal);
                }

                if (!valid) {
                    System.out.println("No encontramos datos de la renta o el número de la renta no te pertenece.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }
}
