package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class AgregarCliente {

    Cuenta perfil = new Cuenta();

    public int Formulario() {
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
            datos.nextLine();

            if (opcion.equalsIgnoreCase("s")) {
                System.out.println("A que nombre de empresa quedaran los eventos?");
                nombreFiscal = datos.next();
            }else{
                nombreFiscal = null;
            }

            System.out.println("Ingrese su nombre y segundo nombre (No Apellidos)");
            Nombre = datos.nextLine();

            System.out.println("Ingrese su primer apellido:");
            primerApellido = datos.nextLine();

            System.out.println("Escriba su segundo apellido, si no tiene solo ponga 0");
            segundoApellido = datos.nextLine();

            if (segundoApellido.equals("0")) {
                segundoApellido = null;
            }

            System.out.println("Ingrese su numero de telefono:");
            numTel = datos.nextLine();

            System.out.println("Ingrese su correo electronico:");
            email = datos.nextLine();

            System.out.println("Escribio bien los datos? (s/n)");
            opcion = datos.next();

        } while (opcion.equalsIgnoreCase("n"));

        try {
            connection = ConexionBD.obtenerConexion();
            
            String agregarUsuario = "INSERT INTO cliente (numero, nombreFiscal, nomContacto, primerApellido, segundoApellido, numTel, email) VALUES (null, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(agregarUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, nombreFiscal);
            statement.setString(2, Nombre);
            statement.setString(3, primerApellido);
            statement.setString(4, segundoApellido);
            statement.setString(5, numTel);
            statement.setString(6, email);

            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    NoCliente = generatedKeys.getInt(1);
                    System.out.println("Gracias por haberse resgistrado en Renta de Salones Salent.");
                    System.out.println("A continuacion sus datos");

                    perfil.perfil(NoCliente);
                }
            } else {
                System.out.println("No se pudo insertar los datos en la tabla cliente.");
            }

        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
        }

        return NoCliente;
    }
}
