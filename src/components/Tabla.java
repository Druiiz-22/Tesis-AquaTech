package components;

import components.map.PanelMap;
import database.AdminDB;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import main.MenuLateral;
import database.ReadDB;
import tabs.ventas.Trasvasos;
import tabs.ventas.Ventas;
import tabs.compras.Compras;
import tabs.compras.Recargas;
import database.DeleteDB;
import java.awt.Desktop;
import tabs.proveedores.Proveedores;
import tabs.clientes.PanelClientes;
import static java.awt.Font.PLAIN;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import static javax.swing.RowFilter.regexFilter;
import javax.swing.SwingUtilities;
import main.Frame;
import static properties.Mensaje.msjYesNo;
import static properties.Colores.NEGRO;
import static properties.Fuentes.segoe;
import static properties.Mensaje.msjAdvertencia;
import static properties.Mensaje.msjError;
import tabs.admin.Empleados;
import tabs.admin.Usuarios;
import tabs.clientes.Deudas;
import tabs.historial.HistorialTrasvasos;
import tabs.ventas.Pedidos;

public class Tabla extends JScrollPane implements properties.Constantes {

    // ========== BACKEND ==========
    /**
     * Función para aplicar los listeners a los items del menú
     */
    private void usuariosListeners() {
        itemBuscar.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                //Obtener la cédula
                Object cedula = tabla.getValueAt(index, 1);
                //Validar que NO esté vacía
                if (!cedula.toString().isEmpty()) {
                    //Cambiar al panel de clientes
                    MenuLateral.clickButton(CLIENTES);

                    //Buscar el cliente en la tabla de clientes
                    PanelClientes.buscarCliente(cedula.toString());

                } else {
                    msjError("No se pudo seleccionar el usuario.");
                }
            }
        });
        itemContratar.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                //Obtener la cédula
                Object cedula = tabla.getValueAt(index, 1);
                //Validar que NO esté vacía
                if (!cedula.toString().isEmpty()) {
                    //Cambiar al panel de clientes
                    MenuLateral.clickButton(ADMIN_EMPLEADOS);

                    //Buscar el cliente en la tabla de clientes
                    Empleados.contratarUsuario(cedula.toString());

                } else {
                    msjError("No se pudo seleccionar el usuario.");
                }
            }
        });
        itemBorrar.addActionListener((e) -> {
            eliminar();
        });
        itemEditar.addActionListener((e) -> {
            editar();
        });
    }

    /**
     * Función para asignar los listeners a los items de la tabla de clientes
     */
    private void clientesListeners() {
        itemTrasvaso.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {

                Object cedula = tabla.getValueAt(index, 1);
                Object apellido = tabla.getValueAt(index, 3);

                //Validar que los campos NO estén vacíos
                if (!cedula.toString().isEmpty() && !apellido.toString().isEmpty()) {

                    //Enviar los datos
                    Trasvasos.setCliente(cedula.toString(), apellido.toString());

                    //Cambiar al panel de trasvasos
                    MenuLateral.clickButton(VENTAS_TRASVASO);
                } else {
                    msjError("No se pudo seleccionar el cliente.");
                }
            }
        });

        itemVender.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                Object cedula = tabla.getValueAt(index, 1);
                Object apellido = tabla.getValueAt(index, 3);

                //Validar que los campos NO estén vacíos
                if (!cedula.toString().isEmpty() && !apellido.toString().isEmpty()) {

                    //Enviar los datos
                    Ventas.setCliente(cedula.toString(), apellido.toString());

                    //Cambiar al panel de trasvasos
                    MenuLateral.clickButton(VENTAS_BOTELLON);
                } else {
                    msjError("No se pudo seleccionar el cliente.");
                }
            }
        });

        itemBorrar.addActionListener((e) -> {
            eliminar();
        });

        itemEditar.addActionListener((e) -> {
            editar();
        });
    }

    /**
     * Función para asignar los listeners a los items de la tabla de proveedores
     */
    private void provListeners() {
        itemRecarga.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                Object rif = tabla.getValueAt(index, 1);
                Object nombre = tabla.getValueAt(index, 2);

                //Validar que los campos NO estén vacíos
                if (!rif.toString().isEmpty() && !nombre.toString().isEmpty()) {

                    //Enviar los datos
                    Recargas.setProveedor(rif.toString(), nombre.toString());

                    //Cambiar al panel de trasvasos
                    MenuLateral.clickButton(COMPRAS_RECARGA);
                } else {
                    msjError("No se pudo seleccionar el proveedor.");
                }
            }
        });

        itemCompra.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                Object rif = tabla.getValueAt(index, 1);
                Object nombre = tabla.getValueAt(index, 2);

                //Validar que los campos NO estén vacíos
                if (!rif.toString().isEmpty() && !nombre.toString().isEmpty()) {

                    //Enviar los datos
                    Compras.setProveedor(rif.toString(), nombre.toString());

                    //Cambiar al panel de trasvasos
                    MenuLateral.clickButton(COMPRAS_BOTELLON);
                } else {
                    msjError("No se pudo seleccionar el proveedor.");
                }
            }
        });

        itemBorrar.addActionListener((e) -> {
            eliminar();
        });

        itemEditar.addActionListener((e) -> {
            editar();
        });
    }

    /**
     * Función para asignar los listeners a los items de la tabla de deudas
     */
    private void deudasListeners() {
        itemBuscar.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {

                String factura = tabla.getValueAt(index, 1).toString();

                //Validar que el campo NO esté vacío
                if (!factura.isEmpty()) {

                    //Cambiar al panel de trasvasos
                    MenuLateral.clickButton(HISTORIAL_TRASVASO);

                    //Buscar la factura en la tabla del historial de trasvasos
                    HistorialTrasvasos.buscarFactura(factura);

                } else {
                    msjError("No se pudo seleccionar la factura.");
                }
            }
        });
        itemTrasvaso.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                //Comprobar que la deuda seleccionada ESTÉ PENDIENTE
                if (deudaActiva[index]) {
                    //Obtener la cédula de la deuda
                    String cedula = tabla.getValueAt(index, 2).toString();

                    //Validar que los campos NO estén vacíos
                    if (!cedula.isEmpty()) {

                        //Buscar el apellido del cliente
                        String apellido = PanelClientes.getApellido(cedula);

                        if (!apellido.isEmpty()) {
                            try {
                                //Intentar convertir pagar y entregar en enteros
                                int pagar = Integer.parseInt(tabla.getValueAt(index, 3).toString());
                                int entregar = Integer.parseInt(tabla.getValueAt(index, 4).toString());

                                //Enviar los datos para pagar la deuda
                                Trasvasos.pagarDeuda(cedula, apellido, pagar, entregar);

                                //Cambiar al panel de trasvasos
                                MenuLateral.clickButton(VENTAS_TRASVASO);

                            } catch (NumberFormatException ex) {
                                msjError("La cantidad de pagar o entregar, son inválidos."
                                        + "\nPor favor, actualice los datos y verifique"
                                        + "que estos campos tengan número enteros válidos.");
                            }
                        }
                    } else {
                        msjError("No se pudo seleccionar el cliente de la deuda.");
                    }
                } else {
                    msjError("La deuda seleccionada ya se encuentra pagada.");
                }
            }
        });
    }

    /**
     * Función para asignar los listeners a los items de la tabla de pedidos
     */
    private void pedidosListeners() {
        itemTrasvaso.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            //Validar si el index seleccionado es valido
            if (validarSelect(index)) {
                //Comprobar que el pedido seleccionado ESTÉ ACTIVO
                if (pedidoActivo[index]) {
                    pagarPedido(index);
                } else {
                    msjError("El pedido seleccionado ya se encuentra pagado.");
                }
            }
        });
        itemUbicar.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                //Buscar el punto en el mapa
                PanelMap.enfocarPunto(latitudes[index], longitudes[index]);
            }
        });
        itemGoogleMaps.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                //Validar que la plataforma sea capaz de usar la clase Desktop
                if (Desktop.isDesktopSupported()) {
                    Desktop escritorio = Desktop.getDesktop();

                    //Validar que la plataforma sea capaz de usar un navegador
                    if (escritorio.isSupported(Desktop.Action.BROWSE)) {
                        try {
                            //URL de google maps, abriendo un punto en el mapa, según 
                            //la latitud y longitud del mismo
                            URI uri = new URI("https://www.google.es/maps?q=" + latitudes[index] + "," + longitudes[index]);

                            //Intentar abrir el navegador y buscar la URL
                            escritorio.browse(uri);

                        } catch (IOException | URISyntaxException ex) {
                            msjError("No se pudo abrir el navegador.\nError: " + ex.getMessage());
                        }
                    } else {
                        msjError("La plataforma actual no es compatible para "
                                + "abrir un navegador.\nDeberá copiar las coordenadas del"
                                + "punto y buscarlo manualmente en Google Maps.");
                    }
                } else {
                    msjError("La plataforma actual no es compatible con la clase"
                            + "\"Desktop\", impidiendo\n la posibilidad de abrir ficheros y "
                            + "sitios web.");
                }
            }
        });
        itemInformacion.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                Pedidos.infoPedido(index);
            }
        });
    }

    private void empleadosListeners() {
        itemBuscar.addActionListener((e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                //Obtener la cédula
                Object cedula = tabla.getValueAt(index, 1);
                //Validar que NO esté vacía
                if (!cedula.toString().isEmpty()) {
                    //Cambiar al panel de clientes
                    MenuLateral.clickButton(ADMIN_USUARIOS);

                    //Buscar el cliente en la tabla de clientes
                    Usuarios.buscarUsuario(cedula.toString());

                } else {
                    msjError("No se pudo seleccionar el usuario.");
                }
            }
        });
        itemBorrar.addActionListener((e) -> {
            eliminar();
        });
        itemEditar.addActionListener((e) -> {
            editar();
        });
    }

    /**
     * Función privada para pagar un pedido de una fila
     *
     * @param index
     */
    public void pagarPedido(int index) {
        if (type == VENTAS_PEDIDOS) {
            //Obtener la cédula de la deuda
            String cedula = tabla.getValueAt(index, 1).toString();

            //Validar que el campos NO esté vacíos
            if (!cedula.isEmpty()) {

                //Buscar el apellido del cliente
                String apellido = PanelClientes.getApellido(cedula);

                //Validar que el campo no esté vacío
                if (!apellido.isEmpty()) {
                    try {
                        //Obtener los datos del pedido
                        String servicio = tabla.getValueAt(index, 2).toString();
                        String tipoPago = tabla.getValueAt(index, 4).toString();
                        int cantidad = Integer.parseInt(tabla.getValueAt(index, 3).toString());

                        //Determinar si es un trasvaso o venta
                        if (servicio.equals("RECARGA")) {
                            //Enviar los datos para pagar el pedido
                            Trasvasos.pagarPedido(cedula, apellido, cantidad, tipoPago);
                            //Cambiar al panel de trasvasos
                            MenuLateral.clickButton(VENTAS_TRASVASO);

                        } else {
                            //Enviar los datos para pagar el pedido
                            Ventas.pagarPedido(cedula, apellido, cantidad, tipoPago);
                            //Cambiar al panel de trasvasos
                            MenuLateral.clickButton(VENTAS_BOTELLON);
                        }

                    } catch (NumberFormatException ex) {
                        msjError("La cantidad de pagar o entregar, son inválidos."
                                + "\nPor favor, actualice los datos y verifique"
                                + "que estos campos tengan número enteros válidos.");
                    }
                }
            } else {
                msjError("No se pudo seleccionar el cliente del pedido.");
            }
        }
    }

    /**
     * <h2>Función para eliminar una fila de la tabla</h2>
     * <p>
     * Esta función obtiene, dentro de ella, el índice del item seleccionado, en
     * caso de NO tener ningún item seleccionado cuando se llame la función,
     * saltará un mensaje de error.</p>
     */
    private void eliminar() {

        int index = tabla.getSelectedRow();
        if (validarSelect(index)) {

            //Mensaje de confirmación
            if (msjYesNo("¿Está seguro de borrar el registro seleccionado?")) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            //Obtener la identificación del cliente o proveedor
                            Object id = tabla.getValueAt(index, 0);

                            //Abrir el GlassPane de carga
                            Frame.openGlass(0);

                            //Comprobar el tipo de la tabla
                            switch (type) {
                                case CLIENTES:
                                    //Intentar eliminarlo de la base de datos
                                    if (DeleteDB.removeCliente(id)) {
                                        //Ya que no es posible eliminar una fila de una tabla
                                        //sin acceder a su Model, al eliminar la final en la
                                        //base de datos, se actualizará la tabla
                                        actualizarDatos();

                                        Ventas.vaciarCampos();
                                    }
                                    break;

                                case PROVEEDOR:
                                    //Intentar eliminarlo de la base de datos
                                    if (DeleteDB.removeProveedor(id)) {
                                        //Ya que no es posible eliminar una fila de una tabla
                                        //sin acceder a su Model, al eliminar la final en la
                                        //base de datos, se actualizará la tabla
                                        actualizarDatos();

                                        Compras.vaciarCampos();
                                    }
                                    break;

                                case DEUDAS:
                                    //Intentar eliminar la deuda de la base de datos
                                    if (DeleteDB.removeDeuda(id)) {
                                        //Ya que no es posible eliminar una fila de una tabla
                                        //sin acceder a su Model, al eliminar la final en la
                                        //base de datos, se actualizará la tabla
                                        actualizarDatos();
                                    }
                                    break;

                                case VENTAS_PEDIDOS:
                                    //Intentar eliminar la deuda de la base de datos
                                    if (DeleteDB.removePedido(id)) {
                                        //Ya que no es posible eliminar una fila de una tabla
                                        //sin acceder a su Model, al eliminar la final en la
                                        //base de datos, se actualizará la tabla
                                        Pedidos.actualizarDatos();
                                    }
                                    break;

                                case ADMIN_USUARIOS:
                                    //Validar el rol de administrador y su clave para
                                    //intentar eliminar el usuario
                                    if (AdminDB.validateAdminUser()) {
                                        if (DeleteDB.removeUsuario(id)) {
                                            //Ya que no es posible eliminar una fila de una tabla
                                            //sin acceder a su Model, al eliminar la final en la
                                            //base de datos, se actualizará la tabla
                                            actualizarDatos();

                                            Usuarios.vaciarCampos();
                                        }
                                    }
                                    break;

                                case ADMIN_EMPLEADOS:
                                    //Validar el rol de administrador y su clave para
                                    //intentar eliminar el usuario
                                    if (AdminDB.validateAdminUser()) {
                                        if (DeleteDB.removeEmpleado(id)) {
                                            //Ya que no es posible eliminar una fila de una tabla
                                            //sin acceder a su Model, al eliminar la final en la
                                            //base de datos, se actualizará la tabla
                                            actualizarDatos();

                                            Empleados.vaciarCampos();
                                        }
                                    }
                                    break;
                            }
                            //Cerrar el GlassPane de carga
                            Frame.closeGlass();
                        } catch (Exception e) {
                            msjError("No se pudo eliminar el registro de la tabla."
                                    + "\nError: " + e);
                        }
                    }
                }.start();
            }
        }
    }

    /**
     * Función para editar una fila
     */
    private void editar() {
        //Obtener le índice de la fila seleccionada
        int index = tabla.getSelectedRow();

        //Validar que el índice sea correcto
        if (validarSelect(index)) {
            //Determinar el tipo de tabla
            switch (type) {
                case CLIENTES: {
                    //Obtener los datos del cliente seleccionado
                    Object cedula = tabla.getValueAt(index, 1);
                    Object nombre = tabla.getValueAt(index, 2);
                    Object apellido = tabla.getValueAt(index, 3);
                    Object telefono = tabla.getValueAt(index, 4);
                    //Enviar el cliente a la pestaña de clientes, que será 
                    //enviado a la ventana de nuevos clientes para su edición
                    PanelClientes.editCliente(
                            cedula.toString(),
                            nombre.toString(),
                            apellido.toString(),
                            telefono.toString()
                    );
                    break;
                }
                case PROVEEDOR: {
                    //Obtener los datos del proveedor seleccionado
                    Object rif = tabla.getValueAt(index, 1);
                    Object nombre = tabla.getValueAt(index, 2);
                    Object telefono = tabla.getValueAt(index, 3);
                    //Enviar el proveedor a la pestaña de proveedores, que será 
                    //enviado a la ventana de nuevos proveedores para su edición
                    Proveedores.editProveedor(
                            rif.toString(),
                            nombre.toString(),
                            telefono.toString()
                    );
                    break;
                }
                case ADMIN_USUARIOS: {
                    //Obtener los datos del usuario seleccionado
                    Object cedula = tabla.getValueAt(index, 1);
                    Object nombre = tabla.getValueAt(index, 2);
                    Object apellido = tabla.getValueAt(index, 3);
                    Object telefono = tabla.getValueAt(index, 4);
                    Object correo = tabla.getValueAt(index, 5);

                    //Enviar el usuario a la pestaña de usuarios, que será 
                    //enviado a la ventana de nuevos usuarios para su edición
                    Usuarios.editUsuario(
                            cedula.toString(),
                            nombre.toString(),
                            apellido.toString(),
                            telefono.toString(),
                            correo.toString()
                    );
                    break;
                }
                case ADMIN_EMPLEADOS: {
                    //Obtener los datos del usuario seleccionado
                    Object cedula = tabla.getValueAt(index, 1);
                    Object cargo = tabla.getValueAt(index, 4);
                    Object rol = tabla.getValueAt(index, 5);
                    Object sucursal = tabla.getValueAt(index, 6);

                    //Enviar el empleado a la pestaña de empleados, que será 
                    //enviado a la ventana de nuevos empleados para su edición
                    Empleados.editEmpleado(
                            cedula.toString(),
                            cargo.toString(),
                            rol.toString(),
                            sucursal.toString()
                    );
                    break;
                }
            }
        }
    }

    /**
     * Función para validar que se haya seleccionado 1 sola fila
     *
     * @return
     */
    private boolean validarSelect(int index) {
        //Obtener el indice del item seleccionado
        int count = tabla.getSelectedRowCount();

        //Validar que SÍ haya un item seleccionado y que SOLO se haya
        //seleccionado un item
        if (index != -1 && count == 1) {

            return true;

        } else {
            msjError("Debe seleccionar una sola fila.");
        }

        return false;
    }

    /**
     * Función para insertar una lista de datos entera a la tabla, mediante una
     * única llamada a una función.
     *
     * @return
     */
    public boolean actualizarDatos() {
        //Matriz para guardar los datos retornados de la base de datos
        datos = null;

        //Determinar el tipo de la tabla y buscar sus registros en la base de datos
        switch (type) {
            case CLIENTES:
                datos = ReadDB.getClientes();
                break;

            case PROVEEDOR:
                datos = ReadDB.getProveedores();
                break;

            case DEUDAS:
                //Comprobar si se filtrarán las deudas activas o no
                boolean filtrado = Deudas.isDeudasFiltradas();
                Object[][] lista = (filtrado) ? ReadDB.getDeudas() : ReadDB.getDeudasSinFiltro();

                //Instanciar los datos
                datos = new Object[lista.length][cabecera.length];
                //Instanciar el estado de las deudas
                deudaActiva = new boolean[lista.length];

                for (int i = 0; i < lista.length; i++) {
                    //Guardar el estado del pedido actual
                    deudaActiva[i] = Boolean.parseBoolean(lista[i][6].toString());
                    //Guardar todos los demás datos de los pedidos
                    datos[i] = new Object[]{
                        lista[i][0],
                        lista[i][1],
                        lista[i][2],
                        lista[i][3],
                        lista[i][4],
                        lista[i][5]
                    };
                }
                break;

            case VENTAS_PEDIDOS:
                //Validar si se filtrarán los pedidos activos o no
                filtrado = Pedidos.isPedidosFiltrados();
                //Obtener los pedidos en una lista auxiliar
                lista = (filtrado) ? ReadDB.getPedidos() : ReadDB.getPedidosSinFiltro();
                //String para unificar las latitudes y longitudes
                String direccion;

                //Instanciar la lista de los datos
                datos = new Object[lista.length][cabecera.length];
                //Instanciar los atributos de latitud y longitud
                latitudes = new double[lista.length];
                longitudes = new double[lista.length];
                pedidoActivo = new boolean[lista.length];
                //Variable para dividir las direcciones
                String split[];

                //Recorrer cada dato para unificar la latitud y longitud, del
                //pedido actual, en una sola cadena de texto
                for (int i = 0; i < lista.length; i++) {

                    //Guardar el estado del pedido
                    pedidoActivo[i] = Boolean.parseBoolean(lista[i][7].toString());

                    //Obtener la dirección y dividirla
                    split = lista[i][6].toString().split(", ");

                    //Guardar las latitudes y longitudes
                    latitudes[i] = Double.parseDouble(split[0]);
                    longitudes[i] = Double.parseDouble(split[1]);

                    //Redondear las direcciones a 5 dígitos
                    String lat = String.valueOf(Math.round(latitudes[i] * 100000) / 100000.0);
                    String lon = String.valueOf(Math.round(longitudes[i] * 100000) / 100000.0);
                    //Guardar la nueva dirección redondeada
                    direccion = lat + ", " + lon;

                    //Guardar los datos con la nueva dirección
                    datos[i] = new Object[]{
                        lista[i][0],
                        lista[i][1],
                        lista[i][2],
                        lista[i][3],
                        lista[i][4],
                        lista[i][5],
                        direccion
                    };
                }
                break;

            case HISTORIAL_TRASVASO:
                datos = ReadDB.getTrasvasos();
                break;

            case HISTORIAL_RECARGA:
                datos = ReadDB.getRecargas();
                break;

            case HISTORIAL_VENTA:
                datos = ReadDB.getVentas();
                break;

            case HISTORIAL_COMPRA:
                datos = ReadDB.getCompras();
                break;

            case ADMIN_USUARIOS:
                datos = ReadDB.getUsers();
                break;

            case ADMIN_EMPLEADOS:
                datos = ReadDB.getEmpleados();
                break;
        }

        //Si los datos obtenidos es nulo, retornar busqueda incompleta
        if (datos == null) {
            return false;
        }

        //Deseleccionar cualquier selección en la tabla
        tabla.clearSelection();
        //Eliminar todos los componentes de la tabla
        tabla.removeAll();

        //Insertar un modelo nuevo en blanco ineditable
        this.modelo = null;
        this.modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        SwingUtilities.invokeLater(() -> {
            //Insertar su header
            for (String campo : cabecera) {
                modelo.addColumn(campo);
            }

            //Insertar todos los datos en el modelo de la tabla
            for (Object[] dato : datos) {
                modelo.addRow(dato);
            }
            modelo.fireTableDataChanged();

            //Insertar el modelo en la tabla
            tabla.setModel(modelo);
        });

        //Retornar busqueda exitosa
        return true;
    }

    /**
     * Función para obtener la cantidad de filas que hayan en la tabla
     *
     * @return
     */
    public int getRowCount() {
        return tabla.getRowCount();
    }

    /**
     * Función para obtener un dato en una celda en específico de la tabla.
     *
     * @param row
     * @param column
     * @return
     */
    public Object getValueAt(int row, int column) {
        return tabla.getValueAt(row, column);
    }

    public int getDeudasActivas() {
        int count = 0;
        //Comprobar que la tabla sea realmente de deudas
        if (type == DEUDAS) {
            //Realizar un ciclo por el tamaño de la cantidad de deudas
            for (boolean estado : deudaActiva) {
                //Si la deuda se encuentra activa, sumar 1 al conteo
                count = (estado) ? count + 1 : count;
            }
        }
        //Retornar el conteo
        return count;
    }

    public int getPedidosActivos() {
        int count = 0;
        //Comprobar que la tabla sea realmente de deudas
        if (type == VENTAS_PEDIDOS) {
            //Realizar un ciclo por el tamaño de la cantidad de pedidos
            for (boolean estado : pedidoActivo) {
                //Si el pedido se encuentra activa, sumar 1 al conteo
                count = (estado) ? count + 1 : count;
            }
        }
        //Retornar el conteo
        return count;
    }

    //ATRIBUTOS BACK-END
    private double[] latitudes, longitudes;
    private boolean[] deudaActiva, pedidoActivo;

    // ========== FRONTEND ==========
    /**
     * Constructor de las tablas que se usarán durante el software
     *
     * @param type
     */
    public Tabla(int type) {
        //Guardar el tipo
        this.type = type;
        this.getVerticalScrollBar().setUnitIncrement(8);

        //Determinar la cabecera
        header();

        //Instanciar los componentes
        initComponents();
    }

    /**
     * Función para determinar la cabecera (columnas) de la tabla según su tipo
     */
    private void header() {
        //Determinar la cabecera, según el tipo de tabla
        switch (type) {
            case CLIENTES:
                //Establecer las columnas de la tabla
                cabecera = new String[]{"ID", "Cedula", "Nombre", "Apellido", "Telefono"};
                break;

            case PROVEEDOR:
                cabecera = new String[]{"ID", "RIF", "Nombre", "Telefono"};
                break;

            case DEUDAS:
                cabecera = new String[]{"ID", "Factura", "Cedula", "Debe pagar", "Debemos dar", "Fecha"};
                break;

            case VENTAS_PEDIDOS:
                cabecera = new String[]{"ID", "Cedula", "Servicio", "Cantidad",
                    "Tipo Pago", "Fecha", "Direccion"};
                break;

            case HISTORIAL_TRASVASO:
                cabecera = new String[]{"ID", "Cedula", "Pagados", "Entregados",
                    "Pago", "Delivery", "Monto Total", "Fecha"};
                break;

            case HISTORIAL_RECARGA:
                cabecera = new String[]{"ID", "RIF", "Proveedor", "Cantidad",
                    "Monto Total", "Fecha"};
                break;

            case HISTORIAL_VENTA:
                //Establecer las columnas de la tabla
                cabecera = new String[]{"ID", "Cedula", "Cantidad", "Tipo pago",
                    "Delivery", "Monto Total", "Fecha"};
                break;

            case HISTORIAL_COMPRA:
                cabecera = new String[]{"ID", "RIF", "Proveedor", "Cantidad",
                    "Monto Total", "Fecha"};
                break;

            case ADMIN_USUARIOS:
                cabecera = new String[]{"ID", "Cedula", "Nombre",
                    "Apellido", "Telefono", "Correo"};
                break;

            case ADMIN_EMPLEADOS:
                cabecera = new String[]{"ID", "Cedula", "Nombre",
                    "Apellido", "Cargo laboral", "Rol", "Sucursal"};
                break;
        }
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        //Instanciar la tabla con el modelo
        this.tabla = new JTable() {

            //Heredar la función getToolTipText para mostrar un mensaje
            //personalizado a cada celda de las tablas
            @Override
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                try {
                    tip = "<html>"
                            + "<b>Dato: </b>"
                            + getValueAt(rowIndex, colIndex).toString()
                            + "</html>";
                } catch (RuntimeException ex) {

                }
                return tip;
            }
        };

        //Insertar la tabla en la clase de scroll
        this.setViewportView(tabla);

        //Crear el menú según el tipo de tabla
        switch (type) {
            case CLIENTES:
                clientesMenu();
                clientesListeners();
                break;

            case PROVEEDOR:
                provMenu();
                provListeners();
                break;

            case DEUDAS:
                deudasMenu();
                deudasListeners();
                break;

            case VENTAS_PEDIDOS:
                pedidosMenu();
                pedidosListeners();
                break;

            case ADMIN_USUARIOS:
                usuariosMenu();
                usuariosListeners();
                break;

            case ADMIN_EMPLEADOS:
                empleadosMenu();
                empleadosListeners();
                break;
        }
    }

    /**
     * Función para iniciar el menú para proveedores
     */
    private void provMenu() {
        //Propiedades de los items del menú
        itemEditar.setFont(segoe(13, PLAIN));
        itemEditar.setForeground(NEGRO);
        itemBorrar.setFont(segoe(13, PLAIN));
        itemBorrar.setForeground(NEGRO);

        itemRecarga = new JMenuItem("Seleccionar para recargar");
        itemRecarga.setFont(segoe(13, PLAIN));
        itemRecarga.setForeground(NEGRO);

        itemCompra = new JMenuItem("Seleccionar para comprarle");
        itemCompra.setFont(segoe(13, PLAIN));
        itemCompra.setForeground(NEGRO);

        try {
            //Buscar la imagen de cada item
            itemEditar.setIcon(getMenuIcon("editar"));
            itemBorrar.setIcon(getMenuIcon("borrar"));
            itemRecarga.setIcon(getMenuIcon("recargar"));
            itemCompra.setIcon(getMenuIcon("comprar"));

        } catch (Exception e) {
            msjAdvertencia("No se pudo cargar los íconos del menú desplegable en los proveedores.\n"
                    + "El software seguirá funcionando sin los íconos.");

        } finally {

            //Añadir los items al menú
            menuPopup.add(itemRecarga);
            menuPopup.add(itemCompra);

            menuPopup.addSeparator();

            menuPopup.add(itemEditar);
            menuPopup.add(itemBorrar);

            tabla.setComponentPopupMenu(menuPopup);
        }
    }

    /**
     * Función para iniciar el menú para clientes
     */
    private void clientesMenu() {
        //Propiedades de los items del menú
        itemEditar.setFont(segoe(13, PLAIN));
        itemEditar.setForeground(NEGRO);
        itemBorrar.setFont(segoe(13, PLAIN));
        itemBorrar.setForeground(NEGRO);

        itemTrasvaso = new JMenuItem("Seleccionar para trasvasarle");
        itemTrasvaso.setFont(segoe(13, PLAIN));
        itemTrasvaso.setForeground(NEGRO);

        itemVender = new JMenuItem("Seleccionar para venderle");
        itemVender.setFont(segoe(13, PLAIN));
        itemVender.setForeground(NEGRO);

        try {
            //Buscar la imagen de cada item
            itemEditar.setIcon(getMenuIcon("editar"));
            itemBorrar.setIcon(getMenuIcon("borrar"));
            itemTrasvaso.setIcon(getMenuIcon("trasvasar"));
            itemVender.setIcon(getMenuIcon("vender"));

        } catch (Exception e) {
            msjAdvertencia("No se pudo cargar los íconos del menú desplegable en los clientes.\n"
                    + "El software seguirá funcionando sin los íconos.");

        } finally {

            //Añadir los items al menú
            menuPopup.add(itemTrasvaso);
            menuPopup.add(itemVender);

            menuPopup.addSeparator();

            menuPopup.add(itemEditar);
            menuPopup.add(itemBorrar);

            tabla.setComponentPopupMenu(menuPopup);
        }
    }

    /**
     * Función para iniciar el menú para usuarios en administración
     */
    private void usuariosMenu() {
        //Propiedades de los items del menú
        itemEditar.setFont(segoe(13, PLAIN));
        itemEditar.setForeground(NEGRO);

        itemBorrar.setFont(segoe(13, PLAIN));
        itemBorrar.setForeground(NEGRO);

        itemContratar = new JMenuItem("Contratar");
        itemContratar.setFont(segoe(13, PLAIN));
        itemContratar.setForeground(NEGRO);

        itemBuscar = new JMenuItem("Ver en clientes");
        itemBuscar.setFont(segoe(13, PLAIN));
        itemBuscar.setForeground(NEGRO);

        try {
            //Buscar la imagen de cada item
            itemEditar.setIcon(getMenuIcon("editar"));
            itemBorrar.setIcon(getMenuIcon("borrar"));
            itemContratar.setIcon(getMenuIcon("contratar"));
            itemBuscar.setIcon(getMenuIcon("buscar"));

        } catch (Exception e) {
            System.out.println("e = " + e);
            msjAdvertencia("No se pudo cargar los íconos del menú desplegable en los usuarios.\n"
                    + "El software seguirá funcionando sin los íconos.");

        } finally {

            //Añadir los items al menú
            menuPopup.add(itemBuscar);
            menuPopup.addSeparator();
            menuPopup.add(itemEditar);
            menuPopup.add(itemBorrar);
            menuPopup.addSeparator();
            menuPopup.add(itemContratar);

            tabla.setComponentPopupMenu(menuPopup);
        }
    }

    private void empleadosMenu() {
        //Propiedades de los items del menú
        itemEditar.setFont(segoe(13, PLAIN));
        itemEditar.setForeground(NEGRO);

        itemBorrar.setText("Despedir");
        itemBorrar.setFont(segoe(13, PLAIN));
        itemBorrar.setForeground(NEGRO);

        itemBuscar = new JMenuItem("Ver su usuario");
        itemBuscar.setFont(segoe(13, PLAIN));
        itemBuscar.setForeground(NEGRO);

        try {
            //Buscar la imagen de cada item
            itemBuscar.setIcon(getMenuIcon("buscar"));
            itemEditar.setIcon(getMenuIcon("editar"));
            itemBorrar.setIcon(getMenuIcon("borrar"));

        } catch (Exception e) {
            msjAdvertencia("No se pudo cargar los íconos del menú desplegable en los usuarios.\n"
                    + "El software seguirá funcionando sin los íconos.");

        } finally {

            //Añadir los items al menú
            menuPopup.add(itemBuscar);
            menuPopup.addSeparator();
            menuPopup.add(itemEditar);
            menuPopup.add(itemBorrar);

            tabla.setComponentPopupMenu(menuPopup);
        }
    }

    /**
     * Función para iniciar el menú para la tabla de las deudas
     */
    private void deudasMenu() {
        itemBorrar.setText("Cancelar deuda");
        itemBorrar.setFont(segoe(13, PLAIN));
        itemBorrar.setForeground(NEGRO);

        itemTrasvaso = new JMenuItem("Pagar deuda");
        itemTrasvaso.setFont(segoe(13, PLAIN));
        itemTrasvaso.setForeground(NEGRO);

        itemBuscar = new JMenuItem("Ver factura");
        itemBuscar.setFont(segoe(13, PLAIN));
        itemBuscar.setForeground(NEGRO);

        try {
            //Buscar la imagen de cada item
            itemBorrar.setIcon(getMenuIcon("borrar"));
            itemTrasvaso.setIcon(getMenuIcon("vender"));
            itemBuscar.setIcon(getMenuIcon("buscar"));

        } catch (Exception e) {
            msjAdvertencia("No se pudo cargar los íconos del menú desplegable en las deudas.\n"
                    + "El software seguirá funcionando sin los íconos.");

        } finally {
            //Añadir los items al menú
            menuPopup.add(itemBuscar);
            menuPopup.add(itemTrasvaso);
            menuPopup.addSeparator();
            menuPopup.add(itemBorrar);

            tabla.setComponentPopupMenu(menuPopup);
        }
    }

    /**
     * Función para iniciar el menú para la tabla de los pedidos
     */
    private void pedidosMenu() {
        itemTrasvaso = new JMenuItem("Pagar el pedido");
        itemTrasvaso.setFont(segoe(13, PLAIN));
        itemTrasvaso.setForeground(NEGRO);

        itemUbicar = new JMenuItem("Ubicar en el mapa");
        itemUbicar.setFont(segoe(13, PLAIN));
        itemUbicar.setForeground(NEGRO);

        itemGoogleMaps = new JMenuItem("Ubicar en Google Maps");
        itemGoogleMaps.setFont(segoe(13, PLAIN));
        itemGoogleMaps.setForeground(NEGRO);

        itemInformacion = new JMenuItem("Más información");
        itemInformacion.setFont(segoe(13, PLAIN));
        itemInformacion.setForeground(NEGRO);

        try {
            //Buscar la imagen de cada item
            itemTrasvaso.setIcon(getMenuIcon("vender"));
            itemUbicar.setIcon(getMenuIcon("ubicacion"));
            itemGoogleMaps.setIcon(getMenuIcon("web"));
            itemInformacion.setIcon(getMenuIcon("informacion"));

        } catch (Exception e) {
            msjAdvertencia("No se pudo cargar los íconos del menú desplegable en los pedidos.\n"
                    + "El software seguirá funcionando sin los íconos.");

        } finally {
            //Añadir los items al menú
            menuPopup.add(itemInformacion);
            menuPopup.add(itemTrasvaso);
            menuPopup.addSeparator();
            menuPopup.add(itemUbicar);
            menuPopup.add(itemGoogleMaps);

            tabla.setComponentPopupMenu(menuPopup);
        }
    }

    /**
     * Función para buscar los íconos de los items
     *
     * @param name Nombre del ícon
     * @return ImageIcon del item
     */
    private ImageIcon getMenuIcon(String name) {
        //Variable para las imagenes
        ImageIcon img = new ImageIcon(getClass().getResource("/icons/popup/" + name + ".png"));

        //Determinar el tamaño de la imagen.
        int size = 18;

        //Retornar la imagen escalada
        return new ImageIcon(img.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
    }

    /**
     * Función para filtrar la tabla y buscar coincidencias en una cadena de
     * texto ingresado
     *
     * @param txt Texto para buscar su coincidencia
     */
    public void buscar(String txt) {
        //Poner en mayuscula el texto
        txt = txt.toUpperCase();

        //Asignar el modelo de tabla al filtro, cada vez que se llame la función
        sorter = new TableRowSorter(modelo);

        //Validar el tipo de tabla
        switch (type) {
            case CLIENTES:
                //Buscar la coincidencia entre todos los indices
                sorter.setRowFilter(regexFilter(txt, 0, 1, 2, 3, 4));
                break;

            case PROVEEDOR:
                //Buscar la coincidencia entre todos los indices
                sorter.setRowFilter(regexFilter(txt, 0, 1, 2, 3));
                break;

            case DEUDAS:
                //Buscar la coincidencia entre todos los indices
                sorter.setRowFilter(regexFilter(txt, 0, 1, 2, 3, 4, 5, 6, 7));
                break;

            case VENTAS_PEDIDOS:
                //Buscar la coincidencia entre todos los indices
                sorter.setRowFilter(regexFilter(txt, 0, 1, 2, 3, 4, 5, 6, 7));
                break;

            case HISTORIAL_TRASVASO:
                //Buscar la coincidencia entre todos los indices
                sorter.setRowFilter(regexFilter(txt, 0, 1, 2, 3, 4, 5, 6, 7));
                break;

            case HISTORIAL_RECARGA:
                //Buscar la coincidencia entre todos los indices
                sorter.setRowFilter(regexFilter(txt, 0, 1, 2, 3, 4, 5));
                break;

            case HISTORIAL_VENTA:
                //Buscar la coincidencia entre todos los indices
                sorter.setRowFilter(regexFilter(txt, 0, 1, 2, 3, 4, 5));
                break;

            case HISTORIAL_COMPRA:
                //Buscar la coincidencia entre todos los indices
                sorter.setRowFilter(regexFilter(txt, 0, 1, 2, 3, 4, 5));
                break;

            case ADMIN_USUARIOS:
                //Buscar la coincidencia entre todos los indices
                sorter.setRowFilter(regexFilter(txt, 0, 1, 2, 3, 4, 5));
                break;

            case ADMIN_EMPLEADOS:
                //Buscar la coincidencia entre todos los indices
                sorter.setRowFilter(regexFilter(txt, 0, 1, 2, 3, 4, 5, 6, 7));
                break;
        }

        //Asignar el filtro a la tabla
        tabla.setRowSorter(sorter);
    }

    /**
     * Función para buscar un texto dentro de la tabla y enfocar su fila.
     *
     * @param txt Texto que será buscado dentro de la tabla
     */
    public void enfocarFila(String txt) {
        int row = -1;
        int index = -1;
        String msj = "La tabla seleccionada no puede enfocar algún dato.";

        //Determinar el index que será buscado y el mensaje de error
        switch (type) {
            case HISTORIAL_TRASVASO:
                index = 0;
                msj = "No se encontró la factura en los registros de trasvasos."
                        + "\nPor favor, actualice los datos y verifique la "
                        + "existencia de la deuda.";
                break;

            case VENTAS_PEDIDOS:
                index = 1;
                msj = "No se encontró el pedido en los registros.\nPor favor, "
                        + "actualice los datos y verifique la existencia de "
                        + "la deuda.";
                break;

            case CLIENTES:
                index = 1;
                msj = "No se encontró el cliente seleccionado.\nPor favor, "
                        + "actualice los datos y verifique la existencia del"
                        + "cliente.";
                break;

            case ADMIN_USUARIOS:
                index = 1;
                msj = "No se encontró el usuario seleccionado.\nPor favor, "
                        + "actualice los datos y verifique la existencia del"
                        + "usuario.";
                break;
        }
        //Validar que se obtuvo algún index
        if (index >= 0) {
            //Buscar el dato en la columna seleccionada de la tabla
            for (int i = 0; i < tabla.getRowCount(); i++) {
                //Obtener el id en cada iteración
                String columna = tabla.getValueAt(i, index).toString();

                //Validar si el id coincide con el id recibido
                if (columna.equals(txt)) {
                    //Guardar el índice de la fila y romper el cíclo
                    row = i;
                    break;
                }
            }
            //Comprobar que se seleccionó alguna fila
            if (row >= 0) {
                tabla.requestFocus();
                tabla.setRowSelectionInterval(row, row);
                tabla.setColumnSelectionInterval(0, tabla.getColumnCount() - 1);
            } else {
                msjError(msj);
            }
        } else {
            msjError(msj);
        }

    }

    /**
     * Función para obtener el apellido de un cliente, de la tabla, según una
     * cédula determinada
     *
     * @param cedula Cédula del cliente a buscar
     * @return Apellido del cliente encontrado
     */
    public String getClienteApellido(String cedula) {
        if (type == CLIENTES) {

            //Buscar todas las cédulas de los clientes en la tabla
            for (int i = 0; i < tabla.getRowCount(); i++) {
                //Obtener la cédula en cada iteración
                String ci = tabla.getValueAt(i, 1).toString();

                //Validar si la cédula coincide con la cédula recibida
                if (ci.equals(cedula)) {
                    //Retornar el apellido del cliente encontrado
                    return tabla.getValueAt(i, 3).toString();
                }
            }
            //Si el ciclo termina, implica que NO hubo coincidencia, por lo que
            //se muestra un mensaje de error
            msjError("No se encontró el cliente de la factura en los registros."
                    + "\nPor favor, actualice los datos y verifique la existencia"
                    + "del cliente en el sistema.");
        }
        return "";
    }

    //ATRIBUTOS FRONTEND
    private final int type;
    private TableRowSorter sorter;

    //COMPONENTES
    private Object[][] datos;
    private String cabecera[];
    private JTable tabla;
    private final JPopupMenu menuPopup = new JPopupMenu();
    private final JMenuItem itemEditar = new JMenuItem("Modificar");
    private final JMenuItem itemBorrar = new JMenuItem("Borrar");
    private JMenuItem itemTrasvaso;
    private JMenuItem itemVender;
    private JMenuItem itemBuscar;
    private JMenuItem itemRecarga;
    private JMenuItem itemCompra;
    private JMenuItem itemUbicar;
    private JMenuItem itemGoogleMaps;
    private JMenuItem itemInformacion;
    private JMenuItem itemContratar;
    private DefaultTableModel modelo;
}
