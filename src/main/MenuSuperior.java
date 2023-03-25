package main;

import components.Label;
import components.Logo;
import components.PanelNotificaciones;
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
import java.awt.Dimension;
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
        btnNotificacion.setHorizontalAlignment(CENTER);
        btnNotificacion.setVerticalAlignment(CENTER);
        btnNotificacion.setCursor(new Cursor(HAND_CURSOR));
        btnNotificacion.setToolTipText("Ver las notificaciones");

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
        btnDesplegar.setHorizontalAlignment(CENTER);
        btnDesplegar.setVerticalAlignment(CENTER);
        btnDesplegar.setCursor(new Cursor(HAND_CURSOR));
        btnDesplegar.setToolTipText("Ver las opciones disponibles");

        gapEnd.setSize(32, 32);
        gapEnd.setPreferredSize(new Dimension(32, 32));
        
        //Cargar las imagenes
        try {
            btnMenu.setIcon(getImageIcon("menu"));
            btnWeb.setIcon(getImageIcon("web"));
            btnPerfil.setIcon(getImageIcon("profile"));

            imgNotificacion = getImageIcon("bell");
            imgDesplegar = getImageIcon("despliegue");
            imgPopUp = getImageIcon("popup/bell");

            imgNotificacionAlert = getImageIcon("bell_alert");
            imgDesplegarAlert = getImageIcon("despliegue_alert");
            imgPopUpAlert = getImageIcon("popup/bell_alert");
            itemWeb.setIcon(getImageIcon("popup/web"));
            itemPerfil.setIcon(getImageIcon("popup/profile"));

            btnNotificacion.setIcon(imgNotificacion);
            btnDesplegar.setIcon(imgDesplegar);
            itemNotif.setIcon(imgPopUp);

        } catch (Exception e) {
            msjError("No se pudo cargar algún ícono en el menú superior.");
            msjError("El software NO podrá iniciar hasta que se carguen "
                    + "todos los íconos.");

            //Terminar el programa arrojando un error
            System.exit(0);
        }

        //Ajustar el primer panel
        panelStart.setOpaque(false);
        //Agregar los componentes
        panelStart.add(gapStart, BorderLayout.LINE_START);
        panelStart.add(btnMenu, BorderLayout.CENTER);
        panelStart.add(logo, BorderLayout.LINE_END);

        //Ajustar el panel de los botones del final
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
     *
     * @param type nombre de la imagen a retornar
     * @return ImageIcon escalado
     */
    private ImageIcon getImageIcon(String type) {
        //Variable para las imagenes
        ImageIcon img = new ImageIcon(getClass().getResource("/icons/" + type + ".png"));

        //Determinar el tamaño de la imagen.
        int size;
        if (type.contains("despliegue") || type.contains("menu")) {
            size = 48;
        } else if (type.contains("popup")) {
            size = 22;
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
            grid.setHgap(0);
            
            panelEnd.remove(btnNotificacion);
            panelEnd.remove(btnWeb);
            panelEnd.remove(btnPerfil);
            panelEnd.remove(gapEnd);

            panelEnd.add(btnDesplegar);
            panelEnd.add(gapEnd);
            

        } else {
            grid.setHgap(15);
            
            //Si el ancho es mayor o igual a 700px, quitar
            //el botón de despliegue y mostrar los demás botones
            panelEnd.remove(btnDesplegar);

            panelEnd.add(btnNotificacion);
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
    private void mouseListeners() {
        //MENÚ LATERAL
        btnMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                showMenuLateral();
            }
        });
        //NOTIFICACIONES
        btnNotificacion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Frame.openNotification(true);
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
        btnDesplegar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Mostrar el menú desplegable
                ppDespliegue.show(btnDesplegar, e.getX(), e.getY());
            }
        });
        //ITEMS DEL POPUP MENÚ
        itemNotif.addActionListener((ActionEvent e) -> {
            Frame.openNotification(true);
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
    public static void abrirWeb() {
        actualizar(0);
    }

    /**
     * Función para ir hacia la información del perfil del usuario en la página
     * web
     */
    private void perfil() {
        actualizar(12);
    }

    /**
     * Función para actualizar las notificaciones
     */
    public static void actualizar(int a){
        
        
        PanelNotificaciones.setNotificationCount(a, a);
        setNotificationCount(a);
    }
    
    /**
     * Función estática para asignar el nombre de la pestaña abierta actualmente
     * en el menú superior
     *
     * @param tab Nombre de la pestaña desplegada
     */
    protected static void setTabTitle(String tab) {
        lblTabTitle.setText(tab);
    }

    /**
     * Función para asignar el número de notificaciones sin revisar
     *
     * @param number Número de notificaciones
     */
    private static void setNotificationCount(int number) {
        //Guardar el número de notificaciones
        notificaciones = number;
        
        if (notificaciones == 0) {
            btnNotificacion.setIcon(imgNotificacion);
            btnDesplegar.setIcon(imgDesplegar);
            itemNotif.setIcon(imgPopUp);
        } else {
            btnNotificacion.setIcon(imgNotificacionAlert);
            btnDesplegar.setIcon(imgDesplegarAlert);
            itemNotif.setIcon(imgPopUpAlert);
        }
    }

    //ATRIBUTOS
    private static int notificaciones = 0;

    //COMPONENTES
    //Panel del logo y menú
    private static final JPanel panelStart = new JPanel(new BorderLayout());
    private static final JLabel gapStart = new JLabel("    ");
    private static final JLabel btnMenu = new JLabel();
    private static final Logo logo = new Logo(LOGO_MENU);

    //Label del centro
    private static final Label lblTabTitle = new Label("Inicio", NORMAL_BLANCO, 30);

    //Panel de los botones finales
    private static final GridLayout grid = new GridLayout(1, 0);;
    private static final JPanel panelEnd = new JPanel();
    private static final JLabel btnNotificacion = new JLabel();
    private static final JLabel btnWeb = new JLabel();
    private static final JLabel btnPerfil = new JLabel();
    private static final JLabel btnDesplegar = new JLabel();
    private static final JLabel gapEnd = new JLabel("");

    //Imagenes para los botones sin notificaciones
    private static ImageIcon imgNotificacion;
    private static ImageIcon imgDesplegar;
    private static ImageIcon imgPopUp;
    //Imagenes para los botones con notificaciones
    private static ImageIcon imgNotificacionAlert;
    private static ImageIcon imgDesplegarAlert;
    private static ImageIcon imgPopUpAlert;

    //PopUp Menú para el despliegue
    private static final JPopupMenu ppDespliegue = new JPopupMenu();
    private static final JMenuItem itemNotif = new JMenuItem("Notificaciones");
    private static final JMenuItem itemWeb = new JMenuItem("Ir al sitio web");
    private static final JMenuItem itemPerfil = new JMenuItem("ver el perfil");

}
