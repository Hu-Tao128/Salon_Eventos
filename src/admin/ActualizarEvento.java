package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import cliente.MostrarEventos;
import conexionDB.ConexionBD;

public class ActualizarEvento {
    private int IDEvento;

    public void updateEvento(){
        Connection connection = null;

        MostrarEventos evento = new MostrarEventos();

        Scanner datos = new Scanner(System.in);
        int opcion5 = 0;
        String resOpcion1 = "s";
        String resOpcion2 = "n";
        String opcion;
        String opcion2 = "";
        String opcion3;
        String opcion4;
        String nombre = "";      

        System.out.println("Modificacion de un evento");

        do{
            System.out.println("Quieres agregar un actualizar un evento? (s/n)");
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
            
            evento.showEventos();
            System.out.println("Seleccione el evento que sera modificado");
            IDEvento = evento.elegirEvento();
    
            System.out.println("Esta seguro de querer actualizar toda la informacion del evento numero " + IDEvento + " (s/n)");
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
                System.out.println("Actualice el nombre del evento");
                nombre = datos.nextLine();
    
                System.out.println("Nombre del evento: " + nombre);

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
                                System.out.println("[1] Nombre del evento:" + nombre);
                                System.out.println("Ingrese el numero de la opcion a modificar");
                                System.out.println("Ingrese 0 para salir de este apartado");
                                opcion5 = datos.nextInt();

                                switch (opcion5) {
                                    case 1:
                                        System.out.println("Ingrese el nombre del evento");
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
                
                String updateEvento = "UPDATE evento SET nombre = ? WHERE numero = ?";
                PreparedStatement statement = connection.prepareStatement(updateEvento);
                statement.setString(1, nombre);
                statement.setInt(2, IDEvento);
 
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han actualizado correctamente en la tabla evento.");
                } else {
                    System.out.println("No se pudo acctualizar los datos en la tabla evento.");
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