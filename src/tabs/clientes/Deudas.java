package tabs.clientes;

import components.CampoTexto;
import components.PanelNotificaciones;
import components.Tabla;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import main.MenuSuperior;
import static properties.Colores.NEGRO;
import static properties.Constantes.PLANO;
import static properties.Fuentes.segoe;

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

        //Propiedades del CheckBox
        checkFiltrar.setSelected(true);
        checkFiltrar.setFont(segoe(16, PLANO));
        checkFiltrar.setForeground(NEGRO);
        checkFiltrar.setSize(checkFiltrar.getPreferredSize());
        checkFiltrar.setToolTipText("Filtrar las deudas para que muestre solo "
                + "los que están pendientes.");
        
        //Agregar los componentes
        this.add(checkFiltrar);
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
        
        checkFiltrar.addActionListener((e) -> {
            Deudas.deudasFiltradas = checkFiltrar.isSelected();
            main.Frame.openGlass(0);
            actualizarDatos();
            main.Frame.closeGlass();
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
        
        int w = this.getWidth() - padding * 3 - checkFiltrar.getWidth();
        txtBusqueda.setBounds(padding, padding, w, fieldHeight);

        int x = w + padding * 2;
        int y = padding + fieldHeight/2 - checkFiltrar.getHeight()/2;
        checkFiltrar.setLocation(x, y);

        y = fieldHeight + padding * 2;
        int h = size.height - y - padding;
        w = size.width - padding * 2;
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
            //Obtener la cantidad de deudas activas
            int count = tabla.getDeudasActivas();
            
            //Asignar la cantidad a las notificaciones
            PanelNotificaciones.setDeudas(count);
            
            //Asignar la cantidad de deudas al menu superior
            MenuSuperior.setNotificationCount(count);
            
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

    protected void habilitarComponents(boolean estado){
        txtBusqueda.setEnabled(estado);
    }
    
    public static boolean isDeudasFiltradas(){
        return Deudas.deudasFiltradas;
    }
    
    //COMPONENTES
    private static boolean deudasFiltradas = true;
    private static final JCheckBox checkFiltrar = new JCheckBox("Mostrar deudas pendientes");
    private static final CampoTexto txtBusqueda = new CampoTexto("Buscar Cliente", CUALQUIER);
    private static final Tabla tabla = new Tabla(DEUDAS);
}
