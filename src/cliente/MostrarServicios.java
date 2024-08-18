package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import admin.MostrarTipoServicio;

import conexionDB.ConexionBD;

public class MostrarServicios {

    private int IDServicio;
    private int opcion1 = 0;
    
    // Método para mostrar servicios disponibles
    public void showServicios() {
        Connection conexion = null;
        String consultaServicios = "SELECT numero, nombreServicio AS Nombre, descripcion AS Descripcion, precio AS Precio FROM servicios WHERE disponibilidad = 1";

        // Obtener la conexión
        conexion = ConexionBD.obtenerConexion();

        try (PreparedStatement statement = conexion.prepareStatement(consultaServicios);
     ResultSet resultado1 = statement.executeQuery()) {

        System.out.println("=====================================================================================================");
    System.out.printf("| %-2s | %-32s | %-44s | %-10s |\n", 
                        "ID", "Nombre", "Descripción", "Precio");
    System.out.println("=====================================================================================================");
    
    while (resultado1.next()) {
        int id = resultado1.getInt("numero");
        String nombre = resultado1.getString("Nombre");
        String descripcion = resultado1.getString("Descripcion");
        float precio = resultado1.getFloat("Precio");
    
        System.out.printf("| %-2d | %-32s | %-44s | %-10.2f |\n", 
                        id, nombre, descripcion, precio);
    }
    
    System.out.println("=====================================================================================================\n");

} catch (SQLException e) {
    e.printStackTrace();
}

    }

