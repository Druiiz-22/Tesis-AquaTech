package database;

import static properties.Mensaje.msjError;

/**
 *
 * @author diego
 */
public class CreateDB {

    public static boolean createCliente(String cedula, String nombre, String apellido, String telefono, String direccion) {

//        Mensaje por si no se puede conectar con la base de datos
//        msjError("No se pudo crear el cliente en la base de datos."
//                + "\nPor favor, verifique su conexión.");
//        
//        Mensaje por si el usuario no coincide
//        msjError("<html>Su contraseña es incorrecta. <b>No se creará el cliente</b> "
//        + "hasta que valide sus datos.</html>");

        return true;
    }

    public static boolean createProveedor(String rif, String nombre, String telefono, String direccion) {

        
        return true;
    }

    public static void createUsuario() {

    }

    public static void createTravaso() {

    }

    public static void createVenta() {

    }

    public static void createRecarga() {

    }

    public static void createCompra() {

    }

}
