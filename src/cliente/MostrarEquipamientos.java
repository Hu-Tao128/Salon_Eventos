package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class MostrarEquipamientos {
    private Scanner Leer = new Scanner(System.in);

    private int cantidad = 0;
    private int ID = -1;
    private float precio = 0f;

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

        do {
            System.out.println("Ingresar el número del equipamiento:");
            try {
                ID = Leer.nextInt();
                Leer.nextLine();
                if (ID <= 0) {
                    System.out.println("El número del equipamiento debe ser un número positivo.");
                    ID = -1; 
                }
            } catch (InputMismatchException e) {
                System.out.println("Ingrese números por favor.");
                Leer.nextLine();
            }
        } while (ID <= 0);

        return ID;
    }

    public int getCantidad(int IDEquipamientos) {
        Connection conexion = null;

        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            do {
                System.out.println("¿Cuál es la cantidad que va a seleccionar?");
                try {
                    cantidad = Leer.nextInt();
                } catch (Exception e) {
                    System.out.println("Ingrese números válidos.");
                    Leer.next(); // Limpiar el buffer del scanner
                    cantidad = 0;
                }

                if (cantidad > 0) {
                    String consultaCantidad = "SELECT stock FROM equipamiento WHERE numero = ?";

                    try (PreparedStatement statement = conexion.prepareStatement(consultaCantidad)) {
                        statement.setInt(1, IDEquipamientos);
                        ResultSet resultado = statement.executeQuery();

                        if (resultado.next()) {
                            int cantidadTotal = resultado.getInt("stock");

                            if (cantidad > cantidadTotal) {
                                System.out.println("Está eligiendo más equipos de los disponibles.");
                                cantidad = 0;
                            }
                        } else {
                            System.out.println("No se encontró equipamiento con el ID especificado.");
                            cantidad = 0;
                        }
                    }
                }
            } while (cantidad <= 0);

        } catch (SQLException e) {
            e.printStackTrace();
            cantidad = 0;
        }

        return cantidad;
    }

    public float getPrecio(int IDEquipamientos) {
        Connection conexion = null;
    
            conexion = ConexionBD.obtenerConexion();
            
            String consultaPrecio = "SELECT precio FROM equipamiento WHERE numero = ?";
    
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

    public void showEquipoRentas(int IDRenta) {
        Connection conexion = null;
        String consultaEquipos = "SELECT\n" + 
                                 "    DATE_FORMAT(r.fechaReservacion, '%y-%m-%d') AS FechaReservacion,\n" + 
                                 "    s.nombre AS NombreSalon,\n" + 
                                 "    eq.descripcion AS DescripcionEquipo,\n" + 
                                 "    eq.precio AS CostoEquipo\n" + 
                                 "FROM renta AS r\n" + 
                                 "INNER JOIN salon AS s ON r.salon = s.numero\n" + 
                                 "INNER JOIN equipos_renta AS er ON r.numero = er.renta\n" + 
                                 "INNER JOIN equipamiento AS eq ON er.equipamiento = eq.numero\n" + 
                                 "WHERE r.numero = ?;";
    
        conexion = ConexionBD.obtenerConexion();
    
        try (PreparedStatement statement = conexion.prepareStatement(consultaEquipos)) {
            statement.setInt(1, IDRenta);
            ResultSet resultado = statement.executeQuery();
    
            System.out.println("\n========================================================================");
            boolean valid = false;
    
            while (resultado.next()) {
                if (!valid) {
                    System.out.printf("| %-25s | %-40s |\n", "Fecha de Reservación", resultado.getString("FechaReservacion"));
                    System.out.printf("| %-25s | %-40s |\n", "Nombre del Salón", resultado.getString("NombreSalon"));
                    System.out.println("========================================================================");
                    valid = true;
                }
    
                String DescripcionEquipo = resultado.getString("DescripcionEquipo");
                float CostoEquipo = resultado.getFloat("CostoEquipo");
    
                System.out.printf("| %-25s | %-40s |\n", "Descripción del Equipo", DescripcionEquipo);
                System.out.printf("| %-25s | %-40.2f |\n", "Costo del Equipo", CostoEquipo);
                System.out.println("------------------------------------------------------------------------");
            }
    
            if (!valid) {
                System.out.println("|                                         No se encontraron equipos para esta renta                                       |");
            } else {
                System.out.println("========================================================================");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

}
