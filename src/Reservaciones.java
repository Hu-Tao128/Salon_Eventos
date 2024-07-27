import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.Scanner;

public class Reservaciones {
    public void verMisReservaciones(int NoCliente){
        System.out.println("Aqui se muestran las reservaciones que tiene registradas");

        Connection conexion = null;
        try {
            conexion = ConexionBD.obtenerConexion();
            
            String consultaReservaciones = "SELECT\n" +
                                            "    DATE_FORMAT(r.fechaReservacion, \"%y-%m-%d\") AS FechaReservacion,\n" +
                                            "    DATE_FORMAT(r.horaInicio, '%h:%i %p') AS HoraReservacion,\n" +
                                            "    CONCAT(c.nomContacto, ' ', c.primerApellido, ' ', IFNULL(c.segundoApellido, '')) AS NombreCliente,\n" +
                                            "    e.nombre AS TipoEvento,\n" +
                                            "    s.nombre AS NombreSalon,\n" +
                                            "    CONCAT(s.dirCalle, ' ', s.dirNumero, ', ', s.dirColonia) AS DireccionSalon,\n" +
                                            "    m.nombreMontaje AS TipoMontaje,\n" +
                                            "    s.capacidad AS CantidadInvitados,\n" +
                                            "    r.total AS CostoTotalEvento\n" +
                                            "FROM renta AS r\n" +
                                            "INNER JOIN cliente AS c ON r.cliente = c.numero\n" +
                                            "INNER JOIN evento AS e ON r.evento = e.numero\n" +
                                            "INNER JOIN salon AS s ON r.salon = s.numero\n" +
                                            "INNER JOIN montaje AS m ON r.montaje = m.numero\n" +
                                            "WHERE c.numero = ?;";
                        
            try (PreparedStatement statement = conexion.prepareStatement(consultaReservaciones)) {
                statement.setInt(1, NoCliente);
                ResultSet resultado = statement.executeQuery();

                while (resultado.next()) {
                    String fechaReservacion = resultado.getString("FechaReservacion");
                    String horaReservacion = resultado.getString("HoraReservacion");
                    String nombreCliente = resultado.getString("NombreCliente");
                    String tipoEvento = resultado.getString("TipoEvento");
                    String nombreSalon = resultado.getString("NombreSalon");
                    String direccionSalon = resultado.getString("DireccionSalon");
                    String tipoMontaje = resultado.getString("TipoMontaje");
                    int cantidadInvitados = resultado.getInt("CantidadInvitados");
                    float costoTotalEvento = resultado.getFloat("CostoTotalEvento");

                    System.out.println("\nFecha de Reservación: " + fechaReservacion);
                    System.out.println("Hora de Reservación: " + horaReservacion);
                    System.out.println("Nombre del Cliente: " + nombreCliente);
                    System.out.println("Tipo de Evento: " + tipoEvento);
                    System.out.println("Nombre del Salón: " + nombreSalon);
                    System.out.println("Dirección del Salón: " + direccionSalon);
                    System.out.println("Tipo de Montaje: " + tipoMontaje);
                    System.out.println("Cantidad de Invitados: " + cantidadInvitados);
                    System.out.println("Costo Total del Evento: " + costoTotalEvento + "\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }catch (Exception e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }
}
