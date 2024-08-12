package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import conexionDB.ConexionBD;

public class AgregarCliente {

    Cuenta perfil = new Cuenta();
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+$");
    private String opcion;
    private String nombreFiscal = "";
    private String Nombre;
    private String primerApellido;
    private String segundoApellido;
    private String numTel;
    private String email;
    private int NoCliente = 0;
    
    public int Formulario() {
        Connection connection = null;
        Scanner datos = new Scanner(System.in);
        boolean valid = false;

        System.out.println("Bienvenido al Formulario para registrarse en Salent");

        do {
            System.out.println("Tiene una empresa? (s/n)");
            opcion = datos.next();
            datos.nextLine();

            if (opcion.equalsIgnoreCase("s")) {
                System.out.println("A que nombre de empresa quedaran los eventos?");
                nombreFiscal = datos.nextLine();
            } else {
                nombreFiscal = null;
            }

            System.out.println("Ingrese su nombre y segundo nombre (No Apellidos)");
            Nombre = datos.nextLine();
            if (!isValidName(Nombre)) {
                System.out.println("Nombre inválido. Debe contener solo letras.");
                continue;
            }

            System.out.println("Ingrese su primer apellido:");
            primerApellido = datos.nextLine();
            if (!isValidName(primerApellido)) {
                System.out.println("Primer apellido inválido. Debe contener solo letras.");
                continue;
            }

            System.out.println("Escriba su segundo apellido, si no tiene solo ponga 0");
            segundoApellido = datos.nextLine();
            if (!segundoApellido.equals("0") && !isValidName(segundoApellido)) {
                System.out.println("Segundo apellido inválido. Debe contener solo letras.");
                continue;
            }

            if (segundoApellido.equals("0")) {
                segundoApellido = null;
            }

            System.out.println("Ingrese su numero de telefono:");
            numTel = datos.nextLine();

            System.out.println("Ingrese su correo electronico:");
            email = datos.nextLine();

            System.out.println("Desea registrarse? (s/n), si escribio mal un dato podra modificarlo al presionar s");
            opcion = datos.next();

            if (opcion.equals("n")) {
                valid = false;
                break;
            } else {
                valid = true;
            }

        } while (opcion.equalsIgnoreCase("n"));

        if (valid) {
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
                        System.out.println("Gracias por haberse registrado en Renta de Salones Salent.");
                        System.out.println("A continuación sus datos:");
    
                        perfil.perfil(NoCliente);
                    }
                } else {
                    System.out.println("No se pudo agregar los datos.");
                }
    
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
            }
        } else {
            System.out.println("Vuelva pronto");
        }

        return NoCliente;
    }
    
    private boolean isValidName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }
}
