package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MostrarMontajes {
    public void showMontajes(int evento){
        Connection conexion = null;

        String consultaMontaje = "SELECT \n" +
                                                    "    m.nombreMontaje AS NombreMontaje, \n" +
                                                    "    m.descripcion AS Descripcion \n" +
                                                    "FROM montaje_evento AS me \n" +
                                                    "INNER JOIN montaje AS m ON me.montaje = m.numero \n" +
                                                    "WHERE me.evento = ?;";
                        
                        try (PreparedStatement statement2 = conexion.prepareStatement(consultaMontaje)) {
                            statement2.setInt(1, evento);
                            ResultSet resultado2 = statement2.executeQuery();
                           
                            System.out.printf("%-30s %-50s\n", "Nombre del Montaje", "Descripción");

                            System.out.printf("%-30s %-50s\n", "------------------------------", "--------------------------------------------------");

                            while (resultado2.next()) {
                                
                                String montaje = resultado2.getString("NombreMontaje");
                                String descripcion = resultado2.getString("Descripcion");

                                System.out.printf("%-30s %-50s\n", montaje, descripcion);
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
                    ID = 0; // Resetear ID para asegurar que el bucle continúe
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.next(); // Limpiar el buffer de entrada
            }

        } while (ID == 0);

        return ID;
    }

}
