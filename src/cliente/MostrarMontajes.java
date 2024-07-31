package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarMontajes {
    public void showMontajes(int evento){
        Connection conexion = null;

        String consultaMontaje = "SELECT " +
                "m.numero AS Numero, " +
                "m.nombreMontaje AS NombreMontaje, " +
                "m.descripcion AS Descripcion " +
                "FROM montaje_evento AS me " +
                "INNER JOIN montaje AS m ON me.montaje = m.numero " +
                "WHERE me.evento = ?;";

        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            try (PreparedStatement statement = conexion.prepareStatement(consultaMontaje)) {
                statement.setInt(1, evento);
                ResultSet resultado = statement.executeQuery();
            
                System.out.printf("%-10s %-30s %-50s\n", "Número", "Nombre del Montaje", "Descripción");
                
                System.out.printf("%-10s %-30s %-50s\n", "----------", "------------------------------", "--------------------------------------------------");
            
                while (resultado.next()) {
                    int numero = resultado.getInt("Numero");
                    String montaje = resultado.getString("NombreMontaje");
                    String descripcion = resultado.getString("Descripcion");
            
                    
                    System.out.printf("%-10d %-30s %-50s\n", numero, montaje, descripcion);
                }
            }
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    public int elegirMontaje() {
        Scanner Leer = new Scanner(System.in);
        int ID = 0;

        do {
            System.out.println("Ingresar el número de montaje:");

            try {
                ID = Leer.nextInt();
                if (ID <= 0) {
                    System.out.println("El número de montaje debe ser un número positivo.");
                    ID = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next(); // Limpiar el buffer de entrada
            }

        } while (ID == 0);

        return ID;
    }
}
