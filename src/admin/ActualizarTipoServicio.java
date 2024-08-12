package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import cliente.MostrarSalones;
import conexionDB.ConexionBD;

public class ActualizarTipoServicio {
    private int IDTipoServicio;

    public void updateTipoServicio(){
        Connection connection = null;

        MostrarTipoServicio tiposServicio = new MostrarTipoServicio();

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String opcion3 = "";
        String opcion4 = "n";
        int opcion5 = 0;
        String resOpcion1 = "s";
        String resOpcion2 = "n";
        String nombre = "";      
        String dirCalle = "";
        String dirColonia = "";
        String dirNumero = "";
        int capacidad = 0;
        double tamanio = 0.0;
        double precio = 0.0;

        System.out.println("Modificacion de un tipo de servicio");

        do{
            System.out.println("Quieres agregar un actualizar un tipo de servicio? (s/n)");
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
                
                System.out.println("Informacion de los tipos de servicios");
                tiposServicio.showTipoServicio();
                System.out.println("Seleccione el salon que sera modificado");;
                IDTipoServicio = tiposServicio.elegirTipoServicio();

                System.out.println("Esta seguro de querer actualizar toda la informacion del tipo de servicio numero " + IDTipoServicio + " (s/n)");
                opcion2 = datos.nextLine();
                if(opcion2.equals("n")){
                    System.out.println("Eliga a otro tipo de servicio");
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
                System.out.println("Actualice el nombre del tipo de servicio");
                nombre = datos.nextLine();
    
                System.out.println("Nombre del tipo de servicio: " + nombre);
    
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
                                System.out.println("[1] Nombre del tipo de servicio: " + nombre);
                                System.out.println("Ingrese el numero de la opcion a modificar o 0 para salir del apartado");
                                
                                opcion5 = datos.nextInt();

                                switch (opcion5) {
                                    case 1:
                                        System.out.println("Ingrese el nombre del salon");
                                        nombre = datos.next();
                                    break;

                                
    
                                    case 0:
                                        System.out.println("Saliendo del apartado");
                                        opcion2 = "s";
                                    break;
                                
                                    default:
                                        System.out.println("Opcion no valida");
                                    break;
                                }
                                if(opcion5 > 0 && opcion5 < 2){
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
                
                String updateSalon = "UPDATE tipo_servicios SET nombre = ? WHERE numero = ?";
                PreparedStatement statement = connection.prepareStatement(updateSalon);
                statement.setString(1, nombre);
                statement.setInt(8, IDTipoServicio);
 
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han actualizado correctamente en la tabla tipo_servicios.");
                } else {
                    System.out.println("No se pudo acctualizar los datos en la tabla tipo_servicios.");
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
