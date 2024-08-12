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
    DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");
    DateTimeFormatter dbDateFormatter = DateTimeFormatter.ofPattern("yy-MM-dd");
    DateTimeFormatter inputTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    DateTimeFormatter outputTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private String fechaReservacion = fechaHoy.format(dbDateFormatter);
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
    private int invitados;

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
                System.out.println("Para cuantos invitados quiere su Reservacion?");

                try {
                    invitados = Leer.nextInt();
                    if (invitados > 500) {
                        System.out.println(invitados + " invitados excede la capacidad de nuestros salones, pruebe con 500 o menos invitados");
                        invitados = -1;
                    }
                } catch (Exception e) {
                    System.out.println("Ingresa numeros");
                    Leer.nextLine();
                    invitados = -1;
                }
            } while (invitados < 0);

            do {
                System.out.println("A continuacion elija un salón de su agrado:");
                salones.showSalonesRenta(invitados);
                IDSalon = salones.elegirSalonRenta();

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

                Leer.nextLine();

                salones.mostrarFechasNoPermitidas(IDSalon);

                while (true) {
                    System.out.println("Ingrese la fecha en la que desea su evento (dd-MM-yy): ");

                    try {
                        String input = Leer.nextLine();
                        salones.validarFecha(input, IDSalon);
                        LocalDate date = LocalDate.parse(input, inputDateFormatter);
                        fechaInicio = date.format(dbDateFormatter);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Fecha inválida. Por favor, ingrese la fecha en el formato dd-MM-yy: ");
                    }
                }

                while (true) {
                    System.out.println("Ingrese la fecha en la que desea terminar su evento (dd-MM-yy): ");
                    try {
                        String input = Leer.nextLine();
                        salones.validarFecha(input, IDSalon);
                        LocalDate date = LocalDate.parse(input, inputDateFormatter);
                        fechaFinal = date.format(dbDateFormatter);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Fecha inválida. Por favor, ingrese la fecha en el formato dd-MM-yy: ");
                    }
                }

                System.out.println("Ingrese la hora de inicio (HH:mm): ");
                while (true) {
                    try {
                        String input = Leer.nextLine();
                        if (input.equals("24:00")) {
                            System.out.println("Hora invalida, por favor ingrese de nuevo la hora");
                        } else {
                            LocalTime time = LocalTime.parse(input, inputTimeFormatter);
                            horaInicio = time.format(outputTimeFormatter);
                            break;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Hora inválida. Por favor, ingrese la hora en el formato HH:mm: ");
                    }
                }

                System.out.println("Ingrese la hora final (HH:mm): ");
                while (true) {
                    try {
                        String input = Leer.nextLine();
                        if (input.equals("24:00")) {
                            System.out.println("Hora invalida, por favor ingrese de nuevo la hora");
                        } else {
                            LocalTime time = LocalTime.parse(input, inputTimeFormatter);
                            horaFinal = time.format(outputTimeFormatter);
                            break;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Hora inválida. Por favor, ingrese la hora en el formato HH:mm: ");
                    }
                }

                System.out.println("La cantidad de invitados sera " + invitados + " ? (s/n)");
                String resInvitados = Leer.next();

                if (resInvitados.equals("n")) {
                    System.out.println("Ingrese la cantidad de Invitados por favor");
                    try {
                        invitados = Leer.nextInt();
                    } catch (Exception e) {
                        System.out.println("Ingrese un numero por favor");
                        Leer.nextLine();
                    }
                }

                try {
                    subtotal = (total / (1 * 0.16f));
                    IVA = total - subtotal;

                    conexion = ConexionBD.obtenerConexion();
                    String sql = "INSERT INTO renta(numero, fechaReservacion, fechaInicio, fechaFinal, horaInicio, horaFinal, invitados, IVA, subtotal, total, montaje, salon, cliente, evento) VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    statement.setString(1, fechaReservacion);
                    statement.setString(2, fechaInicio);
                    statement.setString(3, fechaFinal);
                    statement.setString(4, horaInicio);
                    statement.setString(5, horaFinal);
                    statement.setInt(6, invitados);
                    statement.setFloat(7, IVA);
                    statement.setFloat(8, subtotal);
                    statement.setFloat(9, total);
                    statement.setInt(10, IDMontaje);
                    statement.setInt(11, IDSalon);
                    statement.setInt(12, NoCliente);
                    statement.setInt(13, IDEvento);

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
