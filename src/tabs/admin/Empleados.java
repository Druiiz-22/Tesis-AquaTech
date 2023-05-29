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
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.JPanel;
import properties.Colores;
import static properties.Colores.BLANCO;
import static properties.Colores.GRIS;
import static properties.Constantes.CUALQUIER;

public class Empleados extends JPanel implements properties.Constantes{
    
    public Empleados(){
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        listeners();
    }
    
    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        btnAgregar.setToolTipText("Agregar un nuevo empleado");
        txtBuscar.setToolTipText("Buscar algún empleado");

        panelEmpleados.setBackground(BLANCO);
        panelEmpleados.setBorder(createLineBorder(GRIS));

        panelEmpleados.add(lblTitulo);
        panelEmpleados.add(txtBuscar);
        panelEmpleados.add(btnAgregar);
        panelEmpleados.add(tabla);

        this.add(panelEmpleados);
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
        Empleados.width = width;
        Empleados.panelHeight = height - padding * 2;

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
        panelEmpleados.setBounds(padding, y, panelWidth, panelHeight);

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
        panelEmpleados.setBounds(x, padding, w, panelHeight);
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
        int x = panelEmpleados.getWidth() - padding - w;
        btnAgregar.setBounds(x, y, w, h);

        //Ancho del campo de texto
        w = panelEmpleados.getWidth() - padding * 3 - w;
        txtBuscar.setBounds(padding, y, w, h);

        y += h + padding;
        w = panelEmpleados.getWidth() - padding * 2;
        h = panelEmpleados.getHeight() - y - padding;
        tabla.setBounds(padding, y, w, h);
    }
    
    /**
     * Función para vaciar todos los campos
     */
    public static void vaciarCampos() {
        txtBuscar.setText("");
        //nuevo.vaciarCampos();
    }

    /**
     * Función para actualizar el panel de usuarios y la ventana
     *
     * @return
     */
    protected static boolean actualizarDatos() {
        txtBuscar.setText("");
        return tabla.actualizarDatos() && nuevo.actualizarDatos();
    }
    
    protected void habilitarComponents(boolean estado) {
        txtBuscar.setEnabled(estado);
    }

    public static void contratarUsuario(String cedula){
        nuevo.contratar(cedula);
    }
    
    public static void editEmpleado(String cedula, String cargo, String rol, String sucursal){
        nuevo.editar(cedula, cargo, rol, sucursal);
    }
    
    //ATRIBUTOS
    private static int width, panelHeight;
    private static final int padding = 20;

    //COMPONENTES
    private static final PanelInfo informacion = new PanelInfo(ADMIN_EMPLEADOS);
    private static final JPanel panelEmpleados = new JPanel(null);
    private static final Tabla tabla = new Tabla(ADMIN_EMPLEADOS);
    private static final Label lblTitulo = new Label("Empleados", TITULO, 24);
    private static final CampoTexto txtBuscar = new CampoTexto("Buscar empleado", CUALQUIER);
    private static final Boton btnAgregar = new Boton("Agregar", Colores.VERDE);
    private static final NuevoEmpleado nuevo = new NuevoEmpleado();
}
