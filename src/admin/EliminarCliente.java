package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class EliminarCliente {

    private int IDCliente;
    private String respuesta = "";

    public void deleteCliente(){
        Connection conexion = null;

        Scanner Leer = new Scanner(System.in);
        MostrarClientes clientes = new MostrarClientes();


        System.out.println("Eliga al cliente que sera eliminado");
        clientes.showClientes();
        IDCliente = clientes.elegirCliente();

        System.out.println("Esta seguro de realizar la eleminacion? (s/n)");
        respuesta = Leer.nextLine();

        if(respuesta.equals("s")){
            try {
                conexion = ConexionBD.obtenerConexion();
                String sql = "DELETE FROM cliente WHERE numero = ?";
                PreparedStatement statement = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setInt(1, IDCliente);

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
            System.out.println("Cerrando apartado de eliminacion de un cliente");
        }
    }
}
