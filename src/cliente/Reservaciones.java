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
        
        do{
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
                
                    System.out.println("\nIntroduzca el número de reservación para conocer más detalles o modificar su reservación.");
                    System.out.println("Introduzca 0 si desea salir de este apartado:");
        
                    try {

                        // Encabezados
                        System.out.println("==============================================================================================================================");
                        System.out.printf("| %-17s | %-22s | %-17s | %-32s | %-22s |\n", 
                                            "No. Reservación", "Fecha Reservación", "Nombre del Salón", "Tipo de Evento", "Cantidad de Invitados");
                                            System.out.println("==============================================================================================================================");
                    
                        while (resultado.next()) {
                            reservaciones = true;
                    
                            int noRenta = resultado.getInt("No. Reservacion");
                            String fechaReservacion = resultado.getString("Fecha de reservacion");
                            String nombreSalon = resultado.getString("Salon");
                            String tipoEvento = resultado.getString("Tipo de evento");
                            int cantidadInvitados = resultado.getInt("Cantidad de invitados");
                    
                            // Filas de datos
                            System.out.printf("| %-17d | %-22s | %-17s | %-32s | %-22d |\n", 
                                                noRenta, fechaReservacion, nombreSalon, tipoEvento, cantidadInvitados);
                        }
                    
                        if (!reservaciones) {
                            System.out.println("|                                              No tienes reservaciones aún                                                   |");
                        } else {
                            System.out.println("==============================================================================================================================");
                                
                            IDRenta = Leer.nextInt();
                            if (IDRenta < 0) {
                                System.out.println("El número debe ser un número positivo.");
                            } else if (IDRenta > 0) {
                                verDetallesRenta(IDRenta, NoCliente);
                            }
                        }
                        
                    } catch (InputMismatchException e) {
                        System.out.println("Ingrese números por favor.");
                        Leer.next();
                    }
        
            } catch (SQLException e) {
                e.printStackTrace();
            }
                                            
            } catch (SQLException e) {
                System.out.println("Error en la consulta: " + e.getMessage());
            }
        } while (IDRenta != 0);
    }

    public void verDetallesRenta(int IDRenta, int NoCliente) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
    
            String consultaRenta = "SELECT " + 
                                   "   DATE_FORMAT(r.fechaReservacion, '%d-%m-%y') AS 'Fecha de reservacion', " +
                                   "   DATE_FORMAT(r.horaInicio, '%H:%i') AS HoraReservacion, " + 
                                   "   e.numero AS evento, " +
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
    
                int IDEvento = 0; // Inicializa aquí, para tener acceso fuera del bucle

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

                    IDEvento = resultado.getInt("evento"); // Aquí dentro del bucle

                    // Asumiendo que la dirección está en el formato "Calle Número, Colonia"
                    String[] direccionPartes = direccion.split(",\\s*");
                    String calleNumero = direccionPartes[0];  // "Calle Número"
                    String colonia = direccionPartes.length > 1 ? direccionPartes[1] : ""; // "Colonia"

                    System.out.println("Detalles de la Renta:");
                    System.out.println("========================================================================");
                    System.out.printf("| %-25s | %-40s |\n", "Nombre de la Columna", "Valor");
                    System.out.println("========================================================================");
                    System.out.printf("| %-25s | %-40s |\n", "Fecha de Reservación", fechaReservacion);
                    System.out.printf("| %-25s | %-40s |\n", "Hora de Reservación", horaReservacion);
                    System.out.printf("| %-25s | %-40s |\n", "Tipo de Evento", tipoEvento);
                    System.out.printf("| %-25s | %-40s |\n", "Salón", salon);
                    System.out.printf("| %-25s | %-40s |\n", "Domicilio", "Calle:");
                    System.out.printf("| %-25s | %-40s |\n", "", calleNumero);
                    System.out.printf("| %-25s | %-40s |\n", "", "Colonia:");
                    System.out.printf("| %-25s | %-40s |\n", "", colonia);
                    System.out.printf("| %-25s | %-40s |\n", "Tipo de Montaje", montaje);
                    System.out.printf("| %-25s | %-40d |\n", "Cantidad de Invitados", cantInvitados);
                    System.out.printf("| %-25s | %-40.2f |\n", "Costo Total", costoTotal);
                    System.out.println("========================================================================");
                }

                if (!valid) {
                    System.out.println("No encontramos datos de la renta o el número de la renta no te pertenece.");
                } else {
                    System.out.println("");
                    AgregarComplementos complementos = new AgregarComplementos();
                    complementos.menuComplementos(IDRenta, IDEvento);
                }

                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }

    public float getTotal(int IDRenta) {
        String consultaTotal = "SELECT total FROM renta WHERE numero = ?";
        float total = 0f;
    
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(consultaTotal)) {
    
            statement.setInt(1, IDRenta);
    
            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    total = resultado.getFloat("total");
                } else {
                    System.out.println("No se encontró la renta con ID: " + IDRenta);
                }
            }
    
        } catch (SQLException e) {
            System.out.println("Error al obtener el total de la renta: " + e.getMessage());
        }
    
        return total;
    }
    
}
