package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/*  */
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

    private int IDSalon = 0;
    private int IDEvento = 0;
    private int IDMontaje = 0;
    private int IDServicios = 0;
    private int IDEquipamientos = 0;
    private int opcion = 0;

    public void reservacion(int NoCliente){
        Scanner Leer = new Scanner(System.in);
        MostrarSalones salones = new MostrarSalones();
        MostrarEventos eventos = new MostrarEventos();
        MostrarServicios servicios = new MostrarServicios();
        MostrarEquipamientos equipamiento = new MostrarEquipamientos();
        MostrarMontajes montajes = new MostrarMontajes();

        Connection conexion = null;
        
        System.out.println("Bienvenido al sistema de Renta de Salones Salent, Fecha " + fechaHoy);

        do {
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

                // Preguntar por la hora final
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
                
            } while (IDSalon == 0);

            do {//agregar servicios y/o equipamientos
                System.out.println("Desea agregar algún Servicio o Equipamiento?");
                System.out.println("1) Servicio");
                System.out.println("2) Equipamiento");
                System.out.println("0) No, gracias");
                opcion = Leer.nextInt();
    
                switch (opcion) {
                    case 1:
                        servicios.showServicios();
                        break;
                    case 2:
                        equipamiento.showEquipamientos(IDEvento);
                        break;
                
                    default:
                        System.out.println("Esta bien, prosigamos");
                        break;
                }
            } while (opcion != 0); //se va a mover al final para validar el numero de renta
        
        } while (opcion != 0);
        System.out.println("Hola");
    }
}
