package tabs.ventas;

import components.Boton;
import components.CampoTexto;
import components.Label;
import components.PanelFactura;
import components.PanelInfo;
import database.CreateDB;
import database.ReadDB;
import java.awt.Cursor;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import static javax.swing.BorderFactory.createLineBorder;
import static properties.Fuentes.segoe;
import static properties.Mensaje.msjError;
import static properties.Mensaje.msjYesNo;
import static properties.Mensaje.msjYesNoWarning;
import static properties.ValidarTexto.teclaSuelta;
import static main.MenuLateral.clickButton;

/**
 * Clase para la creación del apartado de ventas a los clientes, esta clase
 * posee el menú de navegación superior y el panel contenedor
 */
public class Ventas extends JPanel implements properties.Constantes, properties.Colores {

    /**
     * Constructor del apartado de ventas a los clientes
     */
    public Ventas() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        mouseListeners();
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {

        //Activar el botón de los trasvasos
        btnTrasvasos.setForeground(AZUL_PRINCIPAL);

        //Asignar el cursor de mano a los botones
        btnTrasvasos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVentas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPedidos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //Agregarles el tooltiptext a cada botón
        btnTrasvasos.setToolTipText("Realizar y registrar un trasvaso a un cliente");
        btnVentas.setToolTipText("Realizar y registrar una venta de un botellón a un cliente");
        btnPedidos.setToolTipText("Ver la lista de los clientes con pedidos a domicilio");

        //Propiedades del menú de navegación
        menu.setBackground(GRIS);
        menu.add(btnTrasvasos);
        menu.add(btnVentas);
        menu.add(btnPedidos);

        //Propiedades del panel contenedor
        //asignar el CardLayout para navegar
        //entre sus paneles
        contenedor.setLayout(card);
        contenedor.setOpaque(false);
        contenedor.add(panelTrasvasos, "1");
        contenedor.add(panelVentas, "2");
        contenedor.add(panelPedidos, "3");
        card.show(contenedor, "1");

        //Scroll para el panel contenedor
        scroll.setOpaque(false);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(8);
        scroll.getViewport().setOpaque(false);
        scroll.setViewportView(contenedor);

        this.add(menu);
        this.add(scroll);
    }

