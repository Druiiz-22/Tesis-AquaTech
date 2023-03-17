package tabs.clientes;

import components.CampoTexto;
import javax.swing.JPanel;

/**
 * Clase para la creación del panel con las deudas pendientes que posean los
 * clientes registrados en el sistema
 */
public class Deudas extends JPanel implements properties.Colores, properties.Constantes {

    /**
     * Constructor del panel de deudas
     */
    public Deudas() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        mouseListeners();
    }

    /**
     * Función para inicar los componentes
     */
    private void initComponents() {

        //Agregar el tooltiptext
        txtBusqueda.setToolTipText("Ingrese cualquier nombre para buscar alguna coincidencia en la tabla");

        //Agregar los componentes
        this.add(txtBusqueda);
    }

    /**
     * Función para asignar los mouse listener a los componentes
     */
    private void mouseListeners() {

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
    }

    /**
     * Función para vaciar los campos
     */
    protected void vaciarCampos(){
        txtBusqueda.setText("");
    }
    
    //COMPONENTES
    private static final CampoTexto txtBusqueda = new CampoTexto("Buscar Cliente", CUALQUIER);
}
