package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import main.Frame;
import main.Run;
import properties.Encript;
import properties.Mensaje;
import static properties.Mensaje.msjError;

public class AdminDB implements properties.Constantes {

    private static int intentos = 0;

    public static boolean exportDB(String fileName, String filePath, boolean copiaSeguridad) {

        return true;
    }

    public static boolean importDB(String filePath) {

        return true;
    }

    /**
     * Función para pedir la contraseña del usuario administrador iniciado
     * sesión
     *
     * @return
     */
    public static boolean validateAdminUser() {

        //Obtener el rol del usuario de la sesión iniciada
        int rol = Frame.getUserRol();

        //Validar que el rol del usuario SEA administrador
        if (rol == ADMINISTRADOR || rol == OPERADOR) {

            //Panel de ingreso de contraseña para el JOptionPane
            JPanel panel = new JPanel();
            JLabel titulo = new JLabel("Ingrese su contraseña");
            JPasswordField clave = new JPasswordField(18);
            JCheckBox mostrar = new JCheckBox("Mostrar contraseña");
            String[] opciones = {"Aceptar", "Cancelar"};

            //Asignar un padding inferior al título
            titulo.setBorder(createEmptyBorder(0, 0, 2, 0));
            //Asignar un padding vertical al checkbox
            mostrar.setBorder(createEmptyBorder(4, 0, 4, 0));

            //Propiedades del campo de clave
            clave.setEchoChar('•');
            //Asignar el layout al panel
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            //Añadir los componentes
            panel.add(titulo);
            panel.add(clave);
            panel.add(mostrar);

            //Listener para cuando se presione el checkbox
            mostrar.addActionListener((e -> {
                if (mostrar.isSelected()) {
                    //Mostrar la clave
                    clave.setEchoChar((char) 0);
                } else {
                    //Ocultar los carácteres por asteriscos
                    clave.setEchoChar('•');
                }
            }));

            //Mostrar el mensaje para el ingreso de contraseña
            int opcion = JOptionPane.showOptionDialog(
                    null,
                    panel,
                    "Ingrese su contraseña",
                    JOptionPane.NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]
            );

            //Validar que se presionó ACEPTAR
            if (opcion == 0) {

                //Encriptar la clave obtenida
                String password = Encript.encriptar(clave.getPassword());

                //Validar que coincida la clave ingresada con el usuario que 
                //tiene la sesión iniciada
                Object[] cuenta = ReadDB.getUser(Frame.getUserIdentified(), password);

                //Validar que se haya obtenido respuesta
                if (cuenta != null) {

                    //Guardar el rol obtenido de la base de datos
                    int rol_2 = (int) (cuenta[0]);

                    //Validar que el usuario obtenido REALMENTE sea administrador
                    if (rol_2 == ADMINISTRADOR || rol_2 == OPERADOR) {
                        intentos = 0;
                        return true;

                    } else {
                        msjError("Su usuario no cuenta con los permisos para "
                                + "realizar esta acción.\nPor seguridad, el "
                                + "programa se cerrará.");
                        //Cerrar el programa
                        Run.cerrarPrograma();

                        //Terminar de ejecutar el programa
                        System.exit(0);
                    }
                } else {
                    //Sumar un intento fallido 
                    intentos++;

                    if (intentos >= 3) {
                        //Mostrar un mensaje de error
                        msjError(
                                "Ha superado el límite de intentos de verificación.\n"
                                + "El programa se cerrará."
                        );

                        //Cerrar el programa
                        Run.cerrarPrograma();

                        //Terminar de ejecutar el programa
                        System.exit(0);
                    }
                }
            }
        } else {
            msjError("Su usuario no cuenta con los permisos para realizar esta "
                    + "acción.\nPor seguridad, el programa se cerrará.");

            //Cerrar el programa
            Run.cerrarPrograma();

            //Terminar de ejecutar el programa
            System.exit(0);
        }
        return false;
    }

    /**
     * Función para obtener la lista de los usuarios registrados en el sistema
     *
     * @return
     */
    public static Object[][] getUsers() {
        //"ID", "Cedula", "Nombre", "Apellido", "Telefono", "Correo"

        //Preparar la sentencia SQL para obtener la cantidad de clientes
        String sql = "SELECT COUNT(*) FROM Usuario";

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {
                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {

                        //Sentencia SQL para obtener todos los clientes
                        sql = "SELECT Usuario.id, cedula, nombre, "
                                + "apellido, telefono, correo "
                                + "FROM Usuario "
                                + "INNER JOIN Cliente "
                                + "ON id_cliente = Cliente.id";
                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object usuarios[][] = new Object[count][6];
                            int i = 0;

                            while (r.next()) {
                                usuarios[i][0] = r.getInt(1);
                                usuarios[i][1] = r.getInt(2);
                                usuarios[i][2] = r.getString(3);
                                usuarios[i][3] = r.getString(4);
                                usuarios[i][4] = r.getString(5);
                                usuarios[i][5] = r.getString(6);

                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return usuarios;
                        }
                    }
                }
                //Desconectar la base de datos
                bdd.desconectar();

                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato o
                //traer la cantidad de 0 registros, por lo tanto, se retornará
                //un objeto vacío, en cualquiera de ambos casos.
                return new Object[][]{};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener los usuarios de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        return null;
    }

    public static Object[][] getEmpleados() {
        //ID, Cedula, Nombre, Apellido, Cargo laboral, Rol, Sucursal

        //Preparar la sentencia SQL para obtener la cantidad de clientes
        int sucursal = Frame.getSucursal();

        String sql;
        int userRol = Frame.getUserRol();
        if (userRol == ADMINISTRADOR) {
            sql = "SELECT COUNT(*) "
                    + "FROM Empleado "
                    + "INNER JOIN Usuario "
                    + "	ON id_usuario = Usuario.id "
                    + "INNER JOIN Cliente "
                    + "	ON id_cliente = Cliente.id "
                    + "INNER JOIN Sucursal "
                    + "	ON id_sucursal = Sucursal.id ";
        } else {
            sql = "SELECT COUNT(*) "
                    + "FROM Empleado "
                    + "INNER JOIN Usuario "
                    + "	ON id_usuario = Usuario.id "
                    + "INNER JOIN Cliente "
                    + "	ON id_cliente = Cliente.id "
                    + "INNER JOIN Sucursal "
                    + "	ON id_sucursal = Sucursal.id "
                    + " AND Sucursal.id = " + sucursal;
        }

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {
                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {

                        //ID, Cedula, Nombre, Apellido, Telefono, Cargo laboral, Rol, Sucursal
                        //Sentencia SQL para obtener todos los clientes
                        if (userRol == ADMINISTRADOR) {
                            sql = "SELECT Empleado.id, cedula, nombre, apellido,"
                                    + " cargo_laboral, rol, descripcion "
                                    + "FROM Empleado "
                                    + "INNER JOIN Usuario "
                                    + "	ON id_usuario = Usuario.id "
                                    + "INNER JOIN Cliente "
                                    + "	ON id_cliente = Cliente.id "
                                    + "INNER JOIN Sucursal "
                                    + "	ON id_sucursal = Sucursal.id ";
                        } else {
                            sql = "SELECT Empleado.id, cedula, nombre, apellido,"
                                    + " cargo_laboral, rol, descripcion "
                                    + "FROM Empleado "
                                    + "INNER JOIN Usuario "
                                    + "	ON id_usuario = Usuario.id "
                                    + "INNER JOIN Cliente "
                                    + "	ON id_cliente = Cliente.id "
                                    + "INNER JOIN Sucursal "
                                    + "	ON id_sucursal = Sucursal.id "
                                    + " AND Sucursal.id = " + sucursal;
                        }
                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object empleados[][] = new Object[count][8];
                            int i = 0;

                            while (r.next()) {
                                empleados[i][0] = r.getInt(1);
                                empleados[i][1] = r.getInt(2);
                                empleados[i][2] = r.getString(3);
                                empleados[i][3] = r.getString(4);
                                empleados[i][4] = r.getString(5);
                                empleados[i][6] = r.getString(7);

                                int rol = r.getInt(6);
                                empleados[i][5] = (rol == ADMINISTRADOR) ? "ADMINISTRADOR"
                                        : (rol == OPERADOR) ? "OPERADOR" : "EMPLEADO";
                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return empleados;
                        }
                    }
                }
                //Desconectar la base de datos
                bdd.desconectar();

                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato o
                //traer la cantidad de 0 registros, por lo tanto, se retornará
                //un objeto vacío, en cualquiera de ambos casos.
                return new Object[][]{};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener los usuarios de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        return null;
    }

    public static int getEmpleadoID(int cedula) {
        //Preparar la sentencia SQL para obtener el id del cliente
        String sql = "SELECT Empleado.id "
                + "FROM Empleado "
                + "INNER JOIN Usuario "
                + "	ON id_usuario = Usuario.id "
                + "INNER JOIN Cliente "
                + "     ON id_cliente = Cliente.id "
                + "     AND cedula = " + cedula;

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
                    int id = r.getInt(1);

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    //Retornar el precio
                    return id;
                }
                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato, en ese
                //caso, se retornará el id como -1
                return -1;
            }
        } catch (NumberFormatException | SQLException e) {
            Mensaje.msjError("No se pudo obtener el id del empleado.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_VALUE;
    }

    public static int getEmpleadoRol(int cedula) {
        //Preparar la sentencia SQL para obtener el id del cliente
        String sql = "SELECT rol "
                + "FROM Empleado "
                + "INNER JOIN Usuario "
                + "	ON id_usuario = Usuario.id "
                + "INNER JOIN Cliente "
                + "     ON id_cliente = Cliente.id "
                + "     AND cedula = " + cedula;

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
                    int id = r.getInt(1);

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    //Retornar el precio
                    return id;
                }
                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato, en ese
                //caso, se retornará el id como -1
                return -1;
            }
        } catch (NumberFormatException | SQLException e) {
            Mensaje.msjError("No se pudo obtener el rol del empleado.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_VALUE;
    }

    public static Object[] getAjustes() {
        Object[] ajustes = {};

        return ajustes;
    }

    public static Object[] getFranquicia() {
        //Preparar la sentencia SQL para obtener el id del cliente
        String sql = "SELECT nombre, rif, nit FROM Franquicia WHERE id = 1";

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
                    //Obtener la información
                    String nombre = r.getString(1);
                    String rif = r.getString(2);
                    int nit = r.getInt(3);

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    //Retornar el precio
                    return new Object[]{nombre, rif, nit};
                }
                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato, en ese
                //caso, se retornará el id como -1
                return new Object[]{};
            }
        } catch (NumberFormatException | SQLException e) {
            Mensaje.msjError("No se pudo obtener la información de la franquicia.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return null;
    }

    public static boolean updateFranquicia(String nombre, String rif, int nit) {

        //Preparar la sentencia SQL para actualizar el usuario
        String sql = "UPDATE `Franquicia` "
                + "SET `nombre`='" + nombre + "', "
                + "     `rif`='" + rif + "', "
                + "     `nit`='" + nit + "' "
                + "WHERE `id` = 1";

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

            Mensaje.msjInformativo("Se aplicaron los cambios a la franquicia con éxito.");
            
            return true;

            //Comprobar si se encontró el cliente y se aplicaron los cambios
        } else {
            //Mensaje de error por falta de permisos
            Mensaje.msjError("No se pudo realizar los cambios a la franquicia.");
            
        } 

        return false;
    }
    
    // ========== REPORTES ==========
    /**
     * Función para obtener el registro de los trasvasos, en una fecha
     * determninada
     *
     * @param initDate Fecha inicial
     * @param finalDate Fecha final
     * @param sucursal
     *
     * @return Registro de los trasvasos
     */
    public static Object[][] getTrasvasos(String initDate, String finalDate, int sucursal) {
        //#, ID, Cedula, Pago, Entr, TipoPago, Delivery, Monto, Fecha, Sucursal*

        //Preparar la sentencia SQL para obtener la cantidad de trasvasos
        String sql;
        //Validar si se van a mostrar todas las sucursales o si será filtrado
        if (sucursal == TODAS_SUCURSALES) {
            sql = "SELECT COUNT(*) FROM Trasvaso "
                    + "INNER JOIN Cliente "
                    + "     ON id_cliente = Cliente.id "
                    + "INNER JOIN Almacen "
                    + "     ON id_almacen = Almacen.id "
                    + "INNER JOIN Sucursal "
                    + "     ON id_sucursal = Sucursal.id "
                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                    + "     AND fecha <= '" + finalDate + " 23:59:59' "
                    + "ORDER BY Trasvaso.id DESC";

        } else {
            sql = "SELECT COUNT(*) FROM Trasvaso "
                    + "INNER JOIN Cliente "
                    + "     ON id_cliente = Cliente.id "
                    + "INNER JOIN Almacen "
                    + "     ON id_almacen = Almacen.id "
                    + "INNER JOIN Sucursal "
                    + "     ON id_sucursal = Sucursal.id "
                    + "     AND Sucursal.id = " + sucursal + " "
                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                    + "     AND fecha <= '" + finalDate + " 23:59:59' "
                    + "ORDER BY Trasvaso.id DESC";
        }

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {

                //Header para los trasvasos
                Object[] header = {"#", "ID", "Cedula", "Pagados", "Entregados",
                    "Tipo Pago", "Delivery", "Monto", "Fecha", "Sucursal"};

                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Sentencia SQL para obtener los trasvasos
                        if (sucursal == TODAS_SUCURSALES) {
                            sql = "SELECT Trasvaso.id, cedula, cant_pagada, "
                                    + "cant_entregada, tipo_pago, delivery, "
                                    + "monto, fecha, descripcion "
                                    + "FROM Trasvaso "
                                    + "INNER JOIN Cliente "
                                    + "     ON id_cliente = Cliente.id "
                                    + "INNER JOIN Almacen "
                                    + "     ON id_almacen = Almacen.id "
                                    + "INNER JOIN Sucursal "
                                    + "     ON id_sucursal = Sucursal.id "
                                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                                    + "AND fecha <= '" + finalDate + " 23:59:59' "
                                    + "ORDER BY Trasvaso.id DESC";

                        } else {
                            sql = "SELECT Trasvaso.id, cedula, cant_pagada, "
                                    + "cant_entregada, tipo_pago, delivery, "
                                    + "monto, fecha, descripcion "
                                    + "FROM Trasvaso "
                                    + "INNER JOIN Cliente "
                                    + "     ON id_cliente = Cliente.id "
                                    + "INNER JOIN Almacen "
                                    + "     ON id_almacen = Almacen.id "
                                    + "INNER JOIN Sucursal "
                                    + "     ON id_sucursal = Sucursal.id "
                                    + "     AND Sucursal.id = " + sucursal + " "
                                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                                    + "AND fecha <= '" + finalDate + " 23:59:59' "
                                    + "ORDER BY Trasvaso.id DESC";
                        }

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            //Instanciar la lista para los trasvasos
                            Object trasvasos[][] = new Object[count + 1][header.length];

                            //Header de la lista
                            trasvasos[0] = header;

                            int i = 1;
                            while (r.next()) {
                                //#, ID, Cedula, Pago, Entr, TipoPago, Delivery, Monto, Fecha
                                trasvasos[i][0] = i;
                                trasvasos[i][1] = r.getInt(1);
                                trasvasos[i][2] = r.getInt(2);
                                trasvasos[i][3] = r.getInt(3);
                                trasvasos[i][4] = r.getInt(4);
                                trasvasos[i][5] = r.getString(5);
                                trasvasos[i][6] = (r.getBoolean(6)) ? "SÍ" : "NO";
                                trasvasos[i][7] = r.getDouble(7);
                                trasvasos[i][8] = r.getString(8);
                                trasvasos[i][9] = r.getString(9);
                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return trasvasos;
                        }
                    }
                }
                //Desconectar la base de datos
                bdd.desconectar();

                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato; en ese
                //caso, se retornará una lista vacía, únicamente con el header
                return new Object[][]{header};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener los trasvasos de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        //Retornar un dato nulo
        return null;
    }

    /**
     * Función para obtener el registro de las recargas, en una fecha
     * determninada
     *
     * @param initDate Fecha inicial
     * @param finalDate Fecha final
     * @param sucursal
     *
     * @return Registro de las recargas
     */
    public static Object[][] getRecargas(String initDate, String finalDate, int sucursal) {
        //#, ID, RIF, Proveedor, Cantidad, Monto Total, Fecha

        //Preparar la sentencia SQL para obtener la cantidad de trasvasos
        String sql;
        if (sucursal == TODAS_SUCURSALES) {
            sql = "SELECT COUNT(*) FROM Recarga "
                    + "INNER JOIN Proveedores "
                    + "	ON id_prov = Proveedores.id "
                    + "INNER JOIN Almacen "
                    + "	ON id_almacen = Almacen.id "
                    + "INNER JOIN Sucursal "
                    + "	ON id_sucursal = Sucursal.id "
                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                    + "	AND fecha <= '" + finalDate + " 23:59:59' "
                    + "ORDER BY Recarga.id DESC";

        } else {
            sql = "SELECT COUNT(*) FROM Recarga "
                    + "INNER JOIN Proveedores "
                    + "	ON id_prov = Proveedores.id "
                    + "INNER JOIN Almacen "
                    + "	ON id_almacen = Almacen.id "
                    + "INNER JOIN Sucursal "
                    + "	ON id_sucursal = Sucursal.id "
                    + "    AND Sucursal.id = " + sucursal + " "
                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                    + "	AND fecha <= '" + finalDate + " 23:59:59' "
                    + "ORDER BY Recarga.id DESC";
        }

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {

                //Header para las recargas
                Object[] header = {"#", "ID", "RIF", "Proveedor",
                    "Cantidad", "Monto Total", "Fecha", "Sucursal"};

                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Sentencia SQL para obtener los trasvasos
                        if (sucursal == TODAS_SUCURSALES) {
                            sql = "SELECT Recarga.id, rif, nombre, cantidad, "
                                    + "monto, fecha, descripcion "
                                    + "FROM Recarga "
                                    + "INNER JOIN Proveedores "
                                    + "	ON id_prov = Proveedores.id "
                                    + "INNER JOIN Almacen "
                                    + "	ON id_almacen = Almacen.id "
                                    + "INNER JOIN Sucursal "
                                    + "	ON id_sucursal = Sucursal.id "
                                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                                    + "	AND fecha <= '" + finalDate + " 23:59:59' "
                                    + "ORDER BY Recarga.id DESC";

                        } else {
                            sql = "SELECT Recarga.id, rif, nombre, cantidad, "
                                    + "monto, fecha, descripcion "
                                    + "FROM Recarga "
                                    + "INNER JOIN Proveedores "
                                    + "	ON id_prov = Proveedores.id "
                                    + "INNER JOIN Almacen "
                                    + "	ON id_almacen = Almacen.id "
                                    + "INNER JOIN Sucursal "
                                    + "	ON id_sucursal = Sucursal.id "
                                    + "    AND Sucursal.id = " + sucursal + " "
                                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                                    + "	AND fecha <= '" + finalDate + " 23:59:59' "
                                    + "ORDER BY Recarga.id DESC";

                        }

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            //Instanciar la lista para las compras
                            Object recargas[][] = new Object[count + 1][header.length];
                            //Header de la lista
                            recargas[0] = header;

                            int i = 1;
                            while (r.next()) {
                                //#, ID, RIF, Proveedor, Cantidad, Monto Total, Fecha
                                recargas[i][0] = i;
                                recargas[i][1] = r.getInt(1);
                                recargas[i][2] = r.getString(2);
                                recargas[i][3] = r.getString(3);
                                recargas[i][4] = r.getInt(4);
                                recargas[i][5] = r.getDouble(5);
                                recargas[i][6] = r.getString(6);
                                recargas[i][7] = r.getString(7);
                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return recargas;
                        }
                    }
                }
                //Desconectar la base de datos
                bdd.desconectar();

                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato; en ese
                //caso, se retornará una lista vacía, únicamente con el header
                return new Object[][]{header};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener las ventas de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        //Retornar un dato nulo
        return null;
    }

    /**
     * Función para obtener el registro de las ventas, en una fecha determninada
     *
     * @param initDate Fecha inicial
     * @param finalDate Fecha final
     * @param sucursal
     *
     * @return Registro de las ventas
     */
    public static Object[][] getVentas(String initDate, String finalDate, int sucursal) {
        //#, ID, Cedula, Cantidad, Tipo pago, Monto Total, Fecha

        //Preparar la sentencia SQL para obtener la cantidad de trasvasos
        String sql;
        if (sucursal == TODAS_SUCURSALES) {
            sql = "SELECT COUNT(*) FROM Venta "
                    + "INNER JOIN Cliente "
                    + "	ON id_cliente = Cliente.id "
                    + "INNER JOIN Almacen "
                    + "	ON id_almacen = Almacen.id "
                    + "INNER JOIN Sucursal "
                    + "	ON id_sucursal = Sucursal.id "
                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                    + "     AND fecha <= '" + finalDate + " 23:59:59' "
                    + "ORDER BY Venta.id DESC";

        } else {
            sql = "SELECT COUNT(*) FROM Venta "
                    + "INNER JOIN Cliente "
                    + "	ON id_cliente = Cliente.id "
                    + "INNER JOIN Almacen "
                    + "	ON id_almacen = Almacen.id "
                    + "INNER JOIN Sucursal "
                    + "	ON id_sucursal = Sucursal.id "
                    + "     AND Sucursal.id = " + sucursal + " "
                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                    + "     AND fecha <= '" + finalDate + " 23:59:59' "
                    + "ORDER BY Venta.id DESC";
        }

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {

                //Header para las ventas
                Object[] header = {"#", "ID", "Cedula", "Cantidad",
                    "Tipo Pago", "Delivery", "Monto", "Fecha", "Sucursal"};

                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Sentencia SQL para obtener los trasvasos
                        if (sucursal == TODAS_SUCURSALES) {
                            sql = "SELECT Venta.id, cedula, cantidad, tipo_pago,"
                                    + " delivery, monto, fecha, descripcion "
                                    + "FROM Venta "
                                    + "INNER JOIN Cliente "
                                    + "	ON id_cliente = Cliente.id "
                                    + "INNER JOIN Almacen "
                                    + "	ON id_almacen = Almacen.id "
                                    + "INNER JOIN Sucursal "
                                    + "	ON id_sucursal = Sucursal.id "
                                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                                    + "AND fecha <= '" + finalDate + " 23:59:59' "
                                    + "ORDER BY Venta.id DESC";

                        } else {
                            sql = "SELECT Venta.id, cedula, cantidad, tipo_pago,"
                                    + " delivery, monto, fecha, descripcion "
                                    + "FROM Venta "
                                    + "INNER JOIN Cliente "
                                    + "	ON id_cliente = Cliente.id "
                                    + "INNER JOIN Almacen "
                                    + "	ON id_almacen = Almacen.id "
                                    + "INNER JOIN Sucursal "
                                    + "	ON id_sucursal = Sucursal.id "
                                    + "     AND Sucursal.id = " + sucursal + " "
                                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                                    + "AND fecha <= '" + finalDate + " 23:59:59' "
                                    + "ORDER BY Venta.id DESC";
                        }

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            //Instanciar la lista para las ventas
                            Object ventas[][] = new Object[count + 1][header.length];
                            //Header de la lista
                            ventas[0] = header;

                            int i = 1;
                            while (r.next()) {
                                //#, ID, Cedula, Cantidad, Tipo pago, Delivery, Monto Total, Fecha
                                ventas[i][0] = i;
                                ventas[i][1] = r.getInt(1);
                                ventas[i][2] = r.getInt(2);
                                ventas[i][3] = r.getInt(3);
                                ventas[i][4] = r.getString(4);
                                ventas[i][5] = (r.getBoolean(5)) ? "SÍ" : "NO";
                                ventas[i][6] = r.getDouble(6);
                                ventas[i][7] = r.getString(7);
                                ventas[i][8] = r.getString(8);
                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return ventas;
                        }
                    }
                }
                //Desconectar la base de datos
                bdd.desconectar();

                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato; en ese
                //caso, se retornará una lista vacía, únicamente con el header
                return new Object[][]{header};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener las ventas de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        //Retornar un dato nulo
        return null;
    }

    /**
     * Función para obtener el registro de las compras, en una fecha
     * determninada
     *
     * @param initDate Fecha inicial
     * @param finalDate Fecha final
     * @param sucursal
     *
     * @return Registro de las compras
     */
    public static Object[][] getCompras(String initDate, String finalDate, int sucursal) {
        //#, ID, RIF, Proveedor, Cantidad, Monto Total, Fecha

        //Preparar la sentencia SQL para obtener la cantidad de trasvasos
        String sql;
        if (sucursal == TODAS_SUCURSALES) {
            sql = "SELECT COUNT(*) FROM Compra "
                    + "INNER JOIN Proveedores "
                    + "	ON id_prov = Proveedores.id "
                    + "INNER JOIN Almacen "
                    + "	ON id_almacen = Almacen.id "
                    + "INNER JOIN Sucursal "
                    + "	ON id_sucursal = Sucursal.id "
                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                    + "	AND fecha <= '" + finalDate + " 23:59:59' "
                    + "ORDER BY Compra.id DESC";

        } else {
            sql = "SELECT COUNT(*) FROM Compra "
                    + "INNER JOIN Proveedores "
                    + "	ON id_prov = Proveedores.id "
                    + "INNER JOIN Almacen "
                    + "	ON id_almacen = Almacen.id "
                    + "INNER JOIN Sucursal "
                    + "	ON id_sucursal = Sucursal.id "
                    + "    AND Sucursal.id = " + sucursal + " "
                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                    + "	AND fecha <= '" + finalDate + " 23:59:59' "
                    + "ORDER BY Compra.id DESC";
        }

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {

                //Header para las recargas
                Object[] header = {"#", "ID", "RIF", "Proveedor",
                    "Cantidad", "Monto Total", "Fecha", "Sucursal"};

                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Sentencia SQL para obtener los trasvasos
                        if (sucursal == TODAS_SUCURSALES) {
                            sql = "SELECT Compra.id, rif, nombre, cantidad, "
                                    + "monto, fecha, descripcion "
                                    + "FROM Compra "
                                    + "INNER JOIN Proveedores "
                                    + "	ON id_prov = Proveedores.id "
                                    + "INNER JOIN Almacen "
                                    + "	ON id_almacen = Almacen.id "
                                    + "INNER JOIN Sucursal "
                                    + "	ON id_sucursal = Sucursal.id "
                                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                                    + "	AND fecha <= '" + finalDate + " 23:59:59' "
                                    + "ORDER BY Compra.id DESC";

                        } else {
                            sql = "SELECT Compra.id, rif, nombre, cantidad, "
                                    + "monto, fecha, descripcion "
                                    + "FROM Compra "
                                    + "INNER JOIN Proveedores "
                                    + "	ON id_prov = Proveedores.id "
                                    + "INNER JOIN Almacen "
                                    + "	ON id_almacen = Almacen.id "
                                    + "INNER JOIN Sucursal "
                                    + "	ON id_sucursal = Sucursal.id "
                                    + "    AND Sucursal.id = " + sucursal + " "
                                    + "WHERE fecha >= '" + initDate + " 00:00:00' "
                                    + "	AND fecha <= '" + finalDate + " 23:59:59' "
                                    + "ORDER BY Compra.id DESC";

                        }

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            //Instanciar la lista para las compras
                            Object compras[][] = new Object[count + 1][header.length];
                            //Header de la lista
                            compras[0] = header;

                            int i = 1;
                            while (r.next()) {
                                //#, ID, RIF, Proveedor, Cantidad, Monto Total, Fecha
                                compras[i][0] = i;
                                compras[i][1] = r.getInt(1);
                                compras[i][2] = r.getString(2);
                                compras[i][3] = r.getString(3);
                                compras[i][4] = r.getInt(4);
                                compras[i][5] = r.getDouble(5);
                                compras[i][6] = r.getString(6);
                                compras[i][7] = r.getString(7);
                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return compras;
                        }
                    }
                }
                //Desconectar la base de datos
                bdd.desconectar();

                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato; en ese
                //caso, se retornará una lista vacía, únicamente con el header
                return new Object[][]{header};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener las ventas de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        //Retornar un dato nulo
        return null;
    }

    public static Object[][] getEmpleados(int sucursal) {
        //ID, Cedula, Nombre, Apellido, Cargo laboral, Sucursal

        //Preparar la sentencia SQL para obtener la cantidad de trasvasos
        String sql;
        //Validar si se van a mostrar todas las sucursales o si será filtrado
        if (sucursal == TODAS_SUCURSALES) {
            sql = "SELECT COUNT(*) FROM Empleado "
                    + "INNER JOIN Usuario "
                    + "	ON id_usuario = Usuario.id "
                    + "INNER JOIN Cliente "
                    + "	ON id_cliente = Cliente.id "
                    + "INNER JOIN Sucursal "
                    + "	ON id_sucursal = Sucursal.id ";

        } else {
            sql = "SELECT COUNT(*) FROM Empleado "
                    + "INNER JOIN Usuario "
                    + "	ON id_usuario = Usuario.id "
                    + "INNER JOIN Cliente "
                    + "	ON id_cliente = Cliente.id "
                    + "INNER JOIN Sucursal "
                    + "	ON id_sucursal = Sucursal.id "
                    + " AND Sucursal.id = " + sucursal;
        }

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {
                //Header para los trasvasos
                Object[] header = {"ID", "Cedula", "Nombre", "Apellido",
                    "Cargo Laboral", "Sucursal"};

                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Sentencia SQL para obtener los trasvasos
                        if (sucursal == TODAS_SUCURSALES) {
                            sql = "SELECT Empleado.id, cedula, nombre, "
                                    + "	apellido, cargo_laboral, descripcion "
                                    + "FROM Empleado "
                                    + "INNER JOIN Usuario "
                                    + "	ON id_usuario = Usuario.id "
                                    + "INNER JOIN Cliente "
                                    + "	ON id_cliente = Cliente.id "
                                    + "INNER JOIN Sucursal "
                                    + "	ON id_sucursal = Sucursal.id ";

                        } else {
                            sql = "SELECT Empleado.id, cedula, nombre, "
                                    + "	apellido, cargo_laboral, descripcion "
                                    + "FROM Empleado "
                                    + "INNER JOIN Usuario "
                                    + "	ON id_usuario = Usuario.id "
                                    + "INNER JOIN Cliente "
                                    + "	ON id_cliente = Cliente.id "
                                    + "INNER JOIN Sucursal "
                                    + "	ON id_sucursal = Sucursal.id "
                                    + "    AND Sucursal.id = " + sucursal;
                        }

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            //Instanciar la lista para los trasvasos
                            Object empleados[][] = new Object[count + 1][header.length];

                            //Header de la lista
                            empleados[0] = header;

                            int i = 1;
                            while (r.next()) {
                                //ID, Cedula, Nombre, Apellido, Cargo laboral, Sucursal
                                empleados[i][0] = r.getInt(1);
                                empleados[i][1] = r.getInt(2);
                                empleados[i][2] = r.getString(3);
                                empleados[i][3] = r.getString(4);
                                empleados[i][4] = r.getString(5);
                                empleados[i][5] = r.getString(6);
                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return empleados;
                        }
                    }
                }
                //Desconectar la base de datos
                bdd.desconectar();

                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato; en ese
                //caso, se retornará una lista vacía, únicamente con el header
                return new Object[][]{header};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener los empleados de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        //Retornar un dato nulo
        return null;
    }

    public static Object[][] getDeudas(String initDate, String finalDate) {
        //ID, Factura, Cedula, Debe Pagar, Debemos Dar, Fecha, Estado

        //Preparar la sentencia SQL para obtener todas las deudas
        String sql = "SELECT COUNT(*) "
                + "FROM `Deuda` "
                + "INNER JOIN `Trasvaso` "
                + "	ON `id_trasvaso` = `Trasvaso`.`id` "
                + "INNER JOIN `Cliente` "
                + "	ON `id_cliente` = `Cliente`.`id` "
                + "WHERE fecha >= '" + initDate + " 00:00:00' AND fecha <= '" + finalDate + " 23:59:59'";

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {

                //Header para las recargas
                Object[] header = {"ID", "Factura", "Cedula",
                    "Debe Pagar", "Debemos Dar", "Fecha", "Estado"};

                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Obtener todos los registros de deudas
                        sql = "SELECT Deuda.id, id_trasvaso, cedula, "
                                + "	pago_pendiente, entrega_pendiente, fecha, "
                                + "	CASE "
                                + "		WHEN (pago_pendiente > 0 "
                                + "			OR entrega_pendiente > 0) "
                                + "			AND Deuda.id IN "
                                + "				(SELECT MAX(Deuda.id) "
                                + "				 FROM Deuda "
                                + "				 INNER JOIN Trasvaso "
                                + "					ON id_trasvaso = Trasvaso.id "
                                + "				 GROUP BY id_cliente) "
                                + "		THEN TRUE "
                                + "		ELSE FALSE "
                                + "	END "
                                + "FROM Deuda "
                                + "INNER JOIN Trasvaso "
                                + "	ON id_trasvaso = Trasvaso.id "
                                + "INNER JOIN Cliente "
                                + "	ON id_cliente = Cliente.id "
                                + "WHERE fecha >= '" + initDate + " 00:00:00' AND fecha <= '" + finalDate + " 23:59:59' "
                                + "ORDER BY Deuda.id DESC";

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            //Instanciar la lista para las compras
                            Object deudas[][] = new Object[count + 1][header.length];
                            //Header de la lista
                            deudas[0] = header;

                            int i = 1;
                            while (r.next()) {
                                //ID, Factura, Cedula, Debe Pagar, Debemos Dar,
                                //Fecha, Estado
                                deudas[i][0] = r.getInt(1);
                                deudas[i][1] = r.getInt(2);
                                deudas[i][2] = r.getInt(3);
                                deudas[i][3] = r.getInt(4);
                                deudas[i][4] = r.getInt(5);
                                deudas[i][5] = r.getString(6);
                                deudas[i][6] = (r.getBoolean(7)) ? "PENDIENTE" : "PAGADA";
                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return deudas;
                        }
                    }
                }
                //Desconectar la base de datos
                bdd.desconectar();

                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato; en ese
                //caso, se retornará una lista vacía, únicamente con el header
                return new Object[][]{header};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener las ventas de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        //Retornar un dato nulo
        return null;
    }

    public static Object[][] getPedidos(String initDate, String finalDate) {
        //ID, cedula, servicio, cantidad, tipo_pago, fecha, direccion, estado

        //Preparar la sentencia SQL para obtener la cantidad de pedidos
        String sql = "SELECT COUNT(*) "
                + "FROM Pedido "
                + "INNER JOIN Usuario "
                + "	ON id_usuario = Usuario.id "
                + "INNER JOIN Cliente "
                + "	ON id_cliente = Cliente.id "
                + "WHERE fecha >= '" + initDate + " 00:00:00' AND fecha <= '" + finalDate + " 23:59:59'";

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {

                //Header para las recargas
                Object[] header = {"ID", "Cedula", "Servicio",
                    "Cantidad", "Tipo Pago", "Fecha", "Direccion", "Estado"};

                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Obtener todos los registros de los pedidos
                        sql = "SELECT Pedido.id, Cliente.cedula, servicio, "
                                + "	cantidad, tipo_pago, fecha, direccion, "
                                + "    CASE "
                                + "        WHEN Pedido.id NOT IN (SELECT id_pedido FROM Pedido_Entregado) "
                                + "        THEN TRUE "
                                + "        ELSE FALSE "
                                + "    END  "
                                + "FROM Pedido "
                                + "INNER JOIN Usuario "
                                + "	ON id_usuario = Usuario.id "
                                + "INNER JOIN Cliente "
                                + "	ON id_cliente = Cliente.id "
                                + "WHERE fecha >= '" + initDate + " 00:00:00' AND fecha <= '" + finalDate + " 23:59:59' "
                                + "ORDER BY Pedido.id DESC";

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            //Instanciar la lista para las compras
                            Object pedidos[][] = new Object[count + 1][header.length];
                            //Header de la lista
                            pedidos[0] = header;

                            int i = 1;
                            while (r.next()) {
                                pedidos[i][0] = r.getInt(1);
                                pedidos[i][1] = r.getInt(2);
                                pedidos[i][2] = (r.getBoolean(3)) ? "COMPRA" : "RECARGA";
                                pedidos[i][3] = r.getInt(4);
                                pedidos[i][4] = r.getString(5);
                                pedidos[i][5] = r.getString(6);
                                pedidos[i][6] = r.getString(7);
                                pedidos[i][7] = (r.getBoolean(8)) ? "PENDIENTE" : "PAGADO";
                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return pedidos;
                        }
                    }
                }
                //Desconectar la base de datos
                bdd.desconectar();

                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato; en ese
                //caso, se retornará una lista vacía, únicamente con el header
                return new Object[][]{header};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener las ventas de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        //Retornar un dato nulo
        return null;
    }

    public static Object[][] getSucursales() {
        //ID, descripción, teléfonos, coordenadas

        //Preparar la sentencia SQL para obtener la cantidad de trasvasos
        String sql = "SELECT COUNT(*) FROM Sucursal;";

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {
                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Sentencia SQL para obtener los trasvasos
                        sql = "SELECT Sucursal.id, descripcion, telefono, "
                                + " cantidad_botellones, coordenadas "
                                + "FROM Sucursal "
                                + "INNER JOIN Almacen "
                                + "	ON id_sucursal = Sucursal.id";

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object sucursales[][] = new Object[count][5];
                            int i = 0;

                            while (r.next()) {
                                sucursales[i][0] = r.getInt(1);
                                sucursales[i][1] = r.getString(2);
                                sucursales[i][2] = r.getString(3);
                                sucursales[i][3] = r.getInt(4);
                                sucursales[i][4] = r.getString(5);
                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return sucursales;
                        }
                    }
                }
                //Desconectar la base de datos
                bdd.desconectar();

                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato o
                //traer la cantidad de 0 registros, por lo tanto, se retornará
                //un objeto vacío, en cualquiera de ambos casos.
                return new Object[][]{};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener las sucursales de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        return null;
    }

    
}