    /**
     * Función para asignar los mouse listener a los componentes
     */
    private void mouseListeners() {
        btnTrasvasos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                replacePanel(VENTAS_TRASVASO);
            }
        });
        btnVentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                replacePanel(VENTAS_BOTELLON);
            }
        });
        btnPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                replacePanel(VENTAS_PEDIDOS);
            }
        });
    }

    /**
     * Función para reposicionar y redimensionar los componentes
     *
     * @param size Tamaño del parent contenedor
     */
    public void relocateComponents(java.awt.Dimension size) {
        this.setSize(size);

        menu.setSize(size.width, menu.getPreferredSize().height);

        int contY = menu.getHeight();
        scroll.setLocation(0, contY);
        scroll.setSize(size.width, size.height - contY);

        //Reducir el tamaño del panel dentro del scroll
        //en 20px horizontal y vertical
        int scrollW = scroll.getWidth() - 20;
        int scrollY = scroll.getHeight() - 20;

        panelTrasvasos.relocateComponents(scrollW, scrollY);
        panelVentas.relocateComponents(scrollW, scrollY);
        panelPedidos.relocateComponents(scrollW, scrollY);

        //Determinar el preferred size
        if (btnTrasvasos.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelTrasvasos.getPreferredSize());

        } else if (btnVentas.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelVentas.getPreferredSize());

        } else if (btnPedidos.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelPedidos.getPreferredSize());
        }

    }

    /**
     * Función para reemplazar el panel contenedor
     *
     * @param type Panel que será mostrado
     */
    public static void replacePanel(int type) {

        //Activar o desactivar el botón, según el tipo
        btnTrasvasos.setForeground((type == VENTAS_TRASVASO) ? AZUL_PRINCIPAL : NEGRO);
        btnVentas.setForeground((type == VENTAS_BOTELLON) ? AZUL_PRINCIPAL : NEGRO);
        btnPedidos.setForeground((type == VENTAS_PEDIDOS) ? AZUL_PRINCIPAL : NEGRO);

        //Determinar qué panel será mostrado, según el tipo
        card.show(
                contenedor,
                (type == VENTAS_BOTELLON) ? "2"
                        : (type == VENTAS_PEDIDOS) ? "3" : "1"
        );

        //Determinar el preferred size
        if (btnTrasvasos.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelTrasvasos.getPreferredSize());

        } else if (btnVentas.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelVentas.getPreferredSize());

        } else if (btnPedidos.getForeground().equals(AZUL_PRINCIPAL)) {
            contenedor.setPreferredSize(panelPedidos.getPreferredSize());
        }

    }

    /**
     * Función para vaciar todos los campos de todos los paneles
     */
    public static void vaciarCampos() {
        panelTrasvasos.vaciarCampos();
        panelVentas.vaciarCampos();
        panelPedidos.vaciarCampos();
    }

    /**
     * Función para establecer el cliente seleccionado en la factura de ventas
     *
     * @param ci
     * @param apellido
     */
    public static void setCliente(String ci, String apellido) {
        panelVentas.setCliente(ci, apellido);
    }

    /**
     * Función para asignar los datos para pagar un pedido de un cliente
     *
     * @param cedula
     * @param apellido
     * @param cantidad
     * @param tipoPago
     */
    public static void pagarPedido(String cedula, String apellido, int cantidad, String tipoPago) {
        panelVentas.pagarPedido(cedula, apellido, cantidad, tipoPago);
    }

    /**
     * Función para actualizar todos los datos de la pestaña
     *
     * @return
     */
    public static boolean actualizarDatos() {
        //retornar como busqueda exitosa cuando todas las actualizaciones
        //se hayan completado.
        return panelTrasvasos.actualizarDatos() && panelVentas.actualizarDatos() && panelPedidos.actualizarDatos();
    }

    //COMPONENTES
    private static final JScrollPane scroll = new JScrollPane();
    private static final JPanel menu = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 5));
    private static final JPanel contenedor = new JPanel();
    private static final CardLayout card = new CardLayout();
    private static final Label btnTrasvasos = new Label("Trasvasos", TITULO, 18);
    private static final Label btnVentas = new Label("Ventas", TITULO, 18);
    private static final Label btnPedidos = new Label("Pedidos", TITULO, 18);
    private static final Trasvasos panelTrasvasos = new Trasvasos();
    private static final PanelVentas panelVentas = new PanelVentas();
    private static final Pedidos panelPedidos = new Pedidos();
}

/**
 * Clase para la creación del panel de registro de ventas de botellones a los
 * clientes
 */
class PanelVentas extends JPanel implements properties.Constantes, properties.Colores {

    // ========== BACKEND ==========
    private void registrar() {
        //Mensaje de confirmación
        if (msjYesNo("¿Está seguro de realizar el registro de la venta?")) {

            if (validarCampos()) {
                if (validarDatos()) {

                    //Validar que el registro NO sea de números más grandes de 100
                    if (cantidad > 100) {

                        //En caso de ser alguno mayor de 100, lanzar un mensaje de alerta
                        String msj = "Está apunto de realizar un registro con una alta\n"
                                + "cantidad de botellones, ¿Está seguro de realizar el registro?";

                        if (msjYesNoWarning(msj)) {
                            CreateDB.createVenta(cantidad, tipoPago, checkDelivery.isSelected(), cedula);
                        }

                    } else {
                        CreateDB.createVenta(cantidad, tipoPago, checkDelivery.isSelected(), cedula);
                    }

                    vaciarCampos();
                }
            }
        }
    }

    /**
     * Función para comprobar que los campos NO estén vacíos y se haya
     * seleccionado un método de pago del cliente
     *
     * @return TRUE en caso de que todos los campos estén validos
     */
    private boolean validarCampos() {

        //Validar que los campos NO estén vacíos y que 
        //se haya seleccionado un tipo de pago
        if (!txtCantidad.getText().trim().isEmpty()) {
            if (boxTipoPago.getSelectedIndex() > 0) {
                if (!cedula.isEmpty() && !apellido.isEmpty()) {
                    return true;

                } else {
                    msjError("Debe seleccionar un cliente");
                }
            } else {
                msjError("Debe seleccionar un tipo de pago");
                boxTipoPago.requestFocus();
            }
        } else {
            msjError("La cantidad de botellones a vender no puede estár vacío.");
            txtCantidad.requestFocus();
        }

        //Retornar falso en caso de no retornar true anteriormente
        return false;
    }

