package conexionDB;

import java.sql.Connection;
//para establecer la clase de conexion
import java.sql.DriverManager;
//para leer el controlador de SQL, el JDBC
import java.sql.SQLException;

public class ConexionBD {
    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:mysql://172.17.20.6:3306/salent";
    private static final String USUARIO = "luis";
    private static final String CONTRASEÑA = "papa";

    // Método para establecer la conexión
    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            // Registrar el controlador o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            //System.out.println("Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador JDBC: " + e.getMessage());
            //en caso que no encuentre el controlador o driver
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            //si la sintaxis o conexion a la base de datos esta mal
        }
        return conexion;
        //retorna la conexion establecida
    }

    // Método para cerrar la conexión
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            //cuando la conexion este abierta, osea no es nula
            try {
                conexion.close();
                //System.out.println("Conexión cerrada correctamente");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
