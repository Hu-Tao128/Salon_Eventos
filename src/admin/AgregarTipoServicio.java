package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class AgregarTipoServicio {
   public void agregarTipoServicioF(){
    Connection connection = null;
    Scanner datos = new Scanner(System.in);
    String opcion;
    String opcion2 = "";
    String opcion3 = "";
    String opcion4 = "n";
    int opcion5 = 0;
    String resOpcion1 = "s";
    String resOpcion2 = "n";

    String nombre = "";   

    System.out.println("Registro de tipo de servicio");

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
           
                System.out.println("Ingrese el nombre del tipo de servicio");
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
                                System.out.println("Ingrese el numero de la opcion a modificar o 0 para salir");
                                opcion5 = datos.nextInt();

                                switch (opcion5) {
                                    case 1:
                                    System.out.println("Ingrese el nombre del tipo de servicio");
                                    nombre = datos.nextLine();
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
                
                String agregarUsuario = "INSERT INTO tipo_servicios (numero, nombre) VALUES (null, ?)";
                PreparedStatement statement = connection.prepareStatement(agregarUsuario);
                statement.setString(1, nombre);
    
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han insertado correctamente en la tabla tipo_servicio.");
                } else {
                    System.out.println("No se pudo insertar los datos en la tabla tipo_servicio.");
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
