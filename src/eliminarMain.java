import java.util.Scanner;

public class eliminarMain {

    public void eliminarMenu(){
        System.out.println("Bienvenido al menu de eliminacion de datos");

        Scanner Leer = new Scanner(System.in);

        String validar1;
        int opcion1 = 0;
        
        do{
            System.out.println("================================");
            System.out.println("|              Menu             |");
            System.out.println("|-------------------------------|");
            System.out.println("|       1) cliente              |");
            System.out.println("|-------------------------------|");
            System.out.println("|       2) disponibilidad       |");
            System.out.println("|-------------------------------|");
            System.out.println("|       3) equipamiento         |");
            System.out.println("|-------------------------------|");
            System.out.println("|       4) equipos_eventos      |");
            System.out.println("|-------------------------------|");
            System.out.println("|       5) equipos_renta        |");
            System.out.println("|-------------------------------|");
            System.out.println("|       6) evento               |");
            System.out.println("|-------------------------------|");
            System.out.println("|       7) montaje              |");
            System.out.println("|-------------------------------|");
            System.out.println("|       8) montaje_evento       |");
            System.out.println("|-------------------------------|");
            System.out.println("|       9) renta                |");
            System.out.println("|-------------------------------|");
            System.out.println("|       10) salon               |");
            System.out.println("|-------------------------------|");
            System.out.println("|       11) servicios           |");
            System.out.println("|-------------------------------|");
            System.out.println("|       12) servicios renta     |");
            System.out.println("|-------------------------------|");
            System.out.println("|       13) salir               |");
            System.out.println("|-------------------------------|");
            System.out.println("================================");

            validar1 = Leer.next();
            try{
                opcion1 = Integer.parseInt(validar1);

                switch(opcion1){
                    case 1:
                        
                    break;

                    case 2:
                        
                    break;

                    case 3:

                    break;

                    case 4:
                        
                    break;

                    case 5:

                    break;

                    case 6:

                    break;

                    case 7:

                    break;

                    case 8:

                    break;

                    case 9:

                    break;

                    case 10:

                    break;

                    case 11:

                    break;

                    case 12:

                    break;

                    case 13:
                        System.out.println("Saliendo del apartado de eliminacion y regresando al menu de administrador");
                    break;

                    default:
                        System.out.println("Ingrese una de las opciones marcadas");
                    break;
                }
            }catch(Exception e){
                System.out.println("Ingrese numeros por favor");
            }
        }while (opcion1 != 13);
        Leer.close();
    } 
}
