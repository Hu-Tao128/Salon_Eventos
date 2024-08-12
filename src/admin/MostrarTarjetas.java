package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarTarjetas {
    public void showTarjetas(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Consulta SQL para obtener datos de la tabla 'montaje'
            String consulta = "SELECT * FROM tarjetas";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de las tarjetas:");
            System.out.println("===============================================================================================");
            System.out.printf("| %-10s | %-20s | %-20s | %-10s | %-20s |\n", "Número", "Numero Tarjeta", "Vencimiento", "CVC", "Cliente");
            System.out.println("===============================================================================================");
            // Iterar sobre el resultado de la consulta
            while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String numTarjeta = resultado.getString("numTarjeta");
                String vencimiento = resultado.getString("vencimiento");
                int cvc = resultado.getInt("cvc");
                int cliente = resultado.getInt("cliente");
    
                // Imprimir datos en forma de tabla
                System.out.printf("| %-10s | %-20s | %-20s | %-10s | %-20d |\n" , numero, numTarjeta, vencimiento, cvc, cliente);
            }
            
            System.out.println("===============================================================================================");
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }

    public int elegirTarjeta() {
        Scanner Leer = new Scanner(System.in);
        int IDTarjeta = 0;

        do {
            System.out.println("Ingresar el número de la tarjeta: ");

            try {
                IDTarjeta = Leer.nextInt();
                if (IDTarjeta <= 0) {
                    System.out.println("El número de la tarjeta debe ser un número positivo.");
                    IDTarjeta = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next();
            }

        } while (IDTarjeta == 0);

        return IDTarjeta;
    }
}
