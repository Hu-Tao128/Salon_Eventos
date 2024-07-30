package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class rentaSalon {
    LocalDate fechaHoy = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private String fechaReservacion = fechaHoy.format(formatter);
    private String fechaInicio;
    private String fechaFinal;
    private String horaInicio;
    private String horaFinal;

    private int IDSalon;
    private int IDEvento;
    private int IDMontaje;
    private int IDServicios;
    private int IDEquipamientos;
    private int opcion = 0;

    private float IVA = 0f;
    private float subtotal = 0f;
    private float total = 0f;

    public void reservacion(int NoCliente){
        Scanner Leer = new Scanner(System.in);
        MostrarSalones salones = new MostrarSalones();
        MostrarEventos eventos = new MostrarEventos();
        MostrarMontajes montajes = new MostrarMontajes();
        MostrarServicios servicios = new MostrarServicios();
        MostrarEquipamientos equipamiento = new MostrarEquipamientos();

        Connection conexion = null;
        
        System.out.println("Bienvenido al sistema de Renta de Salones Salent, Fecha: " + fechaHoy);

        do {

            System.out.println("Desea reaalizar una renta de salon? (s/n)");
            String respuesta = Leer.next();

            if (!respuesta.equals("s")) {
                break;   
            }
            do {
                System.out.println("Primero Elija un salon de su agrado:");
                salones.showSalones();
                IDSalon = salones.elegirSalon();

                System.out.println("Ahora elija su evento");
                eventos.showEventos();
                IDEvento = eventos.elegirEvento();

                System.out.println("Que tipo de montaje requiere?");
                montajes.showMontajes(IDEvento);
                IDMontaje = montajes.elegirMontaje();

                System.out.println("Ingrese la fecha en la que desea su evento (dd-MM-yy): ");
                while (true) {
                    try {
                        String input = Leer.nextLine();
                        LocalDate date = LocalDate.parse(input, formatter);
                        fechaInicio = date.format(formatter);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Fecha inválida. Por favor, ingrese la fecha en el formato dd-MM-yy: ");
                    }
                }

                System.out.println("Ingrese la fecha en la que desea y terminar su evento (dd-MM-yy): ");
                while (true) {
                    try {
                        String input = Leer.nextLine();
                        LocalDate date = LocalDate.parse(input, formatter);
                        fechaFinal = date.format(formatter);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Fecha inválida. Por favor, ingrese la fecha en el formato dd-MM-yy: ");
                    }
                }

                System.out.println("Ingrese la hora de inicio (HH:mm): ");
                while (true) {
                    try {
                        String input = Leer.nextLine();
                        LocalTime time = LocalTime.parse(input, inputFormatter);
                        horaInicio = time.format(outputFormatter);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Hora inválida. Por favor, ingrese la hora en el formato HH:mm: ");
                    }
                }

                System.out.println("Ingrese la hora final (HH:mm): ");
                while (true) {
                    try {
                        String input = Leer.nextLine();
                        LocalTime time = LocalTime.parse(input, inputFormatter);
                        horaFinal = time.format(outputFormatter);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Hora inválida. Por favor, ingrese la hora en el formato HH:mm: ");
                    }
                }

                try {
                
                    subtotal = salones.obtenerPrecioSalon(IDSalon);
                    IVA = (subtotal*1.16f);
                    total = (IVA + subtotal);

                    conexion = ConexionBD.obtenerConexion();
                    String sql = "INSERT INTO renta (fechaReservacion, fechaInicio, fechaFinal, horaInicio, horaFinal, cliente, salon, evento, montaje, IVA, subtotal, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = conexion.prepareStatement(sql);
                    statement.setString(1, fechaReservacion);
                    statement.setString(2, fechaInicio);
                    statement.setString(3, fechaFinal);
                    statement.setString(4, horaInicio);
                    statement.setString(5, horaFinal);
                    statement.setInt(6, NoCliente);
                    statement.setInt(7, IDSalon);
                    statement.setInt(8, IDEvento);
                    statement.setInt(9, IDMontaje);
                    statement.setFloat(10, IVA);
                    statement.setFloat(11, subtotal);
                    statement.setFloat(12, total);
    
                    int filasAfectadas = statement.executeUpdate();
                    if (filasAfectadas > 0) {
                        System.out.println("Reservación realizada con éxito.");
    
                        do {
                            System.out.println("Desea agregar algún Servicio o Equipamiento?");
                            System.out.println("1) Servicio");
                            System.out.println("2) Equipamiento");
                            System.out.println("0) No, gracias");
                            opcion = Leer.nextInt();
                            Leer.nextLine(); 
            
                            switch (opcion) {
                                case 1:
                                    servicios.showServicios();
                                    IDServicios = servicios.elegirServicio();
                                    break;
                                case 2:
                                    equipamiento.showEquipamientos(IDEvento);
                                    IDEquipamientos = equipamiento.elegirEquipamiento();
                                    break;
                                case 0:
                                    System.out.println("Esta bien, prosigamos");
                                    break;
                                default:
                                    System.out.println("Opción no válida, intente de nuevo.");
                                    break;
                            }
                        } while (opcion != 0);

                    } else {
                        System.out.println("No se pudo realizar la reservación.");
                    }
    
                } catch (SQLException e) {
                    System.out.println("Error al realizar la reservación: " + e.getMessage());
                } finally {
                    ConexionBD.cerrarConexion(conexion);
                }

            } while (IDSalon == 0);
        } while (opcion != 0);
        
    }
}
