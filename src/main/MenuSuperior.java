package main;

import components.Label;
import components.Logo;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import static javax.swing.SwingConstants.CENTER;
import static java.awt.Cursor.HAND_CURSOR;
import static java.awt.Font.PLAIN;
import static properties.Mensaje.msjError;
import static main.Frame.showMenuLateral;
import static properties.Fuentes.segoe;

/**
 * Clase para la creación del menú superior del programa principal
 */
public class MenuSuperior extends JPanel implements properties.Colores, properties.Constantes {

    /**
     * Constructor del menú superior
     */
    public MenuSuperior() {
        BorderLayout bl = new BorderLayout();
        this.setLayout(bl);
        this.setBackground(AZUL_PRINCIPAL);

        initComponents();
        mouseListeners();
    }

    /**
     * Función para iniciar los componentes del menú superior
     */
    private void initComponents() {

        //Ajustar el label de las pestañas
        lblTabTitle.setHorizontalAlignment(CENTER);
        lblTabTitle.setVerticalAlignment(CENTER);

        //Ajustar el botón del menú
        btnMenu.setHorizontalAlignment(CENTER);
        btnMenu.setVerticalAlignment(CENTER);
        btnMenu.setCursor(new Cursor(HAND_CURSOR));
        btnMenu.setToolTipText("Abrir el menú lateral para navegar por las pestañas");

        //Ajustar el botón de las notificaciones
        btnNotificaciones.setHorizontalAlignment(CENTER);
        btnNotificaciones.setVerticalAlignment(CENTER);
        btnNotificaciones.setCursor(new Cursor(HAND_CURSOR));
        btnNotificaciones.setToolTipText("Ver las notificaciones");
        
        //Ajustar el botón del sitio web
        btnWeb.setHorizontalAlignment(CENTER);
        btnWeb.setVerticalAlignment(CENTER);
        btnWeb.setCursor(new Cursor(HAND_CURSOR));
        btnWeb.setToolTipText("Ir al sitio web");

        //Ajustar el botón del perfil
        btnPerfil.setHorizontalAlignment(CENTER);
        btnPerfil.setVerticalAlignment(CENTER);
        btnPerfil.setCursor(new Cursor(HAND_CURSOR));
        btnPerfil.setToolTipText("Ir a la información de tu cuenta");

        //Ajustare el botón de despliegue de los botones
        btnAbajo.setHorizontalAlignment(CENTER);
        btnAbajo.setVerticalAlignment(CENTER);
        btnAbajo.setCursor(new Cursor(HAND_CURSOR));
        btnAbajo.setToolTipText("Ver las opciones disponibles");
        
        //Cargar las imagenes
        try {
            btnMenu.setIcon(getImageIcon("menu"));
            btnNotificaciones.setIcon(getImageIcon("bell"));
            btnWeb.setIcon(getImageIcon("web"));
            btnPerfil.setIcon(getImageIcon("profile"));
            btnAbajo.setIcon(getImageIcon("abajo"));
            
            itemNotif.setIcon(getImageIcon("bell_black"));
            itemWeb.setIcon(getImageIcon("web_black"));
            itemPerfil.setIcon(getImageIcon("profile_black"));
            
        } catch (Exception e) {
            msjError("No se pudo cargar algún ícono en el menú superior.");
            msjError("El software NO podrá iniciar hasta que se carguen "
                    + "todos los íconos.");
            
            //Terminar el programa arrojando un error
            throw new NullPointerException();
        }

        
        //Ajustar el primer panel
        panelStart.setOpaque(false);
        //Agregar los componentes
        panelStart.add(gapStart, BorderLayout.LINE_START);
        panelStart.add(btnMenu, BorderLayout.CENTER);
        panelStart.add(logo, BorderLayout.LINE_END);

        //Ajustar el panel de los botones del final
        GridLayout grid = new GridLayout(1, 0);
        grid.setHgap(15);
        panelEnd.setLayout(grid);
        panelEnd.setOpaque(false);
        
        //Agregar los componentes al menú superior
        this.add(panelStart, BorderLayout.LINE_START);
        this.add(lblTabTitle, BorderLayout.CENTER);
        this.add(panelEnd, BorderLayout.LINE_END);
        
        
        //Ajustar el PopUp Menú
        itemNotif.setFont(segoe(18, PLAIN));
        itemNotif.setForeground(NEGRO);
        itemWeb.setFont(segoe(18, PLAIN));
        itemWeb.setForeground(NEGRO);
        itemPerfil.setFont(segoe(18, PLAIN));
        itemPerfil.setForeground(NEGRO);
        //Agregar los items al PopUp Menu
        ppDespliegue.add(itemNotif);
        ppDespliegue.add(itemWeb);
        ppDespliegue.add(itemPerfil);
    }

