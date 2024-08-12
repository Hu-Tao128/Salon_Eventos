package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class Cuenta {
    private int NoCliente;

    public int InicioSesion(){
        Scanner leer = new Scanner(System.in);
        int NoCliente = 0;

            System.out.println("Bienvenido, Por Favor Ingrese su Numero de Cliente");
            NoCliente = leer.nextInt();

            Connection connection = null;

        try {
            connection = ConexionBD.obtenerConexion();
            
            String consulta = "SELECT\n" +
                              "    numero AS NoCliente,\n" +
                              "    nomContacto AS Nombres,\n" +
                              "    primerApellido AS Apellido\n" +
                              "FROM cliente\n" +
                              "WHERE numero = ?";
            
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setInt(1, NoCliente);
            ResultSet resultado = statement.executeQuery();
    
            if (resultado.next()) { 
                String Nombres = resultado.getString("Nombres");
                String Apellido = resultado.getString("Apellido");
                NoCliente = resultado.getInt("NoCliente");
                
                System.out.flush();
                System.out.println("Bienvenido " + Nombres + " " + Apellido);
            } else {
                System.out.println("Usuario no encontrado");
                NoCliente = 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return NoCliente;

    }

    public void perfil(int IDCliente) {
        Connection conexion = null;
        String consultaCliente = "SELECT numero, nombreFiscal, nomContacto, primerApellido, segundoApellido, numTel, email FROM cliente WHERE numero = ?";

        conexion = ConexionBD.obtenerConexion();

        try (PreparedStatement statement = conexion.prepareStatement(consultaCliente)) {
            statement.setInt(1, IDCliente);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) { 
                int numero = resultado.getInt("numero");
                String nombreFiscal = resultado.getString("nombreFiscal");
                String Nombre = resultado.getString("nomContacto");
                String primerApellido = resultado.getString("primerApellido");
                String segundoApellido = resultado.getString("segundoApellido");
                if (segundoApellido  == "" || segundoApellido == null) {
                    segundoApellido = "";
                }
                String numTel = resultado.getString("numTel");
                String email = resultado.getString("email");

                System.out.println("Detalles del Cliente:");
                System.out.println("========================================================================");
                System.out.printf("| %-25s | %-40s |\n", "Cliente", numero);
                System.out.println("========================================================================");
                    
                if (nombreFiscal != null && !nombreFiscal.isEmpty()) {
                    System.out.printf("| %-25s | %-40s |\n", "Nombre de Empresa", nombreFiscal);
                }
                System.out.printf("| %-25s | %-40s |\n", "Nombre", Nombre);
                System.out.printf("| %-25s | %-40s |\n", "Apellido Paterno", primerApellido);
                System.out.printf("| %-25s | %-40s |\n", "Apellido Materno", segundoApellido);
                System.out.printf("| %-25s | %-40s |\n", "Teléfono", numTel);
                System.out.printf("| %-25s | %-40s |\n", "Email", email);
                System.out.println("========================================================================");

                OpcionesCuenta cuenta = new OpcionesCuenta();
                cuenta.menuModificar(IDCliente);

            } else {
                System.out.println("No se encontraron detalles para el cliente con ID: " + IDCliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los datos del cliente: " + e.getMessage());
        }
    }
}