package tabs;

import components.Boton;
import components.CampoTexto;
import components.Tabla;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 * Clase para la creación del panel de registro de los proveedores en el sistema
 */
public class Proveedores extends JPanel implements properties.Constantes, properties.Colores {

    /**
     * Constructor del panel de proveedores
     */
    public Proveedores() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        listeners();
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {
        btnAgregar.setToolTipText("Ir a la ventana para el registro de un nuevo proveedor al sistema");
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
                nuevoProv.agregar();
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
     * Función para redimensionar los componentes y el panel a tiempo real,
     * según una dimensión dada
     *
     * @param size Dimensión del parent contenedor
     */
    public void relocateComponents(java.awt.Dimension size) {
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
     * Función para vaciar todos los campos
     */
    public static void vaciarCampos() {
        txtBusqueda.setText("");
        nuevoProv.vaciarCampos();
    }

    /**
     * Función para actualizar los datos en la tabla de proveedores
     */
    public static void actualizarDatos() {
        txtBusqueda.setText("");
        tabla.actualizarDatos();
    }

    /**
     * Función para acceder a la ventana de nuevo proveedor y enviar la
     * información del proveedor seleccionado para su edición
     *
     * @param rif
     * @param nombre
     * @param telefono
     * @param direccion
     */
    public static void editProveedor(String rif, String nombre, String telefono, String direccion) {
        nuevoProv.editar(rif, nombre, telefono, direccion);
    }

    //COMPONENTES
    private static final CampoTexto txtBusqueda = new CampoTexto("Buscar Proveedor", CUALQUIER);
    private static final Boton btnAgregar = new Boton("Agregar Proveedor", VERDE);
    private static final Tabla tabla = new Tabla(PROVEEDOR);
    private static final NuevoProveedor nuevoProv = new NuevoProveedor();
}
