import java.util.Scanner;

public class Cuenta {

    public int InicioSesion(){
        Scanner leer = new Scanner(System.in);
        int opcion;
        int NoCliente = 0;

        do {
            System.out.println("Bienvenido Cliente");
            System.out.println("Desea Ingresar Por:");
            System.out.println("1) Numero de Cliente");
            System.out.println("2) Nombre de Contacto");
            System.out.println("3) Nombre Empresa");
            System.out.println("0) Salir");
            opcion = leer.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese su Numero de Cliente");
                    int ID = leer.nextInt();

                    ValidarCuenta NumCliente = new ValidarCuenta();
                    NoCliente = NumCliente.validarID(ID);

                    break;
                case 2:
                    System.out.println("Ingrese su Primer Apellido");
                    String Apellido = leer.nextLine();

                    System.out.println("Ingrese Su Nombre, tal como lo indico al registrarse");
                    String Nombre = leer.nextLine();

                    ValidarCuenta NomContacto = new ValidarCuenta();
                    NoCliente = NomContacto.ValidarNombre(Nombre, Apellido);
                    break;
                case 3:
                    System.out.println("Ingrese su Nombre de Empresa");
                    String NombreFiscal = leer.nextLine();

                    ValidarCuenta NomFiscal = new ValidarCuenta();
                    NoCliente = NomFiscal.ValidarNombreFiscal(NombreFiscal);
                    break;
                case 0:
                    System.out.println("Que Tenga Buen Dia :)");
                    break;
            
                default:
                    System.out.println("Ingrese un numero de la lista por favor");
                    break;
            }
            leer.close();
        } while (opcion != 0 || NoCliente != 0);

        return NoCliente;

    }
}