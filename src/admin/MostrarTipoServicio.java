package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarTipoServicio {
    public void showTipoServicio(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM tipo_servicios";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Detalles de los tipos de servicios");
            System.out.println("==================================================");
            System.out.printf("| %-14s | %-30s |\n", "Numero", "Nombre");
            System.out.println("==================================================");

            

                while (resultado.next()) {

                    int numero = resultado.getInt("numero");
                String nombre = resultado.getString("nombre"); 

                   
                System.out.printf("|%-14s |  %-30s | \n", 
                                    numero, nombre);
                }
                System.out.println("==================================================");

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }

    
    public int elegirTipoServicio() {
        Scanner Leer = new Scanner(System.in);
        int ID = -1;
        boolean entradaValida = false;

        do {
            System.out.println("Ingresar el número de tipo del tipo de servicio");
            try {
                ID = Leer.nextInt();
                Leer.nextLine(); 
                entradaValida = true; 
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.nextLine();
            }
        } while (!entradaValida);

        return ID;
    }

}
