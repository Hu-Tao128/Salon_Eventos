package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

//import ConexionBD;

public class AgregarClienteAdmin {
    public void agregarClienteF(){
        Connection connection = null;

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String nombreFiscal = "";
        String Nombre;        
        String primerApellido;
        String segundoApellido;
        String numTel;
        String email;
        
        System.out.println("Registro de un cliente");

        do {
           
            System.out.println("Nombre de la empresa del cliente (dar ENTER en caso de no tener)");
            nombreFiscal = datos.nextLine();
            
            System.out.println("Ingrese el nombre y segundo nombre del cliente (No Apellidos)");
            Nombre = datos.nextLine();

            System.out.println("Ingrese el primer apellido:");
            primerApellido = datos.nextLine();

            System.out.println("Escriba el segundo apellido:");
            segundoApellido = datos.nextLine();

            System.out.println("Ingrese el numero de telefono:");
            numTel = datos.nextLine();

            System.out.println("Ingrese el correo electronico:");
            email = datos.nextLine();

            System.out.println("Escribio bien los datos? (s/n)");
            opcion = datos.next();

            if(opcion.equals("s")){
                opcion2 = "s";
            }
            
        } while (!opcion2.equals("s"));

        try {

            connection = ConexionBD.obtenerConexion();
            
            String agregarUsuario = "INSERT INTO cliente (numero, nombreFiscal, nomContacto, primerApellido, segundoApellido, numTel, email) VALUES (null, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(agregarUsuario);
            statement.setString(1, nombreFiscal);
            statement.setString(2, Nombre);
            statement.setString(3, primerApellido);
            statement.setString(4, segundoApellido);
            statement.setString(5, numTel);
            statement.setString(6, email);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Los datos se han insertado correctamente en la tabla cliente.");
            } else {
                System.out.println("No se pudo insertar los datos en la tabla cliente.");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
        }finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(connection);
        }
    }
}
