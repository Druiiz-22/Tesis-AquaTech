package tabs;

import components.BotonInicio;
import components.Label;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * Clase para la creación del panel del inicio del programa principal
 */
public class Inicio extends JPanel implements properties.Colores, properties.Constantes {

    /**
     * Constructor para la creación del panel del inicio del programa principal
     */
    public Inicio() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
    }

    /**
     * Función para inicializar los componentes
     */
    private void initComponents() {
        //Color de letra del mensaje de bienvenida
        lblSaludo.setForeground(AZUL_PRINCIPAL);

        //Quitar el fondo al panel del saludo de
        //bienvenida en el inicio
        saludo.setOpaque(false);
        //Añadir el label de bienvenida
        saludo.add(lblSaludo);

        //Quitar el fondo al panel contenedor de los 
        //botones del inicio
        inicio.setOpaque(false);
        grid.setHgap(15);
        grid.setVgap(15);
        //Asignar el layout de tipo grid
        inicio.setLayout(grid);

        //Añadir los botones al panel
        inicio.add(btnTrasvasos);
        inicio.add(btnRecargas);
        inicio.add(btnDelivery);
        inicio.add(btnDeudas);
        inicio.add(btnClientes);
        inicio.add(btnVentas);
        inicio.add(btnCompras);
        inicio.add(btnHistorial);
        inicio.add(btnProveedor);
        inicio.add(btnWeb);

        //Añadir el panel de bienvenida y de
        //los botones al inicio
        this.add(saludo);
        this.add(inicio);
    }

    /**
     * Función para actualizar la posición y tamaño de los componentes
     *
     * @param size
     */
    public void relocateComponents(java.awt.Dimension size) {

        //Asignar el tamaño al panel contenedor
        this.setSize(size);
        //Obtener el ancho
        int width = size.width;
        
        //Asignar el tamaño de la barra de bienvenida
        saludo.setSize(size.width, saludo.getPreferredSize().height);
    
        //Si el ancho es demasiado pequeño, se baja la fuente de letra del saludo
        lblSaludo.setFontSize((size.height < 549) ? 20 : 24);
        
        //Asignar la posición y el tamaño del panel inicio
        int inicioY = saludo.getHeight();
        inicio.setLocation(0, inicioY);
        inicio.setSize(width, size.height - inicioY);

        //Determinar cuantas filas y columnas tendrá el layour
        //según el ancho del contenedor
        //TAMAÑO MUY PEQUEÑO
        if (width < 700) {
            grid.setRows(4);
            grid.setColumns(3);

            //TAMAÑO MEDIO
        } else if (width < 900) {
            grid.setRows(3);
            grid.setColumns(4);

            //TAMAÑO GRANDE
        } else if (width >= 900) {
            grid.setRows(2);
            grid.setColumns(5);
        }

        //Asignar el layout con los cambios aplicados
        inicio.setLayout(grid);
    }

    /**
     * Función reposicionar los botones, esta función debe ser llamada LUEGO de
     * repintar el panel contenedor DEL FRAME.También, durante la transición del
     * menú lateral, LUEGO de repintar el panel contenedor
     *
     * @param largeSize TRUE si el botón debe ser de tamaño grande, FALSE en
     * caso de que sea pequeño
     * @param parentHeight altura del panel contenedor
     */
    public static void relocateButtons(boolean largeSize, int parentHeight) {
        btnTrasvasos.relocateComponents(largeSize, parentHeight);
        btnRecargas.relocateComponents(largeSize, parentHeight);
        btnDelivery.relocateComponents(largeSize, parentHeight);
        btnDeudas.relocateComponents(largeSize, parentHeight);
        btnClientes.relocateComponents(largeSize, parentHeight);
        btnVentas.relocateComponents(largeSize, parentHeight);
        btnCompras.relocateComponents(largeSize, parentHeight);
        btnHistorial.relocateComponents(largeSize, parentHeight);
        btnProveedor.relocateComponents(largeSize, parentHeight);
        btnWeb.relocateComponents(largeSize, parentHeight);
    }

    /**
     * Función para colocar todos los botones en sus estados normales
     */
    public void setButtonsUnselected() {
        btnTrasvasos.setUnselectedStated();
        btnRecargas.setUnselectedStated();
        btnDelivery.setUnselectedStated();
        btnDeudas.setUnselectedStated();
        btnClientes.setUnselectedStated();
        btnVentas.setUnselectedStated();
        btnCompras.setUnselectedStated();
        btnHistorial.setUnselectedStated();
        btnProveedor.setUnselectedStated();
        btnWeb.setUnselectedStated();
    }

    /**
     * Mostrar el nombre del usuario (capitalizado) en el label de bienvenida
     *
     * @param name Nombre del usuario
     */
    public static void setUserName(String name) {
        String split[] = name.split(" ");
        String capitalize = "";
        
        for (String nombre : split){
            capitalize += nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
            capitalize += " ";
        }
        
        lblSaludo.setText("Bienvenido, usuario " + capitalize);
    }

    //COMPONENTES
    private static final JPanel saludo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
    private static final Label lblSaludo = new Label("", TITULO, 24);
    private static final JPanel inicio = new JPanel();
    private static final GridLayout grid = new GridLayout(2, 5);
    private static final BotonInicio btnTrasvasos = new BotonInicio(VENTAS_TRASVASO);
    private static final BotonInicio btnRecargas = new BotonInicio(COMPRAS_RECARGA);
    private static final BotonInicio btnDelivery = new BotonInicio(VENTAS_PEDIDOS);
    private static final BotonInicio btnDeudas = new BotonInicio(DEUDAS);
    private static final BotonInicio btnClientes = new BotonInicio(CLIENTES);
    private static final BotonInicio btnVentas = new BotonInicio(VENTAS_BOTELLON);
    private static final BotonInicio btnCompras = new BotonInicio(COMPRAS_BOTELLON);
    private static final BotonInicio btnHistorial = new BotonInicio(HISTORIAL);
    private static final BotonInicio btnProveedor = new BotonInicio(PROVEEDOR);
    private static final BotonInicio btnWeb = new BotonInicio(WEB);
}
