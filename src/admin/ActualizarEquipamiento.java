package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class ActualizarEquipamiento {
    private int IDEquipamiento;

    public void updateEquipamiento(){
        Connection connection = null;

        MostrarEquipamiento equipamiento = new MostrarEquipamiento();

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String opcion3 = "";
        String descripcion = "";
        double precio = 0.0;
        int stock = 0;        

        System.out.println("Modificacion de un equipamiento");

        System.out.println("Seleccione el equipamiento que sera modificado");
        equipamiento.showEquipamiento();
        IDEquipamiento = equipamiento.elegirEquipamiento();

        System.out.println("Esta seguro de querer actualizar toda la informacion del equipamiento numero " + IDEquipamiento + " (s/n)");
        opcion3 = datos.nextLine();

        if(opcion3.equals("s")){
            do {
           
                System.out.println("Actualice la descripcion del equipamiento");
                descripcion = datos.nextLine();
                
                System.out.println("Actualice el precio del equipamiento");
                precio = datos.nextDouble();
    
                System.out.println("Actualice el stock del equipamiento");
                stock = datos.nextInt();
    
                System.out.println("Escribio bien los datos? (s/n)");
                opcion = datos.next();
    
                if(opcion.equals("s")){
                    opcion2 = "s";
                }
                
            } while (!opcion2.equals("s"));
    
            try {
    
                connection = ConexionBD.obtenerConexion();
                
                String updateEquipamiento = "UPDATE equipamiento SET descripcion = ?, precio = ?, stock = ? WHERE numero = ?";
                PreparedStatement statement = connection.prepareStatement(updateEquipamiento);
                statement.setString(1, descripcion);
                statement.setDouble(2, precio);
                statement.setInt(3, stock);
                statement.setInt(4, IDEquipamiento);
 
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han actualizado correctamente en la tabla equipamiento.");
                } else {
                    System.out.println("No se pudo acctualizar los datos en la tabla equipamiento.");
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
