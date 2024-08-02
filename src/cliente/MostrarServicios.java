package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarServicios {

    // Método para mostrar servicios disponibles
    public void showServicios() {
        Connection conexion = null;
        String consultaServicios = "SELECT numero, nombreServicio AS Nombre, descripcion AS Descripcion, precio AS Precio FROM servicios WHERE disponibilidad = 1";

        // Obtener la conexión
        conexion = ConexionBD.obtenerConexion();

        try (PreparedStatement statement = conexion.prepareStatement(consultaServicios);
                ResultSet resultado1 = statement.executeQuery()) {

            System.out.println("=============================================================");
            System.out.printf("| %-5s | %-25s | %-40s | %-10s |\n", 
                            "ID", "Nombre", "Descripción", "Precio");
            System.out.println("=============================================================");

            // Mostrar datos de servicios
            while (resultado1.next()) {
                int id = resultado1.getInt("numero");
                String nombre = resultado1.getString("Nombre");
                String descripcion = resultado1.getString("Descripcion");
                float precio = resultado1.getFloat("Precio");

                System.out.printf("| %-5d | %-25s | %-40s | %-10.2f |\n", 
                                id, nombre, descripcion, precio);
            }
            System.out.println("=============================================================");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para elegir un servicio disponible
    public int elegirServicio() {
        Scanner Leer = new Scanner(System.in);
        int ID = -1;
        boolean entradaValida = false;

        do {
            System.out.println("Ingrese el número del servicio:");
            try {
                ID = Leer.nextInt();
                Leer.nextLine();
                if (ID <= 0) {
                    System.out.println("El número del servicio debe ser un número positivo.");
                    entradaValida = false;
                } else if (!esServicioDisponible(ID)) {
                    System.out.println("El servicio seleccionado no está disponible. Por favor, elija otro.");
                    entradaValida = false;
                } else {
                    entradaValida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
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
                                    "    se.nombreServicio AS DescripcionServicio,\n" + 
                                    "    se.precio AS CostoServicio\n" + 
                                    "FROM renta AS r\n" + 
                                    "INNER JOIN cliente AS c ON r.cliente = c.numero\n" + 
                                    "INNER JOIN salon AS s ON r.salon = s.numero\n" + 
                                    "INNER JOIN servicios_renta AS sr ON r.numero = sr.renta\n" + 
                                    "INNER JOIN servicios AS se ON sr.servicios = se.numero\n" + 
                                    "WHERE r.numero = ?;";

        conexion = ConexionBD.obtenerConexion();
        
        try (PreparedStatement statement = conexion.prepareStatement(consultaServicios)) {
            statement.setInt(1, IDRenta);
            ResultSet resultado = statement.executeQuery();

            System.out.println("===================================================================================");
            System.out.printf("| %-10s | %-20s | %-30s | %-10s |\n", 
                            "Fecha", "Salon", "Descripción", "Precio");
            System.out.println("===================================================================================");

            boolean valid = false;

            while (resultado.next()) {
                valid = true;
                String FechaReservacion = resultado.getString("FechaReservacion");
                String NombreSalon = resultado.getString("NombreSalon");
                String DescripcionServicio = resultado.getString("DescripcionServicio");
                float CostoServicio = resultado.getFloat("CostoServicio");

                System.out.printf("| %-10s | %-20s | %-30s | %-10.2f |\n", 
                                FechaReservacion, NombreSalon, DescripcionServicio, CostoServicio);
            }
            
            if (!valid) {
                System.out.println("|                                                No tienes servicios aún                                                   |");
            } else {
                System.out.println("===================================================================================");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
