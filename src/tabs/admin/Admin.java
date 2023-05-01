package tabs.admin;

import components.Label;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Clase para la creación del panel de ajustes de administrador, esta clase
 * contiene el menú de navegación superior y el panel contenedor
 */
public class Admin extends JPanel implements properties.Constantes, properties.Colores {

    /**
     * Constructor del panel de administrador
     */
    public Admin() {
        this.setLayout(null);

        initComponents();
        mouseListeners();
        
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        //Activar el botón de los ajustes
        btnAjustes.setForeground(AZUL_PRINCIPAL);

        //Asignar el cursor de mano a todos los botones
        btnAjustes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUsuarios.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReportes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRespaldo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Agregar el tooltiptext a cada botón
        btnAjustes.setToolTipText("Ajustes generales del programa");
        btnUsuarios.setToolTipText("Lista de los usuarios registrados en el sistema");
        btnReportes.setToolTipText("Generación de reportes");
        btnRespaldo.setToolTipText("Respaldo e importación de la base de datos");

        //Propiedades del menú de navegación superior
        menu.setBackground(GRIS);
        menu.add(btnAjustes);
        menu.add(btnUsuarios);
        menu.add(btnReportes);
        menu.add(btnRespaldo);

        //Panel contenedor con CardLayout para navegar
        //entre los distintos paneles
        contenedor.setLayout(card);
        contenedor.setOpaque(false);
        contenedor.add(panelAjustes, "1");
        contenedor.add(panelUsuarios, "2");
        contenedor.add(panelReportes, "3");
        contenedor.add(panelRespaldo, "4");
        card.show(contenedor, "1");
        
        scroll.setOpaque(false);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(8);
        scroll.getViewport().setOpaque(false);
        scroll.setViewportView(contenedor);

        //Agregar el menú y el contenedor al panel admin
        this.add(menu);
        this.add(scroll);
    }

    /**
     * Función para asignar los mouse listener a los componentes
     */
    private void mouseListeners() {
        btnAjustes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Comprobar que el botón NO esté presionado
                if (!btnAjustes.getForeground().equals(AZUL_PRINCIPAL)) {
                    replacePanel(ADMIN_AJUSTES);
                }
            }
        });
        btnUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Comprobar que el botón NO esté presionado
                if (!btnUsuarios.getForeground().equals(AZUL_PRINCIPAL)) {
                    replacePanel(ADMIN_USUARIOS);
                }
            }
        });
        btnReportes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Comprobar que el botón NO esté presionado
                if (!btnReportes.getForeground().equals(AZUL_PRINCIPAL)) {
                    replacePanel(ADMIN_REPORTES);
                }
            }
        });
        btnRespaldo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Comprobar que el botón NO esté presionado
                if (!btnRespaldo.getForeground().equals(AZUL_PRINCIPAL)) {
                    replacePanel(ADMIN_RESPALDO);
                }
            }
        });
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * @param size tamaño del parent contenedor
     */
    public void relocateComponents(java.awt.Dimension size) {
        this.setSize(size);

        menu.setSize(size.width, menu.getPreferredSize().height);

        int contY = menu.getHeight();
        scroll.setLocation(0, contY);
        scroll.setSize(size.width, size.height - contY);

        //Reducir el tamaño del panel dentro del scroll
        //en 20px horizontal y vertical
        int scrollW = scroll.getWidth() - 20;
        int scrollY = scroll.getHeight() - 20;
        
        //Redimencionar los paneles
        panelReportes.relocateComponents(scrollW, scrollY);
        panelUsuarios.relocateComponents(scrollW, scrollY);
        panelRespaldo.relocateComponents(scrollW, scrollY);
        
        
        //Determinar el preferred size según cuál panel sea visible
        if (btnAjustes.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelAjustes.getPreferredSize());

        } else if (btnUsuarios.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelUsuarios.getPreferredSize());

        } else if (btnReportes.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelReportes.getPreferredSize());
            
        } else if (btnRespaldo.getForeground().equals(AZUL_PRINCIPAL)){
            contenedor.setPreferredSize(panelRespaldo.getPreferredSize());
        }
    }

    /**
     * Función para navegar entre los distintos paneles
     * @param type Panel que será mostrado
     */
    private static void replacePanel(int type) {
        //Comprobar si se debe activar o desactivar el botón,
        //según el botón presionado
        btnAjustes.setForeground((type == ADMIN_AJUSTES) ? AZUL_PRINCIPAL : NEGRO);
        btnUsuarios.setForeground((type == ADMIN_USUARIOS) ? AZUL_PRINCIPAL : NEGRO);
        btnReportes.setForeground((type == ADMIN_REPORTES) ? AZUL_PRINCIPAL : NEGRO);
        btnRespaldo.setForeground((type == ADMIN_RESPALDO) ? AZUL_PRINCIPAL : NEGRO);

        //Determinar qué botón fue presionado para desplegar su
        //respectivo panel
        card.show(contenedor,
                (type == ADMIN_USUARIOS) ? "2"
                        : (type == ADMIN_REPORTES) ? "3"
                                : (type == ADMIN_RESPALDO) ? "4" : "1"
        );
        
        //Determinar el preferred size según cuál panel sea visible
        if (btnAjustes.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelAjustes.getPreferredSize());

        } else if (btnUsuarios.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelUsuarios.getPreferredSize());

        } else if (btnReportes.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelReportes.getPreferredSize());
            
        } else if (btnRespaldo.getForeground().equals(AZUL_PRINCIPAL)){
            contenedor.setPreferredSize(panelRespaldo.getPreferredSize());
        }
    }

    /**
     * Función para vaciar todos los campos de todos los paneles
     */ 
    public static void vaciarCampos(){
        Ajustes.vaciarCampos();
        Reportes.vaciarCampos();
        Respaldo.vaciarCampos();
        Usuarios.vaciarCampos();
        
        replacePanel(ADMIN_AJUSTES);
    }
    
    public static void actualizarDatos(){
        Usuarios.actualizarDatos();
    }
    
    //COMPONENTES
    private static final JScrollPane scroll = new JScrollPane();
    private static final JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
    private static final JPanel contenedor = new JPanel();
    private static final CardLayout card = new CardLayout();
    private static final Label btnAjustes = new Label("Ajustes", TITULO, 18);
    private static final Label btnUsuarios = new Label("Usuarios", TITULO, 18);
    private static final Label btnReportes = new Label("Reportes", TITULO, 18);
    private static final Label btnRespaldo = new Label("Respaldo", TITULO, 18);
    private static final Ajustes panelAjustes = new Ajustes();
    private static final Usuarios panelUsuarios = new Usuarios();
    private static final Reportes panelReportes = new Reportes();
    private static final Respaldo panelRespaldo = new Respaldo();
}
