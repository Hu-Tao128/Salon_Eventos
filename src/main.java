import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        Scanner Leer = new Scanner(System.in);
        
        
        String Validar;
        int Opcion = 0;
        
        do {
            
            System.out.println("================================");
            System.out.println("|              Menu             |");
            System.out.println("|-------------------------------|");
            System.out.println("|        1) Ver mi Evento       |");
            System.out.println("|-------------------------------|");
            System.out.println("|         2) Registrarse        |");
            System.out.println("|-------------------------------|");
            System.out.println("|    3) Ver Eventos y Salones   |");
            System.out.println("|-------------------------------|");
            System.out.println("|            4) Salir           |");
            System.out.println("|-------------------------------|");
            System.out.println("================================");

            Validar = Leer.next();

            try {
                Opcion = Integer.parseInt(Validar);

                switch (Opcion) {
                    case 1:
                        
                        break;
                    case 2:

                        break;
                    case 3:
                        System.out.println("Elige una opcion: ");
                        System.out.println("1. Comenzar una renta del salon");
                        System.out.println("2. Mostrar eventos de salones");
                        System.out.println("3. Regresar");
                        Opcion = Leer.nextInt();

                        switch (Opcion) {
                            case 1:
                                MostrarSalones salones = new MostrarSalones();
                                salones.MostrarSalones();   
                                
                                break;
                        
                            case 2:
                                MostrarEventos eventos = new MostrarEventos();
                                eventos.MostrarEventos();
                                break;
                        
                            case 3:
                                
                                break;

                            case 4:
                                
                                break;
                            default:
                                break;
                        }
                        break;
                    case 4:
                        System.out.println("Que tenga buen dia :)");
                        break;
            
                    default:
                        break;
                }

            } catch (Exception e) {
                System.out.println("Ingrese Numeros por favor");
            }

        } while (Opcion != 4);
        
        /*InsertarDatos dato = new InsertarDatos();
        dato.insertarDatosSalon();*/
    }

}
