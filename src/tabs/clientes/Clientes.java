package tabs.clientes;

import components.Boton;
import components.CampoTexto;
import components.Label;
import java.awt.Cursor;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Función para la creación del apartado de los clientes, esta clase 
 * contiene el menú de navegación superior y el panel contenedor
 * 
 */
public class Clientes extends JPanel implements properties.Constantes, properties.Colores {

    /**
     * Constructor para crear el panel de clientes
     */
    public Clientes() {
        this.setLayout(null);
        this.setOpaque(false);
        
        initComponents();
        mouseListeners();
    }
    
    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {

        //Activar el botón de clientes
        btnClientes.setForeground(AZUL_PRINCIPAL);
        
        //Asignar el cursor de mano a los botones
        btnClientes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDeudas.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Agregar los tooltiptext a los botones
        btnClientes.setToolTipText("Ver la lista de los clientes registrados en el sistema");
        btnDeudas.setToolTipText("Ver la lista de los clientes con deudas pendientes");

        //BARRA DEL MENÚ
        menu.setBackground(GRIS);
        menu.add(btnClientes);
        menu.add(btnDeudas);

        //PANEL CONTENEDOR
        //Asignar el CardLayout para navegar entre
        //los distintos paneles
        contenedor.setLayout(card);
        contenedor.add(panelClientes, "1");
        contenedor.add(panelDeudas, "2");
        card.show(contenedor, "1");
        
        //Agregar el menú y contenedor 
        //al panel de la clase
        this.add(menu);
        this.add(contenedor);
    }

    /**
     * Función para asignar los mouse listener a los componentes
     */
    private void mouseListeners() {
        btnClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                replacePanel(CLIENTES);
            }
        });
        btnDeudas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                replacePanel(DEUDAS);
            }
        });
    }

    /**
     * Función para reposicionar los componentes según el 
     * tamaño del parent
     * @param size Tamaño del parent contenedor
     */
    public void relocateComponents(java.awt.Dimension size) {
        this.setSize(size);

        menu.setSize(size.width, menu.getPreferredSize().height);

        int contY = menu.getHeight();
        contenedor.setLocation(0, contY);
        contenedor.setSize(size.width, size.height - contY);
        
        //Comprobar qué botón está presionado para cambiar
        //el tamaño de su panel
        panelClientes.relocateComponents(contenedor.getSize());
        panelDeudas.relocateComponents(contenedor.getSize());
        
    }

    /**
     * Función para navegar entre los paneles dentro de clientes
     * @param type panel que será mostrado
     */
    public static void replacePanel(int type) {

        //Activar el botón, según el tipo
        btnClientes.setForeground((type == CLIENTES) ? AZUL_PRINCIPAL : NEGRO);
        btnDeudas.setForeground((type == DEUDAS) ? AZUL_PRINCIPAL : NEGRO);

        //Mostrar el panel, según el tipo
        card.show(contenedor, (type == CLIENTES) ? "1" : "2");

    }

    /**
     * Función para vaciar clientes y deudas
     */
    public void vaciarCampos(){
        panelClientes.vaciarCampos();
        panelDeudas.vaciarCampos();
    }
    
    //COMPONENTES
    private static final JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 5));
    private static final JPanel contenedor = new JPanel();
    private static final CardLayout card = new CardLayout();
    private static final Label btnClientes = new Label("Clientes", TITULO, 18);
    private static final Label btnDeudas = new Label("Deudas", TITULO, 18);
    private static final PanelClientes panelClientes = new PanelClientes();
    private static final Deudas panelDeudas = new Deudas();

}

/**
 * Clase para la creación del panel de registro de los clientes en el sistema
 */
class PanelClientes extends JPanel implements properties.Colores, properties.Constantes {

    /**
     * Constructor del panel de registro de clientes
     */
    public PanelClientes() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        mouseListeners();
    }

    /**
     * Función para iniciar sus componentes
     */
    private void initComponents() {
        btnAgregar.setToolTipText("Ir a la ventana para el registro de un nuevo cliente al sistema");
        txtBusqueda.setToolTipText("Ingrese cualquier nombre para buscar alguna coincidencia en la tabla");

        this.add(txtBusqueda);
        this.add(btnAgregar);
    }
    /**
     * Función para asignar los mouse listener a los componentes
     */
    private void mouseListeners() {
        btnAgregar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

            }
        });
    }
    /**
     * Función para reposicionar y redimensionar los componentes
     * @param size Tamaño del parent contenedor
     */
    protected void relocateComponents(java.awt.Dimension size) {
        this.setSize(size);

        int padding = 20;
        int fieldHeight = 40;

        //Posición y tamaño cuando el panel sea muy pequeño
        if (size.width < 500) {
            int fieldWidth = this.getWidth() - padding * 2;
            int txtY = padding * 2 + fieldHeight;

            btnAgregar.setBounds(padding, padding, fieldWidth, fieldHeight);
            txtBusqueda.setBounds(padding, txtY, fieldWidth, fieldHeight);

        } else {
            //Posición y tamaño normal
            int btnWidth = btnAgregar.getPreferredSize().width + 20;;
            int btnX = this.getWidth() - padding - btnWidth;;
            btnAgregar.setBounds(btnX, padding, btnWidth, fieldHeight);

            int txtWidth = this.getWidth() - padding * 3 - btnWidth;;
            txtBusqueda.setBounds(padding, padding, txtWidth, fieldHeight);
        }

    }
    
    /**
     * Función para vaciar los campos
     */ 
    protected void vaciarCampos(){
        txtBusqueda.setText("");
    }
    
    
    //COMPONENTES
    private static final CampoTexto txtBusqueda = new CampoTexto("Buscar Cliente", CUALQUIER);
    private static final Boton btnAgregar = new Boton("Agregar Cliente", VERDE);
    private static final JScrollPane scrollTable = new JScrollPane();
    private static final JTable tabla = new JTable();
    private static final String[] header = {"Cedula", "Nombre", "Apellido", "Telefono", "Direccion"};
}
