package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import conexionDB.ConexionBD;

public class OpcionesCuenta {

    Scanner Leer = new Scanner(System.in);
    private int opcion;
    
    public void menuModificar(int NoCliente){
        do {
            
            System.out.println("Bienvenido al menu de gestion de cuentas");
            System.out.println("Que Operacion Desea Realizar?");
            System.out.println("1) Agregar o Cambiar Nombre de Empresa");
            System.out.println("2) Eliminar Nombre de Empresa");
            System.out.println("3) Modificar Nombre");
            System.out.println("4) Modificar Primer Apellido");
            System.out.println("5) Modificar o Agregar Segundo Apellido");
            System.out.println("6) Cambiar Numero de Telefono");
            System.out.println("7) Cambiar Correo Electronico");
            System.out.println("8) Agregar Tarjeta");
            if (tieneTarjeta(NoCliente)) {                
                System.out.println("9) Ver mis Tarjetas");
            }
            System.out.println("0) Salir");
            opcion = Leer.nextInt();
            Leer.nextLine();

            switch (opcion) {
                case 1:
                        agregarNombreFiscal(NoCliente);
                    break;
                case 2:
                        eliminarNombreFiscal(NoCliente);
                    break;
                case 3:
                        cambiarNombre(NoCliente);
                    break;
                case 4:
                        cambiarPrimerApellido(NoCliente);
                    break;
                case 5:
                        cambiarSegundoApellido(NoCliente);
                    break;
                case 6:
                        cambiarNumTel(NoCliente);
                    break;
                case 7:
                        cambiarEmail(NoCliente);
                    break;
                case 8:                        
                        agregarTarjetas(NoCliente);
                    break;
                case 9:
                    if (tieneTarjeta(NoCliente)) {                            
                        mostrarTarjetas(NoCliente);
                    }else{
                        System.out.println("Ingrese un numero de los que se te indican");
                    }
                    break;
            
                default:
                    System.out.println("Ingrese un numero de los que se te indican");
                    break;
            }

            verPerfil(NoCliente);
        } while (opcion != 0);
    }

    public void agregarNombreFiscal(int NoCliente) {
        System.out.println("Ingrese el nuevo nombre fiscal:");
        String nuevoNombreFiscal = Leer.nextLine();

        // Verificar si el nombre fiscal ya existe
        String verificarNombreFiscal = "SELECT COUNT(*) FROM cliente WHERE nombreFiscal = ? AND nombreFiscal != Null";
        //consulta para modificar datos de la BD
        String actualizarNombreFiscal = "UPDATE cliente SET nombreFiscal = ? WHERE numero = ?";

        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();

            try (PreparedStatement statementVerificar = conexion.prepareStatement(verificarNombreFiscal)) {
                statementVerificar.setString(1, nuevoNombreFiscal);
                try (ResultSet resultado = statementVerificar.executeQuery()) {
                    if (resultado.next()) {
                        int count = resultado.getInt(1);
                        if (count > 0) {
                            System.out.println("El nombre fiscal '" + nuevoNombreFiscal + "' ya está registrado por otro cliente.");
                            return;
                        }
                    }
                }
            }

            try (PreparedStatement statementActualizar = conexion.prepareStatement(actualizarNombreFiscal)) {
                statementActualizar.setString(1, nuevoNombreFiscal);
                statementActualizar.setInt(2, NoCliente);
                int filasActualizadas = statementActualizar.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("Nombre fiscal actualizado exitosamente.");
                } else {
                    System.out.println("No se pudo actualizar el nombre fiscal. Verifique el número de cliente.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el nombre fiscal: " + e.getMessage());
        }
    }

