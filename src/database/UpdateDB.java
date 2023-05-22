package database;

import properties.Mensaje;

/**
 *
 * @author diego
 */
public class UpdateDB implements properties.Constantes{

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
        if (rol == EMPLEADO || rol == ADMINISTRADOR || rol == ENCARGADO) {
            //Preparar la sentencia SQL para obtener el trasvaso
            String sql = "INSERT INTO Cliente"
                    + "     (cedula, nombre, apellido, telefono)"
                    + "VALUES "
                    + "     (" + cedula + ", \"" + nombre + "\", \"" + apellido + "\", \"" + telefono + "\")";

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

                //Comprobar si fue un error de duplicación
            } else if (status == DUPLICATE_ERROR) {
                Mensaje.msjError("La cédula ingresada ya se encuentra registrada"
                        + " en el sistema.\nPor favor, verifique sus datos.");
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
     * Función para editar los datos de un proveedor registrado
     *
     * @param id
     * @param rif
     * @param nombre
     * @param telefono
     * @return
     */
    public static boolean updateProveedor(int id, String rif, String nombre, String telefono) {

        return true;
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
     * @param rol
     * @return
     */
    public static boolean updateUsuario(int id_usuario, int id_cliente, int cedula, String nombre, String apellido, String telefono, String correo, int rol) {

        if(updateCliente(id_cliente, cedula, nombre, apellido, telefono)){
            
        }
        
        return true;
    }

    /**
     * Función para editar la contraseña de un usuario, desde el login
     *
     * @param correo
     * @param claveNueva
     * @return
     */
    public static boolean updateUsuarioClave(String correo, String claveNueva) {

        return true;
    }
}
