package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

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

    public int elegirEquipamiento() {
        Scanner Leer = new Scanner(System.in);
        int ID = -1;
        boolean entradaValida = false;

        do {
            System.out.println("Ingresar el número del equipamiento:");
            try {
                ID = Leer.nextInt();
                Leer.nextLine(); 
                entradaValida = true; 
                if (ID <= 0) {
                    System.out.println("El número del equipamiento debe ser un número positivo.");
                    ID = -1; 
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.nextLine();
            }
        } while (!entradaValida);

        return ID;
    }

    public int getCantidad(int IDEquipamientos){
        int cantidad;

        Connection conexion = null;

        String consultaCantidad = "SELECT " + 
                                    "   stock, " +
                                    "FROM equipamientos " + 
                                    "WHERE numero = ?";

        try (PreparedStatement statement = conexion.prepareStatement(consultaCantidad)) {
            statement.setInt(1, IDEquipamientos);
            ResultSet resultado = statement.executeQuery();

            cantidad = resultado.getInt("stock");

        } catch (SQLException e) {
            e.printStackTrace();
            cantidad = 0;
        }

        return cantidad;
    }

    public float getPrecio(int IDEquipamientos){
        float precio;

        Connection conexion = null;

        String consultaPrecio = "SELECT " + 
                                    "   precio, " +
                                    "FROM equipamientos " + 
                                    "WHERE numero = ?";

        try (PreparedStatement statement = conexion.prepareStatement(consultaPrecio)) {
            statement.setInt(1, IDEquipamientos);
            ResultSet resultado = statement.executeQuery();

            precio = resultado.getFloat("precio");

        } catch (SQLException e) {
            e.printStackTrace();
            precio = 0f;
        }
        return precio;
    }
}
