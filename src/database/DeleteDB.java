package database;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JFileChooser;
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
        String ident = main.Frame.getUserIdentified();
        int rol = ReadDB.getUserRol(ident);
        //Validar que el usuario que realiza la acción, cuente con los permisos
        //o si se está creando un usuario desde el login
        if (rol == EMPLEADO || rol == ADMINISTRADOR || rol == OPERADOR) {

            //Preparar obtener el ID de cliente del usuario actual
            String sql = "SELECT Cliente.id "
                    + "FROM Cliente "
                    + "INNER JOIN Usuario "
                    + "	ON id_cliente = Cliente.id "
                    + "WHERE cedula = '" + ident + "' "
                    + "	OR correo = '" + ident + "'";

            //Instanciar una conexión con la base de datos y conectarla
            ConexionDB bdd = new ConexionDB(true);
            bdd.conectar();

            //Obtener el resultado de la sentencia
            ResultSet r = bdd.selectQuery(sql);

            try {
                //Validar que la respuesta NO sea null
                if (r != null) {
                    //Validar que haya obtenido algún dato
                    if (r.next()) {
                        //Obtener el id
                        int clienteID = r.getInt(1);

                        //Preparar la sentencia SQL para actualizar el usuario
                        sql = "SELECT ELIMINAR_CLIENTE(" + id + ", " + rol + ")";

                        //Obtener el resultado de la sentencia
                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea null
                        if (r != null) {
                            //Avanzar en el resultado
                            r.next();

                            //Obtener el mensaje
                            String msj = r.getString(1);

                            //Comprobar si fue exitoso o no
                            if (msj.toUpperCase().contains("ÉXITO")) {

                                //Comprobar si el usuario se modificó a sí mismo
                                if (id.toString().equals(String.valueOf(clienteID))) {
                                    Mensaje.msjInformativo("Se eliminó el cliente con éxito."
                                            + "\nPor seguridad, el programa se cerrará.");
                                    //Cerrar el programa
                                    main.Run.cerrarPrograma();

                                    //Terminar de ejecutar el programa
                                    System.exit(0);

                                } else {
                                    //Terminar la conexión con la base de datos
                                    bdd.desconectar();

                                    Mensaje.msjInformativo(msj);

                                    //Si no se modificó a sí mismo, retornar true
                                    return true;
                                }

                            } else {
                                //En caso de no ser mensaje de éxito, mostrar como
                                //mensaje de error
                                Mensaje.msjError(msj);
                            }
                        }

                    } else {
                        //Mensaje de error 
                        Mensaje.msjError("Usted no se encuentra registrado como un"
                                + "cliente del sistema.\nPor seguridad, el programa"
                                + "se cerrará.");
                        //Cerrar el programa
                        main.Run.cerrarPrograma();

                        //Terminar de ejecutar el programa
                        System.exit(0);
                    }
                }

            } catch (NumberFormatException | SQLException e) {
                Mensaje.msjError("No se pudo eliminar el cliente.\nError: " + e);
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
            //Obtener el ID del usuario
            int userID = ReadDB.getUserID(main.Frame.getUserIdentified());

            if (userID > 0) {
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

                            //Comprobar si el usuario se modificó a sí mismo
                            if (id.toString().equals(String.valueOf(userID))) {
                                Mensaje.msjInformativo("Se eliminó el usuario con éxito."
                                        + "\nPor seguridad, el programa se cerrará.");
                                //Cerrar el programa
                                main.Run.cerrarPrograma();

                                //Terminar de ejecutar el programa
                                System.exit(0);

                            } else {
                                //Terminar la conexión con la base de datos
                                bdd.desconectar();

                                Mensaje.msjInformativo(msj);

                                //Si no se modificó a sí mismo, retornar true
                                return true;
                            }

                        } else {
                            //En caso de no ser mensaje de éxito, mostrar como
                            //mensaje de error
                            Mensaje.msjError(msj);
                        }

                    }
                } catch (NumberFormatException | SQLException e) {
                    Mensaje.msjError("No se pudo eliminar el usuario.\nError: " + e);
                }

                //Terminar la conexión con la base de datos
                bdd.desconectar();
            } else if (userID == -1) {
                //Mensaje de error 
                Mensaje.msjError("Usted no se encuentra registrado como un"
                        + "usuario del sistema.\nPor seguridad, el programa"
                        + "se cerrará.");
                //Cerrar el programa
                main.Run.cerrarPrograma();

                //Terminar de ejecutar el programa
                System.exit(0);
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
     * Función para intentar eliminar un usuario seleccionado en la base de
     * datos
     *
     * @param id ID del usuario a eliminar
     * @return
     */
    public static boolean removeEmpleado(Object id) {
        int id_user_empleado = AdminDB.getEmpleadoID(main.Frame.getUserIdentified());

        //Comprobar que el ID de empleado del usuario actual sea válido
        if (id_user_empleado > 0) {

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

                //Comprobar si el usuario se modificó a sí mismo
                if (id.toString().equals(String.valueOf(id_user_empleado))) {
                    Mensaje.msjInformativo("Se eliminó el empleado con éxito."
                            + "\nPor seguridad, el programa se cerrará.");
                    //Cerrar el programa
                    main.Run.cerrarPrograma();

                    //Terminar de ejecutar el programa
                    System.exit(0);

                } else {
                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    //Si no se modificó a sí mismo, retornar true
                    return true;
                }

                //Comprobar si se encontró el cliente y se aplicaron los cambios
            } else {
                //Mensaje de error por falta de permisos
                Mensaje.msjError("No se encontró el empleado seleccionado y no se "
                        + "eliminó.\nPor favor, actualice los "
                        + "registro y verifique la existencia del empleado.");
            }

            //Terminar la conexión con la base de datos
            bdd.desconectar();

        } else if (id_user_empleado == -1) {
            //Mensaje de error 
            Mensaje.msjError("Usted no se encuentra registrado como un"
                    + "empleado del sistema.\nPor seguridad, el programa"
                    + "se cerrará.");
            //Cerrar el programa
            main.Run.cerrarPrograma();

            //Terminar de ejecutar el programa
            System.exit(0);
        }

        return false;
    }

    public static boolean removeDeuda(Object id) {

        return false;
    }

    public static boolean removePedido(Object id) {

        return false;
    }

    public static boolean removeSucursal(Object id) {
        //Realizar una exportación de seguridad antes de eliminar una sucursal
        if (AdminDB.exportDB(getSecurityName(), getSecurityFolder())) {

            //Preparar la sentencia SQL para actualizar el cliente
            String sql = "DELETE FROM Sucursal WHERE id = " + id;

            //Instanciar una conexión con la base de datos y conectarla
            ConexionDB bdd = new ConexionDB(true);
            bdd.conectar();

            //Obtener el resultado de la sentencia
            int status = bdd.executeQuery(sql);

            //Si el status es mayor que 0, entonces la conexión y ejecución 
            //fue exitosa
            if (status > 0) {
                int sucursal = main.Frame.getSucursal();
                //Comprobar si el usuario eliminó la sucursal donde se encuentra
                //iniciada la sesión
                if (id.equals(String.valueOf(sucursal))) {
                    Mensaje.msjInformativo("La sucursal se ha eliminado con éxito.\n"
                            + "Por seguridad, el programa se cerrará.");

                    //Cerrar el programa
                    main.Run.cerrarPrograma();

                    //Terminar de ejecutar el programa
                    System.exit(0);

                } else {
                    Mensaje.msjInformativo("La sucursal se ha eliminado con éxito.\n"
                            + "Asegurece de que TODOS los dispositivos que estaban\n"
                            + "conectados a la sucursal, se desconecten del programa.");
                    bdd.desconectar();
                    return true;
                }
            }

            //Terminar la conexión con la base de datos
            bdd.desconectar();

        } else {
            Mensaje.msjError("No se pudo realizar un respaldo de la base de datos"
                    + "antes de eliminar la sucursal.\nNo se realizará ninguna"
                    + "acción hasta que se genere el respaldo de seguridad.");
        }
        return false;
    }

    private static String getSecurityFolder() {

        //Ubicar la carpeta principal "Documents"
        String documentsPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();

        //Nombre de la carpeta "Aquatech" donde se almacenan los archivos 
        //del programa
        String mainPath = "\\AquaTech";

        //Crear un archivo con la ruta de Documentos + la carpeta principal
        File mainFolder = new File(documentsPath + mainPath);

        //Validar que NO exista la carpeta principal AquaTech
        if (!mainFolder.exists()) {

            //Si no existe, crea la carpeta en "/Documents"
            mainFolder.mkdir();
        }

        //Nombre de la carpeta principal donde se almacenan los respaldos
        mainPath += "\\Respaldos";

        //Crear un archivo en la ruta de Documentos + La carpeta de respaldos
        File backupFolder = new File(documentsPath + mainPath);

        //Validar que NO exista la carpeta principal de respaldos
        if (!backupFolder.exists()) {
            //Si no existe, crea la carpeta en "/Documents/AquaTech"
            backupFolder.mkdir();
        }

        //Crear un archivo con la ruta de la carpeta de copias de seguridad
        //antes de realizar una importación
        File securityFolder = new File(documentsPath + mainPath + "\\Respaldo de Seguridad");

        //Validar que NO exista la carpeta para las copias de seguridad
        if (!securityFolder.exists()) {
            //Si no existe, se crea la carpeta para las copias de seguridad
            securityFolder.mkdir();
        }

        //Retornar la ruta de la carpeta de las copias de seguridad
        return securityFolder.getAbsolutePath();
    }

    private static String getSecurityName() {
        //Objeto tipo date para obtener la fecha actual
        Date date = new Date();
        //Objeto de tipo calendario para obtener los datos de la fehca
        Calendar calendario = new GregorianCalendar();
        //Asignar la fecha actual
        calendario.setTime(date);

        //Dividir la información de la fecha
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH);
        int day = calendario.get(Calendar.DAY_OF_MONTH);
        int hour = calendario.get(Calendar.HOUR_OF_DAY);
        int minute = calendario.get(Calendar.MINUTE);
        int second = calendario.get(Calendar.SECOND);

        //Validar que los datos sean de uno o dos dígitos.
        //En caso de ser de un dígito, se le coloca un cero antes del 
        //valor. Ex: 9 -> "09"
        String mes = (month < 10) ? ("0" + month) : String.valueOf(month);
        String dia = (day < 10) ? ("0" + day) : String.valueOf(day);
        String hora = (hour < 10) ? ("0" + hour) : String.valueOf(hour);
        String minuto = (minute < 10) ? ("0" + minute) : String.valueOf(minute);
        String segundo = (second < 10) ? ("0" + second) : String.valueOf(second);

        //Nombre predeterminado de las base de datos
        String name = "AQUATECH_DB_";
        //Fecha cuando se realizó el respaldo
        String fecha = year + "_" + mes + "_" + dia + "_" + hora + "_" + minuto + "_" + segundo + ".sql";

        //Retornar el nombre con la fecha
        return name + fecha;
    }
}
