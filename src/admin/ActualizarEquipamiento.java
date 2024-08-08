package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class ActualizarEquipamiento {
    private int IDEquipamiento;

    public void updateEquipamiento(){
        Connection connection = null;

        MostrarEquipamiento equipamiento = new MostrarEquipamiento();

        Scanner datos = new Scanner(System.in);
        String opcion = "";
        String opcion2 = "";
        String opcion3 = "";
        String opcion4 = "n";
        int opcion5 = 0;
        String resOpcion1 = "s";
        String resOpcion2 = "n";
        String descripcion = "";
        double precio = 0.0;
        int stock = 0;        

        System.out.println("Modificacion de un equipamiento");

        do{
            System.out.println("Quieres modificar un equipamiento? (s/n)");
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
            System.out.println("Seleccione el equipamiento que sera modificado");
            equipamiento.showEquipamiento();
            IDEquipamiento = equipamiento.elegirEquipamiento();

            System.out.println("Esta seguro de querer actualizar toda la informacion del equipamiento numero " + IDEquipamiento + " (s/n)");
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
                System.out.println("Actualice la descripcion del equipamiento");
                descripcion = datos.nextLine();
                
                System.out.println("Actualice el precio del equipamiento");
                precio = datos.nextDouble();
    
                System.out.println("Actualice el stock del equipamiento");
                stock = datos.nextInt();
    
                System.out.println("Descripcion: " + descripcion);
                System.out.println("Precio: " + precio);
                System.out.println("Stock: " + stock);
                
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
                                System.out.println("[1] Descripcion: " + descripcion);
                                System.out.println("[2] Precio: " + precio);
                                System.out.println("[3] Stock: " + stock);
                                System.out.println("Ingrese el numero de la opcion a modificar");
                                System.out.println("Ingrese 0 para salir de este apartado");
                                opcion5 = datos.nextInt();

                                switch (opcion5) {
                                    case 1:
                                        System.out.println("Ingrese la descripcion del equipamiento");
                                        descripcion = datos.next();
                                    break;
    
                                    case 2:
                                        System.out.println("Ingrese el precio del equipamiento");
                                        precio = datos.nextDouble();
                                    break;
    
                                    case 3:
                                    System.out.println("Ingrese el stock del equipamiento");
                                    stock = datos.nextInt();
                                    break;

                                    case 0:
                                        System.out.println("Saliendo del apartado");
                                        opcion2 = "s";
                                    break;
                                
                                    default:
                                        System.out.println("Opcion no valida");
                                    break;
                                }
                                if(opcion5 > 0 && opcion5 < 4){
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
                
                String updateEquipamiento = "UPDATE equipamiento SET descripcion = ?, precio = ?, stock = ? WHERE numero = ?";
                PreparedStatement statement = connection.prepareStatement(updateEquipamiento);
                statement.setString(1, descripcion);
                statement.setDouble(2, precio);
                statement.setInt(3, stock);
                statement.setInt(4, IDEquipamiento);
 
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han actualizado correctamente en la tabla equipamiento.");
                } else {
                    System.out.println("No se pudo acctualizar los datos en la tabla equipamiento.");
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
