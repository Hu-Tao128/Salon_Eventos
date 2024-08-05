package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import cliente.MostrarSalones;
import conexionDB.ConexionBD;

public class ActualizarSalon {
    private int IDSalon;

    public void updateSalon(){
        Connection connection = null;

        MostrarSalones salon = new MostrarSalones();

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String opcion3 = "";
        String nombre = "";      
        String dirCalle = "";
        String dirColonia = "";
        String dirNumero = "";
        int capacidad = 0;
        double tamanio = 0.0;
        double precio = 0.0;

        System.out.println("Modificacion de un salon");

        System.out.println("Seleccione el salon que sera modificado");
        salon.showSalones();
        IDSalon = salon.elegirSalon();

        System.out.println("Esta seguro de querer actualizar toda la informacion del salon numero " + IDSalon + " (s/n)");
        opcion3 = datos.nextLine();

        if(opcion3.equals("s")){
            do {
           
                System.out.println("Actualice el nombre del salon");
                nombre = datos.nextLine();

                System.out.println("Actualice la calle en donde se encuenta el salon");
                dirCalle = datos.nextLine();

                System.out.println("Actualice la colonia en donde se encuenta el salon");
                dirColonia = datos.nextLine();

                System.out.println("Actualice el numero interior del salon");
                dirNumero = datos.nextLine();

                System.out.println("Actualice la capacidad del salon");
                capacidad = datos.nextInt();

                System.out.println("Actualice el tamaño en metros cuadrados del salon");
                tamanio = datos.nextDouble();

                System.out.println("Actualice el precio del salon");
                precio = datos.nextDouble();
    
                System.out.println("Escribio bien los datos? (s/n)");
                opcion = datos.next();
    
                if(opcion.equals("s")){
                    opcion2 = "s";
                }
                
            } while (!opcion2.equals("s"));
    
            try {
    
                connection = ConexionBD.obtenerConexion();
                
                String updateSalon = "UPDATE salon SET nombre = ?, dirCalle = ?, dirColonia = ?, dirNumero = ?, capacidad = ?, tamanio = ?, precio = ? WHERE numero = ?";
                PreparedStatement statement = connection.prepareStatement(updateSalon);
                statement.setString(1, nombre);
                statement.setString(2, dirCalle);
                statement.setString(3, dirColonia);
                statement.setString(4, dirNumero);
                statement.setInt(5, capacidad);
                statement.setDouble(6, tamanio);
                statement.setDouble(7, precio);
                statement.setInt(8, IDSalon);
 
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han actualizado correctamente en la tabla salon.");
                } else {
                    System.out.println("No se pudo acctualizar los datos en la tabla salon.");
                }
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
            }finally {
                // Cerrar la conexión
                ConexionBD.cerrarConexion(connection);
            }
        }
    }
}