    public int elegirServicio() {
        Scanner Leer = new Scanner(System.in);
        int ID = -1;
        boolean entradaValida = false;

        do {
            System.out.println("Ingrese el número del servicio (o 0 para cancelar):");
            try {
                ID = Leer.nextInt();
                Leer.nextLine(); // Limpiar el buffer del scanner

                if (ID == 0) {
                    System.out.println("Operación cancelada.");
                    return 0; // Salir del método si el usuario cancela
                }

                if (ID <= 0) {
                    System.out.println("El número del servicio debe ser un número positivo.");
                } else if (!esServicioDisponible(ID)) {
                    System.out.println("El servicio seleccionado no está disponible. Por favor, elija otro.");
                } else {
                    entradaValida = true; // El ID es válido y el servicio está disponible
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese un número válido.");
                Leer.nextLine(); // Limpiar el buffer del scanner
            }
        } while (!entradaValida);

        return ID;
    }

    private boolean esServicioDisponible(int idServicio) {
        boolean disponible = false;
        String consultaDisponibilidad = "SELECT disponibilidad FROM servicios WHERE numero = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(consultaDisponibilidad)) {
            statement.setInt(1, idServicio);
            try (ResultSet resultado = statement.executeQuery()) {
                if (resultado.next()) {
                    int disponibilidad = resultado.getInt("disponibilidad");
                    disponible = (disponibilidad == 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return disponible;
    }
    
    public void showServiciosRenta(int IDRenta) {
        Connection conexion = null;
        String consultaServicios = "SELECT\n" + 
                                    "    DATE_FORMAT(r.fechaReservacion, '%y-%m-%d') AS FechaReservacion,\n" + 
                                    "    s.nombre AS NombreSalon,\n" + 
                                    "    srv.descripcion AS DescripcionServicio,\n" + 
                                    "    ts.nombre AS TipoServicio,\n" + 
                                    "    srv.precio AS CostoServicio\n" + 
                                    "FROM renta AS r\n" + 
                                    "INNER JOIN salon AS s ON r.salon = s.numero\n" + 
                                    "INNER JOIN servicios_renta AS sr ON r.numero = sr.renta\n" + 
                                    "INNER JOIN servicios AS srv ON sr.servicios = srv.numero\n" + 
                                    "INNER JOIN tipo_servicios AS ts ON srv.tipoServicio = ts.numero\n" + 
                                    "WHERE r.numero = ?;";
    
        conexion = ConexionBD.obtenerConexion();
    
        try (PreparedStatement statement = conexion.prepareStatement(consultaServicios)) {
            statement.setInt(1, IDRenta);
            ResultSet resultado = statement.executeQuery();
    
            System.out.println("\n========================================================================");
            boolean valid = false;
    
            while (resultado.next()) {
                if (!valid) {
                    System.out.printf("| %-25s | %-40s |\n", "Fecha de Reservación", resultado.getString("FechaReservacion"));
                    System.out.printf("| %-25s | %-40s |\n", "Nombre del Salón", resultado.getString("NombreSalon"));
                    System.out.println("========================================================================");
                    valid = true;
                }
    
                String DescripcionServicio = resultado.getString("DescripcionServicio");
                String TipoServicio = resultado.getString("TipoServicio");
                float CostoServicio = resultado.getFloat("CostoServicio");
    
                System.out.printf("| %-25s | %-40s |\n", "Descripción del Servicio", DescripcionServicio);
                System.out.printf("| %-25s | %-40s |\n", "Tipo de Servicio", TipoServicio);
                System.out.printf("| %-25s | %-40.2f |\n", "Costo del Servicio", CostoServicio);
                System.out.println("--------------------------------------------------------------------------------");
            }
    
            if (!valid) {
                System.out.println("|                                         No se encontraron servicios para esta renta                                       |");
            } else {
                System.out.println("========================================================================");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void consultaResServicio(){

        Scanner Leer = new Scanner(System.in);

        Connection conexion = null;
   
            System.out.println("Escoga el servicio al que quiere ver las reservaciones");
            showServicios();
            IDServicio = Leer.nextInt();
        
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'salon'
            String consulta = "SELECT\r\n" +  
                                "r.numero as Reservacion\r\n," + 
                                "DATE_FORMAT(r.fechaReservacion, \"%d-%m-%y\") as FechaReservacion\r\n," +
                                "sa.nombre as Salon\r\n," +
                                "concat(c.nomContacto,' ',c.primerApellido,' ', ifnull(concat (c.segundoApellido,' '),' ')) as Cliente\r\n," +
                                "e.nombre as TipoEvento\r\n," +
                                "sa.capacidad as CantidadInvitados\r\n," + 
                                "s.descripcion as DescripcionServicio\r\n," + 
                                "s.precio as CostoServicio\r\n" + 
                                "from servicios as s\r\n" + 
                                "inner join servicios_renta as sr on s.numero = sr.servicios\r\n" + 
                                "inner join renta as r on sr.renta = r.numero\r\n" +
                                "inner join salon as sa on sa.numero = r.salon\r\n" + 
                                "inner join cliente as c on c.numero = r.cliente\r\n" + 
                                "inner join evento as e on e.numero = r.evento\r\n" + 
                                "WHERE s.numero = ?";

            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, IDServicio);
            ResultSet resultado11 = statement.executeQuery();
            
            // Encabezado de la tabla
            /*System.out.println("========================================================================");
            System.out.printf("| %-25s | %-40s |\n", "Datos", "Valor");
            System.out.println("========================================================================");*/

            System.out.println("Reservaciones donde se pidio el servicio numero: " + IDServicio);

            // Iterar sobre el resultado de la consulta
            while (resultado11.next()) {
                int Reservacion = resultado11.getInt("Reservacion");
                String FechaReservacion = resultado11.getString("FechaReservacion");
                String Salon = resultado11.getString("Salon");
                String Cliente = resultado11.getString("Cliente");
                String TipoEvento = resultado11.getString("TipoEvento");
                int CantidadInvitados = resultado11.getInt("CantidadInvitados");
                String DescripcionServicio = resultado11.getString("DescripcionServicio");
                double CostoServicio = resultado11.getDouble("CostoServicio");
                
                System.out.println("\n========================================================================");
                System.out.printf("| %-25s | %-40d |\n", "Reservacion", Reservacion);
                System.out.printf("| %-25s | %-40s |\n", "FechaReservacion", FechaReservacion);
                System.out.printf("| %-25s | %-40s |\n", "Salon", Salon);
                System.out.printf("| %-25s | %-40s |\n", "Cliente", Cliente);
                System.out.printf("| %-25s | %-40s |\n", "TipoEvento", TipoEvento);
                System.out.printf("| %-25s | %-40d |\n", "CantidadInvitados", CantidadInvitados);
                System.out.printf("| %-25s | %-40s |\n", "DescripcionServicio", DescripcionServicio);
                System.out.printf("| %-25s | %-40.0f |\n", "CostoServicio", CostoServicio);
                System.out.println("========================================================================");
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }

    public void consultaTiposServicios(){
        Scanner Leer = new Scanner(System.in);

        Connection conexion = null;

        MostrarTipoServicio tpservis = new MostrarTipoServicio();
   
            System.out.println("Lista de tipos de servicios");
            tpservis.showTipoServicio();
            System.out.println("Escoja el numero del servicios en donde quiere ver todas las reservaciones que ha sido pedido el servicio:");
            IDServicio = Leer.nextInt();
        
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'salon'
            String consulta = "SELECT\r\n" +  
                "ts.nombre AS DescripcionTipoServicio\r\n," +
                "s.descripcion AS DescripcionServicio\r\n," +
                "s.precio AS Precio\r\n" +
                "FROM servicios AS s\r\n" + 
                "INNER JOIN tipo_servicios AS ts ON s.tipoServicio = ts.numero\r\n" +
                "WHERE ts.numero = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, IDServicio);
            ResultSet resultado12 = statement.executeQuery();
            
            // Encabezado de la tabla
            /*System.out.println("========================================================================");
            System.out.printf("| %-25s | %-40s |\n", "Datos", "Valor");
            System.out.println("========================================================================");*/

            System.out.println("Reservaciones donde se pidio el servicio numero: " + IDServicio);

            resultado12.next();

            System.out.println("\n===================================================================================");
            String DescripcionTipoServicio = resultado12.getString("DescripcionTipoServicio");
            System.out.printf("| %-35s | %-40s |", "Descripcion del tipo de servicio", DescripcionTipoServicio);
            System.out.println("\n===================================================================================");

            ResultSet resultado11 = statement.executeQuery();

            // Iterar sobre el resultado de la consulta
            while (resultado11.next()) {
                String DescripcionServicio = resultado11.getString("DescripcionServicio");
                int Precio = resultado11.getInt("Precio");
                
                System.out.printf("| %-35s | %-40s |\n", "Descripcion del servicio", DescripcionServicio);
                System.out.printf("| %-35s | %-40d |\n", "Precio", Precio);
                
                
                System.out.println("===================================================================================");
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }

    public void menuServicios(){
        System.out.println("Bienvenido al menu de servicios");

        Scanner Leer = new Scanner(System.in);

        String validar1;

        do{
            System.out.println("=================================");
            System.out.println("|               Menu             |");
            System.out.println("|--------------------------------|");
            System.out.println("|       1) Consulta general      |");
            System.out.println("|--------------------------------|");
            System.out.println("|       2) Reservaciones para    |");
            System.out.println("|          el mismo servicio     |");
            System.out.println("|--------------------------------|");
            System.out.println("|       3) Servicios del         |");
            System.out.println("|          mismo tipo            |");
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
                        showServicios();
                    break;

                    case 2:
                        consultaResServicio();
                    break;

                    case 3:
                        consultaTiposServicios();
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
