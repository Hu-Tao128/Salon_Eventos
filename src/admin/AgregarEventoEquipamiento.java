package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import cliente.MostrarEventos;
import conexionDB.ConexionBD;

public class AgregarEventoEquipamiento {

    private int IDEvento;
    private int IDEquipamiento;

    public void agregarEventoEquipamientoF(){
        Connection connection = null;

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String opcion3 = "";
        String opcion4 = "n";
        int opcion5 = 0;
        String resOpcion1 = "s";
        String resOpcion2 = "n";
        String nombreMontaje = "";   
        String descripcion = "";

        MostrarEventos evento = new MostrarEventos();
        MostrarEquipamiento equipamiento = new MostrarEquipamiento();

        System.out.println("Registro de montaje");

        do{
            System.out.println("Quieres agregar un montaje a un evento? (s/n)");
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
           
                System.out.println("Eliga el evento");
                evento.showEventos();
                IDEvento = evento.elegirEvento();
    
                System.out.println("Elija el equipamiento a agregar al evento");
                equipamiento.showEquipamiento();
                IDEquipamiento = equipamiento.elegirEquipamiento();
    
                System.out.println("Numero del evento: " + IDEvento);
                System.out.println("Numero del equipamiento: " + IDEquipamiento);
    
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
                                System.out.println("[1] Numero del evento: " + IDEvento);
                                System.out.println("[2] Numero del equipamiento: " + IDEquipamiento);
                                System.out.println("Ingrese el numero de la opcion a modificar");
                                System.out.println("Ingrese 0 para salir de este apartado");
                                opcion5 = datos.nextInt();

                                switch (opcion5) {
                                    case 1:
                                    System.out.println("Eliga el evento");
                                    evento.showEventos();
                                    IDEvento = evento.elegirEvento();
                                    break;

                                    case 2:
                                    System.out.println("Elija el equipamiento a agregar al evento");
                                    equipamiento.showEquipamiento();
                                    IDEquipamiento = equipamiento.elegirEquipamiento();
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
                
                String agregarUsuario = "INSERT INTO equipos_evento (evento, equipamiento) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(agregarUsuario);
                statement.setInt(1, IDEvento);
                statement.setInt(2, IDEquipamiento);
    
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han insertado correctamente en la tabla evento_equipamiento.");
                } else {
                    System.out.println("No se pudo insertar los datos en la tabla evento_equipamiento.");
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