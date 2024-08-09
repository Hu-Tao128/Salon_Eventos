package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    private int opcion = 0;
    private int metodoPago = 0;

    private float IVA = 0f;
    private float subtotal = 0f;
    private float total = 0f;

    private float monto = 0f;

    public void reservacion(int NoCliente) {
        Scanner Leer = new Scanner(System.in);
        MostrarSalones salones = new MostrarSalones();
        MostrarEventos eventos = new MostrarEventos();
        AgregarComplementos complementos = new AgregarComplementos();

        Connection conexion = null;

        System.out.println("Bienvenido al sistema de Renta de Salones Salent, Fecha: " + fechaHoy);

        do {
            System.out.println("Desea realizar una renta de salón? (s/n)");
            String respuesta = Leer.next();
            Leer.nextLine();

            if (!respuesta.equals("s")) {
                break;
            }
            do {
                System.out.println("Primero elija un salón de su agrado:");
                salones.showSalones();
                IDSalon = salones.elegirSalon();

                if (IDSalon == 0) {
                    System.out.println("No ha seleccionado un salón válido.");
                    continue; 
                }

                total = salones.obtenerPrecioSalon(IDSalon);

                System.out.println("Ahora elija su evento");
                eventos.showEventos();
                IDEvento = eventos.elegirEvento();

                System.out.println("Qué tipo de montaje requiere?");
                MostrarMontajes montajes = new MostrarMontajes();
                montajes.showMontajes(IDEvento);
                IDMontaje = montajes.elegirMontaje();


                salones.mostrarFechasNoPermitidas(IDSalon);

                while (true) {
                    System.out.println("Ingrese la fecha en la que desea su evento (dd-MM-yy): ");
                    try {
                        String input = Leer.nextLine();
                        LocalDate date = LocalDate.parse(input, formatter);
                        fechaInicio = date.format(formatter);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Fecha inválida. Por favor, ingrese la fecha en el formato dd-MM-yy: ");
                    }
                }

                while (true) {
                    System.out.println("Ingrese la fecha en la que desea terminar su evento (dd-MM-yy): ");
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
                    subtotal = (total/(1*0.16f));
                    IVA = total - subtotal;
                    
                    conexion = ConexionBD.obtenerConexion();
                    String sql = "INSERT INTO renta(fechaReservacion, fechaInicio, fechaFinal, horaInicio, horaFinal, IVA, subtotal, total, montaje, salon, cliente, evento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    statement.setString(1, fechaReservacion);
                    statement.setString(2, fechaInicio);
                    statement.setString(3, fechaFinal);
                    statement.setString(4, horaInicio);
                    statement.setString(5, horaFinal);
                    statement.setFloat(6, IVA);
                    statement.setFloat(7, subtotal);
                    statement.setFloat(8, total);
                    statement.setInt(9, IDMontaje);
                    statement.setInt(10, IDSalon);
                    statement.setInt(11, NoCliente);
                    statement.setInt(12, IDEvento);

                    int filasAfectadas = statement.executeUpdate();
                    if (filasAfectadas > 0) {
                        ResultSet generatedKeys = statement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int IDRenta = generatedKeys.getInt(1);
                            System.out.println("Reservación realizada con éxito.");

                            complementos.menuComplementos(IDRenta, IDEvento, total);

                            boolean pagoCompletado = false;
                            do {
                                System.out.println("El total de su reservacion es: " + total);
                                System.out.println("Cual es su metodo de pago?");
                                System.out.println("1) Efectivo");
                                System.out.println("2) Tarjeta");
                                System.out.println("0) Salir, no puedo pagar");
                                metodoPago = Leer.nextInt();

                                pagos metodoPagos = new pagos();

                                switch (metodoPago) {
                                    case 1:
                                        monto = metodoPagos.Efectivo(total, IDRenta);
                                        if (monto > 0) {
                                            pagoCompletado = true;
                                        } else {
                                            metodoPago = 0;
                                        }
                                        break;
                                    case 2:
                                        monto = metodoPagos.Tarjeta(total, IDRenta);
                                        if (monto > 0) {
                                            pagoCompletado = true;
                                        } else {
                                            metodoPago = 0;
                                        }
                                        break;
                                    case 0:
                                        System.out.println("Hasta luego.");
                                        break;
                                    default:
                                        System.out.println("Esta opción no está en el menú.");
                                        break;
                                }
                            } while (!pagoCompletado && metodoPago != 0);

                            if (metodoPago == 0 || monto == 0) {
                                pagos eliminar = new pagos();
                                eliminar.EliminarRenta(IDRenta);
                            }
                        }
                    } else {
                        System.out.println("No se pudo realizar la reservación.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error al realizar la reservación: " + e.getMessage());
                }
            } while (IDSalon == 0);
        } while (opcion != 0);

    }
}
