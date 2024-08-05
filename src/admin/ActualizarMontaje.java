package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class ActualizarMontaje {
    private int IDMontaje;

    public void updateMontaje(){
        Connection connection = null;

        MostrarMontaje montaje = new MostrarMontaje();

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String opcion3 = "";
        String nombreMontaje = "";
        String descripcion = "";      

        System.out.println("Modificacion de un montaje");

        System.out.println("Seleccione el montaje que sera modificado");
        montaje.showMontaje();
        IDMontaje = montaje.elegirMontaje();

        System.out.println("Esta seguro de querer actualizar toda la informacion del montaje numero " + IDMontaje + " (s/n)");
        opcion3 = datos.nextLine();

        if(opcion3.equals("s")){
            do {
           
                System.out.println("Actualice el nombre del montaje");
                nombreMontaje = datos.nextLine();
                
                System.out.println("Actualice la descripcion del montaje");
                descripcion = datos.nextLine();

                System.out.println("Escribio bien los datos? (s/n)");
                opcion = datos.next();
    
                if(opcion.equals("s")){
                    opcion2 = "s";
                }
                
            } while (!opcion2.equals("s"));
    
            try {
    
                connection = ConexionBD.obtenerConexion();
                
                String updateMontaje = "UPDATE montaje SET nombreMontaje = ?, descripcion = ? WHERE numero = ?";
                PreparedStatement statement = connection.prepareStatement(updateMontaje);
                statement.setString(1, nombreMontaje);
                statement.setString(2, descripcion);
                statement.setInt(3, IDMontaje);

                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han actualizado correctamente en la tabla montaje.");
                } else {
                    System.out.println("No se pudo acctualizar los datos en la tabla montaje.");
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
