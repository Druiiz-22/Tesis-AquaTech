package database;

import properties.Mensaje;

/**
 *
 * @author diego
 */
public class DeleteDB implements properties.Constantes {

    /**
     * Función para intentar eliminar un cliente seleccionado de la base de
     * datos
     *
     * @param id ID del cliente
     * @return
     */
    public static boolean removeCliente(Object id) {
        //Obtener el rol del usuario actual
        int rol = ReadDB.getUserRol(main.Frame.getUserIdentified());
        //Validar que el usuario que realiza la acción, cuente con los permisos
        //o si se está creando un usuario desde el login
        if (rol == EMPLEADO || rol == ADMINISTRADOR || rol == OPERADOR) {
            //Preparar la sentencia SQL para actualizar el cliente
            String sql = "DELETE FROM Cliente WHERE id = " + id;

            //Instanciar una conexión con la base de datos y conectarla
            ConexionDB bdd = new ConexionDB(true);
            bdd.conectar();

            //Obtener el resultado de la sentencia
            int status = bdd.executeQuery(sql);

            //Si el status es mayor que 0, entonces la conexión y ejecución 
            //fue exitosa
            if (status > 0) {

                //Mensaje de éxito
                Mensaje.msjInformativo("El cliente ha sido eliminado con éxito.");

                return true;

                //Comprobar si se encontró el cliente y se aplicaron los cambios
            } else {
                //Mensaje de error por falta de permisos
                Mensaje.msjError("No se encontró el cliente seleccionado y no se "
                        + "eliminó.\nPor favor, actualice los "
                        + "registro y verifique la existencia del cliente.");
            }

            //Terminar la conexión con la base de datos
            bdd.desconectar();
        }
        return false;
    }

    /**
     * Función para intentar eliminar un proveedor seleccionado de la base de
     * datos
     *
     * @param id ID del proveedor
     * @return
     */
    public static boolean removeProveedor(Object id) {
        //Obtener el rol del usuario actual
        int rol = ReadDB.getUserRol(main.Frame.getUserIdentified());
        //Validar que el usuario que realiza la acción, cuente con los permisos
        //o si se está creando un usuario desde el login
        if (rol == EMPLEADO || rol == ADMINISTRADOR || rol == OPERADOR) {
            //Preparar la sentencia SQL para actualizar el cliente
            String sql = "DELETE FROM Proveedores WHERE id = " + id;

            //Instanciar una conexión con la base de datos y conectarla
            ConexionDB bdd = new ConexionDB(true);
            bdd.conectar();

            //Obtener el resultado de la sentencia
            int status = bdd.executeQuery(sql);

            //Si el status es mayor que 0, entonces la conexión y ejecución 
            //fue exitosa
            if (status > 0) {

                //Mensaje de éxito
                Mensaje.msjInformativo("El proveedor ha sido eliminado con éxito.");

                return true;

                //Comprobar si se encontró el cliente y se aplicaron los cambios
            } else {
                //Mensaje de error por falta de permisos
                Mensaje.msjError("No se encontró el proveedor seleccionado y no se "
                        + "eliminó.\nPor favor, actualice los "
                        + "registro y verifique la existencia del proveedor.");
            }

            //Terminar la conexión con la base de datos
            bdd.desconectar();
        }
        return false;
    }

    /**
     * Función para intentar eliminar un usuario seleccionado en la base de
     * datos
     *
     * @param id ID del usuario a eliminar
     * @return
     */
    public static boolean removeUsuario(Object id) {
        //Preparar la sentencia SQL para actualizar el cliente
        String sql = "DELETE FROM Usuario WHERE id = " + id;

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        int status = bdd.executeQuery(sql);

        //Si el status es mayor que 0, entonces la conexión y ejecución 
        //fue exitosa
        if (status > 0) {

            //Mensaje de éxito
            Mensaje.msjInformativo("El usuario ha sido eliminado con éxito.");

            return true;

            //Comprobar si se encontró el cliente y se aplicaron los cambios
        } else {
            //Mensaje de error por falta de permisos
            Mensaje.msjError("No se encontró el usuario seleccionado y no se "
                    + "eliminó.\nPor favor, actualice los "
                    + "registro y verifique la existencia del usuario.");
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        return false;
    }

    /**
     * Función para intentar eliminar un usuario seleccionado en la base de
     * datos
     *
     * @param id ID del usuario a eliminar
     * @return
     */
    public static boolean removeEmpleado(Object id) {
        //Preparar la sentencia SQL para actualizar el cliente
        String sql = "DELETE FROM Empleado WHERE id = " + id;

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        int status = bdd.executeQuery(sql);

        //Si el status es mayor que 0, entonces la conexión y ejecución 
        //fue exitosa
        if (status > 0) {

            //Mensaje de éxito
            Mensaje.msjInformativo("El empleado ha sido eliminado con éxito.");

            return true;

            //Comprobar si se encontró el cliente y se aplicaron los cambios
        } else {
            //Mensaje de error por falta de permisos
            Mensaje.msjError("No se encontró el empleado seleccionado y no se "
                    + "eliminó.\nPor favor, actualice los "
                    + "registro y verifique la existencia del empleado.");
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        return false;
    }
}
