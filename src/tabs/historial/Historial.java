package tabs.historial;

import components.Label;
import java.awt.Cursor;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 * Clase para la creación del apartado del historia, esta clase contiene el menú
 * de navegación superior y el panel contenedor
 */
public class Historial extends JPanel implements properties.Constantes, properties.Colores {

    /**
     * Constructor del apartado del historial
     */
    public Historial() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        mouseListeners();
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        //Activar el botón de historial de trasvasos
        btnTrasvasos.setForeground(AZUL_PRINCIPAL);
        
        //Asignar el mouse de mano a los botones
        btnTrasvasos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRecargas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVentas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCompras.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        //Asignar el tooltip a los botones
        btnTrasvasos.setToolTipText("Historial de todos los trasvasos realizados a los clientes");
        btnRecargas.setToolTipText("Historial de todas las recargas realizados con los proveedores");
        btnVentas.setToolTipText("Historial de las ventas de botellones a los clientes");
        btnCompras.setToolTipText("Historial de las compras de botellones a los proveedores");
        
        //Propiedades del menú de navegación
        menu.setBackground(GRIS);
        menu.add(btnTrasvasos);
        menu.add(btnRecargas);
        menu.add(btnVentas);
        menu.add(btnCompras);

        //Propiedades del contenedor
        //Asignar el CardLayout para navegar
        //entre los paneles
        contenedor.setLayout(card);
        contenedor.setOpaque(false);
        contenedor.add(panelTrasvasos, "1");
        contenedor.add(panelRecargas, "2");
        contenedor.add(panelVentas, "3");
        contenedor.add(panelCompras, "4");
        card.show(contenedor, "1");

        this.add(menu);
        this.add(contenedor);
    }
    
    /**
     * Función para aplicar los MouseListener a los componentes
     */
    private void mouseListeners() {
        btnTrasvasos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!btnTrasvasos.getForeground().equals(AZUL_PRINCIPAL)) {
                    replacePanel(HISTORIAL_TRASVASO);
                }
            }
        });
        btnRecargas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!btnRecargas.getForeground().equals(AZUL_PRINCIPAL)) {
                    replacePanel(HISTORIAL_RECARGA);
                }
            }
        });
        btnVentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!btnVentas.getForeground().equals(AZUL_PRINCIPAL)) {
                    replacePanel(HISTORIAL_VENTA);
                }
            }
        });
        btnCompras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!btnCompras.getForeground().equals(AZUL_PRINCIPAL)) {
                    replacePanel(HISTORIAL_COMPRA);
                }
            }
        });
    }
    /**
     * Función para reposicionar y redimensionar los componentes
     * @param size Tamaño del parent contenedor
     */
    public void relocateComponents(java.awt.Dimension size) {
        this.setSize(size);
        
        menu.setSize(size.width, menu.getPreferredSize().height);

        int contY = menu.getHeight();
        contenedor.setLocation(0, contY);
        contenedor.setSize(size.width, size.height - contY);
        
        panelTrasvasos.relocateComponents(contenedor.getSize());
        panelRecargas.relocateComponents(contenedor.getSize());
        panelVentas.relocateComponents(contenedor.getSize());
        panelCompras.relocateComponents(contenedor.getSize());
    }

    /**
     * Reemplazar el panel contenedor
     * @param type Panel que será mostrado
     */
    private static void replacePanel(int type) {

        //Activar el botón según el tipo
        btnTrasvasos.setForeground((type == HISTORIAL_TRASVASO) ? AZUL_PRINCIPAL : NEGRO);
        btnRecargas.setForeground((type == HISTORIAL_RECARGA) ? AZUL_PRINCIPAL : NEGRO);
        btnVentas.setForeground((type == HISTORIAL_VENTA) ? AZUL_PRINCIPAL : NEGRO);
        btnCompras.setForeground((type == HISTORIAL_COMPRA) ? AZUL_PRINCIPAL : NEGRO);

        //Mostrar el panel según el tipo
        card.show(
                contenedor,
                (type == HISTORIAL_TRASVASO) ? "1"
                        : (type == HISTORIAL_RECARGA) ? "2"
                                : (type == HISTORIAL_VENTA) ? "3" : "4"
        );

    }

    public void vaciarCampos(){
        panelTrasvasos.vaciarCampos();
        panelRecargas.vaciarCampos();
        panelVentas.vaciarCampos();
        panelCompras.vaciarCampos();
    }
    
    //COMPONENTES
    private static final JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
    private static final JPanel contenedor = new JPanel();
    private static final CardLayout card = new CardLayout();
    private static final Label btnTrasvasos = new Label("Trasvasos", TITULO, 18);
    private static final Label btnRecargas = new Label("Recargas", TITULO, 18);
    private static final Label btnVentas = new Label("Ventas", TITULO, 18);
    private static final Label btnCompras = new Label("Compras", TITULO, 18);
    private static final HistorialTrasvasos panelTrasvasos = new HistorialTrasvasos();
    private static final HistorialRecargas panelRecargas = new HistorialRecargas();
    private static final HistorialVentas panelVentas = new HistorialVentas();
    private static final HistorialCompras panelCompras = new HistorialCompras();
}
