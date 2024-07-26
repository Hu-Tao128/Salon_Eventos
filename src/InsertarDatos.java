import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertarDatos {
    private static final String URL = "jdbc:mysql://localhost:3306/salent";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "";

    public void insertarDatosSalon() {
        try {
            // Establecer la conexión
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            System.out.println("Conexión exitosa a la base de datos");

            // Solicitar datos al usuario
            Scanner insert = new Scanner(System.in);
            
            System.out.println("Ingrese el nombre del evento:");
            String nombre = insert.nextLine();

            // Crear la consulta preparada
            String consulta = "INSERT INTO evento (numero, nombre) VALUES (null, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            //statement.setInt(1, id);
            statement.setString(1, nombre);

            // Ejecutar la consulta
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Los datos se han insertado correctamente en la tabla salones.");
            } else {
                System.out.println("No se pudo insertar los datos en la tabla salones.");
            }

            // Cerrar recursos
            statement.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        InsertarDatos data = new InsertarDatos();
        data.insertarDatosSalon();
    }
}
