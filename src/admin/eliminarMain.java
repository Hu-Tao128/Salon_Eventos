package admin;
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
            System.out.println("|       8) MontajeEvento        |");
            System.out.println("|-------------------------------|");
            System.out.println("|       9) EquiposRenta         |");
            System.out.println("|-------------------------------|");
            System.out.println("|       10) Tarjetas            |");
            System.out.println("|-------------------------------|");
            System.out.println("|       11) Tipo Servicio       |");
            System.out.println("|-------------------------------|");
            System.out.println("|       0) salir                |");
            System.out.println("|-------------------------------|");
            System.out.println("================================");
            System.out.println("Escriba el numero de la opción a elegir:");
            validar1 = Leer.next();
            try{
                opcion1 = Integer.parseInt(validar1);

                switch(opcion1){
                    case 1:
                        EliminarCliente data1 = new EliminarCliente();
                        data1.deleteCliente();
                    break;

                    case 2:
                        EliminarEquipamiento data2 = new EliminarEquipamiento();
                        data2.deleteEquipamiento();
                    break;

                    case 3:
                        EliminarEvento data3 = new EliminarEvento();
                        data3.deleteEvento();
                    break;

                    case 4:
                        EliminarMontaje data4 = new EliminarMontaje();
                        data4.deleteMontaje();
                    break;

                    case 5:
                        EliminarRenta data5 = new EliminarRenta();
                        data5.deleteRenta();
                    break;

                    case 6:
                        EliminarSalon data6 = new EliminarSalon();
                        data6.deleteSalon();
                    break;

                    case 7:
                        EliminarServicio data7 = new EliminarServicio();
                        data7.deleteServicio();
                    break;

                    case 8:
                        EliminarEventoMontaje data8 = new EliminarEventoMontaje();
                        data8.deleteEventoMontaje();
                    break;

                    case 9:
                        EliminarEquiposEvento data9 = new EliminarEquiposEvento();
                        data9.deleteEquiposEvento();
                    break;

                    case 10:
                        EliminarTarjetas data10 = new EliminarTarjetas();
                        data10.deleteTarjeta();
                    break;

                    case 11:
                        EliminarTiposServicios data11 = new EliminarTiposServicios();
                        data11.deleteTiposServicios();
                    break;

                    case 0:
                        System.out.println("Saliendo del apartado de eliminacion y regresando al menu de administrador");
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
