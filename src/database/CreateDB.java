package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import properties.Mensaje;

/**
 * Clase contenedora de los métodos para intentar insertar datos nuevos a la
 * base de datos y retornar un booleano en caso de que se realicen con o sin
 * éxito
 */
public class CreateDB implements properties.Constantes {

    /**
     * Función para crear un nuevo cliente en la base de datos
     *
     * @param cedula
     * @param nombre
     * @param apellido
     * @param telefono
     * @return
     */
    public static boolean createCliente(int cedula, String nombre, String apellido, String telefono) {
        //Obtener el rol del usuario actual
        int rol = ReadDB.getUserRol(main.Frame.getUserIdentified());

        //Validar que el usuario que realiza la acción, cuente con los permisos
        //o si se está creando un usuario desde el login
        if (rol == EMPLEADO || rol == ADMINISTRADOR || rol == OPERADOR) {
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
     * Función para crear un nuevo proveedor en la base de datos
     *
     * @param rif
     * @param nombre
     * @param telefono
     * @return
     */
    public static boolean createProveedor(String rif, String nombre, String telefono) {
        //Obtener el rol del usuario actual
        int rol = ReadDB.getUserRol(main.Frame.getUserIdentified());

        //Validar que el usuario que realiza la acción, cuente con los permisos
        if (rol == EMPLEADO || rol == ADMINISTRADOR || rol == OPERADOR) {
            //Preparar la sentencia SQL para obtener el trasvaso
            String sql = "INSERT INTO Proveedores"
                    + "     (rif, nombre, telefono)"
                    + "VALUES "
                    + "     (\"" + rif + "\", \"" + nombre + "\", \"" + telefono + "\")";

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
            } else if (status == DUPLICATE_ERROR) {
                //Mensaje de error
                Mensaje.msjError("El RIF ingresado ya se encuentra registrado en"
                        + "el sistema.\nPor favor, verifique sus datos.");
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
     * Función para crear un usuario operativo nuevo en el sistema
     *
     * @param cedula
     * @param nombre
     * @param apellido
     * @param telefono
     * @param correo
     * @param clave
     *
     * @return
     */
    public static boolean createUsuario(int cedula, String nombre, String apellido, String telefono, String correo, String clave) {
        //Preparar la sentencia SQL para crear el usuario
        String sql = "SELECT REGISTRAR_USUARIO(" + cedula + ", \"" + nombre + "\", "
                + "\"" + apellido + "\", \"" + telefono + "\", \"" + correo + "\", "
                + "\"" + clave + "\")";

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
                    //Mostrar mensaje de error
                    Mensaje.msjError(msj);
                }

                //Terminar la conexión con la base de datos
                bdd.desconectar();

                return false;
            }
        } catch (NumberFormatException | SQLException e) {
            Mensaje.msjError("No se pudo crear el usuario.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return false;
    }

    public static boolean createEmpleado(int cedula, String cargo, int sucursal, int rol) {
        //Preparar la sentencia SQL para crear el usuario
        String sql = "SELECT REGISTRAR_EMPLEADO(" + cedula + ", \""
                + cargo + "\", " + sucursal + ", " + rol + ")";

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
                    //Mostrar mensaje de error
                    Mensaje.msjError(msj);
                }

                //Terminar la conexión con la base de datos
                bdd.desconectar();

                return false;
            }
        } catch (NumberFormatException | SQLException e) {
            Mensaje.msjError("No se pudo crear el empleado.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return false;
    }

    /**
     * Función para validar los datos con la base de datos y registrar un
     * trasvaso de botellones
     *
     * @param entregados Cantidad de botellones trasvasados
     * @param pagados Cantidad de botellones pagados por el cliente
     * @param tipoPago Tipo de pago realizado por el cliente
     * @param delivery Si se hizo en delivery o no
     * @param cedula Cédula del cliente
     * @return
     */
    public static boolean createTravaso(int entregados, int pagados, String tipoPago, boolean delivery, String cedula) {
        //Obtener el rol del usuario actual
        int rol = ReadDB.getUserRol(main.Frame.getUserIdentified());

        //Validar que el usuario que realiza la acción, cuente con los permisos
        if (rol == EMPLEADO || rol == ADMINISTRADOR || rol == OPERADOR) {

            int sucursal = main.Frame.getSucursal();
            
            //Preparar la sentencia SQL para registrar el trasvaso
            String sql = "SELECT REGISTRAR_TRASVASO"
                    + "(" + cedula + ", "+sucursal+", " + pagados + ", " + entregados + ", "
                    + "\"" + tipoPago + "\", " + delivery + ")";

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
                        
                        return true;

                    } else {
                        Mensaje.msjError(msj);
                    }

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    //Si el result NO fue null, implica que SÍ se estableció una 
                    //conexión. Sin embargo, pudo no haber traído algún dato, en ese
                    //caso, se retornará el precio como el valor de 0
                    return false;
                }
            } catch (NumberFormatException | SQLException e) {
                Mensaje.msjError("No se pudo registrar el trasvaso.\nError: " + e);
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
     * Función para validar los datos con la base de datos y registrar una venta
     * de botellones
     *
     * @param cantidad Cantidad de botellones vendidos
     * @param tipoPago Tipo de pago realizado por el cliente
     * @param delivery Si se hizo en delivery o no
     * @param cedula Cédula del cliente
     * @return
     */
    public static boolean createVenta(int cantidad, String tipoPago, boolean delivery, String cedula) {
        //Obtener el rol del usuario actual
        int rol = ReadDB.getUserRol(main.Frame.getUserIdentified());

        //Validar que el usuario que realiza la acción, cuente con los permisos
        if (rol == EMPLEADO || rol == ADMINISTRADOR || rol == OPERADOR) {

            int sucursal = main.Frame.getSucursal();
            
            //Preparar la sentencia SQL para registrar el trasvaso
            String sql = "SELECT REGISTRAR_VENTA"
                    + "(" + cedula + ", "+sucursal+", " + cantidad + ", \"" + tipoPago
                    + "\", " + delivery + ")";

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

                        return true;

                    } else {
                        Mensaje.msjError(msj);
                    }

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    //Si el result NO fue null, implica que SÍ se estableció una 
                    //conexión. Sin embargo, pudo no haber traído algún dato, en ese
                    //caso, se retornará el precio como el valor de 0
                    return false;
                }
            } catch (NumberFormatException | SQLException e) {
                Mensaje.msjError("No se pudo registrar la venta.\nError: " + e);
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
     * Función para registrar una compra de botellones en la base de datos
     *
     * @param cantidad Cantidad de botellones comprados
     * @param monto Precio de cada uno de los botellones
     * @param rif RIF del proveedor
     * @return
     */
    public static boolean createRecarga(int cantidad, double monto, String rif) {
        //Obtener el rol del usuario actual
        int rol = ReadDB.getUserRol(main.Frame.getUserIdentified());

        //Validar que el usuario que realiza la acción, cuente con los permisos
        if (rol == EMPLEADO || rol == ADMINISTRADOR || rol == OPERADOR) {

            int sucursal = main.Frame.getSucursal();
            
            //Preparar la sentencia SQL para registrar el trasvaso
            String sql = "SELECT REGISTRAR_RECARGA"
                    + "(\"" + rif + "\", "+sucursal+", " + cantidad + ", " + monto + ")";

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

                        return true;

                    } else {
                        Mensaje.msjError(msj);
                    }

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    //Si el result NO fue null, implica que SÍ se estableció una 
                    //conexión. Sin embargo, pudo no haber traído algún dato, en ese
                    //caso, se retornará el precio como el valor de 0
                    return false;
                }
            } catch (NumberFormatException | SQLException e) {
                Mensaje.msjError("No se pudo registrar la recarga.\nError: " + e);
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
     * Función para registrar una recarga de botellones en la base de datos
     *
     * @param cantidad Cantidad de los botellones recargados
     * @param monto Precio de cada una de las recargas
     * @param rif RIF del proveedor
     * @return
     */
    public static boolean createCompra(int cantidad, double monto, String rif) {
        //Obtener el rol del usuario actual
        int rol = ReadDB.getUserRol(main.Frame.getUserIdentified());

        //Validar que el usuario que realiza la acción, cuente con los permisos
        if (rol == EMPLEADO || rol == ADMINISTRADOR || rol == OPERADOR) {

            int sucursal = main.Frame.getSucursal();
            
            //Preparar la sentencia SQL para registrar el trasvaso
            String sql = "SELECT REGISTRAR_COMPRA"
                    + "(\"" + rif + "\", "+sucursal+", " + cantidad + ", " + monto + ")";

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

                        return true;

                    } else {
                        Mensaje.msjError(msj);
                    }

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    //Si el result NO fue null, implica que SÍ se estableció una 
                    //conexión. Sin embargo, pudo no haber traído algún dato, en ese
                    //caso, se retornará el precio como el valor de 0
                    return false;
                }
            } catch (NumberFormatException | SQLException e) {
                Mensaje.msjError("No se pudo registrar la compra.\nError: " + e);
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
    
}
