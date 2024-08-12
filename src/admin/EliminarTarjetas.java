package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class EliminarTarjetas {
    private int IDTarjeta;
    private String respuesta = "";

    public void deleteTarjeta(){
        Connection conexion = null;

        Scanner Leer = new Scanner(System.in);
        MostrarTarjetas tarjeta = new MostrarTarjetas();



        
        tarjeta.showTarjetas();
        System.out.println("Eliga el montaje que sera eliminado");
        IDTarjeta = tarjeta.elegirTarjeta();

        System.out.println("Esta seguro de realizar la eleminacion? (s/n)");
        respuesta = Leer.nextLine();

        if(respuesta.equals("s")){
            try {
                conexion = ConexionBD.obtenerConexion();
                String sql = "DELETE FROM tarjetas WHERE numero = ?";
                PreparedStatement statement = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setInt(1, IDTarjeta);

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
            System.out.println("Cerrando apartado de eliminacion de un montaje");
        }
    }
}
