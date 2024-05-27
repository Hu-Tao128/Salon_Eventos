import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertarDatos {
    private static final String URL = "jdbc:mysql://localhost:3306/salon_glanm";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "";

    public void insertarDatosSalon() {
        try {
            // Establecer la conexión
            Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            System.out.println("Conexión exitosa a la base de datos");

            // Solicitar datos al usuario
            Scanner insert = new Scanner(System.in);
            System.out.println("Ingrese el ID del salón:");
            int id = insert.nextInt();
            insert.nextLine(); // Consumir el salto de línea pendiente
            
            System.out.println("Ingrese el nombre del salón:");
            String nombre = insert.nextLine();

            System.out.println("Ingrese el precio:");
            double precio = insert.nextDouble();
            insert.nextLine(); // Consumir el salto de línea pendiente
            
            System.out.println("Ingrese los servicios:");
            String servicios = insert.nextLine();
            
            System.out.println("Ingrese el patrón de mesas:");
            String patronMesas = insert.nextLine();

            // Crear la consulta preparada
            String consulta = "INSERT INTO salones (id, nombre, precio, servicios, acomodo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, id);
            statement.setString(2, nombre);
            statement.setDouble(3, precio);
            statement.setString(4, servicios);
            statement.setString(5, patronMesas);

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
}
