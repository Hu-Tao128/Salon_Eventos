package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import cliente.MostrarServicios;
import conexionDB.ConexionBD;

public class ActualizarServicios {
    private int IDServicio;

    public void updateSalon(){
        Connection connection = null;

        MostrarServicios servicio = new MostrarServicios();

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String opcion3 = "";
        String opcion4 = "n";
        int opcion5 = 0;
        String resOpcion1 = "s";
        String resOpcion2 = "n";
        String nombreServicio = "";      
        String descripcion = "";
        double precio = 0;
        int disponibilidad = 0;

        System.out.println("Modificacion de un servicio");

        do{
            System.out.println("Quieres agregar un actualizar un servicio? (s/n)");
            opcion3 = datos.nextLine();

            if(opcion3.equals("n")){
                System.out.println("Quieres salir de este apartado (s/n)");
                opcion4 = datos.nextLine();
                if(opcion4.equals("s")){
                    break;
                }

                if(opcion4.equals("n")){

                }else{
                    System.out.println("Respuesta invalida");
                }
            }else{
                if(opcion3.equals("s")){

                }else{
                    System.out.println("Introduzca una respuesta valida");
                }  
            }
        }while(!opcion3.equals(resOpcion1));

        if(opcion3.equals("s")){
            do{
                System.out.println("Seleccione el servicio que sera modificado");
                servicio.showServicios();
                IDServicio = servicio.elegirServicio();

                System.out.println("Esta seguro de querer actualizar toda la informacion del servicio numero " + IDServicio + " (s/n)");
                opcion2 = datos.nextLine();
                if(opcion2.equals("n")){
                    System.out.println("Eliga a otro cliente");
                }else{
                    if(opcion2.equals("s")){
    
                    }else{
                        System.out.println("Introduzca una respuesta valida");
                    }
                }
                }while(!opcion2.equals("s"));
            }

        

        if(opcion2.equals("s")){
            do {
                opcion2 = "";
                System.out.println("Actualice el nombre del servicio");
                nombreServicio = datos.nextLine();

                System.out.println("Actualice la descripcion del servicio");
                descripcion = datos.nextLine();

                System.out.println("Actualice el precio del servicio");
                precio = datos.nextDouble();

                System.out.println("Actualice la disponibilidad del servicios (1=disponible / 0=no disponible)");
                disponibilidad = datos.nextInt();
    
                System.out.println("Nombre del servicio: " + nombreServicio);
                System.out.println("Descripcion: " + descripcion);
                System.out.println("Precio del servicio: " + precio);
                System.out.println("Disponibilidad: " + disponibilidad);
    
                do{
                    System.out.println("Escribio bien los datos? (s/n)");
                    opcion = datos.next();
        
                    if(opcion.equals("s")){
                        opcion2 = "s";
                    }else{
                        if(opcion.equals("n")){
                            
                        }else{
                            System.out.println("Ingrese una opcion valida");
                        }
                    }

                    if(opcion.equals("n")){
                        opcion2 = "n";
                        if(opcion2.equals("n")){
                            do{ 
                                System.out.println("--Informacion--");
                                System.out.println("[1] Nombre del servicio: " + nombreServicio);
                                System.out.println("[2] Descripcion: " + descripcion);
                                System.out.println("[3] Precio del servicio: " + precio);
                                System.out.println("[4] Disponibilidad: " + disponibilidad);
                                System.out.println("Ingrese el numero de la opcion a modificar");
                                System.out.println("Ingrese 0 para salir de este apartado");
                                opcion5 = datos.nextInt();

                                switch (opcion5) {
                                    case 1:
                                    System.out.println("Ingrese el nombre del servicio");
                                    nombreServicio = datos.next();
                                    break;

                                    case 2:
                                    System.out.println("Ingrese la descripcion del servicio");
                                    descripcion = datos.next();
                                    break;

                                    case 3:
                                    System.out.println("Ingrese el precio del servicio");
                                    precio = datos.nextDouble();
                                    break;

                                    case 4:
                                    System.out.println("Ingrese si esta disponible o no (1/0)");
                                    disponibilidad = datos.nextInt();
                                    break;

                                    case 0:
                                        System.out.println("Saliendo del apartado");
                                        opcion2 = "s";
                                    break;
                                
                                    default:
                                        System.out.println("Opcion no valida");
                                    break;
                                }
                                if(opcion5 > 0 && opcion5 < 3){
                                    System.out.println("Cambio realizado");
                                }
                            }while(opcion5 != 0);
                        }
                    }else{
                        if(opcion2.equals("n")){

                        }else{

                            if(opcion2.equals("s")){

                            }else{
                                System.out.println("Ingrese una opcion valida");
                            }    
                        }
                    }
                }while(!opcion2.equals(resOpcion1) || opcion2.equals(resOpcion2));
            } while (!opcion2.equals(resOpcion1));
    
    
            try {
    
                connection = ConexionBD.obtenerConexion();
                
                String updateServicio = "UPDATE servicios SET nombreServicio = ?, descripcion = ?, precio = ?, disponibilidad = ? WHERE numero = ?";
                PreparedStatement statement = connection.prepareStatement(updateServicio);
                statement.setString(1, nombreServicio);
                statement.setString(2, descripcion);
                statement.setDouble(3, precio);
                statement.setInt(4, disponibilidad);
                statement.setInt(5, IDServicio);
 
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han actualizado correctamente en la tabla servicio.");
                } else {
                    System.out.println("No se pudo acctualizar los datos en la tabla servicio.");
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
