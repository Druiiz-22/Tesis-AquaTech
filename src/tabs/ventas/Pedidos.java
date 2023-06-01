package tabs.ventas;

import components.CampoTexto;
import components.PanelNotificaciones;
import components.Tabla;
import components.map.PanelMap;
import database.ReadDB;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import main.Frame;
import main.MenuSuperior;
import static properties.Constantes.CUALQUIER;
import static properties.Fuentes.segoe;
import static properties.Mensaje.msjError;

/**
 * Clase para la creación del panel de los pedidos a domicilios actuales
 */
public class Pedidos extends JPanel implements properties.Colores, properties.Constantes {

    /**
     * Constructor del panel para los pedidos a domicilio
     */
    public Pedidos() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        listeners();
    }

    private void initComponents() {
        //Agregar el tooltiptext
        txtBusqueda.setToolTipText("Ingrese cualquier nombre para buscar alguna "
                + "coincidencia en la tabla");

        //Propiedades del CheckBox
        checkFiltrar.setSelected(true);
        checkFiltrar.setFont(segoe(16, PLANO));
        checkFiltrar.setForeground(NEGRO);
        checkFiltrar.setSize(checkFiltrar.getPreferredSize());
        checkFiltrar.setToolTipText("Filtrar los pedidos para que muestre solo "
                + "los que están activos.");

        //Agregar los componentes
        this.add(checkFiltrar);
        this.add(txtBusqueda);
        this.add(tabla);
        this.add(mapa);
    }

    private void listeners() {
        //KEY LISTENER
        txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                tabla.buscar(txtBusqueda.getText());
            }
        });
        checkFiltrar.addActionListener((e) -> {
            new Thread() {
                @Override
                public void run() {
                    //Seleccionar si se filtrará o no
                    Pedidos.pedidosFiltrados = checkFiltrar.isSelected();
                    //Abrir el GlassPane de carga
                    Frame.openGlass(0);
                    //Actualizar los datos
                    actualizarDatos();
                    //Cerrar el GlassPane de car
                    Frame.closeGlass();
                }
            }.start();
        });
    }

    protected void relocateComponents(int width, int height) {
        //Validar el tamaño del panel contenedor
        if (width < 700) {
            pequenio(width, height);

        } else {
            //Asignar el tamaño preferido
            this.setPreferredSize(new Dimension(width, height));
            grande();
        }
    }

    private void pequenio(int width, int height) {

        int w = width - padding * 3 - checkFiltrar.getWidth();
        txtBusqueda.setBounds(padding, padding, w, fieldHeight);

        int x = w + padding * 2;
        int y = padding + fieldHeight / 2 - checkFiltrar.getHeight() / 2;
        checkFiltrar.setLocation(x, y);

        w = width - padding * 2;
        y = padding * 2 + fieldHeight;
        int h = height - y - padding;
        tabla.setBounds(padding, y, w, h);

        y += h + padding;
        mapa.setBounds(padding, y, w, h);

        //Calcular la altura absoluta del panel contenedor, sumando todos los 
        //padding
        int absoluteHeight = padding * 4 + fieldHeight + h * 2;

        //Asignar el tamaño preferido al panel
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    private void grande() {
        int width = this.getPreferredSize().width;
        int height = this.getPreferredSize().height;

        int w = width / 2 - padding * 2;
        int x = width / 2 + padding;
        int h = height - padding * 2;
        mapa.setBounds(x, padding, w, h);

        w = x - padding * 3 - checkFiltrar.getWidth();
        txtBusqueda.setBounds(padding, padding, w, fieldHeight);

        x = w + padding * 2;
        int y = padding + fieldHeight / 2 - checkFiltrar.getHeight() / 2;
        checkFiltrar.setLocation(x, y);

        y = padding * 2 + fieldHeight;
        h = height - y - padding;
        w = width / 2 - padding;
        tabla.setBounds(padding, y, w, h);
    }

    protected void vaciarCampos() {
        txtBusqueda.setText("");
    }

    public static boolean actualizarDatos() {
        txtBusqueda.setText("");
        transferencias = ReadDB.getTransferencias();

        //Validar que la tabla y las transferencias hayan realizado sus busquedas
        //de manera exitosa
        if (tabla.actualizarDatos() && transferencias != null && mapa.getDirecciones()) {

            //Actualizar los puntos del mapa
            mapa.actualizarPuntos();

            //Obtener la cantidad de pedidos activos
            int count = tabla.getPedidosActivos();
            //Enviar la cantidad a las notificaciones
            PanelNotificaciones.setPedidos(count);
            MenuSuperior.setNotificationCount(count);

            //Retornar busqueda exitosa
            return true;

        } else {
            //Retornar busqueda incompleta
            return false;
        }
    }

    public static void enfocarPedido(String cedula) {
        tabla.enfocarFila(cedula);
    }

    public static void pagarPedido(String cedula) {
        int row = obtenerFila(cedula);

        if (row >= 0) {
            tabla.pagarPedido(row);
        }
    }

    public static void infoPedido(String cedula) {
        int row = obtenerFila(cedula);

        if (row >= 0) {
            infoPedido(row);
        }
    }

    public static void infoPedido(int index) {
        String tipoPago = tabla.getValueAt(index, 4).toString();

        if (!tipoPago.equals("TRNSF")) {
            info.mostrar(
                    tabla.getValueAt(index, 0),
                    tabla.getValueAt(index, 1),
                    tabla.getValueAt(index, 2),
                    tabla.getValueAt(index, 3),
                    tabla.getValueAt(index, 4),
                    tabla.getValueAt(index, 5),
                    tabla.getValueAt(index, 6)
            );
        } else {
            int row = -99;
            String pedido = tabla.getValueAt(index, 0).toString();

            for (int i = 0; i < transferencias.length; i++) {

                String id = transferencias[i][1].toString();

                if (id.equals(pedido)) {
                    row = i;
                }
            }
            if (row >= 0) {
                info.mostrar(
                        tabla.getValueAt(index, 0),
                        tabla.getValueAt(index, 1),
                        tabla.getValueAt(index, 2),
                        tabla.getValueAt(index, 3),
                        tabla.getValueAt(index, 4),
                        tabla.getValueAt(index, 5),
                        tabla.getValueAt(index, 6),
                        transferencias[row][2],
                        transferencias[row][3],
                        transferencias[row][4]
                );
            } else {
                msjError("No se pudo encontrar las transferencias realizadas por "
                        + "este cliente.\nPor favor, actualice los datos y "
                        + "vuelva a intentar.");
            }
        }
    }

    private static int obtenerFila(String cedula) {
        int row = -99;

        //Buscar todas las cédulas en la tabla de pedidos
        for (int i = 0; i < tabla.getRowCount(); i++) {
            //Obtener la cédula en cada iteración
            String ci = tabla.getValueAt(i, 1).toString();

            //Validar si la cédula coincide con la cédula recibida
            if (ci.equals(cedula)) {
                //Guardar el índice de la fila y romper el cíclo
                row = i;
                break;
            }
        }
        //Comprobar que se seleccionó alguna fila
        if (row >= 0) {
            return row;
        } else {
            msjError("No se encontró coincidencia entre la cédula del punto "
                    + "y los pedidos.\nPor favor, actualice los datos y "
                    + "verifique la existencia del pedido.");
        }
        return row;
    }

    protected void habilitarComponents(boolean estado) {
        txtBusqueda.setEnabled(estado);
    }

    public static boolean isPedidosFiltrados() {
        return pedidosFiltrados;
    }

    //COMPONENTES
    private static final CampoTexto txtBusqueda = new CampoTexto("Buscar Cliente", CUALQUIER);
    private static final Tabla tabla = new Tabla(VENTAS_PEDIDOS);
    private static final PanelMap mapa = new PanelMap();
    private static final JCheckBox checkFiltrar = new JCheckBox("Mostrar pedidos activos");

    //ATRIBUTOS
    private static boolean pedidosFiltrados = true;
    private static final int fieldHeight = 40;
    private static final int padding = 20;
    private static final PedidoInformacion info = new PedidoInformacion();
    private static Object[][] transferencias;
}