    public void eliminarNombreFiscal(int NoCliente) {
        String eliminarNombreFiscal = "UPDATE cliente SET nombreFiscal = NULL WHERE numero = ?";

        Connection conexion = null;

        try {
            conexion = ConexionBD.obtenerConexion();

            try (PreparedStatement statementActualizar = conexion.prepareStatement(eliminarNombreFiscal)) {
                statementActualizar.setInt(1, NoCliente);
                int filasActualizadas = statementActualizar.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("Nombre fiscal eliminado exitosamente.");
                } else {
                    System.out.println("No se pudo eliminar el nombre fiscal. Verifique el número de cliente.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar el nombre fiscal: " + e.getMessage());
        }
    }

    public void cambiarNombre(int NoCliente) {
        String actualizarNombre = "UPDATE cliente SET nomContacto = ? WHERE numero = ?";

        Connection conexion = null;

        System.out.println("Ingrese el nuevo nombre de contacto:");
        String nuevoNombre = Leer.nextLine();

        try {
            conexion = ConexionBD.obtenerConexion();

            try (PreparedStatement statementActualizar = conexion.prepareStatement(actualizarNombre)) {
                statementActualizar.setString(1, nuevoNombre);
                statementActualizar.setInt(2, NoCliente);
                int filasActualizadas = statementActualizar.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("Nombre de contacto actualizado exitosamente.");
                } else {
                    System.out.println("No se pudo actualizar el nombre de contacto. Verifique el número de cliente.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el nombre de contacto: " + e.getMessage());
        } 
    }

    public void cambiarPrimerApellido(int NoCliente) {
        String actualizarApellido = "UPDATE cliente SET primerApellido = ? WHERE numero = ?";

        Connection conexion = null;

        System.out.println("Ingrese el nuevo primer apellido:");
        String nuevoApellido = Leer.nextLine();

        try {
            conexion = ConexionBD.obtenerConexion();

            // Actualizar el primer apellido del cliente
            try (PreparedStatement statementActualizar = conexion.prepareStatement(actualizarApellido)) {
                statementActualizar.setString(1, nuevoApellido);
                statementActualizar.setInt(2, NoCliente);
                int filasActualizadas = statementActualizar.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("Primer apellido actualizado exitosamente.");
                } else {
                    System.out.println("No se pudo actualizar el primer apellido. Verifique el número de cliente.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el primer apellido: " + e.getMessage());
        }
    }

    public void cambiarSegundoApellido(int NoCliente) {
        String actualizarSegundoApellido = "UPDATE cliente SET segundoApellido = ? WHERE numero = ?";

        Connection conexion = null;

        System.out.println("Ingrese el nuevo segundo apellido (dejar en blanco para eliminar):");
        String nuevoSegundoApellido = Leer.nextLine();

        if (nuevoSegundoApellido.isEmpty()) {
            nuevoSegundoApellido = null;
        }

        try {
            conexion = ConexionBD.obtenerConexion();

            try (PreparedStatement statementActualizar = conexion.prepareStatement(actualizarSegundoApellido)) {
                statementActualizar.setString(1, nuevoSegundoApellido);
                statementActualizar.setInt(2, NoCliente);
                int filasActualizadas = statementActualizar.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("Segundo apellido actualizado exitosamente.");
                } else {
                    System.out.println("No se pudo actualizar el segundo apellido. Verifique el número de cliente.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el segundo apellido: " + e.getMessage());
        }
    }

    public void cambiarNumTel(int NoCliente) {
        String actualizarTelefono = "UPDATE cliente SET numTel = ? WHERE numero = ?";

        Connection conexion = null;

        System.out.println("Ingrese el nuevo número de teléfono:");
        String nuevoNumeroTelefono = Leer.nextLine();

        try {
            conexion = ConexionBD.obtenerConexion();

            try (PreparedStatement statementActualizar = conexion.prepareStatement(actualizarTelefono)) {
                statementActualizar.setString(1, nuevoNumeroTelefono);
                statementActualizar.setInt(2, NoCliente);
                int filasActualizadas = statementActualizar.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("Número de teléfono actualizado exitosamente.");
                } else {
                    System.out.println("No se pudo actualizar el número de teléfono. Verifique el número de cliente.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el número de teléfono: " + e.getMessage());
        }
    }

    public void cambiarEmail(int NoCliente) {
        String actualizarCorreo = "UPDATE cliente SET email = ? WHERE numero = ?";

        Connection conexion = null;

        System.out.println("Ingrese el nuevo correo electrónico:");
        String nuevoCorreoElectronico = Leer.nextLine();

        try {
            conexion = ConexionBD.obtenerConexion();

            try (PreparedStatement statementActualizar = conexion.prepareStatement(actualizarCorreo)) {
                statementActualizar.setString(1, nuevoCorreoElectronico);
                statementActualizar.setInt(2, NoCliente);
                int filasActualizadas = statementActualizar.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("Correo electrónico actualizado exitosamente.");
                } else {
                    System.out.println("No se pudo actualizar el correo electrónico. Verifique el número de cliente.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el correo electrónico: " + e.getMessage());
        }
    }

    public void verPerfil(int IDCliente) {
        Connection conexion = null;
        String consultaCliente = "SELECT nombreFiscal, nomContacto, primerApellido, segundoApellido, numTel, email FROM cliente WHERE numero = ?";

        conexion = ConexionBD.obtenerConexion();

        try (PreparedStatement statement = conexion.prepareStatement(consultaCliente)) {
            statement.setInt(1, IDCliente);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) { 
                String nombreFiscal = resultado.getString("nombreFiscal");
                String Nombre = resultado.getString("nomContacto");
                String primerApellido = resultado.getString("primerApellido");
                String segundoApellido = resultado.getString("segundoApellido");
                
                    if (segundoApellido  == "" || segundoApellido == null) {
                        segundoApellido = "";
                    }                
                
                String numTel = resultado.getString("numTel");
                String email = resultado.getString("email");

                System.out.println("Detalles del Cliente:");
                System.out.println("========================================================================");
                System.out.printf("| %-25s | %-40s |\n", "Cliente", IDCliente);
                System.out.println("========================================================================");
                    
                if (nombreFiscal != null && !nombreFiscal.isEmpty()) {
                    System.out.printf("| %-25s | %-40s |\n", "Nombre de Empresa", nombreFiscal);
                }
                System.out.printf("| %-25s | %-40s |\n", "Nombre", Nombre);
                System.out.printf("| %-25s | %-40s |\n", "Apellido Paterno", primerApellido);
                System.out.printf("| %-25s | %-40s |\n", "Apellido Materno", segundoApellido);
                System.out.printf("| %-25s | %-40s |\n", "Teléfono", numTel);
                System.out.printf("| %-25s | %-40s |\n", "Email", email);
                System.out.println("========================================================================");

            } else {
                System.out.println("No se encontraron detalles para el cliente con ID: " + IDCliente);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los datos del cliente: " + e.getMessage());
        }
    }

    public void mostrarTarjetas(int clienteID) {
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultado = null;
    
        try {
            conexion = ConexionBD.obtenerConexion();
            String consulta = "SELECT numTarjeta, " + 
                              "DATE_FORMAT(vencimiento, '%m/%y') AS vencimiento, " +
                              "cvc FROM tarjetas WHERE cliente = ?";
            
            statement = conexion.prepareStatement(consulta);
            statement.setInt(1, clienteID);
            resultado = statement.executeQuery();
    
            System.out.println("===========================================");
            System.out.printf("| %-19s | %-10s | %-4s |\n", "Número de Tarjeta", "Vencimiento", "CVC");
            System.out.println("===========================================");
    
            while (resultado.next()) {
                String numTarjeta = resultado.getString("numTarjeta");
                String fechaVencimiento = resultado.getString("vencimiento");
                int cvc = resultado.getInt("cvc");
    
                // Formatear el número de tarjeta para que se muestre como 1234-5678-9123-4567
                String formattedTarjeta = numTarjeta.replaceAll("(\\d{4})(?=\\d)", "$1-");
    
                System.out.printf("| %-19s | %-10s | %-4d |\n", formattedTarjeta, fechaVencimiento, cvc);
            }
    
            System.out.println("===========================================");
            
        } catch (SQLException e) {
            System.out.println("Error al consultar las tarjetas: " + e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (resultado != null) resultado.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }
    
    
    public boolean tieneTarjeta(int clienteID) {
        boolean tieneTarjeta = false;
        Connection conexion = null;
        PreparedStatement statement = null;
        ResultSet resultado = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            String consulta = "SELECT COUNT(*) AS cantidad FROM tarjetas WHERE cliente = ?";
            
            statement = conexion.prepareStatement(consulta);
            statement.setInt(1, clienteID);
            resultado = statement.executeQuery();

            if (resultado.next()) {
                int cantidad = resultado.getInt("cantidad");
                tieneTarjeta = cantidad > 0; 
            }
            
        } catch (SQLException e) {
            System.out.println("Error al verificar las tarjetas del cliente: " + e.getMessage());
        } finally {
            try {
                if (resultado != null) resultado.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return tieneTarjeta;
    }

    public void agregarTarjetas(int NoCliente) {
        System.out.println("Bienvenido al apartado de Agregar Tarjeta");
    
        Scanner Leer = new Scanner(System.in);
        int intentos = 0;
    
        // Validar número de tarjeta
        String NoTarjeta = null;
        while (intentos < 3) {
            System.out.println("Introduzca su número de tarjeta (16 dígitos):");
            NoTarjeta = Leer.nextLine();
            if (NoTarjeta.matches("\\d{16}")) {
                break;
            } else {
                intentos++;
                System.out.println("Número de tarjeta inválido. Debe tener 16 dígitos.");
            }
        }
        if (intentos == 3) {
            System.out.println("Ha excedido el número máximo de intentos. El proceso ha sido cancelado.");
            return;
        }
    
        // Validar fecha de vencimiento
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/yy");
        YearMonth fechaVencimiento = null;
        intentos = 0;
        while (intentos < 3) {
            try {
                System.out.println("Ingrese la fecha de vencimiento (MM/yy):");
                String input = Leer.nextLine();
                fechaVencimiento = YearMonth.parse(input, inputFormatter);
                YearMonth now = YearMonth.now();
                if (fechaVencimiento.isAfter(now)) {
                    break;
                } else {
                    intentos++;
                    System.out.println("La fecha de vencimiento no puede ser una fecha pasada.");
                }
            } catch (DateTimeParseException e) {
                intentos++;
                System.out.println("Fecha inválida. Por favor, ingrese la fecha en el formato MM/yy.");
            }
        }
        if (intentos == 3) {
            System.out.println("Ha excedido el número máximo de intentos. El proceso ha sido cancelado.");
            return;
        }
    
        // Validar CVC
        String cvc = null;
        intentos = 0;
        while (intentos < 3) {
            System.out.println("Introduzca los 3 números CVC:");
            cvc = Leer.nextLine();
            if (cvc.matches("\\d{3}")) {
                break;
            } else {
                intentos++;
                System.out.println("CVC inválido. Debe tener 3 dígitos.");
            }
        }
        if (intentos == 3) {
            System.out.println("Ha excedido el número máximo de intentos. El proceso ha sido cancelado.");
            return;
        }
    
        // Conectar a la base de datos y agregar la tarjeta
        Connection conexion = null;
    
        try {
            conexion = ConexionBD.obtenerConexion();
            String sql = "INSERT INTO tarjetas (numTarjeta, vencimiento, cvc, cliente) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, NoTarjeta);
            statement.setDate(2, java.sql.Date.valueOf(fechaVencimiento.atDay(1)));
            statement.setInt(3, Integer.parseInt(cvc));
            statement.setInt(4, NoCliente);
    
            int filasAfectadas = statement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Tarjeta agregada con éxito.");
            } else {
                System.out.println("No se pudo agregar la tarjeta.");
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar la tarjeta: " + e.getMessage());
        }
    }
    
}
