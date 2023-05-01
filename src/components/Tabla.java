package components;

import database.AdminDB;
import java.awt.event.ActionEvent;
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
import tabs.Proveedores;
import tabs.clientes.PanelClientes;
import static java.awt.Font.PLAIN;
import static javax.swing.RowFilter.regexFilter;
import static properties.Mensaje.msjYesNo;
import static properties.Mensaje.msjError;
import static properties.Colores.NEGRO;
import static properties.Fuentes.segoe;
import static properties.Mensaje.msjAdvertencia;
import static properties.Mensaje.msjInformativo;
import tabs.admin.Usuarios;
import tabs.historial.HistorialTrasvasos;

public class Tabla extends JScrollPane implements properties.Constantes {

    // ========== BACKEND ==========
    /**
     * Función para aplicar los listeners a los items del menú
     */
    private void listeners() {

        //ACTION LISTENER A LOS ITEMS
        //Determinar el tipo de la tabla
        switch (type) {
            case CLIENTES:
                clientesListeners();
                break;

            case PROVEEDOR:
                provListeners();
                break;

            case DEUDAS:
                deudasListeners();
                break;

            case ADMIN_USUARIOS:
                itemBorrar.addActionListener((ActionEvent e) -> {
                    eliminar();
                });
                itemEditar.addActionListener((ActionEvent e) -> {
                    editar();
                });
                break;
        }
    }

