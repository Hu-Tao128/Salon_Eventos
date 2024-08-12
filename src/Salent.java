import java.util.Scanner;

import admin.administradorMain;
import cliente.AgregarCliente;
import cliente.Cuenta;
import cliente.Reservaciones;
import cliente.Servicios;
import cliente.rentaSalon;

public class Salent {

    public static void main(String[] args) {
        Scanner Leer = new Scanner(System.in);
        
        int NoCliente = 0;
        String Validar;
        int Opcion = 0;
        
        do {
            
            if (NoCliente == 0) {
                System.out.println("================================");
                System.out.println("|              Menu             |");
                System.out.println("|-------------------------------|");
                System.out.println("|       1) Iniciar Sesion       |");
                System.out.println("|-------------------------------|");
                System.out.println("|         2) Registrarse        |");
                System.out.println("|-------------------------------|");
                System.out.println("|         3) Administrador      |");
                System.out.println("|-------------------------------|");
                System.out.println("|            0) Salir           |");
                System.out.println("|-------------------------------|");
                System.out.println("================================");

                System.out.println("Escriba el numero de la opción a elegir:");
                Validar = Leer.next();

            try {
                Opcion = Integer.parseInt(Validar);

                switch (Opcion) {
                    case 1:
                        Cuenta InicioSesion = new Cuenta();
                        NoCliente = InicioSesion.InicioSesion();
                        break;
                    case 2:
                        AgregarCliente registrarse = new AgregarCliente();
                        NoCliente = registrarse.Formulario();
                        break;
                        case 3:
                            String admin = "papa";
                            String admin1 = "";

                            String  pass = "no";
                            String pass1 = "no";
                            String pass2 = "no";
                            System.out.println("Bienvenido a la vista de administrador");

                            
                                do{
                                    System.out.println("Ingrese la contraseña para ingresar a la vista de administrador:");
                                    admin1 = Leer.next();
                                    
                                    if(!admin1.equals(admin)){
                                        System.out.println("Quieres salir de este apartado? (si/no)");
                                        pass = Leer.next();

                                        if(pass.equals("si")){
                                            pass2 = "si";
                                        }
                                    }

                                    
                                }while (!admin1.equals(admin) && pass2.equals(pass1));

                                if(admin1.equals(admin)){
                                    System.out.println("Inicio de sesion exitoso");

                                    administradorMain data1 = new administradorMain();
                                    data1.menuAdministrador();
                                }
                        break;
                    case 0:
                        System.out.println("Que tenga buen dia :)");
                        break;
            
                    default:
                        break;
                }

            } catch (Exception e) {
                System.out.println("Ingrese Numeros por favor");
                Leer.nextLine();
                Opcion = -1;
            }

            }else{
                System.out.println("================================");
                System.out.println("|              Menu             |");
                System.out.println("|-------------------------------|");
                System.out.println("|      1) Ver mis Eventos       |");
                System.out.println("|-------------------------------|");
                System.out.println("|  2) Conoce Nuestros Servicios |");
                System.out.println("|-------------------------------|");
                System.out.println("|    3) Hacer una reservacion   |");
                System.out.println("|-------------------------------|");
                System.out.println("|        4) Ver mis Datos       |");
                System.out.println("|-------------------------------|");
                System.out.println("|        5) Cerrar Sesion       |");
                System.out.println("|-------------------------------|");
                System.out.println("|            0) Salir           |");
                System.out.println("|-------------------------------|");
                System.out.println("================================");
                System.out.println("Escriba el numero de la opción a elegir:");
                Validar = Leer.next();

                try {
                    Opcion = Integer.parseInt(Validar);
    
                    switch (Opcion) {
                        case 1:
                            Reservaciones reservaciones = new Reservaciones();
                            reservaciones.verMisReservaciones(NoCliente);
                            break;
                        case 2:
                            Servicios servicio = new Servicios();
                            servicio.nuestrosServicios();
                            break;
                        case 3:
                            rentaSalon renta = new rentaSalon();
                            renta.reservacion(NoCliente);
                            
                            break;
                        case 4:
                            Cuenta perfil = new Cuenta();
                            perfil.perfil(NoCliente);
                            
                            break;
                        case 5:
                            NoCliente = 0;
                            System.out.println("Que tenga un excelente dia, vuelva pronto :)");
                            
                            break;
                        default:
                            break;
                    }
    
                } catch (Exception e) {
                    System.out.println("Por Favor, Ingrese los Numeros Indicados Por Favor");
                    Leer.nextLine();
                    Opcion = -1;
                }
            }

        } while (Opcion != 0);
        
        /*InsertarDatos dato = new InsertarDatos();
        dato.insertarDatosSalon();*/
    }

}
