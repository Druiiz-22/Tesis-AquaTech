package main;

import components.PanelNotificaciones;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
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
     * @param identificacion Nombre de usuario
     * @param rol
     * @param nombre
     */
    public Frame(String identificacion, int rol, String nombre) {
        Frame.identificacion = identificacion;
        Frame.rol = rol;
        Frame.nombre = nombre;

        //Propiedades básicas
        this.getContentPane().setLayout(null);
        this.setMinimumSize(getMinSize());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setIconTitle();
        this.setLocationRelativeTo(null);
        this.setTitle("AquaTech - Inicio");

        //Iniciar los componentes
        initComponents();

        listeners();

        //Presionar el botón de inicio
        clickButton(INICIO);

        if (rol == ADMINISTRADOR) {
            lateral.addAdminButton();
        } else {
            lateral.removeAdminButton();
        }
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        Inicio.setUserName(Frame.nombre);

        //Agregar los componentes
        this.add(menu);
        this.add(lateral);
        this.add(contenedor);

        //GlassPane para las notificaciones y el menú lateral
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
        //- Hacer los ajustes
        //- Crear el rol de "operador"
        //- Cancelar un pedido
        //- Reajustar reportes por sucursales
        //- Reajustar reporte de trasvasos
        //- Reporte de pedidos
        //- Dar formulario a los RIF
        //CAMBIOS EN LA BASE DE DATOS
        //- proceso para crear usuario (creando cliente al mismo tiempo)
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
                //Validar que el cursor activo en el GlassPane NO sea el cursor 
                //de cargando.
                if (!glass.getCursor().equals(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR))) {
                    //Si se va a cerrar (true), el glass no será visible (false).
                    //Si NO se va a cerrar (false), el glass sigue visible (true).
                    glass.setVisible(!cerrar);

                }
            }
        });
    }

    /**
     * Función para posicionar los componentes a tiempo real
     */
    private void relocateComponents() {
        //Obtener el tamaño de la pantalla a tiempo real
        this.setMinimumSize(getMinSize());

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
    protected static Dimension getMinSize() {
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
     * @param tipo Entero para determinar lo que se mostrará al abrir el
     * GlassPane. Uno (1) para el menú lateral, dos (2) para las notificaciones
     * y cualquier otro para el glasspane cargando.
     */
    public static void openGlass(int tipo) {
        //Validar que el componente de glass NO esté vacío
        if (glass != null) {

            //Asignar el cursor predeterminado
            glass.setCursor(Cursor.getDefaultCursor());

            //Validar si se abrirá el menú o las notificaciones
            switch (tipo) {
                case 1:
                    //Mostrar el menú lateral
                    lateral.setVisible(true);
                    notificaciones.setVisible(false);
                    break;
                case 2:
                    //Mostrar las notificaciones
                    lateral.setVisible(false);
                    notificaciones.setVisible(true);
                    break;
                default:
                    //Ocultar ambos
                    lateral.setVisible(false);
                    notificaciones.setVisible(false);
                    //Asignar el cursor de cargando
                    glass.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    break;
            }

            glass.setVisible(true);
        }
    }
    
    public static void closeGlass(){
        glass.setVisible(false);
    }

    /**
     * Función para vaciar los datos del usuario y todos los componentes, antes
     * de cerrar el programa
     */
    public static void vaciarFrame() {
        //Vaciar todos los campos
        contenedor.vaciarCampos();

        //Vaciar los datos del usuario
        nombre = null;
        identificacion = null;
        rol = -99;
    }

    /**
     * Función para obtener el nombre del usuario que tiene la sesión activa
     *
     * @return
     */
    public static String getUserName() {
        return nombre;
    }

    /**
     * Función para obtener el rol del usuario que tiene la sesión activa
     *
     * @return
     */
    public static int getUserRol() {
        return rol;
    }

    public static String getUserIdentified() {
        return Frame.identificacion;
    }

    protected static boolean actualizarPrograma() {
        return Contenedor.actualizarDatos();
    }

    //ATRIBUTOS
    private static String nombre;
    private static String identificacion;
    private static int rol;

    //COMPONENTES
    private static final MenuSuperior menu = new MenuSuperior();
    protected static final Contenedor contenedor = new Contenedor();

    private static Container glass;
    private static final MenuLateral lateral = new MenuLateral();
    private static final PanelNotificaciones notificaciones = new PanelNotificaciones();
}
