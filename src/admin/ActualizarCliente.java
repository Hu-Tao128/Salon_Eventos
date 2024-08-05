package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class ActualizarCliente {

    private int IDCliente;

    public void updateCliente(){
        Connection connection = null;

        MostrarClientes cliente = new MostrarClientes();

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String opcion3 = "";
        String nombreFiscal = "";
        String nomContacto = "";
        String primerApellido = "";        
        String segundoApellido = "";
        String numTel = "";
        String email = "";

        System.out.println("Modificacion de un cliente");

        System.out.println("Seleccione el cliente que sera modificado");
        cliente.showClientes();
        IDCliente = cliente.elegirCliente();

        System.out.println("Esta seguro de querer actualizar toda la informacion del cliente numero " + IDCliente + " (s/n)");
        opcion3 = datos.nextLine();

        if(opcion3.equals("s")){
            do {
           
                System.out.println("Actualice el nombre fiscal del cliente");
                nombreFiscal = datos.nextLine();
                
                System.out.println("Actualice el nombre del contacto del cliente (Sin apellidos)");
                nomContacto = datos.nextLine();
    
                System.out.println("Actualice el primer apellido del contacto del cliente");
                primerApellido = datos.nextLine();
    
                System.out.println("Actualice el segundo apellido del contacto del cliente");
                segundoApellido = datos.nextLine();
    
                System.out.println("Actualice el numero de celular del cliente");
                numTel = datos.nextLine();
    
                System.out.println("Actualice correo electronico del cliente");
                email = datos.nextLine();
    
                System.out.println("Escribio bien los datos? (s/n)");
                opcion = datos.next();
    
                if(opcion.equals("s")){
                    opcion2 = "s";
                }
                
            } while (!opcion2.equals("s"));
    
            try {
    
                connection = ConexionBD.obtenerConexion();
                
                String updateCliente = "UPDATE cliente SET nombreFiscal = ?, nomContacto = ?, primerApellido = ?, segundoApellido = ?, numTel = ?, email = ? WHERE numero = ?";
                PreparedStatement statement = connection.prepareStatement(updateCliente);
                statement.setString(1, nombreFiscal);
                statement.setString(2, nomContacto);
                statement.setString(3, primerApellido);
                statement.setString(4, segundoApellido);
                statement.setString(5, numTel);
                statement.setString(6, email);
                statement.setInt(7, IDCliente);
 
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han actualizado correctamente en la tabla cliente.");
                } else {
                    System.out.println("No se pudo acctualizar los datos en la tabla cliente.");
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
