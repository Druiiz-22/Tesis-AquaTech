package tabs.clientes;

import components.CampoTexto;
import components.PanelNotificaciones;
import components.Tabla;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import main.MenuSuperior;

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
        listeners();
    }

    /**
     * Función para inicar los componentes
     */
    private void initComponents() {
        //Agregar el tooltiptext
        txtBusqueda.setToolTipText("Ingrese cualquier nombre para buscar alguna coincidencia en la tabla");

        //Agregar los componentes
        this.add(txtBusqueda);
        this.add(tabla);
    }

    /**
     * Función para asignar los mouse listener a los componentes
     */
    private void listeners() {
        //KEY LISTENER
        txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                tabla.buscar(txtBusqueda.getText());
            }
        });
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
     * Función para actualizar los datos de la tabla del historial
     * @return 
     */
    public static boolean actualizarDatos() {
        txtBusqueda.setText("");
        boolean busqueda = tabla.actualizarDatos();
        
        //Validar que la busqueda fue exitosa
        if(busqueda){
            //Obtener la cantidad de filas de deudas
            int rows = tabla.getRowCount();
            //Asignar la cantidad a las notificaciones
            PanelNotificaciones.setDeudas(rows);
            //Asignar la cantidad de deudas al menu superior
            MenuSuperior.setNotificationCount(rows);
            
            //Retornar busqueda exitosa
            return true;
            
        } else{
            //Retornar busqueda incompleta
            return false;
        }
    }
    
    /**
     * Función para vaciar los campos
     */
    protected static void vaciarCampos() {
        txtBusqueda.setText("");
    }

    //COMPONENTES
    private static final CampoTexto txtBusqueda = new CampoTexto("Buscar Cliente", CUALQUIER);
    private static final Tabla tabla = new Tabla(DEUDAS);
}