    /**
     * Función para validar que los datos ingresados en los campos sean válidos
     *
     * @return TRUE en caso de que los datos sean válidos
     */
    private boolean validarDatos() {

        //Validar que la cantidad de botellones esté dentro del rango correcto
        if (cantidad > 0 && cantidad < 8388607) {

            return true;

        } else {
            msjError("La cantidad de botellones a vender es inválido."
                    + "\nPor favor, verifique la cantidad.");
            txtCantidad.requestFocus();
        }

        //Retornar falso en caso de no haber retornado true anteriormente
        return false;
    }

    /**
     * Función para asignar los mouse listener a los componentes
     */
    private void listeners() {
        //MOUSE LISTENERS
        btnAceptar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                registrar();
            }
        });
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                //Mensaje de confirmación
                String msj = "¿Está seguro de cancelar el trasvaso?\nSe vaciarán los campos y los datos.";
                if (msjYesNo(msj)) {
                    vaciarCampos();
                }
            }
        });
        btnCliente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                clickButton(CLIENTES);
            }
        });

        //KEY LISTENERS
        txtCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                //Retornar el valor convertido en entero, en caso de 
                //que sea válido la conversion.
                cantidad = teclaSuelta(txtCantidad.getText());

                //Actualizar la cantidad en la factura
                factura.setBotellonesTotal(cantidad);

                //Actualizar el monto total
                factura.setMontoTotal(precio * cantidad);
            }
        });

        //ACTION LISTENER
        checkDelivery.addActionListener((ActionEvent e) -> {
            //Enviar a factura el estado del check button cuando
            //este sea accionado.
            factura.setDelivery(checkDelivery.isSelected());
        });

        //ITEM LISTENER
        boxTipoPago.addItemListener((ItemEvent e) -> {
            //Obtener el index del item seleccionado
            int index = boxTipoPago.getSelectedIndex();

            //Validar el tipo de pago seleccionado
            switch (index) {
                case 1:
                    tipoPago = "EFECT";
                    factura.setTipoPago(tipoPago);
                    break;
                case 2:
                    tipoPago = "TRNSF";
                    factura.setTipoPago(tipoPago);
                    break;
                case 3:
                    tipoPago = "DOLAR";
                    factura.setTipoPago(tipoPago);
                    break;
                default:
                    tipoPago = "";
                    factura.setTipoPago(tipoPago);
                    break;
            }
        });
    }

    /**
     * Función para actualizar los datos del panel de información cada vez que
     * se visualice el panel de trasvasos
     *
     * @return
     */
    protected boolean actualizarDatos() {
        //Obtener el precio
        precio = ReadDB.getPrecioVenta();

        //Validar que el precio y el panel informativo, hayan buscado sus datos
        //en la base de datos exitosamente
        if (informacion.actualizarDatos() && precio != ERROR_NUMBER) {
            //Reposicionar el panel de información, según el 
            //ancho del contenedor
            if (width < 600) {
                informacion.relocateComponents(PANEL_MEDIANO);

            } else if (width < 900) {
                informacion.relocateComponents(PANEL_GRANDE);

            } else if (width >= 900) {
                informacion.relocateComponents(PANEL_MEDIANO);
            }
            //Retornar busqueda exitosa
            return true;

        } else {
            //Retornar busqueda incompleta
            return false;
        }
    }

    /**
     * Función para asignar el cliente seleccionado en la clase de ventas
     *
     * @param ci Cédula del cliente seleccionado
     * @param apellido Apellido del cliente seleccionado
     */
    protected void setCliente(String ci, String apellido) {
        PanelVentas.cedula = ci;
        PanelVentas.apellido = apellido;

        //Actualizar la factura
        PanelVentas.factura.setInformacion(cedula, apellido);
    }

    /**
     * Función para asignar los datos para pagar un pedido de un cliente
     *
     * @param cedula
     * @param apellido
     * @param cantidad
     * @param tipoPago
     */
    public static void pagarPedido(String cedula, String apellido, int cantidad, String tipoPago) {
        PanelVentas.cedula = cedula;
        PanelVentas.apellido = apellido;
        PanelVentas.cantidad = cantidad;

        //Poner el valor en los campos
        txtCantidad.setText(String.valueOf(cantidad));
        checkDelivery.setSelected(true);

        //Determinar el tipo de pago
        switch (tipoPago) {
            case "EFECT":
                boxTipoPago.setSelectedIndex(1);
                factura.setTipoPago(tipoPago);
                break;
            case "DOLAR":
                boxTipoPago.setSelectedIndex(3);
                factura.setTipoPago(tipoPago);
                break;
            case "TRNFS":
                boxTipoPago.setSelectedIndex(2);
                factura.setTipoPago(tipoPago);
                break;
            default:
                boxTipoPago.setSelectedIndex(0);
                factura.setTipoPago("");
                break;
        }

        //Actualizar la factura
        PanelVentas.factura.setInformacion(cedula, apellido);
        factura.setBotellonesTotal(cantidad);
        factura.setDelivery(true);
        factura.setMontoTotal(precio * cantidad);
    }

    //ATRIBUTOS BACKEND
    private static int cantidad = 0;
    private static double precio = 0;
    private static String tipoPago = "", cedula = "", apellido = "";

    // ========== FRONTEND ==========
    /**
     * Constructor del panel de venta de botellones
     */
    public PanelVentas() {
        this.setLayout(null);
        this.setOpaque(false);

        initComponents();
        listeners();
    }

    /**
     * Función para iniciar los componentes
     */
    private void initComponents() {

        boxTipoPago.setBackground(BLANCO);
        boxTipoPago.setFont(segoe(16, PLANO));

        checkDelivery.setOpaque(false);
        checkDelivery.setFont(segoe(18, PLANO));

        lblCantidad.setToolTipText(
                "<html>"
                + "Cantidad de botellones que se le <b>venderán al cliente.</b><br>"
                + "El límite es de 8.388.606."
                + "</html>"
        );
        lblTipoPago.setToolTipText("Tipo de pago realizado por el cliente");
        checkDelivery.setToolTipText("Determinar si la entrega se hará en el local o entrega a domicilio");

        panelVentas.add(lblTitulo);
        panelVentas.add(lblCantidad);
        panelVentas.add(txtCantidad);
        panelVentas.add(lblTipoPago);
        panelVentas.add(boxTipoPago);
        panelVentas.add(checkDelivery);
        panelVentas.add(btnCancelar);
        panelVentas.add(btnAceptar);

        panelVentas.setBorder(createLineBorder(GRIS));
        panelVentas.setBackground(BLANCO);

        this.add(btnCliente);
        this.add(factura);
        this.add(panelVentas);
        this.add(informacion);
    }

    /**
     * Función para reposicionar y redimensionar los componentes
     *
     * @param width Ancho del parent contenedor
     * @param height Altura del parent contenedor
     */
    public void relocateComponents(int width, int height) {

        this.width = width;

        //Altura de la factura (solo usado
        //en el panel pequeño)
        facY = padding * 2 + btnHeight;
        //Altura de la factura
        facHeight = height - btnHeight - padding * 3;
        //Altura del trasvaso y del panel
        //info (solo en el panel pequeño y grande)
        ventaHeight = height - padding * 2;

        //Posición del botón y factura
        btnCliente.setLocation(padding, padding);
        factura.setLocation(padding, facY);

        //PANEL PEQUEÑO
        if (width < 600) {

            //Cambiar el tamaño de la factura
            facHeight = 300;
            //Cambiar el tamaño de la venta
            ventaHeight = 440;
            panelPequenio();
            informacion.relocateComponents(PANEL_MEDIANO);

            //PANEL MEDIANO
        } else if (width < 900) {

            panelMediano();
            informacion.relocateComponents(PANEL_GRANDE);

            //PANEL GRANDE
        } else if (width >= 900) {

            //Asignar el tamaño del parent contenedor como
            //el tamaño preferido del panel
            this.setPreferredSize(new Dimension(width, height));

            panelGrande();

            this.revalidate();
            this.repaint();
            informacion.relocateComponents(PANEL_MEDIANO);
        }

        //Reposicionar los elementos de la factura
        factura.relocateComponents(facHeight < 281);
        relocateVentas();
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño menor a 600 px
     */
    private void panelPequenio() {

        //Tamaño del botón y factura
        int panelWidth = width - padding * 2;
        btnCliente.setSize(panelWidth, btnHeight);
        factura.setSize(panelWidth, facHeight);

        //Altura de la factura, sumando la altura de la factura, su
        //alto y un padding
        int trasvY = facY + facHeight + padding;
        //Asignar la posición y tamaño
        panelVentas.setBounds(padding, trasvY, panelWidth, ventaHeight);

        //Altura de la información, sumando la altura del panel de
        //trasvaso, su alto y un padding
        int infoY = trasvY + ventaHeight + padding;
        int infoHeight = 348;
        //Asignar la posición y tamaño
        informacion.setBounds(padding, infoY, panelWidth, infoHeight);

        //Calcular la altura absoluta del panel contenedor, sumando cinco
        //padding, la altura del botón, la altura de la factura y dos 
        //veces la altura del panel de trasvaso
        int absoluteHeight = padding * 5 + btnHeight + facHeight + ventaHeight + infoHeight;

        //Asignar el tamaño preferido al panel
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño mayor a 600px, pero menor a
     * 900 px
     */
    private void panelMediano() {
        this.facHeight += 20;
        this.ventaHeight += 20;

        btnCliente.setSize(facMaxWidth, btnHeight);
        factura.setSize(facMaxWidth, facHeight);

        //Posicionar el panel de trasvaso después de la factura
        int trasvX = padding * 2 + facMaxWidth;
        //Obtener el ancho del panel, menos la posición en X del panel
        //de trasvaso y restarle un padding
        int trasvWidth = width - trasvX - padding;
        //Asignar la posición y el tamaño
        panelVentas.setBounds(trasvX, padding, trasvWidth, ventaHeight);

        //Posicionar el panel info debajo del panel de trasvaso
        int infoY = padding * 2 + ventaHeight;
        //Asignarle todo el ancho del panel
        int infoWidth = width - padding * 2;
        //Asignarle la altura estática
        int infoHeight = 226;
        //Asignar la posición y el tamaño
        informacion.setBounds(padding, infoY, infoWidth, infoHeight);

        //Calcular la altura absoluta del panel contenedor, sumando
        //tres padding, la altura del panel de trasvaso y la altura
        //del panel de info
        int absoluteHeight = padding * 3 + ventaHeight + infoHeight;

        //Asignar el tamaño preferido al panel
        this.setPreferredSize(new Dimension(width, absoluteHeight));
    }

    /**
     * Función para reposicionar y redimensionar los componentes de la clase
     * cuando el panel contenedor tenga un tamaño mayor a 900 px
     */
    private void panelGrande() {

        this.width += 15;
        this.facHeight += 15;
        this.ventaHeight += 15;

        //Dividir el panel en 3 partes iguales
        int thirdWidth = width / 3 - padding * 2;

        //Comprobar que el tercio del panel NO supere el 
        //ancho máximo posible del botón y factura
        int facWidth = (thirdWidth > facMaxWidth) ? facMaxWidth : thirdWidth;
        btnCliente.setSize(facWidth, btnHeight);
        factura.setSize(facWidth, facHeight);

        //Calcular la posición del panel info a la derecha del panel,
        //obteniendo el ancho del panel, menos el ancho máximo para
        //el panel info y restarle un padding
        int infoX = width - facWidth - padding;
        //Asignar la posición y el tamaño
        informacion.setBounds(infoX, padding, facWidth, ventaHeight);

        //Calcular la posición en X del panel trasvaso, sumando
        //el ancho de la factura y dos padding
        int trasvX = facWidth + padding * 2;
        //Calcular el ancho del panel trasvaso, obteniendo el ancho del
        //panel, menos el ancho de la factura y del panel info, y
        //restandole cuatro paddings
        int trasvWidth = (width - facWidth * 2) - padding * 4;
        panelVentas.setBounds(trasvX, padding, trasvWidth, ventaHeight);
    }

    /**
     * Función para posicionar los componentes del panel de ventas
     */
    private void relocateVentas() {
        int txtHeight = 40;
        int gapV = 5;
        int trasW = panelVentas.getWidth();
        int txtWidth = trasW - padding * 2;

        //Asignar el tamaño a los componentes que requieran
        //declarar sus tamaños
        boxTipoPago.setSize(txtWidth, txtHeight);
        checkDelivery.setSize(checkDelivery.getPreferredSize());
        txtCantidad.setSize(txtWidth, txtHeight);

        //Posición del título del panel para los datos
        lblTitulo.setLocation(padding, padding / 2);

        //Para posicionar los campos en el centro vertical del panel
        //primero se obtiene le punto medio del panel
        int middleY = panelVentas.getHeight() / 2;
        //Luego la suma de la altura de TODOS los componentes y sus labels
        int allHeights = txtHeight * 2 + checkDelivery.getHeight() + lblCantidad.getHeight() * 2;
        //Finalmente calcular el punto medio, sumando, además, los padding utilizados 
        int positionY = middleY - (allHeights + padding * 4) / 2;
        lblCantidad.setLocation(padding, positionY);

        //Posición vertical para el primer campo de texto
        positionY = positionY + lblCantidad.getHeight() + gapV;
        txtCantidad.setLocation(padding, positionY);

        //Posición vertical del label para el comboBox
        positionY = positionY + txtHeight + padding;
        lblTipoPago.setLocation(padding, positionY);

        //Posición vertical para el comboBox
        positionY = positionY + lblTipoPago.getHeight() + gapV;
        boxTipoPago.setLocation(padding, positionY);

        //Posición vertical y horizontal para el checkBox
        int positionX = trasW / 2 - checkDelivery.getWidth() / 2;
        positionY = positionY + txtHeight + padding;
        checkDelivery.setLocation(positionX, positionY);

        //Posición vertical de los dos botones inferiores
        txtHeight += 10;
        positionY = panelVentas.getHeight() - txtHeight - padding;

        //Ancho del botón de cancelar
        int btnW = btnCancelar.getPreferredSize().width + 30;
        btnCancelar.setSize(btnW, txtHeight);
        btnCancelar.setLocation(padding, positionY);

        //Ancho del botón de aceptar
        positionX = padding * 2 + btnW;
        btnW = trasW - btnCancelar.getWidth() - padding * 3;
        btnAceptar.setSize(btnW, txtHeight);
        btnAceptar.setLocation(positionX, positionY);
    }

    /**
     * Función para vaciar los campos del panel de ventas
     */
    protected void vaciarCampos() {
        //Restablecer los campos
        txtCantidad.setText("");
        checkDelivery.setSelected(false);
        boxTipoPago.setSelectedIndex(0);

        //Restablecer la factura
        factura.setBotellonesTotal(0);
        factura.setDelivery(false);
        factura.setTipoPago("");
        factura.setMontoTotal(0.00);
        factura.setInformacion("", "");

        //Vaciar los atributos
        cantidad = 0;
        tipoPago = "";
        cedula = "";
        apellido = "";
    }

    //Atributos
    private int width, facHeight, ventaHeight, facY;
    private final int padding = 20;
    private final int btnHeight = 80;
    private final int facMaxWidth = 300;

    //COMPONENTES
    private static final Boton btnCliente = new Boton(SELECT_CLIENTE);
    private static final PanelFactura factura = new PanelFactura(VENTAS_BOTELLON);
    private static final JPanel panelVentas = new JPanel(null);
    private static final PanelInfo informacion = new PanelInfo(VENTAS_BOTELLON);

    private static final Label lblTitulo = new Label("Venta de botellones", TITULO, 24);

    private static final Label lblCantidad = new Label("Cantidad de botellones", PLANO, 18, true);
    private static final CampoTexto txtCantidad = new CampoTexto("Botellones a vender", NUMERO);

    private static final Label lblTipoPago = new Label("Tipo de pago", PLANO, 18, true);
    private static final String opciones[] = {"Seleccionar", "Efectivo", "Transferencia", "Dolar en efectivo"};
    private static final JComboBox boxTipoPago = new JComboBox(opciones);

    private static final JCheckBox checkDelivery = new JCheckBox("Entrega domicilio");

    private static final Boton btnAceptar = new Boton("Registrar", VERDE);
    private static final Boton btnCancelar = new Boton("Cancelar", ROJO_OSCURO);
}
