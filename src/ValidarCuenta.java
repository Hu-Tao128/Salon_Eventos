import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ValidarCuenta {
    private int NoCliente = 0;
    private String Nombres;
    private String Apellido;
    private String nombreFiscal;

    public int ValidarCuenta(int id){
        Connection connection = null;

        try {
            connection = ConexionBD.obtenerConexion();
            
            String consulta = "SELECT\n" +
                              "    numero AS NoCliente,\n" +
                              "    nomContacto AS Nombres,\n" +
                              "    primerApellido AS Apellido\n" +
                              "FROM cliente\n" +
                              "WHERE numero = ?";
            
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setInt(1, id);
            ResultSet resultado = statement.executeQuery();
    
            if (resultado.next()) { 
                String Nombres = resultado.getString("Nombres");
                String Apellido = resultado.getString("Apellido");
                NoCliente = resultado.getInt("NoCliente");
                
                System.out.println("Bienvenido " + Nombres + " " + Apellido);
            } else {
                System.out.println("Usuario no encontrado");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return NoCliente;
    }

    public int ValidarNombre(String Nombre, String Apellido){
        Connection connection = null;

        try {
            connection = ConexionBD.obtenerConexion();
            
            String consulta = "SELECT\n" +
                                "    numero AS NoCliente,\n" +
                                "    nomContacto AS Nombres,\n" +
                                "    primerApellido AS Apellido\n" +
                                "FROM cliente\n" +
                                "WHERE Nombres = ? AND Apellido = ?";
            
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, Nombre);
            statement.setString(2, Apellido);
            ResultSet resultado = statement.executeQuery();

            Nombres = resultado.getString("consulta");
            Apellido = resultado.getString("Apellido");
            NoCliente = resultado.getInt("NoCliente");

            System.out.println("Bienvenido " + Nombres + " " + Apellido);

        } catch (Exception e) {
            System.out.println("Usuario no encontrado");
        }

        return NoCliente;
    }

    public int ValidarNombreFiscal(String NombreFiscal){
        Connection connection = null;

        try {
            connection = ConexionBD.obtenerConexion();
            
            String consulta = "SELECT\n" +
                                "    numero AS NoCliente,\n" +
                                "    nombreFiscal AS nombreFiscal,\n" +
                                "    nomContacto AS Nombres,\n" +
                                "    primerApellido AS Apellido\n" +
                                "FROM cliente\n" +
                                "WHERE nombreFiscal = ?";
            
            PreparedStatement statement = connection.prepareStatement(consulta);
            statement.setString(1, NombreFiscal);
            ResultSet resultado = statement.executeQuery();
            
            Nombres = resultado.getString("consulta");
            Apellido = resultado.getString("Apellido");
            nombreFiscal = resultado.getString("nombreFiscal");
            NoCliente = resultado.getInt("NoCliente");

            System.out.println("Bienvenido " + Nombres + " " + Apellido + " Representante de " + nombreFiscal);

        } catch (Exception e) {
            System.out.println("Usuario no encontrado");
        }

        return NoCliente;
    }
}
