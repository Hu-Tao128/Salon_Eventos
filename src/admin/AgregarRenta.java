package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import cliente.AgregarComplementos;
import cliente.MostrarEventos;
import cliente.MostrarMontajes;
import cliente.MostrarSalones;
import conexionDB.ConexionBD;

public class AgregarRenta {

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
    private int IDCliente;
    private int opcion = 0;

    private float IVA = 0f;
    private float subtotal = 0f;
    private float total = 0f;

    public void agregarRentaF(){
        
        Scanner Leer = new Scanner(System.in);
        MostrarSalones salones = new MostrarSalones();
        MostrarEventos eventos = new MostrarEventos();
        MostrarMontajes montajes = new MostrarMontajes(); 
        MostrarClientes clientes = new MostrarClientes();
        AgregarComplementos complementos = new AgregarComplementos();

        Connection conexion = null;

        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;
        
        System.out.println("Bienvenido al sistema de Renta de Salones Salent, Fecha: " + fechaHoy);

         do {
                System.out.println("Eliga al cliente al que se le asignara la renta");
                clientes.showClientes();
                IDCliente = clientes.elegirCliente();

                System.out.println("Elija un salon para el cliente:");
                salones.showSalones();
                IDSalon = salones.elegirSalon();

                System.out.println("Elija el evento que pidio el cliente");
                eventos.showEventos();
                IDEvento = eventos.elegirEvento();

                System.out.println("Que tipo de montaje requiere?");
                montajes.showMontajes(IDEvento);
                IDMontaje = montajes.elegirMontaje();

                System.out.println("Ingrese la fecha en la que desea realizar el evento (dd-MM-yy): ");
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

                System.out.println("Ingrese la fecha en la que va a terminar el evento (dd-MM-yy): ");
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
                    String sql = "INSERT INTO renta (numero, fechaReservacion, fechaInicio, fechaFinal, horaInicio, horaFinal, cliente, salon, evento, montaje, IVA, subtotal, total) VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                    statement.setString(1, fechaReservacion);
                    statement.setString(2, fechaInicio);
                    statement.setString(3, fechaFinal);
                    statement.setString(4, horaInicio);
                    statement.setString(5, horaFinal);
                    statement.setInt(6, IDCliente);
                    statement.setInt(7, IDSalon);
                    statement.setInt(8, IDEvento);
                    statement.setInt(9, IDMontaje);
                    statement.setFloat(10, IVA);
                    statement.setFloat(11, subtotal);
                    statement.setFloat(12, total);
    
                    int filasAfectadas = statement.executeUpdate();
                    if (filasAfectadas > 0) {
                        ResultSet generatedKeys = statement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            
                            int IDRenta = generatedKeys.getInt(1);
                            System.out.println("Reservación realizada con éxito.");

                            complementos.menuComplementos(IDRenta, IDEvento);
                        }
                    } else {
                        System.out.println("No se pudo realizar la reservación.");
                    }
    
                } catch (SQLException e) {
                    System.out.println("Error al realizar la reservación: " + e.getMessage());
                } finally {
                    ConexionBD.cerrarConexion(conexion);
                }

            } while (IDSalon == 0);
    }
}

