import java.util.Scanner;

public class administradorMain {
    
    public void menuAdministrador(){
        Scanner Leer = new Scanner(System.in);
        System.out.println("Bienvenido a la vista de administrador");
        
        String validar1;
        int opcion1 = 0;
        
        do{
            System.out.println("================================");
            System.out.println("|              Menu             |");
            System.out.println("|-------------------------------|");
            System.out.println("|       1) ver consultas        |");
            System.out.println("|-------------------------------|");
            System.out.println("|       2) agregar datos        |");
            System.out.println("|-------------------------------|");
            System.out.println("|       3) eliminar datos       |");
            System.out.println("|-------------------------------|");
            System.out.println("|       4) modificar datos      |");
            System.out.println("|-------------------------------|");
            System.out.println("|            5) Salir           |");
            System.out.println("|-------------------------------|");
            System.out.println("================================");

            validar1 = Leer.next();
            try{
                opcion1 = Integer.parseInt(validar1);

                switch(opcion1){
                    case 1:
                        consultasMain data = new consultasMain();
                        data.consultasMenu();
                    break;

                    case 2:
                        agregarMain data1 = new agregarMain();
                        data1.agregarMenu();
                    break;

                    case 3:
                        eliminarMain data2 = new eliminarMain();
                        data2.eliminarMenu();
                    break;

                    case 4:
                        modificarMain data3 = new modificarMain();
                        data3.modificarMenu();
                    break;

                    default:
                        System.out.println("Ingrese una de las opciones marcadas");
                    break;
                }
            }catch(Exception e){
                System.out.println("Ingrese numeros por favor");
            }
        }while (opcion1 != 5);
        Leer.close();
    }
}
