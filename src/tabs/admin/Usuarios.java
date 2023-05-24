package tabs.admin;

import components.Boton;
import components.CampoTexto;
import components.Label;
import components.PanelInfo;
import components.Tabla;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import static javax.swing.BorderFactory.createLineBorder;

/**
 * Clase para la creación del panel de los usuarios registrados en el sistema
 */
public class Usuarios extends JPanel implements properties.Constantes, properties.Colores {

    /**
     * Constructor del panel de los ajustes
     */
    public Usuarios() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        listeners();
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        btnAgregar.setToolTipText("Agregar un nuevo usuario");
        txtBuscar.setToolTipText("Buscar algún usuario");

        panelUsuarios.setBackground(BLANCO);
        panelUsuarios.setBorder(createLineBorder(GRIS));

        panelUsuarios.add(lblTitulo);
        panelUsuarios.add(txtBuscar);
        panelUsuarios.add(btnAgregar);
        panelUsuarios.add(tabla);

        this.add(panelUsuarios);
        this.add(informacion);

    }

    /**
     * Función para aplicar los listener a los componentes
     */
    private void listeners() {
        btnAgregar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                nuevo.agregar();
            }
        });

        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                tabla.buscar(txtBuscar.getText());
            }
        });
    }

    /**
     * Función para reposicionar los componentes
     *
     * @param width Ancho del parent content
     * @param height Alto del parent content
     */
    protected void relocateComponents(int width, int height) {
        Usuarios.width = width;
        Usuarios.panelHeight = height - padding * 2;

        //Posicion de la información fija
        informacion.setLocation(padding, padding);

        //Validar el tamaño del panel contenedor
        if (width < 700) {
            panelPequenio();

        } else {
            this.setPreferredSize(new Dimension(width, height));
            panelGrande();
        }

        //Reposicionar los componentes
        informacion.relocateComponents(CUALQUIER);
        relocateUsuario();
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño menor a 600 px
     */
    private void panelPequenio() {
        //Ancho de los paneles
        int panelWidth = width - padding * 2;

        //Tamaño de la información
        int infoH;
        if (width < 402) {
            infoH = 300;

        } else if (width < 477) {
            infoH = 260;

        } else if (width < 630) {
            infoH = 240;
        } else {
            infoH = 210;
        }
        informacion.setSize(panelWidth, infoH);

        //Altura del panel de usuarios
        int y = padding * 2 + informacion.getHeight();
        //Asignar la posición y el tamaño al panel de usuarios
        panelUsuarios.setBounds(padding, y, panelWidth, panelHeight);

        //Altura absoluta del panel
        int absoluteHeight = padding * 3 + panelHeight + informacion.getHeight();
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño mayor a 600px
     */
    private void panelGrande() {
        //Aumentar 15px al ancho y alto de los paneles
        width += 15;
        panelHeight += 15;

        //Ancho máximo para el panel informativo
        int infoMaxWidth = 300;

        //Asignar el tamaño a la información
        informacion.setSize(infoMaxWidth, panelHeight);

        //Posición en x del panel de usuarios
        int x = infoMaxWidth + padding * 2;
        //Ancho del panel de usuarios
        int w = width - x - padding;
        //Asignar la posición y el tamaño al panel de usuarios
        panelUsuarios.setBounds(x, padding, w, panelHeight);
    }

    /**
     * Función para reposicionar los componentes dentro del panel de usuario
     */
    private void relocateUsuario() {
        //Titulo en una posición fija
        lblTitulo.setLocation(padding, padding);

        //Ancgo del botón de agregar usuario
        int w = btnAgregar.getPreferredSize().width + 30;
        //Altura del botón y campo de texto
        int h = 40;
        //Posición en y del botón y campo de texto
        int y = padding * 2 + lblTitulo.getHeight();
        //Posición en x a la derecha del panel
        int x = panelUsuarios.getWidth() - padding - w;
        btnAgregar.setBounds(x, y, w, h);

        //Ancho del campo de texto
        w = panelUsuarios.getWidth() - padding * 3 - w;
        txtBuscar.setBounds(padding, y, w, h);

        y += h + padding;
        w = panelUsuarios.getWidth() - padding * 2;
        h = panelUsuarios.getHeight() - y - padding;
        tabla.setBounds(padding, y, w, h);
    }

    /**
     * Función para vaciar todos los campos
     */
    public static void vaciarCampos() {
        txtBuscar.setText("");
        nuevo.vaciarCampos();
    }

    /**
     * Función para actualizar el panel de usuarios y la ventana
     *
     * @return
     */
    protected static boolean actualizarDatos() {
        txtBuscar.setText("");
        return tabla.actualizarDatos();
    }

    /**
     * Función para editar un usuario seleccionado
     *
     * @param cedula Cédula del usuario
     * @param nombre
     * @param apellido
     * @param telefono
     * @param correo Correo del usuario
     */
    public static void editUsuario(String cedula, String nombre, String apellido, String telefono, String correo) {
        try {
            new Thread() {
                @Override
                public void run() {
                    nuevo.editar(cedula, nombre, apellido, telefono, correo);

                    //Abrir el glassPane para actualizar los datos
                    main.Frame.openGlass(0);
                    //Ya que puede dar muchos problemas el alterar o agregar
                    //un dato a una tabla (y no a su Model), la forma de 
                    //visualizar los cambios será actualizando los datos 
                    //de la tabla con la base de datos
                    Usuarios.actualizarDatos();
                    //Cerrar el glassPane
                    main.Frame.closeGlass();
                }
            }.start();
        } catch (Exception e) {
            System.out.println("Error durante el edit = "+e);
        }

    }

    protected void habilitarComponents(boolean estado) {
        txtBuscar.setEnabled(estado);
    }

    //ATRIBUTOS
    private static int width, panelHeight;
    private static final int padding = 20;

    //COMPONENTES
    private static final PanelInfo informacion = new PanelInfo(ADMIN_USUARIOS);
    private static final JPanel panelUsuarios = new JPanel(null);
    private static final Tabla tabla = new Tabla(ADMIN_USUARIOS);
    private static final Label lblTitulo = new Label("Usuarios", TITULO, 24);
    private static final CampoTexto txtBuscar = new CampoTexto("Buscar usuario", CUALQUIER);
    private static final Boton btnAgregar = new Boton("Agregar", VERDE);
    private static final NuevoUsuario nuevo = new NuevoUsuario();
}
