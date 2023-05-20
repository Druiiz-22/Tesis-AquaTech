package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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
            Mensaje.msjError("No se pudo obtener el precio del trasvaso.Error: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_NUMBER;
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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
            Mensaje.msjError("No se pudo obtener el precio de venta del botellón.Error: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_NUMBER;
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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
            Mensaje.msjError("No se pudo obtener el precio del trasvaso.Error: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        return ERROR_NUMBER;
    }

    /**
     * Función para comprobar la existencia y validez de un usuario
     *
     * @param user
     * @param pass
     * @return
     */
    public static Object[] getUser(String user, String pass) {
        //Preparar la sentencia SQL para obtener el trasvaso
        String sql = "SELECT nombre, apellido, rol "
                + "FROM Usuario "
                + "INNER JOIN Cliente "
                + "ON (Usuario.correo = \"" + user + "\""
                + "OR Cliente.cedula = \"" + user + "\")"
                + "AND Usuario.contraseña = \"" + pass + "\"";

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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
                            "El usuario o contraseña es incorrecto."
                            + "Por favor, verifique sus datos"
                    );
                }
            }
        } catch (NumberFormatException | SQLException e) {
            Mensaje.msjError("No se pudo buscar el usuario en la base de datos."
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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
                        r = bdd.ejecutarQuery(sql);

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
            Mensaje.msjError("No se pudieron obtener los clientes de la base de"
                    + "datos. Error: " + ex);
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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
                        r = bdd.ejecutarQuery(sql);

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
            Mensaje.msjError("No se pudieron obtener los proveedores de la base de"
                    + "datos. Error: " + ex);
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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

                        r = bdd.ejecutarQuery(sql);

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
                                trasvasos[i][5] = r.getBoolean(6);
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
            Mensaje.msjError("No se pudieron obtener los trasvasos de la base de"
                    + "datos. Error: " + ex);
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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

                        r = bdd.ejecutarQuery(sql);

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
            Mensaje.msjError("No se pudieron obtener las deudas de la base de"
                    + "datos. Error: " + ex);
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {
                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Sentencia SQL para obtener los trasvasos
                        sql = "SELECT Recarga.id, Proveedores.nombre, "
                                + "cantidad, monto, fecha "
                                + "FROM Recarga "
                                + "INNER JOIN Proveedores "
                                + "ON id_prov = Proveedores.id "
                                + "ORDER BY Recarga.id DESC";

                        r = bdd.ejecutarQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object recargas[][] = new Object[count][5];
                            int i = 0;

                            while (r.next()) {
                                recargas[i][0] = r.getInt(1);
                                recargas[i][1] = r.getString(2);
                                recargas[i][2] = r.getInt(3);
                                recargas[i][3] = r.getDouble(4);
                                recargas[i][4] = r.getString(5);
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
            Mensaje.msjError("No se pudieron obtener las recargas de la base de"
                    + "datos. Error: " + ex);
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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

                        r = bdd.ejecutarQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object ventas[][] = new Object[count][7];
                            int i = 0;

                            while (r.next()) {
                                ventas[i][0] = r.getInt(1);
                                ventas[i][1] = r.getInt(2);
                                ventas[i][2] = r.getInt(3);
                                ventas[i][3] = r.getString(4);
                                ventas[i][4] = r.getBoolean(5);
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
            Mensaje.msjError("No se pudieron obtener las ventas de la base de"
                    + "datos. Error: " + ex);
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {
                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {
                        //Sentencia SQL para obtener los trasvasos
                        sql = "SELECT Compra.id, Proveedores.nombre, cantidad, "
                                + "monto, fecha "
                                + "FROM Compra "
                                + "INNER JOIN Proveedores "
                                + "ON id_prov = Proveedores.id "
                                + "ORDER BY Compra.id DESC";

                        r = bdd.ejecutarQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object compras[][] = new Object[count][5];
                            int i = 0;

                            while (r.next()) {
                                compras[i][0] = r.getInt(1);
                                compras[i][1] = r.getString(2);
                                compras[i][2] = r.getInt(3);
                                compras[i][3] = r.getDouble(4);
                                compras[i][4] = r.getString(5);
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
            Mensaje.msjError("No se pudieron obtener las recargas de la base de"
                    + "datos. Error: " + ex);
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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

                        r = bdd.ejecutarQuery(sql);

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
            Mensaje.msjError("No se pudieron obtener los pedidos de la base de"
                    + "datos. Error: " + ex);
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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

                        r = bdd.ejecutarQuery(sql);

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
            Mensaje.msjError("No se pudieron obtener las transferencias de la base de"
                    + "datos. Error: " + ex);
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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
            Mensaje.msjError("No se pudo obtener el id del cliente. Error: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_NUMBER;
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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
            Mensaje.msjError("No se pudo obtener el id del proveedor. Error: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_NUMBER;
    }

    // ========== ADMINISTRADOR ==========
    /**
     * Función para obtener la lista de los usuarios registrados en el sistema
     *
     * @return
     */
    public static Object[][] getUsers() {
        //"ID", "Cedula", "Rol", "Nombre", "Apellido", "Telefono", "Correo"

        //Preparar la sentencia SQL para obtener la cantidad de clientes
        String sql = "SELECT COUNT(*) FROM Usuario";

        //Instanciar una conexión con la base de datos y conectarla
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

        try {
            //Validar que la respuesta NO sea nula
            if (r != null) {
                //Validar que haya obtenido algún dato
                if (r.next()) {
                    //Obtener la cantidad de clientes y validar la cantidad
                    int count = r.getInt(1);
                    if (count > 0) {

                        //Sentencia SQL para obtener todos los clientes
                        sql = "SELECT Usuario.id, cedula, rol, nombre, "
                                + "apellido, telefono, correo "
                                + "FROM Usuario "
                                + "INNER JOIN Cliente "
                                + "ON id_cliente = Cliente.id "
                                + "ORDER BY Usuario.id DESC";
                        r = bdd.ejecutarQuery(sql);

                        //Validar que la respuesta NO sea nula
                        if (r != null) {
                            Object usuarios[][] = new Object[count][7];
                            int i = 0;

                            while (r.next()) {
                                usuarios[i][0] = r.getInt(1);
                                usuarios[i][1] = r.getInt(2);
                                usuarios[i][3] = r.getString(4);
                                usuarios[i][4] = r.getString(5);
                                usuarios[i][6] = r.getString(6);
                                usuarios[i][7] = r.getString(7);
                                //Determinar el rol
                                int rol = r.getInt(3);
                                usuarios[i][2] = (rol == EMPLEADO) ? "EMPLEADO"
                                        : (rol == ADMINISTRADOR) ? "ADMIN"
                                                : "CLIENTE";

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
            Mensaje.msjError("No se pudieron obtener los clientes de la base de"
                    + "datos. Error: " + ex);
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
        ConexionDB bdd = new ConexionDB();
        bdd.conectar();

        //Obtener el resultado de la sentencia
        ResultSet r = bdd.ejecutarQuery(sql);

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
            Mensaje.msjError("No se pudo obtener el id del usuario. Error: " + e);
        }

        //Terminar la conexión con la base de datos
        bdd.desconectar();

        //En caso de NO obtener ningún dato, retornar el número de error
        return ERROR_NUMBER;
    }

    // ========== REPORTES ==========
    /**
     * Función para obtener el historial de las deudas, en una fecha determinada
     *
     * @param initDate Fecha de inicio
     * @param finalDate Fecha final
     * @return Historial de las deudas
     */
    public static Object[][] getDeudas(String initDate, String finalDate) {

        int id = 1;

        //Cédula minima y máxima
        int ci_min = 5000000;
        int ci_max = 35000000;

        String[] columnas = new String[]{"ID", "Factura", "Cedula", "Debe pagar", "Debemos dar", "Fecha"};
        Object[][] historial = {
            columnas,
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "124", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 4, "14-3-2023 9:37 AM"},
            {id++, "154", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 4, 0, "14-3-2023 9:37 AM"},
            {id++, "198", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 1, "14-3-2023 9:37 AM"},
            {id++, "201", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 0, 2, "14-3-2023 9:37 AM"},
            {id++, "264", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 3, 0, "14-3-2023 9:37 AM"},
            {id++, "287", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 2, 0, "14-3-2023 9:37 AM"},
            {id++, "354", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 7, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"},
            {id++, "369", (int) (Math.random() * (ci_max - ci_min + 1) + ci_min), 1, 0, "14-3-2023 9:37 AM"}
        };

        return historial;
    }

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
        //#, ID, Cedula, Pago, Entr, TipoPago, Delivery, MontoPago, MontoEntr, Fecha
        String[] header = new String[]{"#", "ID", "Cedula", "Pagos", "Entregados", "Tipo pago", "Delivery", "Monto pago", "Monto entre", "Fecha"};

        int rows = 41;
        //numero del id
        int id = 5403;

        //Variables para las fechas
        int dia = 1;
        int hora = 9;
        int minuto = 0;
        int mes = 3;
        int anio = 2023;

        //Cédula minima y máxima
        int min = 5000000;
        int max = 35000000;

        //Lista de trasvasos que será retornado
        Object[][] trasvasos = new Object[rows + 1][header.length];

        //Cabecera
        trasvasos[0] = header;
        //Variable para agregar cada fila
        Object[] row;

        //Ciclo del tamaño de la lista de trasvasos
        for (int i = 1; i < trasvasos.length; i++) {

            //Obtener numeros aleatorios
            int cedula = (int) (Math.random() * (max - min + 1) + min);
            int tipoPago = (int) (Math.random() * 3 + 1);
            int delivery = (int) (Math.random() * 2 + 1);

            //Los clientes vienen entre 2 a 30 minutos de diferencia
            minuto += (int) (Math.random() * (30 - 2 + 1) + 2);

            //Si los minutos superan los 59min
            if (minuto > 59) {
                //Aumentar una hora
                hora++;
                //Obtener los minutos de esa siguiente hora
                minuto = minuto - 59;

                //Si la hora supera las 19 (7 pm)
                if (hora > 19) {
                    //Aumentar un día
                    dia++;
                    //Reiniciar la hora
                    hora = 9;

                    //Si el día supera los 30 días
                    if (dia > 30) {
                        //Avanzar de mes
                        mes++;
                        //Reinicar los días
                        dia = 1;

                        //Si los meses superan el mes 12
                        if (mes > 12) {

                            //Avanzar de año
                            anio++;
                            //Reiniciar el mes
                            mes = 1;
                        }
                    }
                }
            }
            //Obtener la feha
            String fecha = dia + "-" + mes + "-" + anio + " " + hora + ":" + minuto;

            int entregados = (int) (Math.random() * 12 + 1);
            int montoEntregados = entregados * 7;
            int pagos;
            int montoPago;

            //Un cliente hará un pago distinto al monto entregado con una
            //probabilidad de 1/10
            if (((int) (Math.random() * 10 + 1)) == 10) {

                pagos = (int) (Math.random() * 20 + 1);
                montoPago = pagos * 7;

            } else {
                pagos = entregados;
                montoPago = montoEntregados;
            }

            //Guardar el trasvaso
            //#, ID, Cedula, Pago, Entr, TipoPago, Delivery, MontoPago, MontoEntr, Fecha
            row = new Object[]{
                i,
                id,
                cedula,
                pagos,
                entregados,
                (tipoPago == 1) ? "EFECT" : (tipoPago == 2) ? "TRNSF" : "DOLAR",
                (delivery == 1) ? "SI" : "NO",
                montoPago,
                montoEntregados,
                fecha
            };

            //Agregar el trasvaso
            trasvasos[i] = row;

            //Aumentar el ID
            id++;
        }

        //Retornar la lista
        return trasvasos;
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
        //ID, RIF, Proveedor, Cantidad, Monto Total, Fecha

        String[] header = new String[]{"#", "ID", "RIF", "Proveedor", "Cantidad", "Monto Total", "Fecha"};

        int i = 1;
        int x = 4897;

        Object[][] historial = {
            header,
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "20", "100.0", "14-3-2023 9:37 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "19", "95.0", "15-3-2023 10:01 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "21", "105.0", "16-3-2023 9:04 AM"},
            {i++, x++, "J-516510", "AGUA BLANCA", "17", "85.0", "17-3-2023 2:48 PM"}
        };

        return historial;
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
        //ID, Cedula, Cantidad, Tipo pago, Monto Total, Fecha

        String[] header = new String[]{"#", "ID", "Cedula", "Cantidad", "Tipo Pago", "Monto Total", "Fecha"};

        int i = 1;
        int x = 1098;

        Object[][] historial = {
            header,
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"},
            {i++, x++, "27909011", "1", "DOLAR", "120", "14-12-2022 10:08 AM"},
            {i++, x++, "20154005", "2", "TRNSF", "300", "20-2-2023 10:24 AM"}
        };

        return historial;
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
        //ID, RIF, Proveedor, Cantidad, Monto Total, Fecha

        String[] header = new String[]{"#", "ID", "RIF", "Proveedor", "Cantidad", "Monto Total", "Fecha"};

        int i = 1;
        int x = 1098;

        Object[][] historial = {
            header,
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "1", "100.0", "16-12-2022 2:21 PM"},
            {i++, x++, "J-671164", "MANANTIAL", "2", "250.0", "20-2-2023 3:30 PM"}
        };

        return historial;
    }
}
