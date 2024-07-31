package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MostrarServicios {
    public void showServicios(){
        Connection conexion = null;

        String consultaServicios = "SELECT\n" +
                                                   "    nombreServicio AS Nombre,\n" +
                                                   "    descripcion AS Descripcion,\n" +
                                                   "    precio AS Precio,\n" +
                                                   "    disponibilidad AS Disponibilidad\n" +
                                                   "FROM servicios;";
                        
                        try (PreparedStatement statement = conexion.prepareStatement(consultaServicios)) {
                            ResultSet resultado1 = statement.executeQuery();
                            
                            System.out.printf("%-20s %-30s %-10s %-15s\n", "Nombre", "Descripción", "Precio", "Disponibilidad");

                            System.out.printf("%-20s %-30s %-10s %-15s\n", "--------------------", "------------------------------", "----------", "---------------");

                            while (resultado1.next()) {
                                
                                String nombre = resultado1.getString("Nombre");
                                String descripcion = resultado1.getString("Descripcion");
                                float precio = resultado1.getFloat("Precio");
                                int dis = resultado1.getInt("Disponibilidad");
                                String disponibilidad = (dis == 0) ? "No Disponible" : "Disponible";

                                System.out.printf("%-20s %-30s %-10.2f %-15s\n", nombre, descripcion, precio, disponibilidad);
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
    }

    public int elegirServicio() {
        Scanner Leer = new Scanner(System.in);
        int ID = -1;
        boolean entradaValida = false;

        do {
            System.out.println("Ingresar el número del servicio:");
            try {
                ID = Leer.nextInt();
                Leer.nextLine();
                entradaValida = true;
                if (ID <= 0) {
                    System.out.println("El número del servicio debe ser un número positivo.");
                    ID = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.nextLine();
            }
        } while (!entradaValida);

        return ID;
    }
}
