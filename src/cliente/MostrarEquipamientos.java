package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarEquipamientos {
    public void showEquipamientos(int evento) {
        Connection conexion = null;
    
        String consultaEquipamiento = "SELECT " +
                                      "    eq.numero AS Numero, " + 
                                      "    eq.descripcion AS NombreEquipamiento, " +
                                      "    eq.precio AS Precio, " +
                                      "    eq.stock AS Stock " + 
                                      "FROM equipamiento AS eq " + 
                                      "INNER JOIN equipos_evento AS ee ON eq.numero = ee.equipamiento " +  
                                      "WHERE ee.evento = ?;";
    
        conexion = ConexionBD.obtenerConexion();  
    
        try (PreparedStatement statement = conexion.prepareStatement(consultaEquipamiento)) {
            statement.setInt(1, evento);
            ResultSet resultado3 = statement.executeQuery();
            
            System.out.println("===================================================================================");
            System.out.printf("| %10s | %-33s | %-10s | %-10s |\n", 
                            "Numero", "Nombre del Equipamiento", "Precio", "Stock");
                            System.out.println("===================================================================================");
    
            System.out.printf("| %10s | %-33s | %-10s | %-10s |\n", 
                            "----------", "-------------------------------", "----------", "----------");
    
            while (resultado3.next()) {
                int num = resultado3.getInt("Numero");
                String nombreEquipamiento = resultado3.getString("NombreEquipamiento");
                float precio = resultado3.getFloat("Precio");
                int stock = resultado3.getInt("Stock");
    
                System.out.printf("| %10d | %-33s | %-10.2f | %-10d |\n", 
                                num, nombreEquipamiento, precio, stock);
            }
            System.out.println("===================================================================================");
    
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

        System.out.println("Cual es la cantidad que va a seleccionar?");

        Connection conexion = null;

        String consultaCantidad = "SELECT " + 
                                    "   stock " +
                                    "FROM equipamiento " + 
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

    public float getPrecio(int IDEquipamientos) {
        float precio = 0f;
    
        Connection conexion = null;
    
        String consultaPrecio = "SELECT " + 
                                "   precio " +
                                "FROM equipamiento " + 
                                "WHERE numero = ?";
    
        try (PreparedStatement statement = conexion.prepareStatement(consultaPrecio)) {
            statement.setInt(1, IDEquipamientos);
            ResultSet resultado = statement.executeQuery();
        
            if (resultado.next()) { 
                precio = resultado.getFloat("precio");
            } else {
                System.out.println("No se encontró equipamiento con ID: " + IDEquipamientos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
                                
        return precio;
    }    

    public void showEquipoRentas(int IDRenta){

        Connection conexion = null;
        String consultaEquipos = "SELECT " + 
                            "   DATE_FORMAT(r.fechaReservacion, '%y-%m-%d') AS FechaReservacion, " +
                            "   s.nombre AS NombreSalon, " +
                            "   eq.descripcion AS DescripcionEquipo, " +
                            "   eq.precio AS CostoEquipo " +
                            "FROM renta AS r " +
                            "INNER JOIN cliente AS c ON r.cliente = c.numero " +
                            "INNER JOIN salon AS s ON r.salon = s.numero " +
                            "INNER JOIN equipos_renta AS er ON r.numero = er.renta " +
                            "INNER JOIN equipamiento AS eq ON er.equipamiento = eq.numero " +
                            "WHERE r.numero = ?;";

        conexion = ConexionBD.obtenerConexion();

        try (PreparedStatement statement = conexion.prepareStatement(consultaEquipos)){
                statement.setInt(1, IDRenta);
                ResultSet resultado = statement.executeQuery();

                System.out.println("===================================================================================");
            System.out.printf("| %-10s | %-20s | %-30s | %-10s |\n", 
                            "Fecha", "Salon", "Descripción", "Precio");
            System.out.println("===================================================================================");

            boolean valid = false;

            while (resultado.next()) {
                valid = true;

                String FechaReservacion = resultado.getString("FechaReservacion");
                String NombreSalon = resultado.getString("NombreSalon");
                String DescripcionServicio = resultado.getString("DescripcionEquipo");
                float CostoServicio = resultado.getFloat("CostoEquipo");

                System.out.printf("| %-10s | %-20s | %-30s | %-10.2f |\n", 
                                FechaReservacion, NombreSalon, DescripcionServicio, CostoServicio);
            }

            if (!valid) {
                System.out.println("|                                       No tienes servicios aún                                                   |");
            }else{
                System.out.println("===================================================================================");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
