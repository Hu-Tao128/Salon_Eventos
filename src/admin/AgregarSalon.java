package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class AgregarSalon {
    public void agregarSalonF(){
        Connection connection = null;

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String nombre = "";
        String dirCalle = "";        
        String dirColonia = "";
        String dirNumero = "";
        int capacidad = 0;
        double tamanio = 0.0;
        double precio = 0.0;

        System.out.println("Registro de equipamiento");

        do {
           
            System.out.println("Ingrese el nombre del salon");
            nombre = datos.nextLine();
            
            System.out.println("Ingrese la calle donde se encuentra el salon");
            dirCalle = datos.nextLine();

            System.out.println("Ingrese la colonia donde se encuentra el salon");
            dirColonia = datos.nextLine();

            System.out.println("Ingrese el numero interno del salon");
            dirNumero = datos.nextLine();

            System.out.println("Ingrese la capacidad de personas que posee el salon");
            capacidad = datos.nextInt();

            System.out.println("Ingrese los metros cuadrados que mide el salon");
            tamanio = datos.nextDouble();

            System.out.println("Ingrese el precio de renta del salon");
            precio = datos.nextDouble();

            System.out.println("Escribio bien los datos? (s/n)");
            opcion = datos.next();

            if(opcion.equals("s")){
                opcion2 = "s";
            }
            
        } while (!opcion2.equals("s"));

        try {

            connection = ConexionBD.obtenerConexion();
            
            String agregarUsuario = "INSERT INTO salon (numero, nombre, dirCalle, dirColonia, dirNumero, capacidad, tamanio, precio) VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(agregarUsuario);
            statement.setString(1, nombre);
            statement.setString(2, dirCalle);
            statement.setString(3, dirColonia);
            statement.setString(4, dirNumero);
            statement.setInt(5, capacidad);
            statement.setDouble(6, tamanio);
            statement.setDouble(7, precio);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Los datos se han insertado correctamente en la tabla salon.");
            } else {
                System.out.println("No se pudo insertar los datos en la tabla salon.");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
        }finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(connection);
        }
    }
}
