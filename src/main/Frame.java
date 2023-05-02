package main;

import components.PanelNotificaciones;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.showOptionDialog;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static main.MenuLateral.clickButton;
import static properties.Mensaje.msjAdvertencia;
import tabs.Inicio;

/**
 * Clase para la creación de la ventana principal del software
 */
public class Frame extends JFrame implements properties.Constantes {

    /**
     * Constructor del frame del programa principal
     *
     * @param nombreUsuario Nombre de usuario
     * @param rolUsuario Rol del usuario
     */
    public Frame(String nombreUsuario, int rolUsuario) {
        Frame.nombreUsuario = nombreUsuario;
        Frame.rolUsuario = rolUsuario;

        //Propiedades básicas
        this.getContentPane().setLayout(null);
        this.setMinimumSize(getMinSize());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setIconTitle();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("AquaTech - Inicio");

        //Iniciar los componentes
        initComponents();

        listeners();

        //Presionar el botón de inicio
        clickButton(INICIO);

        if (rolUsuario == ADMINISTRADOR) {
            lateral.addAdminButton();
        } else {
            lateral.removeAdminButton();
        }
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        Inicio.setUserName(Frame.nombreUsuario);

        //Agregar los componentes
        this.add(menu);
        this.add(lateral);
        this.add(contenedor);

        //Actualizar todas las pestañas 
        contenedor.actualizarDatos();

        //GlassPane de notificaciones
        this.setGlassPane(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                //Pinter un fondo oscuro en el contenedor
                g.setColor(new java.awt.Color(0, 0, 0, 0.2f));
                int y = menu.getHeight();
                int h = getContentPane().getHeight() - y;

                g.fillRect(0, y, getContentPane().getWidth(), h);
            }
        });

        glass = (Container) (this.getGlassPane());
        glass.setLayout(null);
        glass.add(notificaciones);
        glass.add(lateral);

        //LISTA DE COSAS PENDIENTES
        //- Ver el google maps.
        //- Diseñar los ajustes.
        //- Diseñar los pedidos.
        //- Diseñar la tabla de deudas.
        //- Realizar la generación de reportes de deudas
        //- Dar formulario a los RIF
        //NOTAS
        //se crea un cliente -> NO se crea un usuario
        //se crea un usuario de cliente -> Se crea un cliente
        //se crea un usuario operativo -> Se crea un cliente
        //se crea un cliente y luego se crea un usuario -> Se sobreponen los datos del usuario en el cliente
    }

    /**
     * Función para asignar los listener a los componentes de la clase
     */
    private void listeners() {
        //Listener para la ventana, para cuando sea presionado
        //el botón de cerrar
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                salir();
            }
        });

        //Listener para cuando la ventana se redimensione su tamaño
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                relocateComponents();
            }
        });

        //Listener para el GlassPane para cuando que se cierre
        //cuando sea presionado fuera del panel de notificaciones
        glass.addMouseListener(new MouseAdapter() {

            //Variable para determinar si se cerrará el GlassPane,
            //ANTES de soltar el mouse
            boolean cerrar = true;

            @Override
            public void mousePressed(MouseEvent e) {
                //Obtener la posición del mouse
                int mouseX = e.getX();
                int mouseY = e.getY();

                //Variables para la osicion y tamaño del panel que esté visible
                int panelX = 0, panelY = 0, panelW = 0, panelH = 0;

                //Comprobar si el menú lateral está siendo visible
                if (lateral.isVisible()) {
                    //Posición y tamaño del menú lateral
                    panelX = lateral.getX();
                    panelY = lateral.getY();
                    panelW = panelX + lateral.getWidth();
                    panelH = panelY + lateral.getHeight();

                } else {
                    //En caso contrario, se estará viendo las notificaciones
                    //Posición y tamaño del panel de notificaciones
                    panelX = notificaciones.getX();
                    panelY = notificaciones.getY();
                    panelW = panelX + notificaciones.getWidth();
                    panelH = panelY + notificaciones.getHeight();
                }

                //Validar que el mouse esté FUERA del panel visible
                cerrar = (mouseX < panelX || mouseX > panelW) || (mouseY < panelY || mouseY > panelH);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Si se va a cerrar (true), el glass no será visible (false).
                //Si NO se va a cerrar (false), el glass sigue visible (true).
                glass.setVisible(!cerrar);
            }

        });
    }

    /**
     * Función para posicionar los componentes a tiempo real
     */
    private void relocateComponents() {
        //Obtener el tamaño de la pantalla a tiempo real
        this.setMinimumSize(getMinSize());

        System.out.println("\nFrame size: w=" + this.getWidth() + ", h=" + this.getHeight());
        System.out.println("Minimum size: mw=" + this.getMinimumSize().width + ", mh=" + this.getMinimumSize().height);

        //Obtener el tamaño del componente
        Dimension frameSize = this.getContentPane().getSize();

        //MENU SUPERIOR
        int menuHeight = (int) (frameSize.height * 0.1);
        menu.setSize(frameSize.width, menuHeight);

        //NOTIFICACIONES
        int w = frameSize.width - notificaciones.getWidth() - 20;
        notificaciones.setLocation(w, menuHeight);

        //CONTENEDOR
        int contenedorHeight = frameSize.height - menuHeight;
        contenedor.setLocation(0, menuHeight);
        contenedor.setSize(frameSize.width, contenedorHeight);

        //MENU LATERAL
        lateral.setSize(220, contenedorHeight);
        lateral.setLocation(lateral.getX(), menuHeight);

        //Reposicionar y redimensionar los componentes internos del
        //menú supeior y del contenedor
        menu.relocateComponents();
        contenedor.relocateComponents();

        this.revalidate();
        this.repaint();

        //Validar si el panel está en su tamaño mínimo
        if (contenedor.getWidth() < 700) {
            notificaciones.panelGrande(false);
            Inicio.relocateButtons(false, contenedorHeight);
        } else {
            notificaciones.panelGrande(true);
            Inicio.relocateButtons(true, contenedorHeight);
        }
    }

    /**
     * Función para colocarle el ícono del software al frame.
     */
    private void setIconTitle() {
        try {
            //Cargar el ícono del frame
            Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo.png"));
            this.setIconImage(img.getScaledInstance(32, 32, ESCALA_SUAVE));

        } catch (Exception ex) {
            msjAdvertencia(
                    "No se pudo encontrar el ícono del software.\n"
                    + "El software seguirá ejecutandose normalmente sin el ícono."
            );
        }
    }

    /**
     * Función para decidir la acción de salida del programa
     */
    private void salir() {

        //Opciones
        String[] botones = {"Cerrar Sesión", "Cerrar", "Cancelar"};

        //Mostrar mensaje para la elección
        int opcion = showOptionDialog(null,
                "¿Está seguro de que quiere cerrar el programa?",
                "Salir",
                0,
                QUESTION_MESSAGE,
                null,
                botones,
                botones[2]);

        //Validar si va a cerrar la sesión
        if (opcion == 0) {

            vaciarFrame();

            Run.cerrarPrograma();
            Run.iniciarLogin();

            //Validar si se va a cerrar el programa
        } else if (opcion == 1) {
            Run.cerrarPrograma();

            //Terminar de ejecutar el programa
            System.exit(0);
        }
    }

    /**
     * Función para obtener el tamaño mínimo que podrá tener el programa
     *
     * @return Dimensiones mínimas para el programa.
     */
    public static Dimension getMinSize() {

        //Obtener el tamaño de la pantalla
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        //Asignar el ancho mínimo a la mitad de la pantalla
        int minWidth = (int) (screen.width / 2);
        //Asignar la altura mínima al 90% de la altura 
        int minHeight = (int) (screen.height * 0.9);

        //Retornar el tamaño mínimo
        return new Dimension(minWidth, minHeight);
    }

    /**
     * Función para reemplazar el panel del contenedor
     *
     * @param type Panel que será mostrado
     */
    public static void replacePanel(int type) {
        contenedor.replacePanel(type);
        lateral.revalidate();
        lateral.repaint();

        if (type == INICIO) {
            Run.repaintFrame();
            if (contenedor.getWidth() < 700) {
                Inicio.relocateButtons(false, contenedor.getHeight());
            } else {
                Inicio.relocateButtons(true, contenedor.getHeight());
            }
        }

    }

    /**
     * Función para abrir el panel de cristal por encima del contentPane
     *
     * @param menu TRUE en caso de abrir el menú lateral, FALSE en caso de abrir
     * las notificaciones
     */
    public static void openGlass(boolean menu) {
        //Validar que el componente de glass NO esté vacío
        if (glass != null) {

            //Validar si se abrirá el menú o las notificaciones
            if (menu) {
                lateral.setVisible(true);
                notificaciones.setVisible(false);
            } else {
                lateral.setVisible(false);
                notificaciones.setVisible(true);
            }

            glass.setVisible(true);
        }
    }

    /**
     * Función para vaciar los datos del usuario y todos los componentes, antes
     * de cerrar el programa
     */
    public static void vaciarFrame() {
        //Vaciar todos los campos
        contenedor.vaciarCampos();

        //Vaciar los datos del usuario
        nombreUsuario = null;
        rolUsuario = -99;
    }

    /**
     * Función para obtener el nombre del usuario que tiene la sesión activa
     *
     * @return
     */
    public static String getUserName() {
        return nombreUsuario;
    }

    /**
     * Función para obtener el rol del usuario que tiene la sesión activa
     *
     * @return
     */
    public static int getUserRol() {
        return rolUsuario;
    }

    //ATRIBUTOS
    private static String nombreUsuario;
    private static int rolUsuario;

    //COMPONENTES
    private static final MenuSuperior menu = new MenuSuperior();
    protected static final Contenedor contenedor = new Contenedor();

    private static Container glass;
    private static final MenuLateral lateral = new MenuLateral();
    private static final PanelNotificaciones notificaciones = new PanelNotificaciones();
}