    /**
     * Función para obtener una imagen escalada
     * @param type nombre de la imagen a retornar
     * @return ImageIcon escalado
     */
    private ImageIcon getImageIcon(String type) {
        //Variable para las imagenes
        ImageIcon img = new ImageIcon(getClass().getResource("/icons/" + type + ".png"));
        
        //Determinar el tamaño de la imagen.
        int size;
        if(type.equals("abajo")){
            size = 38;
        } else if (type.contains("_black")){
            size = 20;
        } else {
            size = 32;
        }
        
        //Retornar la imagen escalada
        return new ImageIcon(img.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
    }

    /**
     * Función para reposicionar los componentes del menú superior
     */
    protected void relocateComponents() {
        //Obtener el ancho del menú
        int panelWidth = this.getWidth();
        
        //Si el menú es menor a 700px, quitar los botones
        //y mostrar solo el botón de despliegue
        if (panelWidth < 700) {
            panelEnd.remove(btnNotificaciones);
            panelEnd.remove(btnWeb);
            panelEnd.remove(btnPerfil);

            panelEnd.add(btnAbajo);
            panelEnd.add(gapEnd);

        } else {
            //Si el ancho es mayor o igual a 700px, quitar
            //el botón de despliegue y mostrar los demás botones
            panelEnd.remove(btnAbajo);
            
            panelEnd.add(btnNotificaciones);
            panelEnd.add(btnWeb);
            panelEnd.add(btnPerfil);
            panelEnd.add(gapEnd);
        }
        
        this.revalidate();
        this.repaint();
    }

    /**
     * Función para asignar los mouse listener a todos los botones
     */
    private void mouseListeners(){
        //MENÚ LATERAL
        btnMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                showMenuLateral();
            }
        });
        //NOTIFICACIONES
        btnNotificaciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                notificaciones();
            }
        });
        //SITIO WEB
        btnWeb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                abrirWeb();
            }
        });
        //PERFIL
        btnPerfil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                perfil();
            }
        });
        //DESPLIEGUE
        btnAbajo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Mostrar el menú desplegable
                ppDespliegue.show(btnAbajo, e.getX(), e.getY());
            }
        });
        //ITEMS DEL POPUP MENÚ
        itemNotif.addActionListener((ActionEvent e) -> {
            notificaciones();
        });
        itemWeb.addActionListener((ActionEvent e) -> {
            abrirWeb();
        });
        itemPerfil.addActionListener((ActionEvent e) -> {
            perfil();
        });
    }
    
    /**
     * Función para abrir el sitio web del programa
     */
    public static void abrirWeb(){
        
    }
    /**
     * Función para abrir el frame de las notificacione
     */
    private void notificaciones(){
        
    }
    
    /**
     * Función para ir hacia la información del perfil del
     * usuario en la página web
     */
    private void perfil(){
    }
    
    /**
     * Función estática para asignar el nombre de la pestaña
     * abierta actualmente en el menú superior
     * @param tab Nombre de la pestaña desplegada
     */
    protected static void setTabTitle(String tab){
        lblTabTitle.setText(tab);
    }
    
    //COMPONENTES
    //Panel del logo y menú
    private static final JPanel panelStart = new JPanel(new BorderLayout());
    private static final JLabel gapStart = new JLabel("    ");
    private static final JLabel btnMenu = new JLabel();
    private static final Logo logo = new Logo(LOGO_MENU);
 
    //Label del centro
    private static final Label lblTabTitle = new Label("Inicio", NORMAL_BLANCO, 30);

    //Panel de los botones finales
    private static final JPanel panelEnd = new JPanel();
    private static final JLabel btnNotificaciones = new JLabel();
    private static final JLabel btnWeb = new JLabel();
    private static final JLabel btnPerfil = new JLabel();
    private static final JLabel btnAbajo = new JLabel();
    private static final JLabel gapEnd = new JLabel("");
    
    //PopUp Menú para el despliegue
    private static final JPopupMenu ppDespliegue = new JPopupMenu();
    private static final JMenuItem itemNotif = new JMenuItem("Notificaciones");
    private static final JMenuItem itemWeb = new JMenuItem("Ir al sitio web");
    private static final JMenuItem itemPerfil = new JMenuItem("ver el perfil");
}
