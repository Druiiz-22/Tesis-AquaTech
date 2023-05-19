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
import java.awt.Dimension;
import static java.awt.Font.PLAIN;
import java.util.logging.Level;
import java.util.logging.Logger;
import static properties.Mensaje.msjError;
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
        btnMenuLateral.setHorizontalAlignment(CENTER);
        btnMenuLateral.setVerticalAlignment(CENTER);
        btnMenuLateral.setCursor(new Cursor(HAND_CURSOR));
        btnMenuLateral.setToolTipText("Abrir el menú lateral para navegar por las pestañas");

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
        
        //Ajustar el botón para actualizar los datos
        btnRefresh.setHorizontalAlignment(CENTER);
        btnRefresh.setVerticalAlignment(CENTER);
        btnRefresh.setCursor(new Cursor(HAND_CURSOR));
        btnRefresh.setToolTipText("Actualizar los datos");

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
            //Imágen del menú lateral
            imgBotonLateral = getImageIcon("menu");

            //Imagenes del menú de la derecha
            imgBotonRefresh = getImageIcon("actualizar");
            imgBotonWeb = getImageIcon("web");
            imgBotonPerfil = getImageIcon("profile");

            imgBotonNotif = getImageIcon("bell");
            imgBotonNotif_Alert = getImageIcon("bell_alert");

            imgBotonDesplegar = getImageIcon("despliegue");
            imgBotonDesplegar_Alert = getImageIcon("despliegue_alert");

            //Imagenes del menú desplegable
            imgMenuRefresh = getImageIcon("popup/actualizar");
            imgMenuWeb = getImageIcon("popup/web");
            imgMenuPerfil = getImageIcon("popup/profile");
            imgMenuNotif = getImageIcon("popup/bell");
            imgMenuNotif_Alert = getImageIcon("popup/bell_alert");

            //Asignar las imágenes a los botones
            btnMenuLateral.setIcon(imgBotonLateral);

            btnRefresh.setIcon(imgBotonRefresh);
            btnWeb.setIcon(imgBotonWeb);
            btnPerfil.setIcon(imgBotonPerfil);
            btnNotificacion.setIcon(imgBotonNotif);
            btnDesplegar.setIcon(imgBotonDesplegar);

            itemRefresh.setIcon(imgMenuRefresh);
            itemWeb.setIcon(imgMenuWeb);
            itemPerfil.setIcon(imgMenuPerfil);
            itemNotif.setIcon(imgMenuNotif);

        } catch (Exception e) {
            msjError("No se pudo cargar algún ícono en el menú superior.");
            msjError("El software NO podrá iniciar hasta que se carguen "
                    + "todos los íconos.");

            //Terminar el programa
            System.exit(0);
        }

        //Ajustar el primer panel
        panelStart.setOpaque(false);
        //Agregar los componentes
        panelStart.add(gapStart, BorderLayout.LINE_START);
        panelStart.add(btnMenuLateral, BorderLayout.CENTER);
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
        itemRefresh.setFont(segoe(13, PLAIN));
        itemRefresh.setForeground(NEGRO);
        itemNotif.setFont(segoe(13, PLAIN));
        itemNotif.setForeground(NEGRO);
        itemWeb.setFont(segoe(13, PLAIN));
        itemWeb.setForeground(NEGRO);
        itemPerfil.setFont(segoe(13, PLAIN));
        itemPerfil.setForeground(NEGRO);

        //Agregar los items al PopUp Menu
        ppDespliegue.add(itemRefresh);
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
            size = 18;

        } else {
            size = 32;
        }

        //Retornar la imagen escalada
        return new ImageIcon(img.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
    }

    private void resizeImage() {
        try {
            int h = Frame.getMinSize().height;

            //Validar que la altura del frame
            if (h < 600) {
                //Tamaño de los botones del menú de la derecha
                int size = 28;
                //Objeto ImageIcon para convertir las imágenes escaladas (Image)
                ImageIcon icono;

                icono = new ImageIcon(imgBotonWeb.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
                btnWeb.setIcon(icono);

                icono = new ImageIcon(imgBotonPerfil.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
                btnPerfil.setIcon(icono);

                //Validar que no hayan notificaciones
                if (notificaciones == 0) {
                    icono = new ImageIcon(imgBotonNotif.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
                } else {
                    icono = new ImageIcon(imgBotonNotif_Alert.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
                }
                btnNotificacion.setIcon(icono);

                //Tamaño del botón del menú lateral y de despliegue
                size = 36;
                icono = new ImageIcon(imgBotonLateral.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
                btnMenuLateral.setIcon(icono);

                //Validar que no hayan notificaciones
                if (notificaciones == 0) {
                    icono = new ImageIcon(imgBotonDesplegar.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
                } else {
                    icono = new ImageIcon(imgBotonDesplegar_Alert.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
                }
                btnDesplegar.setIcon(icono);

            } else {
                //Asignar a cada uno su imagen con el tamaño original
                btnMenuLateral.setIcon(imgBotonLateral);
                btnWeb.setIcon(imgBotonWeb);
                btnPerfil.setIcon(imgBotonPerfil);

                //Validar que no hayan notificaciones
                if (notificaciones == 0) {
                    btnNotificacion.setIcon(imgBotonNotif);
                    btnDesplegar.setIcon(imgBotonDesplegar);
                } else {
                    btnNotificacion.setIcon(imgBotonNotif_Alert);
                    btnDesplegar.setIcon(imgBotonDesplegar_Alert);
                }
            }

        } catch (Exception ex) {
            msjError("No se pudo cargar algún ícono en el menú superior.");
            msjError("El software NO podrá iniciar hasta que se carguen "
                    + "todos los íconos.");

            //Terminar el programa arrojando un error
            System.exit(0);
        }
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

            panelEnd.remove(btnRefresh);
            panelEnd.remove(btnNotificacion);
            panelEnd.remove(btnWeb);
            panelEnd.remove(btnPerfil);
            panelEnd.remove(gapEnd);

            panelEnd.add(btnDesplegar);
            panelEnd.add(gapEnd);

            if (panelWidth < 600) {
                logo.setText("");
                lblTabTitle.setFontSize((panelWidth < 450) ? 22 : 28);

            } else {
                logo.setText("AquaTech");
                lblTabTitle.setFontSize(28);
            }

        } else {
            logo.setText("AquaTech");
            grid.setHgap(15);

            //Si el ancho es mayor o igual a 700px, quitar
            //el botón de despliegue y mostrar los demás botones
            panelEnd.remove(btnDesplegar);

            panelEnd.add(btnRefresh);
            panelEnd.add(btnNotificacion);
            panelEnd.add(btnWeb);
            panelEnd.add(btnPerfil);
            panelEnd.add(gapEnd);
        }

        resizeImage();

        this.revalidate();
        this.repaint();
    }

    /**
     * Función para asignar los mouse listener a todos los botones
     */
    private void mouseListeners() {
        //MENÚ LATERAL
        btnMenuLateral.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Frame.openGlass(1);
            }
        });
        //ACTUALIZAR
        btnRefresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                new Thread(){
                    @Override
                    public void run() {
                        //Abrir el GlassPane cargando
                        Frame.openGlass(0);
                       
                        //Pausar el programa por un segundo
                        try {
                            sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MenuSuperior.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        //Actualizar todos los datos
                        Contenedor.actualizarDatos();
                        
                        //Cerrar el glassPane
                        Frame.closeGlass();
                    }
                }.start();
            }
        });
        //NOTIFICACIONES
        btnNotificacion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Frame.openGlass(2);
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
            Frame.openGlass(2);
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
        
    }

    /**
     * Función para ir hacia la información del perfil del usuario en la página
     * web
     */
    private void perfil() {
        
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
     * @param count Número de notificaciones
     */
    public static void setNotificationCount(int count) {
        //Guardar el número de notificaciones
        notificaciones = count;
        
        //Obtener el tamaño mínimo del frame
        int mh = Frame.getMinSize().height;

        //Si el frame puede tener un tamaño menor a 600 px, entonces se reducen
        //el tamaño de los botones
        if (mh < 600) {
            //Objeto ImageIcon para convertir las imagenes escaladas (Imagen)
            ImageIcon icono;
            int size;
            
            //Validar si hay alguna notificación
            if (notificaciones == 0) {
                //Tamaño del botón de notificaciones
                size = 28;
                //Escalar la imágen y convertirla a ImageIcon
                icono = new ImageIcon(imgBotonNotif.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
                //Asignar la imagen escalada
                btnNotificacion.setIcon(icono);

                //Tamaño del botón del menú desplegable
                size = 36;
                //Escalar la imágen y convertirla a ImageIcon
                icono = new ImageIcon(imgBotonDesplegar.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
                //Asignar la imagen escalada
                btnDesplegar.setIcon(icono);
                
                //El botón de notificaciones en el menú desplegable, siempre 
                //mantiene su mismo tamaño
                itemNotif.setIcon(imgMenuNotif);
                
            } else {
                //Tamaño del botón de notificaciones con alerta
                size = 28;
                //Escalar la imágen y convertirla a ImageIcon
                icono = new ImageIcon(imgBotonNotif_Alert.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
                //Asignar la imagen escalada
                btnNotificacion.setIcon(icono);

                //Tamaño del botón del menú desplegable con alerta
                size = 36;
                //Escalar la imágen y convertirla a ImageIcon
                icono = new ImageIcon(imgBotonDesplegar_Alert.getImage().getScaledInstance(size, size, ESCALA_SUAVE));
                //Asignar la imagen escalada
                btnDesplegar.setIcon(icono);
                
                //El botón de notificaciones en el menú desplegable, siempre 
                //mantiene su mismo tamaño
                itemNotif.setIcon(imgMenuNotif_Alert);
            }

        } else {
            //Si el frame es mayor a 600px, asignar el tamaño predeterminado
            
            //Validar si hay alguna notificacion
            if (notificaciones == 0) {
                btnNotificacion.setIcon(imgBotonNotif);
                btnDesplegar.setIcon(imgBotonDesplegar);
                itemNotif.setIcon(imgMenuNotif);
                
            } else {
                //Imagenes con alerta de notificación
                btnNotificacion.setIcon(imgBotonNotif_Alert);
                btnDesplegar.setIcon(imgBotonDesplegar_Alert);
                itemNotif.setIcon(imgMenuNotif_Alert);
            }
        }
    }

    //ATRIBUTOS
    private static int notificaciones = 0;

    //COMPONENTES
    //Panel del logo y menú
    private static final JPanel panelStart = new JPanel(new BorderLayout());
    private static final JLabel gapStart = new JLabel("     ");
    private static final JLabel btnMenuLateral = new JLabel();
    private static final Logo logo = new Logo(LOGO_MENU);

    //Label del centro
    private static final Label lblTabTitle = new Label("Inicio", NORMAL_BLANCO, 28);

    //Panel de los botones finales
    private static final GridLayout grid = new GridLayout(1, 0);

    private static final JPanel panelEnd = new JPanel();
    private static final JLabel btnNotificacion = new JLabel();
    private static final JLabel btnRefresh = new JLabel();
    private static final JLabel btnWeb = new JLabel();
    private static final JLabel btnPerfil = new JLabel();
    private static final JLabel btnDesplegar = new JLabel();
    private static final JLabel gapEnd = new JLabel("");

    //Imágenes del botón del menú lateral
    private static ImageIcon imgBotonLateral;

    //Imágenes de los botones de la derech
    private static ImageIcon imgBotonRefresh;
    private static ImageIcon imgBotonWeb;
    private static ImageIcon imgBotonPerfil;
    private static ImageIcon imgBotonNotif;
    private static ImageIcon imgBotonNotif_Alert;
    private static ImageIcon imgBotonDesplegar;
    private static ImageIcon imgBotonDesplegar_Alert;

    //Imagenes para los botones dentro del menú desplegable
    private static ImageIcon imgMenuRefresh;
    private static ImageIcon imgMenuWeb;
    private static ImageIcon imgMenuPerfil;
    private static ImageIcon imgMenuNotif;
    private static ImageIcon imgMenuNotif_Alert;

    //PopUp Menú para el despliegue
    private static final JPopupMenu ppDespliegue = new JPopupMenu();
    private static final JMenuItem itemRefresh = new JMenuItem("Actualizar los datos");
    private static final JMenuItem itemNotif = new JMenuItem("Notificaciones");
    private static final JMenuItem itemWeb = new JMenuItem("Ir al sitio web");
    private static final JMenuItem itemPerfil = new JMenuItem("ver el perfil");

}
