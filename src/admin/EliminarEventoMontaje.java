package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class EliminarEventoMontaje {
    private int IDEventoM;
    private int IDMontajeE;

    

    private String respuesta = "";

    public void deleteEventoMontaje(){
        Connection conexion = null;

        Scanner Leer = new Scanner(System.in);
        MostrarMontajeEvento montaje = new MostrarMontajeEvento();

        System.out.println("Eliga el evento que sera eliminado");
        montaje.showMontajeEvento();
        IDEventoM = montaje.elegirMontajeEvento();

        System.out.println("Eliga el montaje que acompaña al evento");
        IDMontajeE = montaje.elegirEventoMontaje();

        System.out.println("Esta seguro de realizar la eleminacion? (s/n)");
        respuesta = Leer.nextLine();

        if(respuesta.equals("s")){
            try {
                conexion = ConexionBD.obtenerConexion();
                String sql = "DELETE FROM montaje_evento WHERE evento = ? and montaje = ?";
                PreparedStatement statement = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setInt(1, IDEventoM);
                statement.setInt(2, IDMontajeE);

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
    public void mostrarMontajeEventoEsp(int IDEventoM){
        Connection conexion = null;
       try {
           // Obtener la conexión
           conexion = ConexionBD.obtenerConexion();
           
           // Ejemplo de consulta
           String consulta = "SELECT * FROM montaje_evento WHERE evento = ?";
           //la consulta se hace en tipo string que pasa a instruccion en el statement
           PreparedStatement statement = conexion.prepareStatement(consulta);
           ResultSet resultado = statement.executeQuery();
           statement.setInt(1, IDEventoM);

           System.out.printf("%-10s %-10s\n", 
           "Evento", "Montaje");

               while (resultado.next()) {
               // Obtener datos
               int evento = resultado.getInt("evento");
               int montaje = resultado.getInt("montaje");  

               // Imprimir datos en forma de tabla
               System.out.printf("%-10d %-10d\n", 
                       evento, montaje);
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
