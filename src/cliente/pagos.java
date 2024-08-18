package cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class pagos {
    private float monto = 0f;
    private float restante;
    private String concepto;

    private int contador = 0;

    Scanner Leer = new Scanner(System.in);

    // Método para procesar pago en efectivo
    public float Efectivo(float total, int renta) {
        int metodoPago = 1;
        System.out.println("La cantidad a pagar es: " + total);
        System.out.println("Ingrese la cantidad que pagara (Debera apartar con " + (total * 0.2f) + " minimo)");
        boolean opcion = false;

        do {
            try {
                monto = Leer.nextFloat();
                if (monto > total) {
                    System.out.println("Su cambio es de: " + (monto - total));
                    concepto = "Pago Completo";
                    registrarPago(total, concepto, monto, renta, metodoPago);
                }else if ((monto < (total * 0.2f) && (monto > (total * 0.2f)))) {
                    restante = total - monto;
                    concepto = "Abono";
                    System.out.println("Su restante sera de: " + restante);
                    registrarPago(total, concepto, monto, renta, metodoPago);
                }else if (monto == 0) {
                    opcion = true;
                }else{
                    System.out.println("El pago es insuficiente");
                    monto = 0;
                }
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("A ocurrido un error, esta seguro que introdujo un numero?");
                System.out.println("Si desea cancelar presione 0");
            }
            contador += 1;

        } while (!opcion || contador < 3);
       
        return monto;
    }

    // Método para procesar pago con tarjeta
    public float Tarjeta(float total, int renta) {
        int metodoPago = 2;
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
            if (monto > total) {
                System.out.println("Su cambio es de: " + (monto - total));
                concepto = "Pago Completo";
                registrarPago(total, concepto, monto, renta, metodoPago);
            }else if ((monto < (total * 0.2f) && (monto > (total * 0.2f)))) {
                restante = total - monto;
                concepto = "Abono";
                System.out.println("Su restante sera de: " + restante);
                registrarPago(total, concepto, monto, renta, metodoPago);
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

    public void registrarPago(float total, String concepto, float monto, int renta, int metodoPago){
        LocalDate fechaHoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        String fechaPago = fechaHoy.format(formatter);    

        Connection conexion = null;
        PreparedStatement statement = null;

        float restante = total - monto;

        try {
            conexion = ConexionBD.obtenerConexion();

            String agregarPago = "INSERT INTO pago(numero, fechaPago, monto, concepto, renta, metodoPago) VALUES(null, ?, ?, ?, ?, ?)";

            try (PreparedStatement statementInsert = conexion.prepareStatement(agregarPago)) {
                statementInsert.setString(1, fechaPago);
                statementInsert.setFloat(2, monto);
                statementInsert.setString(3, concepto);
                statementInsert.setInt(4, renta);
                statementInsert.setInt(5, metodoPago);

                int filasInsertadas = statementInsert.executeUpdate();
    
                if (filasInsertadas > 0) {
                    if (restante > 0) {
                        System.out.println("Pago Completado con exito, su restante es: " + restante);
                    }else{
                        System.out.println("Le han sobrado " + (restante * (-1)));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ingresar datos: " + e.getMessage());
        }

    }

    public void getTotalPagos(int IDRenta){
        Connection conexion = null;
        float total;

        try {
            conexion = ConexionBD.obtenerConexion();

            String obtenerPrecio = "SELECT SUM(monto) AS Pagos from pago WHERE renta = ?";

            try (PreparedStatement statement = conexion.prepareStatement(obtenerPrecio)) {
            statement.setInt(1, IDRenta);
            ResultSet resultado = statement.executeQuery();
    
                if (resultado.next()) {
                    float pagos = resultado.getFloat("Pagos");
                    System.out.println("Tienes un total de  " + pagos + " en tus pagos");
                } else {
                    System.out.println("no tienes pagos registrados");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al ingresar datos: " + e.getMessage());
        }

    }

    public void mostrarPagos(int IDRenta) {
        Connection conexion = null;
        float numPagos = 0f;
        float totalPagos = 0f;
        float totalReservacion = 0f;
        float restante = 0f;

        try {
            conexion = ConexionBD.obtenerConexion();

            String consultaTotal = "SELECT total FROM renta WHERE numero = ?";
            PreparedStatement statementTotal = conexion.prepareStatement(consultaTotal);
            statementTotal.setInt(1, IDRenta);
            ResultSet resultadoTotal = statementTotal.executeQuery();

            if (resultadoTotal.next()) {
                totalReservacion = resultadoTotal.getFloat("total");
            }

            String consulta = "SELECT p.numero, p.fechaPago, p.monto, p.concepto, mp.nombre AS metodoPago " +
                              "FROM pago p " +
                              "INNER JOIN metodo_pago mp ON p.metodoPago = mp.numero " +
                              "WHERE p.renta = ?";
            PreparedStatement statement = conexion.prepareStatement(consulta);
            statement.setInt(1, IDRenta);
            ResultSet resultado = statement.executeQuery();

            System.out.println("Pagos de la reservación número: " + IDRenta);
            System.out.println("=========================================================================");
            System.out.printf("| %-5s | %-12s | %-10s | %-15s | %-15s |\n", "Pago", "Fecha Pago", "Monto", "Concepto", "Método de Pago");
            System.out.println("=========================================================================");

            while (resultado.next()) {
                //numPagos = resultado.getInt("pagos");
                int idPago = resultado.getInt("numero");
                String fechaPago = resultado.getString("fechaPago");
                float monto = resultado.getFloat("monto");
                String concepto = resultado.getString("concepto");
                String metodoPago = resultado.getString("metodoPago");

                totalPagos += monto;
                numPagos += 1;

                System.out.printf("| %-5d | %-12s | %-10.2f | %-15s | %-15s |\n", idPago, fechaPago, monto, concepto, metodoPago);
            }
            restante = totalReservacion - totalPagos;
            if (restante < 0) {
                restante = 0;
            }

            System.out.println("=========================================================================");
            System.out.printf("| %-25s | %-10.2f |\n", "Numero de Pagos:", numPagos);
            System.out.printf("| %-25s | %-10.2f |\n", "Total de los Pagos:", totalPagos);
            System.out.printf("| %-25s | %-10.2f |\n", "Total de la Reservación:", totalReservacion);
            System.out.printf("| %-25s | %-10.2f |\n", "Restante por Pagar:", restante);
            System.out.println("==========================================");

        } catch (SQLException e) {
            System.out.println("Error al obtener los pagos: " + e.getMessage());
        }
    }

    public boolean getPago(int IDRenta){
        boolean valid = false;

        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            String obtenerConceptoPago = "SELECT concepto FROM pago WHERE renta = ?";

            statement = conexion.prepareStatement(obtenerConceptoPago);
            statement.setInt(1, IDRenta);
            resultado = statement.executeQuery();

            while (resultado.next()) {
                String conceptoPago = resultado.getString("concepto");
                if ("Pago Completo".equals(conceptoPago)) {
                    valid = true;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return valid;
    }
}

