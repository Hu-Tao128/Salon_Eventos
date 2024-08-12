package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import cliente.MostrarServicios;
import conexionDB.ConexionBD;

public class EliminarTiposServicios {
    private int IDTipoServicio;
    private String respuesta = "";

    public void deleteTiposServicios(){
        Connection conexion = null;

        Scanner Leer = new Scanner(System.in);
        MostrarTipoServicio servicioT = new MostrarTipoServicio();

        servicioT.showTipoServicio();
        System.out.println("Eliga el tipo de servicio que sera eliminado");
        IDTipoServicio = servicioT.elegirTipoServicio();

        System.out.println("Esta seguro de realizar la eliminacion? (s/n)");
        respuesta = Leer.nextLine();

        if(respuesta.equals("s")){
            try {
                conexion = ConexionBD.obtenerConexion();
                String sql = "DELETE FROM tipo_servicios WHERE numero = ?";
                PreparedStatement statement = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setInt(1, IDTipoServicio);

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
            System.out.println("Cerrando apartado de eliminacion de un tipo de servicio");
        }
    }
}   
