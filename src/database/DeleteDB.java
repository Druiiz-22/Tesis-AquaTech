package database;

import java.sql.ResultSet;
import java.sql.SQLException;
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

            //Preparar la sentencia SQL para actualizar el usuario
            String sql = "SELECT ELIMINAR_CLIENTE(" + id + ", " + rol + ")";

            //Instanciar una conexión con la base de datos y conectarla
            ConexionDB bdd = new ConexionDB(true);
            bdd.conectar();

            //Obtener el resultado de la sentencia
            ResultSet r = bdd.selectQuery(sql);

            try {
                //Validar que la respuesta NO sea null
                if (r != null) {
                    //Avanzar en el resultado
                    r.next();

                    //Obtener el mensaje
                    String msj = r.getString(1);

                    //Comprobar si fue exitoso o no
                    if (msj.toUpperCase().contains("ÉXITO")) {
                        //Terminar la conexión con la base de datos
                        bdd.desconectar();

                        //Mensaje de éxito
                        Mensaje.msjInformativo(msj);

                        return true;

                    } else {
                        //En caso de no ser mensaje de éxito, mostrar como
                        //mensaje de error
                        Mensaje.msjError(msj);
                    }

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    return false;
                }
            } catch (NumberFormatException | SQLException e) {
                Mensaje.msjError("No se pudo eliminar el usuario.\nError: " + e);
            }

            //Terminar la conexión con la base de datos
            bdd.desconectar();
            
        } else {
            //Mensaje de error por falta de permisos
            Mensaje.msjError("Su usuario no cuenta con los permisos para "
                    + "realizar esta acción.\nPor seguridad, el "
                    + "programa se cerrará.");
            //Cerrar el programa
            main.Run.cerrarPrograma();

            //Terminar de ejecutar el programa
            System.exit(0);
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
        //Obtener el rol del usuario actual
        int rol = ReadDB.getUserRol(main.Frame.getUserIdentified());
        //Comprobar que el rol sea válido
        if (rol > 0) {
            //Preparar la sentencia SQL para actualizar el usuario
            String sql = "SELECT ELIMINAR_USUARIO(" + id + ", " + rol + ")";

            //Instanciar una conexión con la base de datos y conectarla
            ConexionDB bdd = new ConexionDB(true);
            bdd.conectar();

            //Obtener el resultado de la sentencia
            ResultSet r = bdd.selectQuery(sql);

            try {
                //Validar que la respuesta NO sea null
                if (r != null) {
                    //Avanzar en el resultado
                    r.next();

                    //Obtener el mensaje
                    String msj = r.getString(1);

                    //Comprobar si fue exitoso o no
                    if (msj.toUpperCase().contains("ÉXITO")) {
                        //Terminar la conexión con la base de datos
                        bdd.desconectar();

                        //Mensaje de éxito
                        Mensaje.msjInformativo(msj);

                        return true;

                    } else {
                        //En caso de no ser mensaje de éxito, mostrar como
                        //mensaje de error
                        Mensaje.msjError(msj);
                    }

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    return false;
                }
            } catch (NumberFormatException | SQLException e) {
                Mensaje.msjError("No se pudo eliminar el usuario.\nError: " + e);
            }

            //Terminar la conexión con la base de datos
            bdd.desconectar();

        } else {
            //Mensaje de error por falta de permisos
            Mensaje.msjError("Su usuario no cuenta con los permisos para "
                    + "realizar esta acción.\nPor seguridad, el "
                    + "programa se cerrará.");
            //Cerrar el programa
            main.Run.cerrarPrograma();

            //Terminar de ejecutar el programa
            System.exit(0);
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

    public static boolean removeDeuda(Object id) {

        return false;
    }

    public static boolean removePedido(Object id) {

        return false;
    }
    
    public static boolean removeSucursal(Object id){
        
        return false;
    }
}
