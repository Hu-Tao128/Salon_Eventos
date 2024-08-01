package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarMontajes {
    private List<Integer> montajesDisponibles = new ArrayList<>();

    public void showMontajes(int evento) {
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
                
                // Limpiar la lista de montajes disponibles antes de agregar nuevos
                montajesDisponibles.clear();

                System.out.printf("%-10s %-30s %-50s\n", "Número", "Nombre del Montaje", "Descripción");
                System.out.printf("%-10s %-30s %-50s\n", "----------", "------------------------------", "--------------------------------------------------");

                while (resultado.next()) {
                    int numero = resultado.getInt("Numero");
                    String montaje = resultado.getString("NombreMontaje");
                    String descripcion = resultado.getString("Descripcion");

                    // Añadir el número del montaje a la lista
                    montajesDisponibles.add(numero);

                    // Imprimir la información del montaje
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
                } else if (!montajesDisponibles.contains(ID)) {
                    System.out.println("El número de montaje ingresado no está disponible. Por favor, elija un número de la lista.");
                    ID = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next(); // Limpiar el buffer del scanner
            }

        } while (ID == 0);

        return ID;
    }
}
