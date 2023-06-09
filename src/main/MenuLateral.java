package main;

import javax.swing.JPanel;
import java.awt.GridLayout;
import components.Boton;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import static main.MenuSuperior.setTabTitle;
import static main.Run.setFrameTitle;
import tabs.admin.Admin;
import tabs.clientes.Clientes;
import tabs.compras.Compras;
import tabs.ventas.Ventas;

/**
 * Clase para la creación del menú lateral desplegable del programa principal
 */
public class MenuLateral extends JPanel implements properties.Colores, properties.Constantes {

    /**
     * Constructor para la creación del menú lateral del programa
     */
    public MenuLateral() {
        this.setLayout(new GridLayout(0, 1));
        this.setBackground(NEGRO);

        //Iniciar los componentes
        initComponents();

        //Asignar los eventos del mouse
        mouseListener();
    }

    /**
     * Función para agregar los botones al panel
     */
    private void initComponents() {
        //Agregar los botones al menú
        this.add(gapTop);
        this.add(btnInicio);
        this.add(btnClientes);
        this.add(btnVentas);
        this.add(btnCompras);
        this.add(btnHistorial);
        this.add(btnProveedores);
        this.add(gapBottom);
    }

    /**
     * Función para asignar los eventos del mouse a los botones del menú.
     */
    private void mouseListener() {
        btnInicio.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clickButton(INICIO);
            }
        });
        btnClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clickButton(CLIENTES);
            }
        });
        btnVentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clickButton(VENTAS_TRASVASO);
            }
        });
        btnCompras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clickButton(COMPRAS);
            }
        });
        btnHistorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clickButton(HISTORIAL);
            }
        });
        btnProveedores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clickButton(PROVEEDOR);
            }
        });
        
        btnAdmin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clickButton(ADMIN);
            }
        });
    }

    /**
     * Función para presionar un botón del menú lateral, cambiando su fondo, el
     * título del frame, el título del menú superior, cambiar el contenedor y,
     * de ser posible, cambiar el panel dentro del contenedor
     *
     * @param type Botón presionado.
     */
    public static void clickButton(int type) {

        //Validar qué botón fue presionado fue el de inicio
        if (type == INICIO) {
            btnInicio.setBackground(AZUL_OSCURO);

            //Cambiar el panel por del botón presionado
            Frame.replacePanel(INICIO);
            setTabTitle("Inicio");
            setFrameTitle("Inicio");

        } else {
            /*
            Si NO fue el botón presionado, validar si el botón
            de inicio está de color azúl. En caso de ser de color
            azúl, implica que el botón YA estaba activo, pero NO
            fue presionado; por lo cual, se le aplica el color
            negro para marcar que se desactivó. 
            
            Caso contrario de que NO sea el botón presionado, ni 
            tampoco es de color azúl, entonces el botón está 
            desactivado, ni tampoco será activado, por lo que,
            quedará como estaba.
             */
            if (btnInicio.getBackground().equals(AZUL_OSCURO)) {
                btnInicio.setBackground(NEGRO);
            }
        }

        //Validar que el botón presionado fue el de clientes
        if (type == CLIENTES || type == DEUDAS) {
            btnClientes.setBackground(AZUL_OSCURO);
            Frame.replacePanel(CLIENTES);
            Clientes.replacePanel(type);
            setTabTitle("Clientes");
            setFrameTitle("Clientes");

        } else {
            if (btnClientes.getBackground().equals(AZUL_OSCURO)) {
                btnClientes.setBackground(NEGRO);
            }
        }
        //Validar que el botón presionado fue el de Ventas
        if (type == VENTAS || type == VENTAS_TRASVASO || type == VENTAS_BOTELLON || type == VENTAS_PEDIDOS) {

            btnVentas.setBackground(AZUL_OSCURO);
            Frame.replacePanel(VENTAS);

            //Validar si se presionó VENTAS u otro tipo
            Ventas.replacePanel((type == VENTAS) ? VENTAS_TRASVASO : type);

            setTabTitle("Ventas");
            setFrameTitle("Ventas");

        } else {
            if (btnVentas.getBackground().equals(AZUL_OSCURO)) {
                btnVentas.setBackground(NEGRO);
            }
        }

        //Validar que el botón presionado fue el de Compras
        if (type == COMPRAS || type == COMPRAS_RECARGA || type == COMPRAS_BOTELLON) {
            btnCompras.setBackground(AZUL_OSCURO);
            Frame.replacePanel(COMPRAS);

            //Validar si se presionó VENTAS u otro tipo
            Compras.replacePanel((type == COMPRAS) ? COMPRAS_RECARGA : type);

            setTabTitle("Compras");
            setFrameTitle("Compras");

        } else {
            if (btnCompras.getBackground().equals(AZUL_OSCURO)) {
                btnCompras.setBackground(NEGRO);
            }
        }
        //Validar que el botón presionado fue el del Historial
        if (type == HISTORIAL || type == HISTORIAL_TRASVASO) {

            btnHistorial.setBackground(AZUL_OSCURO);
            Frame.replacePanel(HISTORIAL);

            setTabTitle("Historial");
            setFrameTitle("Historial");

        } else {
            if (btnHistorial.getBackground().equals(AZUL_OSCURO)) {
                btnHistorial.setBackground(NEGRO);
            }
        }
        //Validar que el botón presionado fue el de Proveedores
        if (type == PROVEEDOR) {
            btnProveedores.setBackground(AZUL_OSCURO);
            Frame.replacePanel(PROVEEDOR);
            setTabTitle("Proveedores");
            setFrameTitle("Proveedores");

        } else {
            if (btnProveedores.getBackground().equals(AZUL_OSCURO)) {
                btnProveedores.setBackground(NEGRO);
            }
        }
        //Validar que el botón presionado fue el de Administración
        if (type == ADMIN || type == ADMIN_USUARIOS || type == ADMIN_EMPLEADOS) {
            btnAdmin.setBackground(AZUL_OSCURO);
            Frame.replacePanel(ADMIN);
            
            //Validar si se presionó Administración u otro tipo
            Admin.replacePanel((type == ADMIN) ? ADMIN_AJUSTES : type);
            
            setTabTitle("Administración");
            setFrameTitle("Administración");

        } else {
            if (btnAdmin.getBackground().equals(AZUL_OSCURO)) {
                btnAdmin.setBackground(NEGRO);
            }
        }

    }

    /**
     * Función para activar el botón del administrador
     */
    protected void addAdminButton() {
        //Quitar el espacio del final
        this.remove(gapBottom);

        //Agregar el botón de admin
        this.add(btnAdmin);
        //Agregar el espacio al final
        this.add(gapBottom);
    }

    /**
     * Función para desactivar el botón del administrador
     */
    protected void removeAdminButton() {

        //Obtener los componentes
        Component[] componente = this.getComponents();
        //Obtener la posición donde DEBERÍA estár el 
        //botón de administración
        int penultimo = componente.length - 2;

        //Validar si el componente en esa posición, es el
        //botón de administración
        if (componente[penultimo].equals(btnAdmin)) {
            //Eliminar el botón
            this.remove(btnAdmin);
        }
    }

    //COMPONENTES
    private static final Boton btnInicio = new Boton(INICIO);
    private static final Boton btnClientes = new Boton(CLIENTES);
    private static final Boton btnVentas = new Boton(VENTAS);
    private static final Boton btnCompras = new Boton(COMPRAS);
    private static final Boton btnHistorial = new Boton(HISTORIAL);
    private static final Boton btnProveedores = new Boton(PROVEEDOR);
    private static final Boton btnAdmin = new Boton(ADMIN);
    private static final JLabel gapTop = new JLabel("");
    private static final JLabel gapBottom = new JLabel("");
}
