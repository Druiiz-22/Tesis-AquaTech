package tabs.historial;

import components.CampoTexto;
import javax.swing.JPanel;

/**
 * Clase para la creación del panel del historial de compras
 */
public class HistorialVentas extends JPanel implements properties.Constantes, properties.Colores{
        
    /**
     * Constructor del historial de compras
     */
    public HistorialVentas(){
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
    }
    
    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        txtBusqueda.setToolTipText("Ingrese cualquier texto para buscar alguna coincidencia en la tabla");
        this.add(txtBusqueda);
    }
    
    /**
     * Función para reposicionar y redimensionar los componentes
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
     * Función para vaciar todos los campos
     */ 
    protected void vaciarCampos(){
        txtBusqueda.setText("");
    }
    
    //COMPONENTES
    private static final CampoTexto txtBusqueda = new CampoTexto("Buscar Venta", CUALQUIER);
}
