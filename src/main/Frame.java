package main;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.Timer;
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

        //Ocultar el menú lateral
        lateral.setVisible(false);
        
        //LISTA DE COSAS PENDIENTES
        //- Hacer las tablas.
        //- Dar la función a los campos de busquedas.
        //- Agregar un popUp menu a la tabla
        //- Redireccionar los registros de clientes y proveedores
        
        //- Hacer la ventana de ingresar nuevo cliente
        //- Hacer la ventana de ingresar nuevo proveedor
        
        //- Hacer el panel de notificaciones.
        
        //- Diseñar los ajustes.
        //- Dar función a la busqueda de rutas y archivos en Reportes y Respaldos
        
        //- Diseñar los pedidos.
        //- Ver el google maps.
        
        //- Realizar el envío de código al correo
    }

    /**
     * Función para posicionar los componentes a tiempo real
     */
    private void relocateComponents() {
        //Obtener el tamaño del componente
        Dimension frameSize = this.getContentPane().getSize();

        //MENU SUPERIOR
        int menuHeight = (int) (frameSize.height * 0.1);
        menu.setSize(frameSize.width, menuHeight);

        //CONTENEDOR
        int contenedorHeight = frameSize.height - menuHeight;
        int contenedorWidth = frameSize.width - ((lateral.isVisible()) ? lateral.getWidth() : 0);
        int contenedorX = (lateral.isVisible()) ? contenedor.getX() : 0;
        contenedor.setLocation(contenedorX, menuHeight);
        contenedor.setSize(contenedorWidth, contenedorHeight);

        //MENU LATERAL
        lateral.setSize(220, contenedorHeight);
        lateral.setLocation(lateral.getX(), menuHeight);

        //Reposicionar y redimensionar los componentes internos del
        //menú supeior y del contenedor
        menu.relocateComponents();
        contenedor.relocateComponents();

        this.revalidate();
        this.repaint();

        Inicio.relocateButtons();
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
            
            contenedor.vaciarCampos();
            
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
    private Dimension getMinSize() {

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
     * Función para mostrar u ocultar el menú lateral
     */
    protected static void showMenuLateral() {

        //Posición en Y del menú lateral
        //Ancho del menú lateral
        int lateralWidth = lateral.getWidth();

        //comprobar que el menú esté visible y que el
        //botón del menú lateral NO esté activado
        if (lateral.isVisible() && !press) {

            //si está visible, OCULTAR el menú
            //Action que realizará la tarea de ocultar el menú
            action = (ActionEvent e) -> {

                //Obtener la altura del menú superior
                int menuHeight = menu.getHeight();

                //Obtener la posición actual del menú lateral
                int lateralX = lateral.getX();

                //restarle 15 px
                lateralX -= 20;

                //Posición del panel contenedor
                int containerX = lateralX + lateralWidth;
                //Ancho del panel contenedor, en base al menú superior
                int containerWidth = menu.getWidth() - containerX;

                //Validar que la posición sea MAYOR al límite -x
                if (lateralX > -lateralWidth) {

                    //Nueva posición del menú lateral
                    lateral.setLocation(lateralX, menuHeight);

                    //Nueva posición del contenedor
                    contenedor.setLocation(containerX, menuHeight);
                    //Nuevo tamaño del contenedor, en base al ancho
                    //del menú superior y la altura del menú lateral
                    contenedor.setSize(containerWidth, lateral.getHeight());
                    contenedor.relocateComponents();

                    contenedor.revalidate();
                    contenedor.repaint();
                    Inicio.relocateButtons();

                } else {
                    //Si la posición es menor al límite en -x,
                    //el menú se ocultó por completo y se coloca, el
                    //menú, en su posición límite -x
                    lateral.setLocation(-lateralWidth, menuHeight);

                    //Localización del contenedor, al comienzo del frame
                    //y debajo del menú superior
                    contenedor.setLocation(0, menuHeight);

                    //Tamaño final del contenedor, con el mismo ancho del
                    //menú superior y altura del menú lateral
                    contenedor.setSize(menu.getWidth(), lateral.getHeight());
                    contenedor.relocateComponents();
                    Inicio.relocateButtons();

                    //Ocultar el menú
                    lateral.setVisible(false);

                    //Indicar que el botón se desactivó
                    press = false;

                    //Terminar el ciclo
                    show.stop();
                }
            };
            //Asignar la acción al timer
            show = new Timer(1, action);
            //Indicar que el botón está activo
            press = true;
            //Iniciar el timer
            show.start();

            //Validar que el menú NO esté visible y que el
            //botón del menú lateral NO esté activo
        } else if (!lateral.isVisible() && !press) {

            //Si no está visible, MOSTRAR el menú
            lateral.setVisible(true);
            //Iniciarlizarlo en el límite -x
            lateral.setLocation(-lateralWidth, menu.getHeight());

            //Action que realizará la tarea de mostrar el menú
            action = (ActionEvent e) -> {

                //Obtener la altura del menú superior
                int menuHeight = menu.getHeight();

                //Obtener la posición actual
                int lateralX = lateral.getX();

                //Sumarle 15 px
                lateralX += 20;

                //Posición del panel contenedor
                int containerX = lateralX + lateralWidth;
                //Ancho del panel contenedor, en base al menú superior
                int containerWidth = menu.getWidth() - containerX;

                //Validar que la posición sea menor a 0
                if (lateralX < 0) {

                    //Nueva posición del menú lateral
                    lateral.setLocation(lateralX, menuHeight);

                    //Nueva posición del contenedor
                    contenedor.setLocation(containerX, menuHeight);
                    //Nuevo tamaño del contenedor, en base al ancho
                    //del menú superior y la altura del menú lateral
                    contenedor.setSize(containerWidth, lateral.getHeight());
                    contenedor.relocateComponents();

                    Inicio.relocateButtons();

                } else {
                    //Si la posición es mayor o igual a 0,
                    //el menú se mostró por completo y se 
                    //coloca, el menú, en x = 0
                    lateral.setLocation(0, menuHeight);

                    //Localización del contenedor, después del menú
                    //lateral y abajo del menú superior
                    contenedor.setLocation(lateralWidth, menuHeight);

                    //Ancho final del contenedor, en base al ancho del
                    //menú superior menos el ancho del menú lateral
                    int finalWidth = menu.getWidth() - lateralWidth;
                    //Tamaño final del contenedor, con la misma altura
                    //del menú lateral
                    contenedor.setSize(finalWidth, lateral.getHeight());

                    contenedor.relocateComponents();

                    Inicio.relocateButtons();

                    //Indicar que el botón se desactivó
                    press = false;

                    //Terminar el ciclo
                    show.stop();
                }
            };

            //Asignar la acción al timer
            show = new Timer(1, action);
            //Indicar que el botón se activó
            press = true;
            //Iniciar el timer
            show.start();
        }
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
            Inicio.relocateButtons();
        }
        
    }
    
    //ATRIBUTOS
    private static String nombreUsuario;
    private static int rolUsuario;
    private static Timer show;
    private static ActionListener action;
    private static boolean press = false;

    //COMPONENTES
    private static final MenuSuperior menu = new MenuSuperior();
    private static final MenuLateral lateral = new MenuLateral();
    protected static final Contenedor contenedor = new Contenedor();
}
