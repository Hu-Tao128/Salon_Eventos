import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AgregarCliente {
    public int Formulario(){
        Connection connection = null;

        Scanner datos = new Scanner(System.in);
        String opcion;
        String nombreFiscal = "";
        String Nombre;        
        String primerApellido;
        String segundoApellido;
        String numTel;
        String email;
        int NoCliente = 0;

        System.out.println("Bienvenido al Formulario para registrarse en Salent");

        do {
            System.out.println("Tiene una empresa? (s/n)");
            opcion = datos.next();

            if (opcion == "S" || opcion == "s") {
                System.out.println("A que nombre de empresa quedaran los eventos?");
                nombreFiscal = datos.nextLine();
            }

            System.out.println("Ingrese su nombre y segundo nombre (No Apellidos)");
            Nombre = datos.nextLine();

            System.out.println("Ingrese su primer apellido:");
            primerApellido = datos.nextLine();

            System.out.println("Escriba su segundo apellido:");
            segundoApellido = datos.nextLine();

            System.out.println("Ingrese su numero de telefono:");
            numTel = datos.nextLine();

            System.out.println("Ingrese su correo electronico:");
            email = datos.nextLine();

            System.out.println("Escribio bien los datos? (s/n)");
            opcion = datos.next();
            
        } while (opcion.equals("S") || opcion.equals("s"));

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
                System.out.println("Los datos se han insertado correctamente en la tabla salones.");
            } else {
                System.out.println("No se pudo insertar los datos en la tabla salones.");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
        }finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(connection);
        }

        datos.close();
        return NoCliente;
    }
    
}
