package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import conexionDB.ConexionBD;

public class MostrarClientes {
    public void showClientes(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM cliente";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();

                while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombreFiscal = resultado.getString("nombreFiscal");
                String nomContacto = resultado.getString("nomContacto");
                String primerApellido = resultado.getString("primerApellido");
                String segundoApellido = resultado.getString("segundoApellido");
                String numTel = resultado.getString("numTel");
                String email = resultado.getString("email");
                

                System.out.println("\n========================================================================");
                System.out.printf("| %-25s | %-40d |\n", "Cliente", numero);
                System.out.printf("| %-25s | %-40s |\n", "Nombre Fiscal", nombreFiscal);
                System.out.printf("| %-25s | %-40s |\n", "Nombre", nomContacto);
                System.out.printf("| %-25s | %-40s |\n", "Primer apellido", primerApellido);
                System.out.printf("| %-25s | %-40s |\n", "Segundo apellido", segundoApellido);
                System.out.printf("| %-25s | %-40s |\n", "Numero de telefono", numTel);
                System.out.printf("| %-25s | %-40s |\n", "Correo electronico", email);
                System.out.println("========================================================================");
                }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }

    public int elegirCliente() {
        Scanner Leer = new Scanner(System.in);
        int IDCliente = 0;

        do {
            System.out.println("Ingresar el número del cliente:");

            try {
                IDCliente = Leer.nextInt();
                if (IDCliente <= 0) {
                    System.out.println("El número del cliente debe ser un número positivo.");
                    IDCliente = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next();
            }

        } while (IDCliente == 0);

        return IDCliente;
    }
}
