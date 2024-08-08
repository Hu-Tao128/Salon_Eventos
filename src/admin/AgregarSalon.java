package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class AgregarSalon {
    public void agregarSalonF(){
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
        String dirCalle = "";        
        String dirColonia = "";
        String dirNumero = "";
        int capacidad = 0;
        double tamanio = 0.0;
        double precio = 0.0;
        
        System.out.println("Registro de salon");

        do{
            System.out.println("Quieres agregar un salon? (s/n)");
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
           
                System.out.println("Ingrese el nombre del salon");
                nombre = datos.nextLine();
                
                System.out.println("Ingrese la calle donde se encuentra el salon");
                dirCalle = datos.nextLine();
    
                System.out.println("Ingrese la colonia donde se encuentra el salon");
                dirColonia = datos.nextLine();
    
                System.out.println("Ingrese el numero interno del salon");
                dirNumero = datos.nextLine();
    
                System.out.println("Ingrese la capacidad de personas que caben en el salon");
                capacidad = datos.nextInt();
    
                System.out.println("Ingrese los metros cuadrados que mide el salon");
                tamanio = datos.nextDouble();
    
                System.out.println("Ingrese el precio de renta del salon");
                precio = datos.nextDouble();

                System.out.println("Nombre del salon: " + nombre);
                System.out.println("Calle del salon: " + dirCalle);
                System.out.println("Colonia del salon: " + dirColonia);
                System.out.println("Numero interno del salon: " + dirNumero);
                System.out.println("Capacidad del salon: " + capacidad);
                System.out.println("Tamaño del salon: " + tamanio);
                System.out.println("Precio de la renta del salon: " + precio);
    
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
                                System.out.println("[1] Nombre del salon: " + nombre);
                                System.out.println("[2] Calle del salon: " + dirCalle);
                                System.out.println("[3] Colonia del salon: " + dirColonia);
                                System.out.println("[4] Numero interno del salon: " + dirNumero);
                                System.out.println("[5] Capacidad del salon: " + capacidad);
                                System.out.println("[6] Tamaño del salon: " + tamanio);
                                System.out.println("[7] Precio de la renta del salon: " + nombre);
                                System.out.println("Ingrese el numero de la opcion a modificar");
                                System.out.println("Ingrese 0 para salir de este apartado");
                                opcion5 = datos.nextInt();

                                switch (opcion5) {
                                    case 1:
                                        System.out.println("Ingrese el nombre del salon");
                                        nombre = datos.next();
                                    break;

                                    case 2:
                                        System.out.println("Ingrese la calle donde se encuentra el salon");
                                        dirCalle = datos.next();
                                    break;

                                    case 3:
                                        System.out.println("Ingrese la colonia donde se encuentra el salon");
                                        dirColonia = datos.next();
                                    break;

                                    case 4:
                                        System.out.println("Ingrese el numero interno del salon");
                                        dirNumero = datos.next();
                                    break;

                                    case 5:
                                        System.out.println("Ingrese la capacidad de personas que posee el salon");
                                        capacidad = datos.nextInt();
                                    break;

                                    case 6:
                                        System.out.println("Ingrese los metros cuadrados que mide el salon");
                                        tamanio = datos.nextDouble();
                                    break;

                                    case 7:
                                        System.out.println("Ingrese el precio de renta del salon");
                                        precio = datos.nextDouble();
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
                
                String agregarUsuario = "INSERT INTO salon (numero, nombre, dirCalle, dirColonia, dirNumero, capacidad, tamanio, precio) VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(agregarUsuario);
                statement.setString(1, nombre);
                statement.setString(2, dirCalle);
                statement.setString(3, dirColonia);
                statement.setString(4, dirNumero);
                statement.setInt(5, capacidad);
                statement.setDouble(6, tamanio);
                statement.setDouble(7, precio);
    
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han insertado correctamente en la tabla salon.");
                } else {
                    System.out.println("No se pudo insertar los datos en la tabla salon.");
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
