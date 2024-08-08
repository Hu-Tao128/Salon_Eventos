package admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import cliente.MostrarMontajes;
import conexionDB.ConexionBD;

public class AgregarEvento {

    private int IDMontaje;
    private int IDEvento;
    private int IDEquipamiento;
    
    public void agregarEventoF(){
        
        Connection connection = null;

        Scanner datos = new Scanner(System.in);
        String opcion1 = "";
        String opcion21 = "";
        String opcion31 = "";
        String opcion41 = "n";
        int opcion5 = 0;
        String resOpcion1 = "s";
        String resOpcion2 = "n";
        String opcion;
        String opcion2 = "";
        String opcion3;
        String opcion4;
        String nombre = "";

        MostrarMontaje montaje = new MostrarMontaje();
        MostrarEquipamiento equipamiento = new MostrarEquipamiento();

        System.out.println("Registro de evento");

        do{
            System.out.println("Quieres agregar un evento? (s/n)");
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
           
                System.out.println("Ingrese el nombre del evento");
                nombre = datos.nextLine();

                System.out.println("Nombre del evento" + nombre);

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
                                System.out.println("[1] Nombre del evento" + nombre);
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
                
                String agregarUsuario = "INSERT INTO evento (numero, nombre) VALUES (null, ?)";
                PreparedStatement statement = connection.prepareStatement(agregarUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setString(1, nombre);
    
                int filasAfectadas = statement.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("Los datos se han insertado correctamente en la tabla evento.");
    
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                IDEvento = generatedKeys.getInt(1);
                            } else {
                                throw new SQLException("No se pudo obtener la clave primaria generada.");
                            }
                        }
                } else {
                    System.out.println("No se pudo insertar los datos en la tabla evento.");
                }
    
                System.out.println("Agregando los montajes para el evento");
    
                do{
                    System.out.println("Eliga el montaje para el evento");
                    montaje.showMontaje();
                    IDMontaje = datos.nextInt();
    
                    agregacionEventoMontaje(IDEvento,IDMontaje);
                    
                    System.out.println("Quieres continuar agregando opciones de montajes al evento (s/n)");
                    opcion3 = datos.next();
    
                }while(!opcion3.equals("n"));
    
                do{
                    System.out.println("Eliga el equipamiento para el evento");
                    equipamiento.showEquipamiento();
                    IDEquipamiento = datos.nextInt();
    
                    agregacionEventoEquipamiento(IDEvento,IDEquipamiento);
    
                    System.out.println("Quieres continuar agregando opciones de equipamiento para al evento (s/n)");
                    opcion4 = datos.next();
                }while(!opcion4.equals("n"));
    
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
    
       

    public void agregacionEventoMontaje(int IDEvento, int IDMontaje){
        Connection connection = null;

        try{

            connection = ConexionBD.obtenerConexion();

            String agregarMontajeEvento = "INSERT INTO montaje_evento (evento, montaje) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(agregarMontajeEvento);
            statement.setInt(1, IDEvento);
            statement.setInt(2, IDMontaje);

            int filasInsertadas = statement.executeUpdate();
                if (filasInsertadas > 0) {
                    System.out.println("Inserción exitosa de los datos.");
                } else {
                    System.out.println("No se pudo insertar los datos.");
                    return; 
                }

        }catch(SQLException e){
            System.out.println("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    public void agregacionEventoEquipamiento(int IDEvento, int IDEquipamiento){

        Connection connection = null;

        try{

            connection = ConexionBD.obtenerConexion();

            String agregarEquipamientoEvento = "INSERT INTO equipos_evento (evento, equipamiento) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(agregarEquipamientoEvento, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setInt(1, IDEvento);
                statement.setInt(2, IDEquipamiento);

                int filasInsertadas = statement.executeUpdate();
                if (filasInsertadas > 0) {
                    System.out.println("Inserción exitosa de los datos.");
                } else {
                    System.out.println("No se pudo insertar los datos.");
                    return; 
                }

        }catch(SQLException e){
            System.out.println("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}
