package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class pagos {
    private float monto = 0f;
    private String concepto;
    private float restante;

    Scanner Leer = new Scanner(System.in);

    // Método para procesar pago en efectivo
    public float Efectivo(float total) {
        System.out.println("La cantidad a pagar es: " + total);
        System.out.println("Ingrese la cantidad que pagara (Debera apartar con " + (total * 0.2f) + " minimo)");

        try {
            monto = Leer.nextFloat();
            if (monto > total) {
                System.out.println("Su cambio es de: " + (monto - total));
                concepto = "Pago Completo";
            }else if ((monto < (total * 0.2f) && (monto > (total * 0.2f)))) {
                restante = total - monto;
                concepto = "Abono";
                System.out.println("Su restante sera de: " + restante);
            }else{
                System.out.println("El pago es insuficiente");
                monto = 0;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("A ocurrido un error, esta seguro que introdujo un numero?");
            System.out.println("Si desea cancelar presione 0");
        }
        return monto;
    }

    // Método para procesar pago con tarjeta
   public float Tarjeta(float total) {
        System.out.println("Bienvenido al apartado de pago con tarjeta");

        // Validar número de tarjeta
        String NoTarjeta;
        while (true) {
            System.out.println("Introduzca su número de tarjeta (16 dígitos):");
            NoTarjeta = Leer.nextLine();
            if (NoTarjeta.matches("\\d{16}")) {
                break;
            } else {
                System.out.println("Número de tarjeta inválido. Debe tener 16 dígitos.");
            }
        }

        // Validar fecha de vencimiento
DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/yy");
YearMonth fechaVencimiento;
while (true) {
    try {
        System.out.println("Ingrese la fecha de vencimiento (MM/yy):");
        String input = Leer.nextLine();
        fechaVencimiento = YearMonth.parse(input, inputFormatter);
        YearMonth now = YearMonth.now();
        if (fechaVencimiento.isAfter(now)) {
            break;
        } else {
            System.out.println("La fecha de vencimiento no puede ser una fecha pasada.");
        }
    } catch (DateTimeParseException e) {
        System.out.println("Fecha inválida. Por favor, ingrese la fecha en el formato MM/yy.");
    }
}

        // Validar CVC
        String cvc;
        while (true) {
            System.out.println("Introduzca los 3 números CVC:");
            cvc = Leer.nextLine();
            if (cvc.matches("\\d{3}")) {
                break;
            } else {
                System.out.println("CVC inválido. Debe tener 3 dígitos.");
            }
        }

        // Ingresar monto y procesar pago
        System.out.println("Cantidad que desea pagar:");
        try {
            monto = Leer.nextFloat();
            Leer.nextLine(); // Limpiar el buffer del scanner
            concepto = concepto(monto, total);
        } catch (Exception e) {
            System.out.println("A ocurrido un error, ¿está seguro que introdujo un número?");
            Leer.nextLine(); // Limpiar el buffer del scanner en caso de excepción
        }
        
        return monto;
    }

    // Método para determinar el concepto del pago basado en el monto y total
    public String concepto(float monto, float total) {
        if (monto >= 0.2 * total) {
            if (monto >= total) {
                this.concepto = "Pago Completo";
                System.out.println("Tu pago ah sido completado por completo");
            } else {
                this.concepto = "Abono";
                System.out.println("Tu pago de " + monto + " a sido abonado con exito");
            }
        } else if (monto == 0) {
            System.out.println("Cancelando Compra");
        } else {
            this.concepto = "Sin Pago";
        }
        return this.concepto;
    }

    public void EliminarRenta(int IDRenta) {
        Connection conexion = null;
        PreparedStatement statement = null;

        try {
            // Obtener la conexión
            conexion = ConexionBD.obtenerConexion();

            // Desactivar el auto-commit para manejar transacciones manualmente
            conexion.setAutoCommit(false);

            // Eliminar registros relacionados en la tabla 'pago'
            String sqlPago = "DELETE FROM pago WHERE renta = ?";
            statement = conexion.prepareStatement(sqlPago);
            statement.setInt(1, IDRenta);
            statement.executeUpdate();

            // Eliminar registros relacionados en la tabla 'servicios_renta'
            String sqlServiciosRenta = "DELETE FROM servicios_renta WHERE renta = ?";
            statement = conexion.prepareStatement(sqlServiciosRenta);
            statement.setInt(1, IDRenta);
            statement.executeUpdate();

            // Eliminar registros relacionados en la tabla 'equipos_renta'
            String sqlEquiposRenta = "DELETE FROM equipos_renta WHERE renta = ?";
            statement = conexion.prepareStatement(sqlEquiposRenta);
            statement.setInt(1, IDRenta);
            statement.executeUpdate();            

            // Eliminar la renta en sí
            String sqlRenta = "DELETE FROM renta WHERE numero = ?";
            statement = conexion.prepareStatement(sqlRenta);
            statement.setInt(1, IDRenta);
            statement.executeUpdate();

            // Confirmar la transacción
            conexion.commit();
            System.out.println("La renta con ID " + IDRenta + " ha sido eliminada correctamente.");

        } catch (SQLException e) {
            // Revertir la transacción en caso de error
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Error al revertir la transacción: " + rollbackEx.getMessage());
                }
            }
            System.out.println("Error al eliminar la renta: " + e.getMessage());
        } finally {
            // Asegurarse de cerrar los recursos
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar el PreparedStatement: " + e.getMessage());
                }
            }
            if (conexion != null) {
                try {
                    conexion.setAutoCommit(true);
                    conexion.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
}

