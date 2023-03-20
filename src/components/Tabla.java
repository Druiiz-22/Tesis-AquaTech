package components;

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
import static java.awt.Font.PLAIN;
import static javax.swing.RowFilter.regexFilter;
import static properties.Mensaje.msjYesNo;
import static properties.Mensaje.msjError;
import static properties.Colores.NEGRO;
import static properties.Fuentes.segoe;
import static properties.Mensaje.msjAdvertencia;

public class Tabla extends JScrollPane implements properties.Constantes {

    // ========== BACKEND ==========
    /**
     * Función para aplicar los listeners a los items del menú
     */
    private void listeners() {
        
        //ACTION LISTENER A LOS ITEMS
        itemBorrar.addActionListener((ActionEvent e) -> {
            eliminar();
        });

        itemEditar.addActionListener((ActionEvent e) -> {
            editar();
        });

        //Determinar el tipo de la tabla
        if (type == CLIENTES) {

            itemTrasv.addActionListener((ActionEvent e) -> {
                if (validarSelect()) {

                    Object cedula = modelo.getValueAt(tabla.getSelectedRow(), 0);
                    Object apellido = modelo.getValueAt(tabla.getSelectedRow(), 2);

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
                if (validarSelect()) {
                    Object cedula = modelo.getValueAt(tabla.getSelectedRow(), 0);
                    Object apellido = modelo.getValueAt(tabla.getSelectedRow(), 2);

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

        } else if (type == PROVEEDOR) {
            itemRecar.addActionListener((ActionEvent e) -> {
                if (validarSelect()) {
                    Object rif = modelo.getValueAt(tabla.getSelectedRow(), 0);
                    Object nombre = modelo.getValueAt(tabla.getSelectedRow(), 1);

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
                if (validarSelect()) {
                    Object rif = modelo.getValueAt(tabla.getSelectedRow(), 0);
                    Object nombre = modelo.getValueAt(tabla.getSelectedRow(), 1);

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
        
        if (validarSelect()) {
            
            //Mensaje de confirmación
            if (msjYesNo("¿Está seguro de borrar el registro seleccionado?")) {
                try {
                    //Obtener la identificación del cliente o proveedor
                    Object dni = modelo.getValueAt(tabla.getSelectedRow(), 0);

                    //Eliminarlo de la base de datos
                    if (type == CLIENTES) {
                        DeleteDB.removeCliente(dni);

                    } else if (type == PROVEEDOR) {
                        DeleteDB.removeProveedor(dni);
                    }

                    //Eliminarlo de la tabla
                    modelo.removeRow(tabla.getSelectedRow());

                } catch (Exception e) {
                    msjError("No se pudo eliminar el cliente.\nError: " + e);
                }
            }
        }
    }

    /**
     * Función para editar una fila
     */
    public void editar() {
        if (validarSelect()) {

        }
    }

    /**
     * Función para validar que se haya seleccionado 1 sola fila
     *
     * @return
     */
    private boolean validarSelect() {
        //Obtener el indice del item seleccionado
        int index = tabla.getSelectedRow();
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
        Object[][] datos;

        //Determinar el tipo de la tabla y buscar sus registros en la base de datos
        switch (type) {
            case CLIENTES:
                datos = ReadDB.getClientes();
                break;

            case PROVEEDOR:
                datos = ReadDB.getProveedores();
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
                datos = ReadDB.getCompras();
                break;

            default:
                datos = new Object[][]{{""}};
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
    public Tabla(int type) {
        //Guardar el tipo
        this.type = type;

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
                cabecera = new String[]{"Cedula", "Nombre", "Apellido", "Telefono", "Direccion"};
                break;

            case PROVEEDOR:
                //Establecer las columnas de la tabla
                cabecera = new String[]{"RIF", "Nombre", "Telefono", "Direccion"};

                break;
            case HISTORIAL_TRASVASO:
                //Establecer las columnas de la tabla
                cabecera = new String[]{"ID", "Cedula", "Pagados", "Entregados",
                    "Pago", "Delivery", "Monto Total", "Fecha"};

                break;
            case HISTORIAL_RECARGA:
                //Establecer las columnas de la tabla
                cabecera = new String[]{"ID", "RIF", "Proveedor", "Cantidad",
                    "Monto Total", "Fecha"};

                break;
            case HISTORIAL_VENTA:
                //Establecer las columnas de la tabla
                cabecera = new String[]{"ID", "Cedula", "Cantidad", "Tipo pago",
                    "Monto Total", "Fecha"};
                break;
            case HISTORIAL_COMPRA:
                //Establecer las columnas de la tabla
                cabecera = new String[]{"ID", "RIF", "Proveedor", "Cantidad",
                    "Monto Total", "Fecha"};
                break;
            case ADMIN_USUARIOS:
                cabecera = new String[]{"ID", "Cedula", "Rol", "Correo"};
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
        if (type == CLIENTES) {
            initClienteMenu();
            listeners();

        } else if (type == PROVEEDOR) {
            initProvMenu();
            listeners();
        }
    }

    /**
     * Función para iniciar el menú para proveedores
     */
    private void initProvMenu() {
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
            itemEditar.setIcon(getImageIcon("editar"));
            itemBorrar.setIcon(getImageIcon("borrar"));
            itemRecar.setIcon(getImageIcon("recargar"));
            itemCompr.setIcon(getImageIcon("comprar"));

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
    private void initClienteMenu() {
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
            itemEditar.setIcon(getImageIcon("editar"));
            itemBorrar.setIcon(getImageIcon("borrar"));
            itemTrasv.setIcon(getImageIcon("trasvasar"));
            itemVend.setIcon(getImageIcon("vender"));

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
     * Función para buscar los íconos de los items
     *
     * @param name Nombre del ícon
     * @return ImageIcon del item
     */
    private ImageIcon getImageIcon(String name) {
        //Variable para las imagenes
        ImageIcon img = new ImageIcon(getClass().getResource("/icons/popup/" + name + ".png"));

        //Determinar el tamaño de la imagen.
        int size = 22;

        //Retornar la imagen escalada
        return new ImageIcon(img.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
    }

    /**
     * <h2>Función para agregar una fila a la tabla</h2>
     * <p>
     * El arreglo con los datos, deberá tener un tamaño determinado según la
     * cantidad de columnas de la tabla.</p>
     * <ul>
     * <li>Clientes: 5 columnas</li>
     * <li>Proveedor: 4 columnas</li>
     * <li>Historial Trasvaso: 8 columnas</li>
     * <li>Historial Recarga: 7 columnas</li>
     * <li>Historial Venta: 6 columnas</li>
     * <li>Historial Compra: 7 columnas</li>
     * </ul>
     *
     * @param row Fila con los datos
     */
    public void agregar(String[] row) {
        modelo.addRow(row);
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

        //Asignar el modelo de tabla (cada vez que se llame la función)
        //al filtro
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
    private JMenuItem itemRecar;
    private JMenuItem itemCompr;
    private DefaultTableModel modelo;
}
