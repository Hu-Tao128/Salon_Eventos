package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarRenta {
    private int IDMes;
    public void showRentas(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM renta";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.printf("%-10s %-30s %-40s %-40s %-30s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n", 
            "Numero", "Fecha reservacion", "Fecha de inicio", "Fecha de finalizacion", "Hora de inicio", "Hora de finalizacion", "IVA", "subtotal", "Total", "Montaje", "Salon", "Cliente", "Evento");

                while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String fechaReservacion = resultado.getString("fechaReservacion");
                String fechaInicio = resultado.getString("fechaInicio");
                String fechaFinal = resultado.getString("fechaFinal");
                String horaInicio = resultado.getString("horaInicio");
                String horaFinal = resultado.getString("horaFinal");
                double IVA = resultado.getDouble("IVA");
                double subtotal = resultado.getDouble("subtotal");
                double total = resultado.getDouble("total");
                int montaje = resultado.getInt("montaje");
                int salon = resultado.getInt("salon");
                int cliente = resultado.getInt("cliente");
                int evento = resultado.getInt("evento");
                

                // Imprimir datos en forma de tabla
                System.out.println("\n========================================================================");
                System.out.printf("| %-25s | %-40d |\n", "Renta", numero);
                System.out.printf("| %-25s | %-40s |\n", "Fecha de reservacion", fechaReservacion);
                System.out.printf("| %-25s | %-40s |\n", "Fecha inicio", fechaInicio);
                System.out.printf("| %-25s | %-40s |\n", "Fecha finalizacion", fechaFinal);
                System.out.printf("| %-25s | %-40s |\n", "Hora de inicio", horaInicio);
                System.out.printf("| %-25s | %-40s |\n", "Hora de finalizacion", horaFinal);
                System.out.printf("| %-25s | %-40s |\n", "IVA", IVA);
                System.out.printf("| %-25s | %-40s |\n", "Subtotal", subtotal);
                System.out.printf("| %-25s | %-40s |\n", "Total", total);
                System.out.printf("| %-25s | %-40s |\n", "Montaje", montaje);
                System.out.printf("| %-25s | %-40s |\n", "Salon", salon);
                System.out.printf("| %-25s | %-40s |\n", "Cliente", cliente);
                System.out.printf("| %-25s | %-40s |\n", "Evento", evento);
                System.out.println("========================================================================");
                }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }

     public int elegirRenta() {
        Scanner Leer = new Scanner(System.in);
        int IDRenta = 0;

        do {
            System.out.println("Ingresar el número de la renta:");

            try {
                IDRenta = Leer.nextInt();
                if (IDRenta <= 0) {
                    System.out.println("El número de la renta debe ser un número positivo.");
                    IDRenta = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next();
            }

        } while (IDRenta == 0);

        return IDRenta;
    }

    public void consultaResRenta(){
        String opcion = "";

        Scanner Leer = new Scanner(System.in);

        Connection conexion = null;
   
            System.out.println("Escoga el mes en el que quiere ver todas las reservaciones");
            IDMes = Leer.nextInt();
        
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'salon'
            String consulta = "select DATE_FORMAT(r.fechaInicio, \"%d-%m-%y\") as FechaInicio\r\n," +
                                "s.nombre as Salon\r\n," +
                                "concat(c.nomContacto,' ',c.primerApellido,' ', ifnull(concat (c.segundoApellido,' '),' ')) as Cliente\r\n," +
                                "e.nombre as TipoEvento\r\n," +
                                "s.capacidad as CantidadInvitados\r\n" +
                                "from renta as r \r\n" +
                                "inner join salon as s on r.salon = s.numero\r\n" +
                                "inner join cliente as c on r.cliente = c.numero\r\n" +
                                "inner join evento as e on r.evento = e.numero\r\n" +
                                "where month(r.fechaInicio) = ?";

            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, IDMes);
            ResultSet resultado12 = statement.executeQuery();
            
            // Encabezado de la tabla
            /*System.out.println("========================================================================");
            System.out.printf("| %-25s | %-40s |\n", "Datos", "Valor");
            System.out.println("========================================================================");*/

            System.out.println("Reservaciones del mes numero: " + IDMes);

            // Iterar sobre el resultado de la consulta
            while (resultado12.next()) {
                String FechaInicio = resultado12.getString("FechaInicio");
                String Salon = resultado12.getString("Salon");
                String Cliente = resultado12.getString("Cliente");
                String TipoEvento = resultado12.getString("TipoEvento");
                int CantidadInvitados = resultado12.getInt("CantidadInvitados");
                
                System.out.println("\n========================================================================");
                System.out.printf("| %-25s | %-40s |\n", "FechaInicio", FechaInicio);
                System.out.printf("| %-25s | %-40s |\n", "Salon", Salon);
                System.out.printf("| %-25s | %-40s |\n", "Cliente", Cliente);
                System.out.printf("| %-25s | %-40s |\n", "TipoEvento", TipoEvento);
                System.out.printf("| %-25s | %-40d |\n", "CantidadInvitados", CantidadInvitados);
                System.out.println("========================================================================");
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }

    public void menuRentas(){
        System.out.println("Bienvenido al menu de renta");

        Scanner Leer = new Scanner(System.in);

        String validar1;
        int opcion1 = 0;

        do{
            System.out.println("=================================");
            System.out.println("|               Menu             |");
            System.out.println("|--------------------------------|");
            System.out.println("|       1) Consulta general      |");
            System.out.println("|--------------------------------|");
            System.out.println("|       2) Reservaciones para    |");
            System.out.println("|          el mismo mes          |");
            System.out.println("|--------------------------------|");
            System.out.println("|        0) salir                |");
            System.out.println("|--------------------------------|");
            System.out.println("=================================");

            validar1 = Leer.next();

            try{
                opcion1 = Integer.parseInt(validar1);

                switch (opcion1) {
                    case 1:
                        showRentas();
                    break;

                    case 2:
                        consultaResRenta();
                    break;

                    case 0:
                        System.out.println("Saliendo del menu de servicios");
                    break;
                
                    default:
                        break;
                }

            }catch(Exception e){
                System.out.println("Ingrese numeros por favor");
            }
        }while(opcion1 != 0);
    }
}
