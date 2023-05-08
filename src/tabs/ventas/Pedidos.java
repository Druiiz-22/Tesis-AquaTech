package tabs.ventas;

import components.CampoTexto;
import components.PanelNotificaciones;
import components.Tabla;
import components.map.PanelMap;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import main.MenuSuperior;
import static properties.Constantes.CUALQUIER;

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
        
        //PENDIENTE
        // - Mostrar el PopUpMenu en el mapa con cualquier click
        // - Buscar los registros del mapa a la tabla
        // - Abrir una ventana con la información detallada del pedido seleccionado
        
    }

    private void initComponents() {
        //Agregar el tooltiptext
        txtBusqueda.setToolTipText("Ingrese cualquier nombre para buscar alguna coincidencia en la tabla");

        //Agregar los componentes
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
        int w = width - padding*2;
        txtBusqueda.setBounds(padding, padding, w, fieldHeight);
        
        int y = padding*2 + fieldHeight;
        int h = height - y - padding;
        tabla.setBounds(padding, y, w, h);
        
        y += h + padding;
        mapa.setBounds(padding, y, w, h);
        
        //Calcular la altura absoluta del panel contenedor, sumando todos los 
        //padding
        int absoluteHeight = padding * 4 + fieldHeight + h*2;

        //Asignar el tamaño preferido al panel
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    private void grande() {
        int width = this.getPreferredSize().width;
        int height = this.getPreferredSize().height;
        
        int w = width/2 - padding*2;
        int x = width/2 + padding;
        int h = height - padding*2;
        mapa.setBounds(x, padding, w, h);
        
        w = width/2 - padding;
        txtBusqueda.setBounds(padding, padding, w, fieldHeight);
        
        int y = padding*2 + fieldHeight;
        h = height - y - padding;
        tabla.setBounds(padding, y, w, h);
    }

    public static void actualizarDatos() {
        txtBusqueda.setText("");
        tabla.actualizarDatos();
        mapa.actualizarPuntos();
        
        int rows = tabla.getRowCount();
        PanelNotificaciones.setPedidos(rows);
        MenuSuperior.setNotificationCount(rows);
    }
    
    public static Object[][] getTable(){
        return tabla.getPedidosTabla();
    }

    protected void vaciarCampos() {
        txtBusqueda.setText("");
    }

    //COMPONENTES
    private static final CampoTexto txtBusqueda = new CampoTexto("Buscar Cliente", CUALQUIER);
    private static final Tabla tabla = new Tabla(VENTAS_PEDIDOS);
    private static final PanelMap mapa = new PanelMap();

    //ATRIBUTOS
    private static final int fieldHeight = 40;
    private static final int padding = 20;
}
