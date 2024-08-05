package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarEquipamiento {

    private int IDEquipamiento;

    public void showEquipamiento(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM equipamiento";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de los equipamientos");
                System.out.println("=====================================================================================");
                System.out.printf("| %-10s | %-40s | %-10s | %-10s |\n", "Número", "Descripcion", "Precio", "Stock");
                System.out.println("=====================================================================================");

                while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String descripcion = resultado.getString("descripcion");
                double precio = resultado.getDouble("precio");
                int stock = resultado.getInt("stock");

                // Imprimir datos en forma de tabla
                System.out.printf("%-15d %-40s %-15s %-10s\n", 
                                numero, descripcion, precio, stock);
                }

                System.out.println("======================================================================================");

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }

     public int elegirEquipamiento() {
        Scanner Leer = new Scanner(System.in);
        int IDEquipamiento = 0;

        do {
            System.out.println("Ingresar el número del equipamiento:");

            try {
                IDEquipamiento = Leer.nextInt();
                if (IDEquipamiento <= 0) {
                    System.out.println("El número del equipamiento debe ser un número positivo.");
                    IDEquipamiento = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next();
            }

        } while (IDEquipamiento == 0);

        return IDEquipamiento;
    }

    public void consultaResEquipa(){
        String opcion = "";

        Scanner Leer = new Scanner(System.in);

        Connection conexion = null;
   
            System.out.println("Escoga el equipamiento al que quiere ver las reservaciones");
            showEquipamiento();
            IDEquipamiento = Leer.nextInt();
        
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'salon'
            String consulta = "select r.numero as Reservacion\r\n," + 
                               " DATE_FORMAT(r.fechaReservacion, \"%d-%m-%y\") as FechaReservacion\r\n," + 
                                "sa.nombre as Salon\r\n," +
                                "concat(c.nomContacto,' ',c.primerApellido,' ', ifnull(concat (c.segundoApellido,' '),' ')) as Cliente\r\n," +
                                "e.nombre as TipoEvento\r\n," +
                                "sa.capacidad as CapacidadInvitados\r\n," +
                                "eq.descripcion as DescripcionEquipo\r\n," +
                                "eq.precio as CostoEquipo\r\n" +
                                "from equipamiento as eq \r\n" + 
                                                                        
                               " inner join equipos_renta as er on eq.numero = er.equipamiento\r\n" + 

                                "inner join renta as r on er.renta = r.numero\r\n" + 

                                "inner join salon as sa on r.salon = sa.numero\r\n" +

                                "inner join cliente as c on r.cliente = c.numero\r\n" +

                               " inner join evento as e on  r.evento = e.numero\r\n" + 

                                "where eq.numero = ?";

            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, IDEquipamiento);
            ResultSet resultado11 = statement.executeQuery();
            
            // Encabezado de la tabla
            /*System.out.println("========================================================================");
            System.out.printf("| %-25s | %-40s |\n", "Datos", "Valor");
            System.out.println("========================================================================");*/

            System.out.println("Reservaciones del equipamiento numero: " + IDEquipamiento);

            // Iterar sobre el resultado de la consulta
            while (resultado11.next()) {
                int Reservacion = resultado11.getInt("Reservacion");
                String FechaReservacion = resultado11.getString("FechaReservacion");
                String Salon = resultado11.getString("Salon");
                String Cliente = resultado11.getString("Cliente");
                String TipoEvento = resultado11.getString("TipoEvento");
                int CapacidadInvitados = resultado11.getInt("CapacidadInvitados");
                String DescripcionEquipo = resultado11.getString("DescripcionEquipo");
                double CostoEquipo = resultado11.getDouble("CostoEquipo");
                
                System.out.println("\n========================================================================");
                System.out.printf("| %-25s | %-40d |\n", "Reservacion", Reservacion);
                System.out.printf("| %-25s | %-40s |\n", "FechaReservacion", FechaReservacion);
                System.out.printf("| %-25s | %-40s |\n", "Salon", Salon);
                System.out.printf("| %-25s | %-40s |\n", "Cliente", Cliente);
                System.out.printf("| %-25s | %-40s |\n", "TipoEvento", TipoEvento);
                System.out.printf("| %-25s | %-40d |\n", "CapacidadInvitados", CapacidadInvitados);
                System.out.printf("| %-25s | %-40s |\n", "DescripcionEquipo", DescripcionEquipo);
                System.out.printf("| %-25s | %-40.0f |\n", "CostoEquipo", CostoEquipo);
                System.out.println("========================================================================");
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }
    
    public void menuEquipamientos(){
        System.out.println("Bienvenido al menu de equipamiento");

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
            System.out.println("|          equipamiento          |");
            System.out.println("|--------------------------------|");
            System.out.println("|        0) salir                |");
            System.out.println("|--------------------------------|");
            System.out.println("=================================");

            validar1 = Leer.next();

            try{
                opcion1 = Integer.parseInt(validar1);

                switch (opcion1) {
                    case 1:
                        showEquipamiento();
                    break;

                    case 2:
                        consultaResEquipa();
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
