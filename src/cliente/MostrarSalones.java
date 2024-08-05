package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
//import java.util.Calendar;
import java.sql.Date;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarSalones {

    private int IDSalon;
    
    public void showSalones() {
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'salon'
            String consulta = "SELECT * FROM salon";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            // Iterar sobre el resultado de la consulta
            while (resultado.next()) {
                int numero = resultado.getInt("numero");
                String nombre = resultado.getString("nombre");
                int capacidad = resultado.getInt("capacidad");
                double tamanio = resultado.getDouble("tamanio");
                double precio = resultado.getDouble("precio");
                String dirColonia = resultado.getString("dirColonia");
                String dirCalle = resultado.getString("dirCalle");
                String dirNumero = resultado.getString("dirNumero");
    
                // Encabezado del salón
                System.out.println("===================================================================");
                System.out.printf("| %-20s | %-40s |\n", "Salon " + numero, nombre);
                System.out.println("===================================================================");
    
                // Detalles del salón
                System.out.printf("| %-20s | %-40d |\n", "Capacidad:", capacidad);
                System.out.printf("| %-20s | %-40.2f |\n", "Tamaño (m²):", tamanio);
                System.out.printf("| %-20s | %-40.2f |\n", "Precio:", precio);
                System.out.printf("| %-20s | %-40s |\n", "Dirección:", dirCalle + " " + dirNumero);
                System.out.printf("| %-20s | %-40s |\n", "", "Colonia: " + dirColonia);
                System.out.println("===================================================================");
            }
    
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }
    

   public int elegirSalon() {
        Scanner Leer = new Scanner(System.in);
        int ID = -1;
        boolean entradaValida = false;

        do {
            System.out.println("Ingresar el número de salón");
            try {
                ID = Leer.nextInt();
                Leer.nextLine(); 
                entradaValida = true; 
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.nextLine();
            }
        } while (!entradaValida);

        return ID;
    }

    public float obtenerPrecioSalon(int numeroSalon) {
        Connection conexion = null;
        float precio = 0;

        String consulta = "SELECT precio FROM salon WHERE numero = ?";

        try {
            conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, numeroSalon);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                precio = resultado.getFloat("precio");
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion(conexion);
        }

        return precio;
    }

    public void mostrarFechasNoPermitidas(int IDSalon) {
        Connection conexion = null;
    
        try {
            conexion = ConexionBD.obtenerConexion();
            
            String consulta = "SELECT fechaInicio, fechaFinal " +
                              "FROM renta " +
                              "WHERE salon = ?";
    
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, IDSalon);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Fechas Apartadas para el Salón ID " + IDSalon + ":");
            System.out.println("===============================");
            System.out.printf("| %-12s | %-12s |\n", "Fecha Inicio", "Fecha Final");
            System.out.println("===============================");
    
            while (resultado.next()) {
                Date fechaInicio = resultado.getDate("fechaInicio");
                Date fechaFinal = resultado.getDate("fechaFinal");
    
                System.out.printf("| %-12s | %-12s |\n", fechaInicio.toString(), fechaFinal.toString());
            }
            
            System.out.println("===============================");
    
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }

    public String validarFecha(String fecha, int IDSalon) {
        Connection conexion = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Validar el formato de la fecha
            sdf.setLenient(false);
            java.util.Date fechaUsuarioUtil = sdf.parse(fecha);
            java.sql.Date fechaUsuarioSQL = new java.sql.Date(fechaUsuarioUtil.getTime());

            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();

            // Consulta para obtener las fechas reservadas
            String consulta = "SELECT fechaInicio, fechaFinal " +
                              "FROM renta " +
                              "WHERE salon = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, IDSalon);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()) {
                Date fechaInicio = resultado.getDate("fechaInicio");
                Date fechaFinal = resultado.getDate("fechaFinal");

                // Validar que la fecha del usuario no esté en el rango de fechas reservadas
                if ((fechaUsuarioSQL.equals(fechaInicio) || fechaUsuarioSQL.equals(fechaFinal)) ||
                    (fechaUsuarioSQL.after(fechaInicio) && fechaUsuarioSQL.before(fechaFinal))) {
                    return "Fecha no disponible. Por favor, elija otra fecha.";
                }
            }

            // Si la fecha es válida, devolverla en el formato correcto
            return sdf.format(fechaUsuarioSQL);

        } catch (ParseException e) {
            return "Formato de fecha inválido. Por favor, use el formato yyyy-MM-dd.";
        } catch (SQLException e) {
            return "Error al verificar las fechas reservadas: " + e.getMessage();
        } finally {
            // Cerrar la conexión si no es null
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
    
    public void consultaResSalon(){

        String opcion = "";

        Scanner Leer = new Scanner(System.in);

        Connection conexion = null;
   
            System.out.println("Escoga el salon al que quiere ver las reservaciones");
            showSalones();
            IDSalon = Leer.nextInt();
        
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'salon'
            String consulta = "SELECT\r\n" + 
                                "    s.nombre AS NombreSalon,\r\n" + 
                                "    DATE_FORMAT(r.fechaReservacion, \"%d-%m-%y\") AS FechaReservacion,\r\n" + 
                                "    CONCAT(c.nomContacto, ' ', c.primerApellido, ' ', IFNULL(c.segundoApellido, '')) AS NombreCliente,\r\n" +
                                "    e.nombre AS TipoEvento\r\n" + 
                                "FROM renta AS r\r\n" + 
                                "INNER JOIN cliente AS c ON r.cliente = c.numero\r\n" + 
                                "INNER JOIN salon AS s ON r.salon = s.numero\r\n" + 
                                "INNER JOIN evento AS e ON r.evento = e.numero\r\n" + 
                                "WHERE s.numero = ?";

            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, IDSalon);
            ResultSet resultado20 = statement.executeQuery();
            
            // Encabezado de la tabla
            /*System.out.println("========================================================================");
            System.out.printf("| %-25s | %-40s |\n", "Datos", "Valor");
            System.out.println("========================================================================");*/

            System.out.println("Reservaciones del salon numero: " + IDSalon);

            // Iterar sobre el resultado de la consulta
            while (resultado20.next()) {
                String nombreSalon = resultado20.getString("NombreSalon");
                String fechaReservacion = resultado20.getString("FechaReservacion");
                String nombreCliente = resultado20.getString("NombreCliente");
                String tipoEvento = resultado20.getString("TipoEvento");
                
                System.out.println("\n========================================================================");
                System.out.printf("| %-25s | %-40s |\n", "NombreSalon", nombreSalon);
                System.out.printf("| %-25s | %-40s |\n", "FechaReservacion", fechaReservacion);
                System.out.printf("| %-25s | %-40s |\n", "NombreCliente", nombreCliente);
                System.out.printf("| %-25s | %-40s |\n", "TipoEvento", tipoEvento);
                System.out.println("========================================================================");
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }
    }

    public void menuSalones(){

        System.out.println("Bienvenido al menu de salones");

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
            System.out.println("|          el mismo salon        |");
            System.out.println("|--------------------------------|");
            System.out.println("|        0) salir                |");
            System.out.println("|--------------------------------|");
            System.out.println("=================================");

            validar1 = Leer.next();

            try{
                opcion1 = Integer.parseInt(validar1);

                switch (opcion1) {
                    case 1:
                        showSalones();
                    break;

                    case 2:
                        consultaResSalon();
                    break;

                    case 0:
                        System.out.println("Saliendo del menu de salones");
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
