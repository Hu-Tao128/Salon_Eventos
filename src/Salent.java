import java.util.Scanner;

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

                        break;
                    case 0:
                        System.out.println("Que tenga buen dia :)");
                        break;
            
                    default:
                        break;
                }

            } catch (Exception e) {
                System.out.println("Ingrese Numeros por favor");
            }

            }else{
                System.out.println("================================");
                System.out.println("|              Menu             |");
                System.out.println("|-------------------------------|");
                System.out.println("|        1) Ver mi Evento       |");
                System.out.println("|-------------------------------|");
                System.out.println("|         2) Ver Salones        |");
                System.out.println("|-------------------------------|");
                System.out.println("|         3) Ver Eventos        |");
                System.out.println("|-------------------------------|");
                System.out.println("|            0) Salir           |");
                System.out.println("|-------------------------------|");
                System.out.println("================================");

                Validar = Leer.next();

                try {
                    Opcion = Integer.parseInt(Validar);
    
                    switch (Opcion) {
                        case 1:
                            verMisReservaciones reservaciones = new verMisReservaciones();
                            reservaciones.verMisReservaciones(NoCliente);
                            break;
                        case 2:
                            MostrarSalones salones = new MostrarSalones();
                            salones.showSalones();
                                    
                            break;
                        case 3:
                            MostrarEventos eventos = new MostrarEventos();
                            eventos.showEventos();

                            break;
                        case 0:
                            System.out.println("Que tenga buen dia :)");
                            Leer.close();
                            break;
                
                        default:
                            break;
                    }
    
                } catch (Exception e) {
                    System.out.println("Ingrese Numeros por favor");
                }
            }

        } while (Opcion != 0);
        
        /*InsertarDatos dato = new InsertarDatos();
        dato.insertarDatosSalon();*/
    }

}
