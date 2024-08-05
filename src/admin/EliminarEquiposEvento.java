package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class EliminarEquiposEvento {

    private int IDEventoE;
    private int IDEquipamientoE; 

    private String respuesta = "";

    public void deleteEquiposEvento(){
        Connection conexion = null;

        Scanner Leer = new Scanner(System.in);
        MostrarMontajeEvento montaje = new MostrarMontajeEvento();


        System.out.println("Eliga el evento que sera eliminado");
        montaje.showMontajeEvento();
        IDEventoE = montaje.elegirMontajeEvento();

        System.out.println("Eliga el equipamiento que acompaña al evento");
        IDEquipamientoE = montaje.elegirEventoMontaje();

        System.out.println("Esta seguro de realizar la eleminacion? (s/n)");
        respuesta = Leer.nextLine();

        if(respuesta.equals("s")){
            try {
                conexion = ConexionBD.obtenerConexion();
                String sql = "DELETE FROM equipos_evento WHERE evento = ? and equipamiento = ?";
                PreparedStatement statement = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setInt(1, IDEventoE);
                statement.setInt(2, IDEquipamientoE);

                int rowsAffected = statement.executeUpdate();

                if(rowsAffected > 0){
                    System.out.println("Regsitro eliminado correctamente");
                }else{
                    System.out.println("No se encontro un registro con ese identificador");
                }

            } catch (SQLException e) {
                System.out.println("Error al realizar la eliminacion: " + e.getMessage());
            } finally {
                ConexionBD.cerrarConexion(conexion);
            }
        }else{
            System.out.println("Cerrando apartado de eliminacion de un evento");
        }
    }

    /* 
    public void mostrarEquiposEvento(int IDEventoE){
        Connection conexion = null;
       try {
           // Obtener la conexión
           conexion = ConexionBD.obtenerConexion();
           
           // Ejemplo de consulta
           String consulta = "SELECT * FROM equipos_evento WHERE evento = ?";
           //la consulta se hace en tipo string que pasa a instruccion en el statement
           PreparedStatement statement = conexion.prepareStatement(consulta);
           ResultSet resultado = statement.executeQuery();
           statement.setInt(1, IDEventoE);

           System.out.printf("%-10s %-10s\n", 
           "Evento", "Equipamiento");

               while (resultado.next()) {
               // Obtener datos
               int evento = resultado.getInt("evento");
               int equipamiento = resultado.getInt("equipamiento");  

               // Imprimir datos en forma de tabla
               System.out.printf("%-10d %-10d\n", 
                       evento, equipamiento);
               }

       } catch (SQLException e) {
           System.out.println("Error en la consulta: " + e.getMessage());
       } finally {
           // Cerrar la conexión
           ConexionBD.cerrarConexion(conexion);
       }
   }

   */
}
