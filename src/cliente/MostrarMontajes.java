package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public int elegirMontaje(){
        Scanner Leer = new Scanner(System.in);
        int ID = 0;
        do {
            System.out.println("Ingresar el numero de salon");
            try {
                ID = Leer.nextInt();
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("Ingrese numeros por favor");
            }
        } while (ID == 0);
        return ID;
    }
}
