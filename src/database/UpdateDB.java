package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import properties.Mensaje;

/**
 *
 * @author diego
 */
public class UpdateDB implements properties.Constantes {

    /**
     * Función para editar los datos de un cliente registrado
     *
     * @param id
     * @param cedula
     * @param nombre
     * @param apellido
     * @param telefono
     * @return
     */
    public static boolean updateCliente(int id, int cedula, String nombre, String apellido, String telefono) {
        //Obtener el rol del usuario actual
        int rol = ReadDB.getUserRol(main.Frame.getUserIdentified());
        //Validar que el usuario que realiza la acción, cuente con los permisos
        //o si se está creando un usuario desde el login
        if (rol == EMPLEADO || rol == ADMINISTRADOR || rol == OPERADOR) {

            //Preparar la sentencia SQL para actualizar el usuario
            String sql = "SELECT EDITAR_CLIENTE(" + id + ", " + cedula + ", \""
                    + nombre + "\", \"" + apellido + "\", \"" + telefono + "\", "
                    + rol + ")";

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

                        //Determinar si el mensaje de error fue por una entrada duplicada
                    } else if (msj.toUpperCase().contains("DUPLICATE ENTRY")) {
                        //Comprobar si el error pudo ser por la cédula u otro
                        if (msj.toUpperCase().contains("CEDULA")) {
                            Mensaje.msjError("La cédula ingresada ya se encuentra registrada.");

                        } else {
                            Mensaje.msjError(msj);
                        }

                    } else {
                        //En caso de no ser ninguno de los anteriores, mostrar
                        //directamente el mensaje arrojado.
                        Mensaje.msjError(msj);
                    }

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    return false;
                }
            } catch (NumberFormatException | SQLException e) {
                Mensaje.msjError("No se pudo actualizar el cliente.\nError: " + e);
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
     * Función para editar los datos de un proveedor registrado
     *
     * @param id
     * @param rif
     * @param nombre
     * @param telefono
     * @return
     */
    public static boolean updateProveedor(int id, String rif, String nombre, String telefono) {
        //Obtener el rol del usuario actual
        int rol = ReadDB.getUserRol(main.Frame.getUserIdentified());
        //Validar que el usuario que realiza la acción, cuente con los permisos
        //o si se está creando un usuario desde el login
        if (rol == EMPLEADO || rol == ADMINISTRADOR || rol == OPERADOR) {
            //Preparar la sentencia SQL para actualizar el proveedor
            String sql = "UPDATE `Proveedores` SET "
                    + "id = " + id + ", "
                    + "rif = \"" + rif + "\", "
                    + "nombre = \"" + nombre + "\", "
                    + "telefono = \"" + telefono + "\" "
                    + "WHERE id = " + id;

            //Instanciar una conexión con la base de datos y conectarla
            ConexionDB bdd = new ConexionDB(true);
            bdd.conectar();

            //Obtener el resultado de la sentencia
            int status = bdd.executeQuery(sql);

            //Terminar la conexión con la base de datos
            bdd.desconectar();

            //Si el status es mayor que 0, entonces la conexión y ejecución 
            //fue exitosa
            if (status > 0) {

                return true;

                //Comprobar si se encontró el cliente y se aplicaron los cambios
            } else if (status == 0) {
                //Mensaje de error por falta de permisos
                Mensaje.msjError("No se encontró el proveedor por su id y no se "
                        + "aplicaron los cambios.\nPor favor, actualice los "
                        + "registro y verifique la existencia del proveedor.");

                //Comprobar si fue un error de duplicación
            } else if (status == DUPLICATE_ERROR) {
                Mensaje.msjError("El rif ingresado ya se encuentra registrado"
                        + " en el sistema.\nPor favor, verifique los datos.");
            }

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
     * Función para editar los datos de un usuario mediante la administración
     *
     * @param id_usuario
     * @param id_cliente
     * @param cedula
     * @param nombre
     * @param apellido
     * @param telefono
     * @param correo
     * @return
     */
    public static boolean updateUsuario(int id_usuario, int id_cliente, int cedula, String nombre, String apellido, String telefono, String correo) {
        //Obtener el rol del usuario actual
        int rol = ReadDB.getUserRol(main.Frame.getUserIdentified());
        
        //Comprobar que el rol sea válido
        if (rol > 0) {
            //Preparar la sentencia SQL para actualizar el usuario
            String sql = "SELECT EDITAR_USUARIO(" + id_usuario + ", " + id_cliente + ", "
                    + "" + cedula + ", \"" + nombre + "\", \"" + apellido + "\", \""
                    + telefono + "\", \"" + correo + "\", " + rol + ")";

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

                        //Determinar si el mensaje de error fue por una entrada duplicada
                    } else if (msj.toUpperCase().contains("DUPLICATE ENTRY")) {
                        //Comprobar si el error pudo ser por correo, cédula u otro
                        if (msj.toUpperCase().contains("CORREO")) {
                            Mensaje.msjError("El correo ingresado ya se encuentra registrado.");

                        } else if (msj.toUpperCase().contains("CEDULA")) {
                            Mensaje.msjError("La cédula ingresada ya se encuentra registrada.");

                        } else {
                            Mensaje.msjError(msj);
                        }

                    } else {
                        //En caso de no ser ninguno de los anteriores, mostrar
                        //directamente el mensaje arrojado.
                        Mensaje.msjError(msj);
                    }

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    return false;
                }
            } catch (NumberFormatException | SQLException e) {
                Mensaje.msjError("No se pudo actualizar el usuario.\nError: " + e);
            }

            //Terminar la conexión con la base de datos
            bdd.desconectar();
            
        }else {
            //Mensaje de error por falta de permisos
            Mensaje.msjError("Su usuario no cuenta con los permisos para "
                    + "realizar esta acción.\nPor seguridad, el "
                    + "programa se cerrará.");
            //Cerrar el programa
            main.Run.cerrarPrograma();

            //Terminar de ejecutar el programa
            System.exit(0);
        }

        //En caso de NO obtener ningún dato, retornar el número de error
        return false;
    }

    /**
     * Función para editar la contraseña de un usuario, desde el login
     *
     * @param correo
     * @param claveNueva
     * @return
     */
    public static boolean updateUsuarioClave(String correo, String claveNueva) {
        //Preparar la sentencia SQL para actualizar la clave
        String sql = "UPDATE Usuario SET contraseña = \"" + claveNueva + "\"";

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        int status = bdd.executeQuery(sql);

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //Si el status es mayor que 0, entonces la conexión y ejecución 
        //fue exitosa
        if (status > 0) {

            return true;

            //Comprobar si se encontró el cliente y se aplicaron los cambios
        } else if (status == 0) {
            //Mensaje de error
            Mensaje.msjError("No se encontró el usuario registrado.\nPor favor"
                    + " actualice y verifique sus datos.");
        }

        return false;
    }

    public static boolean updateEmpleado(int id_empleado, String cargo, int sucursal, int rol) {
        //Preparar la sentencia SQL para actualizar el empleado
        String sql = "UPDATE Empleado SET "
                + "cargo_laboral = \"" + cargo + "\", "
                + "rol = " + rol + ", "
                + "id_sucursal = " + sucursal
                + " WHERE id = " + id_empleado;

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        int status = bdd.executeQuery(sql);

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //Si el status es mayor que 0, entonces la conexión y ejecución 
        //fue exitosa
        if (status > 0) {

            return true;

            //Comprobar si se encontró el cliente y se aplicaron los cambios
        } else if (status == 0) {
            //Mensaje de error 
            Mensaje.msjError("No se encontró el empleado seleccionado.\nPor favor, actualice los "
                    + "registro y verifique la existencia del empleado.");
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return false;
    }
}
