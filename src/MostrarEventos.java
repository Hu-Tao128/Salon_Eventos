import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MostrarEventos {
    public void showEventos() {
        Scanner leer = new Scanner(System.in);
        int opcion;
        int evento = 0;
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM evento";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.println("Escoge tu evento:");
            // Iterar sobre el resultado
            while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String nombre = resultado.getString("nombre");
                System.out.println("Numero: " + numero + ", Nombre: " + nombre);

            }
            try {
                System.out.println("Ingrese el número del evento:");
                evento = leer.nextInt();
            } catch (Exception e) {
                System.out.println("Use números por favor");
                return; // Salir si ocurre una excepción
            }
    
            do {
                System.out.println("Desea agregar algún Servicio, Montaje o Equipamiento?");
                System.out.println("1) Servicio");
                System.out.println("2) Montaje");
                System.out.println("3) Equipamiento");
                System.out.println("0) No");
                opcion = leer.nextInt();
    
                switch (opcion) {
                    case 1:
                        // Consulta para Servicios
                        String consultaServicios = "SELECT\n" +
                                                   "    nombreServicio AS Nombre,\n" +
                                                   "    descripcion AS Descripcion,\n" +
                                                   "    precio AS Precio,\n" +
                                                   "    disponibilidad AS Disponibilidad\n" +
                                                   "FROM servicios;";
                        
                        try (PreparedStatement statement1 = conexion.prepareStatement(consultaServicios)) {
                            ResultSet resultado1 = statement1.executeQuery();
                            while (resultado1.next()) {
                                String nombre = resultado1.getString("Nombre");
                                String descripcion = resultado1.getString("Descripcion");
                                float precio = resultado1.getFloat("Precio");
                                int dis = resultado1.getInt("Disponibilidad"); 
                                String disponibilidad;

                                    if (dis == 0) {
                                        disponibilidad = "No Disponible";
                                    }else {
                                        disponibilidad = "Disponible";
                                    }
                    
                                System.out.println("Nombre: " + nombre + ", Descripción: " + descripcion + ", Precio: " + precio + ", Disponibilidad: " + disponibilidad);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
    
                    case 2:
                        // Consulta para Montaje
                        String consultaMontaje = "SELECT \n" +
                                                    "    m.nombreMontaje AS NombreMontaje, \n" +
                                                    "    m.descripcion AS Descripcion \n" +
                                                    "FROM montaje_evento AS me \n" +
                                                    "INNER JOIN montaje AS m ON me.montaje = m.numero \n" +
                                                    "WHERE me.evento = ?;";
                        
                        try (PreparedStatement statement2 = conexion.prepareStatement(consultaMontaje)) {
                            statement2.setInt(1, evento);
                            ResultSet resultado2 = statement2.executeQuery();
                           
                            while (resultado2.next()) {
                                String montaje = resultado2.getString("NombreMontaje");
                                String descripcion = resultado2.getString("Descripcion");
                                System.out.println("Montaje: " + montaje + ", Descripción: " + descripcion);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
    
                    case 3:
                        // Consulta para Equipamiento
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

                            while (resultado3.next()) {
                                String nombreEquipamiento = resultado3.getString("NombreEquipamiento");
                                float precio = resultado3.getFloat("Precio");
                                int stock = resultado3.getInt("Stock");
                                System.out.println("Nombre del Equipamiento: " + nombreEquipamiento + ", Precio: " + precio + ", Stock: " + stock);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
    
                    default:
                        break;
                }
    
            } while (opcion != 0);
              

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
            leer.close();
        }
    }
}
