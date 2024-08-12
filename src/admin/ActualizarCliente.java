package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class ActualizarCliente {

    private int IDCliente;

    public void updateCliente(){
        Connection connection = null;

        MostrarClientes cliente = new MostrarClientes();

        Scanner datos = new Scanner(System.in);
        String opcion;
        String opcion2 = "";
        String opcion3 = "";
        String opcion4 = "n";
        int opcion5 = 0;
        String resOpcion1 = "s";
        String resOpcion2 = "n";
        String nombreFiscal = "";
        String nomContacto = "";
        String primerApellido = "";        
        String segundoApellido = "";
        String numTel = "";
        String email = "";

        System.out.println("Modificacion de un cliente");

        do{
            System.out.println("Quieres modificar un cliente? (s/n)");
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
            
            cliente.showClientes();
            System.out.println("Seleccione el cliente que sera modificado");
            IDCliente = cliente.elegirCliente();
    
            System.out.println("Esta seguro de querer actualizar toda la informacion del cliente numero " + IDCliente + " (s/n)");
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
                System.out.println("Actualice el nombre fiscal del cliente");
                nombreFiscal = datos.nextLine();
                
                System.out.println("Actualice el nombre del contacto del cliente (Sin apellidos)");
                nomContacto = datos.nextLine();
    
                System.out.println("Actualice el primer apellido del contacto del cliente");
                primerApellido = datos.nextLine();
    
                System.out.println("Actualice el segundo apellido del contacto del cliente");
                segundoApellido = datos.nextLine();
    
                System.out.println("Actualice el numero de celular del cliente");
                numTel = datos.nextLine();
    
                System.out.println("Actualice correo electronico del cliente");
                email = datos.nextLine();
    
                System.out.println("Nombre de la empresa: " + nombreFiscal);
                System.out.println("Nombre: " + nomContacto);
                System.out.println("Primer apellido: " + primerApellido);
                System.out.println("Segundo apellido: " + segundoApellido);
                System.out.println("Celular: " + numTel);
                System.out.println("Correo electronico" + email);
    
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
                                System.out.println("[1] Nombre de la empresa: " + nombreFiscal);
                                System.out.println("[2 ]Nombre: " + nomContacto);
                                System.out.println("[3] Primer apellido: " + primerApellido);
                                System.out.println("[4] Segundo apellido: " + segundoApellido);
                                System.out.println("[5] Celular: " + numTel);
                                System.out.println("[6] Correo electronico" + email);
                                System.out.println("Ingrese el numero de la opcion a modificar");
                                System.out.println("Ingrese 0 para salir de este apartado");
                                opcion5 = datos.nextInt();

                                switch (opcion5) {
                                    case 1:
                                        System.out.println("Nombre de la empresa del cliente (dar ENTER en caso de no tener)");
                                        nombreFiscal = datos.next();
                                    break;
    
                                    case 2:
                                        System.out.println("Ingrese el nombre y segundo nombre del cliente (No Apellidos)");
                                        nomContacto = datos.next();
                                    break;
    
                                    case 3:
                                        System.out.println("Ingrese el primer apellido:");
                                        primerApellido = datos.next();
                                    break;
    
                                    case 4:
                                        System.out.println("Escriba el segundo apellido:");
                                        segundoApellido = datos.next();
                                    break;
    
                                    case 5:
                                        System.out.println("Ingrese el numero de telefono:");
                                        numTel = datos.next();
                                    break;
    
                                    case 6:
                                        System.out.println("Ingrese el correo electronico:");
                                        email = datos.next();
                                    break;

                                    case 0:
                                        System.out.println("Saliendo del apartado");
                                        opcion2 = "s";
                                    break;
                                
                                    default:
                                        System.out.println("Opcion no valida");
                                    break;
                                }
                                if(opcion5 > 0 && opcion5 < 7){
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
                
                String updateCliente = "UPDATE cliente SET nombreFiscal = ?, nomContacto = ?, primerApellido = ?, segundoApellido = ?, numTel = ?, email = ? WHERE numero = ?";
                PreparedStatement statement = connection.prepareStatement(updateCliente);
                statement.setString(1, nombreFiscal);
                statement.setString(2, nomContacto);
                statement.setString(3, primerApellido);
                statement.setString(4, segundoApellido);
                statement.setString(5, numTel);
                statement.setString(6, email);
                statement.setInt(7, IDCliente);
 
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han actualizado correctamente en la tabla cliente.");
                } else {
                    System.out.println("No se pudo acctualizar los datos en la tabla cliente.");
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
