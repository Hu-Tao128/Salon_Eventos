package cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            
                default:
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
                String numTel = resultado.getString("numTel");
                String email = resultado.getString("email");

                System.out.println("Detalles del Cliente:");
                System.out.println("========================================================================");
                System.out.printf("| %-25s | %-40s |\n", "Datos", "Valor");
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
}
