import java.util.ArrayList;
import java.util.Scanner;

public class InvoiceSystem {
    private static ArrayList<InvoiceItem> items = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("SALENT - Datos de Factura");

        System.out.print("Razon Social: ");
        String razonSocial = scanner.nextLine();

        System.out.print("RUC: ");
        String ruc = scanner.nextLine();

        System.out.print("No.: ");
        String numero = scanner.nextLine();

        System.out.print("Direccion: ");
        String direccion = scanner.nextLine();

        System.out.print("Fecha (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();

        System.out.println("\nSALENT - Datos Clientes");

        System.out.print("Nombre Cliente: ");
        String nombreCliente = scanner.nextLine();

        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        while (true) {
            System.out.println("\nSALENT - Agregar Datos a Facturar");

            System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine();

            System.out.print("Precio Unit: ");
            double precioUnit = scanner.nextDouble();

            System.out.print("Cantidad: ");
            int cantidad = scanner.nextInt();

            System.out.print("IVA (%): ");
            double iva = scanner.nextDouble();
            scanner.nextLine();  // Consume la nueva línea

            items.add(new InvoiceItem(descripcion, precioUnit, cantidad, iva));

            System.out.print("¿Desea agregar otro artículo? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("n")) {
                break;
            }
        }

        System.out.println("\nSALENT - Tabla Productos");
        System.out.printf("%-10s %-20s %-10s %-5s %-15s%n", "Cantidad", "Descripcion", "Precio Unit", "IVA", "Precio Total");

        for (InvoiceItem item : items) {
            System.out.println(item);
        }

        scanner.close();
    }

    static class InvoiceItem {
        private String description;
        private double unitPrice;
        private int quantity;
        private double iva;

        public InvoiceItem(String description, double unitPrice, int quantity, double iva) {
            this.description = description;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
            this.iva = iva;
        }

        public double getTotalPrice() {
            return unitPrice * quantity * (1 + iva / 100);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-10d %-20s %-10.2f %-5.2f %-15.2f", 
                                    quantity, description, unitPrice, iva, getTotalPrice()));
            return sb.toString();
        }
    }
}
