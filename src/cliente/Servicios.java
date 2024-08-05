package cliente;

import java.util.Scanner;

import admin.MostrarEquipamiento;
import admin.MostrarMontaje;

public class Servicios {

    Scanner Leer = new Scanner(System.in);
    private int opcion;

    public void nuestrosServicios(){

        do {
            System.out.println("Aqui nuestros Servicios de Renta de Salones");
            System.out.println("Que Accion Desea Realizar?");
            System.out.println("1) Mostrar Nuestros Salones");
            System.out.println("2) Mostrar Nuestros Eventos");
            System.out.println("3) Mostrar Nuestros Montajes");
            System.out.println("4) Mostrar Nuestros Servicios para Salones");
            System.out.println("5) Mostrar Nuestros Equipos para Salones");
            System.out.println("0) Salir");
            
            opcion = Leer.nextInt();
            switch (opcion) {
                case 1:
                    MostrarSalones salones = new MostrarSalones();
                    salones.showSalones();
                    break;
                case 2:
                    MostrarEventos eventos = new MostrarEventos();
                    eventos.showEventos();
                    break;
                case 3:
                    MostrarMontaje montajes = new MostrarMontaje();
                    montajes.showMontaje();
                    break;
                case 4:
                    MostrarServicios servicios = new MostrarServicios();
                    servicios.showServicios();
                    break;
                case 5:
                    MostrarEquipamiento equipo = new MostrarEquipamiento();
                    equipo.showEquipamiento();
                    break;
            
                default:
                    break;
            }
        } while (opcion != 0);
        
    }
}
