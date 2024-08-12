package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class AgregarServicios {
    private int IDTiposServicios;
    public void agregarServiciosF(){
        Connection connection = null;

        MostrarTipoServicio tiposServicios = new MostrarTipoServicio();

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
        double precio = 0.0;
        int disponibilidad = 0;

        System.out.println("Registro de servicio");

        do{
            System.out.println("Quieres agregar un servicio? (s/n)");
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

        if(opcion3.equals(resOpcion1)){
            do {
           
                System.out.println("Ingrese el nombre del servicio");
                nombreServicio = datos.nextLine();
                
                System.out.println("Ingrese la descripcion del servicio");
                descripcion = datos.nextLine();
    
                System.out.println("Ingrese el precio del servicio");
                precio = datos.nextDouble();
    
                System.out.println("Ingrese si esta disponible o no (1 = si/0 = no)");
                disponibilidad = datos.nextInt();

                System.out.println("Informacion de los tipos de servicios");
                tiposServicios.showTipoServicio();
                System.out.println("Escoje el numero del tipo de servicio al que pertenece el servicio");
                IDTiposServicios = datos.nextInt();

                System.out.println("Nombre del servicio: " + nombreServicio);
                System.out.println("Descripcion: " + descripcion);
                System.out.println("Precio del servicio: " + precio);
                System.out.println("Disponibilidad: " + disponibilidad);
                System.out.println("Tipo de servicio: " + IDTiposServicios);
    
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
                                System.out.println("[5] Tipo de servicio: " + IDTiposServicios);
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

                                    case 5:
                                    System.out.println("Informacion de los tipos de servicios");
                                    tiposServicios.showTipoServicio();
                                    System.out.println("Escoje el numero del tipo de servicio al que pertenece el servicio");
                                    IDTiposServicios = datos.nextInt();
                                    break;

                                    case 0:
                                        System.out.println("Saliendo del apartado");
                                        opcion2 = "s";
                                    break;
                                
                                    default:
                                        System.out.println("Opcion no valida");
                                    break;
                                }
                                if(opcion5 > 0 && opcion5 < 6){
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
                
                String agregarUsuario = "INSERT INTO servicios (numero, nombreServicio, descripcion, precio, disponibilidad, tipoServicio) VALUES (null, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(agregarUsuario);
                statement.setString(1, nombreServicio);
                statement.setString(2, descripcion);
                statement.setDouble(3, precio);
                statement.setInt(4, disponibilidad);
                statement.setInt(5, IDTiposServicios);
    
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han insertado correctamente en la tabla servicio.");
                } else {
                    System.out.println("No se pudo insertar los datos en la tabla servicio.");
                }
    
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos o al insertar datos: " + e.getMessage());
            }finally {
                // Cerrar la conexión
                ConexionBD.cerrarConexion(connection);
            }
        }else{
            System.out.println("Saliendo del apartado");
        }
       
    }
}
