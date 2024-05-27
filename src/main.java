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
            System.out.println("|       1) Iniciar Sesion       |");
            System.out.println("|-------------------------------|");
            System.out.println("|         2) Registrarse        |");
            System.out.println("|-------------------------------|");
            System.out.println("|   3) Ver Servicios y Salones  |");
            System.out.println("|-------------------------------|");
            System.out.println("|            4) Salir           |");
            System.out.println("|-------------------------------|");
            System.out.println("================================");

            Validar = Leer.next();
            //popn variable string y co try catch validas si se puede convertir a entero

            try {
                Opcion = Integer.parseInt(Validar);


            switch (Opcion) {
                case 1:
                    System.out.println("Inicia sesion");
                    //InicioSesion User = new InicioSesion();
                    //User.InicioSesion();
                    
                    break;
                case 2:
                    System.out.println("Bienvenido al sisema para registrarse en nuestro salon de eventos /nombre/");
                    
                    
                    break;
                case 3:
                    InsertarDatos datos = new InsertarDatos();
                    datos.insertarDatosSalon();
                    break;
            
                default:
                    System.out.println("Por favor use una de las opciones anteriores");
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
