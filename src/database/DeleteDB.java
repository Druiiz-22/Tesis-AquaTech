package database;

/**
 *
 * @author diego
 */
public class DeleteDB {

    /**
     * Función para intentar eliminar un cliente seleccionado de la base de
     * datos
     *
     * @param cedula Cedula del cliente a eliminar
     * @return
     */
    public static boolean removeCliente(Object cedula) {

        return true;
    }

    /**
     * Función para intentar eliminar un proveedor seleccionado de la base de
     * datos
     *
     * @param rif RIF del proveedor a eliminar
     * @return
     */
    public static boolean removeProveedor(Object rif) {

        return true;
    }

    /**
     * Función para intentar eliminar un usuario seleccionado en la base de
     * datos
     *
     * @param id ID del usuario a eliminar
     * @param cedula Cédula para eliminar el usuario en clientes
     * @return
     */
    public static boolean removeUsuario(Object id, Object cedula) {

        //Remover el cliente con la cédula del usuario
        if(removeCliente(cedula)){
            
        }
        
        return true;
    }
}
