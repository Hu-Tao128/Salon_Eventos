package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class AgregarEquipamiento {
    public void agregarEquipamientoF(){
        Connection connection = null;

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String descripcion = "";
        double precio = 0.0;        
        int stock = 0;

        System.out.println("Registro de equipamiento");

        do {
           
            System.out.println("Ingrese la descripcion del equipamiento");
            descripcion = datos.nextLine();
            
            System.out.println("Ingrese el precio del equipamiento");
            precio = datos.nextDouble();

            System.out.println("Ingrese el stock del equipamiento");
            stock = datos.nextInt();

            System.out.println("Escribio bien los datos? (s/n)");
            opcion = datos.next();

            if(opcion.equals("s")){
                opcion2 = "s";
            }
            
        } while (!opcion2.equals("s"));

        try {

            connection = ConexionBD.obtenerConexion();
            
            String agregarUsuario = "INSERT INTO equipamiento (numero, descripcion, precio, stock) VALUES (null, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(agregarUsuario);
            statement.setString(1, descripcion);
            statement.setDouble(2, precio);
            statement.setInt(3, stock);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Los datos se han insertado correctamente en la tabla equipamiento.");
            } else {
                System.out.println("No se pudo insertar los datos en la tabla equipamiento.");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
        }finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(connection);
        }
    }
}
