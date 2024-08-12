package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarMontaje {

    private int IDMontaje;

    public void showMontaje(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'montaje'
            String consulta = "SELECT * FROM montaje";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de los Montajes:");
            System.out.println("=========================================================================================================");
            System.out.printf("| %-10s | %-40s | %-45s |\n", "Número", "Nombre del Montaje", "Descripción");
            System.out.println("=========================================================================================================");
    
            // Iterar sobre el resultado de la consulta
            while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombreMontaje = resultado.getString("nombreMontaje");
                String descripcion = resultado.getString("descripcion");
    
                // Imprimir datos en forma de tabla
                System.out.printf("| %-10d | %-40s | %-45s |\n", numero, nombreMontaje, descripcion);
            }
            
            System.out.println("=========================================================================================================");
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
    
    public int elegirMontaje() {
        Scanner Leer = new Scanner(System.in);
        int IDMontaje = 0;

        do {
            System.out.println("Ingresar el número del montaje:");

            try {
                IDMontaje = Leer.nextInt();
                if (IDMontaje <= 0) {
                    System.out.println("El número del montaje debe ser un número positivo.");
                    IDMontaje = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next();
            }

        } while (IDMontaje == 0);

        return IDMontaje;
    }

    public void consultaResMontaje(){
        String opcion = "";

        Scanner Leer = new Scanner(System.in);

        Connection conexion = null;
   
            System.out.println("Lista de montajes");
            showMontaje();
            System.out.println("Escoja el montaje al que quiere ver todas las reservaciones en las que se ha pedido:");
            IDMontaje = Leer.nextInt();
        
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'salon'
            String consulta = "select r.numero as Reservacion\r\n," +
                                "DATE_FORMAT(r.fechaReservacion, \"%d-%m-%y\") as FechaReservacion \r\n," +
                                "sa.nombre as Salon\r\n," +
                                "concat(c.nomContacto,' ',c.primerApellido,' ',ifnull(concat (c.segundoApellido,' '),' ')) as Cliente\r\n," +
                                "e.nombre as TipoEvento\r\n," +
                                "sa.capacidad as CantidadInvitados\r\n," +
                                "m.descripcion as DescripcionMontaje\r\n" + 
                                "from montaje as m\r\n" +
                                "inner join renta as r on r.montaje = m.numero\r\n" +
                                "inner join salon as sa on sa.numero = r.salon\r\n" +
                                "inner join cliente as c on c.numero = r.cliente\r\n" +
                                "inner join evento as e on e.numero = r.evento\r\n" + 

                                "WHERE m.numero = ?";

            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, IDMontaje);
            ResultSet resultado11 = statement.executeQuery();
            
            // Encabezado de la tabla
            /*System.out.println("========================================================================");
            System.out.printf("| %-25s | %-40s |\n", "Datos", "Valor");
            System.out.println("========================================================================");*/

            System.out.println("Reservaciones del montaje numero: " + IDMontaje);

            resultado11.next();

            System.out.println("\n========================================================================");
            String DescripcionMontaje = resultado11.getString("DescripcionMontaje");
            System.out.printf("| %-25s | %-40s |", "Descripcion del montaje", DescripcionMontaje);
            System.out.println("\n========================================================================");

            ResultSet resultado14 = statement.executeQuery();

            // Iterar sobre el resultado de la consulta
            while (resultado14.next()) {
                int Reservacion = resultado14.getInt("Reservacion");
                String FechaReservacion = resultado14.getString("FechaReservacion");
                String Salon = resultado14.getString("Salon");
                String Cliente = resultado14.getString("Cliente");
                String TipoEvento = resultado14.getString("TipoEvento");
                int CantidadInvitados = resultado14.getInt("CantidadInvitados");
                   
                System.out.printf("| %-25s | %-40d |\n", "Reservacion", Reservacion);
                System.out.printf("| %-25s | %-40s |\n", "Fecha de Reservacion", FechaReservacion);
                System.out.printf("| %-25s | %-40s |\n", "Salon", Salon);
                System.out.printf("| %-25s | %-40s |\n", "Cliente", Cliente);
                System.out.printf("| %-25s | %-40s |\n", "Tipo de Evento", TipoEvento);
                System.out.printf("| %-25s | %-40d |\n", "Cantidad de Invitados", CantidadInvitados);
                System.out.println("========================================================================");
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }

    public void menuMontajes(){
        System.out.println("Bienvenido al menu de montaje");

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
            System.out.println("|          el mismo tipo de      |");
            System.out.println("|          montaje               |");
            System.out.println("|--------------------------------|");
            System.out.println("|        0) salir                |");
            System.out.println("|--------------------------------|");
            System.out.println("=================================");
            System.out.println("Escriba el numero de la opción a elegir:");
            validar1 = Leer.next();

            try{
                opcion1 = Integer.parseInt(validar1);

                switch (opcion1) {
                    case 1:
                        showMontaje();
                    break;

                    case 2:
                        consultaResMontaje();
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
