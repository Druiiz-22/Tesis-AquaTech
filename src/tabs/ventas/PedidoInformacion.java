package tabs.ventas;

import components.Boton;
import components.Label;
import components.Logo;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.SwingConstants.HORIZONTAL;
import static properties.Colores.ROJO_OSCURO;
import static properties.Colores.VERDE;
import static properties.Mensaje.msjAdvertencia;

public class PedidoInformacion extends JDialog implements properties.Constantes, properties.Colores {

    public PedidoInformacion() {
        super(main.Run.getFrameRoot(), true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setIconTitle();
        this.setResizable(false);
        this.setLayout(null);
        this.getContentPane().setSize(paneSize);
        this.getContentPane().setBackground(BLANCO);
        this.setTitle("Información del pedido - AquaTech");

        initComponents();
        relocateComponents();
        listeners();
    }

    private void initComponents() {
        //Layout para el panel de la información
        GridLayout grid = new GridLayout(0, 4);
        grid.setHgap(5);
        grid.setVgap(5);

        //Propiedades del panel para la información
        info.setOpaque(false);
        info.setLayout(grid);

        //Alinear los textos hacia arriba
        lblID.setVerticalAlignment(JLabel.TOP);
        lblCedula.setVerticalAlignment(JLabel.TOP);
        lblServicio.setVerticalAlignment(JLabel.TOP);
        lblCantidad.setVerticalAlignment(JLabel.TOP);
        lblTipoPago.setVerticalAlignment(JLabel.TOP);
        lblFecha.setVerticalAlignment(JLabel.TOP);
        lblDireccion.setVerticalAlignment(JLabel.TOP);
        lblReferencia.setVerticalAlignment(JLabel.TOP);
        lblBanco.setVerticalAlignment(JLabel.TOP);
        lblFechaEmision.setVerticalAlignment(JLabel.TOP);
        
        this.add(logo);
        this.add(titulo);
        this.add(info);
        this.add(btnPagar);
        this.add(btnCerrar);
        info.add(lblID);
        info.add(lblCedula);
        info.add(lblServicio);
        info.add(lblCantidad);
        info.add(lblTipoPago);
        info.add(lblFecha);
        info.add(lblDireccion);
        info.add(lblReferencia);
        info.add(lblBanco);
        info.add(lblFechaEmision);
    }

    private void relocateComponents() {
        int width = paneSize.width;
        int height = paneSize.height;
        int padding = 20;

        //Posición del logo
        int x = width / 2 - logo.getWidth() / 2;
        logo.setLocation(x, padding);

        //Posición del título
        x = width / 2 - titulo.getWidth() / 2;
        int y = padding * 2 + logo.getHeight();
        titulo.setLocation(x, y);

        //Posición y tamaño de los botones
        int w = btnCerrar.getPreferredSize().width + 30;
        int h = 40;
        x = width/2 - padding/2 - w;
        y = height - padding - h;
        btnCerrar.setBounds(x, y, w, h);
        
        w = btnPagar.getPreferredSize().width + 30;
        x = width/2 + padding/2;
        btnPagar.setBounds(x, y, w, h);
        
        //Posición del panel de la información
        y = titulo.getY() + titulo.getHeight() + padding;
        w = width - padding*4;
        h = height - y - padding*2 - h;
        info.setBounds(padding*2, y, w, h);
    }

    private void listeners(){
        //MOUSE LISTENER
        btnCerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                dispose();
            }
        });       
        btnPagar.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                dispose();
                Pedidos.pagarPedido(cedula);
            }
        });
        
        //WINDOW LISTENER
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                //Obtener el tamaño del panel contenedor + los bordes de la 
                //ventana una vez que la ventana sea abierta, para asignar el 
                //tamaño al Dialog
                int w = paneSize.width + getInsets().left + getInsets().right;
                int h = paneSize.height + getInsets().top + getInsets().bottom;

                //Asignar el tamaño y centrar el Dialog
                setSize(w, h);
                setLocation(centerLocation());
            }
        });
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
     * Función para obtener las coordenadas necesarias para posicionar la
     * ventana en el centro de la pantalla
     *
     * @return
     */
    private java.awt.Point centerLocation() {

        //Obtener el tamaño de la pantalla
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        //Posición en X del frame
        int x = screen.width / 2 - this.getWidth() / 2;
        //Posición en Y del frame
        int y = screen.height / 2 - this.getHeight() / 2;

        //Retornar el tamaño mínimo
        return new java.awt.Point(x, y);
    }

    public void mostrar(
            Object id,
            Object cedula,
            Object servicio,
            Object cantidad,
            Object tipoPago,
            Object fecha,
            Object direccion,
            Object referencia,
            Object banco,
            Object fechaEmision
    ) {
        PedidoInformacion.cedula = cedula.toString();
        
        lblID.setText("<html><p><b>Pedido:</b></p></p>" + id + "</p></html>");
        lblCedula.setText("<html><p><b>Cedula:</b></p><p>" + cedula + "</p></html>");
        lblServicio.setText("<html><p><b>Servicio:</b></p><p>" + servicio + "</p></html>");
        lblCantidad.setText("<html><p><b>Cantidad:</b></p><p>" + cantidad + "</p></html>");
        lblTipoPago.setText("<html><p><b>Tipo de pago:</b></p><p>" + tipoPago + "</p></html>");
        lblFecha.setText("<html><p><b>Fecha pedido:</b></p><p>" + fecha + "</p></html>");
        lblDireccion.setText("<html><p><b>Dirección:</b></p><p>" + direccion + "</p></html>");

        lblReferencia.setText("<html><p><b>Referencia:</b></p><p>" + referencia + "</p></html>");
        lblBanco.setText("<html><p><b>Banco:</b></p><p>" + banco + "</p></html>");
        lblFechaEmision.setText("<html><p><b>Fecha emision:</b></p><p>" + fechaEmision + "</p></html>");

        setVisible(true);
    }

    public void mostrar(
            Object id,
            Object cedula,
            Object servicio,
            Object cantidad,
            Object tipoPago,
            Object fecha,
            Object direccion
    ) {
        PedidoInformacion.cedula = cedula.toString();
        
        lblID.setText("<html><p><b>Pedido:</b></p><p>" + id + "</p></html>");
        lblCedula.setText("<html><p><b>Cedula:</b></p><p>" + cedula + "</p></html>");
        lblServicio.setText("<html><p><b>Servicio:</b></p><p>" + servicio + "</p></html>");
        lblCantidad.setText("<html><p><b>Cantidad:</b></p><p>" + cantidad + "</p></html>");
        lblTipoPago.setText("<html><p><b>Tipo de pago:</b></p><p>" + tipoPago + "</p></html>");
        lblFecha.setText("<html><p><b>Fecha pedido:</b></p><p>" + fecha + "</p></html>");
        lblDireccion.setText("<html><p><b>Dirección:</b></p><p>" + direccion + "</p></html>");

        //Vaciar los datos de transferencia para cuando el pedido no se haya
        //pagado mediante una transferencia.
        lblReferencia.setText("");
        lblBanco.setText("");
        lblFechaEmision.setText("");

        setVisible(true);
    }

    //ATRIBUTOS
    private static String cedula;
    private static final Dimension paneSize = new Dimension(600, 440);
    
    //COMPONENTES
    private static final JPanel info = new JPanel();
    private static final Logo logo = new Logo(HORIZONTAL);
    private static final Label titulo = new Label("Información detallada del pedido realizada por el cliente", TITULO, 20);

    private static final Label lblID = new Label("", PLANO, 16);
    private static final Label lblCedula = new Label("", PLANO, 16);
    private static final Label lblServicio = new Label("", PLANO, 16);
    private static final Label lblCantidad = new Label("", PLANO, 16);
    private static final Label lblTipoPago = new Label("", PLANO, 16);
    private static final Label lblFecha = new Label("", PLANO, 16);
    private static final Label lblDireccion = new Label("", PLANO, 16);

    private static final Label lblReferencia = new Label("", PLANO, 16);
    private static final Label lblBanco = new Label("", PLANO, 16);
    private static final Label lblFechaEmision = new Label("", PLANO, 16);
    
    private static final Boton btnPagar = new Boton("Pagar pedido", VERDE);
    private static final Boton btnCerrar = new Boton("Cerrar", ROJO_OSCURO);
}
