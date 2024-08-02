package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.sql.Date;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarSalones {
    public void showSalones() {
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'salon'
            String consulta = "SELECT * FROM salon";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            // Encabezado de la tabla
            System.out.println("Detalles de los Salones:");
            /*System.out.println("========================================================================");
            System.out.printf("| %-25s | %-40s |\n", "Datos", "Valor");
            System.out.println("========================================================================");*/

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

                
                System.out.println("\n========================================================================");
                System.out.printf("| %-25s | %-40s |\n", "Número de Salón", numero);
                System.out.printf("| %-25s | %-40s |\n", "Nombre del Salón", nombre);
                System.out.printf("| %-25s | %-40s |\n", "Capacidad", capacidad);
                System.out.printf("| %-25s | %-40.2f |\n", "Tamaño (m²)", tamanio);
                System.out.printf("| %-25s | %-40.2f |\n", "Precio", precio);
                System.out.printf("| %-25s | %-40s |\n", "Dirección", "Calle y Número:");
                System.out.printf("| %-25s | %-40s |\n", "", dirCalle + " " + dirNumero);
                System.out.printf("| %-25s | %-40s |\n", "", "Colonia:");
                System.out.printf("| %-25s | %-40s |\n", "", dirColonia);
                System.out.println("========================================================================");
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
                Leer.nextLine(); // Limpiar el buffer de entrada
                entradaValida = true; // Marcar la entrada como válida
            } catch (InputMismatchException e) {
                // Manejar excepción de entrada incorrecta
                System.out.println("Ingrese números por favor.");
                Leer.nextLine(); // Limpiar el buffer de entrada
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
            statement.setInt(1, numeroSalon); // Establece el parámetro del número de salón
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
            System.out.println("========================================================================");
            System.out.printf("| %-12s | %-12s |\n", "Fecha Inicio", "Fecha Final");
            System.out.println("========================================================================");
    
            while (resultado.next()) {
                // Obtener datos
                Date fechaInicio = resultado.getDate("fechaInicio");
                Date fechaFinal = resultado.getDate("fechaFinal");
    
                // Imprimir datos en forma de tabla
                System.out.printf("| %-12s | %-12s |\n", fechaInicio.toString(), fechaFinal.toString());
            }
            
            System.out.println("========================================================================");
    
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
            java.util.Date fechaUsuario = sdf.parse(fecha);

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

                // Convertir las fechas de la base de datos a java.util.Date
                java.util.Date fechaInicioDB = new java.util.Date(fechaInicio.getTime());
                java.util.Date fechaFinalDB = new java.util.Date(fechaFinal.getTime());

                // Validar que la fecha del usuario no esté en el rango de fechas reservadas
                if (!fechaUsuario.before(fechaInicioDB) && !fechaUsuario.after(fechaFinalDB)) {
                    return "Fecha no disponible. Por favor, elija otra fecha.";
                }
            }

            // Si la fecha es válida, devolverla en el formato correcto
            return sdf.format(fechaUsuario);

        } catch (ParseException e) {
            return "Formato de fecha inválido. Por favor, use el formato yyyy-MM-dd.";
        } catch (SQLException e) {
            return "Error al verificar las fechas reservadas: " + e.getMessage();
        }
    }
    
}
