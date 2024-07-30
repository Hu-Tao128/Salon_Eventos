package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexionDB.ConexionBD;

public class MostrarRenta {
    public void showRentas(){
        Connection conexion = null;
        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();
            
            // Ejemplo de consulta
            String consulta = "SELECT * FROM renta";
            //la consulta se hace en tipo string que pasa a instruccion en el statement
            PreparedStatement statement = conexion.prepareStatement(consulta);
            ResultSet resultado = statement.executeQuery();
            
            System.out.printf("%-10s %-30s %-40s %-40s %-30s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n", 
            "Numero", "Fecha reservacion", "Fecha de inicio", "Fecha de finalizacion", "Hora de inicio", "Hora de finalizacion", "IVA", "subtotal", "Total", "Montaje", "Salon", "Cliente", "Evento");

                while (resultado.next()) {
                // Obtener datos
                int numero = resultado.getInt("numero");
                String fechaReservacion = resultado.getString("fechaReservacion");
                String fechaInicio = resultado.getString("fechaInicio");
                String fechaFinal = resultado.getString("fechaFinal");
                String horaInicio = resultado.getString("horaInicio");
                String horaFinal = resultado.getString("horaFinal");
                double IVA = resultado.getDouble("IVA");
                double subtotal = resultado.getDouble("subtotal");
                double total = resultado.getDouble("total");
                int montaje = resultado.getInt("montaje");
                int salon = resultado.getInt("salon");
                int cliente = resultado.getInt("cliente");
                int evento = resultado.getInt("evento");
                

                // Imprimir datos en forma de tabla
                System.out.printf("%-10d %-20s %-20s %-20s %-20s %-20s %-20.0f %-20.0f %-20.0f %-10d %-10d %-10d %-10d\n", 
                                numero, fechaReservacion, fechaInicio, fechaFinal, horaInicio, horaFinal, IVA, subtotal, total, montaje, salon, cliente, evento);
                }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
