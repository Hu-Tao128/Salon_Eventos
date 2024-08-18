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
    Reservaciones salon = new Reservaciones();

    public float menuComplementos(int IDRenta, int IDEvento, float total) {
        
        do {
            System.out.println("Su total es de " + total);
            System.out.println("¿Desea agregar algún Servicio o Equipamiento?");
            System.out.println("1) Agregar Servicio");
            System.out.println("2) Agregar Equipamiento");
            System.out.println("3) Mostrar Servicios de mi renta");
            System.out.println("4) Mostrar Equipamiento de mi renta");
            System.out.println("0) No, gracias");
            opcion = Leer.nextInt();
    
            switch (opcion) {
                case 1:
                    servicios.showServicios();
                    int IDServicios = servicios.elegirServicio();
    
                    if (IDServicios > 0) {
                        AgregarServRentas(IDServicios, IDRenta);
                        float totalServicio = getTotalServicio(IDServicios);
                        total += totalServicio; 
                    } else {
                        System.out.println("ID de servicio inválido.");
                    }
                    break;
    
                case 2:
                    MostrarEquipamientos equipamiento = new MostrarEquipamientos();
                    equipamiento.showEquipamientos(IDEvento);
                    int IDEquipamientos = equipamiento.elegirEquipamiento();
    
                    int cantidad = equipamiento.getCantidad(IDEquipamientos);
                    float precio = equipamiento.getPrecio(IDEquipamientos);
    
                    if (cantidad > 0 && IDEquipamientos > 0) {
                        AgregarEquipRenta(IDEquipamientos, IDRenta, cantidad, precio);
                        total += (cantidad * precio); 
                    } else {
                        System.out.println("ID de equipamiento o cantidad inválidos.");
                    }
                    break;
    
                case 3:
                    MostrarServicios menuServicios = new MostrarServicios();
                    menuServicios.showServiciosRenta(IDRenta);
                    break;
    
                case 4:
                    MostrarEquipamientos mostrarEquipamientos = new MostrarEquipamientos();
                    mostrarEquipamientos.showEquipoRentas(IDRenta);
                    break;
    
                case 0:
                    System.out.println("Está bien, prosigamos.");
                    break;
    
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
                    break;
            }
        } while (opcion != 0);

        return total;
    }

    public float getTotalServicio(int id){
        float total = 0f;
        Connection conexion = null;
        String consulta = "SELECT precio FROM servicios WHERE numero = ?";

        try {
            conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, id);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                total = resultado.getFloat("precio");
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }

        return total;
    }

    public float getTotalEquipamiento(int id){
        float total = 0f;
        Connection conexion = null;
        String consulta = "SELECT precio FROM equipamiento WHERE numero = ?";

        try {
            conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, id);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                precio = resultado.getFloat("precio");
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }

        return total;
    }

    public void menuComplementosReservacion(int IDRenta, int IDEvento) {

        pagos pagoss = new pagos();

        do {
                System.out.println("Desea agregar algún Servicio o Equipamiento?");
                System.out.println("1) Agregar Servicio");
                System.out.println("2) Agregar Equipamiento");
                System.out.println("3) Mostrar Servicios de mi renta");
                System.out.println("4) Mostrar Equipamiento de mi renta");
                System.out.println("5) Ver mis Pagos");
                if (!pagoss.getPago(IDRenta)) {
                    System.out.println("6) Realizar Pago");
                }
                System.out.println("0) No, gracias");
                opcion = Leer.nextInt();

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

                        MostrarEquipamientos equipamiento = new MostrarEquipamientos();
                        equipamiento.showEquipamientos(IDEvento);
                        IDEquipamientos = equipamiento.elegirEquipamiento();

                        cantidad = equipamiento.getCantidad(IDEquipamientos);

                        MostrarEquipamientos precios = new MostrarEquipamientos();
                        precio = precios.getPrecio(IDEquipamientos);

                        System.out.println(precio);

                            AgregarEquipRenta(IDEquipamientos, IDRenta, cantidad, precio);
                        
                        break;

                    case 3:
                        MostrarServicios menu = new MostrarServicios();
                        menu.showServiciosRenta(IDRenta);

                        break;

                    case 4:
                        MostrarEquipamientos mostrarEquipamientos =  new MostrarEquipamientos();
                        mostrarEquipamientos.showEquipoRentas(IDRenta);

                        break;

                    case 5:
                        pagos verPagos = new pagos();
                        verPagos.mostrarPagos(IDRenta);

                        break;
                    case 6:
                        if (!pagoss.getPago(IDRenta)) {
                            System.out.println("Opción no válida, intente de nuevo.");
                        }else{

                        }

                        break;

                    case 0:
                        System.out.println("Está bien, prosigamos.");

                        break;

                    default:
                        System.out.println("Opción no válida, intente de nuevo.");
                        
                        break;
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
                        return; 
                    }
                }
            }
    
            total = salon.getTotal(IDRenta);
            
            total += precioServicio;
            subtotal = (total/1.16f);
            IVA = total - subtotal;
    
            try (PreparedStatement statementInsert = conexion.prepareStatement(insertServiciosRenta)) {
                statementInsert.setInt(1, IDServicios);
                statementInsert.setInt(2, IDRenta);
                int filasInsertadas = statementInsert.executeUpdate();
    
                if (filasInsertadas > 0) {
                    System.out.println("Inserción exitosa de servicio en su renta.");
                } else {
                    System.out.println("No se pudo insertar el servicio en la renta.");
                    return; 
                }
            }
    
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
        } 
    }
    

    public void AgregarEquipRenta(int IDEquipamientos, int IDRenta, int cantidad, float precio) {
        Connection conexion = null;
        float importe = cantidad * precio;
        String insertEquiposRenta = "INSERT INTO equipos_renta (equipamiento, renta, cantidad, importe) VALUES (?, ?, ?, ?)";
        String actualizarRenta = "UPDATE renta SET subtotal = ?, IVA = ?, total = ? WHERE numero = ?";
        String actualizarStock = "UPDATE equipamiento SET stock = stock - ? WHERE numero = ?";
    
        try {
            conexion = ConexionBD.obtenerConexion();
    
            try (PreparedStatement statementInsert = conexion.prepareStatement(insertEquiposRenta)) {
                statementInsert.setInt(1, IDEquipamientos);
                statementInsert.setInt(2, IDRenta);
                statementInsert.setInt(3, cantidad);
                statementInsert.setFloat(4, importe);
                int filasInsertadas = statementInsert.executeUpdate();
    
                if (filasInsertadas > 0) {
                    System.out.println("Inserción exitosa de los equipos a su evento.");
                } else {
                    System.out.println("No se pudo insertar el equipamiento en la renta.");
                    return; 
                }
            }
    
            try (PreparedStatement statementActualizarStock = conexion.prepareStatement(actualizarStock)) {
                statementActualizarStock.setInt(1, cantidad);
                statementActualizarStock.setInt(2, IDEquipamientos);
                int filasActualizadasStock = statementActualizarStock.executeUpdate();
    
                if (filasActualizadasStock > 0) {
                    System.out.println("Stock de equipamiento actualizado.");
                } else {
                    System.out.println("No se pudo actualizar el stock del equipamiento.");
                }
            }
    
            float total = salon.getTotal(IDRenta);
            
            total += importe;
            float subtotal = (total/1.16f);
            float IVA = total - subtotal;
    
            try (PreparedStatement statementActualizarRenta = conexion.prepareStatement(actualizarRenta)) {
                statementActualizarRenta.setFloat(1, subtotal);
                statementActualizarRenta.setFloat(2, IVA);
                statementActualizarRenta.setFloat(3, total);
                statementActualizarRenta.setInt(4, IDRenta);
                int filasActualizadasRenta = statementActualizarRenta.executeUpdate();
    
                if (filasActualizadasRenta > 0) {
                    System.out.println("Actualización exitosa de la renta.");
                } else {
                    System.out.println("No se pudo actualizar la renta.");
                }
            }
    
        } catch (SQLException e) {
            System.out.println("Error al procesar la solicitud: " + e.getMessage());
        } 
    }
    

}
