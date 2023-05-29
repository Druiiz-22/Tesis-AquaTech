package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import properties.Mensaje;

/**
 * Clase contenedora de funciones estáticos para la obtención de datos de la
 * base de datos
 */
public class ReadDB implements properties.Constantes {

    /**
     * Función para obtener el precio actual de los trasvasos
     *
     * @return Precio de los trasvasos
     */
    public static double getPrecioTrasvaso() {
        //Preparar la sentencia SQL para obtener el trasvaso
        String sql = "SELECT trasvaso_bs FROM Precios WHERE id=1";

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
                    //Obtener el precio
                    Double precio = r.getDouble(1);

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    //Retornar el precio
                    return precio;
                }
                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato, en ese
                //caso, se retornará el precio como el valor de 0
                return 0;
            }
        } catch (NumberFormatException | SQLException e) {
            Mensaje.msjError("No se pudo obtener el precio del trasvaso.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_VALUE;
    }

    /**
     * Función para obtener el precio de venta actual de los botellones
     *
     * @return Precio de venta de los botellones
     */
    public static double getPrecioVenta() {
        //Preparar la sentencia SQL para obtener el trasvaso
        String sql = "SELECT venta_bs FROM Precios WHERE id=1";

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea null y que tenga, al menos, un dato
            //obtenido que se pueda avanzar.
            if (r != null) {
                if (r.next()) {

                    //Obtener el precio
                    Double precio = r.getDouble(1);

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    //Retornar el precio
                    return precio;
                }
                //Si el result NO fue null, implica que SÍ se estableció una 
                //conexión. Sin embargo, pudo no haber traído algún dato, en ese
                //caso, se retornará el precio como el valor de 0
                return 0;
            }
        } catch (NumberFormatException | SQLException e) {
            Mensaje.msjError("No se pudo obtener el precio de venta del botellón.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_VALUE;
    }

    /**
     * Función para obtener la cantidad de botellones que tiene el local
     *
     * @return Cantidad de botellones que tiene el local
     */
    public static int getBotellonesAlmacen() {
        return 25;
    }

    /**
     * Función para obtener la cantidad de clientes que deben pagar por
     * trasvasos
     *
     * @return Cantidad de clientes que deben pagar
     */
    public static int getClientesPagar() {
        return 4;
    }

    /**
     * Función para obtener la cantidad de clientes que la empresa les debe
     * recargar botellones
     *
     * @return Cantidad de clientes que se le deben recargar
     */
    public static int getClientesEntregar() {
        return 2;
    }

    /**
     * Función para obtener la cantidad actual de botellones llenos en el local
     *
     * @return Cantidad de botellones llenos
     */
    public static int getBotellonesLlenos() {
        return 20;
    }

    /**
     * Función para obtener la cantidad actual de botellones vacíos en el local
     *
     * @return Cantidad de botellones vacíos
     */
    public static int getBotellonesVacios() {
        return 5;
    }

    /**
     * Función para comprobar la existencia de un correo en la base de datos
     *
     * @param correo Correo que será validado
     *
     * @return
     */
    public static int emailExists(String correo) {
        //Preparar la sentencia SQL para obtener el trasvaso
        String sql = "SELECT COUNT(*) FROM Usuario WHERE correo = \"" + correo + "\"";

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea null y que tenga, al menos, un dato
            //obtenido que se pueda avanzar.
            if (r != null && r.next()) {
                int count = r.getInt(1);

                //Terminar la conexión con la base de datos
                bdd.desconectar();

                return count;
            }
        } catch (NumberFormatException | SQLException e) {
            Mensaje.msjError("No se pudo obtener el precio del trasvaso.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        return ERROR_VALUE;
    }

    /**
     * Función para comprobar la existencia de un usuario con una cédula
     * determinada
     *
     * @param cedula TRUE si existe, al menos, un usuario con la cédula enviada.
     * FALSE si no hay ningún usuario con esa cédula.
     * @return
     */
    public static int cedulaExists(int cedula) {
        //Preparar la sentencia SQL para comprobar la existencia de un usuario
        //con una cédula determinada
        String sql = "SELECT COUNT(*) FROM Usuario "
                + "INNER JOIN Cliente "
                + "ON Cliente.cedula = " + cedula + " "
                + "AND id_cliente = Cliente.id";

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea null y que tenga, al menos, un dato
            //obtenido que se pueda avanzar.
            if (r != null && r.next()) {
                int count = r.getInt(1);

                //Terminar la conexión con la base de datos
                bdd.desconectar();

                //Comprobar si hay, al menos, un usuario con determinada
                //cédula y retornar su resultado
                return count;
            }
        } catch (NumberFormatException | SQLException e) {
            Mensaje.msjError("No se pudo buscar la cédula en la base de datos.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        return ERROR_VALUE;
    }

    /**
     * Función para comprobar la existencia y validez de un usuario
     *
     * @param user
     * @param pass
     * @param sucursal
     * @return
     */
    public static Object[] getUser(String user, String pass, int sucursal) {
        //Preparar la sentencia SQL para obtener el trasvaso
        String sql = "SELECT nombre, apellido, rol "
                + "FROM Empleado "
                + "INNER JOIN Usuario "
                + "	ON id_usuario = Usuario.id "
                + "INNER JOIN Cliente "
                + "	ON id_cliente = Cliente.id "
                + "INNER JOIN Sucursal "
                + "	ON id_sucursal = Sucursal.id "
                + "    AND Sucursal.id = " + sucursal + " "
                + "WHERE (correo = \"" + user + "\" "
                + "   	OR cedula = " + user + ") "
                + "	AND contraseña = \"" + pass + "\";";

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que se haya obtenido una respuesta
            if (r != null) {
                //Validar que se haya obtenido algún dato
                if (r.next()) {
                    //Obtener los datos
                    String nombre = r.getString(1);
                    String apellido = r.getString(2);
                    int rol = r.getInt(3);

                    //Convertir el nombre y apellido en una sola cadena capitalizada
                    String name;
                    name = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
                    name += " ";
                    name += apellido.substring(0, 1).toUpperCase() + apellido.substring(1).toLowerCase();

                    //Terminar la conexión con la base de datos
                    bdd.desconectar();

                    //Retornar el rol y el nombre del usuario
                    return new Object[]{rol, name};
                } else {
                    Mensaje.msjError(
                            "El usuario o contraseña es incorrecto.\n"
                            + "Por favor, verifique sus datos."
                    );
                }
            }
        } catch (NumberFormatException | SQLException e) {
            Mensaje.msjError("No se pudo buscar el usuario en la base de datos.\n"
                    + "Error: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        return null;
    }

    //========== HISTORIAL ==========
    /**
     * Función para obtener todos los clientes registrados en el sistema
     *
     * @return Clientes registrados
     */
    public static Object[][] getClientes() {
        //ID, Cedula, Nombre, Apellido, Telefono

        //Preparar la sentencia SQL para obtener la cantidad de clientes
        String sql = "SELECT COUNT(*) FROM Cliente ORDER BY id DESC";

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
                        sql = "SELECT * FROM Cliente";
                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object clientes[][] = new Object[count][5];
                            int i = 0;

                            while (r.next()) {
                                clientes[i][0] = r.getInt(1);
                                clientes[i][1] = r.getInt(2);
                                clientes[i][2] = r.getString(3);
                                clientes[i][3] = r.getString(4);
                                clientes[i][4] = r.getString(5);
                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return clientes;
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
            Mensaje.msjError("No se pudieron obtener los clientes de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        return null;
    }

    /**
     * Función para obtener todos los proveedores registrados en el sistema
     *
     * @return Proveedores registrados
     */
    public static Object[][] getProveedores() {
        //RIF, Nombre, Telefono, Direccion

        //Preparar la sentencia SQL para obtener la cantidad de proveedores
        String sql = "SELECT COUNT(*) FROM Proveedores;";

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
                    //Obtener la cantidad de proveedores y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {

                        //Sentencia SQL para obtener todos los proveedores
                        sql = "SELECT * FROM Proveedores ORDER BY id DESC";
                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object proveedores[][] = new Object[count][4];
                            int i = 0;

                            while (r.next()) {
                                proveedores[i][0] = r.getInt(1);
                                proveedores[i][1] = r.getString(2);
                                proveedores[i][2] = r.getString(3);
                                proveedores[i][3] = r.getString(4);
                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return proveedores;
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
            Mensaje.msjError("No se pudieron obtener los proveedores de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        return null;
    }

    /**
     * Función para obtener el historial completo de los trasvasos
     *
     * @return Historial de los trasvasos
     */
    public static Object[][] getTrasvasos() {
        //ID, Cedula, Pagados, Entregados, TipoPago, Delivery, Monto total, Fecha

        //Preparar la sentencia SQL para obtener la cantidad de trasvasos
        String sql = "SELECT COUNT(*) FROM Trasvaso;";

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
                        sql = "SELECT Trasvaso.id, cedula, cant_pagada, "
                                + "cant_entregada, tipo_pago, delivery, "
                                + "monto, fecha "
                                + "FROM Trasvaso "
                                + "INNER JOIN Cliente "
                                + "ON id_cliente = Cliente.id "
                                + "ORDER BY Trasvaso.id DESC";

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object trasvasos[][] = new Object[count][8];
                            int i = 0;

                            while (r.next()) {
                                trasvasos[i][0] = r.getInt(1);
                                trasvasos[i][1] = r.getInt(2);
                                trasvasos[i][2] = r.getInt(3);
                                trasvasos[i][3] = r.getInt(4);
                                trasvasos[i][4] = r.getString(5);
                                trasvasos[i][5] = (r.getBoolean(6)) ? "SÍ" : "NO";
                                trasvasos[i][6] = r.getDouble(7);
                                trasvasos[i][7] = r.getString(8);
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
                //conexión. Sin embargo, pudo no haber traído algún dato o
                //traer la cantidad de 0 registros, por lo tanto, se retornará
                //un objeto vacío, en cualquiera de ambos casos.
                return new Object[][]{};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener los trasvasos de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        return null;
    }

    /**
     * Función para obtener las deudas registradas en el sistema
     *
     * @return
     */
    public static Object[][] getDeudas() {
        //ID, Factura, Cedula, Debe Pagar, Debemos Dar, Fecha

        //Preparar la sentencia SQL para obtener las deudas activas
        String sql = "SELECT COUNT(*) "
                + "FROM Deuda "
                + "INNER JOIN Trasvaso "
                + "INNER JOIN Cliente "
                + "	ON id_trasvaso = Trasvaso.id "
                + "	AND id_cliente = Cliente.id "
                + "	AND id_trasvaso IN (SELECT MAX(id) "
                + "         FROM Trasvaso "
                + "         WHERE id_cliente = Cliente.id)";

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
                        /*
                        Sentencia SQL para obtener las deudas pendientes:
                            Seleccioname las deudas DONDE su id de trasvaso, sea
                            el id del ÚLTIMO trasvaso REGISTRADO por el cliente.
                         */
                        sql = "SELECT Deuda.id, "
                                + "     id_trasvaso, "
                                + "     cedula, "
                                + "     pago_pendiente, "
                                + "     entrega_pendiente, "
                                + "     fecha "
                                + "FROM Deuda "
                                + "INNER JOIN Trasvaso "
                                + "INNER JOIN Cliente "
                                + "     ON id_trasvaso = Trasvaso.id "
                                + "     AND id_cliente = Cliente.id "
                                + "     AND id_trasvaso IN (SELECT MAX(Trasvaso.id) "
                                + "         FROM Trasvaso "
                                + "         WHERE id_cliente = Cliente.id) "
                                + "ORDER BY Deuda.id DESC";

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object deudas[][] = new Object[count][6];
                            int i = 0;

                            while (r.next()) {
                                deudas[i][0] = r.getInt(1);
                                deudas[i][1] = r.getInt(2);
                                deudas[i][2] = r.getInt(3);
                                deudas[i][3] = r.getInt(4);
                                deudas[i][4] = r.getInt(5);
                                deudas[i][5] = r.getString(6);
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
                //conexión. Sin embargo, pudo no haber traído algún dato o
                //traer la cantidad de 0 registros, por lo tanto, se retornará
                //un objeto vacío, en cualquiera de ambos casos.
                return new Object[][]{};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener las deudas de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        return null;
    }

    /**
     * Función para obtener el historial completo de las recargas
     *
     * @return Historial de las recargas
     */
    public static Object[][] getRecargas() {
        //ID, RIF, Proveedor, Cantidad, Monto Total, Fecha

        //Preparar la sentencia SQL para obtener la cantidad de recargas
        String sql = "SELECT COUNT(*) FROM Recarga";

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
                        sql = "SELECT Recarga.id, Proveedores.rif, "
                                + "Proveedores.nombre, cantidad, monto, fecha "
                                + "FROM Recarga "
                                + "INNER JOIN Proveedores "
                                + "ON id_prov = Proveedores.id "
                                + "ORDER BY Recarga.id DESC";

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object recargas[][] = new Object[count][6];
                            int i = 0;

                            while (r.next()) {
                                recargas[i][0] = r.getInt(1);
                                recargas[i][1] = r.getString(2);
                                recargas[i][2] = r.getString(3);
                                recargas[i][3] = r.getInt(4);
                                recargas[i][4] = r.getDouble(5);
                                recargas[i][5] = r.getString(6);
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
                //conexión. Sin embargo, pudo no haber traído algún dato o
                //traer la cantidad de 0 registros, por lo tanto, se retornará
                //un objeto vacío, en cualquiera de ambos casos.
                return new Object[][]{};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener las recargas de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        return null;
    }

    /**
     * Función para obtener el historial completo de las ventas de botellones
     *
     * @return Historial de las ventas
     */
    public static Object[][] getVentas() {
        //ID, Cedula, Cantidad, Tipo pago, Delivery, Monto Total, Fecha

        //Preparar la sentencia SQL para obtener la cantidad de trasvasos
        String sql = "SELECT COUNT(*) FROM Venta;";

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
                        sql = "SELECT Venta.id, Cliente.cedula, cantidad, "
                                + "tipo_pago, delivery, monto, fecha "
                                + "FROM Venta "
                                + "INNER JOIN Cliente "
                                + "ON id_cliente = Cliente.id "
                                + "ORDER BY Venta.id DESC";

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object ventas[][] = new Object[count][7];
                            int i = 0;

                            while (r.next()) {
                                ventas[i][0] = r.getInt(1);
                                ventas[i][1] = r.getInt(2);
                                ventas[i][2] = r.getInt(3);
                                ventas[i][3] = r.getString(4);
                                ventas[i][4] = (r.getBoolean(5)) ? "SÍ" : "NO";
                                ventas[i][5] = r.getDouble(6);
                                ventas[i][6] = r.getString(7);
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
                //conexión. Sin embargo, pudo no haber traído algún dato o
                //traer la cantidad de 0 registros, por lo tanto, se retornará
                //un objeto vacío, en cualquiera de ambos casos.
                return new Object[][]{};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener las ventas de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        return null;
    }

    /**
     * Función para obtener el historial completo de las compras de botellones
     *
     * @return Historial de las compras
     */
    public static Object[][] getCompras() {
        //ID, RIF, Proveedor, Cantidad, Monto Total, Fecha

        //Preparar la sentencia SQL para obtener la cantidad de trasvasos
        String sql = "SELECT COUNT(*) FROM Compra;";

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
                        sql = "SELECT Compra.id, Proveedores.rif, "
                                + "Proveedores.nombre, cantidad, monto, fecha "
                                + "FROM Compra "
                                + "INNER JOIN Proveedores "
                                + "ON id_prov = Proveedores.id "
                                + "ORDER BY Compra.id DESC";

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object compras[][] = new Object[count][6];
                            int i = 0;

                            while (r.next()) {
                                compras[i][0] = r.getInt(1);
                                compras[i][1] = r.getString(2);
                                compras[i][2] = r.getString(3);
                                compras[i][3] = r.getInt(4);
                                compras[i][4] = r.getDouble(5);
                                compras[i][5] = r.getString(6);
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
                //conexión. Sin embargo, pudo no haber traído algún dato o
                //traer la cantidad de 0 registros, por lo tanto, se retornará
                //un objeto vacío, en cualquiera de ambos casos.
                return new Object[][]{};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener las recargas de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        return null;
    }

    /**
     * Función para obtener todos los pedidos registrados en el sistema
     *
     * @return
     */
    public static Object[][] getPedidos() {
        //ID, cedula, servicio, cantidad, tipo_pago, fecha, latitud, longitud

        //Preparar la sentencia SQL para obtener la cantidad de trasvasos
        String sql = "SELECT COUNT(*) FROM Pedido WHERE id NOT IN (SELECT id FROM Pedido_Entregado)";

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
                        /*
                            Sentencia SQL para obtener los pedidos activos:
                                Seleccioname los pedidos CUANDO su id NO esté
                                dentro de la tabla de los pedidos pagados
                         */
                        sql = "SELECT Pedido.id, Cliente.cedula, servicio, "
                                + "cantidad, tipo_pago, fecha, direccion "
                                + "FROM Pedido "
                                + "INNER JOIN Usuario "
                                + "ON id_usuario = Usuario.id "
                                + "INNER JOIN Cliente "
                                + "ON id_cliente = Cliente.id "
                                + "INNER JOIN Pedido_Entregado "
                                + "ON Pedido.id NOT IN (SELECT id FROM Pedido_Entregado) "
                                + "ORDER BY Pedido.id DESC";

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object pedidos[][] = new Object[count][8];
                            int i = 0;

                            while (r.next()) {
                                pedidos[i][0] = r.getInt(1);
                                pedidos[i][1] = r.getInt(2);
                                pedidos[i][3] = r.getInt(4);
                                pedidos[i][4] = r.getString(5);
                                pedidos[i][5] = r.getString(6);

                                //Si es TRUE (1) es compra, si es FALSE (0) es recarga
                                boolean servicio = r.getBoolean(3);
                                pedidos[i][2] = servicio ? "COMPRA" : "RECARGA";

                                //Dividir la dirección por su latitud y longitud
                                String direccion = r.getString(7);
                                String split[] = direccion.split(", ");
                                pedidos[i][6] = split[0];
                                pedidos[i][7] = split[1];

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
                //conexión. Sin embargo, pudo no haber traído algún dato o
                //traer la cantidad de 0 registros, por lo tanto, se retornará
                //un objeto vacío, en cualquiera de ambos casos.
                return new Object[][]{};
            }
        } catch (Exception ex) {
            Mensaje.msjError("No se pudieron obtener los pedidos de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        return null;
    }

    /**
     * Función para obtener todos los datos de las transferencias realizadas en
     * los pedidos.
     *
     * @return
     */
    public static Object[][] getTransferencias() {
        //ID, Pedido, Referencia, Banco, Fecha

        //Preparar la sentencia SQL para obtener la cantidad de trasvasos
        String sql = "SELECT COUNT(*) FROM Transferencia;";

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
                        sql = "SELECT * FROM Transferencia ORDER BY id DESC";

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object transferencias[][] = new Object[count][5];
                            int i = 0;

                            while (r.next()) {
                                transferencias[i][0] = r.getInt(1);
                                transferencias[i][1] = r.getInt(2);
                                transferencias[i][2] = r.getString(3);
                                transferencias[i][3] = r.getString(4);
                                transferencias[i][4] = r.getString(5);
                                i++;
                            }

                            //Desconectar la base de datos
                            bdd.desconectar();

                            return transferencias;
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
            Mensaje.msjError("No se pudieron obtener las transferencias de la base de "
                    + "datos.\nError: " + ex);
        }

        //Desconectar la base de datos
        bdd.desconectar();

        return null;
    }

    /**
     * Función para validar la existencia de un cliente en la base de datos,
     * mediante el uso de su cédula.
     *
     * @param cedula Cédula del cliente a buscar
     * @return ID del cliente
     */
    public static int getClienteID(String cedula) {
        //Preparar la sentencia SQL para obtener el id del cliente
        String sql = "SELECT id FROM Cliente WHERE cedula = \"" + cedula + "\"";

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
            Mensaje.msjError("No se pudo obtener el id del cliente.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_VALUE;
    }

    /**
     * Función para validar la existencia de un proveedor en la base de datos,
     * mediante el uso de su RIF.
     *
     * @param rif RIF del proveedor a buscar
     * @return ID del proveedor
     */
    public static int getProveedorID(String rif) {
        //Preparar la sentencia SQL para obtener el id del proveedor
        String sql = "SELECT id FROM Proveedores WHERE rif = \"" + rif + "\"";

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
            Mensaje.msjError("No se pudo obtener el id del proveedor.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_VALUE;
    }

    // ========== ADMINISTRADOR ==========
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
        int sucursal = 1;

        //Preparar la sentencia SQL para obtener la cantidad de clientes
        String sql = "SELECT COUNT(*) "
                + "FROM Empleado "
                + "INNER JOIN Usuario "
                + "	ON id_usuario = Usuario.id "
                + "INNER JOIN Cliente "
                + "	ON id_cliente = Cliente.id "
                + "INNER JOIN Sucursal "
                + "	ON id_sucursal = Sucursal.id "
                + "	AND Sucursal.id = " + sucursal;

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
                        sql = "SELECT Empleado.id, cedula, nombre, apellido,"
                                + " cargo_laboral, rol, descripcion "
                                + "FROM Empleado "
                                + "INNER JOIN Usuario "
                                + "	ON id_usuario = Usuario.id "
                                + "INNER JOIN Cliente "
                                + "	ON id_cliente = Cliente.id "
                                + "INNER JOIN Sucursal "
                                + "	ON id_sucursal = Sucursal.id "
                                + "	AND Sucursal.id = " + sucursal;
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

    /**
     * Función para obtener el id de un usuario mediante su cédula
     *
     * @param cedula
     * @return
     */
    public static int getUserID(String cedula) {
        //Preparar la sentencia SQL para obtener el id del cliente
        String sql = "SELECT Usuario.id "
                + "FROM Usuario "
                + "INNER JOIN Cliente "
                + "ON cedula = \"" + cedula + "\" "
                + "AND id_cliente = Cliente.id";

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
            Mensaje.msjError("No se pudo obtener el id del usuario.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_VALUE;
    }

    public static int getUserRol(String identificacion) {
        //Preparar la sentencia SQL para obtener el id del cliente
        String sql = "SELECT rol FROM Empleado "
                + "INNER JOIN Usuario "
                + "	ON id_usuario = Usuario.id "
                + "INNER JOIN Cliente "
                + "	ON id_cliente = Cliente.id "
                + "WHERE correo = \"" + identificacion + "\" "
                + "	OR cedula = \"" + identificacion + "\"";

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
            Mensaje.msjError("No se pudo obtener el rol del usuario.\nError: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_VALUE;
    }

    public static int getEmpleadoID(int cedula) {
        //Preparar la sentencia SQL para obtener el id del cliente
        String sql = "SELECT Empleado.id "
                + "FROM Empleado "
                + "INNER JOIN Usuario "
                + "	ON id_usuario = Usuario.id "
                + "INNER JOIN Cliente "
                + "     ON id_cliente = Cliente.id "
                + "     AND cedula = "+cedula;

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
        //#, ID, Cedula, Pago, Entr, TipoPago, Delivery, Monto, Fecha
        //Preparar la sentencia SQL para obtener la cantidad de trasvasos
        String sql = "SELECT COUNT(*) FROM Trasvaso "
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

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB(true);
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.selectQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {

                //Header para los trasvasos
                Object[] header = {"#", "ID", "Cedula", "Pagados",
                    "Entregados", "Tipo Pago", "Delivery", "Monto", "Fecha"};

                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Sentencia SQL para obtener los trasvasos
                        sql = "SELECT Trasvaso.id, cedula, cant_pagada, "
                                + "cant_entregada, tipo_pago, delivery, "
                                + "monto, fecha "
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
                return new Object[][]{{header}};
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
        String sql = "SELECT COUNT(*) FROM Recarga "
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
                    "Cantidad", "Monto Total", "Fecha"};

                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Sentencia SQL para obtener los trasvasos
                        sql = "SELECT Recarga.id, rif, nombre, cantidad, monto, fecha "
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
                return new Object[][]{{header}};
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
        String sql = "SELECT COUNT(*) FROM Venta "
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
                    "Tipo Pago", "Delivery", "Monto", "Fecha"};

                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Sentencia SQL para obtener los trasvasos
                        sql = "SELECT Venta.id, cedula, cantidad, "
                                + "	tipo_pago, delivery, monto, fecha "
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
                return new Object[][]{{header}};
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
        String sql = "SELECT COUNT(*) FROM Compra "
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
                    "Cantidad", "Monto Total", "Fecha"};

                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Sentencia SQL para obtener los trasvasos
                        sql = "SELECT Compra.id, rif, nombre, cantidad, monto, fecha "
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
                return new Object[][]{{header}};
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
                        sql = "SELECT * FROM Sucursal";

                        r = bdd.selectQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object sucursales[][] = new Object[count][4];
                            int i = 0;

                            while (r.next()) {
                                sucursales[i][0] = r.getInt(1);
                                sucursales[i][1] = r.getString(2);
                                sucursales[i][2] = r.getString(3);
                                sucursales[i][3] = r.getString(4);
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
