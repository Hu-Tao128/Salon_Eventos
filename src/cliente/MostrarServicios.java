package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
