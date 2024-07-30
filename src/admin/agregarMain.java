package admin;
import java.util.Scanner;

public class agregarMain {
    public void agregarMenu(){
        System.out.println("Bienvenido al menu de agregacion de datos");

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
                        AgregarClienteAdmin data1 = new AgregarClienteAdmin();
                        data1.agregarClienteF();
                    break;

                    case 2:
                        AgregarEquipamiento data2 = new AgregarEquipamiento();
                        data2.agregarEquipamientoF();
                    break;

                    case 3:
                        AgregarEvento data3 = new AgregarEvento();
                        data3.agregarEventoF();
                    break;

                    case 4:
                        AgregarMontaje data4 = new AgregarMontaje();
                        data4.agregarMontajeF();
                    break;

                    case 5:

                    break;

                    case 6:
                        AgregarSalon data6 = new AgregarSalon();
                        data6.agregarSalonF();
                    break;

                    case 7:
                        AgregarServicios data7 = new AgregarServicios();
                        data7.agregarServiciosF();
                    break;

                    case 0:
                        System.out.println("Saliendo del apartado de agregacion y regresando al menu de administrador");
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
