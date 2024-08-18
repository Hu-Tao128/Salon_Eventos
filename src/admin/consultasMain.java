package admin;
import java.util.Scanner;

import cliente.MostrarSalones;
import cliente.MostrarServicios;

public class consultasMain {

    public void consultasMenu(){
        System.out.println("Bienvemido al menu de consultas");

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
            System.out.println("|       4) equipos_renta        |");
            System.out.println("|-------------------------------|");
            System.out.println("|       5) equipos_eventos      |");
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
            System.out.println("|       12) servicios_renta     |");
            System.out.println("|-------------------------------|");
            System.out.println("|       13) pago                |");
            System.out.println("|-------------------------------|");
            System.out.println("|       14) metodo_pago         |");
            System.out.println("|-------------------------------|");
            System.out.println("|       15) tarjetas            |");
            System.out.println("|-------------------------------|");
            System.out.println("|       16) tipo_servicio       |");
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
                        MostrarClientes data1 = new MostrarClientes();
                        data1.showClientes();
                        int noCliente = data1.elegirCliente();
                        
                    break;

                    case 2:
                        MostrarDisponibilidad data2 = new MostrarDisponibilidad();
                        data2.showDispobilidad();
                    break;

                    case 3:
                        MostrarEquipamiento data3 = new MostrarEquipamiento();
                        data3.menuEquipamientos();
                    break;

                    case 4:
                        MostrarEquiposRenta data4 = new MostrarEquiposRenta();
                        data4.showEquiposRenta();
                    break;

                    case 5:
                        MostrarEquiposEvento data5 = new MostrarEquiposEvento();
                        data5.showEquiposEvento();
                    break;

                    case 6:
                        MostrarEventoAdmin data6 = new MostrarEventoAdmin();
                        data6.showEventosAdmin();
                    break;

                    case 7:
                        MostrarMontaje data7 = new MostrarMontaje();
                        data7.menuMontajes();
                    break;

                    case 8:
                        MostrarMontajeEvento data8 = new MostrarMontajeEvento();
                        data8.showMontajeEvento();
                    break;

                    case 9:
                        MostrarRenta data9 = new MostrarRenta();
                        data9.menuRentas();
                    break;

                    case 10:
                        MostrarSalones data10 = new MostrarSalones();
                        data10.menuSalones();
                    break;

                    case 11:
                        MostrarServicios data11 = new MostrarServicios();
                        data11.menuServicios();
                    break;

                    case 12:
                        MostrarServiciosRenta data12 = new MostrarServiciosRenta();
                        data12.showServiciosRenta();
                    break;

                    case 13:
                        MostrarPago data13 = new MostrarPago();
                        data13.showPagos();
                    break;

                    case 14:
                        MostrarMetodoPago data14 = new MostrarMetodoPago();
                        data14.showMetodoPago();
                    break;

                    case 15:
                        MostrarTarjetas data15 = new MostrarTarjetas();
                        data15.showTarjetas();
                    break;

                    case 16:
                        MostrarTipoServicio data16 = new MostrarTipoServicio();
                        data16.showTipoServicio();
                    break;

                    case 0:
                        System.out.println("Saliendo del apartado de consultas y regresando al menu de administrador");
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