    private void clientesListeners() {
        itemTrasv.addActionListener((ActionEvent e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {

                Object cedula = tabla.getValueAt(index, 0);
                Object apellido = tabla.getValueAt(index, 2);

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

        itemVend.addActionListener((ActionEvent e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                Object cedula = tabla.getValueAt(index, 0);
                Object apellido = tabla.getValueAt(index, 2);

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

        itemBorrar.addActionListener((ActionEvent e) -> {
            eliminar();
        });

        itemEditar.addActionListener((ActionEvent e) -> {
            editar();
        });
    }

    private void provListeners() {
        itemRecar.addActionListener((ActionEvent e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                Object rif = tabla.getValueAt(index, 0);
                Object nombre = tabla.getValueAt(index, 1);

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

        itemCompr.addActionListener((ActionEvent e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {
                Object rif = tabla.getValueAt(index, 0);
                Object nombre = tabla.getValueAt(index, 1);

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

        itemBorrar.addActionListener((ActionEvent e) -> {
            eliminar();
        });

        itemEditar.addActionListener((ActionEvent e) -> {
            editar();
        });
    }

    private void deudasListeners() {
        itemFactura.addActionListener((ActionEvent e) -> {
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
        itemTrasv.addActionListener((ActionEvent e) -> {
            //Obtener el index de la fila seleccionada en la tabla
            int index = tabla.getSelectedRow();
            if (validarSelect(index)) {

                Object cedula = tabla.getValueAt(index, 0);
                Object apellido = tabla.getValueAt(index, 2);

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
                try {
                    //Obtener la identificación del cliente o proveedor
                    Object dni = tabla.getValueAt(index, 0);

                    //Comprobar el tipo de la tabla
                    switch (type) {
                        case CLIENTES:
                            //Intentar eliminarlo de la base de datos
                            if (DeleteDB.removeCliente(dni)) {

                                //Ya que no es posible eliminar una fila de una tabla
                                //sin acceder a su Model, al eliminar la final en la
                                //base de datos, se actualizará la tabla
                                actualizarDatos();

                                msjInformativo("Se eliminó al cliente con éxito.");

                                Ventas.vaciarCampos();
                            }
                            break;

                        case PROVEEDOR:
                            //Intentar eliminarlo de la base de datos
                            if (DeleteDB.removeProveedor(dni)) {
                                //Ya que no es posible eliminar una fila de una tabla
                                //sin acceder a su Model, al eliminar la final en la
                                //base de datos, se actualizará la tabla
                                actualizarDatos();

                                msjInformativo("Se eliminó el proveedor con éxito.");

                                Compras.vaciarCampos();
                            }
                            break;

                        case ADMIN_USUARIOS:
                            Object cedula = tabla.getValueAt(index, 1);

                            //Validar el rol de administrador y su clave para
                            //intentar eliminar el usuario
                            if (AdminDB.validateAdminUser()) {
                                if (DeleteDB.removeUsuario(dni, cedula)) {
                                    //Ya que no es posible eliminar una fila de una tabla
                                    //sin acceder a su Model, al eliminar la final en la
                                    //base de datos, se actualizará la tabla
                                    actualizarDatos();

                                    msjInformativo("Se eliminó al usuario con éxito.");

                                    Usuarios.vaciarCampos();
                                }
                            }

                            break;
                    }
                } catch (Exception e) {
                    msjError("No se pudo eliminar el cliente.\nError: " + e);
                }
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
                    Object cedula = tabla.getValueAt(index, 0);
                    Object nombre = tabla.getValueAt(index, 1);
                    Object apellido = tabla.getValueAt(index, 2);
                    Object telefono = tabla.getValueAt(index, 3);
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
                    Object rif = tabla.getValueAt(index, 0);
                    Object nombre = tabla.getValueAt(index, 1);
                    Object telefono = tabla.getValueAt(index, 2);
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
                    Object rol = tabla.getValueAt(index, 2);
                    Object nombre = tabla.getValueAt(index, 3);
                    Object apellido = tabla.getValueAt(index, 4);
                    Object telefono = tabla.getValueAt(index, 5);
                    Object correo = tabla.getValueAt(index, 6);

                    //Enviar el usuario a la pestaña de usuarios, que será 
                    //enviado a la ventana de nuevos usuarios para su edición
                    Usuarios.editUsuario(
                            cedula.toString(),
                            rol.toString(),
                            nombre.toString(),
                            apellido.toString(),
                            telefono.toString(),
                            correo.toString()
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
     */
    public void actualizarDatos() {
        //Matriz para guardar los datos retornados de la base de datos
        Object[][] datos = new Object[][]{{""}};

        //Determinar el tipo de la tabla y buscar sus registros en la base de datos
        switch (type) {
            case CLIENTES:
                datos = ReadDB.getClientes();
                break;

            case PROVEEDOR:
                datos = ReadDB.getProveedores();
                break;

            case DEUDAS:
                datos = ReadDB.getDeudas();
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
        }

        //Eliminar todos los componentes de la tabla
        tabla.removeAll();

        //Insertar un modelo nuevo en blanco ineditable
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //Insertar su header
        modelo.setColumnIdentifiers(cabecera);

        //Insertar todos los datos en el modelo de la tabla
        for (Object[] row : datos) {
            modelo.addRow(row);
        }

        //Insertar el modelo en la tabla
        tabla.setModel(modelo);
    }

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
                cabecera = new String[]{"Cedula", "Nombre", "Apellido", "Telefono"};
                break;

            case PROVEEDOR:
                cabecera = new String[]{"RIF", "Nombre", "Telefono"};
                break;

            case DEUDAS:
                cabecera = new String[]{"id", "Factura", "Cedula", "Debe pagar", "Debemos dar", "Fecha"};
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
                    "Monto Total", "Fecha"};
                break;

            case HISTORIAL_COMPRA:
                cabecera = new String[]{"ID", "RIF", "Proveedor", "Cantidad",
                    "Monto Total", "Fecha"};
                break;

            case ADMIN_USUARIOS:
                cabecera = new String[]{"ID", "Cedula", "Rol", "Nombre", "Apellido", "Telefono", "Correo"};
                break;
        }
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {

        //Instanciar la tabla con el modelo
        this.tabla = new JTable();

        //Insertar la tabla en la clase de scroll
        this.setViewportView(tabla);

        //Crear el menú según el tipo de tabla
        switch (type) {
            case CLIENTES:
                clientesMenu();
                listeners();
                break;

            case PROVEEDOR:
                provMenu();
                listeners();
                break;

            case DEUDAS:
                deudasMenu();
                listeners();
                break;

            case ADMIN_USUARIOS:
                usuariosMenu();
                listeners();
                break;
        }
    }

    /**
     * Función para iniciar el menú para proveedores
     */
    private void provMenu() {
        //Propiedades de los items del menú
        itemEditar.setFont(segoe(18, PLAIN));
        itemEditar.setForeground(NEGRO);
        itemBorrar.setFont(segoe(18, PLAIN));
        itemBorrar.setForeground(NEGRO);

        itemRecar = new JMenuItem("Seleccionar para recargar");
        itemRecar.setFont(segoe(18, PLAIN));
        itemRecar.setForeground(NEGRO);

        itemCompr = new JMenuItem("Seleccionar para comprarle");
        itemCompr.setFont(segoe(18, PLAIN));
        itemCompr.setForeground(NEGRO);

        try {
            //Buscar la imagen de cada item
            itemEditar.setIcon(getMenuIcon("editar"));
            itemBorrar.setIcon(getMenuIcon("borrar"));
            itemRecar.setIcon(getMenuIcon("recargar"));
            itemCompr.setIcon(getMenuIcon("comprar"));

        } catch (Exception e) {
            msjAdvertencia("No se pudo cargar los íconos de un menú desplegable.\n"
                    + "El software seguirá funcionando sin los íconos.");

        } finally {

            //Añadir los items al menú
            menuPopup.add(itemRecar);
            menuPopup.add(itemCompr);

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
        itemEditar.setFont(segoe(18, PLAIN));
        itemEditar.setForeground(NEGRO);
        itemBorrar.setFont(segoe(18, PLAIN));
        itemBorrar.setForeground(NEGRO);

        itemTrasv = new JMenuItem("Seleccionar para trasvasarle");
        itemTrasv.setFont(segoe(18, PLAIN));
        itemTrasv.setForeground(NEGRO);

        itemVend = new JMenuItem("Seleccionar para venderle");
        itemVend.setFont(segoe(18, PLAIN));
        itemVend.setForeground(NEGRO);

        try {
            //Buscar la imagen de cada item
            itemEditar.setIcon(getMenuIcon("editar"));
            itemBorrar.setIcon(getMenuIcon("borrar"));
            itemTrasv.setIcon(getMenuIcon("trasvasar"));
            itemVend.setIcon(getMenuIcon("vender"));

        } catch (Exception e) {
            msjAdvertencia("No se pudo cargar los íconos de un menú desplegable.\n"
                    + "El software seguirá funcionando sin los íconos.");

        } finally {

            //Añadir los items al menú
            menuPopup.add(itemTrasv);
            menuPopup.add(itemVend);

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
        itemEditar.setFont(segoe(18, PLAIN));
        itemEditar.setForeground(NEGRO);

        itemBorrar.setFont(segoe(18, PLAIN));
        itemBorrar.setForeground(NEGRO);

        try {
            //Buscar la imagen de cada item
            itemEditar.setIcon(getMenuIcon("editar"));
            itemBorrar.setIcon(getMenuIcon("borrar"));

        } catch (Exception e) {
            msjAdvertencia("No se pudo cargar los íconos de un menú desplegable.\n"
                    + "El software seguirá funcionando sin los íconos.");

        } finally {

            //Añadir los items al menú
            menuPopup.add(itemEditar);
            menuPopup.add(itemBorrar);

            tabla.setComponentPopupMenu(menuPopup);
        }
    }

    private void deudasMenu() {
        itemBorrar.setText("Cancelar deuda");
        itemBorrar.setFont(segoe(18, PLAIN));
        itemBorrar.setForeground(NEGRO);

        itemTrasv = new JMenuItem("Pagar deuda");
        itemTrasv.setFont(segoe(18, PLAIN));
        itemTrasv.setForeground(NEGRO);

        itemFactura = new JMenuItem("Ver factura");
        itemFactura.setFont(segoe(18, PLAIN));
        itemFactura.setForeground(NEGRO);

        try {
            //Buscar la imagen de cada item
            itemBorrar.setIcon(getMenuIcon("borrar"));
            itemTrasv.setIcon(getMenuIcon("vender"));
            itemFactura.setIcon(getMenuIcon("factura"));

        } catch (Exception e) {
            msjAdvertencia("No se pudo cargar los íconos de un menú desplegable.\n"
                    + "El software seguirá funcionando sin los íconos.");

        } finally {

            //Añadir los items al menú
            menuPopup.add(itemFactura);
            menuPopup.add(itemTrasv);
            menuPopup.addSeparator();
            menuPopup.add(itemBorrar);

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
        int size = 22;

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
        }

        //Asignar el filtro a la tabla
        tabla.setRowSorter(sorter);
    }

    public void focusRow(String txt){
        if(type == HISTORIAL_TRASVASO){
            
            int row = -99;
            
            //Buscar todos los id de la factura en la tabla de trasvasos
            for (int i = 0; i < tabla.getRowCount(); i++) {
                //Obtener el id en cada iteración
                String id = tabla.getValueAt(i, 0);
                //Validar si el id coincide con el id recibido
                if(id.equals(txt)){
                    //Guardar el índice de la fila y romper el cíclo
                    row = i;
                    break;
                }
            }
            
            //Comprobar que se seleccionó alguna fila
            if(row >= 0){
                tabla.requestFocus();
                tabla.setRowSelectionInterval(row, row);
                tabla.setColumnSelectionInterval(0, tabla.getColumnCount()-1);
            } else {
                msjError("No se encontró la factura en los registros de "
                        + "trasvasos.\nPor favor, actualice los datos y verifique"
                        + " la existencia de la deuda.");
            }
        }
    }
    
    public String getClienteApellido(String cedula){
        if(type == CLIENTES){
            
        }
    }
    
    //ATRIBUTOS FRONTEND
    private final int type;
    private TableRowSorter sorter;

    //COMPONENTES
    private String cabecera[];
    private JTable tabla;
    private final JPopupMenu menuPopup = new JPopupMenu();
    private final JMenuItem itemEditar = new JMenuItem("Modificar");
    private final JMenuItem itemBorrar = new JMenuItem("Borrar");
    private JMenuItem itemTrasv;
    private JMenuItem itemVend;
    private JMenuItem itemFactura;
    private JMenuItem itemRecar;
    private JMenuItem itemCompr;
    private DefaultTableModel modelo;
}
