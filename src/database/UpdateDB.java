package database;

/**
 *
 * @author diego
 */
public class UpdateDB {

    /**
     * Función para editar los datos de un cliente registrado
     * 
     * @param id
     * @param cedula
     * @param nombre
     * @param apellido
     * @param telefono
     * @param direccion
     * @return 
     */
    public static boolean editCliente(String id, String cedula, String nombre, String apellido, String telefono, String direccion) {

//        Mensaje por si no se puede conectar con la base de datos
//        msjError("No se pudo crear el cliente en la base de datos."
//                + "\nPor favor, verifique su conexión.");
//        
//        Mensaje por si el usuario no coincide
//        msjError("<html>Su contraseña es incorrecta. <b>No se creará el cliente</b> "
//        + "hasta que valide sus datos.</html>");
        
        return true;
    }

    /**
     * Función para editar los datos de un proveedor registrado
     * @param id
     * @param rif
     * @param nombre
     * @param telefono
     * @param direccion
     * @return 
     */
    public static boolean editProveedor(String id, String rif, String nombre, String telefono, String direccion) {
        
        return true;
    }
    
    public static void editUsuario(){
        
    }
}
