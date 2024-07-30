package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MostrarEquipamientos {
    public void showEquipamientos(int evento){
        Connection conexion = null;

        String consultaEquipamiento = "SELECT \n" +
                                                      "    eq.descripcion AS NombreEquipamiento, \n" +
                                                      "    eq.precio AS Precio,\n" +
                                                      "    eq.stock AS Stock\n" +
                                                      "FROM equipamiento AS eq\n" +
                                                      "INNER JOIN equipos_evento AS ee ON eq.numero = ee.equipamiento\n" +
                                                      "WHERE ee.evento = ?;";
                        
                        try (PreparedStatement statement3 = conexion.prepareStatement(consultaEquipamiento)) {
                            statement3.setInt(1, evento);
                            ResultSet resultado3 = statement3.executeQuery();

                            System.out.printf("%-30s %-10s %-10s\n", "Nombre del Equipamiento", "Precio", "Stock");

                            System.out.printf("%-30s %-10s %-10s\n", "------------------------------", "----------", "----------");

                            while (resultado3.next()) {
                                
                                String nombreEquipamiento = resultado3.getString("NombreEquipamiento");
                                float precio = resultado3.getFloat("Precio");
                                int stock = resultado3.getInt("Stock");

                                System.out.printf("%-30s %-10.2f %-10d\n", nombreEquipamiento, precio, stock);
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
    }
}
