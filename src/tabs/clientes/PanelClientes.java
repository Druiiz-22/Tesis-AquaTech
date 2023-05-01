package tabs.clientes;

import components.Boton;
import components.CampoTexto;
import components.Tabla;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 * Clase para la creación del panel de registro de los clientes en el sistema
 */
public class PanelClientes extends JPanel implements properties.Colores, properties.Constantes {

    /**
     * Constructor del panel de registro de clientes
     */
    public PanelClientes() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        listeners();
    }

    /**
     * Función para iniciar sus componentes
     */
    private void initComponents() {
        btnAgregar.setToolTipText("Ir a la ventana para el registro de un nuevo cliente al sistema");
        txtBusqueda.setToolTipText("Ingrese cualquier nombre para buscar alguna coincidencia en la tabla");

        this.add(txtBusqueda);
        this.add(btnAgregar);
        this.add(tabla);
    }

    /**
     * Función para asignar los listener a los componentes
     */
    private void listeners() {
        //MOUSE LISTENER
        btnAgregar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                nuevoCliente.agregar();
            }
        });
        //KEY LISTENER
        txtBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                tabla.buscar(txtBusqueda.getText());
            }
        });

    }

    /**
     * Función para reposicionar y redimensionar los componentes
     *
     * @param size Tamaño del parent contenedor
     */
    protected void relocateComponents(java.awt.Dimension size) {
        this.setSize(size);

        int padding = 20;
        int fieldHeight = 40;

        //Posición y tamaño cuando el panel sea muy pequeño
        if (size.width < 500) {
            int fieldWidth = this.getWidth() - padding * 2;
            int txtY = padding * 2 + fieldHeight;

            btnAgregar.setBounds(padding, padding, fieldWidth, fieldHeight);
            txtBusqueda.setBounds(padding, txtY, fieldWidth, fieldHeight);

        } else {
            //Posición y tamaño normal
            int btnWidth = btnAgregar.getPreferredSize().width + 20;;
            int btnX = this.getWidth() - padding - btnWidth;;
            btnAgregar.setBounds(btnX, padding, btnWidth, fieldHeight);

            int txtWidth = this.getWidth() - padding * 3 - btnWidth;;
            txtBusqueda.setBounds(padding, padding, txtWidth, fieldHeight);
        }

        //Posicionar la tabla
        int tablaY = txtBusqueda.getY() + txtBusqueda.getHeight() + padding;
        int tablaH = size.height - tablaY - padding;
        int tablaW = size.width - padding * 2;
        tabla.setBounds(padding, tablaY, tablaW, tablaH);
    }

    /**
     * Función para vaciar los campos
     */
    protected static void vaciarCampos() {
        txtBusqueda.setText("");
        nuevoCliente.vaciarCampos();
    }

    /**
     * Función para actualizar los datos de la tabla de clientes
     */
    public static void actualizarDatos() {
        txtBusqueda.setText("");
        tabla.actualizarDatos();
    }

    /**
     * Función para acceder a la tabla de la clase y enviar los datos para
     * editar el cliente seleccionado
     *
     * @param cedula
     * @param nombre
     * @param apellido
     * @param telefono
     */
    public static void editCliente(String cedula, String nombre, String apellido, String telefono) {
        nuevoCliente.editar(cedula, nombre, apellido, telefono);
    }

    /**
     * Función para acceder a la tabla de la clase y buscar el apellido de un
     * cliente, según una cédula dada
     *
     * @param cedula Cédula del cliente a buscar
     * @return Apellido del cliente encontrado
     */
    public static String getApellido(String cedula) {
        return tabla.getClienteApellido(cedula);
    }

    //COMPONENTES
    private static final CampoTexto txtBusqueda = new CampoTexto("Buscar Cliente", CUALQUIER);
    private static final Boton btnAgregar = new Boton("Agregar Cliente", VERDE);
    private static final Tabla tabla = new Tabla(CLIENTES);
    private static final NuevoCliente nuevoCliente = new NuevoCliente();
}
