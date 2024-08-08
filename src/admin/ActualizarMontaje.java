package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class ActualizarMontaje {
    private int IDMontaje;

    public void updateMontaje(){
        Connection connection = null;

        MostrarMontaje montaje = new MostrarMontaje();

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

        System.out.println("Modificacion de un montaje");

        do{
            System.out.println("Quieres agregar un montaje? (s/n)");
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
                System.out.println("Seleccione el montaje que sera modificado");
                montaje.showMontaje();
                IDMontaje = montaje.elegirMontaje();

                System.out.println("Esta seguro de querer actualizar toda la informacion del montaje numero " + IDMontaje + " (s/n)");
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
                System.out.println("Actualice el nombre del montaje");
                nombreMontaje = datos.nextLine();
                
                System.out.println("Actualice la descripcion del montaje");
                descripcion = datos.nextLine();

                System.out.println("Nombre del montaje: " + nombreMontaje);
                System.out.println("Descripcion: " + descripcion);
                
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
                                System.out.println("[1] Nombre del montaje: " + nombreMontaje);
                                System.out.println("[2] Descripcion: " + descripcion);
                                System.out.println("Ingrese el numero de la opcion a modificar");
                                System.out.println("Ingrese 0 para salir de este apartado");
                                opcion5 = datos.nextInt();

                                switch (opcion5) {
                                    case 1:
                                        System.out.println("Ingrese el nombre del montaje");
                                        nombreMontaje = datos.next();
                                    break;

                                    case 2:
                                        System.out.println("Ingrese la descripcion del montaje");
                                        descripcion = datos.next();
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
                
                String updateMontaje = "UPDATE montaje SET nombreMontaje = ?, descripcion = ? WHERE numero = ?";
                PreparedStatement statement = connection.prepareStatement(updateMontaje);
                statement.setString(1, nombreMontaje);
                statement.setString(2, descripcion);
                statement.setInt(3, IDMontaje);

                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han actualizado correctamente en la tabla montaje.");
                } else {
                    System.out.println("No se pudo acctualizar los datos en la tabla montaje.");
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
