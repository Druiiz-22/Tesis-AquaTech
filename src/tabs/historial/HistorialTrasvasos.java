package tabs.historial;

import components.CampoTexto;
import components.Tabla;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

/**
 * Clase para la creación del panel del historial de compras
 */
public class HistorialTrasvasos extends JPanel implements properties.Constantes, properties.Colores {

    /**
     * Constructor del historial de compras
     */
    public HistorialTrasvasos() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();

        listener();
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        txtBusqueda.setToolTipText("Ingrese cualquier texto para buscar alguna coincidencia en la tabla");

        this.add(txtBusqueda);
        this.add(tabla);
    }

    /**
     * Función para reposicionar y redimensionar los componentes
     *
     * @param size Tamaño del parent contenedor
     */
    protected void relocateComponents(java.awt.Dimension size) {
        this.setSize(size);

        int padding = 20;
        int fieldHeight = 40;
        int fieldWidth = this.getWidth() - padding * 2;

        txtBusqueda.setBounds(padding, padding, fieldWidth, fieldHeight);

        int y = fieldHeight + padding * 2;
        int h = size.height - y - padding;
        int w = size.width - padding * 2;

        tabla.setBounds(padding, y, w, h);
    }

    /**
     * Función para vaciar todos los campos
     */
    protected static void vaciarCampos() {
        txtBusqueda.setText("");
    }

    /**
     * Función para actualizar los datos de la tabla del historial
     * @return 
     */
    protected static boolean actualizarDatos() {
        return tabla.actualizarDatos();
    }

    /**
     * Función para asignar los listeners a los componentes
     */
    private void listener() {
        //KEY LISTENER
        txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                tabla.buscar(txtBusqueda.getText());
            }
        });
    }

    /**
     * Función para acceder a la tabla de la clase y buscar una factura entre
     * los registros de los trasvasos
     *
     * @param factura ID de la factura a buscar
     */
    public static void buscarFactura(String factura) {
        //Asignar el texto en la barra de busqueda para filtrar
        txtBusqueda.setText(factura);

        //Filtrar la tabla
        tabla.buscar(factura);

        //Enfocar la tabla con la factura enviada
        tabla.enfocarFila(factura);
    }

    protected void habilitarComponents(boolean estado){
        txtBusqueda.setEnabled(estado);
    }
    
    //COMPONENTES
    private static final CampoTexto txtBusqueda = new CampoTexto("Buscar Trasvaso", CUALQUIER);
    private static final Tabla tabla = new Tabla(HISTORIAL_TRASVASO);
}
