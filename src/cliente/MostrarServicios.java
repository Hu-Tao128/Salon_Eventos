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
        String consultaServicios = "SELECT numero, nombreServicio AS Nombre, descripcion AS Descripcion, precio AS Precio, disponibilidad AS Disponibilidad FROM servicios";

        // Obtener la conexión
        conexion = ConexionBD.obtenerConexion();

        try (PreparedStatement statement = conexion.prepareStatement(consultaServicios);
                ResultSet resultado1 = statement.executeQuery()) {

            // Mostrar encabezados de tabla
            System.out.printf("%-10s %-20s %-30s %-10s %-15s\n", "ID", "Nombre", "Descripción", "Precio", "Disponibilidad");
            System.out.printf("%-10s %-20s %-30s %-10s %-15s\n", "----------", "--------------------", "------------------------------", "----------", "---------------");

            // Mostrar datos de servicios
            while (resultado1.next()) {
                int id = resultado1.getInt("numero");
                String nombre = resultado1.getString("Nombre");
                String descripcion = resultado1.getString("Descripcion");
                float precio = resultado1.getFloat("Precio");
                int dis = resultado1.getInt("Disponibilidad");
                String disponibilidad = (dis == 0) ? "No Disponible" : "Disponible";

                System.out.printf("%-10d %-20s %-30s %-10.2f %-15s\n", id, nombre, descripcion, precio, disponibilidad);
            }

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
}
