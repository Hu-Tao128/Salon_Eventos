package admin;
import java.util.Scanner;

public class modificarMain {

    public void modificarMenu(){
        System.out.println("Bievenido al menu de modificacion de datos");

        Scanner Leer = new Scanner(System.in);

        String validar1;
        int opcion1 = 0;
        
        do{
            System.out.println("================================");
            System.out.println("|              Menu             |");
            System.out.println("|-------------------------------|");
            System.out.println("|       1) cliente              |");
            System.out.println("|-------------------------------|");
            System.out.println("|       2) equipamiento         |");
            System.out.println("|-------------------------------|");
            System.out.println("|       3) evento               |");
            System.out.println("|-------------------------------|");
            System.out.println("|       4) montaje              |");
            System.out.println("|-------------------------------|");
            System.out.println("|       5) renta                |");
            System.out.println("|-------------------------------|");
            System.out.println("|       6) salon                |");
            System.out.println("|-------------------------------|");
            System.out.println("|       7) servicios            |");
            System.out.println("|-------------------------------|");
            System.out.println("|       0) salir                |");
            System.out.println("|-------------------------------|");
            System.out.println("================================");

            validar1 = Leer.next();
            try{
                opcion1 = Integer.parseInt(validar1);

                switch(opcion1){
                    case 1:
                        ActualizarCliente data1 = new ActualizarCliente();
                        data1.updateCliente();
                    break;

                    case 2:
                        ActualizarEquipamiento data2 = new ActualizarEquipamiento();
                        data2.updateEquipamiento();
                    break;

                    case 3:
                        ActualizarEvento data3 = new ActualizarEvento();
                        data3.updateEvento();
                    break;

                    case 4:
                        ActualizarMontaje data4 = new ActualizarMontaje();
                        data4.updateMontaje();
                    break;

                    case 5:
                        ActualizarRenta data5 = new ActualizarRenta();
                        data5.updateRenta();
                    break;

                    case 6:
                        ActualizarSalon data6 = new ActualizarSalon();
                        data6.updateSalon();
                    break;

                    case 7:
                        ActualizarServicios data7 = new ActualizarServicios();
                        data7.updateSalon();
                    break;
                    
                    case 0:
                        System.out.println("Saliendo del apartado de modificacion y regresando al menu de administrador");
                    break;

                    default:
                        System.out.println("Ingrese una de las opciones marcadas");
                    break;
                }
            }catch(Exception e){
                System.out.println("Ingrese numeros por favor");
            }
        }while (opcion1 != 0);
    }
}
