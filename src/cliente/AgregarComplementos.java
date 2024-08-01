package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class AgregarComplementos {
    private int IDServicios;
    private int IDEquipamientos;
    private int cantidad;
    private float precio;
    private int opcion;

    Scanner Leer = new Scanner(System.in);
    MostrarServicios servicios = new MostrarServicios();
    MostrarEquipamientos equipamiento = new MostrarEquipamientos();
    Reservaciones salon = new Reservaciones();

    public void menuComplementos(int IDRenta, int IDEvento) {
        do {
            try {
                System.out.println("Desea agregar algún Servicio o Equipamiento?");
                System.out.println("1) Servicio");
                System.out.println("2) Equipamiento");
                System.out.println("0) No, gracias");
                opcion = Leer.nextInt();
                Leer.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        servicios.showServicios();
                        IDServicios = servicios.elegirServicio();

                        if (IDServicios > 0) {
                            AgregarServRentas(IDServicios, IDRenta);
                        } else {
                            System.out.println("ID de servicio inválido.");
                        }
                        break;

                    case 2:
                        equipamiento.showEquipamientos(IDEvento);
                        IDEquipamientos = equipamiento.elegirEquipamiento();
                        cantidad = equipamiento.getCantidad(IDEquipamientos);
                        precio = equipamiento.getPrecio(IDEquipamientos);

                        if (precio > 0 && cantidad > 0) {
                            AgregarEquipRenta(IDEquipamientos, IDRenta, cantidad, precio);
                        } else {
                            System.out.println("Ha ocurrido un error al agregar el equipamiento.");
                        }
                        break;

                    case 0:
                        System.out.println("Está bien, prosigamos.");
                        break;

                    default:
                        System.out.println("Opción no válida, intente de nuevo.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida, por favor ingrese un número.");
                Leer.nextLine(); // Limpiar buffer
            }
        } while (opcion != 0);
    }

    public void AgregarServRentas(int IDServicios, int IDRenta) {
        String obtenerPrecioServicio = "SELECT precio FROM servicios WHERE numero = ?";
        String insertServiciosRenta = "INSERT INTO servicios_renta (servicios, renta) VALUES (?, ?)";
        String actualizarRenta = "UPDATE renta SET subtotal = ?, IVA = ?, total = ? WHERE numero = ?";
    
        Connection conexion = null;
    
        try {
            conexion = ConexionBD.obtenerConexion();
            
            // Obtener el precio del servicio
            float precioServicio = 0;
            float subtotal;
            float IVA;
            float total;
            
            try (PreparedStatement statementPrecio = conexion.prepareStatement(obtenerPrecioServicio)) {
                statementPrecio.setInt(1, IDServicios);
                try (ResultSet resultado = statementPrecio.executeQuery()) {
                    if (resultado.next()) {
                        precioServicio = resultado.getFloat("precio");
                    } else {
                        System.out.println("No se encontró el servicio con ID: " + IDServicios);
                        return; // Salir del método si no se encuentra el servicio
                    }
                }
            }
    
            // Obtener el subtotal actual
            subtotal = salon.getTotal(IDRenta);
            
            // Calcular nuevos valores
            subtotal += precioServicio;
            IVA = subtotal * 0.16f;
            total = subtotal + IVA;
    
            // Insertar el registro en la tabla servicios_renta
            try (PreparedStatement statementInsert = conexion.prepareStatement(insertServiciosRenta)) {
                statementInsert.setInt(1, IDServicios);
                statementInsert.setInt(2, IDRenta);
                int filasInsertadas = statementInsert.executeUpdate();
    
                if (filasInsertadas > 0) {
                    System.out.println("Inserción exitosa de servicio en su renta.");
                } else {
                    System.out.println("No se pudo insertar el servicio en la renta.");
                    return; // Salir del método si no se pudo insertar el servicio
                }
            }
    
            // Actualizar la tabla renta con los nuevos valores
            try (PreparedStatement statementActualizar = conexion.prepareStatement(actualizarRenta)) {
                statementActualizar.setFloat(1, subtotal);
                statementActualizar.setFloat(2, IVA);
                statementActualizar.setFloat(3, total);
                statementActualizar.setInt(4, IDRenta);
                int filasActualizadas = statementActualizar.executeUpdate();
    
                if (filasActualizadas > 0) {
                    System.out.println("Actualización exitosa de la renta.");
                } else {
                    System.out.println("No se pudo actualizar la renta.");
                }
            }
    
        } catch (SQLException e) {
            System.out.println("Error al procesar la solicitud: " + e.getMessage());
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
    

    public void AgregarEquipRenta(int IDEquipamientos, int IDRenta, int cantidad, float precio) {
        float importe = cantidad * precio;
        String insertEquiposRenta = "INSERT INTO equipos_renta (equipamiento, renta, cantidad, importe) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement statement = conexion.prepareStatement(insertEquiposRenta)) {

            statement.setInt(1, IDEquipamientos);
            statement.setInt(2, IDRenta);
            statement.setInt(3, cantidad);
            statement.setFloat(4, importe);
            int filasInsertadas = statement.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println("Inserción exitosa de los equipos a su evento.");
            } else {
                System.out.println("No se pudo insertar el equipamiento en la renta.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar el equipamiento: " + e.getMessage());
        }
    }
}
