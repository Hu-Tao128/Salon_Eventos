package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void menuComplementos(int IDRenta, int IDEvento){
        
        do {
            System.out.println("Desea agregar algún Servicio o Equipamiento?");
            System.out.println("1) Servicio");
            System.out.println("2) Equipamiento");
            System.out.println("0) No, gracias");
            opcion = Leer.nextInt();
            Leer.nextLine(); 

            switch (opcion) {
                case 1:
                    servicios.showServicios();
                    IDServicios = servicios.elegirServicio();
                    
                    AgregarServRentas(IDServicios, IDRenta);

                    break;
                case 2:
                    equipamiento.showEquipamientos(IDEvento);
                    IDEquipamientos = equipamiento.elegirEquipamiento();
                    cantidad = equipamiento.getCantidad(IDEquipamientos);
                    precio = equipamiento.getPrecio(IDEquipamientos);
                    
                    if (precio != 0 || cantidad != 0) {
                        AgregarEquipRenta(IDEquipamientos, IDRenta, cantidad, precio);   
                    }else{
                        System.out.println("Ah ocurrido un error al agregar el equipamiento");
                    }

                    break;
                case 0:
                    System.out.println("Esta bien, prosigamos");
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
                    break;
            }
        } while (opcion != 0);
    }
    
    public void AgregarServRentas(int IDServicios, int IDRenta){
        String insertServiciosRenta = "INSERT INTO servicios_renta (servicios, renta) VALUES (?, ?)";

            try (Connection conexion = ConexionBD.obtenerConexion();
                PreparedStatement statement = conexion.prepareStatement(insertServiciosRenta)) {

                statement.setInt(1, IDServicios); 
                statement.setInt(2, IDRenta); 
                int filasInsertadas = statement.executeUpdate();

                if (filasInsertadas > 0) {
                    System.out.println("Inserción exitosa de servicio en su renta.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public void AgregarEquipRenta(int IDEquipamientos, int IDRenta, int cantidad, float precio){

        float importe = (cantidad * precio);
        String insertEquiposRenta = "INSERT INTO equipos_renta (equipamiento, renta, cantidad, importe) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(insertEquiposRenta)) {

            statement.setInt(1, IDEquipamientos);
            statement.setInt(2, IDRenta);
            statement.setInt(3, cantidad);
            statement.setDouble(4, importe);
            int filasInsertadas = statement.executeUpdate();

           if (filasInsertadas > 0) {
                    System.out.println("Inserción exitosa de los equipos a su evento.");
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
